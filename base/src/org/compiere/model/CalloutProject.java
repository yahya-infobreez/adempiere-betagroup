/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.math.BigDecimal;
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Env;

import java.sql.PreparedStatement;
import java.sql.ResultSet;


/**
 *	Project Callouts
 *	
 *  @author Jorg Janke
 *  @version $Id: CalloutProject.java,v 1.3 2006/07/30 00:51:04 jjanke Exp $
 */
public class CalloutProject extends CalloutEngine
{
	/**
	 *	Project Planned - Price + Qty.
	 *		- called from PlannedPrice, PlannedQty
	 *		- calculates PlannedAmt (same as Trigger)
	 *  @param ctx context
	 *  @param WindowNo current Window No
	 *  @param mTab Grid Tab
	 *  @param mField Grid Field
	 *  @param value New Value
	 *  @return null or error message
	 */
	public  String planned (Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		if (isCalloutActive() || value == null)
			return "";

		BigDecimal PlannedQty, PlannedPrice;
		int StdPrecision = Env.getContextAsInt(ctx, WindowNo, "StdPrecision");


		//	get values
		PlannedQty = (BigDecimal)mTab.getValue("PlannedQty");
		if (PlannedQty == null)
			PlannedQty = Env.ONE;
		PlannedPrice = ((BigDecimal)mTab.getValue("PlannedPrice"));
		if (PlannedPrice == null)
			PlannedPrice = Env.ZERO;
		//
		BigDecimal PlannedAmt = PlannedQty.multiply(PlannedPrice);
		if (PlannedAmt.scale() > StdPrecision)
			PlannedAmt = PlannedAmt.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
		//
		log.fine("PlannedQty=" + PlannedQty + " * PlannedPrice=" + PlannedPrice + " -> PlannedAmt=" + PlannedAmt + " (Precision=" + StdPrecision+ ")");
		mTab.setValue("PlannedAmt", PlannedAmt);
		return "";
	}	//	planned

	//BETA CODE**********************25AUG2010******************************************************
	
	//Checking For FG
	public String FGCheck(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		String sql = null;
		Integer RowNo =null;
		if ((Integer)mTab.getValue("M_Product_ID")!= null)
		{
			sql= "SELECT Value from M_Product where M_Product_Category_ID=1000001 and M_Product_ID= ? " ;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1,(Integer)mTab.getValue("M_Product_ID"));
				rs = pstmt.executeQuery();
				rs.next();
				RowNo=rs.getRow();
				if (RowNo==0)
				{
					mTab.fireDataStatusEEvent("Product", "Please Select a Valid Finished Good",true);
					mTab.setValue("M_Product_ID",null);
					return "";
				}
			}
			catch(Exception e){
			}
		}
		return "";
	}
	
	//Set cost set price set
	public String SetPrice(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		
		if (isCalloutActive() || value == null)
			return "";
		int  M_P_ID;
		BigDecimal PlannedQty, PlannedPrice,Price_Before_Disc,Netselling_Price,plannedmarginamt;
		int StdPrecision = Env.getContextAsInt(ctx, WindowNo, "StdPrecision");

		String sql = null;
		Integer RowNo =null;
		M_P_ID=(Integer)mTab.getValue("M_Product_ID");
		if ((Integer)mTab.getValue("M_Product_ID")!= null)
		{
			
			//	get values
			PlannedQty = (BigDecimal)mTab.getValue("PlannedQty");
			if (PlannedQty == null)
				PlannedQty = Env.ONE;
			PlannedPrice = ((BigDecimal)mTab.getValue("PlannedPrice"));
			if (PlannedPrice == null)
				PlannedPrice = Env.ZERO;
			Price_Before_Disc = ((BigDecimal)mTab.getValue("Price_Before_Disc"));
			if (Price_Before_Disc == null)
				Price_Before_Disc = Env.ZERO;
			Netselling_Price = ((BigDecimal)mTab.getValue("Netselling_Price"));
			if (Netselling_Price == null)
				Netselling_Price = Env.ZERO;
			
			PlannedPrice = DB.getSQLValueBD(null,"SELECT rmcost from M_Product where  M_Product_ID= ? ", M_P_ID);
			Netselling_Price=DB.getSQLValueBD(null,"SELECT pricelist from M_ProductPrice where M_PriceList_Version_ID=1000001 and M_Product_ID= ? ", M_P_ID);
			
			//
			BigDecimal PlannedAmt = PlannedQty.multiply(PlannedPrice);
			Price_Before_Disc=PlannedQty.multiply(Netselling_Price);
			if (PlannedAmt.scale() > StdPrecision)
				PlannedAmt = PlannedAmt.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
			if (Price_Before_Disc.scale() > StdPrecision)
				Price_Before_Disc = Price_Before_Disc.setScale(StdPrecision, BigDecimal.ROUND_HALF_UP);
			
			mTab.setValue("PlannedPrice", PlannedAmt);
			mTab.setValue("Price_Before_Disc", Price_Before_Disc);
			mTab.setValue("Netselling_Price", Price_Before_Disc);
			plannedmarginamt= Env.ZERO;
			mTab.setValue("plannedmarginamt", plannedmarginamt);
			return "";
			
			
		}
		

		
		return "";
	}
	
	
	//Checking For Business Partner
	public String BPCheck(Properties ctx, int WindowNo, GridTab mTab, GridField mField, Object value)
	{
		String sql = null;
		Integer RowNo =null;
		if ((Integer)mTab.getValue("C_BPartner_ID")!= null)
		{
			sql= "SELECT Value from C_BPartner where IsCustomer='Y' and C_BPartner_ID= ? " ;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1,(Integer)mTab.getValue("C_BPartner_ID"));
				rs = pstmt.executeQuery();
				rs.next();
				RowNo=rs.getRow();
				if (RowNo==0)
				{
					mTab.fireDataStatusEEvent("Select Customer", "Please Select a Valid Customer",true);
					mTab.setValue("C_BPartner_ID",null);
					return "";
				}
			}
			catch(Exception e){
							}
			
		}
			 
		return "";
	}
	
	//********************************BETA CODE END************************************************
}	//	CalloutProject
