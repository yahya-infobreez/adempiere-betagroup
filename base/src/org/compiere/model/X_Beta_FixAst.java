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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for Beta_FixAst
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS - $Id$ */
public class X_Beta_FixAst extends PO implements I_Beta_FixAst, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110403L;

    /** Standard Constructor */
    public X_Beta_FixAst (Properties ctx, int Beta_FixAst_ID, String trxName)
    {
      super (ctx, Beta_FixAst_ID, trxName);
      /** if (Beta_FixAst_ID == 0)
        {
			setFIXAST_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Beta_FixAst (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Beta_FixAst[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set FIXAST_COST.
		@param FIXAST_COST FIXAST_COST	  */
	public void setFIXAST_COST (BigDecimal FIXAST_COST)
	{
		set_Value (COLUMNNAME_FIXAST_COST, FIXAST_COST);
	}

	/** Get FIXAST_COST.
		@return FIXAST_COST	  */
	public BigDecimal getFIXAST_COST () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FIXAST_COST);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set FIXAST_DATE.
		@param FIXAST_DATE FIXAST_DATE	  */
	public void setFIXAST_DATE (Timestamp FIXAST_DATE)
	{
		set_Value (COLUMNNAME_FIXAST_DATE, FIXAST_DATE);
	}

	/** Get FIXAST_DATE.
		@return FIXAST_DATE	  */
	public Timestamp getFIXAST_DATE () 
	{
		return (Timestamp)get_Value(COLUMNNAME_FIXAST_DATE);
	}

	/** Set FIXAST_DEPPER.
		@param FIXAST_DEPPER FIXAST_DEPPER	  */
	public void setFIXAST_DEPPER (BigDecimal FIXAST_DEPPER)
	{
		set_Value (COLUMNNAME_FIXAST_DEPPER, FIXAST_DEPPER);
	}

	/** Get FIXAST_DEPPER.
		@return FIXAST_DEPPER	  */
	public BigDecimal getFIXAST_DEPPER () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FIXAST_DEPPER);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set FIXAST_DESC.
		@param FIXAST_DESC FIXAST_DESC	  */
	public void setFIXAST_DESC (String FIXAST_DESC)
	{
		set_Value (COLUMNNAME_FIXAST_DESC, FIXAST_DESC);
	}

	/** Get FIXAST_DESC.
		@return FIXAST_DESC	  */
	public String getFIXAST_DESC () 
	{
		return (String)get_Value(COLUMNNAME_FIXAST_DESC);
	}

	/** Set FIXAST_ID.
		@param FIXAST_ID FIXAST_ID	  */
	public void setFIXAST_ID (int FIXAST_ID)
	{
		if (FIXAST_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_FIXAST_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_FIXAST_ID, Integer.valueOf(FIXAST_ID));
	}

	/** Get FIXAST_ID.
		@return FIXAST_ID	  */
	public int getFIXAST_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_FIXAST_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set FIXAST_MONTHDEPAMT.
		@param FIXAST_MONTHDEPAMT FIXAST_MONTHDEPAMT	  */
	public void setFIXAST_MONTHDEPAMT (BigDecimal FIXAST_MONTHDEPAMT)
	{
		set_Value (COLUMNNAME_FIXAST_MONTHDEPAMT, FIXAST_MONTHDEPAMT);
	}

	/** Get FIXAST_MONTHDEPAMT.
		@return FIXAST_MONTHDEPAMT	  */
	public BigDecimal getFIXAST_MONTHDEPAMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FIXAST_MONTHDEPAMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set FIXAST_QTY.
		@param FIXAST_QTY FIXAST_QTY	  */
	public void setFIXAST_QTY (BigDecimal FIXAST_QTY)
	{
		set_Value (COLUMNNAME_FIXAST_QTY, FIXAST_QTY);
	}

	/** Get FIXAST_QTY.
		@return FIXAST_QTY	  */
	public BigDecimal getFIXAST_QTY () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FIXAST_QTY);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set FIXAST_TOTDEPAMT.
		@param FIXAST_TOTDEPAMT FIXAST_TOTDEPAMT	  */
	public void setFIXAST_TOTDEPAMT (BigDecimal FIXAST_TOTDEPAMT)
	{
		set_Value (COLUMNNAME_FIXAST_TOTDEPAMT, FIXAST_TOTDEPAMT);
	}

	/** Get FIXAST_TOTDEPAMT.
		@return FIXAST_TOTDEPAMT	  */
	public BigDecimal getFIXAST_TOTDEPAMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FIXAST_TOTDEPAMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set FIXAST_TYPE.
		@param FIXAST_TYPE FIXAST_TYPE	  */
	public void setFIXAST_TYPE (String FIXAST_TYPE)
	{
		set_Value (COLUMNNAME_FIXAST_TYPE, FIXAST_TYPE);
	}

	/** Get FIXAST_TYPE.
		@return FIXAST_TYPE	  */
	public String getFIXAST_TYPE () 
	{
		return (String)get_Value(COLUMNNAME_FIXAST_TYPE);
	}

	/** Set FIXAST_VALUE.
		@param FIXAST_VALUE FIXAST_VALUE	  */
	public void setFIXAST_VALUE (String FIXAST_VALUE)
	{
		set_Value (COLUMNNAME_FIXAST_VALUE, FIXAST_VALUE);
	}

	/** Get FIXAST_VALUE.
		@return FIXAST_VALUE	  */
	public String getFIXAST_VALUE () 
	{
		return (String)get_Value(COLUMNNAME_FIXAST_VALUE);
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
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
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
}