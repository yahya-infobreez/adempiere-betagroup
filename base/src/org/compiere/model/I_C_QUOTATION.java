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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for C_QUOTATION
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS
 */
public interface I_C_QUOTATION 
{

    /** TableName=C_QUOTATION */
    public static final String Table_Name = "C_QUOTATION";

    /** AD_Table_ID=1000024 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name ATTENTION */
    public static final String COLUMNNAME_ATTENTION = "ATTENTION";

	/** Set ATTENTION	  */
	public void setATTENTION (String ATTENTION);

	/** Get ATTENTION	  */
	public String getATTENTION();

    /** Column name BTN_GEN_PROJECT */
    public static final String COLUMNNAME_BTN_GEN_PROJECT = "BTN_GEN_PROJECT";

	/** Set BTN_GEN_PROJECT	  */
	public void setBTN_GEN_PROJECT (String BTN_GEN_PROJECT);

	/** Get BTN_GEN_PROJECT	  */
	public String getBTN_GEN_PROJECT();

    /** Column name BTN_RETRIEVECOST */
    public static final String COLUMNNAME_BTN_RETRIEVECOST = "BTN_RETRIEVECOST";

	/** Set BTN_RETRIEVECOST	  */
	public void setBTN_RETRIEVECOST (String BTN_RETRIEVECOST);

	/** Get BTN_RETRIEVECOST	  */
	public String getBTN_RETRIEVECOST();

    /** Column name CREATEDOOR */
    public static final String COLUMNNAME_CREATEDOOR = "CREATEDOOR";

	/** Set CREATEDOOR	  */
	public void setCREATEDOOR (String CREATEDOOR);

	/** Get CREATEDOOR	  */
	public String getCREATEDOOR();

    /** Column name CUSTNAME */
    public static final String COLUMNNAME_CUSTNAME = "CUSTNAME";

	/** Set CUSTNAME	  */
	public void setCUSTNAME (String CUSTNAME);

	/** Get CUSTNAME	  */
	public String getCUSTNAME();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name C_DocType_ID */
    public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public I_C_DocType getC_DocType() throws RuntimeException;

    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/** Set Order.
	  * Order
	  */
	public void setC_Order_ID (int C_Order_ID);

	/** Get Order.
	  * Order
	  */
	public int getC_Order_ID();

    /** Column name C_PaymentTerm_ID */
    public static final String COLUMNNAME_C_PaymentTerm_ID = "C_PaymentTerm_ID";

	/** Set Payment Term.
	  * The terms of Payment (timing, discount)
	  */
	public void setC_PaymentTerm_ID (int C_PaymentTerm_ID);

	/** Get Payment Term.
	  * The terms of Payment (timing, discount)
	  */
	public int getC_PaymentTerm_ID();

	public I_C_PaymentTerm getC_PaymentTerm() throws RuntimeException;

    /** Column name C_ProjectType_ID */
    public static final String COLUMNNAME_C_ProjectType_ID = "C_ProjectType_ID";

	/** Set Project Type.
	  * Type of the project
	  */
	public void setC_ProjectType_ID (int C_ProjectType_ID);

	/** Get Project Type.
	  * Type of the project
	  */
	public int getC_ProjectType_ID();

	public I_C_ProjectType getC_ProjectType() throws RuntimeException;

    /** Column name C_Project_ID */
    public static final String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/** Set Project.
	  * Financial Project
	  */
	public void setC_Project_ID (int C_Project_ID);

	/** Get Project.
	  * Financial Project
	  */
	public int getC_Project_ID();

	public I_C_Project getC_Project() throws RuntimeException;

    /** Column name C_QUOTATION_ID */
    public static final String COLUMNNAME_C_QUOTATION_ID = "C_QUOTATION_ID";

	/** Set Quotation	  */
	public void setC_QUOTATION_ID (int C_QUOTATION_ID);

	/** Get Quotation	  */
	public int getC_QUOTATION_ID();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name DAMPERS */
    public static final String COLUMNNAME_DAMPERS = "DAMPERS";

	/** Set DAMPERS	  */
	public void setDAMPERS (String DAMPERS);

	/** Get DAMPERS	  */
	public String getDAMPERS();

    /** Column name DELIVERY */
    public static final String COLUMNNAME_DELIVERY = "DELIVERY";

	/** Set DELIVERY	  */
	public void setDELIVERY (String DELIVERY);

	/** Get DELIVERY	  */
	public String getDELIVERY();

    /** Column name DISCAMT */
    public static final String COLUMNNAME_DISCAMT = "DISCAMT";

	/** Set DISCAMT	  */
	public void setDISCAMT (BigDecimal DISCAMT);

	/** Get DISCAMT	  */
	public BigDecimal getDISCAMT();

    /** Column name DISCPER */
    public static final String COLUMNNAME_DISCPER = "DISCPER";

	/** Set DISCPER	  */
	public void setDISCPER (BigDecimal DISCPER);

