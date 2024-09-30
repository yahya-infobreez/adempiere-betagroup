/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for XX_QUOTATIONLINE_RMCOST
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS - $Id$ */
public class X_XX_QUOTATIONLINE_RMCOST extends PO implements I_XX_QUOTATIONLINE_RMCOST, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110125L;

    /** Standard Constructor */
    public X_XX_QUOTATIONLINE_RMCOST (Properties ctx, int XX_QUOTATIONLINE_RMCOST_ID, String trxName)
    {
      super (ctx, XX_QUOTATIONLINE_RMCOST_ID, trxName);
      /** if (XX_QUOTATIONLINE_RMCOST_ID == 0)
        {
			setC_QUOTATIONLINE_ID (0);
			setXX_QUOTATIONLINE_RMCOST_ID (0);
        } */
    }

    /** Load Constructor */
    public X_XX_QUOTATIONLINE_RMCOST (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_XX_QUOTATIONLINE_RMCOST[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Cost Factor.
		@param COST_FACTOR Cost Factor	  */
	public void setCOST_FACTOR (BigDecimal COST_FACTOR)
	{
		set_Value (COLUMNNAME_COST_FACTOR, COST_FACTOR);
	}

	/** Get Cost Factor.
		@return Cost Factor	  */
	public BigDecimal getCOST_FACTOR () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_COST_FACTOR);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_C_QUOTATIONLINE getC_QUOTATIONLINE() throws RuntimeException
    {
		return (I_C_QUOTATIONLINE)MTable.get(getCtx(), I_C_QUOTATIONLINE.Table_Name)
			.getPO(getC_QUOTATIONLINE_ID(), get_TrxName());	}

	/** Set Quotation Line.
		@param C_QUOTATIONLINE_ID Quotation Line	  */
	public void setC_QUOTATIONLINE_ID (int C_QUOTATIONLINE_ID)
	{
		if (C_QUOTATIONLINE_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_QUOTATIONLINE_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_QUOTATIONLINE_ID, Integer.valueOf(C_QUOTATIONLINE_ID));
	}

	/** Get Quotation Line.
		@return Quotation Line	  */
	public int getC_QUOTATIONLINE_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_QUOTATIONLINE_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RM_AMOUNT.
		@param RM_AMOUNT RM_AMOUNT	  */
	public void setRM_AMOUNT (BigDecimal RM_AMOUNT)
	{
		set_Value (COLUMNNAME_RM_AMOUNT, RM_AMOUNT);
	}

	/** Get RM_AMOUNT.
		@return RM_AMOUNT	  */
	public BigDecimal getRM_AMOUNT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RM_AMOUNT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_M_Product getRM_PRODUCT() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getRM_PRODUCT_ID(), get_TrxName());	}

	/** Set RM_PRODUCT_ID.
		@param RM_PRODUCT_ID RM_PRODUCT_ID	  */
	public void setRM_PRODUCT_ID (int RM_PRODUCT_ID)
	{
		if (RM_PRODUCT_ID < 1) 
			set_Value (COLUMNNAME_RM_PRODUCT_ID, null);
		else 
			set_Value (COLUMNNAME_RM_PRODUCT_ID, Integer.valueOf(RM_PRODUCT_ID));
	}

	/** Get RM_PRODUCT_ID.
		@return RM_PRODUCT_ID	  */
	public int getRM_PRODUCT_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RM_PRODUCT_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RM_QUANTITY.
		@param RM_QUANTITY RM_QUANTITY	  */
	public void setRM_QUANTITY (BigDecimal RM_QUANTITY)
	{
		set_Value (COLUMNNAME_RM_QUANTITY, RM_QUANTITY);
	}

	/** Get RM_QUANTITY.
		@return RM_QUANTITY	  */
	public BigDecimal getRM_QUANTITY () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RM_QUANTITY);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Standard Cost.
		@param STDCOST Standard Cost	  */
	public void setSTDCOST (BigDecimal STDCOST)
	{
		set_Value (COLUMNNAME_STDCOST, STDCOST);
	}

	/** Get Standard Cost.
		@return Standard Cost	  */
	public BigDecimal getSTDCOST () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_STDCOST);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Unit Cost.
		@param UNITCOST Unit Cost	  */
	public void setUNITCOST (BigDecimal UNITCOST)
	{
		set_Value (COLUMNNAME_UNITCOST, UNITCOST);
	}

	/** Get Unit Cost.
		@return Unit Cost	  */
	public BigDecimal getUNITCOST () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UNITCOST);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quotation Line BOM.
		@param XX_QUOTATIONLINE_RMCOST_ID Quotation Line BOM	  */
	public void setXX_QUOTATIONLINE_RMCOST_ID (int XX_QUOTATIONLINE_RMCOST_ID)
	{
		if (XX_QUOTATIONLINE_RMCOST_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_XX_QUOTATIONLINE_RMCOST_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_XX_QUOTATIONLINE_RMCOST_ID, Integer.valueOf(XX_QUOTATIONLINE_RMCOST_ID));
	}

	/** Get Quotation Line BOM.
		@return Quotation Line BOM	  */
	public int getXX_QUOTATIONLINE_RMCOST_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_XX_QUOTATIONLINE_RMCOST_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}