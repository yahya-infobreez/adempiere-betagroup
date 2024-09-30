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

/** Generated Model for XX_PRODUCT_ATTRIBUTE
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS - $Id$ */
public class X_XX_PRODUCT_ATTRIBUTE extends PO implements I_XX_PRODUCT_ATTRIBUTE, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110125L;

    /** Standard Constructor */
    public X_XX_PRODUCT_ATTRIBUTE (Properties ctx, int XX_PRODUCT_ATTRIBUTE_ID, String trxName)
    {
      super (ctx, XX_PRODUCT_ATTRIBUTE_ID, trxName);
      /** if (XX_PRODUCT_ATTRIBUTE_ID == 0)
        {
			setXX_PRODUCT_ATTRIBUTE_ID (0);
        } */
    }

    /** Load Constructor */
    public X_XX_PRODUCT_ATTRIBUTE (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_XX_PRODUCT_ATTRIBUTE[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Attribute Cell.
		@param ATTRIBUTE_CELL_ADDR Attribute Cell	  */
	public void setATTRIBUTE_CELL_ADDR (String ATTRIBUTE_CELL_ADDR)
	{
		set_Value (COLUMNNAME_ATTRIBUTE_CELL_ADDR, ATTRIBUTE_CELL_ADDR);
	}

	/** Get Attribute Cell.
		@return Attribute Cell	  */
	public String getATTRIBUTE_CELL_ADDR () 
	{
		return (String)get_Value(COLUMNNAME_ATTRIBUTE_CELL_ADDR);
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
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
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

	/** Set Product Attribute.
		@param XX_PRODUCT_ATTRIBUTE_ID Product Attribute	  */
	public void setXX_PRODUCT_ATTRIBUTE_ID (int XX_PRODUCT_ATTRIBUTE_ID)
	{
		if (XX_PRODUCT_ATTRIBUTE_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_XX_PRODUCT_ATTRIBUTE_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_XX_PRODUCT_ATTRIBUTE_ID, Integer.valueOf(XX_PRODUCT_ATTRIBUTE_ID));
	}

	/** Get Product Attribute.
		@return Product Attribute	  */
	public int getXX_PRODUCT_ATTRIBUTE_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_XX_PRODUCT_ATTRIBUTE_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}