/******************************************************************************
     * BETA CODE --------------AUTHOR -> SUBAIR P.K.
     * Call Out Class for Sales Order 
 *****************************************************************************/
package org.compiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;
import java.math.*;


import org.compiere.util.DB;
import org.compiere.util.Env;

public class Callout_BetaOrder extends CalloutEngine{
	
	public String projectLine (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_ProjectLine_ID = (Integer)value;
		if (C_ProjectLine_ID == null || C_ProjectLine_ID.intValue() == 0)
			return "";

		//	Get Details
		MProjectLine ol = new MProjectLine (ctx, C_ProjectLine_ID.intValue(), null);
		if (ol.get_ID() != 0)
		{
	
			BigDecimal PlannedQty = (BigDecimal)ol.getPlannedQty();
			BigDecimal TotalOrdQty = Env.ZERO;

			String sql = "Select nvl(Sum(QTYORDERED),0) from C_ORDERLINE Where C_ProjectLine_ID = ? " ;
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, C_ProjectLine_ID);
				rs = pstmt.executeQuery();
				if (rs.next()){
					TotalOrdQty = rs.getBigDecimal(1);
				}
			}
			catch(Exception e){
			}
			
			finally 
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
			BigDecimal CurrQty = PlannedQty.subtract(TotalOrdQty);	
					
			mTab.setValue("M_Product_ID", new Integer(ol.getM_Product_ID()));
			mTab.setValue("PRICELIST", (BigDecimal)ol.getPRICE_BEFORE_DISC());
			mTab.setValue("PRICELIMIT", (BigDecimal)ol.getPRICE_BEFORE_DISC());
			mTab.setValue("PRICEACTUAL", (BigDecimal)ol.getNETSELLING_PRICE());
			mTab.setValue("PRICEENTERED", (BigDecimal)ol.getNETSELLING_PRICE());
			mTab.setValue("DISCOUNT", (BigDecimal)ol.getDISC_PCNT());
			if (CurrQty.doubleValue()>=1)
			{
				mTab.setValue("QTYORDERED",CurrQty);
				mTab.setValue("QTYENTERED",CurrQty);
			}
			else
			{
				mTab.setValue("QTYORDERED",Env.ZERO);
				mTab.setValue("QTYENTERED",Env.ZERO);
			}
			
		}
		return "";
		
	}	
	
	public String ProjectHdr (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_Project_ID = (Integer)value;
		BigDecimal DiscPerc = null;
		
		String sql = "Select DISC_PCNT from C_PROJECT Where C_Project_ID = ? " ;

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_Project_ID);
			rs = pstmt.executeQuery();
			if (rs.next()){
				DiscPerc = rs.getBigDecimal(1);
			}
		}
		catch(Exception e){
		}
		
		finally 
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		mTab.setValue("DISCPERC",DiscPerc);
		
		
		return "";
	}
	

}
	
