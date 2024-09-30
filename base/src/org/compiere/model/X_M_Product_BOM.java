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
import org.compiere.util.KeyNamePair;

/** Generated Model for M_Product_BOM
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS - $Id$ */
public class X_M_Product_BOM extends PO implements I_M_Product_BOM, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110125L;

    /** Standard Constructor */
    public X_M_Product_BOM (Properties ctx, int M_Product_BOM_ID, String trxName)
    {
      super (ctx, M_Product_BOM_ID, trxName);
      /** if (M_Product_BOM_ID == 0)
        {
			setBOMQty (Env.ZERO);
// 1
			setLine (0);
// @SQL=SELECT NVL(MAX(Line),0)+10 AS DefaultValue FROM M_Product_BOM WHERE M_Product_ID=@M_Product_ID@
			setM_Product_BOM_ID (0);
			setM_Product_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_Product_BOM (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_M_Product_BOM[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set BOM Quantity.
		@param BOMQty 
		Bill of Materials Quantity
	  */
	public void setBOMQty (BigDecimal BOMQty)
	{
		set_Value (COLUMNNAME_BOMQty, BOMQty);
	}

	/** Get BOM Quantity.
		@return Bill of Materials Quantity
	  */
	public BigDecimal getBOMQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_BOMQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** BOMType AD_Reference_ID=279 */
	public static final int BOMTYPE_AD_Reference_ID=279;
	/** Standard Part = P */
	public static final String BOMTYPE_StandardPart = "P";
	/** Optional Part = O */
	public static final String BOMTYPE_OptionalPart = "O";
	/** In alternative Group 1 = 1 */
	public static final String BOMTYPE_InAlternativeGroup1 = "1";
	/** In alternative Group 2 = 2 */
	public static final String BOMTYPE_InAlternativeGroup2 = "2";
	/** In alternaltve Group 3 = 3 */
	public static final String BOMTYPE_InAlternaltveGroup3 = "3";
	/** In alternative Group 4 = 4 */
	public static final String BOMTYPE_InAlternativeGroup4 = "4";
	/** In alternative Group 5 = 5 */
	public static final String BOMTYPE_InAlternativeGroup5 = "5";
	/** In alternative Group 6 = 6 */
	public static final String BOMTYPE_InAlternativeGroup6 = "6";
	/** In alternative Group 7 = 7 */
	public static final String BOMTYPE_InAlternativeGroup7 = "7";
	/** In alternative Group 8 = 8 */
	public static final String BOMTYPE_InAlternativeGroup8 = "8";
	/** In alternative Group 9 = 9 */
	public static final String BOMTYPE_InAlternativeGroup9 = "9";
	/** Set BOM Type.
		@param BOMType 
		Type of BOM
	  */
	public void setBOMType (String BOMType)
	{

		set_Value (COLUMNNAME_BOMType, BOMType);
	}

	/** Get BOM Type.
		@return Type of BOM
	  */
	public String getBOMType () 
	{
		return (String)get_Value(COLUMNNAME_BOMType);
	}

	/** Set Cost Factor Cell From.
		@param COSTFACTOR_CELL_ADDR Cost Factor Cell From	  */
	public void setCOSTFACTOR_CELL_ADDR (String COSTFACTOR_CELL_ADDR)
	{
		set_Value (COLUMNNAME_COSTFACTOR_CELL_ADDR, COSTFACTOR_CELL_ADDR);
	}

	/** Get Cost Factor Cell From.
		@return Cost Factor Cell From	  */
	public String getCOSTFACTOR_CELL_ADDR () 
	{
		return (String)get_Value(COLUMNNAME_COSTFACTOR_CELL_ADDR);
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

	/** Set Raw Mat Cell From.
		@param FROM_RMPROD_CELL_ADDR Raw Mat Cell From	  */
	public void setFROM_RMPROD_CELL_ADDR (String FROM_RMPROD_CELL_ADDR)
	{
		set_Value (COLUMNNAME_FROM_RMPROD_CELL_ADDR, FROM_RMPROD_CELL_ADDR);
	}

	/** Get Raw Mat Cell From.
		@return Raw Mat Cell From	  */
	public String getFROM_RMPROD_CELL_ADDR () 
	{
		return (String)get_Value(COLUMNNAME_FROM_RMPROD_CELL_ADDR);
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

	public I_M_Product getM_ProductBOM() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_ProductBOM_ID(), get_TrxName());	}

	/** Set BOM Product.
		@param M_ProductBOM_ID 
		Bill of Material Component Product
	  */
	public void setM_ProductBOM_ID (int M_ProductBOM_ID)
	{
		if (M_ProductBOM_ID < 1) 
			set_Value (COLUMNNAME_M_ProductBOM_ID, null);
		else 
			set_Value (COLUMNNAME_M_ProductBOM_ID, Integer.valueOf(M_ProductBOM_ID));
	}

	/** Get BOM Product.
		@return Bill of Material Component Product
	  */
	public int getM_ProductBOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductBOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getM_ProductBOM_ID()));
    }

	/** Set BOM Line.
		@param M_Product_BOM_ID BOM Line	  */
	public void setM_Product_BOM_ID (int M_Product_BOM_ID)
	{
		if (M_Product_BOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Product_BOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_BOM_ID, Integer.valueOf(M_Product_BOM_ID));
	}

	/** Get BOM Line.
		@return BOM Line	  */
	public int getM_Product_BOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_BOM_ID);
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

	/** Set Qty Cell From.
		@param QTY_CELL_ADDR Qty Cell From	  */
	public void setQTY_CELL_ADDR (String QTY_CELL_ADDR)
	{
		set_Value (COLUMNNAME_QTY_CELL_ADDR, QTY_CELL_ADDR);
	}

	/** Get Qty Cell From.
		@return Qty Cell From	  */
	public String getQTY_CELL_ADDR () 
	{
		return (String)get_Value(COLUMNNAME_QTY_CELL_ADDR);
	}

	/** Set Total Cost Cell From.
		@param RMCOST_CELL_ADDR Total Cost Cell From	  */
	public void setRMCOST_CELL_ADDR (String RMCOST_CELL_ADDR)
	{
		set_Value (COLUMNNAME_RMCOST_CELL_ADDR, RMCOST_CELL_ADDR);
	}

	/** Get Total Cost Cell From.
		@return Total Cost Cell From	  */
	public String getRMCOST_CELL_ADDR () 
	{
		return (String)get_Value(COLUMNNAME_RMCOST_CELL_ADDR);
	}

	/** Set Std Cost Cell From.
		@param STDCOST_CELL_ADDR Std Cost Cell From	  */
	public void setSTDCOST_CELL_ADDR (String STDCOST_CELL_ADDR)
	{
		set_Value (COLUMNNAME_STDCOST_CELL_ADDR, STDCOST_CELL_ADDR);
	}

	/** Get Std Cost Cell From.
		@return Std Cost Cell From	  */
	public String getSTDCOST_CELL_ADDR () 
	{
		return (String)get_Value(COLUMNNAME_STDCOST_CELL_ADDR);
	}

	/** Set Cost Factor Cell To.
		@param TO_COSTFACTOR_CELL_ADDR Cost Factor Cell To	  */
	public void setTO_COSTFACTOR_CELL_ADDR (String TO_COSTFACTOR_CELL_ADDR)
	{
		set_Value (COLUMNNAME_TO_COSTFACTOR_CELL_ADDR, TO_COSTFACTOR_CELL_ADDR);
	}

	/** Get Cost Factor Cell To.
		@return Cost Factor Cell To	  */
	public String getTO_COSTFACTOR_CELL_ADDR () 
	{
		return (String)get_Value(COLUMNNAME_TO_COSTFACTOR_CELL_ADDR);
	}

	/** Set Qty Cell To.
		@param TO_QTY_CELL_ADDR Qty Cell To	  */
	public void setTO_QTY_CELL_ADDR (String TO_QTY_CELL_ADDR)
	{
		set_Value (COLUMNNAME_TO_QTY_CELL_ADDR, TO_QTY_CELL_ADDR);
	}

	/** Get Qty Cell To.
		@return Qty Cell To	  */
	public String getTO_QTY_CELL_ADDR () 
	{
		return (String)get_Value(COLUMNNAME_TO_QTY_CELL_ADDR);
	}

	/** Set Total RM Cost To.
		@param TO_RMCOST_CELL_ADDR Total RM Cost To	  */
	public void setTO_RMCOST_CELL_ADDR (String TO_RMCOST_CELL_ADDR)
	{
		set_Value (COLUMNNAME_TO_RMCOST_CELL_ADDR, TO_RMCOST_CELL_ADDR);
	}

	/** Get Total RM Cost To.
		@return Total RM Cost To	  */
	public String getTO_RMCOST_CELL_ADDR () 
	{
		return (String)get_Value(COLUMNNAME_TO_RMCOST_CELL_ADDR);
	}

	/** Set Raw Mat Cell To.
		@param TO_RMPROD_CELL_ADDR Raw Mat Cell To	  */
	public void setTO_RMPROD_CELL_ADDR (String TO_RMPROD_CELL_ADDR)
	{
		set_Value (COLUMNNAME_TO_RMPROD_CELL_ADDR, TO_RMPROD_CELL_ADDR);
	}

	/** Get Raw Mat Cell To.
		@return Raw Mat Cell To	  */
	public String getTO_RMPROD_CELL_ADDR () 
	{
		return (String)get_Value(COLUMNNAME_TO_RMPROD_CELL_ADDR);
	}

	/** Set Std Cost Cell To.
		@param TO_STDCOST_CELL_ADDR Std Cost Cell To	  */
	public void setTO_STDCOST_CELL_ADDR (String TO_STDCOST_CELL_ADDR)
	{
		set_Value (COLUMNNAME_TO_STDCOST_CELL_ADDR, TO_STDCOST_CELL_ADDR);
	}

	/** Get Std Cost Cell To.
		@return Std Cost Cell To	  */
	public String getTO_STDCOST_CELL_ADDR () 
	{
		return (String)get_Value(COLUMNNAME_TO_STDCOST_CELL_ADDR);
	}

	/** Set Unit Cost Cell To.
		@param TO_UNITCOST_CELL_ADDR Unit Cost Cell To	  */
	public void setTO_UNITCOST_CELL_ADDR (String TO_UNITCOST_CELL_ADDR)
	{
		set_Value (COLUMNNAME_TO_UNITCOST_CELL_ADDR, TO_UNITCOST_CELL_ADDR);
	}

	/** Get Unit Cost Cell To.
		@return Unit Cost Cell To	  */
	public String getTO_UNITCOST_CELL_ADDR () 
	{
		return (String)get_Value(COLUMNNAME_TO_UNITCOST_CELL_ADDR);
	}

	/** Set Unit Cost Cell From.
		@param UNITCOST_CELL_ADDR Unit Cost Cell From	  */
	public void setUNITCOST_CELL_ADDR (String UNITCOST_CELL_ADDR)
	{
		set_Value (COLUMNNAME_UNITCOST_CELL_ADDR, UNITCOST_CELL_ADDR);
	}

	/** Get Unit Cost Cell From.
		@return Unit Cost Cell From	  */
	public String getUNITCOST_CELL_ADDR () 
	{
		return (String)get_Value(COLUMNNAME_UNITCOST_CELL_ADDR);
	}
}