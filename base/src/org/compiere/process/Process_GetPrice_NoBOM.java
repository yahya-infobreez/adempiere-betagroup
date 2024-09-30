package org.compiere.process;

//Get Price Class created by Subair @ 16.04.2013 for OD items Like OD4, OD8, OD9

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.logging.Level;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
//import org.apache.poi.hssf.record.formula.functions.Roundup;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class Process_GetPrice_NoBOM extends SvrProcess{

	  private int r = 0;
	  private int c = 0;
	      
	  private int M_Product_ID;
	  private String Path_of_XLFile;
	  private String FGCost_Cell_Addr;
	  private String Lock_Status;
	  private String Prod_Value;
	  private String v_description;
	  private int AD_Client_ID;
      private int AD_Org_ID;
	  private int record_id;
	  
	  POIFSFileSystem fs ;
      HSSFWorkbook wb;
      HSSFSheet sheet ;
      Iterator rows; 
      HSSFRow row;
      FormulaEvaluator evaluator ;
      private double prod_cost;
	  private String a_cell_addr;
	  private String b_cell_addr;
	  private String supplyh_cell_addr;
	  private String prod_desc = "";

      BigDecimal price;
	
 protected void prepare() {
     record_id = getRecord_ID();
     AD_Client_ID =Env.getAD_Client_ID(getCtx());
     AD_Org_ID = Env.getAD_Org_ID(getCtx());
	    
  } // prepare
 
 protected String doIt() throws Exception {
 
	 /*
	 String upd_gtotal = " select sum(totalline) from c_quotationline where c_quotation_id in " +
	 "(select distinct c_quotation_id from c_quotationline" +
	 " where c_quotationline_id = ? )";
	 BigDecimal line_total = DB.getSQLValueBD(get_TrxName(), upd_gtotal, record_id);
	 BigDecimal RndGrAmt = line_total.setScale(0,BigDecimal.ROUND_HALF_UP);
	 		
	 String sql_hdr = " update c_quotation set grandtotal =  " + RndGrAmt + 
	 ",netamt = " + RndGrAmt + " where c_quotation_id in (select distinct c_quotation_id from c_quotationline" +
	 " where c_quotationline_id = " + record_id + ")";
	 int upd_grandtotal = DB.executeUpdate(sql_hdr, get_TrxName());
	 */
	 
	// For A,B & SupplyH
	ExecuteProcess(record_id);
	
	//Price Factor From Product Window
	String sql_pf = "select mp.PRICEFACTOR from m_product mp inner join c_quotationline cql on " +
			"cql.m_product_id=mp.m_product_id where c_quotationline_id = ? ";
    BigDecimal pricefactor = DB.getSQLValueBD(get_TrxName(), sql_pf, record_id);
	
    if (pricefactor.doubleValue()!=0)
  	  price = new BigDecimal(prod_cost).multiply(pricefactor);
    else
    {
  	  //If No Price Factor in Product Window
  	  	  String sql_1 = "select MARKUP_PCNT from c_acctschema where ad_client_id = ? ";
    	  BigDecimal markup_cost = DB.getSQLValueBD(get_TrxName(), sql_1, AD_Client_ID);  	  
    	  price = new BigDecimal(prod_cost).multiply(markup_cost);
    	  //
    }	
	
	price = price.setScale(0,BigDecimal.ROUND_CEILING);  	
	//System.out.println(" Price in Quotation Line (incl. Price Factor):" + price);
	
	String sql_disc = " select DISC_PCNT from c_quotationline where c_quotationline_id = ? ";
	BigDecimal disc_pcnt = DB.getSQLValueBD(null, sql_disc,record_id);
	//System.out.println(" pcnt : " + disc_pcnt);
	disc_pcnt = (new BigDecimal(100).subtract(disc_pcnt)).divide(new BigDecimal(100));
	BigDecimal netprice = price.multiply(disc_pcnt);
	
	netprice = netprice.setScale(0,BigDecimal.ROUND_HALF_UP);
	
	BigDecimal Est_Cost = new BigDecimal(prod_cost);
	String sql_upd = " update c_quotationline set price = " + price + 
	  " ,PRODDESC = '" +v_description  +
	  "',netprice = " + netprice +
	  " ,totalline = " + netprice + " * QTY " + 
	  ",EstCost = " + Est_Cost.setScale(2,BigDecimal.ROUND_HALF_UP) + ",GETPRICE='Y' where c_quotationline_id = " + record_id;
	int no = DB.executeUpdate(sql_upd, get_TrxName());  

	String sql_gt = "select sum(totalline) from c_quotationline where c_quotation_id  in " +
  	" (select distinct c_quotation_id from c_quotationline " +
  	" where c_quotationline_id = ? )";
  	BigDecimal totline_amt = DB.getSQLValueBD(get_TrxName(), sql_gt, record_id);
  	BigDecimal RndAmt = totline_amt.setScale(2,BigDecimal.ROUND_UP);
  	
  	//Line Grand Total
	 String upd_grtot = " select nvl(sum(price*qty),0) from c_quotationline where c_quotation_id in " +
  	" (select distinct c_quotation_id from c_quotationline " +
  	" where c_quotationline_id = ? )";
	 BigDecimal line_grtot = DB.getSQLValueBD(get_TrxName(), upd_grtot, record_id);
	 //End og Line Grand Total
	 
	//Tax Amt
	 String upd_taxamt1 = " SELECT nvl(sum(case rate when 0 then 0 else round(totalline*(rate/100),2) end),0) "
       + "FROM c_quotationline INNER JOIN c_tax ON c_tax.c_tax_id=c_quotationline.c_tax_id"
       + " WHERE c_quotationline.c_quotation_id IN " +
  	" (select distinct c_quotation_id from c_quotationline " +
  	" where c_quotationline_id = ? )";
	 BigDecimal taxamt1 = DB.getSQLValueBD(get_TrxName(), upd_taxamt1, record_id);
	 //End of Tax Amt
	 Double netAmt = RndAmt.doubleValue()+taxamt1.doubleValue();
	 //BigDecimal netbd=new BigDecimal(netAmt);
	 //netbd = netbd.setScale(2, BigDecimal.ROUND_UP);
  	
  	String sqlQtnhdr = " update c_quotation set grandtotal =  " + RndAmt + ",taxamt=" + taxamt1 + 
  	      " ,netamt = " + netAmt + ",LineGrAmt= " + line_grtot + " where " +
  	      " c_quotation_id in (select distinct c_quotation_id from c_quotationline" +
  	      " where c_quotationline_id = " + record_id + ")";
  	int grand_total = DB.executeUpdate(sqlQtnhdr, get_TrxName());     	
  	
  	String sql_prod = " update m_product set lock_status = 'N' where m_product_id = "+M_Product_ID
  	 				+ " and ad_client_id = " + AD_Client_ID;
    int lock_status = DB.executeUpdate(sql_prod, get_TrxName());

    return "";
	
} // doIt
	    
