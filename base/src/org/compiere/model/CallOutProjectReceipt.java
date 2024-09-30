/******************************************************************************
     * BETA CODE --------------AUTHOR -> SUBAIR P.K.
     * Call Out Class for Project Receipt
 *****************************************************************************/

package org.compiere.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;
import java.math.*;

import org.compiere.util.DB;
import org.compiere.util.Env;


public class CallOutProjectReceipt extends CalloutEngine
{
	
	public String projectLine (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		Integer C_ProjectLine_ID = (Integer)value;
		if (C_ProjectLine_ID == null || C_ProjectLine_ID.intValue() == 0)
			return "";

		//	Get Details
		MProjectLine ol = new MProjectLine (ctx, C_ProjectLine_ID.intValue(), null);
		if (ol.get_ID() != 0)
		{
			mTab.setValue("M_Product_ID", new Integer(ol.getM_Product_ID()));
//			mTab.setValue("PlannedPrice", ol.getPlannedPrice());
			BigDecimal RecQty = (BigDecimal)mTab.getValue("QTYRECEIVED");
			BigDecimal PlanPrice = (BigDecimal)mTab.getValue("PlannedPrice");
			BigDecimal Total =  RecQty.multiply(PlanPrice);
			mTab.setValue("TotalLine",Total);
		}
		return "";
	}
	
	public String QtyReceived (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if ( value == null || value.equals(Env.ZERO)) 
			return "";
		
		BigDecimal RecQty = (BigDecimal)mTab.getValue("QTYRECEIVED");
		Integer C_ProjectLine_ID = (Integer)mTab.getValue("C_PROJECTLINE_ID");
		Integer C_PROJECTRECEIPTLINE_ID = (Integer)mTab.getValue("C_PROJECTRECEIPTLINE_ID");
		MProjectLine ol = new MProjectLine (ctx, C_ProjectLine_ID, null);
		BigDecimal PlannedQty = (BigDecimal)ol.getPlannedQty();
		BigDecimal TotalReceivedQty = Env.ZERO;

		
		String sql = "Select nvl(Sum(QTYRECEIVED),0) from C_PROJECTRECEIPTLINE Where C_ProjectLine_ID = ? " +
		"and C_PROJECTRECEIPTLINE_ID != ?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, null);
			pstmt.setInt(1, C_ProjectLine_ID);
			pstmt.setInt(2, C_PROJECTRECEIPTLINE_ID);
			rs = pstmt.executeQuery();
			if (rs.next()){
				TotalReceivedQty = rs.getBigDecimal(1);
			}
		}
		catch(Exception e){
		}
		
		if((TotalReceivedQty.add(RecQty)).compareTo(PlannedQty) == 1) 
		{
			mTab.fireDataStatusEEvent("Received Qty","Total Qty received so far exceeds the Planned Qty",true);
			//mTab.setValue("TotalLine",Env.ZERO);
			//mTab.setValue("QTYRECEIVED", Env.ZERO);
		}
		else
		{
			BigDecimal PlanPrice = (BigDecimal)mTab.getValue("PlannedPrice");
			BigDecimal Total =  RecQty.multiply(PlanPrice);
			mTab.setValue("TotalLine",Total);
		}
		
		
		DB.close(rs, pstmt);
		rs = null;
		pstmt = null;
		
		return "";
	}
	
}
	