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

/** Generated Model for C_PROJECTLINE_BOM
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS - $Id$ */
public class X_C_PROJECTLINE_BOM extends PO implements I_C_PROJECTLINE_BOM, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110403L;

    /** Standard Constructor */
    public X_C_PROJECTLINE_BOM (Properties ctx, int C_PROJECTLINE_BOM_ID, String trxName)
    {
      super (ctx, C_PROJECTLINE_BOM_ID, trxName);
      /** if (C_PROJECTLINE_BOM_ID == 0)
        {
			setC_PROJECTLINE_BOM_ID (0);
			setC_ProjectLine_ID (0);
			setProcessed (false);
        } */
    }

    /** Load Constructor */
    public X_C_PROJECTLINE_BOM (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_PROJECTLINE_BOM[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Project Line BOM.
		@param C_PROJECTLINE_BOM_ID Project Line BOM	  */
	public void setC_PROJECTLINE_BOM_ID (int C_PROJECTLINE_BOM_ID)
	{
		if (C_PROJECTLINE_BOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PROJECTLINE_BOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PROJECTLINE_BOM_ID, Integer.valueOf(C_PROJECTLINE_BOM_ID));
	}

	/** Get Project Line BOM.
		@return Project Line BOM	  */
	public int getC_PROJECTLINE_BOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PROJECTLINE_BOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ProjectLine getC_ProjectLine() throws RuntimeException
    {
		return (I_C_ProjectLine)MTable.get(getCtx(), I_C_ProjectLine.Table_Name)
			.getPO(getC_ProjectLine_ID(), get_TrxName());	}

	/** Set Project Line.
		@param C_ProjectLine_ID 
		Task or step in a project
	  */
	public void setC_ProjectLine_ID (int C_ProjectLine_ID)
	{
		if (C_ProjectLine_ID < 1) 
			set_Value (COLUMNNAME_C_ProjectLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_ProjectLine_ID, Integer.valueOf(C_ProjectLine_ID));
	}

	/** Get Project Line.
		@return Task or step in a project
	  */
	public int getC_ProjectLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ProjectLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Product getM_Product() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
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
}