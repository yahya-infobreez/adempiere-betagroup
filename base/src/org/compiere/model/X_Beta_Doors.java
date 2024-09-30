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

/** Generated Model for Beta_Doors
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS - $Id$ */
public class X_Beta_Doors extends PO implements I_Beta_Doors, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110403L;

    /** Standard Constructor */
    public X_Beta_Doors (Properties ctx, int Beta_Doors_ID, String trxName)
    {
      super (ctx, Beta_Doors_ID, trxName);
      /** if (Beta_Doors_ID == 0)
        {
			setBeta_Doors_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Beta_Doors (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_Beta_Doors[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Beta Doors Table (Fadi Table).
		@param Beta_Doors_ID Beta Doors Table (Fadi Table)	  */
	public void setBeta_Doors_ID (int Beta_Doors_ID)
	{
		if (Beta_Doors_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Beta_Doors_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Beta_Doors_ID, Integer.valueOf(Beta_Doors_ID));
	}

	/** Get Beta Doors Table (Fadi Table).
		@return Beta Doors Table (Fadi Table)	  */
	public int getBeta_Doors_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Beta_Doors_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CLOSER.
		@param CLOSER CLOSER	  */
	public void setCLOSER (BigDecimal CLOSER)
	{
		set_Value (COLUMNNAME_CLOSER, CLOSER);
	}

	/** Get CLOSER.
		@return CLOSER	  */
	public BigDecimal getCLOSER () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CLOSER);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set CODE1.
		@param CODE1 CODE1	  */
	public void setCODE1 (String CODE1)
	{
		set_Value (COLUMNNAME_CODE1, CODE1);
	}

	/** Get CODE1.
		@return CODE1	  */
	public String getCODE1 () 
	{
		return (String)get_Value(COLUMNNAME_CODE1);
	}

	/** Set CODEA.
		@param CODEA CODEA	  */
	public void setCODEA (String CODEA)
	{
		set_Value (COLUMNNAME_CODEA, CODEA);
	}

	/** Get CODEA.
		@return CODEA	  */
	public String getCODEA () 
	{
		return (String)get_Value(COLUMNNAME_CODEA);
	}

	/** Set CODEB.
		@param CODEB CODEB	  */
	public void setCODEB (String CODEB)
	{
		set_Value (COLUMNNAME_CODEB, CODEB);
	}

	/** Get CODEB.
		@return CODEB	  */
	public String getCODEB () 
	{
		return (String)get_Value(COLUMNNAME_CODEB);
	}

	/** Set CODEC.
		@param CODEC CODEC	  */
	public void setCODEC (String CODEC)
	{
		set_Value (COLUMNNAME_CODEC, CODEC);
	}

	/** Get CODEC.
		@return CODEC	  */
	public String getCODEC () 
	{
		return (String)get_Value(COLUMNNAME_CODEC);
	}

	/** Set CODED.
		@param CODED CODED	  */
	public void setCODED (String CODED)
	{
		set_Value (COLUMNNAME_CODED, CODED);
	}

	/** Get CODED.
		@return CODED	  */
	public String getCODED () 
	{
		return (String)get_Value(COLUMNNAME_CODED);
	}

	/** Set CODEE.
		@param CODEE CODEE	  */
	public void setCODEE (String CODEE)
	{
		set_Value (COLUMNNAME_CODEE, CODEE);
	}

	/** Get CODEE.
		@return CODEE	  */
	public String getCODEE () 
	{
		return (String)get_Value(COLUMNNAME_CODEE);
	}

	/** Set DEAD.
		@param DEAD DEAD	  */
	public void setDEAD (BigDecimal DEAD)
	{
		set_Value (COLUMNNAME_DEAD, DEAD);
	}

	/** Get DEAD.
		@return DEAD	  */
	public BigDecimal getDEAD () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DEAD);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set DEST1.
		@param DEST1 DEST1	  */
	public void setDEST1 (String DEST1)
	{
		set_Value (COLUMNNAME_DEST1, DEST1);
	}

	/** Get DEST1.
		@return DEST1	  */
	public String getDEST1 () 
	{
		return (String)get_Value(COLUMNNAME_DEST1);
	}

	/** Set DEST2.
		@param DEST2 DEST2	  */
	public void setDEST2 (String DEST2)
	{
		set_Value (COLUMNNAME_DEST2, DEST2);
	}

	/** Get DEST2.
		@return DEST2	  */
	public String getDEST2 () 
	{
		return (String)get_Value(COLUMNNAME_DEST2);
	}

	/** Set DOORNO.
		@param DOORNO DOORNO	  */
	public void setDOORNO (String DOORNO)
	{
		set_Value (COLUMNNAME_DOORNO, DOORNO);
	}

	/** Get DOORNO.
		@return DOORNO	  */
	public String getDOORNO () 
	{
		return (String)get_Value(COLUMNNAME_DOORNO);
	}

	/** Set DOORUC.
		@param DOORUC DOORUC	  */
	public void setDOORUC (BigDecimal DOORUC)
	{
		set_Value (COLUMNNAME_DOORUC, DOORUC);
	}

	/** Get DOORUC.
		@return DOORUC	  */
	public BigDecimal getDOORUC () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DOORUC);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Discount %.
		@param Discount 
		Discount in percent
	  */
	public void setDiscount (BigDecimal Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
	}

	/** Get Discount %.
		@return Discount in percent
	  */
	public BigDecimal getDiscount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Discount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set EMBEDDING.
		@param EMBEDDING EMBEDDING	  */
	public void setEMBEDDING (BigDecimal EMBEDDING)
	{
		set_Value (COLUMNNAME_EMBEDDING, EMBEDDING);
	}

	/** Get EMBEDDING.
		@return EMBEDDING	  */
	public BigDecimal getEMBEDDING () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_EMBEDDING);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set EXPANSION.
		@param EXPANSION EXPANSION	  */
	public void setEXPANSION (BigDecimal EXPANSION)
	{
		set_Value (COLUMNNAME_EXPANSION, EXPANSION);
	}

	/** Get EXPANSION.
		@return EXPANSION	  */
	public BigDecimal getEXPANSION () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_EXPANSION);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set FL.
		@param FL FL	  */
	public void setFL (BigDecimal FL)
	{
		set_Value (COLUMNNAME_FL, FL);
	}

	/** Get FL.
		@return FL	  */
	public BigDecimal getFL () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FL);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set FLCOST.
		@param FLCOST FLCOST	  */
	public void setFLCOST (BigDecimal FLCOST)
	{
		set_Value (COLUMNNAME_FLCOST, FLCOST);
	}

	/** Get FLCOST.
		@return FLCOST	  */
	public BigDecimal getFLCOST () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FLCOST);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set FLUSH.
		@param FLUSH FLUSH	  */
	public void setFLUSH (BigDecimal FLUSH)
	{
		set_Value (COLUMNNAME_FLUSH, FLUSH);
	}

	/** Get FLUSH.
		@return FLUSH	  */
	public BigDecimal getFLUSH () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FLUSH);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set FOAMCOST.
		@param FOAMCOST FOAMCOST	  */
	public void setFOAMCOST (BigDecimal FOAMCOST)
	{
		set_Value (COLUMNNAME_FOAMCOST, FOAMCOST);
	}

	/** Get FOAMCOST.
		@return FOAMCOST	  */
	public BigDecimal getFOAMCOST () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FOAMCOST);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set FOS.
		@param FOS FOS	  */
	public void setFOS (BigDecimal FOS)
	{
		set_Value (COLUMNNAME_FOS, FOS);
	}

	/** Get FOS.
		@return FOS	  */
	public BigDecimal getFOS () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FOS);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set FRNFR.
		@param FRNFR FRNFR	  */
	public void setFRNFR (String FRNFR)
	{
		set_Value (COLUMNNAME_FRNFR, FRNFR);
	}

	/** Get FRNFR.
		@return FRNFR	  */
	public String getFRNFR () 
	{
		return (String)get_Value(COLUMNNAME_FRNFR);
	}

	/** Set HEIGHT.
		@param HEIGHT HEIGHT	  */
	public void setHEIGHT (BigDecimal HEIGHT)
	{
		set_Value (COLUMNNAME_HEIGHT, HEIGHT);
	}

	/** Get HEIGHT.
		@return HEIGHT	  */
	public BigDecimal getHEIGHT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_HEIGHT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set HINGECOVER.
		@param HINGECOVER HINGECOVER	  */
	public void setHINGECOVER (BigDecimal HINGECOVER)
	{
		set_Value (COLUMNNAME_HINGECOVER, HINGECOVER);
	}

	/** Get HINGECOVER.
		@return HINGECOVER	  */
	public BigDecimal getHINGECOVER () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_HINGECOVER);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set HINGES.
		@param HINGES HINGES	  */
	public void setHINGES (BigDecimal HINGES)
	{
		set_Value (COLUMNNAME_HINGES, HINGES);
	}

	/** Get HINGES.
		@return HINGES	  */
	public BigDecimal getHINGES () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_HINGES);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set HINGP.
		@param HINGP HINGP	  */
	public void setHINGP (BigDecimal HINGP)
	{
		set_Value (COLUMNNAME_HINGP, HINGP);
	}

	/** Get HINGP.
		@return HINGP	  */
	public BigDecimal getHINGP () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_HINGP);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set INDICATOR.
		@param INDICATOR INDICATOR	  */
	public void setINDICATOR (BigDecimal INDICATOR)
	{
		set_Value (COLUMNNAME_INDICATOR, INDICATOR);
	}

	/** Get INDICATOR.
		@return INDICATOR	  */
	public BigDecimal getINDICATOR () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_INDICATOR);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set JAMB.
		@param JAMB JAMB	  */
	public void setJAMB (BigDecimal JAMB)
	{
		set_Value (COLUMNNAME_JAMB, JAMB);
	}

	/** Get JAMB.
		@return JAMB	  */
	public BigDecimal getJAMB () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_JAMB);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set JAMBREL.
		@param JAMBREL JAMBREL	  */
	public void setJAMBREL (BigDecimal JAMBREL)
	{
		set_Value (COLUMNNAME_JAMBREL, JAMBREL);
	}

	/** Get JAMBREL.
		@return JAMBREL	  */
	public BigDecimal getJAMBREL () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_JAMBREL);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set JAMBTYPE.
		@param JAMBTYPE JAMBTYPE	  */
	public void setJAMBTYPE (String JAMBTYPE)
	{
		set_Value (COLUMNNAME_JAMBTYPE, JAMBTYPE);
	}

	/** Get JAMBTYPE.
		@return JAMBTYPE	  */
	public String getJAMBTYPE () 
	{
		return (String)get_Value(COLUMNNAME_JAMBTYPE);
	}

	/** Set LH.
		@param LH LH	  */
	public void setLH (BigDecimal LH)
	{
		set_Value (COLUMNNAME_LH, LH);
	}

	/** Get LH.
		@return LH	  */
	public BigDecimal getLH () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LH);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set LHA.
		@param LHA LHA	  */
	public void setLHA (BigDecimal LHA)
	{
		set_Value (COLUMNNAME_LHA, LHA);
	}

	/** Get LHA.
		@return LHA	  */
	public BigDecimal getLHA () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LHA);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set LHR.
		@param LHR LHR	  */
	public void setLHR (BigDecimal LHR)
	{
		set_Value (COLUMNNAME_LHR, LHR);
	}

	/** Get LHR.
		@return LHR	  */
	public BigDecimal getLHR () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LHR);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set LHRA.
		@param LHRA LHRA	  */
	public void setLHRA (BigDecimal LHRA)
	{
		set_Value (COLUMNNAME_LHRA, LHRA);
	}

	/** Get LHRA.
		@return LHRA	  */
	public BigDecimal getLHRA () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LHRA);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set LOCATION.
		@param LOCATION LOCATION	  */
	public void setLOCATION (String LOCATION)
	{
		set_Value (COLUMNNAME_LOCATION, LOCATION);
	}

	/** Get LOCATION.
		@return LOCATION	  */
	public String getLOCATION () 
	{
		return (String)get_Value(COLUMNNAME_LOCATION);
	}

	/** Set LOCKCOVERC.
		@param LOCKCOVERC LOCKCOVERC	  */
	public void setLOCKCOVERC (BigDecimal LOCKCOVERC)
	{
		set_Value (COLUMNNAME_LOCKCOVERC, LOCKCOVERC);
	}

	/** Get LOCKCOVERC.
		@return LOCKCOVERC	  */
	public BigDecimal getLOCKCOVERC () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LOCKCOVERC);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set LSHAPECOST.
		@param LSHAPECOST LSHAPECOST	  */
	public void setLSHAPECOST (BigDecimal LSHAPECOST)
	{
		set_Value (COLUMNNAME_LSHAPECOST, LSHAPECOST);
	}

	/** Get LSHAPECOST.
		@return LSHAPECOST	  */
	public BigDecimal getLSHAPECOST () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LSHAPECOST);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set LV.
		@param LV LV	  */
	public void setLV (BigDecimal LV)
	{
		set_Value (COLUMNNAME_LV, LV);
	}

	/** Get LV.
		@return LV	  */
	public BigDecimal getLV () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LV);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set LVCOST.
		@param LVCOST LVCOST	  */
	public void setLVCOST (BigDecimal LVCOST)
	{
		set_Value (COLUMNNAME_LVCOST, LVCOST);
	}

	/** Get LVCOST.
		@return LVCOST	  */
	public BigDecimal getLVCOST () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LVCOST);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set MORTISE.
		@param MORTISE MORTISE	  */
	public void setMORTISE (BigDecimal MORTISE)
	{
		set_Value (COLUMNNAME_MORTISE, MORTISE);
	}

	/** Get MORTISE.
		@return MORTISE	  */
	public BigDecimal getMORTISE () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MORTISE);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set NO.
		@param NO NO	  */
	public void setNO (BigDecimal NO)
	{
		set_Value (COLUMNNAME_NO, NO);
	}

	/** Get NO.
		@return NO	  */
	public BigDecimal getNO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NO);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set NOTES.
		@param NOTES NOTES	  */
	public void setNOTES (String NOTES)
	{
		set_Value (COLUMNNAME_NOTES, NOTES);
	}

	/** Get NOTES.
		@return NOTES	  */
	public String getNOTES () 
	{
		return (String)get_Value(COLUMNNAME_NOTES);
	}

	/** Set PANIC.
		@param PANIC PANIC	  */
	public void setPANIC (BigDecimal PANIC)
	{
		set_Value (COLUMNNAME_PANIC, PANIC);
	}

	/** Get PANIC.
		@return PANIC	  */
	public BigDecimal getPANIC () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PANIC);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PANICV.
		@param PANICV PANICV	  */
	public void setPANICV (BigDecimal PANICV)
	{
		set_Value (COLUMNNAME_PANICV, PANICV);
	}

	/** Get PANICV.
		@return PANICV	  */
	public BigDecimal getPANICV () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PANICV);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set POWDER.
		@param POWDER POWDER	  */
	public void setPOWDER (String POWDER)
	{
		set_Value (COLUMNNAME_POWDER, POWDER);
	}

	/** Get POWDER.
		@return POWDER	  */
	public String getPOWDER () 
	{
		return (String)get_Value(COLUMNNAME_POWDER);
	}

	/** Set POWDERCOST.
		@param POWDERCOST POWDERCOST	  */
	public void setPOWDERCOST (BigDecimal POWDERCOST)
	{
		set_Value (COLUMNNAME_POWDERCOST, POWDERCOST);
	}

	/** Get POWDERCOST.
		@return POWDERCOST	  */
	public BigDecimal getPOWDERCOST () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_POWDERCOST);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PP.
		@param PP PP	  */
	public void setPP (BigDecimal PP)
	{
		set_Value (COLUMNNAME_PP, PP);
	}

	/** Get PP.
		@return PP	  */
	public BigDecimal getPP () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PP);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set RH.
		@param RH RH	  */
	public void setRH (BigDecimal RH)
	{
		set_Value (COLUMNNAME_RH, RH);
	}

	/** Get RH.
		@return RH	  */
	public BigDecimal getRH () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RH);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set RHA.
		@param RHA RHA	  */
	public void setRHA (BigDecimal RHA)
	{
		set_Value (COLUMNNAME_RHA, RHA);
	}

	/** Get RHA.
		@return RHA	  */
	public BigDecimal getRHA () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RHA);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set RHR.
		@param RHR RHR	  */
	public void setRHR (BigDecimal RHR)
	{
		set_Value (COLUMNNAME_RHR, RHR);
	}

	/** Get RHR.
		@return RHR	  */
	public BigDecimal getRHR () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RHR);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set RHRA.
		@param RHRA RHRA	  */
	public void setRHRA (BigDecimal RHRA)
	{
		set_Value (COLUMNNAME_RHRA, RHRA);
	}

	/** Get RHRA.
		@return RHRA	  */
	public BigDecimal getRHRA () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_RHRA);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set ROCKCOST.
		@param ROCKCOST ROCKCOST	  */
	public void setROCKCOST (BigDecimal ROCKCOST)
	{
		set_Value (COLUMNNAME_ROCKCOST, ROCKCOST);
	}

	/** Get ROCKCOST.
		@return ROCKCOST	  */
	public BigDecimal getROCKCOST () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ROCKCOST);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SDDD.
		@param SDDD SDDD	  */
	public void setSDDD (String SDDD)
	{
		set_Value (COLUMNNAME_SDDD, SDDD);
	}

	/** Get SDDD.
		@return SDDD	  */
	public String getSDDD () 
	{
		return (String)get_Value(COLUMNNAME_SDDD);
	}

	/** Set SHOP.
		@param SHOP SHOP	  */
	public void setSHOP (BigDecimal SHOP)
	{
		set_Value (COLUMNNAME_SHOP, SHOP);
	}

	/** Get SHOP.
		@return SHOP	  */
	public BigDecimal getSHOP () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SHOP);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SILENCER.
		@param SILENCER SILENCER	  */
	public void setSILENCER (BigDecimal SILENCER)
	{
		set_Value (COLUMNNAME_SILENCER, SILENCER);
	}

	/** Get SILENCER.
		@return SILENCER	  */
	public BigDecimal getSILENCER () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SILENCER);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set SL.
		@param SL SL	  */
	public void setSL (BigDecimal SL)
	{
		set_Value (COLUMNNAME_SL, SL);
	}

	/** Get SL.
		@return SL	  */
	public BigDecimal getSL () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SL);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set STANDARD.
		@param STANDARD STANDARD	  */
	public void setSTANDARD (String STANDARD)
	{
		set_Value (COLUMNNAME_STANDARD, STANDARD);
	}

	/** Get STANDARD.
		@return STANDARD	  */
	public String getSTANDARD () 
	{
		return (String)get_Value(COLUMNNAME_STANDARD);
	}

	/** Set STOPER.
		@param STOPER STOPER	  */
	public void setSTOPER (BigDecimal STOPER)
	{
		set_Value (COLUMNNAME_STOPER, STOPER);
	}

	/** Get STOPER.
		@return STOPER	  */
	public BigDecimal getSTOPER () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_STOPER);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set T.
		@param T T	  */
	public void setT (BigDecimal T)
	{
		set_Value (COLUMNNAME_T, T);
	}

	/** Get T.
		@return T	  */
	public BigDecimal getT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_T);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set THFRAME.
		@param THFRAME THFRAME	  */
	public void setTHFRAME (BigDecimal THFRAME)
	{
		set_Value (COLUMNNAME_THFRAME, THFRAME);
	}

	/** Get THFRAME.
		@return THFRAME	  */
	public BigDecimal getTHFRAME () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_THFRAME);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set THLEAF.
		@param THLEAF THLEAF	  */
	public void setTHLEAF (BigDecimal THLEAF)
	{
		set_Value (COLUMNNAME_THLEAF, THLEAF);
	}

	/** Get THLEAF.
		@return THLEAF	  */
	public BigDecimal getTHLEAF () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_THLEAF);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TONNAGE.
		@param TONNAGE TONNAGE	  */
	public void setTONNAGE (BigDecimal TONNAGE)
	{
		set_Value (COLUMNNAME_TONNAGE, TONNAGE);
	}

	/** Get TONNAGE.
		@return TONNAGE	  */
	public BigDecimal getTONNAGE () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TONNAGE);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TOTALFRAME.
		@param TOTALFRAME TOTALFRAME	  */
	public void setTOTALFRAME (BigDecimal TOTALFRAME)
	{
		set_Value (COLUMNNAME_TOTALFRAME, TOTALFRAME);
	}

	/** Get TOTALFRAME.
		@return TOTALFRAME	  */
	public BigDecimal getTOTALFRAME () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TOTALFRAME);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TOTALHARD.
		@param TOTALHARD TOTALHARD	  */
	public void setTOTALHARD (BigDecimal TOTALHARD)
	{
		set_Value (COLUMNNAME_TOTALHARD, TOTALHARD);
	}

	/** Get TOTALHARD.
		@return TOTALHARD	  */
	public BigDecimal getTOTALHARD () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TOTALHARD);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set TOTALLEAF.
		@param TOTALLEAF TOTALLEAF	  */
	public void setTOTALLEAF (BigDecimal TOTALLEAF)
	{
		set_Value (COLUMNNAME_TOTALLEAF, TOTALLEAF);
	}

	/** Get TOTALLEAF.
		@return TOTALLEAF	  */
	public BigDecimal getTOTALLEAF () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TOTALLEAF);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UPFRAME.
		@param UPFRAME UPFRAME	  */
	public void setUPFRAME (BigDecimal UPFRAME)
	{
		set_Value (COLUMNNAME_UPFRAME, UPFRAME);
	}

	/** Get UPFRAME.
		@return UPFRAME	  */
	public BigDecimal getUPFRAME () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UPFRAME);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set UPLEAF.
		@param UPLEAF UPLEAF	  */
	public void setUPLEAF (BigDecimal UPLEAF)
	{
		set_Value (COLUMNNAME_UPLEAF, UPLEAF);
	}

	/** Get UPLEAF.
		@return UPLEAF	  */
	public BigDecimal getUPLEAF () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_UPLEAF);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set VP.
		@param VP VP	  */
	public void setVP (BigDecimal VP)
	{
		set_Value (COLUMNNAME_VP, VP);
	}

	/** Get VP.
		@return VP	  */
	public BigDecimal getVP () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_VP);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set VPCOST.
		@param VPCOST VPCOST	  */
	public void setVPCOST (BigDecimal VPCOST)
	{
		set_Value (COLUMNNAME_VPCOST, VPCOST);
	}

	/** Get VPCOST.
		@return VPCOST	  */
	public BigDecimal getVPCOST () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_VPCOST);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set WIDTH.
		@param WIDTH WIDTH	  */
	public void setWIDTH (BigDecimal WIDTH)
	{
		set_Value (COLUMNNAME_WIDTH, WIDTH);
	}

	/** Get WIDTH.
		@return WIDTH	  */
	public BigDecimal getWIDTH () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_WIDTH);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}