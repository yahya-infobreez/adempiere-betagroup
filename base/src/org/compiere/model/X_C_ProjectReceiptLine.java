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

/** Generated Model for C_ProjectReceiptLine
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS - $Id$ */
public class X_C_ProjectReceiptLine extends PO implements I_C_ProjectReceiptLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110403L;

    /** Standard Constructor */
    public X_C_ProjectReceiptLine (Properties ctx, int C_ProjectReceiptLine_ID, String trxName)
    {
      super (ctx, C_ProjectReceiptLine_ID, trxName);
      /** if (C_ProjectReceiptLine_ID == 0)
        {
			setC_ProjectLine_ID (0);
			setC_ProjectReceiptLine_ID (0);
			setC_ProjectReceipt_ID (0);
			setLine (0);
// @SQL=SELECT NVL(MAX(Line),0)+10 AS DefaultValue FROM C_ProjectReceiptLine WHERE C_ProjectReceipt_ID=@C_ProjectReceipt_ID@
			setQTYRECEIVED (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_C_ProjectReceiptLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_ProjectReceiptLine[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set C_ProjectReceiptLine.
		@param C_ProjectReceiptLine_ID C_ProjectReceiptLine	  */
	public void setC_ProjectReceiptLine_ID (int C_ProjectReceiptLine_ID)
	{
		if (C_ProjectReceiptLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ProjectReceiptLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ProjectReceiptLine_ID, Integer.valueOf(C_ProjectReceiptLine_ID));
	}

	/** Get C_ProjectReceiptLine.
		@return C_ProjectReceiptLine	  */
	public int getC_ProjectReceiptLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ProjectReceiptLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ProjectReceipt getC_ProjectReceipt() throws RuntimeException
    {
		return (I_C_ProjectReceipt)MTable.get(getCtx(), I_C_ProjectReceipt.Table_Name)
			.getPO(getC_ProjectReceipt_ID(), get_TrxName());	}

	/** Set Project Receipt.
		@param C_ProjectReceipt_ID Project Receipt	  */
	public void setC_ProjectReceipt_ID (int C_ProjectReceipt_ID)
	{
		if (C_ProjectReceipt_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_ProjectReceipt_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_ProjectReceipt_ID, Integer.valueOf(C_ProjectReceipt_ID));
	}

	/** Get Project Receipt.
		@return Project Receipt	  */
	public int getC_ProjectReceipt_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ProjectReceipt_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Line No.
		@param Line 
		Unique line for this document
	  */
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
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

	public I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (I_M_Warehouse)MTable.get(getCtx(), I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Planned Price.
		@param PlannedPrice 
		Planned price for this project line
	  */
	public void setPlannedPrice (BigDecimal PlannedPrice)
	{
		set_Value (COLUMNNAME_PlannedPrice, PlannedPrice);
	}

	/** Get Planned Price.
		@return Planned price for this project line
	  */
	public BigDecimal getPlannedPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PlannedPrice);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set QTYRECEIVED.
		@param QTYRECEIVED QTYRECEIVED	  */
	public void setQTYRECEIVED (BigDecimal QTYRECEIVED)
	{
		set_Value (COLUMNNAME_QTYRECEIVED, QTYRECEIVED);
	}

	/** Get QTYRECEIVED.
		@return QTYRECEIVED	  */
	public BigDecimal getQTYRECEIVED () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QTYRECEIVED);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TOTALLINE.
		@param TOTALLINE TOTALLINE	  */
	public void setTOTALLINE (BigDecimal TOTALLINE)
	{
		set_Value (COLUMNNAME_TOTALLINE, TOTALLINE);
	}

	/** Get TOTALLINE.
		@return TOTALLINE	  */
	public BigDecimal getTOTALLINE () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TOTALLINE);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}