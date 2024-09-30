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

/** Generated Model for C_QUOTATIONLINE
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS - $Id$ */
public class X_C_QUOTATIONLINE extends PO implements I_C_QUOTATIONLINE, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110125L;

    /** Standard Constructor */
    public X_C_QUOTATIONLINE (Properties ctx, int C_QUOTATIONLINE_ID, String trxName)
    {
      super (ctx, C_QUOTATIONLINE_ID, trxName);
      /** if (C_QUOTATIONLINE_ID == 0)
        {
			setC_QUOTATIONLINE_ID (0);
			setC_QUOTATION_ID (0);
			setLine (0);
// @SQL=SELECT NVL(MAX(Line),0)+10 AS DefaultValue FROM C_QuotationLine WHERE C_Quotation_ID=@C_QUOTATION_ID@
        } */
    }

    /** Load Constructor */
    public X_C_QUOTATIONLINE (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_QUOTATIONLINE[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set A.
		@param A A	  */
	public void setA (BigDecimal A)
	{
		set_Value (COLUMNNAME_A, A);
	}

	/** Get A.
		@return A	  */
	public BigDecimal getA () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	// Set Slot No **Added on 03Mar2014.
	public void setSLOTNO (BigDecimal SLOTNO)
	{
		set_Value (COLUMNNAME_SLOTNO, SLOTNO);
	}

	/** Set Slot No **Added on 03Mar2014
		@return Slot No  */
	public BigDecimal getSLOTNO () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SLOTNO);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
	//End for Slot No **Added on 03Mar2014
	
	// Set Act Qty **Added on 04Jun2014.
	public void setACTQTY (BigDecimal ACTQTY)
	{
		set_Value (COLUMNNAME_ACTQTY, ACTQTY);
	}

	/** Set Act Qty **Added on 04Jun2014
		@return Act Qty  */
	public BigDecimal getACTQTY () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ACTQTY);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
	//End for Act Qty **Added on 04Jun2014
	
	// Set Neck Dia **Added on 03Apr2014.
	public void setNECKDIA (BigDecimal NECKDIA)
	{
		set_Value (COLUMNNAME_NECKDIA, NECKDIA);
	}

	/** Set Neck Dia **Added on 03Apr2014
		@return Neck Dia  */
	public BigDecimal getNECKDIA () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NECKDIA);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
	//End for Neck Dia **Added on 03Apr2014
	
	// Set Neck A **Added on 07Apr2014.
	public void setGNA (BigDecimal GNA)
	{
		set_Value (COLUMNNAME_GNA, GNA);
	}

	/** Set Neck A **Added on 07Apr2014
		@return Neck A  */
	public BigDecimal getGNA () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GNA);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
	//End for Neck A **Added on 07Apr2014
	
	// Set Neck B **Added on 07Apr2014.
	public void setGNB (BigDecimal GNB)
	{
		set_Value (COLUMNNAME_GNB, GNB);
	}

	/** Set Neck B **Added on 07Apr2014
		@return Neck B  */
	public BigDecimal getGNB () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GNB);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
	//End for Neck B **Added on 07Apr2014

	/** Column name FDC_RN_YN */  //Added 21.02.2023
	public void setFDC_RN_YN (boolean FDC_RN_YN)
	{
		set_Value (COLUMNNAME_FDC_RN_YN, Boolean.valueOf(FDC_RN_YN));
	}
	
	
	public boolean isFDC_RN_YN () 
	{
		Object oo = get_Value(COLUMNNAME_FDC_RN_YN);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
	//End of FDC_RN_YN
	
	//Column Name FDC_RN_SIZE	Added on 21.02.2023
	public void setFDC_RN_SIZE (BigDecimal FDC_RN_SIZE)
	{
		set_Value (COLUMNNAME_FDC_RN_SIZE, FDC_RN_SIZE);
	}

	/** Get FDC_RN_SIZE.
		@return FDC_RN_SIZE	  */
	public BigDecimal getFDC_RN_SIZE() 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_FDC_RN_SIZE);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
	
	
	/** Set LENGTH.
	@param LENGTH	  */
	public void setLENGTH (BigDecimal LENGTH)
	{
		set_Value (COLUMNNAME_LENGTH, LENGTH);
	}
	/** Get LENGTH.
	@return LENGTH	  */
	public BigDecimal getLENGTH () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_LENGTH);
		if (bd == null)
			return Env.ZERO;
		return bd;
	}
	
	//Added for Actuator,Thermostat,Transformer  @25.12.2013	
	/** Set ACTUATOR ID.
	@param ACTUATOR ID	  */
	public void setBeta_Qtn_Actuator_ID (int Beta_Qtn_Actuator_ID)
	{
		set_Value (COLUMNNAME_Beta_Qtn_Actuator_ID, Beta_Qtn_Actuator_ID);
	}
	/** Get ACTUATOR ID..
	@return ACTUATOR ID.	  */
	public int getBeta_Qtn_Actuator_ID () 
	{
		Integer i1 = (Integer)get_Value(COLUMNNAME_Beta_Qtn_Actuator_ID);
		if (i1 == null)
			return 0;
		
		return i1.intValue();
		
	}
		
	/** Set THERMOSTAT ID.
	@param THERMOSTAT ID	  */
	public void setBETA_QTN_THERMOSTAT_ID (int BETA_QTN_THERMOSTAT_ID)
	{
		set_Value (COLUMNNAME_BETA_QTN_THERMOSTAT_ID, BETA_QTN_THERMOSTAT_ID);
	}
	/** Get THERMOSTAT ID..
	@return THERMOSTAT ID.	  */
	public int getBETA_QTN_THERMOSTAT_ID () 
	{
		Integer i1 = (Integer)get_Value(COLUMNNAME_BETA_QTN_THERMOSTAT_ID);
		if (i1 == null)
			return 0;
		
		return i1.intValue();
	}
	
	
	/** Set TRANSFORMER ID.
	@param TRANSFORMER ID	  */
	public void setBETA_QTN_TRANSFORMER_ID (int BETA_QTN_TRANSFORMER_ID)
	{
		set_Value (COLUMNNAME_BETA_QTN_TRANSFORMER_ID, BETA_QTN_TRANSFORMER_ID);
	}
	/** Get TRANSFORMER ID..
	@return TRANSFORMER ID.	  */
	public int getBETA_QTN_TRANSFORMER_ID () 
	{
		Integer i1 = (Integer)get_Value(COLUMNNAME_BETA_QTN_TRANSFORMER_ID);
		if (i1 == null)
			return 0;
		
		return i1.intValue();
	}
	
	/** Set Heater ID.
	@param Heater ID	  */
	public void setBeta_Qtn_Heater_ID (int Beta_Qtn_Heater_ID)
	{
		set_Value (COLUMNNAME_Beta_Qtn_Heater_ID, Beta_Qtn_Heater_ID);
	}
	/** Get Heater ID..
	@return Heater ID.	  */
	public int getBeta_Qtn_Heater_ID () 
	{
		Integer i1 = (Integer)get_Value(COLUMNNAME_Beta_Qtn_Heater_ID);
		if (i1 == null)
			return 0;
		
		return i1.intValue();
		
	}
	
	//END Added for Actuator,Thermostat,Transformer,Heater  @25.12.2013

	
	
	
	/** Set Create Attributes.
		@param ADDATTR Create Attributes	  */
	public void setADDATTR (String ADDATTR)
	{
		set_Value (COLUMNNAME_ADDATTR, ADDATTR);
	}

	/** Get Create Attributes.
		@return Create Attributes	  */
	public String getADDATTR () 
	{
		return (String)get_Value(COLUMNNAME_ADDATTR);
	}

	/** Set ADFCODE.
		@param ADFCODE ADFCODE	  */
	public void setADFCODE (String ADFCODE)
	{
		set_Value (COLUMNNAME_ADFCODE, ADFCODE);
	}

	/** Get ADFCODE.
		@return ADFCODE	  */
	public String getADFCODE () 
	{
		return (String)get_Value(COLUMNNAME_ADFCODE);
	}
	
	/** Set BUSHBEAR.
	@param BUSHBEAR BUSHBEAR	  */
	public void setBUSHBEAR (String BUSHBEAR)
	{
		set_Value (COLUMNNAME_BUSHBEAR, BUSHBEAR);
	}
	
	/** Get BUSHBEAR.
		@return BUSHBEAR	  */
	public String getBUSHBEAR () 
	{
		return (String)get_Value(COLUMNNAME_BUSHBEAR);
	}
	
	/** Set MECHANISM.
	@param MECHANISM MECHANISM	  */
	public void setMECHANISM (String MECHANISM)
	{
		set_Value (COLUMNNAME_MECHANISM, MECHANISM);
	}
	
	/** Get MECHANISM.
		@return MECHANISM	  */
	public String getMECHANISM () 
	{
		return (String)get_Value(COLUMNNAME_MECHANISM);
	}
	
	/** Set METALTHICK.
	@param METALTHICK METALTHICK	  */
	public void setMETALTHICK (String METALTHICK)
	{
		set_Value (COLUMNNAME_METALTHICK, METALTHICK);
	}
	
	/** Get METALTHICK.
		@return METALTHICK	  */
	public String getMETALTHICK () 
	{
		return (String)get_Value(COLUMNNAME_METALTHICK);
	}
	
	//Added for FAN CASING
	
	//CA_MODEL
	public void setCA_MODEL (String CA_MODEL)
	{
		set_Value (COLUMNNAME_CA_MODEL, CA_MODEL);
	}
	public String getCA_MODEL () 
	{
		return (String)get_Value(COLUMNNAME_CA_MODEL);
	}
	
	//CA_TYPE
	public void setCA_TYPE (String CA_TYPE)
	{
		set_Value (COLUMNNAME_CA_TYPE, CA_TYPE);
	}
	public String getCA_TYPE () 
	{
		return (String)get_Value(COLUMNNAME_CA_TYPE);
	}
	
	//CA_BD
	public void setCA_BD (String CA_BD)
	{
		set_Value (COLUMNNAME_CA_BD, CA_BD);
	}
	public String getCA_BD () 
	{
		return (String)get_Value(COLUMNNAME_CA_BD);
	}
	
	//End of Added for FAN CASING
	
	//STADYN
	public void setSTADYN (String STADYN)
	{
		set_Value (COLUMNNAME_STADYN, STADYN);
	}
	public String getSTADYN () 
	{
		return (String)get_Value(COLUMNNAME_STADYN);
	}
	
	
	
	/** Set DRIVE.  **Added on 29.09.2015
	@param DRIVE DRIVE	  */
	public void setDRIVE (String DRIVE)
	{
		set_Value (COLUMNNAME_DRIVE, DRIVE);
	}
	
	/** Get DRIVE.
		@return DRIVE	  */
	public String getDRIVE () 
	{
		return (String)get_Value(COLUMNNAME_DRIVE);
	}
	
	/** Set ULClass.  **Added on 25.09.2016
	@param UL Class	  */
	public void setULCLASS (String ULCLASS)
	{
		set_Value (COLUMNNAME_ULCLASS, ULCLASS);
	}
	
	/** Get ULCLASS.
		@return ULCLASS	  */
	public String getULCLASS () 
	{
		return (String)get_Value(COLUMNNAME_ULCLASS);
	}
	
	//Act Brand & UL Class Type for BMSD-NEW & BMFSD-NEW  10.03.2020
	public void setUL_CLASSTYPE (String UL_CLASSTYPE)
	{
		set_Value (COLUMNNAME_UL_CLASSTYPE, UL_CLASSTYPE);
	}
	
	public String getUL_CLASSTYPE () 
	{
		return (String)get_Value(COLUMNNAME_UL_CLASSTYPE);
	}
	
	public void setUL_ACT (String UL_ACT)
	{
		set_Value (COLUMNNAME_UL_ACT, UL_ACT);
	}
	
	public String getUL_ACT () 
	{
		return (String)get_Value(COLUMNNAME_UL_ACT);
	}
	
	public void setUL_INTEXTACT (String UL_INTEXTACT)
	{
		set_Value (COLUMNNAME_UL_INTEXTACT, UL_INTEXTACT);
	}
	
	public String getUL_INTEXTACT () 
	{
		return (String)get_Value(COLUMNNAME_UL_INTEXTACT);
	}


	public void setUL_TEMP (String UL_TEMP)
	{
		set_Value (COLUMNNAME_UL_TEMP, UL_TEMP);
	}
	
	public String getUL_TEMP () 
	{
		return (String)get_Value(COLUMNNAME_UL_TEMP);
	}
	
	/** Column name VCD_NONSTD_YN */  //Added 24.02.2020
	public void setVCD_NONSTD_YN (boolean VCD_NONSTD_YN)
	{
		set_Value (COLUMNNAME_VCD_NONSTD_YN, Boolean.valueOf(VCD_NONSTD_YN));
	}
	
	
	public boolean isVCD_NONSTD_YN () 
	{
		Object oo = get_Value(COLUMNNAME_VCD_NONSTD_YN);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
	//////////////////////////////////
	
	/** Column name VCD_BLADE */  //Added 24.02.2020
	public void setVCD_BLADE (String VCD_BLADE)
	{
		set_Value (COLUMNNAME_VCD_BLADE, VCD_BLADE);
	}
	
	public String getVCD_BLADE () 
	{
		return (String)get_Value(COLUMNNAME_VCD_BLADE);
	}
	//////////////////////////////////////////////
	
	
	public void setUL_SPIGOT (boolean UL_SPIGOT)
	{
		set_Value (COLUMNNAME_UL_SPIGOT, Boolean.valueOf(UL_SPIGOT));
	}
	
	public boolean isUL_SPIGOT () 
	{
		Object oo = get_Value(COLUMNNAME_UL_SPIGOT);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
	//End of Act Brand & UL Class Type for BMSD-NEW & BMFSD-NEW
	
	/** Set SETULCLASS  //Added 25.09.2016
	@param SETULCLASS SETULCLASS	  */
	public void setSETULCLASS (boolean SETULCLASS)
	{
		set_Value (COLUMNNAME_SETULCLASS, Boolean.valueOf(SETULCLASS));
	}
	
	/** Get SETULCLASS.
		@return SETULCLASS	  */
	public boolean isSETULCLASS () 
	{
		Object oo = get_Value(COLUMNNAME_SETULCLASS);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
	
	/** Set SETVAVYN  //Added 14.02.2019
	@param SETVAVYN SETVAVYN	  */
	public void setSETVAVYN (boolean SETVAVYN)
	{
		set_Value (COLUMNNAME_SETVAVYN, Boolean.valueOf(SETVAVYN));
	}

	/** Get SETVAVYN.
		@return SETVAVYN	  */
	public boolean isSETVAVYN () 
	{
		Object oo = get_Value(COLUMNNAME_SETVAVYN);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set SETGASKET  //Added 04.11.2021
	@param SETGASKET SETGASKET	  */
	public void setSETGASKET (boolean SETGASKET)
	{
		set_Value (COLUMNNAME_SETGASKET, Boolean.valueOf(SETGASKET));
	}

	/** Get SETGASKET.
		@return SETGASKET	  */
	public boolean isSETGASKET () 
	{
		Object oo = get_Value(COLUMNNAME_SETGASKET);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
	//End for SETGASKET

	
	/** Set ALUFILTERYN.
		@param ALUFILTERYN ALUFILTERYN	  */
	public void setALUFILTERYN (boolean ALUFILTERYN)
	{
		set_Value (COLUMNNAME_ALUFILTERYN, Boolean.valueOf(ALUFILTERYN));
	}

	/** Get ALUFILTERYN.
		@return ALUFILTERYN	  */
	public boolean isALUFILTERYN () 
	{
		Object oo = get_Value(COLUMNNAME_ALUFILTERYN);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set TBARYN. //Added on 23.02.2014
	@param TBARYN TBARYN	  */
	public void setTBARYN (boolean TBARYN)
	{
		set_Value (COLUMNNAME_TBARYN, Boolean.valueOf(TBARYN));
	}
	
	/** Get TBARYN.
		@return TBARYN	  */
	public boolean isTBARYN () 
	{
		Object oo = get_Value(COLUMNNAME_TBARYN);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
	
	/** Set LVBYAREAYN. //Added on 09.04.2014
	@param LVBYAREAYN LVBYAREAYN	  */
	public void setLVBYAREAYN (boolean LVBYAREAYN)
	{
		set_Value (COLUMNNAME_LVBYAREAYN, Boolean.valueOf(LVBYAREAYN));
	}
	
	/** Get LVBYAREAYN.
		@return LVBYAREAYN	  */
	public boolean isLVBYAREAYN () 
	{
		Object oo = get_Value(COLUMNNAME_LVBYAREAYN);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
	
	/** Set B.
		@param B B	  */
	public void setB (BigDecimal B)
	{
		set_Value (COLUMNNAME_B, B);
	}

	/** Get B.
		@return B	  */
	public BigDecimal getB () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_B);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set CURVEYN.
		@param CURVEYN CURVEYN	  */
	public void setCURVEYN (boolean CURVEYN)
	{
		set_Value (COLUMNNAME_CURVEYN, Boolean.valueOf(CURVEYN));
	}

	/** Get CURVEYN.
		@return CURVEYN	  */
	public boolean isCURVEYN () 
	{
		Object oo = get_Value(COLUMNNAME_CURVEYN);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_C_Charge getC_Charge() throws RuntimeException
    {
		return (I_C_Charge)MTable.get(getCtx(), I_C_Charge.Table_Name)
			.getPO(getC_Charge_ID(), get_TrxName());	}

	/** Set Charge.
		@param C_Charge_ID 
		Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID)
	{
		if (C_Charge_ID < 1) 
			set_Value (COLUMNNAME_C_Charge_ID, null);
		else 
			set_Value (COLUMNNAME_C_Charge_ID, Integer.valueOf(C_Charge_ID));
	}

	/** Get Charge.
		@return Additional document charges
	  */
	public int getC_Charge_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Charge_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

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

	public I_C_QUOTATION getC_QUOTATION() throws RuntimeException
    {
		return (I_C_QUOTATION)MTable.get(getCtx(), I_C_QUOTATION.Table_Name)
			.getPO(getC_QUOTATION_ID(), get_TrxName());	}

	/** Set Quotation.
		@param C_QUOTATION_ID Quotation	  */
	public void setC_QUOTATION_ID (int C_QUOTATION_ID)
	{
		if (C_QUOTATION_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_QUOTATION_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_QUOTATION_ID, Integer.valueOf(C_QUOTATION_ID));
	}

	/** Get Quotation.
		@return Quotation	  */
	public int getC_QUOTATION_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_QUOTATION_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Damper Y/N.
		@param DAMPERYN Damper Y/N	  */
	public void setDAMPERYN (boolean DAMPERYN)
	{
		set_Value (COLUMNNAME_DAMPERYN, Boolean.valueOf(DAMPERYN));
	}

	/** Get Damper Y/N.
		@return Damper Y/N	  */
	public boolean isDAMPERYN () 
	{
		Object oo = get_Value(COLUMNNAME_DAMPERYN);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** DEFLECTION AD_Reference_ID=1000017 */
	public static final int DEFLECTION_AD_Reference_ID=1000017;
	/** Double Deflection = D */
	public static final String DEFLECTION_DoubleDeflection = "D";
	/** Single Deflection = S */
	public static final String DEFLECTION_SingleDeflection = "S";
	/** Set DEFLECTION.
		@param DEFLECTION DEFLECTION	  */
	public void setDEFLECTION (String DEFLECTION)
	{

		set_Value (COLUMNNAME_DEFLECTION, DEFLECTION);
	}

	/** Get DEFLECTION.
		@return DEFLECTION	  */
	public String getDEFLECTION () 
	{
		return (String)get_Value(COLUMNNAME_DEFLECTION);
	}

	/** Set Discount %.
		@param DISC_PCNT Discount %	  */
	public void setDISC_PCNT (BigDecimal DISC_PCNT)
	{
		set_Value (COLUMNNAME_DISC_PCNT, DISC_PCNT);
	}

	/** Get Discount %.
		@return Discount %	  */
	public BigDecimal getDISC_PCNT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DISC_PCNT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set LH.
		@param DOORLH LH	  */
	public void setDOORLH (int DOORLH)
	{
		set_Value (COLUMNNAME_DOORLH, Integer.valueOf(DOORLH));
	}

	/** Get LH.
		@return LH	  */
	public int getDOORLH () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DOORLH);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set LHA.
		@param DOORLHA LHA	  */
	public void setDOORLHA (int DOORLHA)
	{
		set_Value (COLUMNNAME_DOORLHA, Integer.valueOf(DOORLHA));
	}

	/** Get LHA.
		@return LHA	  */
	public int getDOORLHA () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DOORLHA);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set LHR.
		@param DOORLHR LHR	  */
	public void setDOORLHR (int DOORLHR)
	{
		set_Value (COLUMNNAME_DOORLHR, Integer.valueOf(DOORLHR));
	}

	/** Get LHR.
		@return LHR	  */
	public int getDOORLHR () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DOORLHR);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set LHRA.
		@param DOORLHRA LHRA	  */
	public void setDOORLHRA (int DOORLHRA)
	{
		set_Value (COLUMNNAME_DOORLHRA, Integer.valueOf(DOORLHRA));
	}

	/** Get LHRA.
		@return LHRA	  */
	public int getDOORLHRA () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DOORLHRA);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Location.
		@param DOORLOCATION Location	  */
	public void setDOORLOCATION (String DOORLOCATION)
	{
		set_Value (COLUMNNAME_DOORLOCATION, DOORLOCATION);
	}

	/** Get Location.
		@return Location	  */
	public String getDOORLOCATION () 
	{
		return (String)get_Value(COLUMNNAME_DOORLOCATION);
	}

	/** Set RH.
		@param DOORRH RH	  */
	public void setDOORRH (int DOORRH)
	{
		set_Value (COLUMNNAME_DOORRH, Integer.valueOf(DOORRH));
	}

	/** Get RH.
		@return RH	  */
	public int getDOORRH () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DOORRH);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RHA.
		@param DOORRHA RHA	  */
	public void setDOORRHA (int DOORRHA)
	{
		set_Value (COLUMNNAME_DOORRHA, Integer.valueOf(DOORRHA));
	}

	/** Get RHA.
		@return RHA	  */
	public int getDOORRHA () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DOORRHA);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RHR.
		@param DOORRHR RHR	  */
	public void setDOORRHR (int DOORRHR)
	{
		set_Value (COLUMNNAME_DOORRHR, Integer.valueOf(DOORRHR));
	}

	/** Get RHR.
		@return RHR	  */
	public int getDOORRHR () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DOORRHR);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set RHRA.
		@param DOORRHRA RHRA	  */
	public void setDOORRHRA (int DOORRHRA)
	{
		set_Value (COLUMNNAME_DOORRHRA, Integer.valueOf(DOORRHRA));
	}

	/** Get RHRA.
		@return RHRA	  */
	public int getDOORRHRA () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DOORRHRA);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** DOORTYPE AD_Reference_ID=1000022 */
	public static final int DOORTYPE_AD_Reference_ID=1000022;
	/** STAIR = S */
	public static final String DOORTYPE_STAIR = "S";
	/** STAIR TRANSOM = T */
	public static final String DOORTYPE_STAIRTRANSOM = "T";
	/** FRAME = F */
	public static final String DOORTYPE_FRAME = "F";
	/** FIRE RATED DOOR = R */
	public static final String DOORTYPE_FIRERATEDDOOR = "R";
	/** DR3 = D */
	public static final String DOORTYPE_DR3 = "D";
	/** OSUS1 = O */
	public static final String DOORTYPE_OSUS1 = "O";
	/** OSUS2 = U */
	public static final String DOORTYPE_OSUS2 = "U";
	/** Set Door Type.
		@param DOORTYPE Door Type	  */
	public void setDOORTYPE (String DOORTYPE)
	{

		set_Value (COLUMNNAME_DOORTYPE, DOORTYPE);
	}

	/** Get Door Type.
		@return Door Type	  */
	public String getDOORTYPE () 
	{
		return (String)get_Value(COLUMNNAME_DOORTYPE);
	}

	/** Set EQUGRIDYN.
		@param EQUGRIDYN EQUGRIDYN	  */
	public void setEQUGRIDYN (boolean EQUGRIDYN)
	{
		set_Value (COLUMNNAME_EQUGRIDYN, Boolean.valueOf(EQUGRIDYN));
	}

	/** Get EQUGRIDYN.
		@return EQUGRIDYN	  */
	public boolean isEQUGRIDYN () 
	{
		Object oo = get_Value(COLUMNNAME_EQUGRIDYN);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set FINISH.
		@param FINISH FINISH	  */
	public void setFINISH (String FINISH)
	{
		set_Value (COLUMNNAME_FINISH, FINISH);
	}

	/** Get FINISH.
		@return FINISH	  */
	public String getFINISH () 
	{
		return (String)get_Value(COLUMNNAME_FINISH);
	}

	/** Set GETPRICE.
		@param GETPRICE GETPRICE	  */
	public void setGETPRICE (String GETPRICE)
	{
		set_Value (COLUMNNAME_GETPRICE, GETPRICE);
	}

	/** Get GETPRICE.
		@return GETPRICE	  */
	public String getGETPRICE () 
	{
		return (String)get_Value(COLUMNNAME_GETPRICE);
	}

	/** HORVER AD_Reference_ID=1000012 */
	public static final int HORVER_AD_Reference_ID=1000012;
	/** HORIZONTAL = H */
	public static final String HORVER_HORIZONTAL = "H";
	/** VERTICAL = V */
	public static final String HORVER_VERTICAL = "V";
	/** Set HORVER.
		@param HORVER HORVER	  */
	public void setHORVER (String HORVER)
	{

		set_Value (COLUMNNAME_HORVER, HORVER);
	}

	/** Get HORVER.
		@return HORVER	  */
	public String getHORVER () 
	{
		return (String)get_Value(COLUMNNAME_HORVER);
	}

	/** Set INSSCRNYN.
		@param INSSCRNYN INSSCRNYN	  */
	public void setINSSCRNYN (boolean INSSCRNYN)
	{
		set_Value (COLUMNNAME_INSSCRNYN, Boolean.valueOf(INSSCRNYN));
	}

	/** Get INSSCRNYN.
		@return INSSCRNYN	  */
	public boolean isINSSCRNYN () 
	{
		Object oo = get_Value(COLUMNNAME_INSSCRNYN);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Bill of Materials.
		@param IsBOM 
		Bill of Materials
	  */
	public void setIsBOM (boolean IsBOM)
	{
		set_Value (COLUMNNAME_IsBOM, Boolean.valueOf(IsBOM));
	}

	/** Get Bill of Materials.
		@return Bill of Materials
	  */
	public boolean isBOM () 
	{
		Object oo = get_Value(COLUMNNAME_IsBOM);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getLine()));
    }

	/** MANTYPE AD_Reference_ID=1000018 */
	public static final int MANTYPE_AD_Reference_ID=1000018;
	/** BPC = B */
	public static final String MANTYPE_BPC = "B";
	/** MF = M */
	public static final String MANTYPE_MF = "M";
	/** Set Manufacture Type.
		@param MANTYPE Manufacture Type	  */
	public void setMANTYPE (String MANTYPE)
	{

		set_Value (COLUMNNAME_MANTYPE, MANTYPE);
	}

	/** Get Manufacture Type.
		@return Manufacture Type	  */
	public String getMANTYPE () 
	{
		return (String)get_Value(COLUMNNAME_MANTYPE);
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

	/** Set NETPRICE.
		@param NETPRICE NETPRICE	  */
	public void setNETPRICE (BigDecimal NETPRICE)
	{
		set_Value (COLUMNNAME_NETPRICE, NETPRICE);
	}

	/** Get NETPRICE.
		@return NETPRICE	  */
	public BigDecimal getNETPRICE () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NETPRICE);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set PRODDESC.
		@param PRODDESC PRODDESC	  */
	public void setPRODDESC (String PRODDESC)
	{
		set_Value (COLUMNNAME_PRODDESC, PRODDESC);
	}

	/** Get PRODDESC.
		@return PRODDESC	  */
	public String getPRODDESC () 
	{
		return (String)get_Value(COLUMNNAME_PRODDESC);
	}

	/** Set Price.
		@param Price 
		Price
	  */
	public void setPrice (BigDecimal Price)
	{
		set_Value (COLUMNNAME_Price, Price);
	}

	/** Get Price.
		@return Price
	  */
	public BigDecimal getPrice () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Price);
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

	/** Set REMARKS.
		@param REMARKS REMARKS	  */
	public void setREMARKS (String REMARKS)
	{
		set_Value (COLUMNNAME_REMARKS, REMARKS);
	}

	/** Get REMARKS.
		@return REMARKS	  */
	public String getREMARKS () 
	{
		return (String)get_Value(COLUMNNAME_REMARKS);
	}

	/** Set SUPPLY.
		@param SUPPLY SUPPLY	  */
	public void setSUPPLY (BigDecimal SUPPLY)
	{
		set_Value (COLUMNNAME_SUPPLY, SUPPLY);
	}

	/** Get SUPPLY.
		@return SUPPLY	  */
	public BigDecimal getSUPPLY () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_SUPPLY);
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

	/** Set Type.
		@param Type 
		Type of Validation (SQL, Java Script, Java Language)
	  */
	public void setType (String Type)
	{
		set_Value (COLUMNNAME_Type, Type);
	}

	/** Get Type.
		@return Type of Validation (SQL, Java Script, Java Language)
	  */
	public String getType () 
	{
		return (String)get_Value(COLUMNNAME_Type);
	}

	/** Set ISOD.
	@param ISOD 
	Type of Validation (SQL, Java Script, Java Language)
  */
	public void setISOD (boolean ISOD)
	{
		set_Value (COLUMNNAME_ISOD, Boolean.valueOf(ISOD));
	}

	/** Get ISOD.
	@return ISOD
	*/

	public boolean ISOD () 
	{
		Object oo = get_Value(COLUMNNAME_ISOD);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
				return "Y".equals(oo);
		}
		return false;
	}
	
	
	/** UNIT AD_Reference_ID=1000016 */
	public static final int UNIT_AD_Reference_ID=1000016;
	/** INCH = I */
	public static final String UNIT_INCH = "I";
	/** MM = M */
	public static final String UNIT_MM = "M";
	/** FEET = F */
	public static final String UNIT_FEET = "F";
	/** METER = N */
	public static final String UNIT_METER = "N";
	/** Set UNIT.
		@param UNIT UNIT	  */
	public void setUNIT (String UNIT)
	{

		set_Value (COLUMNNAME_UNIT, UNIT);
	}

	/** Get UNIT.
		@return UNIT	  */
	public String getUNIT () 
	{
		return (String)get_Value(COLUMNNAME_UNIT);
	}
	
	/** BUNIT AD_Reference_ID=1000040 */
	public static final int BUNIT_AD_Reference_ID=1000038;
	/** INCH = I */
	public static final String BUNIT_INCH = "I";
	/** MM = M */
	public static final String BUNIT_MM = "M";
	
	/** Set BUNIT.	*/
	public void setBUNIT (String BUNIT)
	{

		set_Value (COLUMNNAME_BUNIT, BUNIT);
	}

	/** Get BUNIT.	*/
	public String getBUNIT () 
	{
		return (String)get_Value(COLUMNNAME_BUNIT);
	}
	
	/** Set SETBUNIT.  */
	public void setSETBUNIT (boolean SETBUNIT)
	{
		set_Value (COLUMNNAME_SETBUNIT, Boolean.valueOf(SETBUNIT));
	}

	/** Get SETBUNIT    */
	public boolean getSETBUNIT () 
	{
		Object oo = get_Value(COLUMNNAME_SETBUNIT);
		if (oo != null) 
		{
			if (oo instanceof Boolean) 
				return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}


	/** WAYS AD_Reference_ID=1000019 */
	public static final int WAYS_AD_Reference_ID=1000019;
	/** 1 Way = 1 */
	public static final String WAYS_1Way = "1";
	/** 2 Way = 2 */
	public static final String WAYS_2Way = "2";
	/** 3 Way = 3 */
	public static final String WAYS_3Way = "3";
	/** 4 Way = 4 */
	public static final String WAYS_4Way = "4";
	/** Set WAYS.
		@param WAYS WAYS	  */
	public void setWAYS (String WAYS)
	{

		set_Value (COLUMNNAME_WAYS, WAYS);
	}

	/** Get WAYS.
		@return WAYS	  */
	public String getWAYS () 
	{
		return (String)get_Value(COLUMNNAME_WAYS);
	}
	
	//AIRWAY
	/** AIRWAY AD_Reference_ID=1000053 */
	public static final int AIRWAY_AD_Reference_ID=1000053;
	/** A AIRWAY = A */
	public static final String AIRWAY_A = "A";
	/** B AIRWAY = B */
	public static final String AIRWAY_B = "B";
	/** C AIRWAY = C */
	public static final String AIRWAY_C = "C";
	/** D AIRWAY = D */
	public static final String AIRWAY_D = "D";
	/** E AIRWAY = E */
	public static final String AIRWAY_E = "E";
	/** F AIRWAY = F */
	public static final String AIRWAY_F = "F";
	/** Set AIRWAY.
		@param AIRWAY AIRWAY	  */
	public void setAIRWAY (String AIRWAY)
	{

		set_Value (COLUMNNAME_AIRWAY, AIRWAY);
	}

	/** Get AIRWAY.
		@return AIRWAY	  */
	public String getAIRWAY () 
	{
		return (String)get_Value(COLUMNNAME_AIRWAY);
	}
}