	/** Get DISCPER	  */
	public BigDecimal getDISCPER();

    /** Column name DISCPERFR */
    public static final String COLUMNNAME_DISCPERFR = "DISCPERFR";

	/** Set Disc % Frame	  */
	public void setDISCPERFR (BigDecimal DISCPERFR);

	/** Get Disc % Frame	  */
	public BigDecimal getDISCPERFR();

    /** Column name DISCPERHW */
    public static final String COLUMNNAME_DISCPERHW = "DISCPERHW";

	/** Set Disc % Hardware	  */
	public void setDISCPERHW (BigDecimal DISCPERHW);

	/** Get Disc % Hardware	  */
	public BigDecimal getDISCPERHW();

    /** Column name DISCPERLF */
    public static final String COLUMNNAME_DISCPERLF = "DISCPERLF";

	/** Set Disc % Leaf	  */
	public void setDISCPERLF (BigDecimal DISCPERLF);

	/** Get Disc % Leaf	  */
	public BigDecimal getDISCPERLF();

    /** Column name DOCDATE */
    public static final String COLUMNNAME_DOCDATE = "DOCDATE";

	/** Set Doc Date	  */
	public void setDOCDATE (Timestamp DOCDATE);

	/** Get Doc Date	  */
	public Timestamp getDOCDATE();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name DocAction */
    public static final String COLUMNNAME_DocAction = "DocAction";

	/** Set Document Action.
	  * The targeted status of the document
	  */
	public void setDocAction (String DocAction);

	/** Get Document Action.
	  * The targeted status of the document
	  */
	public String getDocAction();

    /** Column name DocStatus */
    public static final String COLUMNNAME_DocStatus = "DocStatus";

	/** Set Document Status.
	  * The current status of the document
	  */
	public void setDocStatus (String DocStatus);

	/** Get Document Status.
	  * The current status of the document
	  */
	public String getDocStatus();

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name FINISHDESC1 */
    public static final String COLUMNNAME_FINISHDESC1 = "FINISHDESC1";

	/** Set FINISHDESC1	  */
	public void setFINISHDESC1 (String FINISHDESC1);

	/** Get FINISHDESC1	  */
	public String getFINISHDESC1();

    /** Column name FINISHDESC2 */
    public static final String COLUMNNAME_FINISHDESC2 = "FINISHDESC2";

	/** Set FINISHDESC2	  */
	public void setFINISHDESC2 (String FINISHDESC2);

	/** Get FINISHDESC2	  */
	public String getFINISHDESC2();

    /** Column name FIXTYPE */
    public static final String COLUMNNAME_FIXTYPE = "FIXTYPE";

	/** Set FIXTYPE	  */
	public void setFIXTYPE (String FIXTYPE);

	/** Get FIXTYPE	  */
	public String getFIXTYPE();

    /** Column name FRONTBLADE */
    public static final String COLUMNNAME_FRONTBLADE = "FRONTBLADE";

	/** Set FRONTBLADE	  */
	public void setFRONTBLADE (String FRONTBLADE);

	/** Get FRONTBLADE	  */
	public String getFRONTBLADE();

    /** Column name GDPRINT */
    public static final String COLUMNNAME_GDPRINT = "GDPRINT";

	/** Set Print Quotation	  */
	public void setGDPRINT (String GDPRINT);

	/** Get Print Quotation	  */
	public String getGDPRINT();

    /** Column name GrandTotal */
    public static final String COLUMNNAME_GrandTotal = "GrandTotal";

	/** Set Grand Total.
	  * Total amount of document
	  */
	public void setGrandTotal (BigDecimal GrandTotal);

	/** Get Grand Total.
	  * Total amount of document
	  */
	public BigDecimal getGrandTotal();
	
	/** Column name TaxAmt Added on 18/12/2017*/
    public static final String COLUMNNAME_TaxAmt = "TaxAmt";

	/** Set TaxAmt.
	  * Total TaxAmt of document
	  */
	public void setTaxAmt (BigDecimal TaxAmt);

	/** Get TaxAmt.
	  * Total TaxAmt of document
	  */
	public BigDecimal getTaxAmt();
	
	

    /** Column name HWSUPPLY */
    public static final String COLUMNNAME_HWSUPPLY = "HWSUPPLY";

	/** Set Hardware Supply	  */
	public void setHWSUPPLY (String HWSUPPLY);

	/** Get Hardware Supply	  */
	public String getHWSUPPLY();

    /** Column name ISDOOR */
    public static final String COLUMNNAME_ISDOOR = "ISDOOR";

	/** Set ISDOOR	  */
	public void setISDOOR (boolean ISDOOR);

	/** Get ISDOOR	  */
	public boolean isDOOR();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name JOBSTATUS */
    public static final String COLUMNNAME_JOBSTATUS = "JOBSTATUS";

	/** Set JOBSTATUS	  */
	public void setJOBSTATUS (String JOBSTATUS);

