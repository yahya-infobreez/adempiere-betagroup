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
import org.compiere.util.KeyNamePair;

/** Generated Model for C_QUOTATION
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS - $Id$ */
public class X_C_QUOTATION extends PO implements I_C_QUOTATION, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110419L;

    /** Standard Constructor */
    public X_C_QUOTATION (Properties ctx, int C_QUOTATION_ID, String trxName)
    {
      super (ctx, C_QUOTATION_ID, trxName);
      /** if (C_QUOTATION_ID == 0)
        {
			setC_DocType_ID (0);
			setC_PaymentTerm_ID (0);
			setC_ProjectType_ID (0);
			setC_QUOTATION_ID (0);
			setDocAction (null);
// CO
			setDocStatus (null);
// DR
			setDocumentNo (null);
			setHWSUPPLY (null);
			setPosted (false);
// N
			setProcessed (false);
			setSalesRep_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_QUOTATION (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_QUOTATION[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set ATTENTION.
		@param ATTENTION ATTENTION	  */
	public void setATTENTION (String ATTENTION)
	{
		set_Value (COLUMNNAME_ATTENTION, ATTENTION);
	}

	/** Get ATTENTION.
		@return ATTENTION	  */
	public String getATTENTION () 
	{
		return (String)get_Value(COLUMNNAME_ATTENTION);
	}

	/** Set BTN_GEN_PROJECT.
		@param BTN_GEN_PROJECT BTN_GEN_PROJECT	  */
	public void setBTN_GEN_PROJECT (String BTN_GEN_PROJECT)
	{
		set_Value (COLUMNNAME_BTN_GEN_PROJECT, BTN_GEN_PROJECT);
	}

	/** Get BTN_GEN_PROJECT.
		@return BTN_GEN_PROJECT	  */
	public String getBTN_GEN_PROJECT () 
	{
		return (String)get_Value(COLUMNNAME_BTN_GEN_PROJECT);
	}

	/** Set BTN_RETRIEVECOST.
		@param BTN_RETRIEVECOST BTN_RETRIEVECOST	  */
	public void setBTN_RETRIEVECOST (String BTN_RETRIEVECOST)
	{
		set_Value (COLUMNNAME_BTN_RETRIEVECOST, BTN_RETRIEVECOST);
	}

	/** Get BTN_RETRIEVECOST.
		@return BTN_RETRIEVECOST	  */
	public String getBTN_RETRIEVECOST () 
	{
		return (String)get_Value(COLUMNNAME_BTN_RETRIEVECOST);
	}

	/** Set CREATEDOOR.
		@param CREATEDOOR CREATEDOOR	  */
	public void setCREATEDOOR (String CREATEDOOR)
	{
		set_Value (COLUMNNAME_CREATEDOOR, CREATEDOOR);
	}

	/** Get CREATEDOOR.
		@return CREATEDOOR	  */
	public String getCREATEDOOR () 
	{
		return (String)get_Value(COLUMNNAME_CREATEDOOR);
	}

	/** Set CUSTNAME.
		@param CUSTNAME CUSTNAME	  */
	public void setCUSTNAME (String CUSTNAME)
	{
		set_Value (COLUMNNAME_CUSTNAME, CUSTNAME);
	}

	/** Get CUSTNAME.
		@return CUSTNAME	  */
	public String getCUSTNAME () 
	{
		return (String)get_Value(COLUMNNAME_CUSTNAME);
	}

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_DocType getC_DocType() throws RuntimeException
    {
		return (I_C_DocType)MTable.get(getCtx(), I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else 
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Order.
		@param C_Order_ID 
		Order
	  */
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Order.
		@return Order
	  */
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException
    {
		return (I_C_PaymentTerm)MTable.get(getCtx(), I_C_PaymentTerm.Table_Name)
			.getPO(getC_PaymentTerm_ID(), get_TrxName());	}

	/** Set Payment Term.
		@param C_PaymentTerm_ID 
		The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, Integer.valueOf(C_PaymentTerm_ID));
	}

	/** Get Payment Term.
		@return The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ProjectType getC_ProjectType() throws RuntimeException
    {
		return (I_C_ProjectType)MTable.get(getCtx(), I_C_ProjectType.Table_Name)
			.getPO(getC_ProjectType_ID(), get_TrxName());	}

	/** Set Project Type.
		@param C_ProjectType_ID 
		Type of the project
	  */
	public void setC_ProjectType_ID (int C_ProjectType_ID)
	{
		if (C_ProjectType_ID < 1) 
			set_Value (COLUMNNAME_C_ProjectType_ID, null);
		else 
			set_Value (COLUMNNAME_C_ProjectType_ID, Integer.valueOf(C_ProjectType_ID));
	}

	/** Get Project Type.
		@return Type of the project
	  */
	public int getC_ProjectType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ProjectType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Project getC_Project() throws RuntimeException
    {
		return (I_C_Project)MTable.get(getCtx(), I_C_Project.Table_Name)
			.getPO(getC_Project_ID(), get_TrxName());	}

	/** Set Project.
		@param C_Project_ID 
		Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID)
	{
		if (C_Project_ID < 1) 
			set_Value (COLUMNNAME_C_Project_ID, null);
		else 
			set_Value (COLUMNNAME_C_Project_ID, Integer.valueOf(C_Project_ID));
	}

	/** Get Project.
		@return Financial Project
	  */
	public int getC_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Project_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

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

	/** DAMPERS AD_Reference_ID=1000010 */
	public static final int DAMPERS_AD_Reference_ID=1000010;
	/** BLACK = B */
	public static final String DAMPERS_BLACK = "B";
	/** MILFINISH = M */
	public static final String DAMPERS_MILFINISH = "M";
	/** Set DAMPERS.
		@param DAMPERS DAMPERS	  */
	public void setDAMPERS (String DAMPERS)
	{

		set_Value (COLUMNNAME_DAMPERS, DAMPERS);
	}

	/** Get DAMPERS.
		@return DAMPERS	  */
	public String getDAMPERS () 
	{
		return (String)get_Value(COLUMNNAME_DAMPERS);
	}

	/** Set DELIVERY.
		@param DELIVERY DELIVERY	  */
	public void setDELIVERY (String DELIVERY)
	{
		set_Value (COLUMNNAME_DELIVERY, DELIVERY);
	}

	/** Get DELIVERY.
		@return DELIVERY	  */
	public String getDELIVERY () 
	{
		return (String)get_Value(COLUMNNAME_DELIVERY);
	}

	/** Set DISCAMT.
		@param DISCAMT DISCAMT	  */
	public void setDISCAMT (BigDecimal DISCAMT)
	{
		set_Value (COLUMNNAME_DISCAMT, DISCAMT);
	}

	/** Get DISCAMT.
		@return DISCAMT	  */
	public BigDecimal getDISCAMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DISCAMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set DISCPER.
		@param DISCPER DISCPER	  */
	public void setDISCPER (BigDecimal DISCPER)
	{
		set_Value (COLUMNNAME_DISCPER, DISCPER);
	}

	/** Get DISCPER.
		@return DISCPER	  */
	public BigDecimal getDISCPER () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DISCPER);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Disc % Frame.
		@param DISCPERFR Disc % Frame	  */
	public void setDISCPERFR (BigDecimal DISCPERFR)
	{
		set_Value (COLUMNNAME_DISCPERFR, DISCPERFR);
	}

	/** Get Disc % Frame.
		@return Disc % Frame	  */
	public BigDecimal getDISCPERFR () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DISCPERFR);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Disc % Hardware.
		@param DISCPERHW Disc % Hardware	  */
	public void setDISCPERHW (BigDecimal DISCPERHW)
	{
		set_Value (COLUMNNAME_DISCPERHW, DISCPERHW);
	}

	/** Get Disc % Hardware.
		@return Disc % Hardware	  */
	public BigDecimal getDISCPERHW () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DISCPERHW);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Disc % Leaf.
		@param DISCPERLF Disc % Leaf	  */
	public void setDISCPERLF (BigDecimal DISCPERLF)
	{
		set_Value (COLUMNNAME_DISCPERLF, DISCPERLF);
	}

	/** Get Disc % Leaf.
		@return Disc % Leaf	  */
	public BigDecimal getDISCPERLF () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DISCPERLF);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Doc Date.
		@param DOCDATE Doc Date	  */
	public void setDOCDATE (Timestamp DOCDATE)
	{
		set_Value (COLUMNNAME_DOCDATE, DOCDATE);
	}

	/** Get Doc Date.
		@return Doc Date	  */
	public Timestamp getDOCDATE () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DOCDATE);
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

	/** DocAction AD_Reference_ID=135 */
	public static final int DOCACTION_AD_Reference_ID=135;
	/** Complete = CO */
	public static final String DOCACTION_Complete = "CO";
	/** Approve = AP */
	public static final String DOCACTION_Approve = "AP";
	/** Reject = RJ */
	public static final String DOCACTION_Reject = "RJ";
	/** Post = PO */
	public static final String DOCACTION_Post = "PO";
	/** Void = VO */
	public static final String DOCACTION_Void = "VO";
	/** Close = CL */
	public static final String DOCACTION_Close = "CL";
	/** Reverse - Correct = RC */
	public static final String DOCACTION_Reverse_Correct = "RC";
	/** Reverse - Accrual = RA */
	public static final String DOCACTION_Reverse_Accrual = "RA";
	/** Invalidate = IN */
	public static final String DOCACTION_Invalidate = "IN";
	/** Re-activate = RE */
	public static final String DOCACTION_Re_Activate = "RE";
	/** <None> = -- */
	public static final String DOCACTION_None = "--";
	/** Prepare = PR */
	public static final String DOCACTION_Prepare = "PR";
	/** Unlock = XL */
	public static final String DOCACTION_Unlock = "XL";
	/** Wait Complete = WC */
	public static final String DOCACTION_WaitComplete = "WC";
	/** Set Document Action.
		@param DocAction 
		The targeted status of the document
	  */
	public void setDocAction (String DocAction)
	{

		set_Value (COLUMNNAME_DocAction, DocAction);
	}

	/** Get Document Action.
		@return The targeted status of the document
	  */
	public String getDocAction () 
	{
		return (String)get_Value(COLUMNNAME_DocAction);
	}

	/** DocStatus AD_Reference_ID=131 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** Not Approved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** In Progress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** Waiting Payment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** Waiting Confirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	/** Set Document Status.
		@param DocStatus 
		The current status of the document
	  */
	public void setDocStatus (String DocStatus)
	{

		set_Value (COLUMNNAME_DocStatus, DocStatus);
	}

	/** Get Document Status.
		@return The current status of the document
	  */
	public String getDocStatus () 
	{
		return (String)get_Value(COLUMNNAME_DocStatus);
	}

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value(COLUMNNAME_DocumentNo);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getDocumentNo());
    }

	/** Set FINISHDESC1.
		@param FINISHDESC1 FINISHDESC1	  */
	public void setFINISHDESC1 (String FINISHDESC1)
	{
		set_Value (COLUMNNAME_FINISHDESC1, FINISHDESC1);
	}

	/** Get FINISHDESC1.
		@return FINISHDESC1	  */
	public String getFINISHDESC1 () 
	{
		return (String)get_Value(COLUMNNAME_FINISHDESC1);
	}

	/** Set FINISHDESC2.
		@param FINISHDESC2 FINISHDESC2	  */
	public void setFINISHDESC2 (String FINISHDESC2)
	{
		set_Value (COLUMNNAME_FINISHDESC2, FINISHDESC2);
	}

	/** Get FINISHDESC2.
		@return FINISHDESC2	  */
	public String getFINISHDESC2 () 
	{
		return (String)get_Value(COLUMNNAME_FINISHDESC2);
	}

	/** FIXTYPE AD_Reference_ID=1000011 */
	public static final int FIXTYPE_AD_Reference_ID=1000011;
	/** BOX TYPE = B */
	public static final String FIXTYPE_BOXTYPE = "B";
	/** CONCEALED = C */
	public static final String FIXTYPE_CONCEALED = "C";
	/** FLANGE TYPE = F */
	public static final String FIXTYPE_FLANGETYPE = "F";
	/** SCREW TYPE = S */
	public static final String FIXTYPE_SCREWTYPE = "S";
	/** S & C TYPE = T */
	public static final String FIXTYPE_SCTYPE = "T";
	/** Set FIXTYPE.
		@param FIXTYPE FIXTYPE	  */
	public void setFIXTYPE (String FIXTYPE)
	{

		set_Value (COLUMNNAME_FIXTYPE, FIXTYPE);
	}

	/** Get FIXTYPE.
		@return FIXTYPE	  */
	public String getFIXTYPE () 
	{
		return (String)get_Value(COLUMNNAME_FIXTYPE);
	}

	/** FRONTBLADE AD_Reference_ID=1000012 */
	public static final int FRONTBLADE_AD_Reference_ID=1000012;
	/** HORIZONTAL = H */
	public static final String FRONTBLADE_HORIZONTAL = "H";
	/** VERTICAL = V */
	public static final String FRONTBLADE_VERTICAL = "V";
	/** Set FRONTBLADE.
		@param FRONTBLADE FRONTBLADE	  */
	public void setFRONTBLADE (String FRONTBLADE)
	{

		set_Value (COLUMNNAME_FRONTBLADE, FRONTBLADE);
	}

	/** Get FRONTBLADE.
		@return FRONTBLADE	  */
	public String getFRONTBLADE () 
	{
		return (String)get_Value(COLUMNNAME_FRONTBLADE);
	}

	/** Set Print Quotation.
		@param GDPRINT Print Quotation	  */
	public void setGDPRINT (String GDPRINT)
	{
		set_Value (COLUMNNAME_GDPRINT, GDPRINT);
	}

	/** Get Print Quotation.
		@return Print Quotation	  */
	public String getGDPRINT () 
	{
		return (String)get_Value(COLUMNNAME_GDPRINT);
	}

	/** Set Grand Total.
		@param GrandTotal 
		Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal)
	{
		set_Value (COLUMNNAME_GrandTotal, GrandTotal);
	}

	/** Get Grand Total.
		@return Total amount of document
	  */
	public BigDecimal getGrandTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrandTotal);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
	
	/** Set TaxAmt  Added on 18/12/2017
	@param TaxAmt 
	Total TaxAmt of document
  */
	public void setTaxAmt (BigDecimal TaxAmt)
	{
		set_Value (COLUMNNAME_TaxAmt, TaxAmt);
	}
	
	/** Get TaxAmt.
		@return Total TaxAmt of document
	  */
	public BigDecimal getTaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
	

	/** HWSUPPLY AD_Reference_ID=1000030 */
	public static final int HWSUPPLY_AD_Reference_ID=1000030;
	/** ADF = A */
	public static final String HWSUPPLY_ADF = "A";
	/** CUSTOMER = C */
	public static final String HWSUPPLY_CUSTOMER = "C";
	/** Set Hardware Supply.
		@param HWSUPPLY Hardware Supply	  */
	public void setHWSUPPLY (String HWSUPPLY)
	{

		set_Value (COLUMNNAME_HWSUPPLY, HWSUPPLY);
	}

	/** Get Hardware Supply.
		@return Hardware Supply	  */
	public String getHWSUPPLY () 
	{
		return (String)get_Value(COLUMNNAME_HWSUPPLY);
	}

	/** Set ISDOOR.
		@param ISDOOR ISDOOR	  */
	public void setISDOOR (boolean ISDOOR)
	{
		set_Value (COLUMNNAME_ISDOOR, Boolean.valueOf(ISDOOR));
	}

	/** Get ISDOOR.
		@return ISDOOR	  */
	public boolean isDOOR () 
	{
		Object oo = get_Value(COLUMNNAME_ISDOOR);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** JOBSTATUS AD_Reference_ID=1000013 */
	public static final int JOBSTATUS_AD_Reference_ID=1000013;
	/** JOB IN HAND = J */
	public static final String JOBSTATUS_JOBINHAND = "J";
	/** TENDER = T */
	public static final String JOBSTATUS_TENDER = "T";
	/** Set JOBSTATUS.
		@param JOBSTATUS JOBSTATUS	  */
	public void setJOBSTATUS (String JOBSTATUS)
	{

		set_Value (COLUMNNAME_JOBSTATUS, JOBSTATUS);
	}

	/** Get JOBSTATUS.
		@return JOBSTATUS	  */
	public String getJOBSTATUS () 
	{
		return (String)get_Value(COLUMNNAME_JOBSTATUS);
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

	/** Set NETAMT.
		@param NETAMT NETAMT	  */
	public void setNETAMT (BigDecimal NETAMT)
	{
		set_Value (COLUMNNAME_NETAMT, NETAMT);
	}

	/** Get NETAMT.
		@return NETAMT	  */
	public BigDecimal getNETAMT () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_NETAMT);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Order Reference.
		@param POReference 
		Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	  */
	public void setPOReference (String POReference)
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	/** Get Order Reference.
		@return Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	  */
	public String getPOReference () 
	{
		return (String)get_Value(COLUMNNAME_POReference);
	}

	/** POWDERCOATTYPE AD_Reference_ID=1000014 */
	public static final int POWDERCOATTYPE_AD_Reference_ID=1000014;
	/** POWDER COATED TO RAL 9010/9016 = 1 */
	public static final String POWDERCOATTYPE_POWDERCOATEDTORAL90109016 = "1";
	/** POWDER COATED TO RAL 9016 = 2 */
	public static final String POWDERCOATTYPE_POWDERCOATEDTORAL9016 = "2";
	/** POWDER COATED TO RAL 9010 = 3 */
	public static final String POWDERCOATTYPE_POWDERCOATEDTORAL9010 = "3";
	/** ALUMINIUM SILVER COLOR ANODISED = 4 */
	public static final String POWDERCOATTYPE_ALUMINIUMSILVERCOLORANODISED = "4";
	/** Set POWDERCOATTYPE.
		@param POWDERCOATTYPE POWDERCOATTYPE	  */
	public void setPOWDERCOATTYPE (String POWDERCOATTYPE)
	{

		set_Value (COLUMNNAME_POWDERCOATTYPE, POWDERCOATTYPE);
	}

	/** Get POWDERCOATTYPE.
		@return POWDERCOATTYPE	  */
	public String getPOWDERCOATTYPE () 
	{
		return (String)get_Value(COLUMNNAME_POWDERCOATTYPE);
	}

	/** Set Print Frame & Leaf.
		@param PRINTDOOR Print Frame & Leaf	  */
	public void setPRINTDOOR (String PRINTDOOR)
	{
		set_Value (COLUMNNAME_PRINTDOOR, PRINTDOOR);
	}

	/** Get Print Frame & Leaf.
		@return Print Frame & Leaf	  */
	public String getPRINTDOOR () 
	{
		return (String)get_Value(COLUMNNAME_PRINTDOOR);
	}

	/** Set Print Summary.
		@param PRINTDOORSUMMARY Print Summary	  */
	public void setPRINTDOORSUMMARY (String PRINTDOORSUMMARY)
	{
		set_Value (COLUMNNAME_PRINTDOORSUMMARY, PRINTDOORSUMMARY);
	}

	/** Get Print Summary.
		@return Print Summary	  */
	public String getPRINTDOORSUMMARY () 
	{
		return (String)get_Value(COLUMNNAME_PRINTDOORSUMMARY);
	}

	/** Set PRINTHW.
		@param PRINTHW PRINTHW	  */
	public void setPRINTHW (String PRINTHW)
	{
		set_Value (COLUMNNAME_PRINTHW, PRINTHW);
	}

	/** Get PRINTHW.
		@return PRINTHW	  */
	public String getPRINTHW () 
	{
		return (String)get_Value(COLUMNNAME_PRINTHW);
	}

	/** Set PROJECT.
		@param PROJECT PROJECT	  */
	public void setPROJECT (String PROJECT)
	{
		set_Value (COLUMNNAME_PROJECT, PROJECT);
	}

	/** Get PROJECT.
		@return PROJECT	  */
	public String getPROJECT () 
	{
		return (String)get_Value(COLUMNNAME_PROJECT);
	}

	/** Set PROJ_REF.
		@param PROJ_REF PROJ_REF	  */
	public void setPROJ_REF (String PROJ_REF)
	{
		set_Value (COLUMNNAME_PROJ_REF, PROJ_REF);
	}

	/** Get PROJ_REF.
		@return PROJ_REF	  */
	public String getPROJ_REF () 
	{
		return (String)get_Value(COLUMNNAME_PROJ_REF);
	}

	/** Set Posted.
		@param Posted 
		Posting status
	  */
	public void setPosted (boolean Posted)
	{
		set_Value (COLUMNNAME_Posted, Boolean.valueOf(Posted));
	}

	/** Get Posted.
		@return Posting status
	  */
	public boolean isPosted () 
	{
		Object oo = get_Value(COLUMNNAME_Posted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** QUOTSTATUS AD_Reference_ID=1000015 */
	public static final int QUOTSTATUS_AD_Reference_ID=1000015;
	/** INPROCESS = I */
	public static final String QUOTSTATUS_INPROCESS = "I";
	/** LOST = L */
	public static final String QUOTSTATUS_LOST = "L";
	/** REJECT = R */
	public static final String QUOTSTATUS_REJECT = "R";
	/** Set QUOTSTATUS.
		@param QUOTSTATUS QUOTSTATUS	  */
	public void setQUOTSTATUS (String QUOTSTATUS)
	{

		set_Value (COLUMNNAME_QUOTSTATUS, QUOTSTATUS);
	}

	/** Get QUOTSTATUS.
		@return QUOTSTATUS	  */
	public String getQUOTSTATUS () 
	{
		return (String)get_Value(COLUMNNAME_QUOTSTATUS);
	}

	/** Set SO_REF.
		@param SO_REF SO_REF	  */
	public void setSO_REF (String SO_REF)
	{
		set_Value (COLUMNNAME_SO_REF, SO_REF);
	}

	/** Get SO_REF.
		@return SO_REF	  */
	public String getSO_REF () 
	{
		return (String)get_Value(COLUMNNAME_SO_REF);
	}

	public I_AD_User getSalesRep() throws RuntimeException
    {
		return (I_AD_User)MTable.get(getCtx(), I_AD_User.Table_Name)
			.getPO(getSalesRep_ID(), get_TrxName());	}

	/** Set Sales Representative.
		@param SalesRep_ID 
		Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_Value (COLUMNNAME_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Sales Representative.
		@return Sales Representative or Company Agent
	  */
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Subject.
		@param Subject 
		Email Message Subject
	  */
	public void setSubject (String Subject)
	{
		set_Value (COLUMNNAME_Subject, Subject);
	}

	/** Get Subject.
		@return Email Message Subject
	  */
	public String getSubject () 
	{
		return (String)get_Value(COLUMNNAME_Subject);
	}

	/** Set TOTAL_COST.
		@param TOTAL_COST TOTAL_COST	  */
	public void setTOTAL_COST (BigDecimal TOTAL_COST)
	{
		set_Value (COLUMNNAME_TOTAL_COST, TOTAL_COST);
	}

	/** Get TOTAL_COST.
		@return TOTAL_COST	  */
	public BigDecimal getTOTAL_COST () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TOTAL_COST);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** UNITTYPE AD_Reference_ID=1000016 */
	public static final int UNITTYPE_AD_Reference_ID=1000016;
	/** INCH = I */
	public static final String UNITTYPE_INCH = "I";
	/** MM = M */
	public static final String UNITTYPE_MM = "M";
	/** Set UNITTYPE.
		@param UNITTYPE UNITTYPE	  */
	public void setUNITTYPE (String UNITTYPE)
	{

		set_Value (COLUMNNAME_UNITTYPE, UNITTYPE);
	}

	/** Get UNITTYPE.
		@return UNITTYPE	  */
	public String getUNITTYPE () 
	{
		return (String)get_Value(COLUMNNAME_UNITTYPE);
	}

	/** Set VALIDITY.
		@param VALIDITY VALIDITY	  */
	public void setVALIDITY (String VALIDITY)
	{
		set_Value (COLUMNNAME_VALIDITY, VALIDITY);
	}

	/** Get VALIDITY.
		@return VALIDITY	  */
	public String getVALIDITY () 
	{
		return (String)get_Value(COLUMNNAME_VALIDITY);
	}
}