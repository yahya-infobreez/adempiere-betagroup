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

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for XX_QUOTATIONLINE_ATTRIB
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS - $Id$ */
public class X_XX_QUOTATIONLINE_ATTRIB extends PO implements I_XX_QUOTATIONLINE_ATTRIB, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110125L;

    /** Standard Constructor */
    public X_XX_QUOTATIONLINE_ATTRIB (Properties ctx, int XX_QUOTATIONLINE_ATTRIB_ID, String trxName)
    {
      super (ctx, XX_QUOTATIONLINE_ATTRIB_ID, trxName);
      /** if (XX_QUOTATIONLINE_ATTRIB_ID == 0)
        {
			setXX_QUOTATIONLINE_ATTRIB_ID (0);
        } */
    }

    /** Load Constructor */
    public X_XX_QUOTATIONLINE_ATTRIB (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_XX_QUOTATIONLINE_ATTRIB[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set ATTRIBUTE_VALUE.
		@param ATTRIBUTE_VALUE ATTRIBUTE_VALUE	  */
	public void setATTRIBUTE_VALUE (String ATTRIBUTE_VALUE)
	{
		set_Value (COLUMNNAME_ATTRIBUTE_VALUE, ATTRIBUTE_VALUE);
	}

	/** Get ATTRIBUTE_VALUE.
		@return ATTRIBUTE_VALUE	  */
	public String getATTRIBUTE_VALUE () 
	{
		return (String)get_Value(COLUMNNAME_ATTRIBUTE_VALUE);
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

	public I_M_Attribute getM_Attribute() throws RuntimeException
    {
		return (I_M_Attribute)MTable.get(getCtx(), I_M_Attribute.Table_Name)
			.getPO(getM_Attribute_ID(), get_TrxName());	}

	/** Set Attribute.
		@param M_Attribute_ID 
		Product Attribute
	  */
	public void setM_Attribute_ID (int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1) 
			set_Value (COLUMNNAME_M_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_M_Attribute_ID, Integer.valueOf(M_Attribute_ID));
	}

	/** Get Attribute.
		@return Product Attribute
	  */
	public int getM_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Quotation Line Attribute.
		@param XX_QUOTATIONLINE_ATTRIB_ID Quotation Line Attribute	  */
	public void setXX_QUOTATIONLINE_ATTRIB_ID (int XX_QUOTATIONLINE_ATTRIB_ID)
	{
		if (XX_QUOTATIONLINE_ATTRIB_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_XX_QUOTATIONLINE_ATTRIB_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_XX_QUOTATIONLINE_ATTRIB_ID, Integer.valueOf(XX_QUOTATIONLINE_ATTRIB_ID));
	}

	/** Get Quotation Line Attribute.
		@return Quotation Line Attribute	  */
	public int getXX_QUOTATIONLINE_ATTRIB_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_XX_QUOTATIONLINE_ATTRIB_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}