	/** Get JOBSTATUS	  */
	public String getJOBSTATUS();

    /** Column name M_Warehouse_ID */
    public static final String COLUMNNAME_M_Warehouse_ID = "M_Warehouse_ID";

	/** Set Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID);

	/** Get Warehouse.
	  * Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID();

	public I_M_Warehouse getM_Warehouse() throws RuntimeException;

    /** Column name NETAMT */
    public static final String COLUMNNAME_NETAMT = "NETAMT";

	/** Set NETAMT	  */
	public void setNETAMT (BigDecimal NETAMT);

	/** Get NETAMT	  */
	public BigDecimal getNETAMT();

    /** Column name POReference */
    public static final String COLUMNNAME_POReference = "POReference";

	/** Set Order Reference.
	  * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	  */
	public void setPOReference (String POReference);

	/** Get Order Reference.
	  * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	  */
	public String getPOReference();

    /** Column name POWDERCOATTYPE */
    public static final String COLUMNNAME_POWDERCOATTYPE = "POWDERCOATTYPE";

	/** Set POWDERCOATTYPE	  */
	public void setPOWDERCOATTYPE (String POWDERCOATTYPE);

	/** Get POWDERCOATTYPE	  */
	public String getPOWDERCOATTYPE();

    /** Column name PRINTDOOR */
    public static final String COLUMNNAME_PRINTDOOR = "PRINTDOOR";

	/** Set Print Frame & Leaf	  */
	public void setPRINTDOOR (String PRINTDOOR);

	/** Get Print Frame & Leaf	  */
	public String getPRINTDOOR();

    /** Column name PRINTDOORSUMMARY */
    public static final String COLUMNNAME_PRINTDOORSUMMARY = "PRINTDOORSUMMARY";

	/** Set Print Summary	  */
	public void setPRINTDOORSUMMARY (String PRINTDOORSUMMARY);

	/** Get Print Summary	  */
	public String getPRINTDOORSUMMARY();

    /** Column name PRINTHW */
    public static final String COLUMNNAME_PRINTHW = "PRINTHW";

	/** Set PRINTHW	  */
	public void setPRINTHW (String PRINTHW);

	/** Get PRINTHW	  */
	public String getPRINTHW();

    /** Column name PROJECT */
    public static final String COLUMNNAME_PROJECT = "PROJECT";

	/** Set PROJECT	  */
	public void setPROJECT (String PROJECT);

	/** Get PROJECT	  */
	public String getPROJECT();

    /** Column name PROJ_REF */
    public static final String COLUMNNAME_PROJ_REF = "PROJ_REF";

	/** Set PROJ_REF	  */
	public void setPROJ_REF (String PROJ_REF);

	/** Get PROJ_REF	  */
	public String getPROJ_REF();

    /** Column name Posted */
    public static final String COLUMNNAME_Posted = "Posted";

	/** Set Posted.
	  * Posting status
	  */
	public void setPosted (boolean Posted);

	/** Get Posted.
	  * Posting status
	  */
	public boolean isPosted();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name QUOTSTATUS */
    public static final String COLUMNNAME_QUOTSTATUS = "QUOTSTATUS";

	/** Set QUOTSTATUS	  */
	public void setQUOTSTATUS (String QUOTSTATUS);

	/** Get QUOTSTATUS	  */
	public String getQUOTSTATUS();

    /** Column name SO_REF */
    public static final String COLUMNNAME_SO_REF = "SO_REF";

	/** Set SO_REF	  */
	public void setSO_REF (String SO_REF);

	/** Get SO_REF	  */
	public String getSO_REF();

    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/** Set Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID);

	/** Get Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public int getSalesRep_ID();

	public I_AD_User getSalesRep() throws RuntimeException;

    /** Column name Subject */
    public static final String COLUMNNAME_Subject = "Subject";

	/** Set Subject.
	  * Email Message Subject
	  */
	public void setSubject (String Subject);

	/** Get Subject.
	  * Email Message Subject
	  */
	public String getSubject();

    /** Column name TOTAL_COST */
    public static final String COLUMNNAME_TOTAL_COST = "TOTAL_COST";

	/** Set TOTAL_COST	  */
	public void setTOTAL_COST (BigDecimal TOTAL_COST);

	/** Get TOTAL_COST	  */
	public BigDecimal getTOTAL_COST();

    /** Column name UNITTYPE */
    public static final String COLUMNNAME_UNITTYPE = "UNITTYPE";

	/** Set UNITTYPE	  */
	public void setUNITTYPE (String UNITTYPE);

	/** Get UNITTYPE	  */
	public String getUNITTYPE();
	
	

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();

    /** Column name VALIDITY */
    public static final String COLUMNNAME_VALIDITY = "VALIDITY";

	/** Set VALIDITY	  */
	public void setVALIDITY (String VALIDITY);

	/** Get VALIDITY	  */
	public String getVALIDITY();
}
