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

/** Generated Model for Beta_FixAstList
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS - $Id$ */
public class X_Beta_FixAstList extends PO implements I_Beta_FixAstList, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110403L;

    /** Standard Constructor */
    public X_Beta_FixAstList (Properties ctx, int Beta_FixAstList_ID, String trxName)
    {
      super (ctx, Beta_FixAstList_ID, trxName);
      /** if (Beta_FixAstList_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_Beta_FixAstList (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Beta_FixAstList[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set FIXAST_BAL.
		@param FIXAST_BAL FIXAST_BAL	  */
	public void setFIXAST_BAL (BigDecimal FIXAST_BAL)
	{
		set_Value (COLUMNNAME_FIXAST_BAL, FIXAST_BAL);
	}

	/** Get FIXAST_BAL.
		@return FIXAST_BAL	  */
	public BigDecimal getFIXAST_BAL () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FIXAST_BAL);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set FIXAST_MONOPDEP.
		@param FIXAST_MONOPDEP FIXAST_MONOPDEP	  */
	public void setFIXAST_MONOPDEP (BigDecimal FIXAST_MONOPDEP)
	{
		set_Value (COLUMNNAME_FIXAST_MONOPDEP, FIXAST_MONOPDEP);
	}

	/** Get FIXAST_MONOPDEP.
		@return FIXAST_MONOPDEP	  */
	public BigDecimal getFIXAST_MONOPDEP () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FIXAST_MONOPDEP);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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
}