void ExecuteProcess(int record_id)
	{
	  int r1 = 0,c1 = 0,r2 = 0,c2 =0 ,r3 = 0,c3 = 0, r4 = 0, c4 = 0, r5 = 0, c5 =0;
	  int r = 0,c = 0;
	  String unit, b_unit, lenYN, awYN, len_addr, aw_addr, aw,odType,odSize,odPL,odVCD,odSizeStr,odTypeStr = null,odApp=null,
	  screw= null,odSizeName=null;  
	  BigDecimal odSizeID,odTypeID;
	
	  String sql1 = "SELECT cql.m_product_id,mp.EXCEL_FILE_NAME," +
	  		" mp.FGCOST_CELL_ADDR,mp.LOCK_STATUS,mp.VALUE," +
	  		" nvl(mp.a_cell_addr,0),nvl(mp.b_cell_addr,0),nvl(mp.supplyh_cell_addr,0) ," +
	  		" trim(cql.unit) as unit ,nvl(Beta_OD_Size_ID,0),nvl(Beta_OD_Type_ID,0),nvl(ODPL,'N') as ODPL,originalname " +
	  		",nvl(cql.SETLENGTH,'N'),nvl(cql.SETAIRWAY,'N'),nvl(trim(mp.length_cell_addr),' '),nvl(trim(mp.airway_cell_addr),' ')" +
	  		",nvl(ODVCD,'N') as ODVCD,nvl(ODAPPLICATION,'N') as ODAPPLICATION,nvl(SCREWNOS,'N') as SCREW  from c_quotationline cql" +
	  		" INNER JOIN m_product mp ON cql.m_product_id = mp.m_product_id" +
	  		" where cql.c_quotationline_id = ? " +
	  		" and (mp.a_cell_addr is not null or " +
	  		"      mp.b_cell_addr is not null or" +
	  		"      mp.supplyh_cell_addr is not null ) ";
	    				
	 PreparedStatement pstmt1 = null;
	 ResultSet rs1 = null;
	 try 
	 {
	  pstmt1 = DB.prepareStatement(sql1, "DSPL");
	  pstmt1.setInt(1, record_id);
	  rs1 = pstmt1.executeQuery();
	
	  if(rs1.next()) 
	  {	
		//System.out.println("Inside ExecuteProcess");
		M_Product_ID = rs1.getInt(1);
		Path_of_XLFile = rs1.getString(2);
		FGCost_Cell_Addr = rs1.getString(3);
		Lock_Status = rs1.getString(4);
		Prod_Value = rs1.getString(5);
		a_cell_addr = rs1.getString(6);
		b_cell_addr = rs1.getString(7);
		supplyh_cell_addr = rs1.getString(8);
		unit = rs1.getString(9);
		odSizeID = rs1.getBigDecimal(10);
		odTypeID = rs1.getBigDecimal(11);
		odPL = rs1.getString(12);
	  	v_description = rs1.getString(13);
	  	
	  	lenYN = rs1.getString(14);
	  	awYN = rs1.getString(15);
	  	len_addr = rs1.getString(16);
	  	aw_addr = rs1.getString(17);
	  	odVCD = rs1.getString(18);
	  	odApp = rs1.getString("ODAPPLICATION");
	  	screw = rs1.getString("SCREW");
	  	
	  	
	  		
	  	if (Lock_Status.equalsIgnoreCase("N"))
		{
		 String sql_prod = " update m_product set lock_status = 'Y' where m_product_id = "+M_Product_ID
		 				 + " and ad_client_id = " + AD_Client_ID;
		 int lock_status = DB.executeUpdate(sql_prod, get_TrxName());
		}
	  	else
	  	{
	  	 Thread.sleep(5000);
		 addLog("Excel file of Product ' " +Prod_Value+
		    	    					" ' being accessed by another user. Waiting for him to close'");
		 }
		 Path_of_XLFile = ""+Path_of_XLFile + "";
		 
		 InputStream input = new FileInputStream(Path_of_XLFile);
		 fs = new POIFSFileSystem( input );
	   	 wb = new HSSFWorkbook(fs);
		 sheet = wb.getSheetAt(1);
	   	 rows = sheet.rowIterator(); 
	   	 row = (HSSFRow) rows.next();
	   	    
	   	 evaluator = wb.getCreationHelper().createFormulaEvaluator();
		 
	   	 /** TO Get Cell address for input parameter from Product Master */
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
			     
		  /** B cell Address */
		
		 String sql_row_col2 = "select fn_getcelladdres(?) from dual ";
		 pstmt2 = null;
		 rs2 = null;
	   	 try // fn to get cell address
	   	 {
	  	  pstmt2 = DB.prepareStatement(sql_row_col2, null);
	   	  pstmt2.setString(1,b_cell_addr);
	   	  rs2 = pstmt2.executeQuery();
	   		
	   	  if (rs2.next())
	   	  {
	   		String celladdr = rs2.getString(1);
	   		int length = celladdr.length();
	  		
	   		String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 		r2 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
		    		    	    			
		    String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
		    c2 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
		    
	   	  } //end of if
		  rs2.close();
	   	  pstmt2.close();
		  }catch(Exception e){log.log(Level.SEVERE, sql_row_col2, e); } // end of fn_getcelladdr
		   finally { DB.close(rs2, pstmt2);  }
		   
		
		  /** Supply/H Cell Addr */
			  
		  String sql_row_col3 = "select fn_getcelladdres(?) from dual ";
		  pstmt2 = null;
		  rs2 = null;
		  try // fn to get cell address
		  {
		   pstmt2 = DB.prepareStatement(sql_row_col3, null);
		   pstmt2.setString(1,supplyh_cell_addr);
		   rs2 = pstmt2.executeQuery();
		      	
		   if (rs2.next())
		   {
		    String celladdr = rs2.getString(1);
		    int length = celladdr.length();
		  		
		   	String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
		 	r3 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
			    		    	    			
			String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
			c3 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
			    		    	    			    		    	    			
			} //end of if
			
		   rs2.close();
		   pstmt2.close();
		  }catch(Exception e){log.log(Level.SEVERE, sql_row_col3, e); } // end of fn_getcelladdr
	       finally {DB.close(rs2, pstmt2); }   
		
	       //Get Cell Addr for Length
	       if (lenYN.equalsIgnoreCase("Y"))
	       {
	    	   String sql_row_col4 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col4, null);
	 			   pstmt2.setString(1,len_addr);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r4 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c4 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col4, e); } // end of fn_getcelladdr
	 	       finally {DB.close(rs2, pstmt2); }
	       }
	       
	     //Get Cell Addr for Airway
	       if (awYN.equalsIgnoreCase("Y"))
	       {
	    	   String sql_row_col5 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col5, null);
	 			   pstmt2.setString(1,aw_addr);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r5 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c5 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col5, e); } // end of fn_getcelladdr
	 	       finally {DB.close(rs2, pstmt2); }
	       }
	       
	       //Set Input Values into Excel
	       if (r1 != 0 && c1 != 0) 
		   {
				HSSFRow row1 = sheet.getRow(r1); //Row
			    HSSFCell attr_cost1 = row1.getCell(c1); //Column
			    String odSizeSql = "Select value from beta_od_size where beta_od_size_id = ?";
			    odSizeStr = DB.getSQLValueString(get_TrxName(), odSizeSql, odSizeID);
			    //System.out.println(" OD Size Value :" + odSizeStr);
			    String odSizeNameSql =  "Select name from beta_od_size where beta_od_size_id = ?";
			    odSizeName = DB.getSQLValueString(get_TrxName(), odSizeNameSql, odSizeID);
			    attr_cost1.setCellValue(odSizeStr);
			    //v_description = v_description + " " + odSizeName + "";
		   }
	       
	       if (r2 != 0 && c2 != 0)
		   {
				HSSFRow row11 = sheet.getRow(r2); //Row
			    HSSFCell attr_cost2 = row11.getCell(c2); //Column
			    String odTypeSql = "Select value from beta_od_type where beta_od_type_id = ?";
			    odTypeStr = DB.getSQLValueString(get_TrxName(), odTypeSql, odTypeID);
			    //System.out.println(" OD Type value :" + odTypeStr);
			    attr_cost2.setCellValue(odTypeStr);
			    //v_description = v_description + "/" + odTypeStr + "";
			}
	       
	       if (r3 != 0 && c3 != 0)
		   {
	    	   HSSFRow row12 = sheet.getRow(r3); //Row
			   HSSFCell attr_cost3 = row12.getCell(c3); //Column
			   //System.out.println(" OD Application value :" + odApp);
			   attr_cost3.setCellValue(odApp);
		    }
	       
			if (r4 !=0 && c4 !=0 ) 
			{
				HSSFRow row14 = sheet.getRow(r4); //Row
			    HSSFCell attr_cost4 = row14.getCell(c4); //Column
			    //System.out.println(" OD PL value :" + odPL);
			    attr_cost4.setCellValue(odPL);
			    //if (odPL.equalsIgnoreCase("Y"))
			    	//v_description = v_description + "/PLENUM";
			}
     	    
			if (r5 !=0 && c5 !=0 ) 
			{
				HSSFRow row15 = sheet.getRow(r5); //Row
			    HSSFCell attr_cost5 = row15.getCell(c5); //Column
			    //System.out.println(" OD VCD value :" + odVCD);
			    attr_cost5.setCellValue(odVCD);
			    //if (odVCD.equalsIgnoreCase("Y"))
			    	//v_description = v_description + "/VCD";
			}
			
			//Check Neck size for Display
			String odNeckSql = "Select odneck from beta_od_size where beta_od_size_id = ?";
		    String odNeckStr = DB.getSQLValueString(get_TrxName(), odNeckSql, odSizeID);
				//v_description = v_description + " NECK:" + odNeckStr;

		    //Make the Prod.description**********************************************
		    if (Prod_Value.equalsIgnoreCase("SW3") && odTypeStr.equalsIgnoreCase("RR"))
		    	v_description = v_description + "-R" + ""; 
		    else
		    	v_description = v_description + "-"	+ odTypeStr + ""; 
			if (odTypeStr.equalsIgnoreCase("S") | odTypeStr.equalsIgnoreCase("SR") | odTypeStr.equalsIgnoreCase("SS"))
			{
				if (screw.equalsIgnoreCase("1") | screw.equalsIgnoreCase("4"))
					v_description = v_description + screw + "";
			}	
			if (Prod_Value.equalsIgnoreCase("SW1"))
			{
				if (odVCD.equalsIgnoreCase("Y") | odPL.equalsIgnoreCase("Y"))
					v_description = v_description + "-";
			}
			else 
			{
				if (odVCD.equalsIgnoreCase("Y") | odPL.equalsIgnoreCase("Y") | odApp.equalsIgnoreCase("S"))
					v_description = v_description + "-";
				if (odApp.equalsIgnoreCase("S"))
					v_description = v_description + "D";
			}
			if (odPL.equalsIgnoreCase("Y"))
				v_description = v_description + "P";
			if (odVCD.equalsIgnoreCase("Y"))
				v_description = v_description + "V";
			
			if (Prod_Value.equalsIgnoreCase("SW1"))
				v_description = v_description + " " + odSizeName + " SLOT:" + odNeckStr;
			else 
				v_description = v_description + " " + odSizeName + "";
			//**********************************************************************
				
			FileOutputStream fileOut = new FileOutputStream(Path_of_XLFile);
		  	wb.write(fileOut);
		  	fileOut.flush();
			    	 
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
		 	System.out.println(" FG CELL value :" + prod_cost);
	  }
	 }catch(Exception e) {  }
	  	 
 }
}
