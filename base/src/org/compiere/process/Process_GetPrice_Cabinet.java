/******************************************************************************
     * BETA CODE --------------AUTHOR -> SUBAIR P.K. 
	 * Date of Creation : 11 Oct 2011
     * Class for Getting price and BOM in Cabinet Quotation from Excel sheet
 *****************************************************************************/

package org.compiere.process;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.logging.Level;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class Process_GetPrice_Cabinet extends SvrProcess{

	private int record_id=0;
	private int AD_Client_ID;
	private int AD_Org_ID;
	
	private int r = 0;
    private int c = 0;
    private int fr_r = 0;
    private int to_r = 0;
    private int fr_c = 0;
    private int to_c = 0;
	
	private int M_Product_ID;
    private String Path_of_XLFile;
    private String FGCost_Cell_Addr;
    private String Lock_Status;
    private String Prod_Value;
    private double prod_cost;
    
    private String fr_rmprod_cell_addr;
	private String to_rmprod_cell_addr;
	  
	private String fr_rmQty_cell_addr;
	private String fr_rmCF_cell_addr;
	private String fr_rmStdCost_cell_addr;
	private String fr_rmUnitCost_cell_addr;
	private String fr_rmCost_cell_addr;
	/// Modified by Abdulla on 06-02-2013 Begin
	  private double m_RawStk_cost;
    /// Modified by Abdulla on 06-02-2013 Endi
	  
	private int C_Orderline_BOM_ID;
	private BigDecimal AD_User_ID;
	
    POIFSFileSystem fs ;
    HSSFWorkbook wb;
    HSSFSheet sheet ;
    Iterator rows; 
    HSSFRow row;
    FormulaEvaluator evaluator ;
    BigDecimal price;
    
    private String a_cell_addr; 
	
	protected void prepare() 
	{
		 record_id = getRecord_ID();
		 AD_Client_ID =Env.getAD_Client_ID(getCtx());
		 AD_Org_ID = Env.getAD_Org_ID(getCtx());
			    
	} // prepare
	
	protected String doIt() throws Exception 
	{	
		System.out.println(" Inside doIt meth");
		System.out.println("Record ID : " + record_id);

		String sql_cnt = " select count(1) from C_OrderLine_BOM where c_orderline_id = ?" ;
		int count = DB.getSQLValue(get_TrxName(), sql_cnt, record_id);
		   
		if (count > 0)
		{
	   	     String sql_del = " delete from C_OrderLine_BOM where c_orderline_id = " + record_id ;
			 int del_rmcost = DB.executeUpdate(sql_del, get_TrxName()); 
			 
			 String upd_gtotal = " select sum(linenetamt) from c_orderline where c_order_id in " +
			 "(select distinct c_order_id from c_orderline" +
			 " where c_orderline_id = ? )";
			 BigDecimal line_total = DB.getSQLValueBD(get_TrxName(), upd_gtotal, record_id);
			 BigDecimal RndGrAmt = line_total.setScale(0,BigDecimal.ROUND_HALF_UP);
			 		
			 String sql_hdr = " update c_order set grandtotal =  " + RndGrAmt + 
			         ",totallines = " + RndGrAmt + " where c_order_id in (select distinct c_order_id from c_orderline" +
			         " where c_orderline_id = " + record_id + ")";
			  int upd_grandtotal = DB.executeUpdate(sql_hdr, get_TrxName());
		}
		
		//Update the GetPrice button value to 'N', to mark the start of the process
	    String sql_getp1 = " update c_orderline set GetPrice = 'N' where c_orderline_id = " + record_id;
	    int getp_status1 = DB.executeUpdate(sql_getp1, get_TrxName());
		
		ExecuteProcess(record_id);
		
	  	
	  	// Code for BOM Starts
	  	String sql_3 = "select mpb.FROM_RMPROD_CELL_ADDR,mpb.TO_RMPROD_CELL_ADDR,mpb.QTY_CELL_ADDR,mpb.TO_QTY_CELL_ADDR," +
	  		" mpb.COSTFACTOR_CELL_ADDR,mpb.TO_COSTFACTOR_CELL_ADDR,mpb.STDCOST_CELL_ADDR,mpb.TO_STDCOST_CELL_ADDR," +
	  		" mpb.UNITCOST_CELL_ADDR,mpb.TO_UNITCOST_CELL_ADDR,mpb.RMCOST_CELL_ADDR,mpb.TO_RMCOST_CELL_ADDR,nvl(col.createdby,100)" +
	  		" from m_product_bom mpb inner join c_orderline col " +
	  		" on mpb.m_product_id = col.m_product_id where mpb.m_product_id = ?" +
	  		" and col.c_orderline_id = ? ";
	  	PreparedStatement pstmt5 = null;
		ResultSet rs5 = null;
		try
	    {
			System.out.println(" Inside BOM");
	    	pstmt5 = DB.prepareStatement(sql_3, null);
	    	pstmt5.setInt(1, M_Product_ID);
	    	pstmt5.setInt(2,record_id);
	    	rs5 = pstmt5.executeQuery();
	    	
	    	if (rs5.next())
	    	{
	    		
	    		fr_rmprod_cell_addr = rs5.getString(1);
	    		to_rmprod_cell_addr = rs5.getString(2);
	    		fr_rmQty_cell_addr = rs5.getString(3);
	    		fr_rmCF_cell_addr = rs5.getString(5);
	    		fr_rmStdCost_cell_addr = rs5.getString(7);
	    		fr_rmUnitCost_cell_addr = rs5.getString(9);
	    		fr_rmCost_cell_addr = rs5.getString(11);
	    		AD_User_ID = rs5.getBigDecimal(13);

	    		String from = "select fn_getcelladdres(?) from dual ";
	    		PreparedStatement pstmt_gen = null;
	    		ResultSet rs_gen = null;
		   	 	try // to get From Cell addr
		   	 	{
		   	 		pstmt_gen = DB.prepareStatement(from, null);
		   	 		pstmt_gen.setString(1,fr_rmprod_cell_addr);
		   	 		rs_gen = pstmt_gen.executeQuery();
			  
		   	 		if (rs_gen.next())
		   	 		{
		   	 			String celladdr = rs_gen.getString(1);
		   	 			int length = celladdr.length();
		   	 			String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
		   	 			fr_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
			      
		   	 			String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
		   	 			fr_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
		   	 		}
		   	 		rs_gen.close();
		   	 		pstmt_gen.close();
		   	 	}catch(Exception e){} //for fn_getcelladdr
		   	 	finally 
		   	 	{
		   	 		DB.close(rs_gen, pstmt_gen);
		   	 	}
 			    
		   	 	String to = "select fn_getcelladdres(?) from dual ";
		   	 	try // to get To Cell addr
		   	 	{
		   	 		pstmt_gen = DB.prepareStatement(to, null);
		   	 		pstmt_gen.setString(1,to_rmprod_cell_addr);
		   	 		rs_gen = pstmt_gen.executeQuery();
		    	    		
		   	 		if (rs_gen.next())
		   	 		{
		   	 			String celladdr = rs_gen.getString(1);
		   	 			int length = celladdr.length();
		    	
		   	 			String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
		   	 			to_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
 			 
		   	 			String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
		   	 			to_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
		   	 		}
		   	 		rs_gen.close();
		   	 		pstmt_gen.close();
		   	 	}catch(Exception e){} //for fn_getcelladdr
		   	 	finally 
		   	 	{
		   	 		DB.close(rs_gen, pstmt_gen);
		   	 	}
		   	 	int X = 1;		
		   	 	int k =0;int l=0; int m =0; int n=0 ; int o = 0;
		   	 	int k1 =0;int l1=0; int m1 =0; int n1=0 ; int o1 = 0;
		   	 	System.out.println("client : " + AD_Client_ID );
		   	 	for (int i=fr_r;i<=to_r;i++)	    
		   	 	{    
		   	 		int j= fr_c;
		   	 		
		   	 		row=sheet.getRow(i);
		   	 		HSSFCell prod_cell = row.getCell(j);
		   	 		//String bom_prod = prod_cell.getStringCellValue();
		   	 		CellValue prod_cellValue = evaluator.evaluate(prod_cell);
		   	 		String bom_prod = prod_cellValue.getStringValue();
		  	   	   	    	
		   	 		String sql_prod = " Select m_product_id from m_product where trim(upper(value)) = " +
		   	       		" trim(upper(?)) and ad_client_id = " + AD_Client_ID;
		   	 		Integer m_product_id = DB.getSQLValue(get_TrxName(), sql_prod, bom_prod);
		   	 				   	 		
		   	 	//***************Abdulla
		       	 String sql_prod_Cost = " Select currentcostprice from m_cost where m_product_id = " +
		       		" ? and m_costelement_id = 1000001 and ad_client_id = " + AD_Client_ID;
		       BigDecimal m_RawStk_cost = DB.getSQLValueBD (get_TrxName(), sql_prod_Cost,m_product_id);
		       	 
		      //System.out.println("Prodct Cost : " + m_RawStk_cost);} //for Quotationline
		       	 
		     //****************Abdulla
		   	 		
		   	 		if (m_product_id == null || m_product_id <= 0 )
		   	 		{
		   	 			String sql_del = " delete from c_orderline_bom where c_orderline_id = " + record_id ;
		   	 			int del_rmcost = DB.executeUpdate(sql_del, get_TrxName()); 
		   	 			
		   	 			String sql_upd1 = " update c_orderline set pricelist  = 0, pricelimit=0, PriceActual=0, PriceEntered=0 "+ 
		   	 			"  ,LineNetAmt = 0 where m_product_id = " + M_Product_ID + 
		   	 			" and c_orderline_id = " + record_id;
		   	 			int no1 = DB.executeUpdate(sql_upd1, get_TrxName());  
	  	    	
		   	 			String sql_gt1 = "select sum(LineNetAmt) from c_orderline" +
		   	 			" where c_order_id in (select distinct c_order_id from c_orderline" +
		   	 			" where c_orderline_id = ? )";
	  	    	
		   	 			BigDecimal totline_amt1 = DB.getSQLValueBD(get_TrxName(), sql_gt1, record_id);
		   	 			BigDecimal RndAmt1 = totline_amt1.setScale(0,BigDecimal.ROUND_HALF_UP);
	  	  		
		   	 			String sql_hdr1 = " update c_order set grandtotal = "+ RndAmt1 + 
		   	 			",totallines = "+ RndAmt1 + 
		   	 			"  where c_order_id in (select distinct c_order_id from c_orderline" +
		   	 			" where c_orderline_id = " + record_id + ")";
		   	 			int grand_total1 = DB.executeUpdate(sql_hdr1, get_TrxName());

		   	 			String sql_upd_prod = " update m_product set lock_status = 'N' where m_product_id = "+M_Product_ID
	  	  				+ " and ad_client_id = " + AD_Client_ID;
		   	 			int lock_status = DB.executeUpdate(sql_upd_prod, get_TrxName());
		   	 			addLog(" Product MisMatch Between Excel Sheet & Product Master (Excel Item : " +  bom_prod + ")");
		   	 			return "";
		   	 		}
		   	 		
		   	 		/****For Qty ***/  
		   	 		int qty_r = 0; 
		   	 		int qty_c = 0; 
		   	  	 
		   	    	String from_qty = "select fn_getcelladdres(?) from dual ";
		   		   	try // to get From Cell addr
		   		   	{
		   		   		pstmt_gen = DB.prepareStatement(from_qty, null);
		   		   		pstmt_gen.setString(1,fr_rmQty_cell_addr);
		   		   		rs_gen = pstmt_gen.executeQuery();
		   			  
		   		   		if (rs_gen.next())
		   		   		{
		   		   			String celladdr = rs_gen.getString(1);
		   		   			int length = celladdr.length();
		   		   			String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
		   		   			qty_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
		   			      
		   		   			String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
		   		   			qty_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
		   		   		}
		   		   		rs_gen.close();
		   		   		pstmt_gen.close();
		   		   	 }catch(Exception e){} //for fn_getcelladdr
		   			 finally 
		   			 {
		   				 DB.close(rs_gen, pstmt_gen);
		   			 }
		   			 k = qty_c;
		   	 		
		   			 /****For COST FACTOR ***/   
			         int CF_r = 0; 
			         int CF_c = 0; 
			    	 String from_CF = "select fn_getcelladdres(?) from dual ";
				   	 try // to get From Cell addr
				   	 {
				   		 pstmt_gen = DB.prepareStatement(from_CF, null);
				   		 pstmt_gen.setString(1,fr_rmCF_cell_addr);
				   		 rs_gen = pstmt_gen.executeQuery();
					  
				   		 if (rs_gen.next())
				   		 {
				   			 String celladdr = rs_gen.getString(1);
				   			 int length = celladdr.length();
				   			 String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
				   			 CF_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
					      
				   			 String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
				   			 CF_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
				   		 }
				   		 rs_gen.close();
				   		 pstmt_gen.close();
				   	 }catch(Exception e){} //for fn_getcelladdr
					 finally 
					 {
						 DB.close(rs_gen, pstmt_gen);
					 }
					 l = CF_c;
		   	 		
					 /****For STANDARD COST ***/   
			         int SC_r = 0; 
			         int SC_c = 0; 
			    	 String from_SC = "select fn_getcelladdres(?) from dual ";
				   	 try // to get From Cell addr
				   	 {
				   		 pstmt_gen = DB.prepareStatement(from_SC, null);
				   		 pstmt_gen.setString(1,fr_rmStdCost_cell_addr);
				   		 rs_gen = pstmt_gen.executeQuery();
					  
				   		 if (rs_gen.next())
				   		 {
				   			 String celladdr = rs_gen.getString(1);
				   			 int length = celladdr.length();
				   			 String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
				   			 SC_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
					      
				   			 String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
				   			 SC_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
				   		 }
				   		 rs_gen.close();
				   		 pstmt_gen.close();
				   	  }catch(Exception e){} //for fn_getcelladdr
				   	  finally 
				   	  {
						 DB.close(rs_gen, pstmt_gen);
				   	  }
				   	  m = SC_c;
				   	  
				   	 /****For UNIT COST ***/ 
				     int UC_r = 0; 
				     int UC_c = 0; 
				     String from_UC = "select fn_getcelladdres(?) from dual ";
					 try // to get From Cell addr
					 {
					   	  pstmt_gen = DB.prepareStatement(from_UC, null);
					   	  pstmt_gen.setString(1,fr_rmUnitCost_cell_addr);
						  rs_gen = pstmt_gen.executeQuery();
						  
						  if (rs_gen.next())
						  {
							  String celladdr = rs_gen.getString(1);
						      int length = celladdr.length();
						      String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
						      UC_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
						      
						      String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
						      UC_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
						  }
						  rs_gen.close();
						  pstmt_gen.close();
					   	}catch(Exception e){} //for fn_getcelladdr
						finally 
						{
							 DB.close(rs_gen, pstmt_gen);
						}
						n = UC_c;
						
						/****For RM COST ***/ 
				        int RMC_r = 0; 
				        int RMC_c =0;
					    String from_RMC = "select fn_getcelladdres(?) from dual ";
						try // to get From Cell addr
						{
							pstmt_gen = DB.prepareStatement(from_RMC, null);
						   	pstmt_gen.setString(1,fr_rmCost_cell_addr);
							rs_gen = pstmt_gen.executeQuery();
							  
							if (rs_gen.next())
							{
								String celladdr = rs_gen.getString(1);
							    int length = celladdr.length();
							    String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
							    RMC_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
							      
							    String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
							    RMC_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
							 }
    						 rs_gen.close();
							 pstmt_gen.close();
						  }catch(Exception e){} //for fn_getcelladdr
						  finally 
						  {
							 DB.close(rs_gen, pstmt_gen);
						  }
					  	  o = RMC_c;
					  	  
					  	  //qty
					  	  row = sheet.getRow(i);
					      HSSFCell Qty_cell = row.getCell(k);
					      //costfactor
					  	  row = sheet.getRow(i);
					      HSSFCell costfact_cell = row.getCell(l); 	         
					      //std cost   
						  row = sheet.getRow(i);
					      HSSFCell sc_cell = row.getCell(m);       
					      //unit cost
						  row = sheet.getRow(i);
					      HSSFCell uc_cell = row.getCell(n);
					      //tot.cost
					      row = sheet.getRow(i);
					      HSSFCell cost_cell = row.getCell(o);
					
					      if (cost_cell == null || Qty_cell == null || costfact_cell == null || sc_cell == null|| uc_cell == null)
					      {
					    	  String sql_updprod = " update m_product set lock_status = 'N' where m_product_id = "+M_Product_ID
			  	  			  + " and ad_client_id = " + AD_Client_ID;
				   	 	      int lockstatus = DB.executeUpdate(sql_updprod, get_TrxName());
					    	  return "";
					      } 
					  	   
					      CellValue cost_cellValue = evaluator.evaluate(cost_cell);
						  double bom_cost = cost_cellValue.getNumberValue();
						  Double bom = new Double(cost_cellValue.getNumberValue());
						
						  CellValue Qty_cellValue = evaluator.evaluate(Qty_cell);
						  double bom_Qty = Qty_cellValue.getNumberValue();
						
						  CellValue cf_cellvalue = evaluator.evaluate(costfact_cell);
						  double bom_cf = cf_cellvalue.getNumberValue();
						
						  CellValue sc_cellvalue = evaluator.evaluate(sc_cell);
						  double bom_sc = sc_cellvalue.getNumberValue();

						  CellValue uc_cellvalue = evaluator.evaluate(uc_cell);
						  double bom_uc = uc_cellvalue.getNumberValue();

						  /**to get sequence id for c_orderline_bom*/
						  String sql = "{call proc_upd_seq_no(?,?,?,?)}";
						  CallableStatement cstmt = null;
						  try 
						  {
							  cstmt = DB.prepareCall(sql);
							  cstmt.setInt(1,AD_Client_ID);
							  cstmt.setInt(2,AD_Org_ID);
							  cstmt.setString(3,"C_ORDERLINE_BOM");
							  cstmt.registerOutParameter(4, java.sql.Types.INTEGER);
							  cstmt.executeUpdate();
							  C_Orderline_BOM_ID = cstmt.getInt(4);
							  cstmt.close();
						   }catch(Exception e){}
						   finally 
						   {
								DB.close(cstmt);
						   }
						 
						   /** Insert into Order Line BOM */
						   String sql_2 = "insert into C_OrderLine_BOM(C_ORDERLINE_BOM_ID,AD_CLIENT_ID," +
						   "AD_ORG_ID,ISACTIVE,CREATED,CREATEDBY,UPDATED,UPDATEDBY,C_ORDERLINE_ID," +
						   " M_PRODUCT_ID,QUANTITY,TOTCOST,COST_FACTOR," +
						   " STDCOST,UNITCOST,RAWMAT_AVG_COST)" +
						   " values("+C_Orderline_BOM_ID+"," +AD_Client_ID+","
						   +AD_Org_ID+",'Y',sysdate," + AD_User_ID + ",sysdate," + AD_User_ID + ","+record_id+
						   ","+m_product_id+","+bom_Qty+","+bom_cost + "," + bom_cf + 
						   ","+bom_sc+","+ bom_uc +","+ m_RawStk_cost +")";
							
					   	   int a = DB.executeUpdate(sql_2,get_TrxName());
				          
				}//end of For loop
			} // if loop for BOM Ends 
			rs5.close();
			pstmt5.close();
		}catch(Exception e){}
		finally 
		{
			DB.close(rs5, pstmt5);
		}
		   	   
		//Get Cost Factor from Acc.Schema
		String sql_1 = "select MARKUP_PCNT from c_acctschema where ad_client_id = ? ";
	    BigDecimal markup_cost = DB.getSQLValueBD(get_TrxName(), sql_1, AD_Client_ID);
	    System.out.println("Price Factor in Acct Schema : " + markup_cost);
		
	    //Multiply Cost Factor * Est.Cost (from Excel) to get selling price
		price = new BigDecimal(prod_cost).multiply(markup_cost);
		price = price.setScale(0,BigDecimal.ROUND_CEILING);
		System.out.println(" Price in Quotation Line (incl. Price Factor):" + price);

		//Find discount and calculate the net price
		String sql_disc = " select discount from c_orderline where c_orderline_id = ? ";
		BigDecimal disc_pcnt = DB.getSQLValueBD(null, sql_disc,record_id);
		System.out.println("Disc pcnt : " + disc_pcnt);
		BigDecimal disc_amt = (new BigDecimal(100).subtract(disc_pcnt)).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
		System.out.println("Disc Amt: " + disc_amt);
		BigDecimal netprice = price.multiply(disc_amt);
		netprice = netprice.setScale(0,BigDecimal.ROUND_HALF_UP);
		System.out.println("Net Price : " + netprice);
		
		//Update price into OrderLine
		String sql_upd = " update c_orderline set pricelist = " + price + " ,pricelimit = " + price +
  		",PriceActual = " + netprice + ",PriceEntered = " + netprice + ",LineNetAmt = " + netprice + " * Qtyordered " + 
  		"where m_product_id = " + M_Product_ID + " and c_orderline_id = " + record_id;
	    int no = DB.executeUpdate(sql_upd, get_TrxName());
	    
	    //Update GrandTotal into Order Header
	    String sql_gt = "select sum(LineNetAmt) from c_orderline where c_order_id  in " +
	  	" (select distinct c_order_id from c_orderline " +
	  	" where c_orderline_id = ? )";
	  	BigDecimal totline_amt = DB.getSQLValueBD(get_TrxName(), sql_gt, record_id);
	  	BigDecimal RndAmt = totline_amt.setScale(0,BigDecimal.ROUND_HALF_UP);
	  	String sql_hdr = " update c_order set grandtotal =  " + RndAmt + 
	  	      " ,totallines = " + RndAmt + " where " +
	  	      " c_order_id in (select distinct c_order_id from c_orderline" +
	  	      " where c_orderline_id = " + record_id + ")";
	  	int grand_total = DB.executeUpdate(sql_hdr, get_TrxName());  
	  	
		
	  	//Change the lock status in the product master
	    String sql_prod = " update m_product set lock_status = 'N' where m_product_id = "+M_Product_ID
			+ " and ad_client_id = " + AD_Client_ID;
	    int lock_status = DB.executeUpdate(sql_prod, get_TrxName());

	    //Update the GetPrice button value to 'Y', to confirm the GetPrice process run successfully
	    String sql_getp = " update c_orderline set GetPrice = 'Y' where c_orderline_id = " + record_id;
	    int getp_status = DB.executeUpdate(sql_getp, get_TrxName());
	    
		return "";
	} // doIt
	
	void ExecuteProcess(int record_id)  //This procedure will enter the input value into Excel and will fetch tot.est cost
	{
			
			double ExRowNo;
			int r1 = 0,c1 = 0;
			int r = 0,c = 0;
			
			String sql1 = "SELECT col.m_product_id,mp.EXCEL_FILE_NAME," +
	  		" mp.FGCOST_CELL_ADDR,mp.LOCK_STATUS,mp.VALUE,nvl(mp.ExcelRowNo,0), nvl(mp.a_cell_addr,0) " +
	  		" from c_orderline col" +
	  		" INNER JOIN m_product mp ON col.m_product_id = mp.m_product_id" +
	  		" where col.c_orderline_id = ? and mp.a_cell_addr is not null and mp.ExcelRowNo is not null" ;
    				
		    PreparedStatement pstmt1 = null;
		    ResultSet rs1 = null;
			try 
			{
				pstmt1 = DB.prepareStatement(sql1, "DSPL");
				pstmt1.setInt(1, record_id);
				rs1 = pstmt1.executeQuery();
				if(rs1.next()) 
				{	 	
					System.out.println("Inside ExecuteProcess");
					M_Product_ID = rs1.getInt(1);
					Path_of_XLFile = rs1.getString(2);
					FGCost_Cell_Addr = rs1.getString(3);
					Lock_Status = rs1.getString(4);
					Prod_Value = rs1.getString(5);
					ExRowNo = rs1.getDouble(6);
					a_cell_addr = rs1.getString(7);
					
					if (Lock_Status.equalsIgnoreCase("N"))
					{
						String sql_prod = " update m_product set lock_status = 'Y' where m_product_id = "+M_Product_ID
					 				 + " and ad_client_id = " + AD_Client_ID;
						int lock_status = DB.executeUpdate(sql_prod, get_TrxName());
					}
			    	else
			    	{
			    		Thread.sleep(5000);
			    		addLog( "Excel file of Product ' " +Prod_Value+
					    	    					" ' being accessed by another user. Waiting for him to close'");
					 }
					 Path_of_XLFile = ""+Path_of_XLFile + "";
					 InputStream input = new FileInputStream(Path_of_XLFile);
					 fs = new POIFSFileSystem( input );
				   	 wb = new HSSFWorkbook(fs);
					 sheet = wb.getSheetAt(0);
				   	 rows = sheet.rowIterator(); 
				   	 row = (HSSFRow) rows.next();
				   	    
				   	 evaluator = wb.getCreationHelper().createFormulaEvaluator();
				   	 
				   	 /** TO  Set the Input values into Excel */
				   	 
				   	 /** A Cell Address */
				   	 String sql_row_col1 = "select fn_getcelladdres(?) from dual ";
					 PreparedStatement pstmt2 = null;
					 ResultSet rs2 = null;
					 try // fn to get cell address
				   	 {
						 pstmt2 = DB.prepareStatement(sql_row_col1, null);
				   	  	 pstmt2.setString(1,a_cell_addr);
				   	  	 rs2 = pstmt2.executeQuery();
				   		
				   	  	 if (rs2.next())
				   	  	 {
				   	  		 String celladdr = rs2.getString(1);
				   	  		 int length = celladdr.length();
				  		 
				   	  		 String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
				   	  		 r1 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
					    		    	    			
				   	  		 String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
				   	  		 c1 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
				   	  	  } //end of if
				   	  	  rs2.close();
				   	  	  pstmt2.close();
					   }catch(Exception e){  log.log(Level.SEVERE, sql_row_col1, e);  } // end of fn_getcelladdr
					   finally { DB.close(rs2, pstmt2);   }
				   	 
					   if (r1 != 0)
					   {
						   HSSFRow row1 = sheet.getRow(r1); //Row
						   HSSFCell attr_cost1 = row1.getCell(c1); //Column
						   
						   System.out.println("Input value :" + ExRowNo);
						   attr_cost1.setCellValue(ExRowNo);
					   }
					   FileOutputStream fileOut = new FileOutputStream(Path_of_XLFile);
					   wb.write(fileOut);
					   fileOut.flush();   //End of Input values
					   
					   // to get price from XL Starts
					   String sql_row_col = "select fn_getcelladdres(?) from dual ";
					   PreparedStatement pstmt3 = null;
					   ResultSet rs3 = null;
					   try
					   {
						   
					   	 	pstmt3 = DB.prepareStatement(sql_row_col, null);
					   	 	pstmt3.setString(1,FGCost_Cell_Addr);
					   	 	rs3 = pstmt3.executeQuery();
					   	
					   	 	if (rs3.next())
					   	 	{
					   	 		String celladdr = rs3.getString(1);
					   	 		int length = celladdr.length();
					   		    	 
					   	 		String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
					   	 		r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
					   		    	 
					   	 		String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
					   	 		c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
					   	 	 }
					   	 	rs3.close();
					   	 	pstmt3.close();
					   	}catch(Exception e){} //for FG COST Cell Addr fn_getcelladdr
						finally {DB.close(rs3, pstmt3); }
					   	  
						row = sheet.getRow(r);
						HSSFCell cell = row.getCell(c);
					 
						CellValue cellValue1 = evaluator.evaluate(cell);
					 	prod_cost = cellValue1.getNumberValue();
					    System.out.println(" prod_cost : " + prod_cost);  
					    
				}
				
			} catch(Exception e) {  }
			
	} //ExecuteProcess
}
