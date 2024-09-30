package org.compiere.process;


import java.io.File;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

public class Process_DB_TO_XLS_GD extends SvrProcess{
	
	  //private int r = 0;
	  //private int c = 0;
	  private int fr_r = 0;
	  private int to_r = 0;
	  private int fr_c = 0;
	  private int to_c = 0;
	  
	  //For Execute method
	  private int r1 = 0,c1 = 0,r2 = 0,c2 =0 ,r3 = 0,c3 = 0, r4 = 0, c4 = 0, r5 = 0, c5 =0,r6=0,c6=0,r7=0,c7=0,r8=0,c8=0,r9=0,c9=0,r10=0,c10=0,r11=0,c11=0,
			  r12=0,c12=0,r13=0,c13=0,r14=0,c14=0,r15=0,c15=0,r16=0,c16=0,r17=0,c17=0,r18=0,c18=0,r19=0,c19=0,r20=0,c20=0,r21=0,c21=0;
			  int r = 0,c = 0,r22=0,c22=0,r23=0,c23=0,r25=0,c25=0,r26=0,r27=0,c26=0,c27=0,r28=0,c28=0,r29=0,c29=0,
			  r30=0,c30=0;
			  
	  private BigDecimal b_disp,a_disp,s_disp,len_disp,gnAVal_disp,gnBVal_disp,cjnpPole_disp,
      ct_size2_disp,ct_size3_disp;
	  
	  private String ul_act=null,ul_temp=null,ul_power=null,ul_spigot=null,ul_classtype=null,ulact_name=null,ActCode=null,
	  ul_linktype=null,ul_stad=null,vcd_gasket=null,fd_slv=null,vcd_phen=null,ct_riser=null,ct_degree=null,
	  ct_riser_desc=null,ct_degree_desc=null,str_type=null,str_pctype=null,str_ais=null,str_fins=null,
	  str_metal=null,str_model=null,str_reg=null,dh_phase=null,dh_pl=null,dh_metal=null,dh_stage=null,
	  ca_fins_bvn=null,ca_model_bvn=null,p4w_tbaryn=null,vcd_excel=null,vcd_shaft=null,vcd_jamseal=null,vcd_nonstd=null,
	  vcd_blade=null,ul_noact_yn=null,agvcd_blade=null,ct_pctype=null,pgl_flange=null,
	  cowl_base_yn=null,fdc_slv_yn=null,fdc_ind_yn=null,gasket_yn=null,gdtype=null,fb_slotn=null,
	  fb_slotw=null,fdc_rating=null,nrd_fra_thick=null,pgl_psheet=null,vcd_rivets=null,fancfw_blade=null,fancfw_kw=null,
	  fancfw_pole=null,bvn_nonstd=null,bvn_gland=null,bvn_ip66=null,bvn_flanges=null,bvn_insscr_type=null,
	  bvn_ains=null,mprd_bush=null,mprd_frame=null,mprd_jamb=null,fb_flange=null,eal_filteryn=null,datarack_model=null,
	  datarack_frame=null,ul_intextact=null,fdc_rn_yn=null,evdr_yn=null,mfeet_pc=null,mfeet_brand=null,
	  rad_12mm_yn=null,jb_frame=null,jb_logo_yn=null,jb_kout_yn=null;
	  
	  private double fdc_rn=0; 
	  
	  //End of Execute method Var
	      
	  private int M_Product_ID;
	  private String Path_of_XLFile;
	  private String FGCost_Cell_Addr;
	  private String Lock_Status;
	  private String Prod_Value;
	  private String noparam; 
	  private String v_description;
	  private String mpname;
	  private int M_Attribute_ID;
//	  private double attr_value;
	  private String attr_value;
	  private String Attr_Cell_Addr;
	  private String Attr_Name;
	  private int AD_Client_ID;
	  private String bom_cell_addr; 
	  
//	  private int m_productbom_id;
	  private int AD_Org_ID;
	  private String qty_cell_addr;
	  private String costfact_cell_addr;
	  private String stdcost_cell_addr;
	  private String unitcost_cell_addr;
	  private String fr_rmprod_cell_addr;
	  private String to_rmprod_cell_addr;
	  
	  private String fr_rmQty_cell_addr;
	  private String to_rmQty_cell_addr;
	  
	  private String fr_rmCF_cell_addr;
	  private String to_rmCF_cell_addr;
	  
	  private String fr_rmStdCost_cell_addr;
	  private String to_rmStdCost_cell_addr;
	  
	  private String fr_rmUnitCost_cell_addr;
	  private String to_rmUnitCost_cell_addr;
	  
	  private String fr_rmCost_cell_addr;
	  private String to_rmCost_cell_addr;
	  /// Modified by Abdulla on 06-02-2013 Begin
	  private double m_RawMat_cost;
	  private int record_id;
	  /// Modified by Abdulla on 06-02-2013 Endi
	  
	  private int XX_Quotationline_RMCost_ID;
	  POIFSFileSystem fs ;
      HSSFWorkbook wb;
      HSSFSheet sheet ;
      Iterator rows; 
      HSSFRow row,rowLT,rowAct;
      FormulaEvaluator evaluator ;
      private double prod_cost;
      private String v_quotline_desc = "";
	  private String a_cell_addr;
	  private String b_cell_addr;
	  private String supplyh_cell_addr;
	  private String prod_desc = "";
      private String unit_symbol;
      private String strLT="";
      BigDecimal price;
      private int quotID; 
      private String qtn_prd=""; //use for updating into column qtnprd on c_qline table which will be used for product desc.
      
      private BigDecimal pricefactor;
      
      public double ul_act_totcost = 0,therm_totcost=0,trans_totcost=0,heat_totcost=0,elect_cost=0;
      public int ul_act_id=0,ul_act_qty=0;  //For BMFD-NEW,BMFSD-NEW & BMSD-NEW
	
 protected void prepare() {
   record_id = getRecord_ID();
   AD_Client_ID =Env.getAD_Client_ID(getCtx());
   AD_Org_ID = Env.getAD_Org_ID(getCtx());
   
   String sqlqid ="SELECT c_quotation_id FROM c_quotationline WHERE c_quotationline_id = ?";
   quotID = DB.getSQLValue(get_TrxName(), sqlqid, record_id);
	    
  } // prepare
	   	    
  protected String doIt() throws Exception{
	  
	   //System.out.println(" Inside doIt meth");
	   
	   //System.out.println("Record ID : " + record_id);
	   
	   
	  
	   String sql_cnt = " select count(1) from XX_QUOTATIONLINE_RMCOST where c_quotationline_id = ?" ;
	   //String sql_cnt = " select count(1) from C_QUOTATIONLINE where GETPRICE='Y' AND c_quotationline_id = ? " ;
	   int count = DB.getSQLValue(get_TrxName(), sql_cnt, record_id);
	   
	   if (count > 0)
	   {
   	     String sql_delline = " delete from xx_quotationline_rmcost where c_quotationline_id = " + record_id ;
		 int del_rmcostline = DB.executeUpdate(sql_delline, get_TrxName()); 
		
	 
//Beta Code		 
		 String upd_gtotal = " select sum(totalline) from c_quotationline where c_quotation_id = ?";
		 BigDecimal line_total = DB.getSQLValueBD(get_TrxName(), upd_gtotal, quotID);
		 BigDecimal RndGrAmt = line_total.setScale(2,BigDecimal.ROUND_UP);
		 	
		 //Line Grand Total
		 String upd_grtot = " select nvl(sum(price*qty),0) from c_quotationline where c_quotation_id = ?";
		 BigDecimal line_grtot = DB.getSQLValueBD(get_TrxName(), upd_grtot, quotID);
		 //End og Line Grand Total
		 
		//Tax Amt
		 String upd_taxamt1 = " SELECT nvl(sum(case ct.rate when 0 then 0 else round(cql.totalline*(ct.rate/100),2) end),0) "
            + "FROM c_quotationline cql INNER JOIN c_tax ct ON ct.c_tax_id=cql.c_tax_id"
            + " WHERE cql.c_quotation_id= ?";
		 BigDecimal taxamt1 = DB.getSQLValueBD(get_TrxName(), upd_taxamt1, quotID);
		 //End of Tax Amt
		 
		 Double netAmt = RndGrAmt.doubleValue()+taxamt1.doubleValue();
		 //BigDecimal netbd=new BigDecimal(netAmt);
		 //netbd = netbd.setScale(2, BigDecimal.ROUND_UP);
		 
		 String sql_hdr1 = " update c_quotation set grandtotal =  " + RndGrAmt + ",taxamt=" + taxamt1 + 
		         ",netamt = " + netAmt + ",LineGrAmt= " + line_grtot + " where c_quotation_id = " + quotID + "";
		  int upd_grandtotal = DB.executeUpdate(sql_hdr1, get_TrxName());

//Beta Code End
		  
		 }
	   
	 // For A,B & SupplyH
	ExecuteProcess(record_id);
	           				
	 String sql1 = "select cql.m_product_id,mp.value,mp.originalname,mp.pricefactor" +
	  " from c_quotationline cql " +
	  " inner join m_product mp on cql.m_product_id = mp.m_product_id " +
	  " where cql.c_quotationline_id = ? ";
	    				
	PreparedStatement pstmt1 = null;
	ResultSet rs1 = null;
	    try 
	    {
	     pstmt1 = DB.prepareStatement(sql1, "DSPL");
	     pstmt1.setInt(1, record_id);
	     rs1 = pstmt1.executeQuery();
	   
	      while (rs1.next()) // While loop for quot Starts
	      {	
	    	  
	    	  M_Product_ID = rs1.getInt(1);
	    	  Prod_Value = rs1.getString(2);
	    	  //v_description = rs1.getString(3);
	    	  pricefactor = rs1.getBigDecimal(4);
	    	  	    	 		  		  
	       }// QuotationLine while loop ends
	      
	      //For making seperate price factor for Actuator & Other Raw materials for UL 
		   if (Prod_Value.equalsIgnoreCase("BMSD") | Prod_Value.equalsIgnoreCase("BMFSD-T") |
				   Prod_Value.equalsIgnoreCase("BEMFSD-T") | Prod_Value.equalsIgnoreCase("BMSD/R") |
				   Prod_Value.equalsIgnoreCase("BMFSD/R-T") | Prod_Value.equalsIgnoreCase("BEMFSD/R-T") |
				   Prod_Value.equalsIgnoreCase("BMFD-T") | Prod_Value.equalsIgnoreCase("BEMFD-T") |
				   Prod_Value.equalsIgnoreCase("BMFD/R-T") | Prod_Value.equalsIgnoreCase("BEMFD/R-T") |
				   Prod_Value.equalsIgnoreCase("BMFD-F") | Prod_Value.equalsIgnoreCase("BEMFD-F") |
				   Prod_Value.equalsIgnoreCase("BMFD/R-F") | Prod_Value.equalsIgnoreCase("BEMFD/R-F") |
				   Prod_Value.equalsIgnoreCase("BMFD-TF") | Prod_Value.equalsIgnoreCase("BEMFD-TF") |
				   Prod_Value.equalsIgnoreCase("BMFD/R-TF") | Prod_Value.equalsIgnoreCase("BEMFD/R-TF") |
				   Prod_Value.equalsIgnoreCase("BMFSD-NEW") | Prod_Value.equalsIgnoreCase("BEMFSD-NEW") |
				   Prod_Value.equalsIgnoreCase("BMSD-NEW") | Prod_Value.equalsIgnoreCase("BEMFD-NEW") |
				   Prod_Value.equalsIgnoreCase("BMFD-NEW") | Prod_Value.equalsIgnoreCase("VDR") | 
				   Prod_Value.equalsIgnoreCase("PI3VCD") | Prod_Value.equalsIgnoreCase("PIAVCD"))
		   {
			   elect_cost=ul_act_totcost+therm_totcost+trans_totcost+heat_totcost;
			   double cost2 = prod_cost-elect_cost;
			   //System.out.println("Cost Elect.: " + elect_cost);
			   //System.out.println("Cost 2: " + cost2);
			   
			   String sql_act_markup = "select ACT_MARKUP_PCNT from c_acctschema where ad_client_id = ? ";
		       BigDecimal act_markup_cost = DB.getSQLValueBD(get_TrxName(), sql_act_markup, AD_Client_ID); 
			   BigDecimal price1 = new BigDecimal(elect_cost).multiply(act_markup_cost);
			   BigDecimal price2 = new BigDecimal(cost2).multiply(pricefactor);
			   price = price1.add(price2);
			   //System.out.println("Price : " + price);
			   
		   }
		   //Added seperate Elect.fact for motorized VCD models (Dtd: 16.11.2022)
		   else if (Prod_Value.equalsIgnoreCase("3VCDB") | Prod_Value.equalsIgnoreCase("3VCDF") |  
				   Prod_Value.equalsIgnoreCase("3VCDS") | Prod_Value.equalsIgnoreCase("3VCDH") |
				   Prod_Value.equalsIgnoreCase("AUVCDF") | Prod_Value.equalsIgnoreCase("AUVCDB") |
				   Prod_Value.equalsIgnoreCase("AUVCDH") | Prod_Value.equalsIgnoreCase("AUVCDS") |
				   Prod_Value.equalsIgnoreCase("AGVCDF") | Prod_Value.equalsIgnoreCase("AGVCDB") | Prod_Value.equalsIgnoreCase("AGVCDS") |
				   Prod_Value.equalsIgnoreCase("LVCDF") | Prod_Value.equalsIgnoreCase("LVCDH"))
		   {
			    elect_cost=ul_act_totcost+therm_totcost+trans_totcost+heat_totcost;
			   	double costRM = prod_cost-elect_cost;
			   
			    String sql_vcd_markup = "select VCDELE_MARKUP_PCNT from c_acctschema where ad_client_id = ? ";
		        BigDecimal vcd_markup_cost = DB.getSQLValueBD(get_TrxName(), sql_vcd_markup, AD_Client_ID); 
			    BigDecimal price1 = new BigDecimal(elect_cost).multiply(vcd_markup_cost);
			    BigDecimal price2 = new BigDecimal(costRM).multiply(pricefactor);
			    price = price1.add(price2);	
		   }
		   else if (Prod_Value.equalsIgnoreCase("CTC100") | Prod_Value.equalsIgnoreCase("CTC125") |
				   Prod_Value.equalsIgnoreCase("CTC160") | Prod_Value.equalsIgnoreCase("CTC200") |
				   Prod_Value.equalsIgnoreCase("CTC250") | Prod_Value.equalsIgnoreCase("CTC315") |
				   Prod_Value.equalsIgnoreCase("CTC400") | Prod_Value.equalsIgnoreCase("CTC500") | 
				   Prod_Value.equalsIgnoreCase("CTC630") | Prod_Value.equalsIgnoreCase("CTCDS100") |
				   Prod_Value.equalsIgnoreCase("CTCDS125") | Prod_Value.equalsIgnoreCase("CTCDS160") |
				   Prod_Value.equalsIgnoreCase("CTCDS200") | Prod_Value.equalsIgnoreCase("CTCDS250") |
				   Prod_Value.equalsIgnoreCase("CTCDS315") | Prod_Value.equalsIgnoreCase("CTCDS400") |
				   Prod_Value.equalsIgnoreCase("CTCDS500") | Prod_Value.equalsIgnoreCase("CTCDS630") |
				   Prod_Value.equalsIgnoreCase("EBP1") | Prod_Value.equalsIgnoreCase("EBP2") | 
				   Prod_Value.equalsIgnoreCase("EBP3") | Prod_Value.equalsIgnoreCase("EBP4") |
				   Prod_Value.equalsIgnoreCase("EBP5") | Prod_Value.equalsIgnoreCase("EBP6") |
				   Prod_Value.equalsIgnoreCase("EBP7") | Prod_Value.equalsIgnoreCase("EBP8") | 
				   Prod_Value.equalsIgnoreCase("RM") | Prod_Value.equalsIgnoreCase("RMDS") |
				   Prod_Value.equalsIgnoreCase("RS") | Prod_Value.equalsIgnoreCase("RSDS") |
				   Prod_Value.equalsIgnoreCase("SDV100") | Prod_Value.equalsIgnoreCase("SDV150") | 
				   Prod_Value.equalsIgnoreCase("SDV200") | Prod_Value.equalsIgnoreCase("SDV250") |
				   Prod_Value.equalsIgnoreCase("SDV300") | Prod_Value.equalsIgnoreCase("SDV350") |
				   Prod_Value.equalsIgnoreCase("SDV400") | Prod_Value.equalsIgnoreCase("SDVBP") | 
				   Prod_Value.equalsIgnoreCase("SDVBPE") | Prod_Value.equalsIgnoreCase("SDVE100") |
				   Prod_Value.equalsIgnoreCase("SDVE150") | Prod_Value.equalsIgnoreCase("SDVE200") |
				   Prod_Value.equalsIgnoreCase("SDVE250") | Prod_Value.equalsIgnoreCase("SDVE300") |
				   Prod_Value.equalsIgnoreCase("SDVE350") | Prod_Value.equalsIgnoreCase("SDVE400") |
				   Prod_Value.equalsIgnoreCase("SDV") | Prod_Value.equalsIgnoreCase("SDVE") |
				   Prod_Value.equalsIgnoreCase("CTC") | Prod_Value.equalsIgnoreCase("CTCDS"))
		   {    //Added VAV Elect.parts separate factor (17.02.22)
			   	elect_cost=ul_act_totcost+therm_totcost+trans_totcost+heat_totcost;
			   	double costRM = prod_cost-elect_cost;
			   
			    String sql_vav_markup = "select VAVELE_MARKUP_PCNT from c_acctschema where ad_client_id = ? ";
		        BigDecimal vav_markup_cost = DB.getSQLValueBD(get_TrxName(), sql_vav_markup, AD_Client_ID); 
			    BigDecimal price1 = new BigDecimal(elect_cost).multiply(vav_markup_cost);
			    BigDecimal price2 = new BigDecimal(costRM).multiply(pricefactor);
			    price = price1.add(price2);
		   }
		   
		   else
		   {
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
		   }
		   // End For making seperate price factor for Actuator & Other Raw materials for UL 
		   
		   price = price.setScale(0,BigDecimal.ROUND_CEILING);
		   	
		 //System.out.println(" Price in Quotation Line (incl. Price Factor):" + price);
		 //String ProdDesc =  		     
		 
		/* String sql_upd = " update c_quotationline set price = " + price + 
		  		  " ,PRODDESC = '" + prod_desc.trim() + v_quotline_desc.trim() +
		  		  "',totalline = " + price + " * QTY where m_product_id = " + M_Product_ID + 
		  		   " and c_quotationline_id = " + record_id;
//		  		     
		  int no = DB.executeUpdate(sql_upd, get_TrxName());  */ //Commented on 02/11/2020
		  			  		    
		  
		  //Update Act ID & Act Qty for BMFD-NEW,BMFSD-NEW & BMSD-NEW into Qline Table
	  	  if (Prod_Value.equalsIgnoreCase("BMFSD-NEW") | Prod_Value.equalsIgnoreCase("BEMFSD-NEW") |
			  Prod_Value.equalsIgnoreCase("BMSD-NEW") | Prod_Value.equalsIgnoreCase("BEMFD-NEW") |
			  Prod_Value.equalsIgnoreCase("BMFD-NEW"))
	  	  {
	  		  if (ul_act_id!=0)
	  		  {
	  			  String sql_updul = " update c_quotationline set beta_qtn_actuator_id = " + ul_act_id + 
	  	  		  " ,actqty = " + ul_act_qty + " where c_quotationline_id = " + record_id;
	  			  int noul = DB.executeUpdate(sql_updul, get_TrxName());
	  			  
	  		  }
	  		  else
	  		  {
		  			String sql_updul = " update c_quotationline set beta_qtn_actuator_id =null " + 
					  		  " ,actqty =0  where c_quotationline_id = " + record_id;				  		     
					int noul = DB.executeUpdate(sql_updul, get_TrxName()); 
					if (Prod_Value.equalsIgnoreCase("BMSD-NEW") && ul_act_qty<=0 && ul_noact_yn.equalsIgnoreCase("N"))
		  			{
						String sql_upd_prod_B = " update m_product set lock_status = 'N' where m_product_id = "+M_Product_ID
  	  					  		+ " and ad_client_id = " + AD_Client_ID;
						int lock_status_B = DB.executeUpdate(sql_upd_prod_B, get_TrxName());
						addLog("The given size is not as per U/L range,Please give the size as per the specifications given by production dept.");
						return "";
		  			}
	  		  }
	  		  //Update Product description from beta_qtn_prd table
	  		  String sql_updprd="Update c_quotationline set (prdcode,prdname)=(select code,name from beta_qtn_prd " +
	  		  " where code='" + qtn_prd + "') WHERE c_quotationline_id = " + record_id;	
	  		  int noulprd = DB.executeUpdate(sql_updprd, get_TrxName());		  
	  		 
	  	  }	  
		  //End of Update Act ID & Act Qty for BMFD-NEW,BMFSD-NEW & BMSD-NEW into Qline Table
	  	  
	  	  //Update Prd.Code & Description from beta_qtn_prd table (16.09.2020)
	  	  if (Prod_Value.equalsIgnoreCase("STERILAIRE"))
	  	  {
	  		  String sql_strprd="Update c_quotationline set (prdcode,prdname)=(select code,name from beta_qtn_prd " +
	  	  	  " where code='" + qtn_prd + "') WHERE c_quotationline_id = " + record_id;	
	  		  int noulstr = DB.executeUpdate(sql_strprd, get_TrxName());
	  	  }
	  	  //End of Update Prd.Code & Description from beta_qtn_prd table
		  	
		  rs1.close();
		   pstmt1.close();
		 }catch(Exception e){String s=e.getLocalizedMessage();
         System.out.println("Error : " + s);} //for Quotationline 
	          finally  {try {DB.close(rs1, pstmt1);} catch (Exception e) {}}
		    	
		   // Code for BOM Starts 
 		   String sql_3 = "select mpb.FROM_RMPROD_CELL_ADDR,mpb.TO_RMPROD_CELL_ADDR,mpb.QTY_CELL_ADDR,mpb.TO_QTY_CELL_ADDR," +
 		  		" mpb.COSTFACTOR_CELL_ADDR,mpb.TO_COSTFACTOR_CELL_ADDR,mpb.STDCOST_CELL_ADDR,mpb.TO_STDCOST_CELL_ADDR," +
 		  		" mpb.UNITCOST_CELL_ADDR,mpb.TO_UNITCOST_CELL_ADDR,mpb.RMCOST_CELL_ADDR,mpb.TO_RMCOST_CELL_ADDR" +
 		  		" from m_product_bom mpb inner join c_quotationline cql " +
 		  		" on mpb.m_product_id = cql.m_product_id where mpb.m_product_id = ?" +
 		  		" and cql.c_quotationline_id = ? ";
   	    				 
		   PreparedStatement pstmt5 = null;
		   ResultSet rs5 = null; 
 		  	try
  	   	{
 		  		//System.out.println(" Inside BOM");
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
			 finally {try {DB.close(rs_gen, pstmt_gen);} catch (Exception e) {}}
    			    
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
			finally {try {DB.close(rs_gen, pstmt_gen);} catch (Exception e) {}}
			
	int X = 1;		
	int k =0;int l=0; int m =0; int n=0 ; int o = 0;
	int k1 =0;int l1=0; int m1 =0; int n1=0 ; int o1 = 0;
	//System.out.println("client : " + AD_Client_ID );
  	 for (int i=fr_r;i<=to_r;i++)	    
  	 {    
  	   int j= fr_c;
  	   row=sheet.getRow(i);

  	   HSSFCell prod_cell = row.getCell(j);
 	   //String bom_prod = prod_cell.getStringCellValue();
 	   CellValue prod_cellValue = evaluator.evaluate(prod_cell);
       String bom_prod = prod_cellValue.getStringValue().toUpperCase().trim();
	   
  	    	
 	   //String sql_prod = " Select m_product_id from m_product where trim(upper(value)) = " +
  	   //" trim(upper(?)) and ad_client_id = " + AD_Client_ID; 
       String sql_prod = " Select m_product_id from m_product where value = " +
	   " ? and ad_client_id = " + AD_Client_ID; 
  	   Integer m_product_id = DB.getSQLValue(get_TrxName(), sql_prod, bom_prod);
  	   

  	   //***************Abdulla
    	 String sql_prod_Cost = " Select currentcostprice from m_cost where m_product_id = " +
    		" ? and m_costelement_id = 1000001 and ad_client_id = " + AD_Client_ID;
         BigDecimal m_RawMat_cost = DB.getSQLValueBD (get_TrxName(), sql_prod_Cost,m_product_id);
  	  //BigDecimal m_RawMat_cost=Env.ZERO;
      //****************Abdulla  	  
  	     	   
  	   if (m_product_id == null || m_product_id <= 0 )
  	   {
  		   String sql_del = " delete from xx_quotationline_rmcost where c_quotationline_id = " + record_id ;
 	       int del_rmcost = DB.executeUpdate(sql_del, get_TrxName()); 
  	    	
 	       String sql_upd = " update c_quotationline set price = 0 "+ 
  	  		  	"  ,totalline = 0 where m_product_id = " + M_Product_ID + 
  	  		    " and c_quotationline_id = " + record_id;
  	  		int no = DB.executeUpdate(sql_upd, get_TrxName());  
  	    	
  	  		String sql_gt = "select sum(totalline) from c_quotationline where c_quotation_id = ?";
  	  		BigDecimal totline_amt = DB.getSQLValueBD(get_TrxName(), sql_gt, quotID);
  	  		BigDecimal RndAmt = totline_amt.setScale(2,BigDecimal.ROUND_UP);
  	  		
  	  	    //Line Grand Total
  			String upd_grtot1 = " select nvl(sum(price*qty),0) from c_quotationline where c_quotation_id = ?";
  			BigDecimal line_grtot1 = DB.getSQLValueBD(get_TrxName(), upd_grtot1, quotID);
  			//End of Line Grand Total
  			
  			//Tax Amt
  	         String upd_taxamt2 = " SELECT nvl(sum(case ct.rate when 0 then 0 else round(cql.totalline*(ct.rate/100),2) end),0) "
  	            + "FROM c_quotationline cql INNER JOIN c_tax ct ON ct.c_tax_id=cql.c_tax_id"
  	            + " WHERE cql.c_quotation_id= ?";
  	         BigDecimal taxamt2 = DB.getSQLValueBD(get_TrxName(), upd_taxamt2, quotID);
  	         //End of Tax Amt
  	         
  	         Double netAmt2 = RndAmt.doubleValue()+taxamt2.doubleValue();
  	         //BigDecimal netbd2=new BigDecimal(netAmt2);
  		     //netbd2 = netbd2.setScale(2, BigDecimal.ROUND_UP);
  	  		
  	  		String sql_hdr = " update c_quotation set grandtotal = "+ RndAmt + ",taxamt=" + taxamt2 + 
  	    	   	",NETAMT = "+ netAmt2 + ",LineGrAmt=" + line_grtot1 +
  	    	    "  where c_quotation_id = " + quotID + "";
  	  		int grand_total = DB.executeUpdate(sql_hdr, get_TrxName());

  	  		String sql_upd_prod = " update m_product set lock_status = 'N' where m_product_id = "+M_Product_ID
  	  					  		+ " and ad_client_id = " + AD_Client_ID;
  	    	int lock_status = DB.executeUpdate(sql_upd_prod, get_TrxName());
  	    	
  	    	//JOptionPane.showMessageDialog(null, " Product MisMatch Between Excel & Master (OR) Invalid Cell Address ");
  	    	
  	    	//Beta Changed on 14Aug2011 for showing Raw material on display
  	    	addLog(" Product MisMatch Between Excel Sheet & Product Master (Excel Item : " +  bom_prod + ")");
  	    	DB.close(rs5, pstmt5);
  	    	return "";
  	     }
  	   
  	     	   
  /***NEW CODE ADDED ON 25Apr11 by DSPL **/
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
		 finally {try {DB.close(rs_gen, pstmt_gen);} catch (Exception e) {}}
	   
		k = qty_c;
		//qty
	  	row = sheet.getRow(i);
	    HSSFCell Qty_cell = row.getCell(k);
		CellValue Qty_cellValue = evaluator.evaluate(Qty_cell);
		double bom_Qty = Qty_cellValue.getNumberValue();
  	   
		//Skip Insert if Qty=0 in BOM **Added by Subair 10.09.2015
		if (bom_Qty>0)
		{
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
				 finally {try {DB.close(rs_gen, pstmt_gen);} catch (Exception e) {}}
					    
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
				 finally {try {DB.close(rs_gen, pstmt_gen);} catch (Exception e) {}}
					    
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
				 finally {try {DB.close(rs_gen, pstmt_gen);} catch (Exception e) {}}
					    
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
					 finally {try {DB.close(rs_gen, pstmt_gen);} catch (Exception e) {}}
						    
			  	   o = RMC_c;
			  	 		  	   
			  	   
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
			
	      /***NEW CODE ENDS HERE **/   
	              
	            
	          if (cost_cell == null || Qty_cell == null || costfact_cell == null || sc_cell == null|| uc_cell == null)
	          {
	        	  DB.close(rs5, pstmt5); 
		     	  return "";
	          }
	          CellValue cost_cellValue = evaluator.evaluate(cost_cell);
	          double bom_cost = cost_cellValue.getNumberValue();
			  Double bom = new Double(cost_cellValue.getNumberValue());
			  
			/*  DecimalFormat df=new DecimalFormat("0.000000");
			  String formatbom_cost = df.format(bom_cost); 
			  bom_cost = (Double)df.parse(formatbom_cost);
			  String formatbom = df.format(bom); 
			  bom = (Double)df.parse(formatbom);  */
			  
			  CellValue cf_cellvalue = evaluator.evaluate(costfact_cell);
			  double bom_cf = cf_cellvalue.getNumberValue();
			
			  CellValue sc_cellvalue = evaluator.evaluate(sc_cell);
			  double bom_sc = sc_cellvalue.getNumberValue();
	
			  CellValue uc_cellvalue = evaluator.evaluate(uc_cell);
			  double bom_uc = uc_cellvalue.getNumberValue();
	
			  
			  /**to get sequence id for xx_quotationline_rmcost*/
	/*		  String sql = "{call proc_upd_seq_no(?,?,?,?)}";
			  CallableStatement cstmt = null;
			  try {
				 cstmt = DB.prepareCall(sql);
				 cstmt.setInt(1,AD_Client_ID);
				 cstmt.setInt(2,AD_Org_ID);
				 cstmt.setString(3,"XX_QUOTATIONLINE_RMCOST");
				 cstmt.registerOutParameter(4, java.sql.Types.INTEGER);
				 cstmt.executeUpdate();
				 XX_Quotationline_RMCost_ID = cstmt.getInt(4);
				 cstmt.close();
				 }catch(Exception e){}
			  	finally {try {DB.close(cstmt);} catch (Exception e) {}} 
			  	
			  	
				//Insert into Quotation RM COST
				String sql_2 = "insert into XX_QUOTATIONLINE_RMCOST(XX_QUOTATIONLINE_RMCOST_ID,AD_CLIENT_ID," +
				 "AD_ORG_ID,ISACTIVE,CREATED,CREATEDBY,UPDATED,UPDATEDBY,C_QUOTATIONLINE_ID," +
				 " RM_PRODUCT_ID,RM_QUANTITY,RM_AMOUNT,COST_FACTOR," +
				 " STDCOST,UNITCOST,RAWMAT_AVG_COST)" +
				 " values("+XX_Quotationline_RMCost_ID+"," +AD_Client_ID+","
				 +AD_Org_ID+",'Y',sysdate,100,sysdate,100,"+record_id+
				","+m_product_id+","+bom_Qty+","+bom_cost + "," + bom_cf + 
				 ","+bom_sc+","+ bom_uc +","+ m_RawMat_cost +")";
	*/
			     String sql_2 = "insert into XX_QUOTATIONLINE_RMCOST(AD_CLIENT_ID," +
				 "AD_ORG_ID,ISACTIVE,CREATED,CREATEDBY,UPDATED,UPDATEDBY,C_QUOTATIONLINE_ID," +
				 " RM_PRODUCT_ID,RM_QUANTITY,RM_AMOUNT,COST_FACTOR," +
				 " STDCOST,UNITCOST,RAWMAT_AVG_COST)" +
				 " values("+AD_Client_ID+","
				 +AD_Org_ID+",'Y',sysdate,100,sysdate,100,"+record_id+
				","+m_product_id+","+bom_Qty+","+bom_cost + "," + bom_cf + 
				 ","+bom_sc+","+ bom_uc +","+ m_RawMat_cost +")";
			  
				int a = DB.executeUpdate(sql_2,get_TrxName());
				if(a<0)  
				{  
					//try {
				    	String sql_prodErr = " update m_product set lock_status = 'N' where m_product_id = "+ M_Product_ID
			 				+ " and ad_client_id = " + AD_Client_ID;
				    	int lock_statusErr = DB.executeUpdate(sql_prodErr, get_TrxName());
				    	
				    	String sql_updErr = " update c_quotationline set price = 0,netprice=0" + 
				  		  " ,PRODDESC = '',GETPRICE='N'" + 
				  		  ",totalline = 0" + " where m_product_id = " + M_Product_ID + 
				  		   " and c_quotationline_id = " + record_id;
				    	int noErr = DB.executeUpdate(sql_updErr, get_TrxName());
				    	
				    	String sql_GetNewID = "Select Max(XX_QUOTATIONLINE_RMCOST_ID) from  XX_QUOTATIONLINE_RMCOST";
				    	BigDecimal newID = DB.getSQLValueBD(get_TrxName(), sql_GetNewID);
				    	Double nID = newID.doubleValue()+1;
				    	String sql_updNewID = "update ad_sequence set currentnext=" + nID + " where name like 'XX_QUOTATIONLINE_RMCOST'";
				    	int updNewID = DB.executeUpdate(sql_updNewID, get_TrxName());
				    	
				    	String sql_delErr = " delete from xx_quotationline_rmcost where c_quotationline_id = " + record_id ;
				 	    int del_rmcostErr = DB.executeUpdate(sql_delErr, get_TrxName());
				    	
				    	addLog("Server Busy, Please click OK button to try again..");
				    	DB.close(rs5, pstmt5);
						return ""; 
					//} catch(Exception e){JOptionPane.showMessageDialog(null, e);}
			    }  
			 } //End of Skip Insert if Qty=0 in BOM **Added by Subair 10.09.2015
			
			
  	
  	   }//end of For loop
	} // if loop for BOM Ends 
	  	rs5.close();
	  	pstmt5.close();
  	}catch(Exception e)
  		{ 
  			//JOptionPane.showMessageDialog(null,"Error..While making bill of material.Please click OK button to try again..");
  			DB.close(rs5, pstmt5);
  			//JOptionPane.showMessageDialog(null,e.getMessage());
  			return ""; 
  		}
	finally {try {DB.close(rs5, pstmt5);} catch (Exception e) {}}
 		  	
 	//DUCT HEATER PRICING (to consider elect.items and GI items separately) 
 	if (Prod_Value.equalsIgnoreCase("EDH") | Prod_Value.equalsIgnoreCase("MPRD"))// | Prod_Value.equalsIgnoreCase("FAN-VORTICE"))
 	{
 		
 		String str_rmcost="select nvl(sum(rm_amount),0) from xx_quotationline_rmcost where rm_product_id in(select m_product_id from " +
 						  "beta_qtn_elecitems) and c_quotationline_id=?";
 		BigDecimal str_eleccost=DB.getSQLValueBD(get_TrxName(), str_rmcost, record_id);
 		double str_cost = prod_cost-str_eleccost.doubleValue();
 		BigDecimal str_price1=Env.ZERO,str_markup_cost=Env.ZERO;
 		if (Prod_Value.equalsIgnoreCase("EDH")) 
 		{
 			String sql_str_markup = "select nvl(EDH_MARKUP_PCNT,2) from c_acctschema where ad_client_id = ? ";
 			str_markup_cost = DB.getSQLValueBD(get_TrxName(), sql_str_markup, AD_Client_ID); 
 		}
 		else if (Prod_Value.equalsIgnoreCase("MPRD")) 
 		{
 			String sql_act_markup = "select ACT_MARKUP_PCNT from c_acctschema where ad_client_id = ? ";
 			str_markup_cost = DB.getSQLValueBD(get_TrxName(), sql_act_markup, AD_Client_ID);
 			//str_markup_cost =new BigDecimal("3.5");
 		}
 		
 		str_price1 = str_eleccost.multiply(str_markup_cost);
 		BigDecimal str_price2 = new BigDecimal(str_cost).multiply(pricefactor);
 		price = str_price1.add(str_price2);
 		price = price.setScale(0,BigDecimal.ROUND_CEILING);
		 
		String sql_strupd = " update c_quotationline set price = " + price + 
		  		  " ,PRODDESC = '" + prod_desc.trim() + v_quotline_desc.trim() +
		  		  "',totalline = " + price + " * QTY where m_product_id = " + M_Product_ID + 
		  		   " and c_quotationline_id = " + record_id;  		     
		int str_no = DB.executeUpdate(sql_strupd, get_TrxName());
 		
 	}
 	//END OF DUCT HEATER PRICING (Added MPRD also here on 13.06.22		
			
	String sql_disc = " select DISC_PCNT from c_quotationline where c_quotationline_id = ? ";
	BigDecimal disc_pcnt = DB.getSQLValueBD(null, sql_disc,record_id);
	disc_pcnt = (new BigDecimal(100).subtract(disc_pcnt)).divide(new BigDecimal(100));
	
	BigDecimal netprice = price.multiply(disc_pcnt);
 
	BigDecimal Est_Cost = new BigDecimal(prod_cost);
	String sql_netprice = "update c_quotationline set PRODDESC = '" + prod_desc.trim() + v_quotline_desc.trim() + "'," +
	"price=" + price + ",netprice=" + netprice + ",totalline=" + netprice + " * Qty " +
	 ",EstCost=" + Est_Cost.setScale(2,BigDecimal.ROUND_HALF_UP) + ",GETPRICE='Y' where c_quotationline_id =  " + record_id;
	
	int np = DB.executeUpdate(sql_netprice, get_TrxName());
	
  	
//Beta Code  	
  	String sql_gt = "select sum(totalline) from c_quotationline where c_quotation_id = ?";
  	BigDecimal totline_amt = DB.getSQLValueBD(get_TrxName(), sql_gt, quotID);
  	BigDecimal RndAmt = totline_amt.setScale(2,BigDecimal.ROUND_UP);
  	
  	//Line Grand Total
	String upd_grtot2 = " select nvl(sum(price*qty),0) from c_quotationline where c_quotation_id = ?";
	BigDecimal line_grtot2 = DB.getSQLValueBD(get_TrxName(), upd_grtot2, quotID);
	//End of Line Grand Total
	
	//Tax Amt
    String upd_taxamt3 = " SELECT nvl(sum(case ct.rate when 0 then 0 else round(cql.totalline*(ct.rate/100),2) end),0) "
       + "FROM c_quotationline cql INNER JOIN c_tax ct ON ct.c_tax_id=cql.c_tax_id"
       + " WHERE cql.c_quotation_id= ?";
    BigDecimal taxamt3 = DB.getSQLValueBD(get_TrxName(), upd_taxamt3, quotID);
    //End of Tax Amt
    
    Double netAmt3 = RndAmt.doubleValue()+taxamt3.doubleValue();
    //BigDecimal netbd3=new BigDecimal(netAmt3);
	//netbd3 = netbd3.setScale(2, BigDecimal.ROUND_UP);
  	
  	String sql_hdr = " update c_quotation set grandtotal =  " + RndAmt + ",taxamt=" + taxamt3 + 
  	      " ,netamt = " + netAmt3 + ",LineGrAmt= " + line_grtot2 + " where c_quotation_id = " + quotID + "";
  	int grand_total = DB.executeUpdate(sql_hdr, get_TrxName());  
//Beta Code End  	
    	
  	
  	String sql_prod = " update m_product set lock_status = 'N' where m_product_id = "+M_Product_ID
  	 				+ " and ad_client_id = " + AD_Client_ID;
    int lock_status = DB.executeUpdate(sql_prod, get_TrxName());

    return "";
	    			
   }
    
  void ExecuteProcess(int record_id)
  {
	
	  String unit, b_unit, lenYN, awYN, len_addr, aw_addr, aw,ways;
	  double a ,b , a_value=0 ,b_value,s_value, supply,inch = 25, len,gnAVal,gnBVal,cjnpPole,heaterprice,tr_thick_val,
			  ct_size2,ct_size3;
	    
	  
	  String act_cell=null,act_qty_cell=null,act_cost_cell=null,ther_cell=null,ther_qty_cell=null,ther_cost_cell=null,
	  tran_cell=null,tran_qty_cell=null,tran_cost_cell=null,settbar=null,tBarYN=null,gNA=null,gNB=null,slyn=null,
	  heater_cell=null,heater_qty_cell=null,heater_cost_cell=null,lenDia=null;
	  int act_id=0,ther_id=0,tran_id=0,heater_id=0;
	  String act_code=null,ther_code=null,tran_code=null,act_sname=null,ther_sname=null,tran_sname=null,heater_code=null;
	  String act_name=null,ther_name=null,tran_name=null,disprem=null,lvByArea=null,driveCell=null,mechCell=null,bushCell=null,metalCell=null,
	  driveVal=null,mechVal=null,bushVal=null,metalVal=null,metalVal_Vdr=null,heater_name=null,flangeCell=null,flangeVal=null,spindleCell=null,
	  spindleVal=null,ulClass=null,setULClass=null,ltSeriesYN=null,bladeThickCell=null,bladeThickVal=null,
	  ca_pc_cell=null,ca_pc_val=null,ca_type_cell=null,ca_type_val=null,ca_ais_cell=null,ca_ais_val=null,
	  ca_bd_cell=null,ca_bd_val=null,ca_met_cell=null,ca_met_val=null,ca_model_val=null,
	  ct_len_cell=null,ct_len_val=null,ct_cot_cell=null,ct_cot_val=null,pgl_met_cell=null,pgl_fgm_cell=null,pgl_met_val=null,pgl_fgm_val=null,ul_ssbush=null,
	  diffwirecell=null,diffwireyn=null,diffchainyn=null,ad_metal_cell=null,ad_ins_Cell=null,ad_metal_val=null,ad_ins_val,
	  tr_thick_cell=null,ct_hand=null,stl_is=null,stl_is_cell=null,stl_filter=null,stl_filter_cell=null,
	  ld_slotwidth=null;
	  
	  int act_qty=0,ther_qty=0,tran_qty=0,heater_qty=0;
	  double act_cost=0,ther_cost=0,tran_cost=0,heater_cost=0,dh_kw=0,fdc_slv_depth=0,fdc_slv_thick=0,
	  fdc_fra_thick=0,vav_fra_thick=0,bvn_fancost=0,fancfw_impcost=0,fancfw_motcost=0,fancfw_cascost=0;
	  int act_r=0,act_c=0,act_qty_r=0,act_qty_c=0,act_cost_r=0,act_cost_c=0,ther_r=0,ther_c=0,ther_qty_r=0,ther_qty_c=0,ther_cost_r=0,
	  ther_cost_c=0,tran_r=0,tran_c=0,tran_qty_r=0,tran_qty_c=0,tran_cost_r=0,tran_cost_c=0,heater_r=0,heater_c=0,heater_qty_r=0,heater_qty_c=0,
	  heater_cost_r=0,heater_cost_c=0;
  
	  String sql1 = "SELECT cql.m_product_id,mp.EXCEL_FILE_NAME," +
	  		" mp.FGCOST_CELL_ADDR,mp.LOCK_STATUS,mp.VALUE," +
	  		" nvl(mp.a_cell_addr,0),nvl(mp.b_cell_addr,0),nvl(mp.supplyh_cell_addr,0) ," +
	  		" trim(cql.unit) as unit ,cql.a,cql.b,cql.supply,mp.originalname,nvl(trim(cql.bunit),' ') as bunit,nvl(mp.noparam,'N') as noparam " +
	  		",nvl(cql.SETLENGTH,'N'),nvl(cql.SETAIRWAY,'N'),nvl(trim(mp.length_cell_addr),' '),nvl(trim(mp.airway_cell_addr),' ')" +
	  		",nvl(cql.length,0),nvl(cql.airway,' '),mp.name as mpname,nvl(cql.disprem,'N') as disprem,nvl(cql.tbaryn,'N') as tbar" +
	  		",nvl(trim(mp.gna_cell_addr),'N') as GNAADDR,nvl(trim(mp.gnb_cell_addr),'N') as GNBADDR,nvl(cql.GNA,0) as GNA,nvl(cql.GNB,0) as GNB" +
	  		",nvl(cql.LVBYAREAYN,'N') as LVBYAREAYN,NVL(cql.SLEEVEYN,'N') as SLEEVEYN,nvl(trim(mp.drive_cell_addr),'N') as DRIVECELL" +
	  		",nvl(trim(mp.mech_cell_addr),'N') as MECHCELL,nvl(trim(mp.bush_cell_addr),'N') as BUSHCELL,nvl(trim(mp.metal_cell_addr),'N') as METALCELL" +
	  		",nvl(trim(cql.DRIVE),'N') as DRIVE,nvl(trim(cql.MECHANISM),'N') as MECHANISM,nvl(trim(cql.BUSHBEAR),'N') as BUSHBEAR" +
	  		",nvl(trim(cql.METALTHICK),'N') as METALTHICK,nvl(trim(cql.METALTHICK_VDR),'N') as METALTHICK_VDR,nvl(cql.CJNPPOLE,0) as CJNPPOLE" +
	  		",nvl(cql.heaterprice,0) as HEATERPRICE,nvl(trim(mp.FLANGE_cell_addr),'N') as FLANGECELL,nvl(cql.flangemtrl,'N') as FLANGEMTRL" +
	  		",nvl(trim(mp.spindle_cell_addr),'N') as SPINDLECELL,nvl(cql.SPINDLE,'N') as SPINDLE,nvl(cql.SETULCLASS,'N') as SETULCLASS," +
	  		"nvl(cql.ULCLASS,' ') as ULCLASS,nvl(cql.SILAXLENDIA,'1') as SILAXLENDIA,nvl(trim(mp.bladethick_cell_addr),'N') as BLADETHICKCELL," +
	  		"nvl(cql.BLADETHICK,'N') as BLADETHICK,nvl(trim(mp.ca_pc_cell_addr),'N') as CAPCCELL,nvl(trim(mp.ca_type_cell_addr),'N') as CATYPECELL," +
	  		"nvl(trim(mp.ca_ais_cell_addr),'N') as CAAISCELL,nvl(trim(mp.ca_bd_cell_addr),'N') as CABDCELL,nvl(trim(mp.ca_metal_cell_addr),'N') as CAMETCELL," +
	  		"nvl(cql.CA_PC,'N') as CAPCVAL,nvl(cql.CA_TYPE,'N') as CATYPEVAL,nvl(cql.CA_AIS,'N') as CAAISVAL,nvl(cql.CA_BD,'N') as CABDVAL," +
	  		"nvl(cql.CA_METAL,'N') as CAMETVAL,nvl(cql.CA_MODEL,'N') as CAMODELVAL,nvl(trim(mp.ctcoating_cell_addr),'N') as CTCOTCELL," +
	  		"nvl(trim(mp.ctlength_cell_addr),'N') as CTLENCELL,nvl(cql.ct_coating,'N') as CTCOTVAL,nvl(cql.ct_length,'N') as CTLENVAL," +
	  		"nvl(trim(mp.pgl_metal_cell_addr),'N') as PGLMETCELL,nvl(cql.pgl_metal,'N') as PGLMETVAL," +
	  		"nvl(trim(mp.pgl_fgm_cell_addr),'N') as PGLFGMCELL,nvl(cql.pgl_fgm_yn,'N') as PGLFGMVAL,cql.UL_SSBUSHYN,nvl(trim(mp.diff_wire_cell_addr),'N') as DIFFWIRECELL," +
	  		"cql.DIFF_WIREYN,cql.DIFF_CHAINYN,nvl(trim(mp.ad_metal_cell_addr),'N') as ADMETALCELL,nvl(trim(mp.ad_ins_cell_addr),'N') as ADINSCELL," +
	  		"nvl(cql.AD_METAL,'N') as ADMETALVAL,nvl(cql.AD_INSULATION,'N') as ADINSVAL,nvl(cql.TR_THICK,0) as TR_THICK," +
	  		"nvl(trim(mp.tr_thick_cell_addr),'N') as TR_THICKCELL,NVL(cql.ct_handtype,'N') as CTHAND," +
	  		"NVL(cql.stl_is_swm,'N') as STL_IS,nvl(trim(mp.stl_is_cell_addr),'N') as STLISCELL," +
	  		"NVL(cql.stl_filter,'N') as STL_FILTER,nvl(trim(mp.stl_filter_cell_addr),'N') as STLFILTERCELL," +
	  		"NVL(cql.ul_act,'N') as ul_act,NVL(cql.ul_temp,'N') as ul_temp,NVL(cql.ul_power,'N') as ul_power,"+
	  		"NVL(cql.ul_classtype,'N') as ul_classtype,NVL(cql.ul_spigot,'X') as ul_spigot,NVL(cql.ul_linktype,'N') as ul_linktype," +
	  		"NVL(cql.stadyn,'S') as ul_stad,NVL(cql.vcd_gasket,'N') as vcdgasket,NVL(cql.FD_SLVTHICK,'N') as fd_slv," +
	  		"NVL(cql.vcd_phen_yn,'N') as vcd_phen_yn,NVL(cql.ct_riser,'N') as ct_riser," +
	  		"NVL(cql.ct_degree,'N') as ct_degree,nvl(cql.ct_size2,0) as ct_size2,nvl(cql.ct_size3,0) as ct_size3," +
	  		"NVL(cql.str_type,'N') as str_type,nvl(cql.str_pctype,'N') as str_pctype,nvl(cql.str_ais,'N') as str_ais," +
	  		"NVL(cql.str_fins,'N') as str_fins,nvl(cql.str_metal,'N') as str_metal,nvl(cql.str_model,'N') as str_model,nvl(cql.str_reg,'N') as str_reg," +
	  		"NVL(cql.dh_kw,0) as dh_kw,NVL(cql.dh_phase,'N') as dh_phase,NVL(cql.dh_pl,'N') as dh_pl," +
	  		"NVL(cql.dh_metal,'N') as dh_metal,NVL(cql.dh_stage,'N') as dh_stage,NVL(cql.ca_model_bvn,'N') as CAMODELBVN," +
	  		"NVL(cql.ca_fins_bvn,'N') as CAFINSBVN,NVL(cql.p4w_tbaryn,'N') as P4W_TBARYN," +
	  		"nvl(trim(mp.vcd_nonstd_excel),'N') as vcd_excel,nvl(cql.vcd_jamseal_yn,'N') as vcd_jamseal," +
	  		"nvl(cql.vcd_shaft,'N') as vcd_shaft,nvl(cql.vcd_nonstd_yn,'N') as vcd_nonstd,nvl(cql.vcd_blade,'N') as vcd_blade," +
	  		"nvl(cql.ul_noact_yn,'N') as ul_noact_yn,nvl(cql.agvcd_blade,'N') as agvcd_blade," +
	  		"nvl(cql.ct_pctype,'N') as ct_pctype,nvl(cql.pgl_flange,'N') as pgl_flange," +
	  		"nvl(cql.cowl_base_yn,'N') as cowl_base_yn,nvl(cql.fdc_slv_yn,'N') as fdc_slv_yn,nvl(cql.fdc_slv_depth,0) as fdc_slv_depth," +
	  		"nvl(cql.fdc_slv_thick,0) as fdc_slv_thick,nvl(cql.fdc_fra_thick,0) as fdc_fra_thick," +
	  		"nvl(cql.fdc_ind_yn,'N') as fdc_ind_yn,nvl(cql.gasket_yn,'N') as gasket_yn,nvl(cql.vav_fra_thick,0) as vav_fra_thick," +
	  		"nvl(mp.gd_type,'N') as gdtype,nvl(cql.fb_slotn,'0') as fb_slotn,nvl(cql.fb_slotw,'00') as fb_slotw," +
	  		"nvl(cql.fdc_rating,'N') as fdc_rating,nvl(cql.nrd_fra_thick,'N') as nrd_fra_thick,nvl(cql.pgl_psheet_thick,'0.7') as pgl_psheet_thick," +
	  		"nvl(cql.slotwidth,'N') as ld_slotwidth,nvl(cql.vcd_rivets,'N') as vcd_rivets,nvl(cql.fancfw_kw,'N') as fancfw_kw," +
	  		"nvl(cql.fancfw_blade,'N') as fancfw_blade,nvl(cql.fancfw_pole,'N') as fancfw_pole,nvl(cql.bvn_nonstd_yn,'N') as bvn_nonstd," +
	  		"nvl(cql.bvn_gland_yn,'N') as bvn_gland,nvl(cql.bvn_ip66_yn,'N') as bvn_ip66,nvl(cql.bvn_flanges_yn,'N') as bvn_flanges," +
	  		"nvl(cql.bvn_insscr_type,'N') as bvn_insscr_type,nvl(cql.bvn_ains_yn,'N') as bvn_ains," +
	  		"nvl(cql.mprd_bush,'N') as mprd_bush,nvl(cql.mprd_frame,'N') as mprd_frame," +
	  		"nvl(cql.mprd_jamseal_yn,'N') as mprd_jamb,nvl(cql.fb_flange,'51') as fb_flange," +
	  		"nvl(cql.bvn_fancost,0) as bvn_fancost,nvl(cql.fancfw_impcost,0) as fancfw_impcost," +
	  		"nvl(cql.fancfw_motcost,0) as fancfw_motcost,nvl(cql.fancfw_cascost,0) as fancfw_cascost," +
	  		"nvl(cql.eal_filteryn,'N') as eal_filteryn,nvl(cql.datarack_model,'N') as datarack_model," +
	  		"nvl(cql.datarack_frame,'N') as datarack_frame,nvl(cql.ul_intextact,'N') as ul_intextact," +
	  		"nvl(cql.fdc_rn_yn,'N') as fdc_rn_yn,nvl(cql.fdc_rn_size,0) as fdc_rn_size,cql.evdr_yn," +
	  		"nvl(cql.mfeet_pc,'N') as mfeet_pc,nvl(cql.mfeet_brand,'NN') as mfeet_brand," +
	  		"nvl(cql.rad_12mm_yn,'N') as rad_12mm_yn,nvl(cql.jb_frame,'N') as jb_frame," +
	  		"cql.jb_logo_yn,cql.jb_kout_yn " +
	  		" from c_quotationline cql INNER JOIN m_product mp ON cql.m_product_id = mp.m_product_id" +
	  		" where cql.c_quotationline_id = ? " +
	  		" and ((mp.a_cell_addr is not null or " +
	  		"      mp.b_cell_addr is not null or" +
	  		"      mp.supplyh_cell_addr is not null) or (nvl(mp.noparam,'N') ='Y')) ";
	    				
	 PreparedStatement pstmt1 = null;
	 ResultSet rs1 = null;
	 try 
	 {
	  pstmt1 = DB.prepareStatement(sql1, "DSPL");
	  pstmt1.setInt(1, record_id);
	  rs1 = pstmt1.executeQuery();
	
	  if(rs1.next()) 
	  {	
		M_Product_ID = rs1.getInt(1);
		Path_of_XLFile = rs1.getString(2);
		FGCost_Cell_Addr = rs1.getString(3);
		Lock_Status = rs1.getString(4);
		Prod_Value = rs1.getString(5);
		a_cell_addr = rs1.getString(6);
		b_cell_addr = rs1.getString(7);
		supplyh_cell_addr = rs1.getString(8);
		unit = rs1.getString(9);
		a = rs1.getDouble(10);
		b = rs1.getDouble(11);
		supply = rs1.getDouble(12);
    	v_description = rs1.getString(13);
    	b_unit = rs1.getString(14);
    	noparam = rs1.getString(15);
    	lenYN = rs1.getString(16);
    	awYN = rs1.getString(17);
    	len_addr = rs1.getString(18);
    	aw_addr = rs1.getString(19);
    	len = rs1.getDouble(20);
    	aw = rs1.getString(21);
    	len_disp = rs1.getBigDecimal(20);
    	mpname=rs1.getString("mpname");
    	disprem=rs1.getString("disprem");
    	settbar=rs1.getString("tbar");
    	gNA=rs1.getString("GNAADDR");
    	gNB=rs1.getString("GNBADDR");
    	gnAVal=rs1.getDouble("GNA");
    	gnBVal=rs1.getDouble("GNB");
    	lvByArea = rs1.getString("LVBYAREAYN");
    	slyn = rs1.getString("SLEEVEYN");
    	
    	//Added by Subair on 17Nov2011 for display purpose 
    	a_disp = rs1.getBigDecimal(10);
    	b_disp = rs1.getBigDecimal(11);
    	s_disp = rs1.getBigDecimal(12);
    	gnAVal_disp = rs1.getBigDecimal("GNA");
    	gnBVal_disp = rs1.getBigDecimal("GNB");
    	cjnpPole_disp = rs1.getBigDecimal("CJNPPOLE");
    	//
    	
    	driveCell = rs1.getString("DRIVECELL");
    	mechCell = rs1.getString("MECHCELL");
    	bushCell = rs1.getString("BUSHCELL");
    	metalCell = rs1.getString("METALCELL");
    	driveVal=rs1.getString("DRIVE");
    	mechVal=rs1.getString("MECHANISM");
    	bushVal=rs1.getString("BUSHBEAR");
    	metalVal=rs1.getString("METALTHICK");
    	metalVal_Vdr=rs1.getString("METALTHICK_VDR");
    	cjnpPole = rs1.getDouble("CJNPPOLE");
    	heaterprice = rs1.getDouble("HEATERPRICE");
    	flangeCell = rs1.getString("FLANGECELL");
    	flangeVal = rs1.getString("FLANGEMTRL");
    	spindleCell= rs1.getString("SPINDLECELL");
    	spindleVal= rs1.getString("SPINDLE");
    	setULClass = rs1.getString("SETULCLASS");
    	ulClass = rs1.getString("ULCLASS");
    	lenDia = rs1.getString("SILAXLENDIA");
    	bladeThickCell = rs1.getString("BLADETHICKCELL");
    	bladeThickVal = rs1.getString("BLADETHICK");
    	
    	//FAN CASING 
    	ca_pc_cell=rs1.getString("CAPCCELL");
    	ca_type_cell=rs1.getString("CATYPECELL");
    	ca_ais_cell=rs1.getString("CAAISCELL");
    	ca_bd_cell=rs1.getString("CABDCELL");
    	ca_met_cell=rs1.getString("CAMETCELL");
    	ca_pc_val=rs1.getString("CAPCVAL");
    	ca_type_val=rs1.getString("CATYPEVAL");
    	ca_ais_val=rs1.getString("CAAISVAL");
    	ca_bd_val=rs1.getString("CABDVAL");
    	ca_met_val=rs1.getString("CAMETVAL");
    	ca_model_val=rs1.getString("CAMODELVAL");
    	ca_model_bvn=rs1.getString("CAMODELBVN");
    	ca_fins_bvn=rs1.getString("CAFINSBVN");
    	
    	ct_cot_cell=rs1.getString("CTCOTCELL");
    	ct_len_cell=rs1.getString("CTLENCELL");
    	ct_cot_val=rs1.getString("CTCOTVAL");
    	ct_len_val=rs1.getString("CTLENVAL");
    	
    	pgl_met_cell=rs1.getString("PGLMETCELL");
    	pgl_fgm_cell=rs1.getString("PGLFGMCELL");
    	pgl_met_val=rs1.getString("PGLMETVAL");
    	pgl_fgm_val=rs1.getString("PGLFGMVAL");
    	ul_ssbush=rs1.getString("UL_SSBUSHYN");
    	diffwirecell=rs1.getString("DIFFWIRECELL");
    	diffwireyn=rs1.getString("DIFF_WIREYN");
    	diffchainyn=rs1.getString("DIFF_CHAINYN");
    	ad_metal_cell=rs1.getString("ADMETALCELL");
    	ad_ins_Cell=rs1.getString("ADINSCELL");
    	ad_metal_val=rs1.getString("ADMETALVAL");
    	ad_ins_val=rs1.getString("ADINSVAL");
    	tr_thick_cell=rs1.getString("TR_THICKCELL");
    	tr_thick_val=rs1.getDouble("TR_THICK");
    	ct_hand=rs1.getString("CTHAND");
    	
    	stl_is=rs1.getString("STL_IS");
    	stl_is_cell=rs1.getString("STLISCELL");
    	stl_filter=rs1.getString("STL_FILTER");
    	stl_filter_cell=rs1.getString("STLFILTERCELL");
    	
    	ul_act=rs1.getString("ul_act");
    	ul_temp=rs1.getString("ul_temp");
    	ul_power=rs1.getString("ul_power");
    	ul_classtype=rs1.getString("ul_classtype");
    	ul_spigot=rs1.getString("ul_spigot");
    	ul_linktype=rs1.getString("ul_linktype");
    	ul_stad=rs1.getString("ul_stad");
    	ul_intextact=rs1.getString("ul_intextact");
    	
    	vcd_gasket=rs1.getString("vcdgasket");
    	fd_slv=rs1.getString("fd_slv");
    	vcd_phen=rs1.getString("vcd_phen_yn");
    	
    	ct_riser=rs1.getString("ct_riser");
    	ct_degree=rs1.getString("ct_degree");
    	ct_size2=rs1.getDouble("ct_size2");
    	ct_size3=rs1.getDouble("ct_size3");
    	ct_size2_disp=rs1.getBigDecimal("ct_size2");
    	ct_size3_disp=rs1.getBigDecimal("ct_size3");
    	ct_pctype=rs1.getString("ct_pctype");
    	
    	str_type=rs1.getString("str_type");
    	str_pctype=rs1.getString("str_pctype");
    	str_ais=rs1.getString("str_ais");
    	str_fins=rs1.getString("str_fins");
    	str_metal=rs1.getString("str_metal");
    	str_model=rs1.getString("str_model");
    	str_reg=rs1.getString("str_reg");
    	
    	dh_kw=rs1.getDouble("dh_kw");
    	dh_phase=rs1.getString("dh_phase");
    	dh_pl=rs1.getString("dh_pl");
    	dh_metal=rs1.getString("dh_metal");
    	dh_stage=rs1.getString("dh_stage");
    	
    	p4w_tbaryn=rs1.getString("P4W_TBARYN");
    	
    	vcd_excel=rs1.getString("vcd_excel");
    	vcd_jamseal=rs1.getString("vcd_jamseal");
    	vcd_shaft=rs1.getString("vcd_shaft");
    	vcd_nonstd=rs1.getString("vcd_nonstd");
    	vcd_blade=rs1.getString("vcd_blade");
    	agvcd_blade=rs1.getString("agvcd_blade");
    	
    	ul_noact_yn=rs1.getString("ul_noact_yn");
    	pgl_flange=rs1.getString("pgl_flange");
    	
    	cowl_base_yn=rs1.getString("cowl_base_yn");
    	fdc_slv_yn=rs1.getString("fdc_slv_yn");
    	fdc_slv_depth=rs1.getDouble("fdc_slv_depth");
    	fdc_slv_thick=rs1.getDouble("fdc_slv_thick");
    	fdc_fra_thick=rs1.getDouble("fdc_fra_thick");
    	fdc_ind_yn=rs1.getString("fdc_ind_yn");
    	fdc_rating=rs1.getString("fdc_rating");
    	
    	gasket_yn=rs1.getString("gasket_yn");
    	vav_fra_thick=rs1.getDouble("vav_fra_thick");
    	gdtype=rs1.getString("gdtype");
    	fb_slotn=rs1.getString("fb_slotn");
    	fb_slotw=rs1.getString("fb_slotw");
    	fb_flange=rs1.getString("fb_flange");
    	
    	nrd_fra_thick=rs1.getString("nrd_fra_thick");
    	pgl_psheet=rs1.getString("pgl_psheet_thick");
    	ld_slotwidth=rs1.getString("ld_slotwidth");
    	vcd_rivets=rs1.getString("vcd_rivets");
    	
    	fancfw_blade=rs1.getString("fancfw_blade");
    	fancfw_kw=rs1.getString("fancfw_kw");
    	fancfw_pole=rs1.getString("fancfw_pole");
    	
    	bvn_nonstd=rs1.getString("bvn_nonstd");
    	bvn_gland=rs1.getString("bvn_gland");
    	bvn_ip66=rs1.getString("bvn_ip66");
    	bvn_flanges=rs1.getString("bvn_flanges");
    	bvn_insscr_type=rs1.getString("bvn_insscr_type");
    	bvn_ains=rs1.getString("bvn_ains");
    	
    	mprd_bush=rs1.getString("mprd_bush");
    	mprd_frame=rs1.getString("mprd_frame");
    	mprd_jamb=rs1.getString("mprd_jamb");
    	
    	bvn_fancost=rs1.getDouble("bvn_fancost");
    	fancfw_impcost=rs1.getDouble("fancfw_impcost");
    	fancfw_motcost=rs1.getDouble("fancfw_motcost");
    	fancfw_cascost=rs1.getDouble("fancfw_cascost");
    	
    	eal_filteryn=rs1.getString("eal_filteryn");
    	datarack_model=rs1.getString("datarack_model");
    	datarack_frame=rs1.getString("datarack_frame");
    	
    	fdc_rn_yn=rs1.getString("fdc_rn_yn");
    	fdc_rn=rs1.getDouble("fdc_rn_size");
    	evdr_yn=rs1.getString("evdr_yn");
    	
    	mfeet_pc=rs1.getString("mfeet_pc");
    	mfeet_brand=rs1.getString("mfeet_brand");
    	
    	rad_12mm_yn=rs1.getString("rad_12mm_yn");
    	
    	jb_frame=rs1.getString("jb_frame");
    	jb_logo_yn=rs1.getString("jb_logo_yn");
    	jb_kout_yn=rs1.getString("jb_kout_yn");
    	
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
		 
		//************************* Beta Code*****************************
	       String sqlprd = " select Value from m_product where m_product_id = ? ";
	 	   String PrdVal = DB.getSQLValueStringEx(get_TrxName(), sqlprd, M_Product_ID);      
	    //*************************Beta Code End**************************
		 
	 	 //Assign Non VCD excel file for 3VCD & AUVCD  (Added on 16/02/2021  
		 if (PrdVal.equalsIgnoreCase("3VCDB") | PrdVal.equalsIgnoreCase("3VCDF") | 
			 PrdVal.equalsIgnoreCase("3VCDS") | PrdVal.equalsIgnoreCase("AUVCDB") |
			 PrdVal.equalsIgnoreCase("AUVCDF") | PrdVal.equalsIgnoreCase("AUVCDS"))
		 {
			 	if (vcd_nonstd.equalsIgnoreCase("Y"))
			 	{
			 		Path_of_XLFile = ""+vcd_excel+"";
			 	}
			 
		 }
		 //End of Assign Non VCD excel file for 3VCD & AUVCD 
		 
		 //Assign Non VCD excel file for FAN-BVN (Added on 03.06.22)
		 if (PrdVal.equalsIgnoreCase("FAN-BVN"))
		 {
			 if (bvn_nonstd.equalsIgnoreCase("Y"))
			 {
			 		Path_of_XLFile = "/opt/Adempiere/Grills/FAN-BVN-NONSTD.xls";
			 }
		 }
		 //End of Assign Non VCD excel file for FAN-BVN  
		 
		//Assign 2 Slot excel file for FBF & FBR (Added on 09.09.22)
		 if (PrdVal.equalsIgnoreCase("FBR") | PrdVal.equalsIgnoreCase("FBF"))
		 {
			 if (fb_slotn.equalsIgnoreCase("2"))
			 {
			 		Path_of_XLFile = "/opt/Adempiere/Grills/FBR-FBF-2S.xls";
			 }
		 }
		 //End of Assign 2 Slot excel file for FBF & FBR  
		 
		 //Assign Belimo Excel sheet for UL Dampers
		 if (PrdVal.equalsIgnoreCase("BMFSD-NEW") | PrdVal.equalsIgnoreCase("BEMFSD-NEW") |
			 PrdVal.equalsIgnoreCase("BMFD-NEW") | PrdVal.equalsIgnoreCase("BEMFD-NEW") |
			 PrdVal.equalsIgnoreCase("BMSD-NEW"))
		 {
			if (ul_act.equalsIgnoreCase("B")) 
			{
				if (PrdVal.equalsIgnoreCase("BMFSD-NEW") | PrdVal.equalsIgnoreCase("BEMFSD-NEW"))
					Path_of_XLFile = "/opt/Adempiere/Grills/BMFSD-BELIMO.xls";
				else if (PrdVal.equalsIgnoreCase("BMFD-NEW") | PrdVal.equalsIgnoreCase("BEMFD-NEW"))
					Path_of_XLFile = "/opt/Adempiere/Grills/BMFD-BELIMO.xls";
				else if (PrdVal.equalsIgnoreCase("BMSD-NEW"))
					Path_of_XLFile = "/opt/Adempiere/Grills/BMSD-BELIMO.xls";
			}
		 }
		 //End of Assign Belimo Excel sheet for UL Dampers
		
		 InputStream input = new FileInputStream(Path_of_XLFile);
		 fs = new POIFSFileSystem( input );
	   	 wb = new HSSFWorkbook(fs);
		 sheet = wb.getSheetAt(0);
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
			   finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
			     
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
		   finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
		   
		
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
	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
		
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
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
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
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       
	       //Get Cell Addr for GN A Cell
	       if (gNA.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col6 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col6, null);
	 			   pstmt2.setString(1,gNA);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r6 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c6 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col6, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       
	       //Get Cell Addr for GN B Cell
	       if (gNB.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col7 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col7, null);
	 			   pstmt2.setString(1,gNB);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r7 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c7 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col7, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       
	       
	     //Get Cell Addr for Drive Cell  
	       if (driveCell.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col8 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col8, null);
	 			   pstmt2.setString(1,driveCell);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r8 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c8 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col8, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       //End of Get Cell Addr for Drive Cell
	       
	     //Get Cell Addr for Mechanism Cell  
	       if (mechCell.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col9 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col9, null);
	 			   pstmt2.setString(1,mechCell);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r9 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c9 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col9, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       //End of Get Cell Addr for Mechanism Cell
	       
	     //Get Cell Addr for Bush Cell  
	       if (bushCell.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col10 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col10, null);
	 			   pstmt2.setString(1,bushCell);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r10 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c10 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col10, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       //End of Get Cell Addr for Bush Cell
	       
	     //Get Cell Addr for Metal Cell  
	       if (metalCell.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col11 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col11, null);
	 			   pstmt2.setString(1,metalCell);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r11 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c11 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col11, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       //End of Get Cell Addr for Metal Cell
	       
	     //Get Cell Addr for Flange Cell  
	       if (flangeCell.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col12 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col12, null);
	 			   pstmt2.setString(1,flangeCell);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r12 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c12 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col12, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       //End of Get Cell Addr for Flange Cell
	       
	     //Get Cell Addr for Spindle Cell  
	       if (spindleCell.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col13 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col13, null);
	 			   pstmt2.setString(1,spindleCell);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r13 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c13 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col13, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       //End of Get Cell Addr for Spindle Cell
	       
	     //Get Cell Addr for Blade Thickness Cell  
	       if (bladeThickCell.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col14 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col14, null);
	 			   pstmt2.setString(1,bladeThickCell);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r14 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c14 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col14, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       //End of Get Cell Addr for Blade Thickness Cell
	     
	       //CA - Get Cell Addr for PC  
	       if (ca_pc_cell.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col15 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col15, null);
	 			   pstmt2.setString(1,ca_pc_cell);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r15 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c15 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col15, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       //End of CA - Get Cell Addr for PC 
	       
	     //CA - Get Cell Addr for TYPE  
	       if (ca_type_cell.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col16 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col16, null);
	 			   pstmt2.setString(1,ca_type_cell);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r16 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c16 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col16, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       //End of CA - Get Cell Addr for TYPE
	       
	     //CA - Get Cell Addr for AIS  
	       if (ca_ais_cell.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col17 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col17, null);
	 			   pstmt2.setString(1,ca_ais_cell);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r17 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c17 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col17, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       //End of CA - Get Cell Addr for AIS
	       
	     //CA - Get Cell Addr for BD  
	       if (ca_bd_cell.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col18 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col18, null);
	 			   pstmt2.setString(1,ca_bd_cell);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r18 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c18 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col18, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       //End of CA - Get Cell Addr for BD 
	       
	     //CA - Get Cell Addr for METAL  
	       if (ca_met_cell.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col19 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col19, null);
	 			   pstmt2.setString(1,ca_met_cell);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r19 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c19 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col19, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       //End of CA - Get Cell Addr for METAL 
	       
	     //CT - Get Cell Addr for COATING  
	       if (ct_cot_cell.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col20 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col20, null);
	 			   pstmt2.setString(1,ct_cot_cell);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r20 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c20 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col20, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       //End of CT - Get Cell Addr for COATING  
	       
	     //CT - Get Cell Addr for LENGTH  
	       if (ct_len_cell.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col21 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col21, null);
	 			   pstmt2.setString(1,ct_len_cell);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r21 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c21 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col21, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       //End of CT - Get Cell Addr for LENGTH
	       
	     //PGL - Get Cell Addr for METAL  
	       if (pgl_met_cell.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col22 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col22, null);
	 			   pstmt2.setString(1,pgl_met_cell);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r22 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c22 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col22, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       //End of PGL - Get Cell Addr for METAL
	       
	     //PGL - Get Cell Addr for FGM  
	       if (pgl_fgm_cell.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col23 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col23, null);
	 			   pstmt2.setString(1,pgl_fgm_cell);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r23 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c23 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col23, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       //End of PGL - Get Cell Addr for FGM
	       
	     //RAD & SAD  Get Cell Address for Wire/Chain  
	       if (diffwirecell.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col25 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col25, null);
	 			   pstmt2.setString(1,diffwirecell);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r25 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c25 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col25, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       //End of RAD & SAD  Get Cell Address for Wire/Chain
	       
	     //Access Doors (ACC2L,ACCPHLK etc...)  Get Cell Address for Insualation Type 
	       if (ad_ins_Cell.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col26 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col26, null);
	 			   pstmt2.setString(1,ad_ins_Cell);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r26 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c26 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col26, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       //End of Access Doors (ACC2L,ACCPHLK etc...)  Get Cell Address for Insualation Type
	       
	     //Access Doors (ACC2L,ACCPHLK etc...)  Get Cell Address for Metal Thickness 
	       if (ad_metal_cell.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col27 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col27, null);
	 			   pstmt2.setString(1,ad_metal_cell);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r27 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c27 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col27, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       //End of Access Doors (ACC2L,ACCPHLK etc...)  Get Cell Address for Metal Thickness
	       
	     //Trunking & Cable Tray Get Cell Address for Thickness 
	       if (tr_thick_cell.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col28 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col28, null);
	 			   pstmt2.setString(1,tr_thick_cell);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r28 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c28 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col28, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       //End of Trunking & Cable Tray  Get Cell Address for Thickness 
	     
		   //STLG & STLCG Get Cell Address for IS/SSWM 
	       if (stl_is_cell.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col29 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col29, null);
	 			   pstmt2.setString(1,stl_is_cell);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r29 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c29 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col29, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       //End of STLG & STLCG Get Cell Address for IS/SSWM
	       
		   //STLG & STLCG Get Cell Address for Filter
	       if (stl_filter_cell.equalsIgnoreCase("N"))
	    	   ;
	       else
	       {
	    	   String sql_row_col30 = "select fn_getcelladdres(?) from dual ";
	 		   pstmt2 = null;
	 		   rs2 = null;
	 		   try // fn to get cell address
	 		   {
	 			   pstmt2 = DB.prepareStatement(sql_row_col30, null);
	 			   pstmt2.setString(1,stl_filter_cell);
	 			   rs2 = pstmt2.executeQuery();
	 		      	
	 			   if (rs2.next())
	 			   {
	 				   String celladdr = rs2.getString(1);
	 				   int length = celladdr.length();
	 		  		
	 				   String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 				   r30 = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 			    		    	    			
	 				   String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 				   c30 = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 			    		    	    			    		    	    			
	 			   } //end of if
	 			
	 			   rs2.close();
	 			   pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_col30, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
	       }
	       //End of STLG & STLCG Get Cell Address for Filter
	       
	       /** For A */ 
	       	 	   
	 	  //For Items to Transfer Actuator,Thermostat & Transformer
	 	  if (PrdVal.equalsIgnoreCase("EBP1") | PrdVal.equalsIgnoreCase("EBP2") | PrdVal.equalsIgnoreCase("EBP3") |
		      PrdVal.equalsIgnoreCase("EBP4") | PrdVal.equalsIgnoreCase("EBP5") | PrdVal.equalsIgnoreCase("EBP6") |
		 	  PrdVal.equalsIgnoreCase("EBP7") | PrdVal.equalsIgnoreCase("EBP8") | PrdVal.equalsIgnoreCase("SDV100") | 
		 	  PrdVal.equalsIgnoreCase("SDV150") | PrdVal.equalsIgnoreCase("SDV200") | PrdVal.equalsIgnoreCase("SDV250") | 
		 	  PrdVal.equalsIgnoreCase("SDV300") | PrdVal.equalsIgnoreCase("SDV350") | PrdVal.equalsIgnoreCase("SDV400") |
		 	  PrdVal.equalsIgnoreCase("SDVE100") | 
 		 	  PrdVal.equalsIgnoreCase("SDVE150") | PrdVal.equalsIgnoreCase("SDVE200") | PrdVal.equalsIgnoreCase("SDVE250") | 
 		 	  PrdVal.equalsIgnoreCase("SDVE300") | PrdVal.equalsIgnoreCase("SDVE350") | PrdVal.equalsIgnoreCase("SDVE400") | 
 		 	  PrdVal.equalsIgnoreCase("SDV") | PrdVal.equalsIgnoreCase("SDVE") |
 		 	  PrdVal.equalsIgnoreCase("CTC100") | 
		 	  PrdVal.equalsIgnoreCase("CTC125") | PrdVal.equalsIgnoreCase("CTC160") | PrdVal.equalsIgnoreCase("CTC200") | 
		 	  PrdVal.equalsIgnoreCase("CTC250") | PrdVal.equalsIgnoreCase("CTC315") | PrdVal.equalsIgnoreCase("CTC400") |
		 	  PrdVal.equalsIgnoreCase("CTC500") | PrdVal.equalsIgnoreCase("CTC630") |
		 	  PrdVal.equalsIgnoreCase("CTCDS100") | 
	 	      PrdVal.equalsIgnoreCase("CTCDS125") | PrdVal.equalsIgnoreCase("CTCDS160") | PrdVal.equalsIgnoreCase("CTCDS200") | 
	 	      PrdVal.equalsIgnoreCase("CTCDS250") | PrdVal.equalsIgnoreCase("CTCDS315") | PrdVal.equalsIgnoreCase("CTCDS400") |
	 	      PrdVal.equalsIgnoreCase("CTCDS500") | PrdVal.equalsIgnoreCase("CTCDS630") |
	 	      PrdVal.equalsIgnoreCase("CTC") | PrdVal.equalsIgnoreCase("CTCDS") | 
	 	      PrdVal.equalsIgnoreCase("RS") | PrdVal.equalsIgnoreCase("RM") |
	 	      PrdVal.equalsIgnoreCase("RSDS") | PrdVal.equalsIgnoreCase("RMDS") |
	 	      PrdVal.equalsIgnoreCase("MVCD") | PrdVal.equalsIgnoreCase("SSMVCD") | PrdVal.equalsIgnoreCase("BMSD") |
	 	      PrdVal.equalsIgnoreCase("BMFSD-T") | PrdVal.equalsIgnoreCase("BEMFSD-T") |
	 	      PrdVal.equalsIgnoreCase("BMSD/R") | PrdVal.equalsIgnoreCase("BMFSD/R-T") | PrdVal.equalsIgnoreCase("BEMFSD/R-T") |
	 	      PrdVal.equalsIgnoreCase("BMFD-T") | PrdVal.equalsIgnoreCase("BEMFD-T") |
	 	      PrdVal.equalsIgnoreCase("BMFD/R-T") | PrdVal.equalsIgnoreCase("BEMFD/R-T") |
	 	      PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-F") |
	 	      PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F") |
	 	      PrdVal.equalsIgnoreCase("BMFD-TF") | PrdVal.equalsIgnoreCase("BEMFD-TF") |
	 	      PrdVal.equalsIgnoreCase("BMFD/R-TF") | PrdVal.equalsIgnoreCase("BEMFD/R-TF") | 
	 	      PrdVal.equalsIgnoreCase("AUVCDF") | PrdVal.equalsIgnoreCase("AUVCDB") |
			  PrdVal.equalsIgnoreCase("AUVCDH") | PrdVal.equalsIgnoreCase("AUVCDS") |
			  PrdVal.equalsIgnoreCase("AGVCDF") | PrdVal.equalsIgnoreCase("AGVCDB") | PrdVal.equalsIgnoreCase("AGVCDS") |
			  PrdVal.equalsIgnoreCase("LVCDF") | PrdVal.equalsIgnoreCase("LVCDH") |
			  PrdVal.equalsIgnoreCase("3VCDF") | PrdVal.equalsIgnoreCase("3VCDB") |
			  PrdVal.equalsIgnoreCase("3VCDH") | PrdVal.equalsIgnoreCase("3VCDS") | PrdVal.equalsIgnoreCase("VDR") |
			  PrdVal.equalsIgnoreCase("SDVBP") | PrdVal.equalsIgnoreCase("SDVBPE")| 
			  PrdVal.equalsIgnoreCase("PI3VCD") | PrdVal.equalsIgnoreCase("PIAVCD") | PrdVal.equalsIgnoreCase("MPRD"))
		   {
	 		   	String sqlEbp = "SELECT nvl(mp.ACT_CELL_ADDR,' '),nvl(mp.ACT_QTY_CELL_ADDR,' '),nvl(mp.ACT_COST_CELL_ADDR,' '),nvl(mp.THER_CELL_ADDR,' ')," +
	 		   			"nvl(mp.THER_QTY_CELL_ADDR,' '),nvl(mp.THER_COST_CELL_ADDR,' '),nvl(mp.TRAN_CELL_ADDR,' '),nvl(mp.TRAN_QTY_CELL_ADDR,' ')," +
	 		   			"nvl(mp.TRAN_COST_CELL_ADDR,' '),nvl(cql.BETA_QTN_ACTUATOR_ID,0),nvl(cql.BETA_QTN_THERMOSTAT_ID,0)," +
	 		   			"nvl(cql.BETA_QTN_TRANSFORMER_ID,0), nvl(cql.actqty,0),nvl(mp.HEATER_CELL_ADDR,' '),nvl(mp.HEATER_QTY_CELL_ADDR,' ')," +
	 		   			"nvl(mp.HEATER_COST_CELL_ADDR,' '),nvl(cql.BETA_QTN_HEATER_ID,0)" +
	 		   			" from c_quotationline cql INNER JOIN m_product mp ON cql.m_product_id = mp.m_product_id" +
	 		   			" where cql.c_quotationline_id = ? " ;
	 		   	
	 		   PreparedStatement pstmtEbp = null;
 			   ResultSet rsEbp = null;
 			   try 
 			   {
 			      pstmtEbp = DB.prepareStatement(sqlEbp, "DSPL");
 			      pstmtEbp.setInt(1, record_id);
 			      rsEbp = pstmtEbp.executeQuery();
 			
 			      if(rsEbp.next()) 
 			      {
 			    	  act_cell = rsEbp.getString(1);
 			    	  act_qty_cell=rsEbp.getString(2);
 			    	  act_cost_cell=rsEbp.getString(3);
 			    	  ther_cell = rsEbp.getString(4);
 			    	  ther_qty_cell=rsEbp.getString(5);
 			    	  ther_cost_cell=rsEbp.getString(6);
			    	  tran_cell = rsEbp.getString(7);
 			    	  tran_qty_cell=rsEbp.getString(8);
 			    	  tran_cost_cell=rsEbp.getString(9);
 			    	  act_id=rsEbp.getInt(10);
 			    	  ther_id=rsEbp.getInt(11);
 			    	  tran_id=rsEbp.getInt(12);
 			    	  act_qty=rsEbp.getInt(13);
 			    	  
 			    	  heater_cell = rsEbp.getString(14);
 			    	  heater_qty_cell=rsEbp.getString(15);
 			    	  heater_cost_cell=rsEbp.getString(16);
 			    	  heater_id=rsEbp.getInt(17);
 			      }
 			      rsEbp.close();
 			      pstmtEbp.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sqlEbp, e); } // end of fn_getcelladdr
	 	       finally {try {DB.close(rsEbp, pstmtEbp);} catch (Exception e) {}}
	 	       
	 	       if (act_id !=0)
	 	       {
	 	    	  String sql_act ="SELECT mp.value,act.qty,act.stdcost,act.shortname,act.name,nvl(act.LTSERIESYN,'N') FROM beta_qtn_actuator act INNER JOIN " +
   		   		  "m_product mp ON mp.m_product_id=act.m_product_id WHERE act.beta_qtn_actuator_id=?";
	 	    	  PreparedStatement pstmtact = null;
	 	    	  ResultSet rsact = null;
	 	    	  try 
	 	    	  {
				      pstmtact = DB.prepareStatement(sql_act, "DSPL");
				      pstmtact.setInt(1, act_id);
				      rsact = pstmtact.executeQuery();
				      
				      if(rsact.next()) 
				      {
				    	  act_code = rsact.getString(1);
				    	  if (PrdVal.equalsIgnoreCase("MVCD") | PrdVal.equalsIgnoreCase("SSMVCD") | 
				    		  PrdVal.equalsIgnoreCase("BMFD-T") | PrdVal.equalsIgnoreCase("BEMFD-T") |
							  PrdVal.equalsIgnoreCase("BMFD/R-T") | PrdVal.equalsIgnoreCase("BEMFD/R-T") |
							  PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-F") |
							  PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F")  |
							  PrdVal.equalsIgnoreCase("BMFD-TF") | PrdVal.equalsIgnoreCase("BEMFD-TF") |
							  PrdVal.equalsIgnoreCase("BMFD/R-TF") | PrdVal.equalsIgnoreCase("BEMFD/R-TF") | 
							  PrdVal.equalsIgnoreCase("AUVCDF") | PrdVal.equalsIgnoreCase("AUVCDB") |
							  PrdVal.equalsIgnoreCase("AUVCDH") | PrdVal.equalsIgnoreCase("AUVCDS") |
							  PrdVal.equalsIgnoreCase("AGVCDF") | PrdVal.equalsIgnoreCase("AGVCDB") | PrdVal.equalsIgnoreCase("AGVCDS") |
							  PrdVal.equalsIgnoreCase("LVCDF") | PrdVal.equalsIgnoreCase("LVCDH") |
							  PrdVal.equalsIgnoreCase("3VCDF") | PrdVal.equalsIgnoreCase("3VCDB") |
							  PrdVal.equalsIgnoreCase("3VCDH") | PrdVal.equalsIgnoreCase("3VCDS") | 
							  PrdVal.equalsIgnoreCase("VDR") | PrdVal.equalsIgnoreCase("PI3VCD") | 
							  PrdVal.equalsIgnoreCase("PIAVCD") | PrdVal.equalsIgnoreCase("MPRD"))
				    		  ;
				    	  else
				    		  act_qty = rsact.getInt(2);
				    	  act_cost=rsact.getDouble(3);
				    	  act_sname=rsact.getString(4);
				    	  act_name=rsact.getString(5);
				    	  ltSeriesYN=rsact.getString(6);
				      }
				      rsact.close();
				      pstmtact.close();
	 	    	   }catch(Exception e){log.log(Level.SEVERE, sql_act, e); } // end of fn_getcelladdr
	 	    	   finally {try {DB.close(rsact, pstmtact);} catch (Exception e) {}}
	 	       }
	 	       else
	 	       {
	 	    	   act_code="RACTUATOR-BAE165 US";
	 	    	   act_qty=0;
	 	    	   act_cost=0;
	 	    	   act_name="N";
	 	       }	 
	 	       
	 	       if ((PrdVal.equalsIgnoreCase("MVCD") | PrdVal.equalsIgnoreCase("SSMVCD") | 
			    	PrdVal.equalsIgnoreCase("BMSD") | PrdVal.equalsIgnoreCase("BMFSD-T") |
			    	PrdVal.equalsIgnoreCase("BEMFSD-T") | PrdVal.equalsIgnoreCase("BMSD/R") |
			    	PrdVal.equalsIgnoreCase("BMFSD/R-T") | PrdVal.equalsIgnoreCase("BEMFSD/R-T") |
			    	PrdVal.equalsIgnoreCase("BMFD-T") | PrdVal.equalsIgnoreCase("BEMFD-T") |
			    	PrdVal.equalsIgnoreCase("BMFD/R-T") | PrdVal.equalsIgnoreCase("BEMFD/R-T") |
			    	PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-F") |
			    	PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F") |
			    	PrdVal.equalsIgnoreCase("BMFD-TF") | PrdVal.equalsIgnoreCase("BEMFD-TF") |
			    	PrdVal.equalsIgnoreCase("BMFD/R-TF") | PrdVal.equalsIgnoreCase("BEMFD/R-TF")| 
			    	PrdVal.equalsIgnoreCase("AUVCDF") | PrdVal.equalsIgnoreCase("AUVCDB") |
					PrdVal.equalsIgnoreCase("AUVCDH") | PrdVal.equalsIgnoreCase("AUVCDS") |
					PrdVal.equalsIgnoreCase("AGVCDF") | PrdVal.equalsIgnoreCase("AGVCDB") | PrdVal.equalsIgnoreCase("AGVCDS") |
					PrdVal.equalsIgnoreCase("LVCDF") | PrdVal.equalsIgnoreCase("LVCDH") |
				    PrdVal.equalsIgnoreCase("3VCDF") | PrdVal.equalsIgnoreCase("3VCDB") |
				    PrdVal.equalsIgnoreCase("3VCDH") | PrdVal.equalsIgnoreCase("3VCDS") | 
				    PrdVal.equalsIgnoreCase("VDR") | PrdVal.equalsIgnoreCase("PI3VCD") | PrdVal.equalsIgnoreCase("PIAVCD")) == false)
	 	       {
	 	    	   if (ther_id !=0)
	 	    	   {
	 	    		  String sql_ther ="SELECT mp.value,ther.qty,ther.stdcost,ther.shortname,ther.name FROM beta_qtn_thermostat ther INNER JOIN " +
	   		   		  "m_product mp ON mp.m_product_id=ther.m_product_id WHERE ther.beta_qtn_thermostat_id=?";
		 	    	  PreparedStatement pstmtther = null;
		 	    	  ResultSet rsther = null;
		 	    	  try 
		 	    	  {
		 	    		  pstmtther = DB.prepareStatement(sql_ther, "DSPL");
		 	    		  pstmtther.setInt(1, ther_id);
		 	    		  rsther = pstmtther.executeQuery();
				
					      if(rsther.next()) 
					      {
					    	  ther_code = rsther.getString(1);
					    	  ther_qty = rsther.getInt(2);
					    	  ther_cost=rsther.getDouble(3);
					    	  ther_sname=rsther.getString(4);
					    	  ther_name=rsther.getString(5);
					      }
					      rsther.close();
					      pstmtther.close();
		 	    	   }catch(Exception e){log.log(Level.SEVERE, sql_ther, e); } // end of fn_getcelladdr
		 	    	   finally {try {DB.close(rsther, pstmtther);} catch (Exception e) {}}
	 	    	   }
	 	    	   else
	 	    	   {
	 	    		   ther_code="RTHERMOSTAT";
		 	    	   ther_qty=0;
		 	    	   ther_cost=0;
		 	    	   ther_name="N";
	 	    	   }
	 	    	   
	 	    	   if (tran_id !=0)
	 	    	   {
	 	    		  String sql_tran ="SELECT mp.value,tran.qty,tran.stdcost,tran.shortname,tran.name FROM beta_qtn_transformer tran INNER JOIN " +
	   		   		  "m_product mp ON mp.m_product_id=tran.m_product_id WHERE tran.beta_qtn_transformer_id=?";
		 	    	  PreparedStatement pstmttran = null;
		 	    	  ResultSet rstran = null;
		 	    	  try 
		 	    	  {
		 	    		  pstmttran = DB.prepareStatement(sql_tran, "DSPL");
		 	    		  pstmttran.setInt(1, tran_id);
		 	    		 rstran = pstmttran.executeQuery();
				
					      if(rstran.next()) 
					      {
					    	  tran_code = rstran.getString(1);
					    	  tran_qty = rstran.getInt(2);
					    	  tran_cost=rstran.getDouble(3);
					    	  tran_sname=rstran.getString(4);
					    	  tran_name=rstran.getString(5);
					      }
					      rstran.close();
					      pstmttran.close();
		 	    	   }catch(Exception e){log.log(Level.SEVERE, sql_tran, e); } // end of fn_getcelladdr
		 	    	   finally {try {DB.close(rstran, pstmttran);} catch (Exception e) {}} 
	 	    	    }
	 	    	    else
	 	    	    {
	 	    		   tran_code="RTRANSFORMER";
		 	    	   tran_qty=0;
		 	    	   tran_cost=0;
		 	    	   tran_name="N";
	 	    	    }
	 	    	   
	 	    	    //Heater 
	 	    	    if (PrdVal.equalsIgnoreCase("SDVBPE") | PrdVal.equalsIgnoreCase("SDVE100") | PrdVal.equalsIgnoreCase("SDVE") | 
	 		 	    PrdVal.equalsIgnoreCase("SDVE150") | PrdVal.equalsIgnoreCase("SDVE200") | PrdVal.equalsIgnoreCase("SDVE250") | 
	 		 	    PrdVal.equalsIgnoreCase("SDVE300") | PrdVal.equalsIgnoreCase("SDVE350") | PrdVal.equalsIgnoreCase("SDVE400"))
	 	    	    {
	 	    	    	if (heater_id !=0)
	 	 	    	   {
	 	 	    		  String sql_heater ="SELECT mp.value,he.qty,he.stdcost,he.name FROM beta_qtn_heater he INNER JOIN " +
	 	   		   		  "m_product mp ON mp.m_product_id=he.m_product_id WHERE he.beta_qtn_heater_id=?";
	 		 	    	  PreparedStatement pstmtheater = null;
	 		 	    	  ResultSet rsheater = null;
	 		 	    	  try 
	 		 	    	  {
	 		 	    		  pstmtheater = DB.prepareStatement(sql_heater, "DSPL");
	 		 	    		  pstmtheater.setInt(1, heater_id);
	 		 	    		  rsheater = pstmtheater.executeQuery();
	 				
	 					      if(rsheater.next()) 
	 					      {
	 					    	  heater_code = rsheater.getString(1);
	 					    	  heater_qty = rsheater.getInt(2);
	 					    	  heater_cost=rsheater.getDouble(3);
	 					    	  heater_name=rsheater.getString(4);
	 					      }
	 					      rsheater.close();
	 					     pstmtheater.close();
	 		 	    	   }catch(Exception e){log.log(Level.SEVERE, sql_heater, e); } // end of fn_getcelladdr
	 		 	    	   finally {try {DB.close(rsheater, pstmtheater);} catch (Exception e) {}}
	 	 	    	    }
	 	    	    	else
		 	    	    {
	 	    	    	   heater_code="RHEATER-SDV";
			 	    	   heater_qty=0;
			 	    	   heater_cost=0;
		 	    	    }
	 	    	     }
	 	    	     //End of Heater
	 	    	   
	 	       }  //End For MVCD & SSMVCD Checking
	 	       
	 	       //Get Row & Col for Act Code
	 	      String sql_row_ac = "select fn_getcelladdres(?) from dual ";
	 	      pstmt2 = null;
	 		  rs2 = null;
	 	   	  try // fn to get cell address
	 	   	  {
	 	   		 pstmt2 = DB.prepareStatement(sql_row_ac, null);
	 	   	     pstmt2.setString(1,act_cell);
	 	   	     rs2 = pstmt2.executeQuery();
	 	   		
	 	   	     if (rs2.next())
	 	   	     {
	 	   		     String celladdr = rs2.getString(1);
	 	   		     int length = celladdr.length();
	 	  		
	 	   		     String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 	 		     act_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 		    		    	    			
	 		         String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 		         act_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 		    
	 	   	     } //end of if
	 		     rs2.close();
	 		    pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_ac, e); } // end of fn_getcelladdr
	 		   finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}} 
	 		   
	 		  //Get Row & Col for Act Qty 
	 	      String sql_row_acqty = "select fn_getcelladdres(?) from dual ";
	 	      pstmt2 = null;
	 		  rs2 = null;
	 	   	  try // fn to get cell address
	 	   	  {
	 	   		 pstmt2 = DB.prepareStatement(sql_row_acqty, null);
	 	   	     pstmt2.setString(1,act_qty_cell);
	 	   	     rs2 = pstmt2.executeQuery();
	 	   		
	 	   	     if (rs2.next())
	 	   	     {
	 	   		     String celladdr = rs2.getString(1);
	 	   		     int length = celladdr.length();
	 	  		
	 	   		     String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 	 		     act_qty_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 		    		    	    			
	 		         String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 		         act_qty_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 		    
	 	   	     } //end of if
	 		     rs2.close();
	 		    pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_acqty, e); } // end of fn_getcelladdr
	 		   finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}} 
	 		   
	 		  //Get Row & Col for Act Cost 
	 	      String sql_row_accost = "select fn_getcelladdres(?) from dual ";
	 	      pstmt2 = null;
	 		  rs2 = null;
	 	   	  try // fn to get cell address
	 	   	  {
	 	   		 pstmt2 = DB.prepareStatement(sql_row_accost, null);
	 	   	     pstmt2.setString(1,act_cost_cell);
	 	   	     rs2 = pstmt2.executeQuery();
	 	   		
	 	   	     if (rs2.next())
	 	   	     {
	 	   		     String celladdr = rs2.getString(1);
	 	   		     int length = celladdr.length();
	 	  		
	 	   		     String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
	 	 		     act_cost_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
	 		    		    	    			
	 		         String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
	 		         act_cost_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
	 		    
	 	   	     } //end of if
	 		     rs2.close();
	 		    pstmt2.close();
	 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_accost, e); } // end of fn_getcelladdr
	 		   finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}} 
		 		   
	 		  if ((PrdVal.equalsIgnoreCase("MVCD") | PrdVal.equalsIgnoreCase("SSMVCD") | 
				    	PrdVal.equalsIgnoreCase("BMSD") | PrdVal.equalsIgnoreCase("BMFSD-T") |
				    	PrdVal.equalsIgnoreCase("BEMFSD-T") | PrdVal.equalsIgnoreCase("BMSD/R") |
				    	PrdVal.equalsIgnoreCase("BMFSD/R-T") | PrdVal.equalsIgnoreCase("BEMFSD/R-T") |
				    	PrdVal.equalsIgnoreCase("BMFD-T") | PrdVal.equalsIgnoreCase("BEMFD-T") |
				    	PrdVal.equalsIgnoreCase("BMFD/R-T") | PrdVal.equalsIgnoreCase("BEMFD/R-T") |
				    	PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-F") |
				    	PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F") |
				    	PrdVal.equalsIgnoreCase("BMFD-TF") | PrdVal.equalsIgnoreCase("BEMFD-TF") |
				    	PrdVal.equalsIgnoreCase("BMFD/R-TF") | PrdVal.equalsIgnoreCase("BEMFD/R-TF") | 
				    	PrdVal.equalsIgnoreCase("AUVCDF") | PrdVal.equalsIgnoreCase("AUVCDB") |
						PrdVal.equalsIgnoreCase("AUVCDH") | PrdVal.equalsIgnoreCase("AUVCDS") |
						PrdVal.equalsIgnoreCase("AGVCDF") | PrdVal.equalsIgnoreCase("AGVCDB") | PrdVal.equalsIgnoreCase("AGVCDS") |
						PrdVal.equalsIgnoreCase("LVCDF") | PrdVal.equalsIgnoreCase("LVCDH") |
					    PrdVal.equalsIgnoreCase("3VCDF") | PrdVal.equalsIgnoreCase("3VCDB") |
					    PrdVal.equalsIgnoreCase("3VCDH") | PrdVal.equalsIgnoreCase("3VCDS") | 
					    PrdVal.equalsIgnoreCase("VDR") | PrdVal.equalsIgnoreCase("PI3VCD") | PrdVal.equalsIgnoreCase("PIAVCD")) == false)
	 	      {
		 		  //Get Row & Col for Therm Code
		 	      String sql_row_th = "select fn_getcelladdres(?) from dual ";
		 	      pstmt2 = null;
		 		  rs2 = null;
		 	   	  try // fn to get cell address
		 	   	  {
		 	   		 pstmt2 = DB.prepareStatement(sql_row_th, null);
		 	   	     pstmt2.setString(1,ther_cell);
		 	   	     rs2 = pstmt2.executeQuery();
		 	   		
		 	   	     if (rs2.next())
		 	   	     {
		 	   		     String celladdr = rs2.getString(1);
		 	   		     int length = celladdr.length();
		 	  		
		 	   		     String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
		 	 		     ther_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
		 		    		    	    			
		 		         String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
		 		         ther_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
		 		    
		 	   	     } //end of if
		 		     rs2.close();
		 		    pstmt2.close();
		 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_th, e); } // end of fn_getcelladdr
		 		   finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}} 
		 		   
		 		  //Get Row & Col for Ther Qty 
		 	      String sql_row_thqty = "select fn_getcelladdres(?) from dual ";
		 	      pstmt2 = null;
		 		  rs2 = null;
		 	   	  try // fn to get cell address
		 	   	  {
		 	   		 pstmt2 = DB.prepareStatement(sql_row_thqty, null);
		 	   	     pstmt2.setString(1,ther_qty_cell);
		 	   	     rs2 = pstmt2.executeQuery();
		 	   		
		 	   	     if (rs2.next())
		 	   	     {
		 	   		     String celladdr = rs2.getString(1);
		 	   		     int length = celladdr.length();
		 	  		
		 	   		     String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
		 	 		     ther_qty_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
		 		    		    	    			
		 		         String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
		 		         ther_qty_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
		 		    
		 	   	     } //end of if
		 		     rs2.close();
		 		    pstmt2.close();
		 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_thqty, e); } // end of fn_getcelladdr
		 		   finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}} 
		 		   
		 		  //Get Row & Col for Ther Cost 
		 	      String sql_row_thcost = "select fn_getcelladdres(?) from dual ";
		 	      pstmt2 = null;
		 		  rs2 = null;
		 	   	  try // fn to get cell address
		 	   	  {
		 	   		 pstmt2 = DB.prepareStatement(sql_row_thcost, null);
		 	   	     pstmt2.setString(1,ther_cost_cell);
		 	   	     rs2 = pstmt2.executeQuery();
		 	   		
		 	   	     if (rs2.next())
		 	   	     {
		 	   		     String celladdr = rs2.getString(1);
		 	   		     int length = celladdr.length();
		 	  		
		 	   		     String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
		 	 		     ther_cost_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
		 		    		    	    			
		 		         String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
		 		         ther_cost_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
		 		    
		 	   	     } //end of if
		 		     rs2.close();
		 		    pstmt2.close();
		 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_thcost, e); } // end of fn_getcelladdr
		 		   finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}} 
		 		   
		 		  //Get Row & Col for Trans Code
		 	      String sql_row_tr = "select fn_getcelladdres(?) from dual ";
		 	      pstmt2 = null;
		 		  rs2 = null;
		 	   	  try // fn to get cell address
		 	   	  {
		 	   		 pstmt2 = DB.prepareStatement(sql_row_tr, null);
		 	   	     pstmt2.setString(1,tran_cell);
		 	   	     rs2 = pstmt2.executeQuery();
		 	   		
		 	   	     if (rs2.next())
		 	   	     {
		 	   		     String celladdr = rs2.getString(1);
		 	   		     int length = celladdr.length();
		 	  		
		 	   		     String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
		 	 		     tran_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
		 		    		    	    			
		 		         String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
		 		         tran_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
		 		    
		 	   	     } //end of if
		 		     rs2.close();
		 		    pstmt2.close();
		 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_tr, e); } // end of fn_getcelladdr
		 		   finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}} 
			 		   
		 		  //Get Row & Col for Tran Qty 
		 	      String sql_row_trqty = "select fn_getcelladdres(?) from dual ";
		 	      pstmt2 = null;
		 		  rs2 = null;
		 	   	  try // fn to get cell address
		 	   	  {
		 	   		 pstmt2 = DB.prepareStatement(sql_row_trqty, null);
		 	   	     pstmt2.setString(1,tran_qty_cell);
		 	   	     rs2 = pstmt2.executeQuery();
		 	   		
		 	   	     if (rs2.next())
		 	   	     {
		 	   		     String celladdr = rs2.getString(1);
		 	   		     int length = celladdr.length();
		 	  		
		 	   		     String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
		 	 		     tran_qty_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
		 		    		    	    			
		 		         String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
		 		         tran_qty_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
		 		    
		 	   	     } //end of if
		 		     rs2.close();
		 		    pstmt2.close();
		 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_trqty, e); } // end of fn_getcelladdr
		 		   finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}} 
			 		   
		 		  //Get Row & Col for Tran Cost 
		 	      String sql_row_trcost = "select fn_getcelladdres(?) from dual ";
		 	      pstmt2 = null;
		 		  rs2 = null;
		 	   	  try // fn to get cell address
		 	   	  {
		 	   		 pstmt2 = DB.prepareStatement(sql_row_trcost, null);
		 	   	     pstmt2.setString(1,tran_cost_cell);
		 	   	     rs2 = pstmt2.executeQuery();
		 	   		
		 	   	     if (rs2.next())
		 	   	     {
		 	   		     String celladdr = rs2.getString(1);
		 	   		     int length = celladdr.length();
		 	  		
		 	   		     String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
		 	 		     tran_cost_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
		 		    		    	    			
		 		         String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
		 		         tran_cost_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
		 		    
		 	   	     } //end of if
		 		     rs2.close();
		 		    pstmt2.close();
		 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_trcost, e); } // end of fn_getcelladdr
		 		   finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}} 
		 		   
		 		   //Heater
		 		  if (PrdVal.equalsIgnoreCase("SDVBPE") | PrdVal.equalsIgnoreCase("SDVE100") | PrdVal.equalsIgnoreCase("SDVE") | 
 		 	      PrdVal.equalsIgnoreCase("SDVE150") | PrdVal.equalsIgnoreCase("SDVE200") | PrdVal.equalsIgnoreCase("SDVE250") | 
 		 	      PrdVal.equalsIgnoreCase("SDVE300") | PrdVal.equalsIgnoreCase("SDVE350") | PrdVal.equalsIgnoreCase("SDVE400"))
 	    	      {
		 		   	  //Get Row & Col for Heater Code
			 	      String sql_row_he = "select fn_getcelladdres(?) from dual ";
			 	      pstmt2 = null;
			 		  rs2 = null;
			 	   	  try // fn to get cell address
			 	   	  {
			 	   		 pstmt2 = DB.prepareStatement(sql_row_he, null);
			 	   	     pstmt2.setString(1,heater_cell);
			 	   	     rs2 = pstmt2.executeQuery();
			 	   		
			 	   	     if (rs2.next())
			 	   	     {
			 	   		     String celladdr = rs2.getString(1);
			 	   		     int length = celladdr.length();
			 	  		
			 	   		     String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
			 	 		     heater_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
			 		    		    	    			
			 		         String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
			 		         heater_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
			 		    
			 	   	     } //end of if
			 		     rs2.close();
			 		     pstmt2.close();
			 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_he, e); } // end of fn_getcelladdr
			 		   finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}} 
				 		   
			 		  //Get Row & Col for Heater Qty 
			 	      String sql_row_heqty = "select fn_getcelladdres(?) from dual ";
			 	      pstmt2 = null;
			 		  rs2 = null;
			 	   	  try // fn to get cell address
			 	   	  {
			 	   		 pstmt2 = DB.prepareStatement(sql_row_heqty, null);
			 	   	     pstmt2.setString(1,heater_qty_cell);
			 	   	     rs2 = pstmt2.executeQuery();
			 	   		
			 	   	     if (rs2.next())
			 	   	     {
			 	   		     String celladdr = rs2.getString(1);
			 	   		     int length = celladdr.length();
			 	  		
			 	   		     String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
			 	   		     heater_qty_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
			 		    		    	    			
			 		         String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
			 		         heater_qty_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
			 		    
			 	   	     } //end of if
			 		     rs2.close();
			 		    pstmt2.close();
			 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_heqty, e); } // end of fn_getcelladdr
			 		   finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}} 
				 		   
			 		  //Get Row & Col for Heater Cost 
			 	      String sql_row_hecost = "select fn_getcelladdres(?) from dual ";
			 	      pstmt2 = null;
			 		  rs2 = null;
			 	   	  try // fn to get cell address
			 	   	  {
			 	   		 pstmt2 = DB.prepareStatement(sql_row_hecost, null);
			 	   	     pstmt2.setString(1,heater_cost_cell);
			 	   	     rs2 = pstmt2.executeQuery();
			 	   		
			 	   	     if (rs2.next())
			 	   	     {
			 	   		     String celladdr = rs2.getString(1);
			 	   		     int length = celladdr.length();
			 	  		
			 	   		     String s1 = " select to_number(substr(?,1,instr(?,',')-1)) from dual";
			 	   		     heater_cost_r = DB.getSQLValue(get_TrxName(), s1, celladdr,celladdr);
			 		    		    	    			
			 		         String s2 = " select to_number(substr(?,instr(?,',')+1,"+length+")) from dual";
			 		         heater_cost_c = DB.getSQLValue(get_TrxName(), s2, celladdr,celladdr);
			 		    
			 	   	     } //end of if
			 		     rs2.close();
			 		    pstmt2.close();
			 		   }catch(Exception e){log.log(Level.SEVERE, sql_row_hecost, e); } // end of fn_getcelladdr
			 		   finally {try {DB.close(rs2, pstmt2);} catch (Exception e) {}}
 	    	      }
		 		  //End of Heater
			 		   
	 	      } //End Checking for MVCD & SSMVCD
	 		   
	 	       //Set Values into Excel Sheet
	 		   
	 		   //Set Actuator Code
	 	       HSSFRow rowAct = sheet.getRow(act_r); 
			   HSSFCell attr_costAct = rowAct.getCell(act_c);
			   attr_costAct.setCellValue(act_code);
			   
			   //Set Actuator Qty
	 	       HSSFRow rowActqty = sheet.getRow(act_qty_r); 
			   HSSFCell attr_costActqty = rowActqty.getCell(act_qty_c);
			   attr_costActqty.setCellValue(act_qty);
			   
			   //Set Actuator Cost
	 	       HSSFRow rowActcost = sheet.getRow(act_cost_r); 
			   HSSFCell attr_costActcost = rowActcost.getCell(act_cost_c);
			   attr_costActcost.setCellValue(act_cost);
			   
			   //For making seperate price factor for Actuator & Other Raw materials for UL 
			   if (PrdVal.equalsIgnoreCase("BMSD") | PrdVal.equalsIgnoreCase("BMFSD-T") |
		    	PrdVal.equalsIgnoreCase("BEMFSD-T") | PrdVal.equalsIgnoreCase("BMSD/R") |
		    	PrdVal.equalsIgnoreCase("BMFSD/R-T") | PrdVal.equalsIgnoreCase("BEMFSD/R-T") |
		    	PrdVal.equalsIgnoreCase("BMFD-T") | PrdVal.equalsIgnoreCase("BEMFD-T") |
		    	PrdVal.equalsIgnoreCase("BMFD/R-T") | PrdVal.equalsIgnoreCase("BEMFD/R-T") |
		    	PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-F") |
		    	PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F") |
		    	PrdVal.equalsIgnoreCase("BMFD-TF") | PrdVal.equalsIgnoreCase("BEMFD-TF") |
		    	PrdVal.equalsIgnoreCase("BMFD/R-TF") | PrdVal.equalsIgnoreCase("BEMFD/R-TF") |
		    	PrdVal.equalsIgnoreCase("3VCDB") | PrdVal.equalsIgnoreCase("3VCDF") |  //added Motorized VCD series here on 16.03.2020
		    	PrdVal.equalsIgnoreCase("3VCDS") | PrdVal.equalsIgnoreCase("3VCDH") |
		    	PrdVal.equalsIgnoreCase("AUVCDF") | PrdVal.equalsIgnoreCase("AUVCDB") |
				PrdVal.equalsIgnoreCase("AUVCDH") | PrdVal.equalsIgnoreCase("AUVCDS") |
				PrdVal.equalsIgnoreCase("AGVCDF") | PrdVal.equalsIgnoreCase("AGVCDB") | PrdVal.equalsIgnoreCase("AGVCDS") |
				PrdVal.equalsIgnoreCase("LVCDF") | PrdVal.equalsIgnoreCase("LVCDH") |
				PrdVal.equalsIgnoreCase("PI3VCD") | PrdVal.equalsIgnoreCase("PIAVCD") |
				PrdVal.equalsIgnoreCase("VDR") |
				//added VAV models also here on 28.03.2021
				Prod_Value.equalsIgnoreCase("CTC100") | Prod_Value.equalsIgnoreCase("CTC125") |
			    Prod_Value.equalsIgnoreCase("CTC160") | Prod_Value.equalsIgnoreCase("CTC200") |
			    Prod_Value.equalsIgnoreCase("CTC250") | Prod_Value.equalsIgnoreCase("CTC315") |
			    Prod_Value.equalsIgnoreCase("CTC400") | Prod_Value.equalsIgnoreCase("CTC500") | 
			    Prod_Value.equalsIgnoreCase("CTC630") | Prod_Value.equalsIgnoreCase("CTCDS100") |
			    Prod_Value.equalsIgnoreCase("CTCDS125") | Prod_Value.equalsIgnoreCase("CTCDS160") |
			    Prod_Value.equalsIgnoreCase("CTCDS200") | Prod_Value.equalsIgnoreCase("CTCDS250") |
			    Prod_Value.equalsIgnoreCase("CTCDS315") | Prod_Value.equalsIgnoreCase("CTCDS400") |
			    Prod_Value.equalsIgnoreCase("CTCDS500") | Prod_Value.equalsIgnoreCase("CTCDS630") |
			    Prod_Value.equalsIgnoreCase("EBP1") | Prod_Value.equalsIgnoreCase("EBP2") | 
			    Prod_Value.equalsIgnoreCase("EBP3") | Prod_Value.equalsIgnoreCase("EBP4") |
			    Prod_Value.equalsIgnoreCase("EBP5") | Prod_Value.equalsIgnoreCase("EBP6") |
			    Prod_Value.equalsIgnoreCase("EBP7") | Prod_Value.equalsIgnoreCase("EBP8") | 
			    Prod_Value.equalsIgnoreCase("RM") | Prod_Value.equalsIgnoreCase("RMDS") |
			    Prod_Value.equalsIgnoreCase("RS") | Prod_Value.equalsIgnoreCase("RSDS") |
			    Prod_Value.equalsIgnoreCase("SDV100") | Prod_Value.equalsIgnoreCase("SDV150") | 
			    Prod_Value.equalsIgnoreCase("SDV200") | Prod_Value.equalsIgnoreCase("SDV250") |
			    Prod_Value.equalsIgnoreCase("SDV300") | Prod_Value.equalsIgnoreCase("SDV350") |
			    Prod_Value.equalsIgnoreCase("SDV400") | Prod_Value.equalsIgnoreCase("SDVBP") | 
			    Prod_Value.equalsIgnoreCase("SDVBPE") | Prod_Value.equalsIgnoreCase("SDVE100") |
			    Prod_Value.equalsIgnoreCase("SDVE150") | Prod_Value.equalsIgnoreCase("SDVE200") |
			    Prod_Value.equalsIgnoreCase("SDVE250") | Prod_Value.equalsIgnoreCase("SDVE300") |
			    Prod_Value.equalsIgnoreCase("SDVE350") | Prod_Value.equalsIgnoreCase("SDVE400") |
			    Prod_Value.equalsIgnoreCase("SDV") | Prod_Value.equalsIgnoreCase("SDVE") |
			    Prod_Value.equalsIgnoreCase("CTC") | Prod_Value.equalsIgnoreCase("CTCDS") |
			    Prod_Value.equalsIgnoreCase("MPRD"))
			
			   {
				   ul_act_totcost =  act_cost*act_qty;
			   }
			   // End For making seperate price factor for Actuator & Other Raw materials for UL 
			   
			   if ((PrdVal.equalsIgnoreCase("MVCD") | PrdVal.equalsIgnoreCase("SSMVCD") | 
				    	PrdVal.equalsIgnoreCase("BMSD") | PrdVal.equalsIgnoreCase("BMFSD-T") |
				    	PrdVal.equalsIgnoreCase("BEMFSD-T") | PrdVal.equalsIgnoreCase("BMSD/R") |
				    	PrdVal.equalsIgnoreCase("BMFSD/R-T") | PrdVal.equalsIgnoreCase("BEMFSD/R-T") |
				    	PrdVal.equalsIgnoreCase("BMFD-T") | PrdVal.equalsIgnoreCase("BEMFD-T") |
				    	PrdVal.equalsIgnoreCase("BMFD/R-T") | PrdVal.equalsIgnoreCase("BEMFD/R-T") |
				    	PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-F") |
				    	PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F") |
				    	PrdVal.equalsIgnoreCase("BMFD-TF") | PrdVal.equalsIgnoreCase("BEMFD-TF") |
				    	PrdVal.equalsIgnoreCase("BMFD/R-TF") | PrdVal.equalsIgnoreCase("BEMFD/R-TF") | 
				    	PrdVal.equalsIgnoreCase("AUVCDF") | PrdVal.equalsIgnoreCase("AUVCDB") |
						PrdVal.equalsIgnoreCase("AUVCDH") | PrdVal.equalsIgnoreCase("AUVCDS") |
						PrdVal.equalsIgnoreCase("AGVCDF") | PrdVal.equalsIgnoreCase("AGVCDB") | PrdVal.equalsIgnoreCase("AGVCDS") |
						PrdVal.equalsIgnoreCase("LVCDF") | PrdVal.equalsIgnoreCase("LVCDH") |
					    PrdVal.equalsIgnoreCase("3VCDF") | PrdVal.equalsIgnoreCase("3VCDB") |
					    PrdVal.equalsIgnoreCase("3VCDH") | PrdVal.equalsIgnoreCase("3VCDS") | 
					    PrdVal.equalsIgnoreCase("VDR") | PrdVal.equalsIgnoreCase("PI3VCD") | 
					    PrdVal.equalsIgnoreCase("PIAVCD") | PrdVal.equalsIgnoreCase("MPRD")) == false)
	 	       {
				   //Set Thermostat Code
		 	       HSSFRow rowther = sheet.getRow(ther_r); 
				   HSSFCell attr_costther = rowther.getCell(ther_c);
				   attr_costther.setCellValue(ther_code);
				   
				   //Set Thermostat Qty
		 	       HSSFRow rowTherqty = sheet.getRow(ther_qty_r); 
				   HSSFCell attr_costTherqty = rowTherqty.getCell(ther_qty_c);
				   attr_costTherqty.setCellValue(ther_qty);
				   
				   //Set Thermostat Cost
		 	       HSSFRow rowThercost = sheet.getRow(ther_cost_r); 
				   HSSFCell attr_costThercost = rowThercost.getCell(ther_cost_c);
				   attr_costThercost.setCellValue(ther_cost);
				   
				   //Consider thermostat cost separate in pricing with sp.factor for elect.parts (11.02.22)
				   therm_totcost=ther_cost*ther_qty;
				   //End of Consider thermostat cost separate in pricing with sp.factor for elect.parts
				   
				   //Set Transformer Code
		 	       HSSFRow rowtran = sheet.getRow(tran_r); 
				   HSSFCell attr_costtran = rowtran.getCell(tran_c);
				   attr_costtran.setCellValue(tran_code);
				   
				   //Set Transformer Qty
		 	       HSSFRow rowTranqty = sheet.getRow(tran_qty_r); 
				   HSSFCell attr_costTranqty = rowTranqty.getCell(tran_qty_c);
				   attr_costTranqty.setCellValue(tran_qty);
				   
				   //Set Transformer Cost
		 	       HSSFRow rowTrancost = sheet.getRow(tran_cost_r); 
				   HSSFCell attr_costTrancost = rowTrancost.getCell(tran_cost_c);
				   attr_costTrancost.setCellValue(tran_cost);
				   
				   //Consider transformer cost separate in pricing with sp.factor for elect.parts (11.02.22)
				   trans_totcost=tran_cost*tran_qty;
				   //End of Consider transformer cost separate in pricing with sp.factor for elect.parts
				   
				   if (PrdVal.equalsIgnoreCase("SDVBPE") | PrdVal.equalsIgnoreCase("SDVE100") | PrdVal.equalsIgnoreCase("SDVE") |
			 		   PrdVal.equalsIgnoreCase("SDVE150") | PrdVal.equalsIgnoreCase("SDVE200") | PrdVal.equalsIgnoreCase("SDVE250") | 
			 		   PrdVal.equalsIgnoreCase("SDVE300") | PrdVal.equalsIgnoreCase("SDVE350") | PrdVal.equalsIgnoreCase("SDVE400"))
			 	   {
					   //Set Heater Code
			 	       HSSFRow rowheater = sheet.getRow(heater_r); 
					   HSSFCell attr_costheater = rowheater.getCell(heater_c);
					   attr_costheater.setCellValue(heater_code);
					   
					   //Set Heater Qty
			 	       HSSFRow rowheaterqty = sheet.getRow(heater_qty_r); 
					   HSSFCell attr_costheaterqty = rowheaterqty.getCell(heater_qty_c);
					   attr_costheaterqty.setCellValue(heater_qty);
					   
					   //Set Heater Cost
			 	       HSSFRow rowheatercost = sheet.getRow(heater_cost_r); 
					   HSSFCell attr_costheatercost = rowheatercost.getCell(heater_cost_c);
					   attr_costheatercost.setCellValue(heater_cost);
					   
					   //Consider heater cost separate in pricing with sp.factor for elect.parts (11.02.22)
					   heat_totcost=heater_cost*heater_qty;
					   //End of Consider heater cost separate in pricing with sp.factor for elect.parts
					   
			 	   }
				   
	 	       }  //End Checking for MVCD & SSMVCD
			   
		   }
	       
	 	   //END For Non Parameter Items to Transfer Actuator,Thermostat & Transformer
	 	   
	       if (r1 != 0 && c1 != 0) 
		   {
				HSSFRow row1 = sheet.getRow(r1); //Row
			    HSSFCell attr_cost1 = row1.getCell(c1); //Column
			    Double x = new Double(a);
			    //System.out.println(" a cell value :" + x);
			    a_value = 0;
			    if (PrdVal.equalsIgnoreCase("SLD") | PrdVal.equalsIgnoreCase("RLD") | PrdVal.equalsIgnoreCase("SLD/RLD") |
			    	PrdVal.equalsIgnoreCase("RLDF") | PrdVal.equalsIgnoreCase("RLD45") | PrdVal.equalsIgnoreCase("RLD-CRV") |
			    	PrdVal.equalsIgnoreCase("SLDF") | PrdVal.equalsIgnoreCase("SLD45") | PrdVal.equalsIgnoreCase("SLDF/RLDF") | 
			    	PrdVal.equalsIgnoreCase("SLD45/RLD45") | PrdVal.equalsIgnoreCase("JZ-SLD") |
			    	PrdVal.equalsIgnoreCase("RLD2F") | PrdVal.equalsIgnoreCase("RLD45-20") |
			    	PrdVal.equalsIgnoreCase("SLD2F") | PrdVal.equalsIgnoreCase("SLD45-20") |
			    	PrdVal.equalsIgnoreCase("SLD2F/RLD2F") | PrdVal.equalsIgnoreCase("SLD45-20/RLD45-20"))
			    {
			    	if (unit.equalsIgnoreCase("I"))
			    		a_value = x / 40;
			    	else if (unit.equalsIgnoreCase("F"))
			    		a_value = x / 3.330;
			    	else if (unit.equalsIgnoreCase("N"))
			    		a_value = x;	
			    	else 
			    		a_value = x;
			    }
			    else if (PrdVal.equalsIgnoreCase("RLG16") | PrdVal.equalsIgnoreCase("RLRM16") | PrdVal.equalsIgnoreCase("RLRB16") | 
			    		PrdVal.equalsIgnoreCase("RLRB16/RLG16") | PrdVal.equalsIgnoreCase("RLRM16/RLG16") | PrdVal.equalsIgnoreCase("SLG16") |
			    		PrdVal.equalsIgnoreCase("SLRM16") | PrdVal.equalsIgnoreCase("SLRB16") | PrdVal.equalsIgnoreCase("SLRB16/SLG16") | 
			    		PrdVal.equalsIgnoreCase("SLRM16/SLG16") | PrdVal.equalsIgnoreCase("RLG12") | PrdVal.equalsIgnoreCase("RLRM12") | 
			    		PrdVal.equalsIgnoreCase("RLRB12") | PrdVal.equalsIgnoreCase("RLRB12/RLG12") | PrdVal.equalsIgnoreCase("RLRM12/RLG12") | 
			    		PrdVal.equalsIgnoreCase("SLG12") | PrdVal.equalsIgnoreCase("SLRM12") | PrdVal.equalsIgnoreCase("SLRB12") | 
					    PrdVal.equalsIgnoreCase("SLRB12/SLG12") | PrdVal.equalsIgnoreCase("SLRM12/SLG12") 
					    | PrdVal.equalsIgnoreCase("RLRB12-20/RLG12-20") |  PrdVal.equalsIgnoreCase("RLRB16-20/RLG16-20") |
					    PrdVal.equalsIgnoreCase("RLRM12-20/RLG12-20") |  PrdVal.equalsIgnoreCase("RLRM16-20/RLG16-20") |
					    PrdVal.equalsIgnoreCase("SLRM12-12.5/SLG12-12.5") | PrdVal.equalsIgnoreCase("SLRM12-20/SLG12-20") |
					    PrdVal.equalsIgnoreCase("SLRM16-12.5/SLG16-12.5") | PrdVal.equalsIgnoreCase("SLRM16-20/SLG16-20") |
					    PrdVal.equalsIgnoreCase("SLRB12-12.5/SLG12-12.5") | PrdVal.equalsIgnoreCase("SLRB12-20/SLG12-20") |
					    PrdVal.equalsIgnoreCase("SLRB16-12.5/SLG16-12.5") | PrdVal.equalsIgnoreCase("SLRB16-20/SLG16-20") |
					    
					    PrdVal.equalsIgnoreCase("RLG12-20") | PrdVal.equalsIgnoreCase("RLG16-20") | PrdVal.equalsIgnoreCase("SLG12-12.5") |
					    PrdVal.equalsIgnoreCase("SLG12-20") | PrdVal.equalsIgnoreCase("SLG16-12.5") | PrdVal.equalsIgnoreCase("SLG16-20") | 
					    PrdVal.equalsIgnoreCase("RLRB12-20") | PrdVal.equalsIgnoreCase("RLRB16-20") | PrdVal.equalsIgnoreCase("RLRM12-20") | 
					    PrdVal.equalsIgnoreCase("RLRM16-20") | PrdVal.equalsIgnoreCase("SLRB12-12.5") | PrdVal.equalsIgnoreCase("SLRB12-20") | 
					    PrdVal.equalsIgnoreCase("SLRB16-12.5") | PrdVal.equalsIgnoreCase("SLRB16-20") | PrdVal.equalsIgnoreCase("SLRM12-12.5") | 
					    PrdVal.equalsIgnoreCase("SLRM12-20") | PrdVal.equalsIgnoreCase("SLRM16-12.5") | PrdVal.equalsIgnoreCase("SLRM16-20") |
					    PrdVal.equalsIgnoreCase("JZ-RLG16-S") | PrdVal.equalsIgnoreCase("JZ-RLG16-D")) 
			    {
			    	if (unit.equalsIgnoreCase("I"))
			    		a_value = x * 25;
			    	else if (unit.equalsIgnoreCase("F"))
			    		a_value = x * 12 * 25;
			    	else if (unit.equalsIgnoreCase("N"))
			    		a_value = x * 1000;	
			    	else 
			    		a_value = x;
			    }
			    else if (PrdVal.equalsIgnoreCase("BMSD") | PrdVal.equalsIgnoreCase("BMFSD-T") |
			    		 PrdVal.equalsIgnoreCase("BEMFSD-T") | PrdVal.equalsIgnoreCase("BMSD/R") |
			    		 PrdVal.equalsIgnoreCase("BMFSD/R-T") | PrdVal.equalsIgnoreCase("BEMFSD/R-T") |
			    		 PrdVal.equalsIgnoreCase("BMSD-NEW") | PrdVal.equalsIgnoreCase("BMFSD-NEW") |
			    		 PrdVal.equalsIgnoreCase("BEMFSD-NEW"))
			    {
			    	if (unit.equalsIgnoreCase("I"))
			    	{
			    		a_value = x * 25.4;
			    		//a_value = Math.round(a_value); (Commented as per the discussion with Mr. Shankar, as per the request from Farooq, Dtd:07.06.24)
			    	}
			    	else 
			    		a_value = x;
			    }
			    else if (PrdVal.equalsIgnoreCase("BMFD-T") | PrdVal.equalsIgnoreCase("BEMFD-T") | 
			    		PrdVal.equalsIgnoreCase("BMFD/R-T") | PrdVal.equalsIgnoreCase("BEMFD/R-T") |
			    		PrdVal.equalsIgnoreCase("BFD-F") | PrdVal.equalsIgnoreCase("BEFD-F") |
			    		PrdVal.equalsIgnoreCase("BFD/R-F") | PrdVal.equalsIgnoreCase("BEFD/R-F") |
			    		PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-F") |
			    		PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F") |
			    		PrdVal.equalsIgnoreCase("BMFD-TF") | PrdVal.equalsIgnoreCase("BEMFD-TF") |
			    		PrdVal.equalsIgnoreCase("BMFD/R-TF") | PrdVal.equalsIgnoreCase("BEMFD/R-TF") |
			    		PrdVal.equalsIgnoreCase("BMFD-NEW") | PrdVal.equalsIgnoreCase("BEMFD-NEW"))
			    {
			    	if (unit.equalsIgnoreCase("I"))
			    	{
			    		if (x<8)
			    			x = new Double(8);
			    		
			    		a_value = x * 25.4;
			    		a_value = Math.round(a_value);
			    	}
			    	else
			    	{
			    		if (x<203)
			    			x=new Double(203);
			    		a_value = x;
			    	}
			    }
			    else if (PrdVal.equalsIgnoreCase("CJNP200") | PrdVal.equalsIgnoreCase("CJNP250") |
			    		 PrdVal.equalsIgnoreCase("CJNP300") | PrdVal.equalsIgnoreCase("CJNP350") |
			    		 PrdVal.equalsIgnoreCase("CJNP400") | PrdVal.equalsIgnoreCase("BJNP125") |
						 PrdVal.equalsIgnoreCase("BJNP160") | PrdVal.equalsIgnoreCase("BJNP200") |
						 PrdVal.equalsIgnoreCase("BJNP250") | PrdVal.equalsIgnoreCase("BJNP315") |
						 PrdVal.equalsIgnoreCase("BJNP400"))
			    {
			    	a_value = cjnpPole;
			    }
			    else if (lvByArea.equalsIgnoreCase("Y"))  //For Items with Louvers By Area=Y
			    	a_value = x * 1000;
			    else
			    {
			    	if (unit.equalsIgnoreCase("I"))
			    		a_value = x * new Double(inch);
			    	else
			    		a_value = x;
			    }
			    
			    //Add Q for FAN CASING A VALUE
			    if (PrdVal.equalsIgnoreCase("FAN-VORTICE"))
			    {
			    	if (ca_model_val.equalsIgnoreCase("Q"))
			    	{
			    		String a_casing = a_disp.intValue() + ca_model_val.trim();
			    		attr_cost1.setCellValue(a_casing);
			    	}
			    	else
			    		attr_cost1.setCellValue(a_value);
			    }
			    else
			    	attr_cost1.setCellValue(a_value);
		
		     }
			/** for B */
	        if (r2 != 0 && c2 != 0)
			{
				HSSFRow row11 = sheet.getRow(r2); //Row
			    HSSFCell attr_cost2 = row11.getCell(c2); //Column
			    //System.out.println(" b cell value :" + b);
			    b_value = 0;
			 // set value in XL file for i/p parameter
			    if (PrdVal.equalsIgnoreCase("SLD") | PrdVal.equalsIgnoreCase("RLD") | PrdVal.equalsIgnoreCase("SLD/RLD")  |
				    PrdVal.equalsIgnoreCase("RLDF") | PrdVal.equalsIgnoreCase("RLD45") | PrdVal.equalsIgnoreCase("RLD-CRV") |
				    PrdVal.equalsIgnoreCase("SLDF") | PrdVal.equalsIgnoreCase("SLD45") | PrdVal.equalsIgnoreCase("SLDF/RLDF") | 
				    PrdVal.equalsIgnoreCase("SLD45/RLD45") | PrdVal.equalsIgnoreCase("JZ-SLD") |
				    PrdVal.equalsIgnoreCase("RLD2F") | PrdVal.equalsIgnoreCase("RLD45-20") |
			    	PrdVal.equalsIgnoreCase("SLD2F") | PrdVal.equalsIgnoreCase("SLD45-20") |
			    	PrdVal.equalsIgnoreCase("SLD2F/RLD2F") | PrdVal.equalsIgnoreCase("SLD45-20/RLD45-20"))
			    {
			    	b_value = b;
			    }
			    else if (PrdVal.equalsIgnoreCase("RLG16") | PrdVal.equalsIgnoreCase("RLRM16") | PrdVal.equalsIgnoreCase("RLRB16") | 
			    		PrdVal.equalsIgnoreCase("RLRB16/RLG16") | PrdVal.equalsIgnoreCase("RLRM16/RLG16") | PrdVal.equalsIgnoreCase("SLG16") |
			    		PrdVal.equalsIgnoreCase("SLRM16") | PrdVal.equalsIgnoreCase("SLRB16") | PrdVal.equalsIgnoreCase("SLRB16/SLG16") | 
			    		PrdVal.equalsIgnoreCase("SLRM16/SLG16") | PrdVal.equalsIgnoreCase("RLG12") | PrdVal.equalsIgnoreCase("RLRM12") | 
			    		PrdVal.equalsIgnoreCase("RLRB12") | PrdVal.equalsIgnoreCase("RLRB12/RLG12") | PrdVal.equalsIgnoreCase("RLRM12/RLG12") | 
			    		PrdVal.equalsIgnoreCase("SLG12") | PrdVal.equalsIgnoreCase("SLRM12") | PrdVal.equalsIgnoreCase("SLRB12") | 
					    PrdVal.equalsIgnoreCase("SLRB12/SLG12") | PrdVal.equalsIgnoreCase("SLRM12/SLG12") |
					    PrdVal.equalsIgnoreCase("SLRB12-12.5/SLG12-12.5") | PrdVal.equalsIgnoreCase("SLRB12-20/SLG12-20") |
					    PrdVal.equalsIgnoreCase("SLRB16-12.5/SLG16-12.5") | PrdVal.equalsIgnoreCase("SLRB16-20/SLG16-20") |
					    PrdVal.equalsIgnoreCase("SLRM12-12.5/SLG12-12.5") | PrdVal.equalsIgnoreCase("SLRM12-20/SLG12-20") |
					    PrdVal.equalsIgnoreCase("SLRM16-12.5/SLG16-12.5") | PrdVal.equalsIgnoreCase("SLRM16-20/SLG16-20") |
					    PrdVal.equalsIgnoreCase("RLRB12-20/RLG12-20") |  PrdVal.equalsIgnoreCase("RLRB16-20/RLG16-20") |
					    PrdVal.equalsIgnoreCase("RLRM12-20/RLG12-20") |  PrdVal.equalsIgnoreCase("RLRM16-20/RLG16-20") |
					    PrdVal.equalsIgnoreCase("RLG12-20") | PrdVal.equalsIgnoreCase("RLG16-20") | PrdVal.equalsIgnoreCase("SLG12-12.5") |
					    PrdVal.equalsIgnoreCase("SLG12-20") | PrdVal.equalsIgnoreCase("SLG16-12.5") | PrdVal.equalsIgnoreCase("SLG16-20") | 
					    PrdVal.equalsIgnoreCase("RLRB12-20") | PrdVal.equalsIgnoreCase("RLRB16-20") | PrdVal.equalsIgnoreCase("RLRM12-20") | 
					    PrdVal.equalsIgnoreCase("RLRM16-20") | PrdVal.equalsIgnoreCase("SLRB12-12.5") | PrdVal.equalsIgnoreCase("SLRB12-20") | 
					    PrdVal.equalsIgnoreCase("SLRB16-12.5") | PrdVal.equalsIgnoreCase("SLRB16-20") | PrdVal.equalsIgnoreCase("SLRM12-12.5") | 
					    PrdVal.equalsIgnoreCase("SLRM12-20") | PrdVal.equalsIgnoreCase("SLRM16-12.5") | PrdVal.equalsIgnoreCase("SLRM16-20") |
					    PrdVal.equalsIgnoreCase("JZ-RLG16-S") | PrdVal.equalsIgnoreCase("JZ-RLG16-D"))
			    {
			    	if (unit.equalsIgnoreCase("I"))
			    		b_value = b * 25;
			    	else if (unit.equalsIgnoreCase("F"))
			    		b_value = b * 25;
			    	else if (unit.equalsIgnoreCase("N"))
			    	{
			    		if (b_unit.equalsIgnoreCase("I"))
			    			b_value = b * 25;
			    		else 
			    			b_value = b;
			    	}
			    	else	
			    		b_value = b;
			    }
			    else if (PrdVal.equalsIgnoreCase("BMSD") | PrdVal.equalsIgnoreCase("BMFSD-T") |
			    		 PrdVal.equalsIgnoreCase("BEMFSD-T") | PrdVal.equalsIgnoreCase("BMSD/R") |
			    		 PrdVal.equalsIgnoreCase("BMFSD/R-T") | PrdVal.equalsIgnoreCase("BEMFSD/R-T") |
			    		 PrdVal.equalsIgnoreCase("BMSD-NEW") | PrdVal.equalsIgnoreCase("BMFSD-NEW") |
			    		 PrdVal.equalsIgnoreCase("BEMFSD-NEW"))
			    {
			    	if (unit.equalsIgnoreCase("I"))
			    	{
			    		b_value = b * 25.4;
			    		//b_value = Math.round(b_value);  (Commented as per the discussion with Mr. Shankar, as per the request from Farooq, Dtd:07.06.24)
			    	}
			    	else 
			    		b_value = b;
			    }
			    else if (PrdVal.equalsIgnoreCase("BMFD-T") | PrdVal.equalsIgnoreCase("BEMFD-T") |
			    		PrdVal.equalsIgnoreCase("BMFD/R-T") | PrdVal.equalsIgnoreCase("BEMFD/R-T") |
			    		PrdVal.equalsIgnoreCase("BFD-F") | PrdVal.equalsIgnoreCase("BEFD-F") | 
			    		PrdVal.equalsIgnoreCase("BFD/R-F") | PrdVal.equalsIgnoreCase("BEFD/R-F") |
			    		PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-F") |
			    		PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F") |
			    		PrdVal.equalsIgnoreCase("BMFD-TF") | PrdVal.equalsIgnoreCase("BEMFD-TF") |
			    		PrdVal.equalsIgnoreCase("BMFD/R-TF") | PrdVal.equalsIgnoreCase("BEMFD/R-TF") |
			    		PrdVal.equalsIgnoreCase("BMFD-NEW") | PrdVal.equalsIgnoreCase("BEMFD-NEW"))
			    {
			    	Double y = new Double(b);
			    	if (unit.equalsIgnoreCase("I"))
			    	{
			    		if (y<8)
			    			y = new Double(8);
			    		
			    		b_value = y * 25.4;
			    		b_value = Math.round(b_value);
			    	}
			    	else
			    	{
			    		if (y<200)
			    			y=new Double(200);
			    		b_value = y;
			    	}
			    }
			    else
			    {
			    	if (unit.equalsIgnoreCase("I"))
			    		b_value = b * new Double(inch);
			    	else
			    		b_value = b;
			    }
			    attr_cost2.setCellValue(b_value);
			}

	        /**SupplyH*/
			if (r3 != 0 && c3 != 0)
		    {
				HSSFRow row12 = sheet.getRow(r3); //Row
			    HSSFCell attr_cost3 = row12.getCell(c3); //Column
			    
			    s_value = 0;
				 // set value in XL file for i/p parameter
			    if (PrdVal.equalsIgnoreCase("SLD/RLD") | PrdVal.equalsIgnoreCase("SLDF/RLDF") | PrdVal.equalsIgnoreCase("SLD45/RLD45") |
				    PrdVal.equalsIgnoreCase("SLD2F/RLD2F") | PrdVal.equalsIgnoreCase("SLD45-20/RLD45-20"))
			    {
			    	if (unit.equalsIgnoreCase("I"))
			    		s_value = supply / 40;
			    	else if (unit.equalsIgnoreCase("F"))
			    		s_value = supply / 3.330;
			    	else if (unit.equalsIgnoreCase("N"))
			    		s_value = supply;	
			    	
			    	attr_cost3.setCellValue(s_value);
			    }
			    else if (PrdVal.equalsIgnoreCase("RLRB16/RLG16") | PrdVal.equalsIgnoreCase("RLRM16/RLG16") |
					     PrdVal.equalsIgnoreCase("SLRB16/SLG16") | PrdVal.equalsIgnoreCase("SLRM16/SLG16") |				     
				    	 PrdVal.equalsIgnoreCase("RLRB12/RLG12") | PrdVal.equalsIgnoreCase("RLRM12/RLG12") | 
				    	 PrdVal.equalsIgnoreCase("RLRM12-20/RLG12-20") |  PrdVal.equalsIgnoreCase("RLRM16-20/RLG16-20") |
				    	 PrdVal.equalsIgnoreCase("RLRB12-20/RLG12-20") |  PrdVal.equalsIgnoreCase("RLRB16-20/RLG16-20") |
						 PrdVal.equalsIgnoreCase("SLRB12/SLG12") | PrdVal.equalsIgnoreCase("SLRM12/SLG12") |
						 PrdVal.equalsIgnoreCase("SLRB12-12.5/SLG12-12.5") | PrdVal.equalsIgnoreCase("SLRB12-20/SLG12-20") |
						    PrdVal.equalsIgnoreCase("SLRB16-12.5/SLG16-12.5") | PrdVal.equalsIgnoreCase("SLRB16-20/SLG16-20") |
						    
						 PrdVal.equalsIgnoreCase("SLRM12-12.5/SLG12-12.5") | PrdVal.equalsIgnoreCase("SLRM12-20/SLG12-20") |
						 PrdVal.equalsIgnoreCase("SLRM16-12.5/SLG16-12.5") | PrdVal.equalsIgnoreCase("SLRM16-20/SLG16-20")
			    )
			    {
			    	if (unit.equalsIgnoreCase("I"))
			    		s_value = supply * 25;
			    	else if (unit.equalsIgnoreCase("F"))
			    		s_value = supply * 12 * 25;
			    	else if (unit.equalsIgnoreCase("N"))
			    		s_value = supply * 1000;	
			    	else 
			    		s_value = supply;
			    	
			    	attr_cost3.setCellValue(s_value);
			    }
			    else
			    {
			    	if (unit.equalsIgnoreCase("I"))
				        attr_cost3.setCellValue(supply * new Double(inch));
				    else
				    	attr_cost3.setCellValue(supply);
			    }
			    
      	     }  
			
			//Set Value Length into Excel
			if (r4 !=0 && c4 !=0 ) 
			{
				HSSFRow row14 = sheet.getRow(r4); //Row
			    HSSFCell attr_cost4 = row14.getCell(c4); //Column
			    
			    if (PrdVal.equalsIgnoreCase("RAD-GNR") |PrdVal.equalsIgnoreCase("SADB-GNR") | PrdVal.equalsIgnoreCase("SADM-GNR") |
			    PrdVal.equalsIgnoreCase("SADB-EG-GNR") | PrdVal.equalsIgnoreCase("SADM-EG-GNR"))
			    {
			    	if (unit.equalsIgnoreCase("I"))
			    		len = len * 25;
			    }
			    else if (PrdVal.equalsIgnoreCase("BMSD") | PrdVal.equalsIgnoreCase("BMFSD-T") |
			  			  PrdVal.equalsIgnoreCase("BEMFSD-T") | PrdVal.equalsIgnoreCase("BMFD-T") | PrdVal.equalsIgnoreCase("BEMFD-T") |
			  			  PrdVal.equalsIgnoreCase("BFD-F") | PrdVal.equalsIgnoreCase("BEFD-F") |
			  			  PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-F") |
			  			  PrdVal.equalsIgnoreCase("BMFD-TF") | PrdVal.equalsIgnoreCase("BEMFD-TF"))
			    {
			    	if (slyn.equalsIgnoreCase("Y"))
			    		len = 1;
			    	else	
			    		len = 0;
			    }
			    attr_cost4.setCellValue(len);
			}
      	    
			//Set Value Airway into Excel
			if (r5 !=0 && c5 !=0 ) 
			{
				HSSFRow row15 = sheet.getRow(r5); //Row
			    HSSFCell attr_cost5 = row15.getCell(c5); //Column
			    //System.out.println(" Airway cell value :" + aw);
			    attr_cost5.setCellValue(aw);
			}
			
			//Set GNA Value into Excel
			if (r6 !=0 && c6 !=0 ) 
			{
				HSSFRow row16 = sheet.getRow(r6); //Row
			    HSSFCell attr_cost6 = row16.getCell(c6); //Column
			    //System.out.println(" GN A cell value :" + gnAVal);
			    if (unit.equalsIgnoreCase("I"))
			    	attr_cost6.setCellValue(gnAVal*25);
			    else
			    	attr_cost6.setCellValue(gnAVal);
			}
			//End of Set GNA Value into Excel
			
			//Set GNB Value into Excel
			if (r7 !=0 && c7 !=0 ) 
			{
				HSSFRow row17 = sheet.getRow(r7); //Row
			    HSSFCell attr_cost7 = row17.getCell(c7); //Column
			    //System.out.println(" GN B cell value :" + gnBVal);
			    if (unit.equalsIgnoreCase("I"))
			    	attr_cost7.setCellValue(gnBVal*25);
			    else
			    	attr_cost7.setCellValue(gnBVal);
			}
			//End of Set GNA Value into Excel
			
			//Set Drive Value into Excel 
			if (r8 !=0 && c8 !=0 ) 
			{
				HSSFRow row18 = sheet.getRow(r8); //Row
			    HSSFCell attr_cost8 = row18.getCell(c8); //Column
			    attr_cost8.setCellValue(driveVal);
			}
			//End of Set Drive Value into Excel
			
			//Set Mechanism Value into Excel  
			if (r9 !=0 && c9 !=0 ) 
			{
				HSSFRow row19 = sheet.getRow(r9); //Row
			    HSSFCell attr_cost9 = row19.getCell(c9); //Column
			    attr_cost9.setCellValue(mechVal);
			}
			//End of Set Mechanism Value into Excel
			
			//Set Bush Value into Excel  
			if (r10 !=0 && c10 !=0 ) 
			{
				HSSFRow row10 = sheet.getRow(r10); //Row
			    HSSFCell attr_cost10 = row10.getCell(c10); //Column
			    attr_cost10.setCellValue(bushVal);
			}
			//End of Set Bush Value into Excel
			
			//Set Metal Value into Excel  
			if (r11 !=0 && c11 !=0 ) 
			{
				HSSFRow row11 = sheet.getRow(r11); //Row
			    HSSFCell attr_cost11 = row11.getCell(c11); //Column
			    if (PrdVal.equalsIgnoreCase("VDR"))
			    	attr_cost11.setCellValue(metalVal_Vdr);
			    else
			    	attr_cost11.setCellValue(metalVal);
			}
			//End of Set Metal Value into Excel
			
			//Set Flange Value into Excel  
			if (r12 !=0 && c12 !=0 ) 
			{
				HSSFRow row12 = sheet.getRow(r12); //Row
			    HSSFCell attr_cost12 = row12.getCell(c12); //Column
		    	attr_cost12.setCellValue(flangeVal);
			}
			//End of Set Flange Value into Excel
			
			//Set Spindle Value into Excel  
			if (r13 !=0 && c13 !=0 ) 
			{
				HSSFRow row13 = sheet.getRow(r13); //Row
			    HSSFCell attr_cost13 = row13.getCell(c13); //Column
		    	attr_cost13.setCellValue(spindleVal);
			}
			//End of Set Spindle Value into Excel
			
			//Set Blade Thickness Value into Excel  (AGVCD)
			if (r14 !=0 && c14 !=0 ) 
			{
				HSSFRow row14 = sheet.getRow(r14); //Row
			    HSSFCell attr_cost14 = row14.getCell(c14); //Column
			    if (bladeThickVal.equalsIgnoreCase("G"))
			    {
			    	if (metalVal.contains("G"))
			    		attr_cost14.setCellValue("G0");
			    	else if (metalVal.contains("S"))
			    		attr_cost14.setCellValue("S0");
			    	else
			    		attr_cost14.setCellValue("N");
			    }
			    else
			    	attr_cost14.setCellValue("N");
			}
			//End of Set Blade Thickness Value into Excel
			
			//CA - Set PC Value into Excel  
			if (r15 !=0 && c15 !=0 ) 
			{
				HSSFRow row15 = sheet.getRow(r15); //Row
			    HSSFCell attr_cost15 = row15.getCell(c15); //Column
		    	attr_cost15.setCellValue(ca_pc_val);
			}
			//CA - End of Set PC Value into Excel
			
			//CA - Set TYPE Value into Excel  
			if (r16 !=0 && c16 !=0 ) 
			{
				HSSFRow row16 = sheet.getRow(r16); //Row
			    HSSFCell attr_cost16 = row16.getCell(c16); //Column
		    	attr_cost16.setCellValue(ca_type_val);
			}
			//CA - End of Set TYPE Value into Excel
			
			//CA - Set AIS Value into Excel  
			if (r17 !=0 && c17 !=0 ) 
			{
				HSSFRow row17 = sheet.getRow(r17); //Row
			    HSSFCell attr_cost17 = row17.getCell(c17); //Column
		    	attr_cost17.setCellValue(ca_ais_val);
			}
			//CA - End of Set AIS Value into Excel
			
			//CA - Set BD Value into Excel  
			if (r18 !=0 && c18 !=0 ) 
			{
				HSSFRow row18 = sheet.getRow(r18); //Row
			    HSSFCell attr_cost18 = row18.getCell(c18); //Column
		    	attr_cost18.setCellValue(ca_bd_val);
			}
			//CA - End of Set BD Value into Excel
			
			//CA - Set METAL Value into Excel  
			if (r19 !=0 && c19 !=0 ) 
			{
				HSSFRow row19 = sheet.getRow(r19); //Row
			    HSSFCell attr_cost19 = row19.getCell(c19); //Column
		    	attr_cost19.setCellValue(ca_met_val);
			}
			//CA - End of Set METAL Value into Excel
			
			//CT - Set COATING Value into Excel  
			if (r20 !=0 && c20 !=0 ) 
			{
				HSSFRow row20 = sheet.getRow(r20); //Row
			    HSSFCell attr_cost20 = row20.getCell(c20); //Column
		    	attr_cost20.setCellValue(ct_cot_val);
			}
			//End of CT - Set COATING Value into Excel
			
			//CT - Set LENGTH Value into Excel  
			if (r21 !=0 && c21 !=0 ) 
			{
				if (PrdVal.equalsIgnoreCase("CT-REDU"))
				{
					HSSFRow row21 = sheet.getRow(r21); //Row
					HSSFCell attr_cost21 = row21.getCell(c21); //Column
					attr_cost21.setCellValue(ct_hand);
				}
				else
				{
					HSSFRow row21 = sheet.getRow(r21); //Row
					HSSFCell attr_cost21 = row21.getCell(c21); //Column
					attr_cost21.setCellValue(ct_len_val);
				}
			}
			//End of CT - Set LENGTH Value into Excel
			
			//PGL - Set Metal Value into Excel  
			if (r22 !=0 && c22 !=0 ) 
			{
				HSSFRow row22 = sheet.getRow(r22); //Row
			    HSSFCell attr_cost22 = row22.getCell(c22); //Column
		    	attr_cost22.setCellValue(pgl_met_val);
			}
			//End of PGL - Set Metal Value into Excel
			
			//PGL - Set FGM Value into Excel  
			if (r23 !=0 && c23 !=0 ) 
			{
				HSSFRow row23 = sheet.getRow(r23); //Row
			    HSSFCell attr_cost23 = row23.getCell(c23); //Column
			    if (pgl_fgm_val.equalsIgnoreCase("Y"))
			    	attr_cost23.setCellValue("F");
			    else
			    	attr_cost23.setCellValue("N");
			}
			//End of PGL - Set Metal Value into Excel
			
			//UL Dampers - Set value for SS Bush
			if (PrdVal.equalsIgnoreCase("BMSD") | PrdVal.equalsIgnoreCase("BMFSD-T") | PrdVal.equalsIgnoreCase("BEMFSD-T") |
				PrdVal.equalsIgnoreCase("BMSD/R") | PrdVal.equalsIgnoreCase("BMFSD/R-T") | PrdVal.equalsIgnoreCase("BEMFSD/R-T") |
				PrdVal.equalsIgnoreCase("BMFD-T") | PrdVal.equalsIgnoreCase("BEMFD-T") | PrdVal.equalsIgnoreCase("BMFD/R-T") | PrdVal.equalsIgnoreCase("BEMFD/R-T") |
				PrdVal.equalsIgnoreCase("BFD-F") | PrdVal.equalsIgnoreCase("BEFD-F") | PrdVal.equalsIgnoreCase("BFD/R-F") | PrdVal.equalsIgnoreCase("BEFD/R-F") |
				PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-F") | PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F") |
				PrdVal.equalsIgnoreCase("BMFD-TF") | PrdVal.equalsIgnoreCase("BEMFD-TF") | PrdVal.equalsIgnoreCase("BMFD/R-TF") | PrdVal.equalsIgnoreCase("BEMFD/R-TF"))
			{
				HSSFRow row24 = sheet.getRow(2); //Row   			ROW=3
			    HSSFCell attr_cost24 = row24.getCell(9); //Column   COLUMN=J
				if (ul_ssbush.equalsIgnoreCase("Y"))
				    attr_cost24.setCellValue("S");
				else
					attr_cost24.setCellValue("B");
			}
			//End of UL Dampers - Set value for SS Bush
			
			//RAD & SAD - Set Value for Wire/Chain
			if (r25 !=0 && c25 !=0 ) 
			{
				HSSFRow row25 = sheet.getRow(r25); //Row
			    HSSFCell attr_cost25 = row25.getCell(c25); //Column
			    if (diffwireyn.equalsIgnoreCase("Y"))
			    	attr_cost25.setCellValue("W");
			    else if (diffchainyn.equalsIgnoreCase("Y"))
			    	attr_cost25.setCellValue("C");
			    else
			    	attr_cost25.setCellValue("N");
			}
			//End of RAD & SAD - Set Value for Wire/Chain
			
			//Access Doors (ACC2L,ACCPHLK etc..) - Set Value for Insulation type
			if (r26 !=0 && c26 !=0 ) 
			{
				HSSFRow row26 = sheet.getRow(r26); //Row
			    HSSFCell attr_cost26 = row26.getCell(c26); //Column
			    attr_cost26.setCellValue(ad_ins_val);
			}
			//End of Access Doors (ACC2L,ACCPHLK etc..) - Set Value for Insulation type
			
			//Access Doors (ACC2L,ACCPHLK etc..) - Set Value for Metal thickness
			if (r27 !=0 && c27 !=0 ) 
			{
				HSSFRow row27 = sheet.getRow(r27); //Row
			    HSSFCell attr_cost27 = row27.getCell(c27); //Column
			    attr_cost27.setCellValue(ad_metal_val);
			}
			//End of Access Doors 
			
			//Trunking & Cable Tray - Set Value for thickness
			if (r28 !=0 && c28 !=0 ) 
			{
				HSSFRow row28 = sheet.getRow(r28); //Row
			    HSSFCell attr_cost28 = row28.getCell(c28); //Column
			    attr_cost28.setCellValue(tr_thick_val);
			}
			//End of Trunking & Cable Tray
			
			//STLG & STLCG - Set Value for IS/SSWM
			if (r29 !=0 && c29 !=0 ) 
			{
				HSSFRow row29 = sheet.getRow(r29); //Row
			    HSSFCell attr_cost29 = row29.getCell(c29); //Column
			    attr_cost29.setCellValue(stl_is);
			}
			//End of STLG & STLCG - Set Value for IS/SSWM
			
			//STLG & STLCG - Set Value for Filter
			if (r30 !=0 && c30 !=0 ) 
			{
				HSSFRow row30 = sheet.getRow(r30); //Row
			    HSSFCell attr_cost30 = row30.getCell(c30); //Column
			    attr_cost30.setCellValue(stl_filter);
			}
			//End of STLG & STLCG 
			
			//For UL Dampers New Items - Insert Parameter Values 
			
			if (PrdVal.equalsIgnoreCase("BMSD-NEW") | PrdVal.equalsIgnoreCase("BMFSD-NEW") | 
				PrdVal.equalsIgnoreCase("BEMFSD-NEW") | PrdVal.equalsIgnoreCase("BMFD-NEW") | 
				PrdVal.equalsIgnoreCase("BEMFD-NEW"))
			{
				//Bush Type
				HSSFRow row31 = sheet.getRow(12); //Row   			 ROW=13
			    HSSFCell attr_cost31 = row31.getCell(10); //Column   COLUMN=K
				if (ul_ssbush.equalsIgnoreCase("Y"))
				    attr_cost31.setCellValue("SS");
				else
					attr_cost31.setCellValue("BB");
				//End of Bush Type
				
				//Power
				HSSFRow row33 = sheet.getRow(5); //Row   			 ROW=6
			    HSSFCell attr_cost33 = row33.getCell(10); //Column   COLUMN=K
				if (ul_power.equalsIgnoreCase("N"))
				    attr_cost33.setCellValue("0");
				else
					attr_cost33.setCellValue(ul_power);
				//End of Power
				
				//Actuator Type
				HSSFRow row35 = sheet.getRow(9); //Row   			 ROW=10
			    HSSFCell attr_cost35 = row35.getCell(13); //Column   COLUMN=N
			    if (ul_act.equalsIgnoreCase("B")) 
				{
					attr_cost35.setCellValue(ul_intextact);  //Assign Mounting type Internal/External 
				}
				else
				{
					if (ul_act.equalsIgnoreCase("N"))
						attr_cost35.setCellValue("N");
					else
						attr_cost35.setCellValue(ul_act);
				}
				//End of Actuator Type
				
				//Spigot Type
				HSSFRow row36 = sheet.getRow(8); //Row   			 ROW=9
			    HSSFCell attr_cost36 = row36.getCell(2); //Column   COLUMN=C
				if (ul_spigot.equalsIgnoreCase("Y"))
				    attr_cost36.setCellValue("R");
				else
					attr_cost36.setCellValue("X");
				//End of Spigot Type
				
			}
			
			if (PrdVal.equalsIgnoreCase("BMSD-NEW") | PrdVal.equalsIgnoreCase("BMFSD-NEW") | 
				PrdVal.equalsIgnoreCase("BEMFSD-NEW"))
			{
				//Temperature
				HSSFRow row32 = sheet.getRow(2); //Row   			 ROW=3
			    HSSFCell attr_cost32 = row32.getCell(10); //Column   COLUMN=K
				if (ul_temp.equalsIgnoreCase("N"))
				    attr_cost32.setCellValue("0");
				else
					attr_cost32.setCellValue(ul_temp);
				//End of Temperature
				
				//Class Type
				HSSFRow row34 = sheet.getRow(9); //Row   			 ROW=10
			    HSSFCell attr_cost34 = row34.getCell(10); //Column   COLUMN=K
				if (ul_classtype.equalsIgnoreCase("N"))
				    attr_cost34.setCellValue("N");
				else
					attr_cost34.setCellValue(ul_classtype);
				//End of Class Type
			}
			
			if (PrdVal.equalsIgnoreCase("BMFD-NEW") | PrdVal.equalsIgnoreCase("BEMFD-NEW"))
			{
				//Link Type
				HSSFRow row37 = sheet.getRow(11); //Row   			 ROW=12
			    HSSFCell attr_cost37 = row37.getCell(2); //Column   COLUMN=C
				if (ul_linktype.equalsIgnoreCase("N"))
				    attr_cost37.setCellValue("X");
				else
					attr_cost37.setCellValue(ul_linktype);
				//End of Link Type
				
				//Static/Dynamic
				HSSFRow row38 = sheet.getRow(2); //Row   			 ROW=3
			    HSSFCell attr_cost38 = row38.getCell(10); //Column   COLUMN=K
				if (ul_stad.equalsIgnoreCase("N"))
				    attr_cost38.setCellValue("X");
				else
					attr_cost38.setCellValue(ul_stad);
				//End of Static/Dynamic
			}
			//End of UL Dampers New Items - Insert Parameter Values
			
			//Update Gasket in Excel Parameter for VCD new excel sheets
			if (PrdVal.equalsIgnoreCase("3VCDF") | PrdVal.equalsIgnoreCase("AUVCDF") | PrdVal.equalsIgnoreCase("3VCDB") | 
				PrdVal.equalsIgnoreCase("AUVCDB") | PrdVal.equalsIgnoreCase("3VCDS") | PrdVal.equalsIgnoreCase("AUVCDS") | 
				PrdVal.equalsIgnoreCase("AUVCDH") | PrdVal.equalsIgnoreCase("3VCDH") | PrdVal.equalsIgnoreCase("AGVCDF"))
			{
				HSSFRow row39 = sheet.getRow(14); //Row   			 ROW=15
			    HSSFCell attr_cost39 = row39.getCell(2); //Column   COLUMN=C
			    if (vcd_gasket.equalsIgnoreCase("N"))
				    attr_cost39.setCellValue("X");
				else
					attr_cost39.setCellValue("GASKET");
			}
			//End of Update Gasket in Excel Parameter for VCD new excel sheets    
			
			//Update Sleeve Thickness for FDC/SLV & FDCA/SLV
			if (PrdVal.equalsIgnoreCase("FDC/SLV") | PrdVal.equalsIgnoreCase("FDCA/SLV"))
			{
				HSSFRow row40 = sheet.getRow(4); //Row   			 ROW=5
			    HSSFCell attr_cost40 = row40.getCell(8); //Column   COLUMN=I
			    if (fd_slv.equalsIgnoreCase("N"))
				    attr_cost40.setCellValue("X");
				else
					attr_cost40.setCellValue(fd_slv);
			}
			//End of Update Sleeve Thickness for FDC/SLV & FDCA/SLV
			
			//Update Phenolic Y/N for PI3VCD & PIAVCD
			if (PrdVal.equalsIgnoreCase("PI3VCD") | PrdVal.equalsIgnoreCase("PIAVCD"))
			{
				HSSFRow row41 = sheet.getRow(6); //Row   			 ROW=7
			    HSSFCell attr_cost41 = row41.getCell(12); //Column   COLUMN=M
			    if (vcd_phen.equalsIgnoreCase("N"))
				    attr_cost41.setCellValue("NO");
				else
					attr_cost41.setCellValue("PH");
			}
			//End of Update Phenolic Y/N for PI3VCD & PIAVCD
			
			//Update Without Sleeve option for UL Dampers New Items - Insert Parameter Values
			if (PrdVal.equalsIgnoreCase("BMSD-NEW") | PrdVal.equalsIgnoreCase("BMFSD-NEW") | 
					PrdVal.equalsIgnoreCase("BEMFSD-NEW") | PrdVal.equalsIgnoreCase("BMFD-NEW") | 
					PrdVal.equalsIgnoreCase("BEMFD-NEW"))
			{
				HSSFRow row42 = sheet.getRow(14); //Row   			 ROW=15
			    HSSFCell attr_cost42 = row42.getCell(2); //Column   COLUMN=C
			    if (slyn.equalsIgnoreCase("Y") & ul_spigot.equalsIgnoreCase("N"))
				    attr_cost42.setCellValue("Y");
				else
					attr_cost42.setCellValue("N");
			}
			//End of Update Without Sleeve option for UL Dampers New Items - Insert Parameter Values
			
			//Update RISER input for CT-RISER
			if (PrdVal.equalsIgnoreCase("CT-RISER"))
			{
				HSSFRow row43 = sheet.getRow(9); //Row   			 ROW=10
			    HSSFCell attr_cost43 = row43.getCell(2); //Column   COLUMN=C
				if (ct_riser.equalsIgnoreCase("I"))
				{
					attr_cost43.setCellValue("INSIDE RISER");
					ct_riser_desc="INSIDE RISER BEND";
				}
				else
				{
					attr_cost43.setCellValue("OUTSIDE RISER");
					ct_riser_desc="OUTSIDE RISER BEND";
				}
			}
			//End of Update RISER input for CT-RISER
			
					
			//Update Degree input for CT-ACC-2W (G4)
			if (PrdVal.equalsIgnoreCase("CT-ACC-2W"))
			{
				HSSFRow row44 = sheet.getRow(3); //Row   			 ROW=4
			    HSSFCell attr_cost44 = row44.getCell(6); //Column   COLUMN=G
				if (ct_degree.equalsIgnoreCase("F"))
				{
					attr_cost44.setCellValue(45);
					ct_degree_desc="DEGREE 45";
				}
				else
				{
					attr_cost44.setCellValue(90);
					ct_degree_desc="DEGREE 90";
				}
			}
			//End of Update Degree input for CT-ACC-2W
			
			//Update Size 2 & 3 input for CT-ACC-3W-UE
			if (PrdVal.equalsIgnoreCase("CT-ACC-3W-UE"))
			{
				//Size 2
				HSSFRow row45 = sheet.getRow(12); //Row   			 ROW=13
			    HSSFCell attr_cost45 = row45.getCell(6); //Column   COLUMN=G
			    attr_cost45.setCellValue(ct_size2);
			    //Size 3
				HSSFRow row46 = sheet.getRow(12); //Row   			 ROW=13
			    HSSFCell attr_cost46 = row46.getCell(7); //Column   COLUMN=H
			    attr_cost46.setCellValue(ct_size3);
			}
			//End of Update Size 2 & 3 input for CT-ACC-3W-UE
			
			//Update Input Values for Sterilaire (Added on 16.09.2020)
			
			if (PrdVal.equalsIgnoreCase("STERILAIRE"))
			{
				//Type (Ducted/Stand Alone) Cell=C5
				HSSFRow row47 = sheet.getRow(4); //Row   			 ROW=5
			    HSSFCell attr_cost47 = row47.getCell(2); //Column   COLUMN=C
			    attr_cost47.setCellValue(str_type);
			    
			    //Powder Coating Type(PC,MF)  Cell=D9
			    HSSFRow row48 = sheet.getRow(8); //Row   			 ROW=9
			    HSSFCell attr_cost48 = row48.getCell(3); //Column   COLUMN=D
			    attr_cost48.setCellValue(str_pctype);
			    
			    //Anti Insect Screen[Y/N] Cell=G5
			    HSSFRow row49 = sheet.getRow(4); //Row   			 ROW=5
			    HSSFCell attr_cost49 = row49.getCell(6); //Column   COLUMN=G
				if (str_ais.equalsIgnoreCase("N"))
					attr_cost49.setCellValue("W");
				else
					attr_cost49.setCellValue("AIS");
				
				//Foam Insulation[Y/N] Cell=K13
			    HSSFRow row50 = sheet.getRow(12); //Row   			 ROW=13
			    HSSFCell attr_cost50 = row50.getCell(10); //Column   COLUMN=K
				if (str_fins.equalsIgnoreCase("N"))
					attr_cost50.setCellValue("N");
				else
					attr_cost50.setCellValue("INS");
				
				//Metal Thickness(G1,G2,G3)  Cell=K5
			    HSSFRow row51 = sheet.getRow(4); //Row   			 ROW=5
			    HSSFCell attr_cost51 = row51.getCell(10); //Column   COLUMN=K
			    attr_cost51.setCellValue(str_metal);
			    
			    //Model(U,UF,HF,UHF)  Cell=K9
			    HSSFRow row52 = sheet.getRow(8); //Row   			 ROW=9
			    HSSFCell attr_cost52 = row52.getCell(10); //Column   COLUMN=K
			    attr_cost52.setCellValue(str_model);
			    
			    //Fan Regulator [Y/N] Cell=D12
			    HSSFRow row53 = sheet.getRow(11); //Row   			 ROW=12
			    HSSFCell attr_cost53 = row53.getCell(3); //Column   COLUMN=D
				if (str_reg.equalsIgnoreCase("N"))
					attr_cost53.setCellValue("N");
				else
					attr_cost53.setCellValue("R");
			}
			//End of Update Input Values for Sterilaire
			
			//Update Input Values for EDH (Dtd: 04.10.2020)
			if (PrdVal.equalsIgnoreCase("EDH"))
			{
				//Kilo Watt Kw Cell=C5
				HSSFRow row54 = sheet.getRow(4); //Row   			 ROW=5
			    HSSFCell attr_cost54 = row54.getCell(2); //Column   COLUMN=C
			    attr_cost54.setCellValue(dh_kw);
			    
			    //Phase Cell=C9
				HSSFRow row55 = sheet.getRow(8); //Row   			 ROW=9
			    HSSFCell attr_cost55 = row55.getCell(2); //Column   COLUMN=C
			    attr_cost55.setCellValue(dh_phase);
			    
			    //PL pilot light [Y/N] Cell=G5
				HSSFRow row56 = sheet.getRow(4); //Row   			 ROW=5
			    HSSFCell attr_cost56 = row56.getCell(6); //Column   COLUMN=G
			    if (dh_pl.equalsIgnoreCase("Y"))
			    	attr_cost56.setCellValue("P");
			    else
			    	attr_cost56.setCellValue("W");
			    
			    //Metal Cell=K5
				HSSFRow row57 = sheet.getRow(4); //Row   			 ROW=5
			    HSSFCell attr_cost57 = row57.getCell(10); //Column   COLUMN=K
			    attr_cost57.setCellValue(dh_metal);
			
			    //Stage Cell=G8
				HSSFRow row58 = sheet.getRow(7); //Row   			 ROW=8
			    HSSFCell attr_cost58 = row58.getCell(6); //Column   COLUMN=G
			    attr_cost58.setCellValue(dh_stage);
			}
			//End of Update Input Values for EDH
			
		 	//Update additional Input Values for FANCASING-BVN (Dtd: 16.11.2020)
			if (PrdVal.equalsIgnoreCase("FAN-BVN"))
			{
				//Fan Model Cell=F13
				HSSFRow row59 = sheet.getRow(12); //Row   			 ROW=13
			    HSSFCell attr_cost59 = row59.getCell(5); //Column   COLUMN=F
			    attr_cost59.setCellValue(ca_model_bvn);
			    
			    //Foam Insulation Y/N Cell=K13
			    HSSFRow row60 = sheet.getRow(12); //Row   			 ROW=13
			    HSSFCell attr_cost60 = row60.getCell(10); //Column   COLUMN=K
			    if (ca_fins_bvn.equalsIgnoreCase("Y"))
			    	attr_cost60.setCellValue("INS");
			    else
			    	attr_cost60.setCellValue("N");
			    
			    //Update Fancost **Added on 13.10.2022  Cell=H13
			    if (bvn_fancost<=0)
		 		{
		 			addLog("Fan cost should not be blank or zero");
		 		}
			    HSSFRow row60a = sheet.getRow(12); //Row   			 ROW=13
			    HSSFCell attr_cost60a = row60a.getCell(7); //Column   COLUMN=H
			    attr_cost60a.setCellValue(bvn_fancost);
			    //End of Update Fancost
			    
			}
			//End of Update additional Input Values for FANCASING-BVN   
			
			//Update T-BAR Input Value for P4W,P4WB,P4WM (Dtd: 07.12.2020)  
			if (PrdVal.equalsIgnoreCase("P4W") | PrdVal.equalsIgnoreCase("P4WB") | PrdVal.equalsIgnoreCase("P4WM"))
			{
				//T-BAR Y/N Cell=C3
				HSSFRow row61 = sheet.getRow(2); //Row   			 ROW=3
			    HSSFCell attr_cost61 = row61.getCell(2); //Column   COLUMN=C
			    if (p4w_tbaryn.equalsIgnoreCase("Y"))
			    	attr_cost61.setCellValue("Y");
			    else
			    	attr_cost61.setCellValue("N");
				
			}
			//End of Update T-BAR Input Value for P4W,P4WB,P4WM
			
			//Assign parameters for Non VCD excel for 3VCD & AUVCD  (Added on 16/02/2021)  
			 if (PrdVal.equalsIgnoreCase("3VCDB") | PrdVal.equalsIgnoreCase("3VCDF") | 
				 PrdVal.equalsIgnoreCase("3VCDS") | PrdVal.equalsIgnoreCase("AUVCDB") |
				 PrdVal.equalsIgnoreCase("AUVCDF") | PrdVal.equalsIgnoreCase("AUVCDS") |
				 PrdVal.equalsIgnoreCase("AGVCDF"))
			 {
				 	if (vcd_nonstd.equalsIgnoreCase("Y"))
				 	{
				 		//VCD SHAFT Cell=N8
				 		HSSFRow row62 = sheet.getRow(7); 			//		 ROW=8
					    HSSFCell attr_cost62 = row62.getCell(13);	//		 Col=N
					    attr_cost62.setCellValue(vcd_shaft);
					    
					    //VCD Jamb Seal YN  Cell=M15
					    HSSFRow row63 = sheet.getRow(14); 		   	//		 ROW=15
					    HSSFCell attr_cost63 = row63.getCell(12);	//		 Col=M
					    attr_cost63.setCellValue(vcd_jamseal); 		
				 	}
			 }
			 if (PrdVal.equalsIgnoreCase("3VCDB") | PrdVal.equalsIgnoreCase("3VCDF") | 
				 PrdVal.equalsIgnoreCase("3VCDS"))
			 {
				    if (vcd_nonstd.equalsIgnoreCase("Y"))
				 	{
				    		HSSFRow row64 = sheet.getRow(7); 		   	//		 ROW=8
					    	HSSFCell attr_cost64 = row64.getCell(17);	//		 Col=R
					    	attr_cost64.setCellValue(vcd_blade);
				 	}
			 }
			 else if (PrdVal.equalsIgnoreCase("AGVCDF"))
			 {
				    if (vcd_nonstd.equalsIgnoreCase("Y"))
				 	{
				    		HSSFRow row64 = sheet.getRow(7); 		   	//		 ROW=8
					    	HSSFCell attr_cost64 = row64.getCell(17);	//		 Col=R
					    	attr_cost64.setCellValue(agvcd_blade);
				 	}
			 }
			 //End of Assign parameters for Non VCD excel for 3VCD & AUVCD 
			 
			 //Set No Actuator Y/N parameter for UL dampers  (15.03.21)  Cell=L12
			 if (Prod_Value.equalsIgnoreCase("BMFSD-NEW") | Prod_Value.equalsIgnoreCase("BEMFSD-NEW") |
				 Prod_Value.equalsIgnoreCase("BMSD-NEW") | Prod_Value.equalsIgnoreCase("BEMFD-NEW") |
				 Prod_Value.equalsIgnoreCase("BMFD-NEW"))
			 {
				 HSSFRow row65 = sheet.getRow(11); 		   	//		 ROW=12
			     HSSFCell attr_cost65 = row65.getCell(11);	//		 Col=L
				 if (ul_noact_yn.equalsIgnoreCase("Y"))  			    	
					 attr_cost65.setCellValue("Y");
				 else
					 attr_cost65.setCellValue("N");
				 
			 }
			 //End of Set No Actuator Y/N parameter for UL dampers
			 
			 //Set parameter Powder Coating Type for Cable Tray related items (25.05.2021) cell=C14
			 if (Prod_Value.equalsIgnoreCase("CABLETRAY") || Prod_Value.equalsIgnoreCase("CT-COVER") ||
				 Prod_Value.equalsIgnoreCase("CT-REDU") || Prod_Value.equalsIgnoreCase("CT-RISER") ||
				 Prod_Value.equalsIgnoreCase("CT-ACC-3W-E") || Prod_Value.equalsIgnoreCase("CT-ACC-3W-UE") ||
				 Prod_Value.equalsIgnoreCase("CT-ACC-4W") || Prod_Value.equalsIgnoreCase("CT-ACC-2W") ||
				 Prod_Value.equalsIgnoreCase("TRUNKING"))
			 {
				 HSSFRow row66 = sheet.getRow(13); 		   	//		 ROW=14
			     HSSFCell attr_cost66 = row66.getCell(2);	//		 Col=C
			     attr_cost66.setCellValue(ct_pctype);
			 }
			 //End of Set parameter Powder Coating Type for Cable Tray related items
			 
			 //Set Parameter Flange Type for PGL (Added:13.06.21) Cell=N5
			 if (Prod_Value.equalsIgnoreCase("PGL"))
			 {
				 HSSFRow row67 = sheet.getRow(4); 		   	//		 ROW=5
			     HSSFCell attr_cost67 = row67.getCell(13);	//		 Col=N
			     attr_cost67.setCellValue(Integer.parseInt(pgl_flange));
			 }
			 //End of Set Parameter Flange Type for PGL
			 
			 //Set Parameter for COWL & SHUTTER  (06/07/21)
			 if (Prod_Value.equalsIgnoreCase("COWL"))
			 {			     
			     //Base Y/N Cell=H9
			     HSSFRow row69 = sheet.getRow(8); 		   	//		 ROW=9
			     HSSFCell attr_cost69 = row69.getCell(7);	//		 Col=H
			     attr_cost69.setCellValue(cowl_base_yn);
			 }
			 //End of Set Parameter for COWL & SHUTTER
			 
			 //Set Parameter for FDC (06/07/21)
			 if (Prod_Value.equalsIgnoreCase("FDC") | Prod_Value.equalsIgnoreCase("FDCA"))
			 {  
				 //Static/Dynamic  Cell=G2
				 HSSFRow row70 = sheet.getRow(1); 		   	//		 ROW=2
			     HSSFCell attr_cost70 = row70.getCell(6);	//		 Col=G
			     if (ul_stad.equalsIgnoreCase("S"))
			    	 attr_cost70.setCellValue("STATIC");
			     else
			    	 attr_cost70.setCellValue("DYNAMIC");
			     
			     //Sleeve Y/N Cell=H7
			     HSSFRow row71 = sheet.getRow(6); 		   	//		 ROW=7
			     HSSFCell attr_cost71 = row71.getCell(7);	//		 Col=H
			     attr_cost71.setCellValue(fdc_slv_yn);
			     
			     //Sleeve Depth Cell=I7
			     HSSFRow row72 = sheet.getRow(6); 		   	//		 ROW=7
			     HSSFCell attr_cost72 = row72.getCell(8);	//		 Col=I
			     //attr_cost72.setCellValue(Integer.parseInt(fdc_slv_depth));
			     attr_cost72.setCellValue(fdc_slv_depth);
			     
			     //Sleeve Thickness Cell=J7
			     HSSFRow row73 = sheet.getRow(6); 		   	//		 ROW=7
			     HSSFCell attr_cost73 = row73.getCell(9);	//		 Col=J
			     //attr_cost73.setCellValue(Integer.parseInt(fdc_slv_thick));
			     attr_cost73.setCellValue(fdc_slv_thick);
			     
			     //Frame Thickness Cell=N6
			     HSSFRow row74 = sheet.getRow(5); 		   	//		 ROW=6
			     HSSFCell attr_cost74 = row74.getCell(13);	//		 Col=N
			     //attr_cost74.setCellValue(Integer.parseInt(fdc_fra_thick));
			     attr_cost74.setCellValue(fdc_fra_thick);
			     
			     //FDC/FDCA  Cell=C4
			     HSSFRow row75 = sheet.getRow(3); 		   	//		 ROW=4
			     HSSFCell attr_cost75 = row75.getCell(2);	//		 Col=C
			     attr_cost75.setCellValue(PrdVal);
			     
			     //Manual Indicator Y/N  Cell=B2
			     HSSFRow row76 = sheet.getRow(1); 	   	//		 ROW=2
			     HSSFCell attr_cost76 = row76.getCell(1);	//		 Col=B
			     if (fdc_ind_yn.equalsIgnoreCase("Y"))
			    	 attr_cost76.setCellValue("YES");
			     else
			    	 attr_cost76.setCellValue("NO");
			     
			 }
			 //End of Set Parameter for FDC
			 
			//Set Parameter for RAD & SAD (12/09/21)
			if (Prod_Value.equalsIgnoreCase("RAD") | Prod_Value.equalsIgnoreCase("RAD-GNR") |
				Prod_Value.equalsIgnoreCase("RAD-GNS") | Prod_Value.equalsIgnoreCase("SADB") |
				Prod_Value.equalsIgnoreCase("SADB-EG") | Prod_Value.equalsIgnoreCase("SADB-EG-GNR") |
				Prod_Value.equalsIgnoreCase("SADB-EG-GNS") | Prod_Value.equalsIgnoreCase("SADB-GNR") |
				Prod_Value.equalsIgnoreCase("SADB-GNS") | Prod_Value.equalsIgnoreCase("SADM") |
				Prod_Value.equalsIgnoreCase("SADM-EG") | Prod_Value.equalsIgnoreCase("SADM-EG-GNR") |
				Prod_Value.equalsIgnoreCase("SADM-EG-GNS") | Prod_Value.equalsIgnoreCase("SADM-GNR") |
				Prod_Value.equalsIgnoreCase("SADM-GNS"))
			{
				String rad_prdval=null;
				if (rad_12mm_yn.equalsIgnoreCase("N"))
					rad_prdval = Prod_Value;
				else
					rad_prdval = Prod_Value.substring(0, 3)+"12"+Prod_Value.substring(3);
				
				//Set Prod.Value  Cell=C5
				HSSFRow row77 = sheet.getRow(4); 		   	//		 ROW=5
				HSSFCell attr_cost77 = row77.getCell(2);	//		 Col=C
				attr_cost77.setCellValue(rad_prdval);			
			    
			    //Set Value for Gasket Y/N  Cell=H5
			    HSSFRow row78 = sheet.getRow(4); 		   	//		 ROW=5
			    HSSFCell attr_cost78 = row78.getCell(7);	//		 Col=H
			    attr_cost78.setCellValue(gasket_yn);
			}
			//End of Set Parameter for RAD & SAD (12/09/21)
			
			//Set Parameter for SDV,SDVE,SDVBP,SDVBPE,CTC,CTCDS (20/09/21)
			if (Prod_Value.equalsIgnoreCase("SDV") | Prod_Value.equalsIgnoreCase("SDVE") | Prod_Value.equalsIgnoreCase("SDVBP") | 
				Prod_Value.equalsIgnoreCase("SDVBPE") | Prod_Value.equalsIgnoreCase("CTC") | Prod_Value.equalsIgnoreCase("CTCDS"))
			{
				//Set Prod.Value  Cell=C4
				HSSFRow row79 = sheet.getRow(3); 		   	//		 ROW=4
			    HSSFCell attr_cost79 = row79.getCell(2);	//		 Col=C
			    attr_cost79.setCellValue(Prod_Value);
			    
			    //Set GI Thick  Cell=J7
			    HSSFRow row80 = sheet.getRow(6); 		   	//		 ROW=7
			    HSSFCell attr_cost80 = row80.getCell(9);	//		 Col=J
			    attr_cost80.setCellValue(vav_fra_thick);
			}
			//End of Set Parameter for SDV,SDVE,SDVBP,SDVBPE,CTC,CTCDS
			
			//Set Parameter for EBP (20/09/21)
			if (Prod_Value.equalsIgnoreCase("EBP1") | Prod_Value.equalsIgnoreCase("EBP2") | Prod_Value.equalsIgnoreCase("EBP3") |
				Prod_Value.equalsIgnoreCase("EBP4") | Prod_Value.equalsIgnoreCase("EBP5") | Prod_Value.equalsIgnoreCase("EBP6") |
				Prod_Value.equalsIgnoreCase("EBP7") | Prod_Value.equalsIgnoreCase("EBP8"))
			{
				//Set Prod.Value  Cell=C2
				HSSFRow row79 = sheet.getRow(1); 		   	//		 ROW=2
			    HSSFCell attr_cost79 = row79.getCell(2);	//		 Col=C
			    attr_cost79.setCellValue(Prod_Value);
			    
			    //Set GI Thick  Cell=J7
			    HSSFRow row80 = sheet.getRow(6); 		   	//		 ROW=7
			    HSSFCell attr_cost80 = row80.getCell(9);	//		 Col=J
			    attr_cost80.setCellValue(vav_fra_thick);
			}
			//End of Set Parameter for EBP
			
			//Set Parameter for Grills models (added on 04.11.2021)
			//Set Parameter for Linear Grills models (added on 02.03.2022)
			if (gdtype.equalsIgnoreCase("G") | gdtype.equalsIgnoreCase("LG"))
			{
				String sqlgdtype = " select excelcode from beta_qtn_excelcode where code= ? ";
				String exclcode = DB.getSQLValueString(get_TrxName(), sqlgdtype, PrdVal);
				
				//Set Value for Product code  Cell=C5
				HSSFRow row81 = sheet.getRow(4);            //       Row=5
				HSSFCell attr_cost81 = row81.getCell(2);	//		 Col=C
				attr_cost81.setCellValue(exclcode);
				
				if (!(PrdVal.equalsIgnoreCase("OBDB")) & !(PrdVal.equalsIgnoreCase("OBDM")))
				{
					//Set Value for Gasket Y/N  Cell=G5
					HSSFRow row82 = sheet.getRow(4); 		   	//		 ROW=5
					HSSFCell attr_cost82 = row82.getCell(6);	//		 Col=G
					attr_cost82.setCellValue(gasket_yn);
				}	
			}
			//End of Set Parameter for Grills & Linear Grills models
			
			//Set Parameter for FBR & FBF (11/11/21)
			if (Prod_Value.equalsIgnoreCase("FBR") | Prod_Value.equalsIgnoreCase("FBF"))
			{
				//Set Value for Product code  Cell=C5
				String excelval=null;
				if (Prod_Value.equalsIgnoreCase("FBF"))
				{
					if (fb_flange.equalsIgnoreCase("51"))
						excelval=Prod_Value + '-' + fb_slotn + '/' + fb_slotw ;
					else
						excelval=Prod_Value + fb_flange + '-' + fb_slotn + '/' + fb_slotw ;
				}
				else if (Prod_Value.equalsIgnoreCase("FBR"))
					excelval=Prod_Value + '-' + fb_slotn + '/' + fb_slotw ;
				
				HSSFRow row83 = sheet.getRow(4);            //       Row=5
				HSSFCell attr_cost83 = row83.getCell(2);	//		 Col=C
				attr_cost83.setCellValue(excelval);
			}
			//End of Set Parameter for FBR & FBF
			
			//Set Parameter for FDC Rating (26/01/22)  Cell=C7
			if (Prod_Value.equalsIgnoreCase("FDC") | Prod_Value.equalsIgnoreCase("FDCA"))
			{
				HSSFRow row84 = sheet.getRow(6); 		   	//		 ROW=7
			    HSSFCell attr_cost84 = row84.getCell(3);	//		 Col=D
			    if (fdc_rating.equalsIgnoreCase("1.5"))
			    	attr_cost84.setCellValue("1.5H");
			    else if (fdc_rating.equalsIgnoreCase("3"))
			    	attr_cost84.setCellValue("3H");
			}
			//End of Set Parameter for FDC Rating 
			
			//Set Parameter (Product Code & Thickness) for NRD series & PRD (Dtd:02.02.2022)
			if (Prod_Value.equalsIgnoreCase("NRD") | Prod_Value.equalsIgnoreCase("NRDE") |
				Prod_Value.equalsIgnoreCase("NRD-CR") | Prod_Value.equalsIgnoreCase("NRDE-CR") |
				Prod_Value.equalsIgnoreCase("PRD"))
			{
				//Set Prod.Value  Cell=C6
				HSSFRow row85 = sheet.getRow(5); 		   	//		 ROW=6
			    HSSFCell attr_cost85 = row85.getCell(2);	//		 Col=C
			    attr_cost85.setCellValue(Prod_Value);
			    
			    //Set GI Thick  Cell=K8
			    HSSFRow row86 = sheet.getRow(7); 		   	//		 ROW=8
			    HSSFCell attr_cost86 = row86.getCell(10);	//		 Col=K
			    attr_cost86.setCellValue(nrd_fra_thick);
			}
			//End of Set Parameter (Product Code & Thickness) for NRD series & PRD
			
			//Set Parameter Perf.Sheet for PGL (Dtd:22.02.2022)
			if (Prod_Value.equalsIgnoreCase("PGL"))
			{
				//Set Perforated Sheet Thick  Cell=M11
				HSSFRow row87 = sheet.getRow(10); 		   	//		 ROW=11
			    HSSFCell attr_cost87 = row87.getCell(12);	//		 Col=M
			    attr_cost87.setCellValue(pgl_psheet+"");
			}
			//End of Set Parameter Perf.Sheet for PGL
			
			//Set Parameter for Linear Diffusers models (added on 31.03.2022)
			if (gdtype.equalsIgnoreCase("LD") & !(Prod_Value.equalsIgnoreCase("FBR")) & 
				!(Prod_Value.equalsIgnoreCase("FBF")))
			{
				String sqlgdtype = " select excelcode from beta_qtn_excelcode where code= ? ";
				String exclcode = DB.getSQLValueString(get_TrxName(), sqlgdtype, PrdVal);
				String sqlldtype = " select type from beta_qtn_excelcode where code= ? ";
				String typecode = DB.getSQLValueString(get_TrxName(), sqlldtype, PrdVal);
				Integer ldr=0,ldc_code=0,ldc_slot=0,ldc_gasket=0;
				if (typecode.equalsIgnoreCase("LD"))
				{
					ldr=4;ldc_code=2;ldc_slot=6;ldc_gasket=7;
				}
				else if (typecode.equalsIgnoreCase("LDC"))
				{
					ldr=5;ldc_code=1;ldc_slot=5;ldc_gasket=7;
				} 
				//Set Value for Product code  Cell=C5 for LD, Cell=B5 for LDC
				HSSFRow row88 = sheet.getRow(ldr);              
				HSSFCell attr_cost88 = row88.getCell(ldc_code);	
				attr_cost88.setCellValue(exclcode);
				
				//Set Value for Slot Size  Cell=G5 for LD, Cell=F5 for LDC
				HSSFRow row89 = sheet.getRow(ldr);              
				HSSFCell attr_cost89 = row89.getCell(ldc_slot);	
				attr_cost89.setCellValue(Integer.parseInt(ld_slotwidth));
				
				//Set Value for Gasket Y/N  Cell=H5 for LD, cell=I5 for LDC
				HSSFRow row90 = sheet.getRow(ldr); 		   	
				HSSFCell attr_cost90 = row90.getCell(ldc_gasket);	
				attr_cost90.setCellValue(gasket_yn);
					
			}
			//End of Set Parameter for Grills & Linear Grills models
			
			//Set Value for Product code for AccessDoors Cell=E2  (Dtd:07.04.22)
			if(PrdVal.equalsIgnoreCase("ACC2L") | PrdVal.equalsIgnoreCase("ACC2LD") | PrdVal.equalsIgnoreCase("ACCSH") | 
				    PrdVal.equalsIgnoreCase("ACCPHLK") | PrdVal.equalsIgnoreCase("ACCSHD") |PrdVal.equalsIgnoreCase("ACCPH") |
				    PrdVal.equalsIgnoreCase("ACCSHLK") | PrdVal.equalsIgnoreCase("ACCPHD"))
			{
				HSSFRow row91 = sheet.getRow(1); 		   	//Row=2
				HSSFCell attr_cost91 = row91.getCell(4);	//Col=E
				attr_cost91.setCellValue(PrdVal);
			}
			//End of Set Value for Product code for AccessDoors 
			
			//Set Value for Product code for 3VCD & AUVCD, Cell=C3  (Dtd:06.05.22)
			if(PrdVal.equalsIgnoreCase("3VCDB") | PrdVal.equalsIgnoreCase("3VCDF") | PrdVal.equalsIgnoreCase("3VCDH") | 
				    PrdVal.equalsIgnoreCase("3VCDS") | PrdVal.equalsIgnoreCase("AUVCDB") |PrdVal.equalsIgnoreCase("AUVCDF") |
				    PrdVal.equalsIgnoreCase("AUVCDH") | PrdVal.equalsIgnoreCase("AUVCDS"))
			{
				HSSFRow row92 = sheet.getRow(2); 		   	//Row=3
				HSSFCell attr_cost92 = row92.getCell(2);	//Col=C
				if(PrdVal.equalsIgnoreCase("3VCDB") | PrdVal.equalsIgnoreCase("3VCDF") | PrdVal.equalsIgnoreCase("3VCDH") | 
				   PrdVal.equalsIgnoreCase("3VCDS"))
					attr_cost92.setCellValue("3V");
				else 
					attr_cost92.setCellValue("AV");
			}
			//End of Set Value for Product code for 3VCD & AUVCD   if (vcd_nonstd.equalsIgnoreCase("N"))
			
			//Set Rivets Value for Non-Std VCD, cell=  (Std:09.05.22)
			if (PrdVal.equalsIgnoreCase("3VCDB") | PrdVal.equalsIgnoreCase("3VCDF") | 
				PrdVal.equalsIgnoreCase("3VCDS") | PrdVal.equalsIgnoreCase("AUVCDB") |
				PrdVal.equalsIgnoreCase("AUVCDF") | PrdVal.equalsIgnoreCase("AUVCDS") |
				PrdVal.equalsIgnoreCase("AGVCDF"))
			{
			 	if (vcd_nonstd.equalsIgnoreCase("Y"))
			 	{
			 		//VCD Rivets Cell=Z10
			 		HSSFRow row93 = sheet.getRow(9); 			//		 ROW=10
				    HSSFCell attr_cost93 = row93.getCell(25);	//		 Col=Z
				    attr_cost93.setCellValue(vcd_rivets);
			 	}
			}
			//End of Set Rivets Value for Non-Std VCD
			
			//Set Kw value for CFW Axial Fans (Dtd: 17.05.22)
			if (PrdVal.equalsIgnoreCase("FAN-CFW-AXIAL"))
			{
				//Kw Cell=K6
				HSSFRow row94 = sheet.getRow(5); 			//		 ROW=6
			    HSSFCell attr_cost94 = row94.getCell(10);	//		 Col=K
			    attr_cost94.setCellValue(Double.parseDouble(fancfw_kw));
			    
			  /*  //Update Fan/Impellor/Casing cost **Added on 13.10.2022  
			    if (fancfw_impcost<=0)  //Cell=D7
		 		{
		 			addLog("Fan Impeller cost should not be blank or zero");
		 		}
			    HSSFRow row94a = sheet.getRow(6); //Row   			 ROW=7
			    HSSFCell attr_cost94a = row94a.getCell(3); //Column   COLUMN=D
			    attr_cost94a.setCellValue(fancfw_impcost);
			    
			    if (fancfw_motcost<=0)  //Cell=D8
		 		{
		 			addLog("Fan Motor cost should not be blank or zero");
		 		}
			    HSSFRow row94b = sheet.getRow(7); //Row   			 ROW=8
			    HSSFCell attr_cost94b = row94b.getCell(3); //Column   COLUMN=D
			    attr_cost94b.setCellValue(fancfw_motcost);
			    
			    if (fancfw_cascost<=0)  //Cell=D9
		 		{
		 			addLog("Fan Casing cost should not be blank or zero");
		 		}
			    HSSFRow row94c = sheet.getRow(8); //Row   			 ROW=9
			    HSSFCell attr_cost94c = row94c.getCell(3); //Column   COLUMN=D
			    attr_cost94c.setCellValue(fancfw_cascost);
			    
			    //End of Update Impeller/Casing/Motor cost  */
			}
			//End of Set Kw value for CFW Axial Fans 
				
		  	//Set additional parameters for FAN-BVN Non-Std (Dated:03.06.22)
			if (PrdVal.equalsIgnoreCase("FAN-BVN"))
			 {
				//Set value (GI/SS) for Wire Mesh Screen Type (Cell=G8)
				HSSFRow row95 = sheet.getRow(7); 			//		 ROW=8
			    HSSFCell attr_cost95 = row95.getCell(6);	//		 Col=G
				if (bvn_insscr_type.equalsIgnoreCase("Y"))
					attr_cost95.setCellValue("SS");
				else
					attr_cost95.setCellValue("GI");
				
				//Set Value for non-std parameters
				if (bvn_nonstd.equalsIgnoreCase("Y"))
				{
					//Set value for Gland (Cell=M6)
					HSSFRow row96 = sheet.getRow(5); 			//		 ROW=6
				    HSSFCell attr_cost96 = row96.getCell(12);	//		 Col=M
					if (bvn_gland.equalsIgnoreCase("Y"))
						attr_cost96.setCellValue("GLAND");
					else
						attr_cost96.setCellValue("NIL");
					
					//Set value for IP66 (Cell=P6)
					HSSFRow row97 = sheet.getRow(5); 			//		 ROW=6
				    HSSFCell attr_cost97 = row97.getCell(15);	//		 Col=P
					if (bvn_ip66.equalsIgnoreCase("Y"))
						attr_cost97.setCellValue("IP 66");
					else
						attr_cost97.setCellValue("NIL");
					
					//Set value for Flanges (Cell=P10)
					HSSFRow row98 = sheet.getRow(9); 			//		 ROW=10
				    HSSFCell attr_cost98 = row98.getCell(15);	//		 Col=P
					if (bvn_flanges.equalsIgnoreCase("Y"))
						attr_cost98.setCellValue("FLANGES");
					else
						attr_cost98.setCellValue("NIL");
					
					//Set value for Acoustic Ins (Cell=K13)
					HSSFRow row99 = sheet.getRow(12); 			//		 ROW=13
				    HSSFCell attr_cost99 = row99.getCell(10);	//		 Col=K
					if (bvn_ains.equalsIgnoreCase("Y"))
						attr_cost99.setCellValue("AINS");
					
				}
			 }
			//End of Set additional parameters for FAN-BVN Non-Std   
			
			//Set Paramters for MPRD (Dtd: 10.06.22)
			if (PrdVal.equalsIgnoreCase("MPRD"))
			{
				//Set Value for Bush (Brass/Plastic) Cell=G6
				HSSFRow row100 = sheet.getRow(5); 			//		 ROW=6
			    HSSFCell attr_cost100 = row100.getCell(6);	//		 Col=G
			    attr_cost100.setCellValue(mprd_bush);
			    
				//Set Value for Frame (G2/G3/G4) Cell=K6
			    HSSFRow row101 = sheet.getRow(5); 			//		 ROW=6
			    HSSFCell attr_cost101 = row100.getCell(10);	//		 Col=K
			    attr_cost101.setCellValue(mprd_frame);
			    
				//Set Value for Jambseal (Y/N) Cell=M10
			    HSSFRow row102 = sheet.getRow(9); 			//		 ROW=10
			    HSSFCell attr_cost102 = row102.getCell(12);	//		 Col=M
			    attr_cost102.setCellValue(mprd_jamb);
			}
			//End of Set Paramters for MPRD 
			
			//Set Parameter for STL/STLC models (added on 14.06.2022)
			if (PrdVal.equalsIgnoreCase("STLA") | PrdVal.equalsIgnoreCase("STLA-SF") | PrdVal.equalsIgnoreCase("STLA-IS-SF") |
 			    PrdVal.equalsIgnoreCase("STLA-IS") | PrdVal.equalsIgnoreCase("STLCA") | PrdVal.equalsIgnoreCase("STLCA-SF") |
 			    PrdVal.equalsIgnoreCase("STLCA-IS") | PrdVal.equalsIgnoreCase("STLCA-IS-SF") | PrdVal.equalsIgnoreCase("STLA-SF2") |
 			    PrdVal.equalsIgnoreCase("STLA-IS-SF2") | PrdVal.equalsIgnoreCase("STLCA-SF2") | PrdVal.equalsIgnoreCase("STLCA-IS-SF2") |
 			    PrdVal.equalsIgnoreCase("STLCA-SSWM") | PrdVal.equalsIgnoreCase("STLCA-SSWM-SF") | PrdVal.equalsIgnoreCase("STLA-SSWM") |
   	            PrdVal.equalsIgnoreCase("STLA-SSWM-SF") | PrdVal.equalsIgnoreCase("STLA-SSWM-SF2") | PrdVal.equalsIgnoreCase("STLCA-SSWM-SF2"))
			{
				String sqlgdtype = " select excelcode from beta_qtn_excelcode where code= ? ";
				String exclcode = DB.getSQLValueString(get_TrxName(), sqlgdtype, PrdVal);
								
				//Set Value for Product code  Cell=C5 
				HSSFRow row103 = sheet.getRow(4);         			//Row=5     
				HSSFCell attr_cost103 = row103.getCell(2);	        //Column=C
				attr_cost103.setCellValue(exclcode);
			}
			//End of Set Parameter for STL/STLC models
			
			//Set Parameter for EAL2/EAL4 models (added on 23.06.2022)
			if (PrdVal.equalsIgnoreCase("EAL2") | PrdVal.equalsIgnoreCase("EAL2-IS") | PrdVal.equalsIgnoreCase("EAL4") |
	 			PrdVal.equalsIgnoreCase("EAL4-IS") | PrdVal.equalsIgnoreCase("EAL2-SSWM") | PrdVal.equalsIgnoreCase("EAL4-SSWM"))
			{
				//Set Value for Product code  Cell=C5 
				HSSFRow row104 = sheet.getRow(4);         			//Row=5     
				HSSFCell attr_cost104 = row104.getCell(2);	        //Column=C
				attr_cost104.setCellValue(PrdVal);
				
				//Set Value for Al. Filter Cell=G5
				HSSFRow row104a = sheet.getRow(4);         			//Row=5     
				HSSFCell attr_cost104a = row104a.getCell(6);	    //Column=G
				attr_cost104a.setCellValue(eal_filteryn);
			}
 			//End of Set Parameter for EAL2/EAL4 models
			
			//Set Parameter for GAL/GALB models (added on 18.08.2022)
			if (PrdVal.equalsIgnoreCase("GAL") | PrdVal.equalsIgnoreCase("GAL-CR") | PrdVal.equalsIgnoreCase("GAL-CR-IS") |
	 			PrdVal.equalsIgnoreCase("GAL-CR-SSWM") | PrdVal.equalsIgnoreCase("GAL-IS") | PrdVal.equalsIgnoreCase("GAL-SSWM") |
	 			PrdVal.equalsIgnoreCase("GALB") | PrdVal.equalsIgnoreCase("GALB-CR") | PrdVal.equalsIgnoreCase("GALB-CR-IS") |
				PrdVal.equalsIgnoreCase("GALB-CR-SSWM") | PrdVal.equalsIgnoreCase("GALB-IS") | PrdVal.equalsIgnoreCase("GALB-SSWM"))
			
			{
				//Set Value for Product code  Cell=C5 
				HSSFRow row105 = sheet.getRow(4);         			//Row=5     
				HSSFCell attr_cost105 = row105.getCell(2);	        //Column=C
				attr_cost105.setCellValue(PrdVal);
			}
 			//End of Set Parameter for GAL/GALB models
			
			//Set Parameter for FAL models (added on 19.08.2022)
			if (PrdVal.equalsIgnoreCase("FAL") | PrdVal.equalsIgnoreCase("FALDB") | PrdVal.equalsIgnoreCase("FALDM") |
	 			PrdVal.equalsIgnoreCase("FALH") | PrdVal.equalsIgnoreCase("FALHDB") | PrdVal.equalsIgnoreCase("FALHDM"))
			
			{
				//Set Value for Product code  Cell=C5 
				HSSFRow row106 = sheet.getRow(4);         			//Row=5     
				HSSFCell attr_cost106 = row106.getCell(2);	        //Column=C
				attr_cost106.setCellValue(PrdVal);
			}
 			//End of Set Parameter for FAL models
			
			//Set Parameter for FAG models (added on 25.08.2022)
			if (PrdVal.equalsIgnoreCase("FAG") | PrdVal.equalsIgnoreCase("FAG-45") | 
				PrdVal.equalsIgnoreCase("FAGH") | PrdVal.equalsIgnoreCase("FAGH-45"))
			{
				String sqlgdtype = " select excelcode from beta_qtn_excelcode where code= ? ";
				String exclcode = DB.getSQLValueString(get_TrxName(), sqlgdtype, PrdVal);
								
				//Set Value for Product code  Cell=C5 
				HSSFRow row107 = sheet.getRow(4);         			//Row=5     
				HSSFCell attr_cost107 = row107.getCell(2);	        //Column=C
				attr_cost107.setCellValue(exclcode);
			}
			//End of Set Parameter for FAG models
			
			//Set Parameter for ECG models (added on 25.08.2022)
			if (PrdVal.equalsIgnoreCase("ECG") | PrdVal.equalsIgnoreCase("ECRB") | 
				PrdVal.equalsIgnoreCase("ECRM") | PrdVal.equalsIgnoreCase("FECH"))
			
			{
				//Set Value for Product code  Cell=C5 
				HSSFRow row108 = sheet.getRow(4);         			//Row=5     
				HSSFCell attr_cost108 = row108.getCell(2);	        //Column=C
				attr_cost108.setCellValue(PrdVal);
			}
 			//End of Set Parameter for ECG models
			
			//Set Parameter for P4W & PAG models (added on 25.08.2022)
			if (PrdVal.equalsIgnoreCase("P4W") | PrdVal.equalsIgnoreCase("P4WM") | 
				PrdVal.equalsIgnoreCase("P4WB") | PrdVal.equalsIgnoreCase("PAG"))
			
			{
				//Set Value for Product code  Cell=C5 
				HSSFRow row109 = sheet.getRow(4);         			//Row=5     
				HSSFCell attr_cost109 = row109.getCell(2);	        //Column=C
				attr_cost109.setCellValue(PrdVal);
			}
 			//End of Set Parameter for P4W & PAG models
			
			//Set Parameter for Data Rack Cabinet (added 22.11.2022)
			if (PrdVal.equalsIgnoreCase("DATA-CABINET"))
			{
				//Set Value for model   Cell=F10
				HSSFRow row110 = sheet.getRow(9);         			//Row=10     
				HSSFCell attr_cost110 = row110.getCell(5);	        //Column=F
				attr_cost110.setCellValue(datarack_model);
				
				//Set Value for Frame   Cell=K5
				HSSFRow row111 = sheet.getRow(4);         			//Row=5     
				HSSFCell attr_cost111 = row111.getCell(10);	        //Column=K
				attr_cost111.setCellValue(datarack_frame);
			}
			//End of Set Parameter for Data Rack Cabinet
			
			//Set Parameter for Round Neck (RN) for FDC/FDCA
			if (PrdVal.equalsIgnoreCase("FDC") | PrdVal.equalsIgnoreCase("FDCA"))
			{
				//Set Value for RN Yes/No   Cel=P4
				HSSFRow row112 = sheet.getRow(3);         			//Row=4     
				HSSFCell attr_cost112 = row112.getCell(15);	        //Column=P
				if (fdc_rn_yn.equalsIgnoreCase("Y"))
					attr_cost112.setCellValue("RN");
				else
					attr_cost112.setCellValue("NN");
				
				//Set Value for RN Size  Cel=R5
				if (fdc_rn_yn.equalsIgnoreCase("Y"))
				{
					HSSFRow row113 = sheet.getRow(4);         			//Row=5     
					HSSFCell attr_cost113 = row113.getCell(17);	        //Column=R
					attr_cost113.setCellValue(fdc_rn);
				}		
			}
			//End of Set Parameter for Round Neck (RN) for FDC/FDCA
			
			//Set VDR/EVDR parameter for VDR
			if (PrdVal.equalsIgnoreCase("VDR"))
			{
				//Cel=D2
				HSSFRow row114 = sheet.getRow(1);         			//Row=2     
				HSSFCell attr_cost114 = row114.getCell(3);	        //Column=D
				if (evdr_yn.equalsIgnoreCase("Y"))
					attr_cost114.setCellValue("EVDR");
				else
					attr_cost114.setCellValue("VDR");
			}
			//End of VDR/EVDR parameter for VDR
			
			//Set parameter for Mounting Feet (MFEET)
			if (PrdVal.equalsIgnoreCase("MFEET"))
			{
				//Cel=J3   Brand Code
				HSSFRow row115 = sheet.getRow(2);         			//Row=3     
				HSSFCell attr_cost115 = row115.getCell(9);	        //Column=J
				attr_cost115.setCellValue(mfeet_brand);
				
				//Cell=E6  Powder Coating Type
				HSSFRow row116 = sheet.getRow(5);         			//Row=6     
				HSSFCell attr_cost116 = row116.getCell(4);	        //Column=E
				attr_cost116.setCellValue(mfeet_pc);
			}
			//End of Set parameter for Mounting Feet
			
			//Set parameter for ITCC Junction Box (JB-ITCC)
			if (PrdVal.equalsIgnoreCase("JB-ITCC"))
			{
				//Cel=C5   Logo Y/N
				HSSFRow row116 = sheet.getRow(4);         			//Row=5    
				HSSFCell attr_cost116 = row116.getCell(2);	        //Column=C
				if (jb_logo_yn.equalsIgnoreCase("Y"))
					attr_cost116.setCellValue("LOGO");
				else
					attr_cost116.setCellValue("NOLOGO");
				
				//Cel=G5   KnockOut Y/N
				HSSFRow row117 = sheet.getRow(4);         			//Row=5    
				HSSFCell attr_cost117 = row117.getCell(6);	        //Column=G
				if (jb_kout_yn.equalsIgnoreCase("Y"))
					attr_cost117.setCellValue("KNOCKOUT");
				else
					attr_cost117.setCellValue("NOKNOCKOUT");
				
				//Cell=K5  Frame Type
				HSSFRow row118 = sheet.getRow(4);         			//Row=5     
				HSSFCell attr_cost118 = row118.getCell(10);	        //Column=K
				attr_cost118.setCellValue(jb_frame);
			}
			//End of Set parameter for ITCC Junction Box 			
			
	//Making of Product Description	
		 prod_desc = v_description + ' ';
		 
		 //Add 12 for RAD & SAD models for 12MM Flange (Dtd:19.02.24)
		 if (Prod_Value.equalsIgnoreCase("RAD") | Prod_Value.equalsIgnoreCase("RAD-GNR") | 
			Prod_Value.equalsIgnoreCase("RAD-GNS") | Prod_Value.equalsIgnoreCase("SADB") |
			Prod_Value.equalsIgnoreCase("SADB-EG") | Prod_Value.equalsIgnoreCase("SADB-EG-GNR") |
			Prod_Value.equalsIgnoreCase("SADB-EG-GNS") | Prod_Value.equalsIgnoreCase("SADB-GNR") |
			Prod_Value.equalsIgnoreCase("SADB-GNS") | Prod_Value.equalsIgnoreCase("SADM") |
			Prod_Value.equalsIgnoreCase("SADM-EG") | Prod_Value.equalsIgnoreCase("SADM-EG-GNR") |
			Prod_Value.equalsIgnoreCase("SADM-EG-GNS") | Prod_Value.equalsIgnoreCase("SADM-GNR") |
			Prod_Value.equalsIgnoreCase("SADM-GNS"))
		 {
			 String rad_prdval=null;
			 if (rad_12mm_yn.equalsIgnoreCase("N"))
				 prod_desc = Prod_Value + ' ';
			 else
				 prod_desc = Prod_Value.substring(0, 3)+"12"+Prod_Value.substring(3)+ ' ';
		 }
		 //End of add 12 for RAD & SAD models for 12MM Flange (Dtd:19.02.24)
		 
		 //Add Fire Rating (1.5/3 Hrs) in description
		 if (PrdVal.equalsIgnoreCase("FDC") | PrdVal.equalsIgnoreCase("FDCA"))
		 {
			 if (fdc_rating.equalsIgnoreCase("3"))
			 	prod_desc = "FDC-3 ";
			 else
				 prod_desc = "FDC "; 
			 if (PrdVal.equalsIgnoreCase("FDCA"))
				 prod_desc = prod_desc +  "NOT 100% FREE AREA " ;
			 else if (PrdVal.equalsIgnoreCase("FDC"))
				 prod_desc = prod_desc +  "100% FREE AREA " ;
		 }
		 //End of FDC/FDCA Description
		 
		 //Update Prod.description for Data Rack Cabinet (Added:22.11.22)
		 if (PrdVal.equalsIgnoreCase("DATA-CABINET"))
		 {
			 String sqldr = " select name from ad_ref_list where ad_reference_id=(select ad_reference_id from "+
			 "ad_reference where name='Beta_Qtn_DataRack_Model') and value='" + datarack_model +"'";
			 String dr_model = DB.getSQLValueString(get_TrxName(), sqldr);
			 			 
			 prod_desc = datarack_model + "-" + datarack_frame;
			 
			 String sql_upd_dr_rem = " update c_quotationline set remarks ='" + dr_model + "' where c_quotationline_id = "+ record_id;
		     int updrem_status = DB.executeUpdate(sql_upd_dr_rem, get_TrxName());
		 }
		 //End of Prod.description for Data Rack Cabinet
		 
		 //Update Prod.description for Sterilaire (Added on 16.09.2020)
		 if (PrdVal.equalsIgnoreCase("STERILAIRE"))
		 {
			 prod_desc = "S-" + str_model + " " + a_disp.intValue() + "-" + str_type;
		 	 qtn_prd=prod_desc;
		 	 
		 	 if (str_pctype.equalsIgnoreCase("MF"))
		 		prod_desc = prod_desc + "-WITH MF";
		 	 if (str_ais.equalsIgnoreCase("N"))
		 		prod_desc = prod_desc + "-WITHOUT AIS";
		 	 if (str_metal.equalsIgnoreCase("G1"))
		 		prod_desc = prod_desc + "-WITH THICKNESS:G1";
		 	 if (str_metal.equalsIgnoreCase("G3"))
		 		prod_desc = prod_desc + "-WITH THICKNESS:G3";
		 	 if (str_fins.equalsIgnoreCase("N"))
		 		prod_desc = prod_desc + "-WITHOUT FOAM INS.";
		 	if (str_reg.equalsIgnoreCase("Y"))
		 		prod_desc = prod_desc + "-WITH REGULATOR";
		 	 
		 }
		 //End of Update Prod.description for Sterilaire
		 
		 //Update Prod.description for EDH (Added on 04.10.2020)
		 if (PrdVal.equalsIgnoreCase("EDH"))
		 {
			 if (a==100)
				 prod_desc = prod_desc.trim() + "-235X205";
			 else if (a==150)
			 	prod_desc = prod_desc.trim() + "-235X205";
			 else if (a==200)
				 	prod_desc = prod_desc.trim() + "-285X230";
			 else if (a==250)
				 	prod_desc = prod_desc.trim() + "-335X290";
			 else if (a==300)
				 	prod_desc = prod_desc.trim() + "-385X355";
			 else if (a==350)
				 	prod_desc = prod_desc.trim() + "-490X420";
			 else if (a==400)
				 	prod_desc = prod_desc.trim() + "-590X415";
			 
			 prod_desc = prod_desc.trim() + "-"+ dh_kw + "KW-" + dh_phase;
			 if (dh_pl.equalsIgnoreCase("Y"))
				 prod_desc = prod_desc + "-" + "PL";
			 else
				 prod_desc = prod_desc + "-" + "WPL";
			 prod_desc = prod_desc + "-" + dh_metal + "-" + dh_stage;
		 }
		 //End of Update Prod.description for EDH
		 
		 //Update Prod.description for FDC/SLV, FDCA/SLV & FDF/SLV
		 if (PrdVal.equalsIgnoreCase("FDC/SLV") | PrdVal.equalsIgnoreCase("FDCA/SLV"))
		 {
			 if (fd_slv.equalsIgnoreCase("0.9MM"))
				 prod_desc = prod_desc.trim() + "(09) ";
			 else if (fd_slv.equalsIgnoreCase("1.2MM"))
				 prod_desc = prod_desc.trim() + "(12) ";
			 else if (fd_slv.equalsIgnoreCase("1.5MM"))
				 prod_desc = prod_desc.trim() + "(15) ";
			 else if (fd_slv.equalsIgnoreCase("2MM"))
				 prod_desc = prod_desc.trim() + "(20) ";	 
			 if (PrdVal.equalsIgnoreCase("FDC/SLV"))
				 prod_desc = prod_desc + "100% FREE AREA ";
			 else if (PrdVal.equalsIgnoreCase("FDCA/SLV"))
				 prod_desc = prod_desc + "NOT 100% FREE AREA ";
		  }		 
		 else if (PrdVal.equalsIgnoreCase("FDF/SLV"))
			 prod_desc = prod_desc.trim() + "(12) ";
		 
		 //End of Update Prod.description for FDC/SLV, FDCA/SLV & FDF/SLV
		 
		 //Update LT-R-T to product desc for MFSD-NEW,BEMFSD-NEW,BMFD-NEW, BEFMD-NEW Items
		 if (PrdVal.equalsIgnoreCase("BEMFSD-NEW") | PrdVal.equalsIgnoreCase("BMSD-NEW") | 
			 PrdVal.equalsIgnoreCase("BMFSD-NEW") | PrdVal.equalsIgnoreCase("BMFD-NEW") | 
			 PrdVal.equalsIgnoreCase("BEMFD-NEW")) 
		 {
			 rowLT = sheet.getRow(4);
			 HSSFCell cellLT = rowLT.getCell(6);
			 
			 CellValue cellValueLT = evaluator.evaluate(cellLT);
			 strLT = cellValueLT.getStringValue();
			 strLT=strLT + "";
			 
			 if (strLT.equalsIgnoreCase("LT"))
			 {
				 if (PrdVal.equalsIgnoreCase("BEMFSD-NEW"))
					  prod_desc = "BEMFSDLT";
				 else if (PrdVal.equalsIgnoreCase("BMFSD-NEW"))
					  prod_desc = "BMFSDLT";
				 else if (PrdVal.equalsIgnoreCase("BMSD-NEW"))
					  prod_desc = "BMSDLT";
				 else if (PrdVal.equalsIgnoreCase("BMFD-NEW"))
					  prod_desc = "BMFDLT";
				 else if (PrdVal.equalsIgnoreCase("BEMFD-NEW"))
					  prod_desc = "BEMFDLT";
			 }
			 else
			 {
				 if (PrdVal.equalsIgnoreCase("BEMFSD-NEW"))
					  prod_desc = "BEMFSD";
				 else if (PrdVal.equalsIgnoreCase("BMFSD-NEW"))
					  prod_desc = "BMFSD";
				 else if (PrdVal.equalsIgnoreCase("BMSD-NEW"))
					  prod_desc = "BMSD"; 
				 else if (PrdVal.equalsIgnoreCase("BMFD-NEW"))
					  prod_desc = "BMFD";
				 else if (PrdVal.equalsIgnoreCase("BEMFD-NEW"))
					  prod_desc = "BEMFD";
			 }
			 if (ul_spigot.equalsIgnoreCase("Y"))
			 {
				 prod_desc = prod_desc +"/R";
				 qtn_prd = prod_desc;
				 
				 String sqlnd1 = " select NVL(NECKDIA,0) from c_quotationline where c_quotationline_id = ? ";
				 BigDecimal nd1 = DB.getSQLValueBD(get_TrxName(), sqlnd1, record_id);
				 prod_desc = prod_desc.trim() + "(" + nd1 + ")";
			 }
			 else
				 qtn_prd = prod_desc;
			 
			 if (PrdVal.equalsIgnoreCase("BEMFSD-NEW") | PrdVal.equalsIgnoreCase("BMFSD-NEW"))
			 {
				 prod_desc = prod_desc +"-T";
				 qtn_prd = qtn_prd+"-T";
			 }
			 if (PrdVal.equalsIgnoreCase("BMFD-NEW") | PrdVal.equalsIgnoreCase("BEMFD-NEW"))
			 {
				 prod_desc = prod_desc +"-" + ul_linktype+"";
				 qtn_prd = qtn_prd+"-" + ul_linktype+"";
			 }
			 if (slyn.equalsIgnoreCase("Y") & ul_spigot.equalsIgnoreCase("N"))
				  prod_desc = prod_desc.trim() + "-XS";
			 
			 prod_desc = prod_desc +" ";
			 
			 
		 }
		 //End of Update LT-R-T to product desc for MFSD-NEW,BEMFSD-NEW,BMSD-NEW,BMFD-NEW, BEFMD-NEW items
		 
		 //Check for Actuator HONEYWELL - MS4604F1210
		  if (PrdVal.equalsIgnoreCase("BEMFSD-T") | PrdVal.equalsIgnoreCase("BMFSD-T") | PrdVal.equalsIgnoreCase("BMSD/R") |
			  PrdVal.equalsIgnoreCase("BEMFSD/R-T") | PrdVal.equalsIgnoreCase("BMFSD/R-T") | PrdVal.equalsIgnoreCase("BMSD") |
			  PrdVal.equalsIgnoreCase("BEMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-T") | PrdVal.equalsIgnoreCase("BEMFD-TF") |
			  PrdVal.equalsIgnoreCase("BEMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-T") | PrdVal.equalsIgnoreCase("BEMFD/R-TF") |
			  PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BMFD-T") | PrdVal.equalsIgnoreCase("BMFD-TF") |
			  PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BMFD/R-T") | PrdVal.equalsIgnoreCase("BMFD/R-TF") )
			  
		  { 
			  if (act_id!=0)
			  {
				  if (ltSeriesYN.equalsIgnoreCase("Y")) 
				  {
					  if (PrdVal.equalsIgnoreCase("BEMFSD-T"))
						  prod_desc = "BEMFSDLT-T"+ ' ';
					  else if (PrdVal.equalsIgnoreCase("BMFSD-T"))
						  prod_desc = "BMFSDLT-T"+ ' ';
					  else if (PrdVal.equalsIgnoreCase("BMSD/R"))
						  prod_desc = "BMSDLT/R"+ ' ';
					  else if (PrdVal.equalsIgnoreCase("BEMFSD/R-T"))
						  prod_desc = "BEMFSDLT/R-T"+ ' ';
					  else if (PrdVal.equalsIgnoreCase("BMFSD/R-T"))
						  prod_desc = "BMFSDLT/R-T"+ ' ';
					  else if (PrdVal.equalsIgnoreCase("BMSD"))
						  prod_desc = "BMSDLT"+ ' ';
					  else if (PrdVal.equalsIgnoreCase("BEMFD-F"))
						  prod_desc = "BEMFDLT-F"+ ' ';
					  else if (PrdVal.equalsIgnoreCase("BEMFD-T"))
						  prod_desc = "BEMFDLT-T"+ ' ';
					  else if (PrdVal.equalsIgnoreCase("BEMFD-TF"))
						  prod_desc = "BEMFDLT-TF"+ ' ';
					  else if (PrdVal.equalsIgnoreCase("BEMFD/R-F"))
						  prod_desc = "BEMFDLT/R-F"+ ' ';
					  else if (PrdVal.equalsIgnoreCase("BEMFD/R-T"))
						  prod_desc = "BEMFDLT/R-T"+ ' ';
					  else if (PrdVal.equalsIgnoreCase("BEMFD/R-TF"))
						  prod_desc = "BEMFDLT/R-TF"+ ' ';
					  else if (PrdVal.equalsIgnoreCase("BMFD-F"))
						  prod_desc = "BMFDLT-F"+ ' ';
					  else if (PrdVal.equalsIgnoreCase("BMFD-T"))
						  prod_desc = "BMFDLT-T"+ ' ';
					  else if (PrdVal.equalsIgnoreCase("BMFD-TF"))
						  prod_desc = "BMFDLT-TF"+ ' ';
					  else if (PrdVal.equalsIgnoreCase("BMFD/R-F"))
						  prod_desc = "BMFDLT/R-F"+ ' ';
					  else if (PrdVal.equalsIgnoreCase("BMFD/R-T"))
						  prod_desc = "BMFDLT/R-T"+ ' ';
					  else if (PrdVal.equalsIgnoreCase("BMFD/R-TF"))
						  prod_desc = "BMFDLT/R-TF"+ ' ';
					  
					  
					  
				  }
					  //prod_desc = prod_desc.trim() + "-LT" + " ";
			  }
				  
		  }
		  //End of Check for Actuator HONEYWELL - MS4604F1210
			
		  
		  //Set Product Code for Access Doors (ACC2L,ACCPHLK etc..) with metal and insulation type
		  if(PrdVal.equalsIgnoreCase("ACC2L") | PrdVal.equalsIgnoreCase("ACC2LD") | PrdVal.equalsIgnoreCase("ACCSH") | 
			    PrdVal.equalsIgnoreCase("ACCPHLK") | PrdVal.equalsIgnoreCase("ACCSHD") |PrdVal.equalsIgnoreCase("ACCPH") |
			    PrdVal.equalsIgnoreCase("ACCSHLK") | PrdVal.equalsIgnoreCase("ACCPHD"))
		  {
			  prod_desc = "ACC" + ad_metal_val + ad_ins_val.toUpperCase()+prod_desc.substring(3,prod_desc.length());
			  
		  }
		  //End of Set Product Code for Access Doors 
			
		 //Check for Curve Y/N
		 String sqlcur = " select NVL(CURVEYN,'N') from c_quotationline where c_quotationline_id = ? ";
		 String curyn = DB.getSQLValueStringEx(get_TrxName(), sqlcur, record_id); 	
		 if (curyn.equalsIgnoreCase("Y"))
			 prod_desc = prod_desc.trim() + "-CRV ";	
			
		  //Check for Corner Y/N
		  String sqlcor = " select NVL(CORNERYN,'N') from c_quotationline where c_quotationline_id = ? ";
		  String coryn = DB.getSQLValueStringEx(get_TrxName(), sqlcor, record_id); 		
		
		  if (coryn.equalsIgnoreCase("Y"))
		  {
			  prod_desc = prod_desc.trim();
			  if (unit.equalsIgnoreCase("I") | unit.equalsIgnoreCase("F"))
				  prod_desc = prod_desc + "/COR 12" + "\" " + "* 12" + "\" " + "(90) * ";
			  else
				  prod_desc = prod_desc + "/COR 300MM * 300MM (90) * ";
		  }
		//End of Check for Corner Y/N	
		  
		//Check for Ways
		  String sqlsetways ="Select nvl(setways,'N') from c_quotationline where c_quotationline_id = ? ";
		  String setways = DB.getSQLValueStringEx(get_TrxName(), sqlsetways, record_id);
		  if (setways.equalsIgnoreCase("N"))
			  ways = "";
		  else
		  {
			  String sqlways = " select NVL(WAYS,'0') from c_quotationline where c_quotationline_id = ? ";
		  	  ways = DB.getSQLValueStringEx(get_TrxName(), sqlways, record_id);
		  }
		  if (ways.equalsIgnoreCase("1"))
			  prod_desc = "1W" + prod_desc ;
		  else if (ways.equalsIgnoreCase("2"))
			  prod_desc = "2W" + prod_desc ;
		  else if (ways.equalsIgnoreCase("3"))
			  prod_desc = "3W" + prod_desc ;
		  else if (ways.equalsIgnoreCase("4"))
			  prod_desc = "4W" + prod_desc ;
	  
		  //End of Check for Ways 
			
		  //Check for T-Bar Ceiling   Added on 23.02.2014
		  if (settbar.equalsIgnoreCase("Y"))
		  {
			  String sqltbar = " select NVL(tbaryn,'N') from c_quotationline where c_quotationline_id = ? ";
		  	  tBarYN = DB.getSQLValueStringEx(get_TrxName(), sqltbar, record_id);
		  	  if (tBarYN.equalsIgnoreCase("Y"))
		  		prod_desc = prod_desc.trim() + "-TB" + " ";
		  }
		  else
			  tBarYN = "N";
		  //End of Check for T-Bar Ceiling
		  
		  //Check for Airway
		  String sqlsetaw = "Select NVL(setairway,'N') from c_quotationline where c_quotationline_id = ? ";
		  String setawstr = DB.getSQLValueStringEx(get_TrxName(), sqlsetaw, record_id);
		  if (setawstr.equalsIgnoreCase("Y"))
		  {
			  String sqlaw = " select NVL(AIRWAY,'N') from c_quotationline where c_quotationline_id = ? ";
			  String awstr = DB.getSQLValueStringEx(get_TrxName(), sqlaw, record_id);
			  if (!(awstr.equalsIgnoreCase("N")))
				  prod_desc = prod_desc.trim() + "" + awstr + "D ";
		  }
		  
		//Check for Neck Dia
		  if (PrdVal.equalsIgnoreCase("BMSD/R") | PrdVal.equalsIgnoreCase("BMFSD/R-T") | PrdVal.equalsIgnoreCase("BEMFSD/R-T") |
			  PrdVal.equalsIgnoreCase("BMFD/R-T") | PrdVal.equalsIgnoreCase("BEMFD/R-T") |
			  PrdVal.equalsIgnoreCase("BFD/R-F") | PrdVal.equalsIgnoreCase("BEFD/R-F") |
			  PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F") |
			  PrdVal.equalsIgnoreCase("BMFD/R-TF") | PrdVal.equalsIgnoreCase("BEMFD/R-TF"))
		  {
			  String sqlnd = " select NVL(NECKDIA,0) from c_quotationline where c_quotationline_id = ? ";
			  BigDecimal nd = DB.getSQLValueBD(get_TrxName(), sqlnd, record_id);
			  if (PrdVal.equalsIgnoreCase("BMSD/R"))
				  prod_desc = prod_desc.trim() + "(" + nd + ") ";
			  else if (PrdVal.equalsIgnoreCase("BMFSD/R-T") | PrdVal.equalsIgnoreCase("BEMFSD/R-T") |
					  PrdVal.equalsIgnoreCase("BMFD/R-T") | PrdVal.equalsIgnoreCase("BEMFD/R-T"))
				  prod_desc = prod_desc.trim() + "(" + nd + ")-T ";
			  else if (PrdVal.equalsIgnoreCase("BFD/R-F") | PrdVal.equalsIgnoreCase("BEFD/R-F") |
					   PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F"))
				  prod_desc = prod_desc.trim() + "(" + nd + ")-F";
			  else if (PrdVal.equalsIgnoreCase("BMFD/R-TF") | PrdVal.equalsIgnoreCase("BEMFD/R-TF"))
				  prod_desc = prod_desc.trim() + "(" + nd + ")-TF";
		  }
		  //End of Check for Neck Dia
		  
		  //Check for Temp
		  if (PrdVal.equalsIgnoreCase("BFD-F") | PrdVal.equalsIgnoreCase("BEFD-F") |
			  PrdVal.equalsIgnoreCase("BFD/R-F") | PrdVal.equalsIgnoreCase("BEFD/R-F") |
			  PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-F") |
			  PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F"))
		  {
			  String sqltemp= " select NVL(temp,'165') from c_quotationline where c_quotationline_id = ? ";
		  	  String sltemp = DB.getSQLValueStringEx(get_TrxName(), sqltemp, record_id);
		  	  prod_desc = prod_desc.trim() + "" + sltemp + " ";
		  }
		  //End of Check for Temp
		  
		  //Check for Without Sleeve Y/N
		  if (PrdVal.equalsIgnoreCase("BMSD") | PrdVal.equalsIgnoreCase("BMFSD-T") |
			  PrdVal.equalsIgnoreCase("BEMFSD-T") | PrdVal.equalsIgnoreCase("BMFD-T") | PrdVal.equalsIgnoreCase("BEMFD-T") |
			  PrdVal.equalsIgnoreCase("BFD-F") | PrdVal.equalsIgnoreCase("BEFD-F") |
			  PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-F") |
			  PrdVal.equalsIgnoreCase("BMFD-TF") | PrdVal.equalsIgnoreCase("BEMFD-TF"))
		  {
			  if (slyn.equalsIgnoreCase("Y"))
				  prod_desc = prod_desc.trim() + "-XS" + " ";
		  }
		  //End of Check for Without Sleeve Y/N
		  
		//Prepare Item Description AVCD Items
		  if (PrdVal.equalsIgnoreCase("AUVCDF") | PrdVal.equalsIgnoreCase("AUVCDB") |
			  PrdVal.equalsIgnoreCase("AUVCDH") | PrdVal.equalsIgnoreCase("AUVCDS") |
			  PrdVal.equalsIgnoreCase("AGVCDF") | PrdVal.equalsIgnoreCase("AGVCDB") | PrdVal.equalsIgnoreCase("AGVCDS") |
			  PrdVal.equalsIgnoreCase("LVCDF") | PrdVal.equalsIgnoreCase("LVCDH") |
			  PrdVal.equalsIgnoreCase("3VCDF") | PrdVal.equalsIgnoreCase("3VCDB") |
			  PrdVal.equalsIgnoreCase("3VCDH") | PrdVal.equalsIgnoreCase("3VCDS") |
			  PrdVal.equalsIgnoreCase("PI3VCD") | PrdVal.equalsIgnoreCase("PIAVCD"))
		  {
			  if (PrdVal.equalsIgnoreCase("PI3VCD") | PrdVal.equalsIgnoreCase("PIAVCD"))
				 prod_desc = flangeVal + prod_desc.trim();
			  if (driveVal.equalsIgnoreCase("M"))
			  	 prod_desc = driveVal + prod_desc.trim();
			  if (!(metalVal.equalsIgnoreCase("N")) && (!(PrdVal.equalsIgnoreCase("PIAVCD"))))
				 prod_desc = prod_desc.trim() + metalVal;
			  else if (!(spindleVal.equalsIgnoreCase("N")) && (PrdVal.equalsIgnoreCase("PIAVCD")))
				  prod_desc = prod_desc.trim() + spindleVal; 
			  if (!(bushVal.equalsIgnoreCase("N")))
				 prod_desc = prod_desc.trim() + bushVal;
			  if (!(mechVal.equalsIgnoreCase("N")))
				 prod_desc = prod_desc.trim() + mechVal;
			  if (PrdVal.equalsIgnoreCase("AUVCDF") | PrdVal.equalsIgnoreCase("LVCDF") | PrdVal.equalsIgnoreCase("3VCDF") | PrdVal.equalsIgnoreCase("AGVCDF"))
				 prod_desc = prod_desc.trim() + "F ";
			  else if (PrdVal.equalsIgnoreCase("AUVCDB") | PrdVal.equalsIgnoreCase("3VCDB") | PrdVal.equalsIgnoreCase("AGVCDB"))
				  prod_desc = prod_desc.trim() + "B ";
			  else if (PrdVal.equalsIgnoreCase("AUVCDH") | PrdVal.equalsIgnoreCase("LVCDH") | PrdVal.equalsIgnoreCase("3VCDH"))
				  prod_desc = prod_desc.trim() + "H ";
			  else if (PrdVal.equalsIgnoreCase("AUVCDS") | PrdVal.equalsIgnoreCase("3VCDS") | PrdVal.equalsIgnoreCase("AGVCDS"))
				  prod_desc = prod_desc.trim() + "S ";
			  else if (PrdVal.equalsIgnoreCase("PI3VCD") | PrdVal.equalsIgnoreCase("PIAVCD"))
				  prod_desc = prod_desc.trim() + " ";
			  
		  }
		  if (PrdVal.equalsIgnoreCase("VDR"))
		  {
			  if (evdr_yn.equalsIgnoreCase("Y"))
				  prod_desc = "EVDR";
			  
			  if (driveVal.equalsIgnoreCase("M"))
				 prod_desc = driveVal + prod_desc.trim();		  
			  if (!(metalVal.equalsIgnoreCase("N")))
				 prod_desc = prod_desc.trim() + metalVal_Vdr;
			  if (!(bushVal.equalsIgnoreCase("N")))
				 prod_desc = prod_desc.trim() + bushVal + " ";
			  
		  }
		  //Add parameter values in description for MPRD
		  if (PrdVal.equalsIgnoreCase("MPRD"))
		  {
			  
			  if (!(mprd_frame.equalsIgnoreCase("N")))
				 prod_desc = prod_desc.trim() + mprd_frame;
			  if (!(mprd_bush.equalsIgnoreCase("N")))
				 prod_desc = prod_desc.trim() + mprd_bush + " ";
			  
		  }
		  //End of Add parameter values in description for MPRD
		  
		//End of Prepare Item Description AVCD Items  
		  
		//Prepare Item Description for CJNP
		  if (PrdVal.equalsIgnoreCase("CJNP200") | PrdVal.equalsIgnoreCase("CJNP250") |
			  PrdVal.equalsIgnoreCase("CJNP300") | PrdVal.equalsIgnoreCase("CJNP350") |
			  PrdVal.equalsIgnoreCase("CJNP400") | PrdVal.equalsIgnoreCase("BJNP125") |
			  PrdVal.equalsIgnoreCase("BJNP160") | PrdVal.equalsIgnoreCase("BJNP200") |
			  PrdVal.equalsIgnoreCase("BJNP250") | PrdVal.equalsIgnoreCase("BJNP315") |
			  PrdVal.equalsIgnoreCase("BJNP400"))  
		  {
			  prod_desc = prod_desc.trim() + cjnpPole_disp + " ";
			  if (PrdVal.equalsIgnoreCase("CJNP200"))
				  prod_desc = prod_desc + "SIZE 200 MM";
			  else if (PrdVal.equalsIgnoreCase("CJNP250"))
				  prod_desc = prod_desc + "SIZE 250 MM";
			  else if (PrdVal.equalsIgnoreCase("CJNP300"))
				  prod_desc = prod_desc + "SIZE 300 MM";
			  else if (PrdVal.equalsIgnoreCase("CJNP350"))
				  prod_desc = prod_desc + "SIZE 350 MM";
			  else if (PrdVal.equalsIgnoreCase("CJNP400"))
				  prod_desc = prod_desc + "SIZE 400 MM";
			  else if (PrdVal.equalsIgnoreCase("BJNP125"))
				  prod_desc = prod_desc + "SIZE 125 MM";
			  else if (PrdVal.equalsIgnoreCase("BJNP160"))
				  prod_desc = prod_desc + "SIZE 160 MM";
			  else if (PrdVal.equalsIgnoreCase("BJNP200"))
				  prod_desc = prod_desc + "SIZE 200 MM";
			  else if (PrdVal.equalsIgnoreCase("BJNP250"))
				  prod_desc = prod_desc + "SIZE 250 MM";
			  else if (PrdVal.equalsIgnoreCase("BJNP315"))
				  prod_desc = prod_desc + "SIZE 315 MM";
			  else if (PrdVal.equalsIgnoreCase("BJNP400"))
				  prod_desc = prod_desc + "SIZE 400 MM";
		  }
		//End of Prepare Item Description for CJNP 
			
		if (PrdVal.equalsIgnoreCase("FAN-BVN"))
 	    	{
 				prod_desc = "SV-" + ca_model_bvn + "-" + ca_type_val + "-" + ca_met_val + "-" + ca_pc_val + "-" + ca_bd_val + "-" + ca_ais_val;
 				String catdesc=ca_model_bvn;
 				
 				if (ca_fins_bvn.equalsIgnoreCase("Y"))
 					prod_desc = prod_desc + "-INS";
 				if (bvn_gland.equalsIgnoreCase("Y") | bvn_ip66.equalsIgnoreCase("Y") | bvn_flanges.equalsIgnoreCase("Y") |
 					bvn_ains.equalsIgnoreCase("Y"))
 				{
 					prod_desc = prod_desc + " (WITH ";
 					if (bvn_gland.equalsIgnoreCase("Y"))
 						prod_desc = prod_desc + "GLAND WITH CLIP,";
 					if (bvn_ip66.equalsIgnoreCase("Y"))
 						prod_desc = prod_desc + "IP66 JUNCTION BOX,";
 					if (bvn_flanges.equalsIgnoreCase("Y"))
 						prod_desc = prod_desc + "LONG FLANGES,";
 					if (bvn_ains.equalsIgnoreCase("Y"))
 						prod_desc = prod_desc + "ACOUSTIC INSULATION,";
 					prod_desc = prod_desc.substring(0, prod_desc.length()-1) + ")";
 				}
 				catdesc = catdesc + "-FORWARD CURVED CENTRIFUGAL"; 
 				if (ca_type_val.equalsIgnoreCase("S"))
 					catdesc = catdesc + " SINGLE CABINET FAN";
 				else if (ca_type_val.equalsIgnoreCase("T"))
 					catdesc = catdesc + " TWIN CABINET FAN";
  				
	    		String sql_strprd="Update c_quotationline set remarks='" + catdesc +"'" + 
	    	   		" WHERE c_quotationline_id = " + record_id;	
	    	    int noulstr = DB.executeUpdate(sql_strprd, get_TrxName());
 	    	}
 	    //End of Prepare Item Description for FAN CASING-BVN   
		  
		//Prepare Item Description for PGL
		  if (PrdVal.equalsIgnoreCase("PGL"))
		  {
			  prod_desc = prod_desc.trim() + "-" + pgl_met_val + "";
			  if (pgl_fgm_val.equalsIgnoreCase("Y"))
				  prod_desc = prod_desc + "-F ";
			  else
				  prod_desc = prod_desc + "-N ";
		  }
		//End of Prepare Item Description for PGL
		 
		//Add TBar in item description for P4W,P4WB,P4WM  
		if (PrdVal.equalsIgnoreCase("P4W") | PrdVal.equalsIgnoreCase("P4WB") | PrdVal.equalsIgnoreCase("P4WM"))
		{
		    if (p4w_tbaryn.equalsIgnoreCase("Y"))  
		    {
		    	prod_desc = prod_desc.trim() + "-TB ";
		    }
		}
		//End of Add T-Bar in item description for P4W,P4WB,P4WM  
		  
       if (supply == 0 && ( a > 0 && b > 0))	
       {
           //For Louvers By Area
    	   if (lvByArea.equalsIgnoreCase("Y"))
    		   prod_desc = prod_desc + a_disp + " M²";
    	   //End of For Louvers By Area
    	   else if (PrdVal.equalsIgnoreCase("SLD") | PrdVal.equalsIgnoreCase("RLD")  |
			    	PrdVal.equalsIgnoreCase("RLDF") | PrdVal.equalsIgnoreCase("RLD45") | PrdVal.equalsIgnoreCase("RLD-CRV") |
			    	PrdVal.equalsIgnoreCase("SLDF") | PrdVal.equalsIgnoreCase("SLD45") | PrdVal.equalsIgnoreCase("JZ-SLD") |
			    	PrdVal.equalsIgnoreCase("RLD2F") | PrdVal.equalsIgnoreCase("RLD45-20") |
			    	PrdVal.equalsIgnoreCase("SLD2F") | PrdVal.equalsIgnoreCase("SLD45-20"))
		   {
    		   if (coryn.equalsIgnoreCase("Y"))
    		   {
    			   if (unit.equalsIgnoreCase("I"))
    				   prod_desc = prod_desc + b_disp.intValue() + " SLT" ;
    			   else if (unit.equalsIgnoreCase("F"))
    				   prod_desc = prod_desc + b_disp.intValue() + " SLT" ;
    			   else if (unit.equalsIgnoreCase("N"))
    				   prod_desc = prod_desc + b_disp.intValue() + " SLT" ;
    			   else
    				   prod_desc = prod_desc + b_disp.intValue() + " SLT" ;
    		   }
    		   else
    		   {
    			   if (unit.equalsIgnoreCase("I"))
    				   prod_desc = prod_desc + a +  "\" * " + b_disp.intValue() + " SLT" ;
    			   else if (unit.equalsIgnoreCase("F"))
    				   prod_desc = prod_desc + a +  " F * " + b_disp.intValue() + " SLT" ;
    			   else if (unit.equalsIgnoreCase("N"))
    				   prod_desc = prod_desc + a +  " M * " + b_disp.intValue() + " SLT" ;
    			   else
    				   prod_desc = prod_desc + a + " MM * " + b_disp.intValue() + " SLT" ;
    		   }
		   }
    	   else if (PrdVal.equalsIgnoreCase("RLG16") | PrdVal.equalsIgnoreCase("RLRM16") | PrdVal.equalsIgnoreCase("RLRB16") | 
		    		PrdVal.equalsIgnoreCase("RLRB16/RLG16") | PrdVal.equalsIgnoreCase("RLRM16/RLG16") | PrdVal.equalsIgnoreCase("SLG16") |
		    		PrdVal.equalsIgnoreCase("SLRM16") | PrdVal.equalsIgnoreCase("SLRB16") | PrdVal.equalsIgnoreCase("SLRB16/SLG16") | 
		    		PrdVal.equalsIgnoreCase("SLRM16/SLG16") | PrdVal.equalsIgnoreCase("RLG12") | PrdVal.equalsIgnoreCase("RLRM12") | 
		    		PrdVal.equalsIgnoreCase("RLRB12") | PrdVal.equalsIgnoreCase("RLRB12/RLG12") | PrdVal.equalsIgnoreCase("RLRM12/RLG12") | 
		    		PrdVal.equalsIgnoreCase("SLG12") | PrdVal.equalsIgnoreCase("SLRM12") | PrdVal.equalsIgnoreCase("SLRB12") | 
				    PrdVal.equalsIgnoreCase("SLRB12/SLG12") | PrdVal.equalsIgnoreCase("SLRM12/SLG12") |
				    PrdVal.equalsIgnoreCase("SLRB12-12.5/SLG12-12.5") | PrdVal.equalsIgnoreCase("SLRB12-20/SLG12-20") |
				    PrdVal.equalsIgnoreCase("SLRB16-12.5/SLG16-12.5") | PrdVal.equalsIgnoreCase("SLRB16-20/SLG16-20") |
				    PrdVal.equalsIgnoreCase("SLRM12-12.5/SLG12-12.5") | PrdVal.equalsIgnoreCase("SLRM12-20/SLG12-20") |
				    PrdVal.equalsIgnoreCase("SLRM16-12.5/SLG16-12.5") | PrdVal.equalsIgnoreCase("SLRM16-20/SLG16-20") |
				    PrdVal.equalsIgnoreCase("RLRB12-20/RLG12-20") |  PrdVal.equalsIgnoreCase("RLRB16-20/RLG16-20") |
				    PrdVal.equalsIgnoreCase("RLRM12-20/RLG12-20") |  PrdVal.equalsIgnoreCase("RLRM16-20/RLG16-20") |
				    PrdVal.equalsIgnoreCase("RLG12-20") | PrdVal.equalsIgnoreCase("RLG16-20") | PrdVal.equalsIgnoreCase("SLG12-12.5") |
				    PrdVal.equalsIgnoreCase("SLG12-20") | PrdVal.equalsIgnoreCase("SLG16-12.5") | PrdVal.equalsIgnoreCase("SLG16-20") | 
				    PrdVal.equalsIgnoreCase("RLRB12-20") | PrdVal.equalsIgnoreCase("RLRB16-20") | PrdVal.equalsIgnoreCase("RLRM12-20") | 
				    PrdVal.equalsIgnoreCase("RLRM16-20") | PrdVal.equalsIgnoreCase("SLRB12-12.5") | PrdVal.equalsIgnoreCase("SLRB12-20") | 
				    PrdVal.equalsIgnoreCase("SLRB16-12.5") | PrdVal.equalsIgnoreCase("SLRB16-20") | PrdVal.equalsIgnoreCase("SLRM12-12.5") | 
				    PrdVal.equalsIgnoreCase("SLRM12-20") | PrdVal.equalsIgnoreCase("SLRM16-12.5") | PrdVal.equalsIgnoreCase("SLRM16-20") |
				    PrdVal.equalsIgnoreCase("JZ-RLG16-S") | PrdVal.equalsIgnoreCase("JZ-RLG16-D"))
    	   {
    		   if (coryn.equalsIgnoreCase("Y"))
    		   {
    			   if (unit.equalsIgnoreCase("I"))
    				   prod_desc = prod_desc + b + "\" " ;
    			   else if (unit.equalsIgnoreCase("F"))
    				   prod_desc = prod_desc + b + "\" " ;
    			   else if (unit.equalsIgnoreCase("N"))
    			   {
    				   if (b_unit.equalsIgnoreCase("I"))
    					   prod_desc = prod_desc + b + "\" " ;
    				   else 
    					   prod_desc = prod_desc + b_disp.intValue() + " MM" ;
    			   }   
    			   else
    				   prod_desc = prod_desc + b_disp.intValue() + " MM" ;
    		   }
    		   else
    		   {
    			   if (unit.equalsIgnoreCase("I"))
    				   prod_desc = prod_desc + a +  "\" * " + b + "\" " ;
    			   else if (unit.equalsIgnoreCase("F"))
    				   prod_desc = prod_desc + a +  " F * " + b + "\" " ;
    			   else if (unit.equalsIgnoreCase("N"))
    			   {
    				   if (b_unit.equalsIgnoreCase("I"))
    					   prod_desc = prod_desc + a +  " M * " + b + "\" " ;
    				   else
    					   prod_desc = prod_desc + a +  " M * " + b_disp.intValue() + " MM" ;
    			   }
    			   else
    				   prod_desc = prod_desc + a + " MM * " + b_disp.intValue() + " MM" ;
    		   }
    	   }
    	   else if (PrdVal.equalsIgnoreCase("SADB") | PrdVal.equalsIgnoreCase("SADM") | 
    			    PrdVal.equalsIgnoreCase("RAD") | PrdVal.equalsIgnoreCase("SADM-EG") | 
    			    PrdVal.equalsIgnoreCase("SADB-EG"))
    	   {
    		   if (coryn.equalsIgnoreCase("Y"))
    		   {
    			   if (unit.equalsIgnoreCase("I"))
	    		       prod_desc = prod_desc + b + "\" " ;
	    		   else if (unit.equalsIgnoreCase("F"))
	    		       prod_desc = prod_desc + b + " F" ;
	    		   else if (unit.equalsIgnoreCase("N"))
	    		       prod_desc = prod_desc + b + " M" ;
	    		   else
	    		       prod_desc = prod_desc + b + " MM" ;
    		   }
    		   else
    		   {
    			   if (unit.equalsIgnoreCase("I"))
	    		       prod_desc = prod_desc + a_disp.intValue() +  "\" * " + b_disp.intValue() + "\" " ;
	    		   else if (unit.equalsIgnoreCase("F"))
	    		       prod_desc = prod_desc + a +  " F * " + b + " F" ;
	    		   else if (unit.equalsIgnoreCase("N"))
	    		       prod_desc = prod_desc + a +  " M * " + b + " M" ;
	    		   else
	    		       prod_desc = prod_desc + a_disp.intValue() + " MM * " + b_disp.intValue() + " MM" ;
    		   }
    	   }	   
    	   else if (PrdVal.equalsIgnoreCase("SARB")| PrdVal.equalsIgnoreCase("SARM") | 
		    		PrdVal.equalsIgnoreCase("SAG") | PrdVal.equalsIgnoreCase("RARB") |
		    		PrdVal.equalsIgnoreCase("RARB-45") | PrdVal.equalsIgnoreCase("RARM") |
		    		PrdVal.equalsIgnoreCase("RARM-45")| PrdVal.equalsIgnoreCase("RAG") | PrdVal.equalsIgnoreCase("RAG-45") |
		    		PrdVal.equalsIgnoreCase("RARB-45-EG") | PrdVal.equalsIgnoreCase("SAG20") | PrdVal.equalsIgnoreCase("SAG12.5") |
		    		PrdVal.equalsIgnoreCase("RAG20") | PrdVal.equalsIgnoreCase("RARB20") | PrdVal.equalsIgnoreCase("RARM20") |
		    		PrdVal.equalsIgnoreCase("SARB12.5") | PrdVal.equalsIgnoreCase("SARB20") |
			 		PrdVal.equalsIgnoreCase("SARM12.5") | PrdVal.equalsIgnoreCase("SARM20") | PrdVal.equalsIgnoreCase("RAG20-45") |
			 		PrdVal.equalsIgnoreCase("RARM20-45") | PrdVal.equalsIgnoreCase("RARB20-45"))
		   {
	   		   if (coryn.equalsIgnoreCase("Y"))
	   		   {
	   			   if (unit.equalsIgnoreCase("I"))
		    		       prod_desc = prod_desc + b + "\" " ;
		    		   else if (unit.equalsIgnoreCase("F"))
		    		       prod_desc = prod_desc + b + " F" ;
		    		   else if (unit.equalsIgnoreCase("N"))
		    		       prod_desc = prod_desc + b + " M" ;
		    		   else
		    		       prod_desc = prod_desc + b + " MM" ;
	   		   }
	   		   else
	   		   {
	   			   if (unit.equalsIgnoreCase("I"))
		    		       prod_desc = prod_desc + a +  "\" * " + b + "\" " ;
		    		   else if (unit.equalsIgnoreCase("F"))
		    		       prod_desc = prod_desc + a +  " F * " + b + " F" ;
		    		   else if (unit.equalsIgnoreCase("N"))
		    		       prod_desc = prod_desc + a +  " M * " + b + " M" ;
		    		   else
		    		       prod_desc = prod_desc + a_disp.intValue() + " MM * " + b_disp.intValue() + " MM" ;
	   		   }
	   	   }	
    	   
    	   // Added on 28/11/2013
    	   else if (PrdVal.equalsIgnoreCase("GAL") | PrdVal.equalsIgnoreCase("GAL-CR-IS") |
   			    PrdVal.equalsIgnoreCase("GAL-IS")| PrdVal.equalsIgnoreCase("GAL-CR") |
   			    PrdVal.equalsIgnoreCase("GALB") | PrdVal.equalsIgnoreCase("GALB-CR-IS") |
			    PrdVal.equalsIgnoreCase("GALB-IS")| PrdVal.equalsIgnoreCase("GALB-CR") |
   			    PrdVal.equalsIgnoreCase("NRDE") | PrdVal.equalsIgnoreCase("NRDE-CR") |
   			    PrdVal.equalsIgnoreCase("NRD") | PrdVal.equalsIgnoreCase("NRD-CR") |
   			    PrdVal.equalsIgnoreCase("AF") | PrdVal.equalsIgnoreCase("AF2") | PrdVal.equalsIgnoreCase("STLA") |
   			    PrdVal.equalsIgnoreCase("STLA-IS") | PrdVal.equalsIgnoreCase("STLA-SF") |
   			    PrdVal.equalsIgnoreCase("STLA-IS-SF") | PrdVal.equalsIgnoreCase("STLCA")	| 
   			    PrdVal.equalsIgnoreCase("STLCA-SF")| PrdVal.equalsIgnoreCase("STLCA-IS") | 
   			    PrdVal.equalsIgnoreCase("STLCA-IS-SF") | PrdVal.equalsIgnoreCase("EAL2") |
   			    PrdVal.equalsIgnoreCase("EAL2-IS") | PrdVal.equalsIgnoreCase("FAL") |
   			    PrdVal.equalsIgnoreCase("FALDB") | PrdVal.equalsIgnoreCase("FALDM") |
   			    PrdVal.equalsIgnoreCase("FALHDB") | PrdVal.equalsIgnoreCase("FALHDM") |
   			    PrdVal.equalsIgnoreCase("FALH") | PrdVal.equalsIgnoreCase("EAL4") |
   			    PrdVal.equalsIgnoreCase("EAL4-IS") | PrdVal.equalsIgnoreCase("FVCD") | PrdVal.equalsIgnoreCase("SVCD") |
   			    PrdVal.equalsIgnoreCase("BVCD") | PrdVal.equalsIgnoreCase("MVCD") | PrdVal.equalsIgnoreCase("FVCDSSBR") |
   			    PrdVal.equalsIgnoreCase("ACC2L") | PrdVal.equalsIgnoreCase("ACC2LD") | PrdVal.equalsIgnoreCase("ACCSH") | 
   			    PrdVal.equalsIgnoreCase("ACCPHLK") | PrdVal.equalsIgnoreCase("ACCSHD") |PrdVal.equalsIgnoreCase("ACC2L2") |
   			    PrdVal.equalsIgnoreCase("ACCSHLK") | PrdVal.equalsIgnoreCase("ACCPHD") | PrdVal.equalsIgnoreCase("ACCPH") |
   			    PrdVal.equalsIgnoreCase("FDF") | PrdVal.equalsIgnoreCase("FDC") | PrdVal.equalsIgnoreCase("FDCA") |
   			    PrdVal.equalsIgnoreCase("SLVF12") |PrdVal.equalsIgnoreCase("SLVC09") | PrdVal.equalsIgnoreCase("SLVC12") |
   			    PrdVal.equalsIgnoreCase("SLVC15") | PrdVal.equalsIgnoreCase("SLVC20")|PrdVal.equalsIgnoreCase("SLVA09") | 
   			    PrdVal.equalsIgnoreCase("SLVA12") | PrdVal.equalsIgnoreCase("SLVA15") | PrdVal.equalsIgnoreCase("SLVA20") |
   			    PrdVal.equalsIgnoreCase("SLVF12") | PrdVal.equalsIgnoreCase("SSFVCD") | PrdVal.equalsIgnoreCase("SSSVCD") |
   			    PrdVal.equalsIgnoreCase("SSBVCD") | PrdVal.equalsIgnoreCase("SSMVCD") |
    	        PrdVal.equalsIgnoreCase("STLA-SF2") | PrdVal.equalsIgnoreCase("STLA-IS-SF2") |
    	        PrdVal.equalsIgnoreCase("STLCA-SF2") | PrdVal.equalsIgnoreCase("STLCA-IS-SF2") |
    	        PrdVal.equalsIgnoreCase("SSWM") | PrdVal.equalsIgnoreCase("EAL2-SSWM") | PrdVal.equalsIgnoreCase("EAL4-SSWM") |
    	        PrdVal.equalsIgnoreCase("GAL-SSWM") | PrdVal.equalsIgnoreCase("GALB-CR-SSWM") | PrdVal.equalsIgnoreCase("GAL-CR-SSWM") |
    	        PrdVal.equalsIgnoreCase("STLCA-SSWM") | PrdVal.equalsIgnoreCase("STLCA-SSWM-SF") | PrdVal.equalsIgnoreCase("STLA-SSWM") |
    	        PrdVal.equalsIgnoreCase("STLA-SSWM-SF") | PrdVal.equalsIgnoreCase("STLA-SSWM-SF2") | PrdVal.equalsIgnoreCase("STLCA-SSWM-SF2") |
    	        PrdVal.equalsIgnoreCase("PRD") | PrdVal.equalsIgnoreCase("DPRD") | PrdVal.equalsIgnoreCase("IPRD") |
    	        PrdVal.equalsIgnoreCase("FVCDBB") | PrdVal.equalsIgnoreCase("P4WB") | PrdVal.equalsIgnoreCase("FVCD-SMC") |
    	        PrdVal.equalsIgnoreCase("16FVCD") | PrdVal.equalsIgnoreCase("P4WM") | PrdVal.equalsIgnoreCase("P4W") | 
    	        PrdVal.equalsIgnoreCase("BMSD") | PrdVal.equalsIgnoreCase("BMFSD-T") | PrdVal.equalsIgnoreCase("BEMFSD-T") |
    	        PrdVal.equalsIgnoreCase("BMSD/R") | PrdVal.equalsIgnoreCase("BMFSD/R-T") | PrdVal.equalsIgnoreCase("BEMFSD/R-T") |
    	        PrdVal.equalsIgnoreCase("BMFD-T") | PrdVal.equalsIgnoreCase("BEMFD-T") | PrdVal.equalsIgnoreCase("BMFD/R-T") | 
    	        PrdVal.equalsIgnoreCase("BEMFD/R-T") | PrdVal.equalsIgnoreCase("BFD-F") | PrdVal.equalsIgnoreCase("BEFD-F") |
    	        PrdVal.equalsIgnoreCase("BFD/R-F") | PrdVal.equalsIgnoreCase("BEFD/R-F") |
    	        PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-F") |
    	        PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F") |
    	        PrdVal.equalsIgnoreCase("BMFD-TF") | PrdVal.equalsIgnoreCase("BEMFD-TF") |
    	        PrdVal.equalsIgnoreCase("BMFD/R-TF") | PrdVal.equalsIgnoreCase("BEMFD/R-TF") | 
    	        PrdVal.equalsIgnoreCase("AUVCDF") | PrdVal.equalsIgnoreCase("AUVCDB") |
				PrdVal.equalsIgnoreCase("AUVCDH") | PrdVal.equalsIgnoreCase("AUVCDS") |
				PrdVal.equalsIgnoreCase("AGVCDF") | PrdVal.equalsIgnoreCase("AGVCDB") | PrdVal.equalsIgnoreCase("AGVCDS") |
				PrdVal.equalsIgnoreCase("LVCDF") | PrdVal.equalsIgnoreCase("LVCDH") |
				PrdVal.equalsIgnoreCase("3VCDF") | PrdVal.equalsIgnoreCase("3VCDB") |
				PrdVal.equalsIgnoreCase("3VCDH") | PrdVal.equalsIgnoreCase("3VCDS") |
				PrdVal.equalsIgnoreCase("PI3VCD") | PrdVal.equalsIgnoreCase("PIAVCD") | PrdVal.equalsIgnoreCase("GALB-SSWM") |
				PrdVal.equalsIgnoreCase("BMSD-NEW") | PrdVal.equalsIgnoreCase("BMFSD-NEW") | 
				PrdVal.equalsIgnoreCase("BEMFSD-NEW") | PrdVal.equalsIgnoreCase("BMFD-NEW") |
				PrdVal.equalsIgnoreCase("BEMFD-NEW") | PrdVal.equalsIgnoreCase("FDC/SLV") | 
				PrdVal.equalsIgnoreCase("FDCA/SLV") | PrdVal.equalsIgnoreCase("FDF/SLV") | PrdVal.equalsIgnoreCase("MPRD"))
    		   
		   {
    		   if (unit.equalsIgnoreCase("I"))
		  	       prod_desc = prod_desc + a +  "\" * " + b +  "\" " ;
		   	   else
		   		   prod_desc = prod_desc + a_disp.intValue() +  " MM * " + b_disp.intValue() +  " MM" ;
		   }
    	   
    	   else if (PrdVal.equalsIgnoreCase("PGL") | PrdVal.equalsIgnoreCase("RSA"))
    		   prod_desc = prod_desc + a_disp.intValue() +  " MM * " + b_disp.intValue() +  " MM * " + len_disp.intValue() + " MM";
    	     // End Modified by Abdulla on 19/12/2012
    	   else if (PrdVal.equalsIgnoreCase("SILAX"))
    		   prod_desc = prod_desc + lenDia + " - " + a_disp.intValue() +  " Ø - " + b_disp +  " L";
    	   else if (PrdVal.equalsIgnoreCase("SILAX-P"))
    		   prod_desc = prod_desc.trim() + lenDia + " - " + a_disp.intValue() +  " Ø - " + b_disp +  " L";
    	   else if (PrdVal.equalsIgnoreCase("AL1") | PrdVal.equalsIgnoreCase("AL2"))
    		   prod_desc = prod_desc + a_disp.intValue() +  " MM * " + b_disp.intValue() +  " MM"; // * 297 MM";
    	   else if (PrdVal.equalsIgnoreCase("RAD-GNR") |
			    PrdVal.equalsIgnoreCase("SADB-GNR") | PrdVal.equalsIgnoreCase("SADM-GNR") |
			    PrdVal.equalsIgnoreCase("SADB-EG-GNR") | PrdVal.equalsIgnoreCase("SADM-EG-GNR"))
    		   	if (unit.equalsIgnoreCase("I"))
		  	       prod_desc = prod_desc + a +  "\" * " + b +  "\" GN " + len/25 +  "\" ";
    		   	else
		   		   prod_desc = prod_desc + a_disp.intValue() +  " MM * " + b_disp.intValue() +  " MM GN " + len_disp.intValue() + " MM";
    	   else if (PrdVal.equalsIgnoreCase("RAD-GNS") |
   			    PrdVal.equalsIgnoreCase("SADB-GNS") | PrdVal.equalsIgnoreCase("SADM-GNS") |
   			    PrdVal.equalsIgnoreCase("SADB-EG-GNS") | PrdVal.equalsIgnoreCase("SADM-EG-GNS"))
       		   	if (unit.equalsIgnoreCase("I"))
   		  	       prod_desc = prod_desc + a +  "\" * " + b +  "\" GN " + gnAVal +  "\" * " + gnBVal +  "\"";
       		   	else
   		   		   prod_desc = prod_desc + a_disp.intValue() +  " MM * " + b_disp.intValue() +  " MM GN " + gnAVal_disp.intValue() + " MM * " + gnBVal_disp.intValue() + " MM";
    	   //Added on 10/Nov/2013
    	   else if (PrdVal.equalsIgnoreCase("PAG") | PrdVal.equalsIgnoreCase("PARM") | PrdVal.equalsIgnoreCase("PARB") |
    			   PrdVal.equalsIgnoreCase("ECG") | PrdVal.equalsIgnoreCase("ECRM") | PrdVal.equalsIgnoreCase("ECRB") |
    			   PrdVal.equalsIgnoreCase("FAG") | PrdVal.equalsIgnoreCase("FAG-45") | PrdVal.equalsIgnoreCase("FECH") |
    			   PrdVal.equalsIgnoreCase("FAGH") | PrdVal.equalsIgnoreCase("FAGH-45") | PrdVal.equalsIgnoreCase("DG") |
    			   PrdVal.equalsIgnoreCase("OBDM") | PrdVal.equalsIgnoreCase("OBDB"))
    		   if (unit.equalsIgnoreCase("I"))
    			   prod_desc = prod_desc + a +  "\" * " + b + "\" " ;
    		   else
    			   prod_desc = prod_desc + a_disp.intValue() + " MM * " + b_disp.intValue() + " MM" ;	
    	   //10/Nov/2013
    	   
    	   //Prepare Item Description for Trunking & Cable Tray  31/12/2019
  	    	else if (PrdVal.equalsIgnoreCase("TRUNKING"))
  	    	{
  	    		prod_desc = ct_cot_val + "-TR-" + a_disp.intValue() + 'X' + b_disp.intValue() + "-" + tr_thick_val + "MM-" + ct_len_val + "";
  	    	}
  	    	else if (PrdVal.equalsIgnoreCase("CABLETRAY"))	//Description for Cable Tray  09/02/2020
  	    	{
  	    		prod_desc = ct_cot_val + "-CT-" + a_disp.intValue() + 'X' + b_disp.intValue() + "-" + tr_thick_val + "MM-" + ct_len_val + "";
  	    	}
    	    else if (PrdVal.equalsIgnoreCase("CT-COVER"))	//Description for Cable Tray Cover 09/02/2020
 	    	{
 	    		prod_desc = ct_cot_val + "-CT-COVER-" + a_disp.intValue() + 'X' + b_disp.intValue() + "-" + tr_thick_val + "MM-" + ct_len_val + "";
 	    	}
    	    else if (PrdVal.equalsIgnoreCase("CT-REDU"))	//Description for Cable Tray Reducers 09/02/2020
 	    	{
 	    		prod_desc = ct_cot_val + "-CT-REDU-" + a_disp.intValue() + 'X' + b_disp.intValue() + "-" + tr_thick_val + "MM-" + ct_hand + "";
 	    	}
    	    else if (PrdVal.equalsIgnoreCase("CT-RISER"))	//Description for Cable Tray RISER 03/08/2020
 	    	{
 	    		prod_desc = ct_cot_val + "-CT-RISER-" + a_disp.intValue() + 'X' + b_disp.intValue() + "-" + tr_thick_val + "MM-" + ct_riser_desc + "";
 	    	}
    	    else if (PrdVal.equalsIgnoreCase("CT-ACC-3W-E"))	//Description for CT-ACC-3W_E 16/08/2020
 	    	{
 	    		prod_desc = ct_cot_val + "-CT-ACC-3W-" + a_disp.intValue() + 'X' + b_disp.intValue() + "-" + tr_thick_val + "MM-EQUAL";
 	    	}  
    	    else if (PrdVal.equalsIgnoreCase("CT-ACC-3W-UE"))	//Description for CT-ACC-3W_UE 16/08/2020
 	    	{
 	    		prod_desc = ct_cot_val + "-CT-ACC-3W-" + a_disp.intValue() + 'X' + ct_size2_disp.intValue() + 'X' + ct_size3_disp.intValue() + 'X' + b_disp.intValue() + "-" + tr_thick_val + "MM-UNEQUAL";
 	    	}
    	    else if (PrdVal.equalsIgnoreCase("CT-ACC-4W"))	//Description for CT-ACC-4W 16/08/2020
 	    	{
 	    		prod_desc = ct_cot_val + "-CT-ACC-4W-" + a_disp.intValue() + 'X' + b_disp.intValue() + "-" + tr_thick_val + "MM-EQUAL";
 	    	}  
    	    else if (PrdVal.equalsIgnoreCase("CT-ACC-2W"))	//Description for CT-ACC-2W 16/08/2020
 	    	{
 	    		prod_desc = ct_cot_val + "-CT-ACC-2W-" + a_disp.intValue() + 'X' + b_disp.intValue() + "-" + tr_thick_val + "MM-" + ct_degree_desc + "-EQUAL";
 	    	}      	   
  	    	//End of Prepare Item Description for Trunking & Cable Tray 31/12/2019
    	   
    	    //Prepare Item Description for STLG & STLCG
    	    else if (PrdVal.equalsIgnoreCase("STLG") | PrdVal.equalsIgnoreCase("STLCG"))
    	    {
    	    	prod_desc = prod_desc.trim();
    	    	if (!(stl_is.equalsIgnoreCase("N")))
    	    	{
    	    		prod_desc = prod_desc + "-" + stl_is;
    	    	}
    	    	if (!(stl_filter.equalsIgnoreCase("N")))
    	    	{
    	    		prod_desc = prod_desc + "-" + stl_filter;
    	    	}
    	    	prod_desc = prod_desc + " ";
    	    	if (unit.equalsIgnoreCase("I"))
 		  	       prod_desc = prod_desc + a +  "\" * " + b +  "\" " ;
 		   	    else
 		   		   prod_desc = prod_desc + a_disp.intValue() +  " MM * " + b_disp.intValue() +  " MM" ;
    	    	if (metalVal.equalsIgnoreCase("G1"))
    	    	   prod_desc = prod_desc + " (GI 0.9MM)";
    	    	else if (metalVal.equalsIgnoreCase("G2"))
     	    	   prod_desc = prod_desc + " (GI 1.2MM)";
    	    	else if (metalVal.equalsIgnoreCase("G3"))
     	    	   prod_desc = prod_desc + " (GI 1.5MM)";
    	    	else if (metalVal.equalsIgnoreCase("G4"))
     	    	   prod_desc = prod_desc + " (GI 1.7MM)";
    	    	else if (metalVal.equalsIgnoreCase("S1"))
     	    	   prod_desc = prod_desc + " (SS 1.0MM)";
     	    	else if (metalVal.equalsIgnoreCase("S2"))
      	    	   prod_desc = prod_desc + " (SS 1.2MM)";
     	    	else if (metalVal.equalsIgnoreCase("S3"))
      	    	   prod_desc = prod_desc + " (SS 1.5MM)";
     	    	else if (metalVal.equalsIgnoreCase("S4"))
      	    	   prod_desc = prod_desc + " (SS 1.7MM)";
    	    	
    	    }
    	    //End of Prepare Item Description for STLG & STLCG 
    	   
    	    else
    	    {
    		   if (coryn.equalsIgnoreCase("Y"))
    		   {
	    		   if (unit.equalsIgnoreCase("I"))
	    		       prod_desc = prod_desc + b + "\" " ;
	    		   else if (unit.equalsIgnoreCase("F"))
	    		       prod_desc = prod_desc + b + " F" ;
	    		   else if (unit.equalsIgnoreCase("N"))
	    		       prod_desc = prod_desc + b + " M" ;
	    		   else
	    		       prod_desc = prod_desc + b + " MM" ;
    		   }
    		   else
    		   {
    			   if (unit.equalsIgnoreCase("I"))
	    		       prod_desc = prod_desc + a +  "\" * " + b + "\" " ;
	    		   else if (unit.equalsIgnoreCase("F"))
	    		       prod_desc = prod_desc + a +  " F * " + b + " F" ;
	    		   else if (unit.equalsIgnoreCase("N"))
	    		       prod_desc = prod_desc + a +  " M * " + b + " M" ;
	    		   else
	    		       prod_desc = prod_desc + a + " MM * " + b + " MM" ;
    		   }
    	   }
       }
       else if (supply > 0)
       {
    	   if (PrdVal.equalsIgnoreCase("SLD/RLD") | PrdVal.equalsIgnoreCase("SLDF/RLDF") | PrdVal.equalsIgnoreCase("SLD45/RLD45") |
    		   PrdVal.equalsIgnoreCase("SLD2F/RLD2F") | PrdVal.equalsIgnoreCase("SLD45-20/RLD45-20"))
    	   {
    		    //Add Slot Width in the description
    		    String sqlsw1 = " select NVL(SLOTWIDTH,'N') from c_quotationline where c_quotationline_id = ? ";
   		    	String sw1 = DB.getSQLValueStringEx(get_TrxName(), sqlsw1, record_id); 	
   		    	if (!sw1.equalsIgnoreCase("N"))
   		    		sw1 = "(" + sw1 + ")";
   		    	else
   		    		sw1 = "";
   		    	//********************************
   		    	
    		   if (unit.equalsIgnoreCase("I"))
    		       prod_desc = prod_desc + a +  "\" * " + b_disp.intValue() + " SLT" + sw1 + " S: " + supply + "\" ";
    		   else if (unit.equalsIgnoreCase("F"))
    		       prod_desc = prod_desc + a +  " F * " + b_disp.intValue() + " SLT" + sw1 + " S: " + supply + " F";
    		   else if (unit.equalsIgnoreCase("N"))
    		       prod_desc = prod_desc + a +  " M * " + b_disp.intValue() + " SLT" + sw1 + " S: " + supply + " M";
    		   else
    		       prod_desc = prod_desc + a + " MM * " + b_disp.intValue() + " SLT" + sw1 + " S: " + supply + " MM";
    	   }
    	   else if (PrdVal.equalsIgnoreCase("RLRB16/RLG16") | PrdVal.equalsIgnoreCase("RLRM16/RLG16") | 
				    PrdVal.equalsIgnoreCase("SLRB16/SLG16") | PrdVal.equalsIgnoreCase("SLRM16/SLG16") |
		    		PrdVal.equalsIgnoreCase("RLRB12/RLG12") | PrdVal.equalsIgnoreCase("RLRM12/RLG12") | 
		    		PrdVal.equalsIgnoreCase("RLRM12-20/RLG12-20") |  PrdVal.equalsIgnoreCase("RLRM16-20/RLG16-20") |
		    		 PrdVal.equalsIgnoreCase("RLRB12-20/RLG12-20") |  PrdVal.equalsIgnoreCase("RLRB16-20/RLG16-20") |
				    PrdVal.equalsIgnoreCase("SLRB12/SLG12") | PrdVal.equalsIgnoreCase("SLRM12/SLG12") |
				    PrdVal.equalsIgnoreCase("SLRB12-12.5/SLG12-12.5") | PrdVal.equalsIgnoreCase("SLRB12-20/SLG12-20") |
				    PrdVal.equalsIgnoreCase("SLRB16-12.5/SLG16-12.5") | PrdVal.equalsIgnoreCase("SLRB16-20/SLG16-20") |
				    PrdVal.equalsIgnoreCase("SLRM12-12.5/SLG12-12.5") | PrdVal.equalsIgnoreCase("SLRM12-20/SLG12-20") |
				    PrdVal.equalsIgnoreCase("SLRM16-12.5/SLG16-12.5") | PrdVal.equalsIgnoreCase("SLRM16-20/SLG16-20")   )
    	   {
    		   if (unit.equalsIgnoreCase("I"))
    		       prod_desc = prod_desc + a +  "\" * " + b + "\" " + " S: " + supply + "\" ";
    		   else if (unit.equalsIgnoreCase("F"))
    		       prod_desc = prod_desc + a +  " F * " + b + "\" " + " S: " + supply + " F";
    		   else if (unit.equalsIgnoreCase("N"))
    		   {
    			   if (b_unit.equalsIgnoreCase("I"))
    				   prod_desc = prod_desc + a +  " M * " + b + "\" " + " S: " + supply + " M";
    			   else
    				   prod_desc = prod_desc + a +  " M * " + b_disp.intValue() + " MM" + " S: " + supply + " M";
    		   } 
    		   else
    		       prod_desc = prod_desc + a + " MM * " + b_disp.intValue() + " MM" + " S: " + supply + " MM";
    	   }
    	   else if (PrdVal.equalsIgnoreCase("SARB/SAG") | PrdVal.equalsIgnoreCase("SARM/SAG") |
    			    PrdVal.equalsIgnoreCase("RARB/RAG") | PrdVal.equalsIgnoreCase("RARM/RAG") |
    			    PrdVal.equalsIgnoreCase("RARB-45/RAG-45") | PrdVal.equalsIgnoreCase("RARM-45/RAG-45") | 
    			    PrdVal.equalsIgnoreCase("SAG20") | PrdVal.equalsIgnoreCase("SAG12.5") |
		    		PrdVal.equalsIgnoreCase("RAG20") | PrdVal.equalsIgnoreCase("RARB20") | PrdVal.equalsIgnoreCase("RARM20") |
		    		PrdVal.equalsIgnoreCase("SARB12.5") | PrdVal.equalsIgnoreCase("SARB20") |
			 		PrdVal.equalsIgnoreCase("SARM12.5") | PrdVal.equalsIgnoreCase("SARM20")| PrdVal.equalsIgnoreCase("RARB20/RAG20") |
			 		PrdVal.equalsIgnoreCase("RARM20/RAG20") | PrdVal.equalsIgnoreCase("SARB12.5/SAG12.5") |PrdVal.equalsIgnoreCase("SARB20/SAG20") |
			 		PrdVal.equalsIgnoreCase("SARM12.5/SAG12.5") |PrdVal.equalsIgnoreCase("SARM20/SAG20") |
			 		PrdVal.equalsIgnoreCase("RARM20-45/RAG20-45") |PrdVal.equalsIgnoreCase("RARB20-45/RAG20-45"))
    	   {
    		   if (unit.equalsIgnoreCase("I"))
    		       prod_desc = prod_desc + a +  "\" * " + b + "\" " + " S: " + supply + "\" ";
    		   else if (unit.equalsIgnoreCase("F"))
    		       prod_desc = prod_desc + a +  " F * " + b + " F" + " S: " + supply + " F";
    		   else if (unit.equalsIgnoreCase("N"))
    		       prod_desc = prod_desc + a +  " M * " + b + " M" + " S: " + supply + " M";
    		   else
    		       prod_desc = prod_desc + a_disp.intValue() + " MM * " + b_disp.intValue() + " MM" + " S: " + s_disp.intValue() + " MM";
    	   }
    	   else
    	   {
    		   if (unit.equalsIgnoreCase("I"))
    		       prod_desc = prod_desc + a +  "\" * " + b + "\" " + " S: " + supply + "\" ";
    		   else if (unit.equalsIgnoreCase("F"))
    		       prod_desc = prod_desc + a +  " F * " + b + " F" + " S: " + supply + " F";
    		   else if (unit.equalsIgnoreCase("N"))
    		       prod_desc = prod_desc + a +  " M * " + b + " M" + " S: " + supply + " M";
    		   else
    		       prod_desc = prod_desc + a + " MM * " + b + " MM" + " S: " + supply + " MM";
    	   	}
       }
       
       if (a > 0 && ( supply == 0 && b == 0))
       {   
   	    	if (PrdVal.equalsIgnoreCase("VDR") | PrdVal.equalsIgnoreCase("VDR-SMC") | PrdVal.equalsIgnoreCase("VDRBB"))
	   	   	{
			   if (unit.equalsIgnoreCase("I"))
		  	       prod_desc = prod_desc + a +  "\" Ø"  ;
		   	   else
		   		   prod_desc = prod_desc + a_disp.intValue() +  " MM Ø" ;
	   	   	}
   	    	else if (PrdVal.equalsIgnoreCase("RRD") | PrdVal.equalsIgnoreCase("SRDM") 
   	    			| PrdVal.equalsIgnoreCase("SRD") | PrdVal.equalsIgnoreCase("CJN") | PrdVal.equalsIgnoreCase("BJN"))
     	    {
     		   if (unit.equalsIgnoreCase("I"))
 		  	       prod_desc = prod_desc + a +  "\"" ;
 		   	   else
 		   		   prod_desc = prod_desc + a_disp.intValue() +  " MM" ;
     	    }
   	    	else if (PrdVal.equalsIgnoreCase("SDVBP") | PrdVal.equalsIgnoreCase("SDVBPE") |
   	    			PrdVal.equalsIgnoreCase("SDV") | PrdVal.equalsIgnoreCase("SDVE") |
   	    			PrdVal.equalsIgnoreCase("CTC") | PrdVal.equalsIgnoreCase("CTCDS"))
     	    {
     		   if (unit.equalsIgnoreCase("I"))
 		  	       prod_desc = prod_desc.trim() + a +  "\"" ;
 		   	   else
 		   		   prod_desc = prod_desc.trim() + a_disp.intValue() +  "" ;
     	    }
   	    	else if (PrdVal.equalsIgnoreCase("SEC"))
     		   prod_desc = prod_desc + a_disp.intValue() + " SLT ";
   	    	
   	    	else if (PrdVal.equalsIgnoreCase("FAN-VORTICE"))
   	    	{
  				prod_desc = "C-CA" + a_disp.intValue();
  				String catdesc="CA" + a_disp.intValue();
  				if (ca_model_val.equalsIgnoreCase("M"))
  				{
  					prod_desc = prod_desc + "MD";
  					catdesc = catdesc + "MD";
  				}
  				else if (ca_model_val.equalsIgnoreCase("Q"))
  				{
  					prod_desc = prod_desc + "QMD";
  					catdesc = catdesc + "QMD";
  				}
  				prod_desc = prod_desc + "-" + ca_type_val + "-" + ca_met_val + "-" + ca_pc_val + "-" + ca_bd_val + "-" + ca_ais_val;
  				catdesc = catdesc + "-INLINE DUCT CENTRIFUGAL";
  				if (ca_type_val.equalsIgnoreCase("S"))
 					catdesc = catdesc + " SINGLE CABINET FAN";
 				else if (ca_type_val.equalsIgnoreCase("T"))
 					catdesc = catdesc + " TWIN CABINET FAN";
  				
	    		String sql_strprd="Update c_quotationline set remarks='" + catdesc +"'" + 
	    	   		" WHERE c_quotationline_id = " + record_id;	
	    	    int noulstr = DB.executeUpdate(sql_strprd, get_TrxName());
   	    	}
   	    	//End of Prepare Item Description for FAN-VORTICE  
   	    	
   	    	else if (PrdVal.equalsIgnoreCase("FAN-CFW-AXIAL"))
   	    	{
   	    		prod_desc = "SV-LDA-" + a_disp.intValue() +  "-" + fancfw_blade + "-" + fancfw_kw + "KW/" + fancfw_pole + "P";
   	    		//Prod.Catalogue desc
   	    		String catdesc="INLINE AXIAL FAN " + a_disp.intValue() +  "DIA WITH " + fancfw_blade + 
   	    				       " BLADES," + fancfw_kw + "KW AND " + fancfw_pole + "POLE";
   	    		
   	    		//String sql_strprd="Update c_quotationline set prdcode='" + prod_desc +"',prdname='" + catdesc + 
   	    		//		"' WHERE c_quotationline_id = " + record_id;	
   	   	  		//int noulstr = DB.executeUpdate(sql_strprd, get_TrxName());
   	    		String sql_strprd="Update c_quotationline set remarks='" + catdesc +"'" + 
   	    	   		" WHERE c_quotationline_id = " + record_id;	
   	    	    int noulstr = DB.executeUpdate(sql_strprd, get_TrxName());
   	   	  		  
   	    	}
   	    	//End of Prepare Item Description for CFW AXIAL FAN
   	    	
   	    	
   	    	//Prepare Item Description for Cable Tray - CT
   	    	else if (PrdVal.equalsIgnoreCase("CT-HD") | PrdVal.equalsIgnoreCase("CT-MD"))
   	    	{
   	    		prod_desc = ct_cot_val + "-CT-" + a_disp.intValue() + v_description + "-" + ct_len_val + "";
   	    	}
   	    	//End of Prepare Item Description for Cable Tray - CT 
   	    	
       }
             
        //Add Slot Width in Prod.Description for SLD & RLD
	 	if (PrdVal.equalsIgnoreCase("RLD") | PrdVal.equalsIgnoreCase("SLD") | PrdVal.equalsIgnoreCase("SEC")  |
		    PrdVal.equalsIgnoreCase("RLDF") | PrdVal.equalsIgnoreCase("RLD45") | PrdVal.equalsIgnoreCase("RLD-CRV") |
		    PrdVal.equalsIgnoreCase("SLDF") | PrdVal.equalsIgnoreCase("SLD45") | PrdVal.equalsIgnoreCase("JZ-SLD") |
		    PrdVal.equalsIgnoreCase("RLD2F") | PrdVal.equalsIgnoreCase("RLD45-20") |
	    	PrdVal.equalsIgnoreCase("SLD2F") | PrdVal.equalsIgnoreCase("SLD45-20"))
	 	{
	 		String sqlsw = " select NVL(SLOTWIDTH,'N') from c_quotationline where c_quotationline_id = ? ";
		    String sw = DB.getSQLValueStringEx(get_TrxName(), sqlsw, record_id); 	
		    if (!sw.equalsIgnoreCase("N"))
		    {
		    	if (PrdVal.equalsIgnoreCase("RLD") | PrdVal.equalsIgnoreCase("SLD") |
		    		PrdVal.equalsIgnoreCase("RLDF") | PrdVal.equalsIgnoreCase("RLD45") | PrdVal.equalsIgnoreCase("RLD-CRV") |
		    		PrdVal.equalsIgnoreCase("SLDF") | PrdVal.equalsIgnoreCase("SLD45") | PrdVal.equalsIgnoreCase("JZ-SLD") |
		    		PrdVal.equalsIgnoreCase("RLD2F") | PrdVal.equalsIgnoreCase("RLD45-20") |
			    	PrdVal.equalsIgnoreCase("SLD2F") | PrdVal.equalsIgnoreCase("SLD45-20"))
		    		prod_desc = prod_desc + "(" + sw + ")";
		    	else if (PrdVal.equalsIgnoreCase("SEC"))
		    		prod_desc = prod_desc + sw + " MM";
		    }
	 	}
	 	
	 	//For Non Parameter Items
	 	if (PrdVal.equalsIgnoreCase("EBP1") | PrdVal.equalsIgnoreCase("EBP2") | PrdVal.equalsIgnoreCase("EBP3") |
		    PrdVal.equalsIgnoreCase("EBP4") | PrdVal.equalsIgnoreCase("EBP5") | PrdVal.equalsIgnoreCase("EBP6") |
		 	PrdVal.equalsIgnoreCase("EBP7") | PrdVal.equalsIgnoreCase("EBP8") | PrdVal.equalsIgnoreCase("SDV100") | 
		 	PrdVal.equalsIgnoreCase("SDV150") | PrdVal.equalsIgnoreCase("SDV200") | PrdVal.equalsIgnoreCase("SDV250") | 
		 	PrdVal.equalsIgnoreCase("SDV300") | PrdVal.equalsIgnoreCase("SDV350") | PrdVal.equalsIgnoreCase("SDV400") |
		 	PrdVal.equalsIgnoreCase("SDV") | PrdVal.equalsIgnoreCase("SDVE") | PrdVal.equalsIgnoreCase("SDVE100") | 
		 	PrdVal.equalsIgnoreCase("SDVE150") | PrdVal.equalsIgnoreCase("SDVE200") | PrdVal.equalsIgnoreCase("SDVE250") | 
		 	PrdVal.equalsIgnoreCase("SDVE300") | PrdVal.equalsIgnoreCase("SDVE350") | PrdVal.equalsIgnoreCase("SDVE400") |
		 	PrdVal.equalsIgnoreCase("CTC") | PrdVal.equalsIgnoreCase("CTCDS") | PrdVal.equalsIgnoreCase("CTC100") |
		 	PrdVal.equalsIgnoreCase("CTC125") | PrdVal.equalsIgnoreCase("CTC160") | PrdVal.equalsIgnoreCase("CTC200") | 
		 	PrdVal.equalsIgnoreCase("CTC250") | PrdVal.equalsIgnoreCase("CTC315") | PrdVal.equalsIgnoreCase("CTC400") |
		 	PrdVal.equalsIgnoreCase("CTC500") | PrdVal.equalsIgnoreCase("CTC630") |
		 	PrdVal.equalsIgnoreCase("CTCDS100") | 
	 	    PrdVal.equalsIgnoreCase("CTCDS125") | PrdVal.equalsIgnoreCase("CTCDS160") | PrdVal.equalsIgnoreCase("CTCDS200") | 
	 	    PrdVal.equalsIgnoreCase("CTCDS250") | PrdVal.equalsIgnoreCase("CTCDS315") | PrdVal.equalsIgnoreCase("CTCDS400") |
	 	    PrdVal.equalsIgnoreCase("CTCDS500") | PrdVal.equalsIgnoreCase("CTCDS630") | 
	 	    PrdVal.equalsIgnoreCase("RS") | PrdVal.equalsIgnoreCase("RM") |
	 	    PrdVal.equalsIgnoreCase("RSDS") | PrdVal.equalsIgnoreCase("RMDS") | PrdVal.equalsIgnoreCase("SDVBP") | PrdVal.equalsIgnoreCase("SDVBPE"))
	 	{
	 		if (PrdVal.equalsIgnoreCase("RS") | PrdVal.equalsIgnoreCase("RM") | PrdVal.equalsIgnoreCase("RSDS") |
	 			PrdVal.equalsIgnoreCase("RMDS"))
	 			prod_desc = v_description;
	 		if (PrdVal.equalsIgnoreCase("SDVBP") | PrdVal.equalsIgnoreCase("SDVBPE") |
	 			PrdVal.equalsIgnoreCase("SDV") | PrdVal.equalsIgnoreCase("SDVE") | PrdVal.equalsIgnoreCase("CTC") | PrdVal.equalsIgnoreCase("CTCDS"))
	 			mpname = prod_desc;
	 		String sqlct = " select NVL(CTRLTYPE,'N') from c_quotationline where c_quotationline_id = ? ";
		    String ct = DB.getSQLValueStringEx(get_TrxName(), sqlct, record_id); 	
		    if (!ct.equalsIgnoreCase("N"))
		    {
		    	prod_desc = prod_desc.trim() + "-" + ct + "";
		    	if (ct.equalsIgnoreCase("L"))
		    		mpname = mpname + " LEFT CONTROL";
		    	else if (ct.equalsIgnoreCase("R"))
		    		mpname = mpname + " RIGHT CONTROL";
		    }
		    
		    if (act_id!=0 | ther_id!=0 | tran_id!=0)
		    {
		    	prod_desc = prod_desc + "-" + "";
		    	String actcomb="N";
		    	if (act_id!=0)
		    	{
		    		prod_desc = prod_desc + act_sname + "";
		    		mpname = mpname + " WITH ";
		    		mpname = mpname + act_name +"";
		    		
		    		//Mark themostat if the actuator is combo model (act+therm)  #02May2019
		    		String sqlactcomb="Select combomodelyn from beta_qtn_actuator where beta_qtn_Actuator_id=? ";
					actcomb = DB.getSQLValueString(get_TrxName(), sqlactcomb,act_id);
					if (actcomb.equalsIgnoreCase("Y"))
					{
						prod_desc = prod_desc + act_sname + "";
					}
		    		//End of Mark themostat if the actuator is combo model (act+therm)
		    	}
		    	else if (act_id==0)
		    	{
		    		prod_desc = prod_desc + "W";
		    		mpname = mpname + ",WITHOUT ACTUATOR"; 
		    	}
		    	if (ther_id!=0)
		    	{
		    		prod_desc = prod_desc + ther_sname + "";
		    		mpname = mpname + ",WITH " + ther_name + "";
		    	}
		    	else if (ther_id==0 && actcomb.equalsIgnoreCase("N"))
		    	{
		    		prod_desc = prod_desc + "W";
		    		mpname = mpname + ",WITHOUT THERMOSTAT"; 
		    	}
		    	if (tran_id!=0)
		    	{
		    		prod_desc = prod_desc + tran_sname + "";
		    		mpname = mpname + ",WITH " + tran_name + "";
		    	}
		    	else if (tran_id==0)
		    	{
		    		prod_desc = prod_desc + "W";
		    		mpname = mpname + ",WITHOUT TRANSFORMER"; 
		    	}
		    }
		    if (PrdVal.equalsIgnoreCase("RS") | PrdVal.equalsIgnoreCase("RM") |
		    	PrdVal.equalsIgnoreCase("RSDS") | PrdVal.equalsIgnoreCase("RMDS")	)
		    	prod_desc = prod_desc + " " + a_disp.intValue() +  " MM * " + b_disp.intValue() +  " MM * " + len_disp.intValue() + " MM";
		    if (!(disprem.equalsIgnoreCase("N")))
		    {
		    	String sql_upd_rem = " update c_quotationline set remarks ='" + mpname + "' where c_quotationline_id = "+ record_id;
		    	int updrem_status = DB.executeUpdate(sql_upd_rem, get_TrxName());
		    }
	 	}
	 	//End For Non Parameter Items
	 	
	 	//Add Heater specification for SDVE & SDVBPE Items
	 	if (PrdVal.equalsIgnoreCase("SDVBPE") | PrdVal.equalsIgnoreCase("SDVE100") | PrdVal.equalsIgnoreCase("SDVE") |
	 		PrdVal.equalsIgnoreCase("SDVE150") | PrdVal.equalsIgnoreCase("SDVE200") |
	 		PrdVal.equalsIgnoreCase("SDVE250") | PrdVal.equalsIgnoreCase("SDVE300") |
	 		PrdVal.equalsIgnoreCase("SDVE350") | PrdVal.equalsIgnoreCase("SDVE400"))
	 	{
	 		if (heater_id!= 0)
	 			prod_desc = prod_desc +  " WITH " + heater_name +"";
	 		else
	 			prod_desc = prod_desc +  " ENABLED FOR ELECTRIC HEATER";
	 		
	 	}
	 	//End of Add Heater specification for SDVE & SDVBPE Items
	 	
	 	//Add Leakage Class for Items : BEMFSD-T,BEMFSD/R-T,BMFSD-T,BMFSD/R-T,BMSD,BMSD/R
	 	if (setULClass.equalsIgnoreCase("Y"))
	 	{
	 		prod_desc = prod_desc + " (" + ulClass + ")";
	 	}
	 	//End of Add Leakage Class
	 	
	 	//Update Class,Actuator & Temp to product desc for BMFD-NEW,BMFSD-NEW,BEMFSD-NEW & BMSD-NEW items
		 if (PrdVal.equalsIgnoreCase("BEMFSD-NEW") | PrdVal.equalsIgnoreCase("BMSD-NEW") | 
			 PrdVal.equalsIgnoreCase("BMFSD-NEW")) 
		 {
			 prod_desc = prod_desc + " (" + ul_classtype +"-"+ ul_temp.substring(0, 3)+ ")";
		 }
		 
		 if (PrdVal.equalsIgnoreCase("BEMFSD-NEW") | PrdVal.equalsIgnoreCase("BMSD-NEW") | 
			 PrdVal.equalsIgnoreCase("BMFSD-NEW") | PrdVal.equalsIgnoreCase("BMFD-NEW") | 
			 PrdVal.equalsIgnoreCase("BEMFD-NEW"))
		 {
			 if (ul_noact_yn.equalsIgnoreCase("N"))
			 {
					 //Get Act.Code,Qty & Cost from Excel
					 rowAct = sheet.getRow(4);                //Row=5 
					 HSSFCell cellAct = rowAct.getCell(12);   //Column=M				 
					 CellValue cellValueAct = evaluator.evaluate(cellAct);
					 double rActrow = cellValueAct.getNumberValue();
					 int actRow=(int)rActrow;
					 //Act.Code
					 rowAct=sheet.getRow(actRow-1);
					 HSSFCell cellCode = rowAct.getCell(1);   //Column=B				 
					 CellValue cellValueCode = evaluator.evaluate(cellCode);
					 ActCode = cellValueCode.getStringValue();
					 //Act.Qty
					 HSSFCell cellQty = rowAct.getCell(4);   //Column=E				 
					 CellValue cellValueQty = evaluator.evaluate(cellQty);
					 double actqty = cellValueQty.getNumberValue();
					 ul_act_qty=(int)actqty;  //Assign to global variable
					 //Act.cost
					 HSSFCell cellCost = rowAct.getCell(7);   //Column=H				 
					 CellValue cellValueCost = evaluator.evaluate(cellCost);
					 act_cost= cellValueCost.getNumberValue();
					 
					 ul_act_totcost =  act_cost*ul_act_qty;
					 
					 //Get Name from actuator table
					  String sql_actul ="SELECT act.beta_qtn_actuator_id,act.name FROM beta_qtn_actuator act INNER JOIN " +
			   		  "m_product mp ON mp.m_product_id=act.m_product_id WHERE mp.value=?";
		 	    	  PreparedStatement pstmtactul = null;
		 	    	  ResultSet rsactul = null;
		 	    	  try 
		 	    	  {
					      pstmtactul = DB.prepareStatement(sql_actul, "DSPL");
					      pstmtactul.setString(1, ActCode);
					      rsactul = pstmtactul.executeQuery();
					      
					      if(rsactul.next()) 
					      {
					    	  ul_act_id = rsactul.getInt(1);
					    	  ulact_name = rsactul.getString(2);
					    	  
					      }
					      rsactul.close();
					      pstmtactul.close();
		 	    	   }catch(Exception e){log.log(Level.SEVERE, sql_actul, e); } // end of fn_getcelladdr
		 	    	   finally {try {DB.close(rsactul, pstmtactul);} catch (Exception e) {}}
		 	    	  
		 	    	 //Update Act.name to prod.desc
		 	    	 prod_desc = prod_desc + "-" + ulact_name +"";
			    	 if (ul_act_qty>1)
			    	 {
			    		 prod_desc = prod_desc + " X " + ul_act_qty +"";
			    	 }
			 }	 
		 }
		 //End of Update Class,Actuator & Temp to product desc for BMFD-NEW,BMFSD-NEW,BEMFSD-NEW & BMSD-NEW items
	 	
	 	//Add Actuator Name for MVCD & SSMVCD
	    if (PrdVal.equalsIgnoreCase("MVCD") | PrdVal.equalsIgnoreCase("SSMVCD") | PrdVal.equalsIgnoreCase("BMSD") |
			PrdVal.equalsIgnoreCase("BMFSD-T") | PrdVal.equalsIgnoreCase("BEMFSD-T") |
			PrdVal.equalsIgnoreCase("BMSD/R") | PrdVal.equalsIgnoreCase("BMFSD/R-T") | PrdVal.equalsIgnoreCase("BEMFSD/R-T") |
			PrdVal.equalsIgnoreCase("BMFD-T") | PrdVal.equalsIgnoreCase("BEMFD-T") | 
			PrdVal.equalsIgnoreCase("BMFD/R-T") | PrdVal.equalsIgnoreCase("BEMFD/R-T") |
			PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-F") |
			PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F") |
			PrdVal.equalsIgnoreCase("BMFD-TF") | PrdVal.equalsIgnoreCase("BEMFD-TF") |
			PrdVal.equalsIgnoreCase("BMFD/R-TF") | PrdVal.equalsIgnoreCase("BEMFD/R-TF") | 
			PrdVal.equalsIgnoreCase("AUVCDF") | PrdVal.equalsIgnoreCase("AUVCDB") |
			PrdVal.equalsIgnoreCase("AUVCDH") | PrdVal.equalsIgnoreCase("AUVCDS") |
			PrdVal.equalsIgnoreCase("AGVCDF") | PrdVal.equalsIgnoreCase("AGVCDB") | PrdVal.equalsIgnoreCase("AGVCDS") |
			PrdVal.equalsIgnoreCase("LVCDF") | PrdVal.equalsIgnoreCase("LVCDH") |
			PrdVal.equalsIgnoreCase("3VCDF") | PrdVal.equalsIgnoreCase("3VCDB") |
			PrdVal.equalsIgnoreCase("3VCDH") | PrdVal.equalsIgnoreCase("3VCDS") | 
			PrdVal.equalsIgnoreCase("VDR") | PrdVal.equalsIgnoreCase("PI3VCD") | PrdVal.equalsIgnoreCase("PIAVCD") |
			PrdVal.equalsIgnoreCase("MPRD"))
	    {
	    	if (act_id!=0)
	    	{
	    		prod_desc = prod_desc + "-" + act_name +"";
	    		if (act_qty>1)
	    		{
	    			prod_desc = prod_desc + " X " + act_qty +"";
	    		}
	    	}
	    }
	    //END Add Actuator Name for MVCD & SSMVCD
	    
	    //Add Jambseal in description for MPRD
	    if (PrdVal.equalsIgnoreCase("MPRD"))
	    {
	    	//Add differential pressure switch on description (Dtd:20.0923)
	    	prod_desc = prod_desc + " (WITH DIFFERENTIAL PRESSURE SWITCH HONEYWELL DPS-200";
	    	
	    	if (mprd_jamb.equalsIgnoreCase("Y"))
	    		prod_desc = prod_desc + " AND JAMBSEAL";
	    	
	    	prod_desc = prod_desc + ")";
	    }
	    //End of Add Jambseal in description for MPRD
	    
	    //ADD STATIC/DYNAMIC for MOTORIZED FIRE DAMPER
	    if (PrdVal.equalsIgnoreCase("BMFD-T") | PrdVal.equalsIgnoreCase("BEMFD-T") |
			PrdVal.equalsIgnoreCase("BMFD/R-T") | PrdVal.equalsIgnoreCase("BEMFD/R-T") |
			PrdVal.equalsIgnoreCase("BFD-F") | PrdVal.equalsIgnoreCase("BEFD-F") |
			PrdVal.equalsIgnoreCase("BFD/R-F") | PrdVal.equalsIgnoreCase("BEFD/R-F") |
			PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-F") |
			PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F") |
			PrdVal.equalsIgnoreCase("BMFD-TF") | PrdVal.equalsIgnoreCase("BEMFD-TF") |
			PrdVal.equalsIgnoreCase("BMFD/R-TF") | PrdVal.equalsIgnoreCase("BEMFD/R-TF") |
			PrdVal.equalsIgnoreCase("BMFD-NEW") | PrdVal.equalsIgnoreCase("BEMFD-NEW") |
			PrdVal.equalsIgnoreCase("FDC") | PrdVal.equalsIgnoreCase("FDCA"))
	    {
	    	//String sqlstdy = " select NVL(STADYN,'S') from c_quotationline where c_quotationline_id = ? ";
		    //String stdy = DB.getSQLValueStringEx(get_TrxName(), sqlstdy, record_id); 	
		    if (ul_stad.equalsIgnoreCase("S"))
		    	prod_desc = "STATIC " + prod_desc;
		    else if (ul_stad.equalsIgnoreCase("D"))
		    	prod_desc = "DYNAMIC " + prod_desc;
	    }
	    //END OF ADD STATIC/DYNAMIC for MOTORIZED FIRE DAMPER
	    
	    //Update Prod.description for FDC,FDCA (06.07.21)
		if (PrdVal.equalsIgnoreCase("FDC") | PrdVal.equalsIgnoreCase("FDCA"))
		{
			prod_desc = prod_desc + "(" + fdc_fra_thick + ")";
			if (fdc_slv_yn.equalsIgnoreCase("Y"))
			{
				if (PrdVal.equalsIgnoreCase("FDC"))
					prod_desc = prod_desc + " SLVC(" + fdc_slv_thick + "/" + (int)fdc_slv_depth + ")";
				else if (PrdVal.equalsIgnoreCase("FDCA"))
					prod_desc = prod_desc + " SLVA(" + fdc_slv_thick + "/" + (int)fdc_slv_depth + ")";
			}
			if (fdc_ind_yn.equalsIgnoreCase("Y"))
			{
				prod_desc = prod_desc + " (WITH MANUAL INDICATOR)";
			}
		}
		//End of Update Prod.description for FDC,FDCA 
		
		//Update Prod.description for COWL
		if (PrdVal.equalsIgnoreCase("COWL"))
		{
			prod_desc = "COWL & SHUTTER " + a_disp.intValue() + " MM";
			if (cowl_base_yn.equalsIgnoreCase("Y"))
				prod_desc = prod_desc + " WITH BASE";
			else
				prod_desc = prod_desc + " WITHOUT BASE";
			
		}
		//End of Update Prod.description for COWL
		
	    //ADD BLADE THICKNESS FOR AGVCD
	    if (PrdVal.equalsIgnoreCase("AGVCDB") | PrdVal.equalsIgnoreCase("AGVCDF") | PrdVal.equalsIgnoreCase("AGVCDS"))
	    {
	    	if (bladeThickVal.equalsIgnoreCase("G"))
		    {
		    	if (metalVal.equalsIgnoreCase("G1"))
		    		prod_desc = prod_desc + " (CASING:0.9MM,BLADE:0.7MM)";
		    	else if (metalVal.equalsIgnoreCase("G2"))
		    		prod_desc = prod_desc + " (CASING:1.2MM,BLADE:0.7MM)";
		    	else if (metalVal.equalsIgnoreCase("G3"))
		    		prod_desc = prod_desc + " (CASING:1.5MM,BLADE:0.7MM)";
		    	else if (metalVal.equalsIgnoreCase("G4"))
		    		prod_desc = prod_desc + " (CASING:1.7MM,BLADE:0.7MM)";
		    	else if (metalVal.equalsIgnoreCase("S1"))
		    		prod_desc = prod_desc + " (CASING:1.0MM,BLADE:0.7MM)";
		    	else if (metalVal.equalsIgnoreCase("S2"))
		    		prod_desc = prod_desc + " (CASING:1.2MM,BLADE:0.7MM)";
		    	else if (metalVal.equalsIgnoreCase("S3"))
		    		prod_desc = prod_desc + " (CASING:1.5MM,BLADE:0.7MM)";
		    	else if (metalVal.equalsIgnoreCase("S4"))
		    		prod_desc = prod_desc + " (CASING:1.7MM,BLADE:0.7MM)";
		    }
	    }
	    //END OF ADD BLADE THICKNESS FOR AGVCD
	    
	    //ADD SS BUSH ON DESC FOR UL Dampers
		if (PrdVal.equalsIgnoreCase("BMSD") | PrdVal.equalsIgnoreCase("BMFSD-T") | PrdVal.equalsIgnoreCase("BEMFSD-T") |
			PrdVal.equalsIgnoreCase("BMSD/R") | PrdVal.equalsIgnoreCase("BMFSD/R-T") | PrdVal.equalsIgnoreCase("BEMFSD/R-T") |
			PrdVal.equalsIgnoreCase("BMFD-T") | PrdVal.equalsIgnoreCase("BEMFD-T") | PrdVal.equalsIgnoreCase("BMFD/R-T") | PrdVal.equalsIgnoreCase("BEMFD/R-T") |
			PrdVal.equalsIgnoreCase("BFD-F") | PrdVal.equalsIgnoreCase("BEFD-F") | PrdVal.equalsIgnoreCase("BFD/R-F") | PrdVal.equalsIgnoreCase("BEFD/R-F") |
			PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-F") | PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F") |
			PrdVal.equalsIgnoreCase("BMFD-TF") | PrdVal.equalsIgnoreCase("BEMFD-TF") | PrdVal.equalsIgnoreCase("BMFD/R-TF") |
			PrdVal.equalsIgnoreCase("BEMFD/R-TF") | PrdVal.equalsIgnoreCase("BEMFSD-NEW") | PrdVal.equalsIgnoreCase("BMSD-NEW") | 
			PrdVal.equalsIgnoreCase("BMFSD-NEW") | PrdVal.equalsIgnoreCase("BEMFD-NEW") | PrdVal.equalsIgnoreCase("BMFD-NEW"))
		{
			if (ul_ssbush.equalsIgnoreCase("Y"))
				prod_desc = prod_desc + " (WITH SS BUSH)";
		}
	    //End of ADD SS BUSH ON DESC FOR UL Dampers
		
		//ADD WIRE/CHAIN ON DESC for RAD & SAD
		if (PrdVal.equalsIgnoreCase("RAD") | PrdVal.equalsIgnoreCase("SADB") | PrdVal.equalsIgnoreCase("SADB-EG") |
			PrdVal.equalsIgnoreCase("SADM") | PrdVal.equalsIgnoreCase("SADM-EG"))
		{
			if (diffwireyn.equalsIgnoreCase("Y"))
				prod_desc = prod_desc + " (WITH HANGING WIRE)";
			else if (diffchainyn.equalsIgnoreCase("Y"))
				prod_desc = prod_desc + " (WITH HANGING CHAIN)";
				
		}
		//End of ADD WIRE/CHAIN ON DESC for RAD & SAD
		
		//ADD PHENOLIC remarks on PI3VCD & PIAVCD
		if (PrdVal.equalsIgnoreCase("PI3VCD") | PrdVal.equalsIgnoreCase("PIAVCD"))
		{
		    if (vcd_phen.equalsIgnoreCase("N")==false)
		    	prod_desc = prod_desc + " (WITH PHENOLIC)";
		}
		//End of ADD PHENOLIC remarks on PI3VCD & PIAVCD
		
		//ADD additional description for NON Std VCD
		if (PrdVal.equalsIgnoreCase("3VCDB") | PrdVal.equalsIgnoreCase("3VCDF") | 
			PrdVal.equalsIgnoreCase("3VCDS") | PrdVal.equalsIgnoreCase("AGVCDF"))
		{
			 	if (vcd_nonstd.equalsIgnoreCase("Y"))
			 	{
			 		if (vcd_jamseal.equalsIgnoreCase("Y"))
			 		{
			 			prod_desc = prod_desc + " (WITH JAMB SEAL,SHAFT:";
			 			if (vcd_shaft.equalsIgnoreCase("GIS"))
			 				prod_desc = prod_desc + "GI SQUARE,";
			 			else if (vcd_shaft.equalsIgnoreCase("GIR"))
			 				prod_desc = prod_desc + "GI ROUND,";
			 			else if (vcd_shaft.equalsIgnoreCase("SSS"))
			 				prod_desc = prod_desc + "SS SQUARE,";
			 			else if (vcd_shaft.equalsIgnoreCase("SSR"))
			 				prod_desc = prod_desc + "SS ROUND,";
			 		}
			 		else
			 		{
			 			prod_desc = prod_desc + " (SHAFT:";
			 			if (vcd_shaft.equalsIgnoreCase("GIS"))
			 				prod_desc = prod_desc + "GI SQUARE,";
			 			else if (vcd_shaft.equalsIgnoreCase("GIR"))
			 				prod_desc = prod_desc + "GI ROUND,";
			 			else if (vcd_shaft.equalsIgnoreCase("SSS"))
			 				prod_desc = prod_desc + "SS SQUARE,";
			 			else if (vcd_shaft.equalsIgnoreCase("SSR"))
			 				prod_desc = prod_desc + "SS ROUND,";
			 		}
			 		if (PrdVal.equalsIgnoreCase("3VCDB") | PrdVal.equalsIgnoreCase("3VCDF") | 
			 			PrdVal.equalsIgnoreCase("3VCDS"))
			 		{
			 			prod_desc = prod_desc + "BLADE:" + vcd_blade + "MM,";
			 			if (vcd_rivets.equalsIgnoreCase("ALR"))
			 				prod_desc = prod_desc + "RIVETS:AL)";
			 			else if (vcd_rivets.equalsIgnoreCase("GIR"))
			 				prod_desc = prod_desc + "RIVETS:GI)";
			 		}
			 		else if (PrdVal.equalsIgnoreCase("AGVCDF"))
			 		{
			 			prod_desc = prod_desc + "BLADE:" + agvcd_blade + "MM,";
			 			if (vcd_rivets.equalsIgnoreCase("ALR"))
			 				prod_desc = prod_desc + "RIVETS:AL)";
			 			else if (vcd_rivets.equalsIgnoreCase("GIR"))
			 				prod_desc = prod_desc + "RIVETS:GI)";
			 		}
			 		
			 	}
		}
		if (PrdVal.equalsIgnoreCase("AUVCDB") | PrdVal.equalsIgnoreCase("AUVCDF") | PrdVal.equalsIgnoreCase("AUVCDS"))
		{
			 	if (vcd_nonstd.equalsIgnoreCase("Y"))
			 	{
				 		if (vcd_jamseal.equalsIgnoreCase("Y"))
				 		{
				 			prod_desc = prod_desc + " (WITH JAMB SEAL,SHAFT:";
				 			if (vcd_shaft.equalsIgnoreCase("GIS"))
				 				prod_desc = prod_desc + "GI SQUARE,";
				 			else if (vcd_shaft.equalsIgnoreCase("GIR"))
				 				prod_desc = prod_desc + "GI ROUND,";
				 			else if (vcd_shaft.equalsIgnoreCase("SSS"))
				 				prod_desc = prod_desc + "SS SQUARE,";
				 			else if (vcd_shaft.equalsIgnoreCase("SSR"))
				 				prod_desc = prod_desc + "SS ROUND,";
				 		}
				 		else
				 		{
				 			prod_desc = prod_desc + " (SHAFT:";
				 			if (vcd_shaft.equalsIgnoreCase("GIS"))
				 				prod_desc = prod_desc + "GI SQUARE,";
				 			else if (vcd_shaft.equalsIgnoreCase("GIR"))
				 				prod_desc = prod_desc + "GI ROUND,";
				 			else if (vcd_shaft.equalsIgnoreCase("SSS"))
				 				prod_desc = prod_desc + "SS SQUARE,";
				 			else if (vcd_shaft.equalsIgnoreCase("SSR"))
				 				prod_desc = prod_desc + "SS ROUND,";
				 		}
				 		if (vcd_rivets.equalsIgnoreCase("ALR"))
			 				prod_desc = prod_desc + "RIVETS:AL)";
			 			else if (vcd_rivets.equalsIgnoreCase("GIR"))
			 				prod_desc = prod_desc + "RIVETS:GI)";
				 		
				 }
			}
		
		//End of additional description for NON Std VCD
		
		//CableTray & Trunking items: Powder Coat to be added in description (08/06/21)
		if (Prod_Value.equalsIgnoreCase("CABLETRAY") || Prod_Value.equalsIgnoreCase("CT-COVER") ||
			Prod_Value.equalsIgnoreCase("CT-REDU") || Prod_Value.equalsIgnoreCase("CT-RISER") ||
			Prod_Value.equalsIgnoreCase("CT-ACC-3W-E") || Prod_Value.equalsIgnoreCase("CT-ACC-3W-UE") ||
			Prod_Value.equalsIgnoreCase("CT-ACC-4W") || Prod_Value.equalsIgnoreCase("CT-ACC-2W") ||
			Prod_Value.equalsIgnoreCase("TRUNKING"))
		{
			if (ct_pctype.equalsIgnoreCase("SINGLE"))
				prod_desc = prod_desc + " (WITH PC:SINGLE)";
			else if (ct_pctype.equalsIgnoreCase("BASE"))
				prod_desc = prod_desc + " (WITH PC:BASE)";
		}
		//End of CableTray & Trunking items: Powder Coat to be added in description 
		
		//Add flange type & Perf.Sheet in PGL description (Added:13.06.21) Perf.Sheet(22.02.22)
		if (Prod_Value.equalsIgnoreCase("PGL"))
		{
			prod_desc = prod_desc + " (WITH " + pgl_flange + "MM FLANGE,"; 
			prod_desc = prod_desc + pgl_psheet + "MM PERFORATED SHEET)"; 
		}
		//End of Add flange type in PGL description
		
		//Add GI THICK for VAV products (20.09.21)
		if (Prod_Value.equalsIgnoreCase("SDV") | Prod_Value.equalsIgnoreCase("SDVE") |
			Prod_Value.equalsIgnoreCase("SDVBP") | Prod_Value.equalsIgnoreCase("SDVBPE") |
			Prod_Value.equalsIgnoreCase("CTC") | Prod_Value.equalsIgnoreCase("CTCDS") |
			Prod_Value.equalsIgnoreCase("EBP1") | Prod_Value.equalsIgnoreCase("EBP2") |
			Prod_Value.equalsIgnoreCase("EBP3") | Prod_Value.equalsIgnoreCase("EBP4") |
			Prod_Value.equalsIgnoreCase("EBP5") | Prod_Value.equalsIgnoreCase("EBP6") |
			Prod_Value.equalsIgnoreCase("EBP7") | Prod_Value.equalsIgnoreCase("EBP8"))
		{
			prod_desc = prod_desc + " (GI THICK:" + vav_fra_thick + "MM)";
		}
		//End of Add GI THICK for VAV products
		
		//Add GI THICK for NRD products (02.02.2022)
		if (Prod_Value.equalsIgnoreCase("NRD") | Prod_Value.equalsIgnoreCase("NRDE") |
			Prod_Value.equalsIgnoreCase("NRD-CR") | Prod_Value.equalsIgnoreCase("NRDE-CR") |
			Prod_Value.equalsIgnoreCase("PRD"))
		{
			if (nrd_fra_thick.equalsIgnoreCase("G1"))
				prod_desc = prod_desc + " (GI THICK:0.9 MM)";
			else if (nrd_fra_thick.equalsIgnoreCase("G2"))
				prod_desc = prod_desc + " (GI THICK:1.2 MM)";
			else if (nrd_fra_thick.equalsIgnoreCase("G3"))
				prod_desc = prod_desc + " (GI THICK:1.5 MM)";
		}
		//End of Add GI THICK for NRD products
		
		//make description for FBR & FBF
		if (Prod_Value.equalsIgnoreCase("FBR") | Prod_Value.equalsIgnoreCase("FBF"))
		{
			if (Prod_Value.equalsIgnoreCase("FBF"))
			{
				if (fb_flange.equalsIgnoreCase("51"))
					prod_desc = Prod_Value + "-" + fb_slotn + "/" + fb_slotw + " ";
				else
					prod_desc = Prod_Value + fb_flange + "-" + fb_slotn + "/" + fb_slotw + " ";
			}
			else if (Prod_Value.equalsIgnoreCase("FBR"))
				prod_desc = Prod_Value + "-" + fb_slotn + "/" + fb_slotw + " ";
			
			if (unit.equalsIgnoreCase("I"))
		  	       prod_desc = prod_desc + a +  "\"" ;
 		   	else
		   		   prod_desc = prod_desc + a_disp.intValue() +  " MM";
		}
		//End of make description for FBR & FBF
		
		//
		if (Prod_Value.equalsIgnoreCase("EAL2") | Prod_Value.equalsIgnoreCase("EAL2-IS") | Prod_Value.equalsIgnoreCase("EAL4") |
			Prod_Value.equalsIgnoreCase("EAL4-IS") | Prod_Value.equalsIgnoreCase("EAL2-SSWM") | Prod_Value.equalsIgnoreCase("EAL4-SSWM"))
		{
			if (eal_filteryn.equalsIgnoreCase("Y"))
				prod_desc = prod_desc + " (WITH FILTER)"; 
		}
		
		//Add Ext/Int mounting option for BMFSD,BMFD 
		if (Prod_Value.equalsIgnoreCase("BMFSD-NEW") | Prod_Value.equalsIgnoreCase("BEMFSD-NEW") |
			Prod_Value.equalsIgnoreCase("BMFD-NEW") | Prod_Value.equalsIgnoreCase("BEMFD-NEW") |
			Prod_Value.equalsIgnoreCase("BMSD-NEW"))
		{
			if (ul_act.equalsIgnoreCase("B"))
			{
				if (ul_intextact.equalsIgnoreCase("I"))
					prod_desc = prod_desc + " (INTERNAL)";
				else if (ul_intextact.equalsIgnoreCase("E"))
					prod_desc = prod_desc + " (EXTERNAL)";
			}	
		}
		//		
		
		//Add Round Neck for FDC/FDCA in description
		if (PrdVal.equalsIgnoreCase("FDC") | PrdVal.equalsIgnoreCase("FDCA"))
		{
			if (fdc_rn_yn.equalsIgnoreCase("Y"))
				prod_desc = prod_desc + " (RN:" + fdc_rn + " MM)";
		}
		//End of Add Round Neck for FDC/FDCA
		
		//Desc for Mounting Feet (MFeet)
		if (PrdVal.equalsIgnoreCase("MFEET"))
		{
			
			prod_desc = mfeet_brand + "-" + a_disp.intValue() + "MM MOUNTING FEET";
		}
		//End of Desc for Mounting Feet (MFeet)
		
		//Desc for Mounting Feet (MFeet)
		if (PrdVal.equalsIgnoreCase("JB-ITCC"))
		{
			prod_desc = PrdVal + " " + a_disp.intValue() + "MM * " + b_disp.intValue() + "MM * " + len_disp.intValue() + "MM:" + jb_frame + " ";
			if (jb_logo_yn.equalsIgnoreCase("Y"))
				prod_desc = prod_desc + "WITH LOGO AND ";
			else
				prod_desc = prod_desc + "WITHOUT LOGO AND ";
			if (jb_kout_yn.equalsIgnoreCase("Y"))
				prod_desc = prod_desc + "WITH KNOCKOUT";
			else
				prod_desc = prod_desc + "WITHOUT KNOCKOUT";
		}
		//End of Desc for JB-ITCC
		
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
		finally {try {DB.close(rs3, pstmt3);} catch (Exception e) {}} 
	   	  
		row = sheet.getRow(r);
		HSSFCell cell = row.getCell(c);
	 
		CellValue cellValue1 = evaluator.evaluate(cell);
	 	prod_cost = cellValue1.getNumberValue();
		
	 	//Check cost getting zero for cable tray access.
	 	if (PrdVal.equalsIgnoreCase("CT-ACC-3W-UE") | PrdVal.equalsIgnoreCase("CT-ACC-3W-E") |
	 		PrdVal.equalsIgnoreCase("CT-ACC-2W") | PrdVal.equalsIgnoreCase("CT-ACC-4W") | 
	 		PrdVal.equalsIgnoreCase("CT-RISER"))
	 	{
	 		if (prod_cost<=0)
	 		{
	 			addLog("Price getting zero, since the size entered may not be the standard size.");
	 			prod_desc="";
	 		}
	 	}
	 	//End of check cost getting zero for cable tray access.
	 	
	 	//Check size as per std for ITCC Junction box(if std.cost getting from excel is less than 1)
	 	if (PrdVal.equalsIgnoreCase("JB-ITCC"))
	 	{
	 		if (prod_cost<1)
	 		{
	 			addLog("Wrong Price, since the size entered may not be the standard size.");
	 			prod_desc="";
	 		}
	 	}
	 	//End of Check size as per std for ITCC Junction box 
	 		 	
	 	// Apply Cost Factor 2.85 for curve
	 	if (curyn.equalsIgnoreCase("Y") | PrdVal.equalsIgnoreCase("RLD-CRV")) 
	 		prod_cost = prod_cost * 2.85;
	 	
	 	//Apply Cost Factor 1.1 for 1/2/3 Ways
	 	if (ways.equalsIgnoreCase("1") | ways.equalsIgnoreCase("2") | ways.equalsIgnoreCase("3"))
	 		prod_cost = prod_cost * 1.1;
	 	
	 	//Apply cost Factor 0.92 for Equilizing Grid
	/* 	if (eqyn.equalsIgnoreCase("Y"))
	 		prod_cost = prod_cost * 1.092;  */
	 	
	 	//Add 20 to cost for T-Bar Added on 23.02.2014			 
	 	if (tBarYN.equalsIgnoreCase("Y"))
	 		prod_cost = prod_cost + 20;
	 	
	 	//Add 7 to cost for Smoke Dampers if value less than 4'' or 200 
	 	if (PrdVal.equalsIgnoreCase("BMFD-T") | PrdVal.equalsIgnoreCase("BEMFD-T") |
	 		PrdVal.equalsIgnoreCase("BMFD/R-T") | PrdVal.equalsIgnoreCase("BEMFD/R-T") |
	 		PrdVal.equalsIgnoreCase("BFD-F") | PrdVal.equalsIgnoreCase("BEFD-F") |
	 		PrdVal.equalsIgnoreCase("BFD/R-F") | PrdVal.equalsIgnoreCase("BEFD/R-F") |
	 		PrdVal.equalsIgnoreCase("BMFD-F") | PrdVal.equalsIgnoreCase("BEMFD-F") |
	 		PrdVal.equalsIgnoreCase("BMFD/R-F") | PrdVal.equalsIgnoreCase("BEMFD/R-F") |
	 		PrdVal.equalsIgnoreCase("BMFD-TF") | PrdVal.equalsIgnoreCase("BEMFD-TF") |
	 		PrdVal.equalsIgnoreCase("BMFD/R-TF") | PrdVal.equalsIgnoreCase("BEMFD/R-TF") |
	 		PrdVal.equalsIgnoreCase("BMFD-NEW") | PrdVal.equalsIgnoreCase("BEMFD-NEW"))
	 	{
	 		if (unit.equalsIgnoreCase("I"))
	 		{
	 			if (a<8 | b<8)
	 				prod_cost = prod_cost + 7;
	 			
	 		}
	 		else
	 		{
	 			if (a<203 | b<203)
	 				prod_cost = prod_cost + 7;
	 		}
	 	}
	 	//End of Add 7 to cost for Smoke Dampers if value less than 4'' or 200 
	 	
	 	System.out.println(" prod_cost : " + prod_cost);
	  }
	  rs1.close();
	  pstmt1.close();
	 } catch(Exception e) { log.saveError("Error", e.getMessage());}
	 finally {try {DB.close(rs1, pstmt1);} catch (Exception e) {}} 
		
  }
}
