package org.compiere.model;

import java.util.Properties;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;

public class Callout_Quotation extends CalloutEngine{
	
	 /** to set TotalPrice While updating Qty  in  quotationLine*/	
 public String setTotal (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
 {
  if (isCalloutActive() || value == null)
	return "";
  setCalloutActive(true);
  
  try {
	  BigDecimal Qty = (BigDecimal)mTab.getValue("Qty");
      BigDecimal Price = (BigDecimal)mTab.getValue("Price");
      BigDecimal netPrice = (BigDecimal)mTab.getValue("NETPRICE");
	  BigDecimal Totalline = Qty.multiply(netPrice);
	  //Totalline = Totalline.setScale(0,BigDecimal.ROUND_HALF_UP);
	  mTab.setValue("TOTALLINE", Totalline);   
  }catch (Exception e) {
	  return "";
  }
 setCalloutActive(false);
 return "";
}	
 
 /** to set isbom flag in quotationline*/
 public String setIsBom (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
 {
  if (isCalloutActive() || value == null)
	return "";
  setCalloutActive(true);
	Integer M_Product_ID = (Integer)mTab.getValue("M_Product_ID");
	Integer C_Quotation_ID = (Integer)mTab.getValue("C_Quotation_ID");
	
	String sql = " select nvl(isbom,'N') from m_product where m_product_id = ? ";
	String IsBom = DB.getSQLValueString(null, sql, M_Product_ID);
	mTab.setValue("isbom", IsBom);

	if (IsBom.trim().contentEquals("Y"))
	{
		String sqlgrp = " select nvl(subgrp,'N') from m_product where m_product_id = ? ";
		String Subgrp = DB.getSQLValueString(null, sqlgrp, M_Product_ID);
		
		if (Subgrp.trim().contentEquals("N"))
		{ 
			String sql1 = "Select nvl(DISCPER,0) from c_quotation where c_quotation_id = ?";
			BigDecimal disc_pcnt = DB.getSQLValueBD(null, sql1, C_Quotation_ID);
			mTab.setValue("DISC_PCNT", disc_pcnt);
		}
		else if (Subgrp.trim().contentEquals("F"))  
		{
			String sqlfr = "Select nvl(DISCPERFR,0) from c_quotation where c_quotation_id = ?";
			BigDecimal disc_pcnt = DB.getSQLValueBD(null, sqlfr, C_Quotation_ID);
			mTab.setValue("DISC_PCNT", disc_pcnt);
		}
		else if (Subgrp.trim().contentEquals("L"))  
		{
			String sqlLf = "Select nvl(DISCPERLF,0) from c_quotation where c_quotation_id = ?";
			BigDecimal disc_pcnt = DB.getSQLValueBD(null, sqlLf, C_Quotation_ID);
			mTab.setValue("DISC_PCNT", disc_pcnt);
		}	
			
	}
	else
	{
		String sqlIsDoor = "Select nvl(ISDOOR,'N') from c_quotation where c_quotation_id = ?";
		String IsDoor = DB.getSQLValueString(null, sqlIsDoor, C_Quotation_ID);
		
		if (IsDoor.trim().contentEquals("Y"))
		{
			String sqlHw = "Select nvl(DISCPERHW,0) from c_quotation where c_quotation_id = ?";
		//	BigDecimal disc_pcntHw = DB.getSQLValueBD(null, sqlHw, C_Quotation_ID);
		//  mTab.setValue("DISC_PCNT", disc_pcntHw);
		}
		else
		{
			String sqlGD = "Select nvl(DISCPER,0) from c_quotation where c_quotation_id = ?";
			BigDecimal disc_pcntGD = DB.getSQLValueBD(null, sqlGD, C_Quotation_ID);
			mTab.setValue("DISC_PCNT", disc_pcntGD);
		}
	}
	
	
 setCalloutActive(false);
 return "";
}	
 
 /** to set TotalPrice While updating price for non-BOM items in quotationLine*/
 public String setTotalfromPrice (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
 {
  if (isCalloutActive() || value == null)
	return "";
  setCalloutActive(true);
  
  try {
	  BigDecimal Qty = (BigDecimal)mTab.getValue("Qty");
      BigDecimal Price = (BigDecimal)mTab.getValue("Price");
	  BigDecimal disc_pcnt = (BigDecimal)mTab.getValue("DISC_PCNT");
		  
	 //BigDecimal pcnt = (new BigDecimal(100).subtract(disc_pcnt)).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
	  BigDecimal pcnt = (new BigDecimal(100).subtract(disc_pcnt)).divide(new BigDecimal(100));
	 System.out.println(" pcnt : " + pcnt);
	 BigDecimal netprice = Price.multiply(pcnt);
	 netprice = netprice.setScale(10,BigDecimal.ROUND_HALF_UP);
	 System.out.println(" Int Net Price : " + netprice);
	 mTab.setValue("NETPRICE", netprice); 
	 
	
	 
	 BigDecimal Totalline = Qty.multiply(netprice);
	  Totalline = Totalline.setScale(10,BigDecimal.ROUND_HALF_UP);
	  mTab.setValue("TOTALLINE", Totalline);
  	}catch (Exception e) {
	  return "";
  	}  

	  
 setCalloutActive(false);
 return "";
}	
 
 
}
