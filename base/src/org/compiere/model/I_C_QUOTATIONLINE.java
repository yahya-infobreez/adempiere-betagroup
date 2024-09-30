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

/** Generated Interface for C_QUOTATIONLINE
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS
 */
public interface I_C_QUOTATIONLINE 
{

    /** TableName=C_QUOTATIONLINE */
    public static final String Table_Name = "C_QUOTATIONLINE";

    /** AD_Table_ID=1000025 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

    /** Load Meta Data */
    
    /** Column name LENGTH */
    public static final String COLUMNNAME_LENGTH = "LENGTH";
	/** Set LENGTH	  */
	public void setLENGTH (BigDecimal LENGTH);
	/** Get LENGTH	  */
	public BigDecimal getLENGTH();

	//Added for Actuator,Thermostat,Transformer  @25.12.2013
	/** Column name Beta_Qtn_Actuator_ID */
    public static final String COLUMNNAME_Beta_Qtn_Actuator_ID = "Beta_Qtn_Actuator_ID";
	/** Set ACTUATOR	  */
	public void setBeta_Qtn_Actuator_ID (int Beta_Qtn_Actuator_ID);
	/** Get ACTUATOR	  */
	public int getBeta_Qtn_Actuator_ID();
	
	/** Column name BETA_QTN_THERMOSTAT_ID */
    public static final String COLUMNNAME_BETA_QTN_THERMOSTAT_ID = "BETA_QTN_THERMOSTAT_ID";
	/** Set THERMOSTAT	  */
	public void setBETA_QTN_THERMOSTAT_ID (int BETA_QTN_THERMOSTAT_ID);
	/** Get THERMOSTAT	  */
	public int getBETA_QTN_THERMOSTAT_ID();
	
	/** Column name BETA_QTN_TRANSFORMER_ID */
    public static final String COLUMNNAME_BETA_QTN_TRANSFORMER_ID = "BETA_QTN_TRANSFORMER_ID";
	/** Set TRANSFORMER	  */
	public void setBETA_QTN_TRANSFORMER_ID (int BETA_QTN_TRANSFORMER_ID);
	/** Get TRANSFORMER	  */
	public int getBETA_QTN_TRANSFORMER_ID();
	
	/** Column name Beta_Qtn_Heater_ID */
    public static final String COLUMNNAME_Beta_Qtn_Heater_ID = "Beta_Qtn_Heater_ID";
	/** Set Heater	  */
	public void setBeta_Qtn_Heater_ID (int Beta_Qtn_Heater_ID);
	/** Get Heater	  */
	public int getBeta_Qtn_Heater_ID();
	
	//END Added for Actuator,Thermostat,Transformer,Heater  @25.12.2013
	
	/** Column name ACTQTY **Added on 04Jun2014*/
    public static final String COLUMNNAME_ACTQTY = "ACTQTY";

	/** Set ACTQTY	 **Added on 04Jun2014 */
	public void setACTQTY (BigDecimal ACTQTY);

	/** Get ACTQTY	  **Added on 04Jun2014*/
	public BigDecimal getACTQTY();
	
    /** Column name A */
    public static final String COLUMNNAME_A = "A";

	/** Set A	  */
	public void setA (BigDecimal A);

	/** Get A	  */
	public BigDecimal getA();
	
	/** Column name SLOTNO **Added on 03Mar2014*/
    public static final String COLUMNNAME_SLOTNO = "SLOTNO";

	/** Set SLOTNO	 **Added on 03Mar2014 */
	public void setSLOTNO (BigDecimal SLOTNO);

	/** Get SLOTNO	  **Added on 03Mar2014*/
	public BigDecimal getSLOTNO();
	
	/** Column name NECKDIA **Added on 03Apr2014*/
    public static final String COLUMNNAME_NECKDIA = "NECKDIA";

	/** Set NECKDIA	 **Added on 03Apr2014 */
	public void setNECKDIA (BigDecimal NECKDIA);

	/** Get NECKDIA	  **Added on 03Apr2014*/
	public BigDecimal getNECKDIA();
	
	/** Column name BUSHBEAR **Added on 18May2016*/
    public static final String COLUMNNAME_BUSHBEAR = "BUSHBEAR";

	/** Set BUSHBEAR	 **Added on 18May2016 */
	public void setBUSHBEAR (String BUSHBEAR);

	/** Get BUSHBEAR	  **Added on 18May2016*/
	public String getBUSHBEAR();
	
	/** Column name MECHANISM **Added on 26Oct2016*/
    public static final String COLUMNNAME_MECHANISM = "MECHANISM";

	/** Set MECHANISM	 **Added on 26Oct2016 */
	public void setMECHANISM (String MECHANISM);

	/** Get MECHANISM	  **Added on 26Oct2016*/
	public String getMECHANISM();
	
	/** Column name METALTHICK **Added on 18May2016*/
    public static final String COLUMNNAME_METALTHICK = "METALTHICK";

	/** Set METALTHICK	 **Added on 18May2016 */
	public void setMETALTHICK (String METALTHICK);

	/** Get METALTHICK	  **Added on 18May2016*/
	public String getMETALTHICK();
	
	//Added for FAN CASING
	 
	// Column name CA_MODEL 
    public static final String COLUMNNAME_CA_MODEL = "CA_MODEL";
	// Set CA_MODEL	 
	public void setCA_MODEL (String CA_MODEL);
	// Get CA_MODEL	  
	public String getCA_MODEL();
	
	// Column name CA_TYPE 
    public static final String COLUMNNAME_CA_TYPE = "CA_TYPE";
	// Set CA_TYPE	 
	public void setCA_TYPE (String CA_TYPE);
	// Get CA_TYPE	  
	public String getCA_TYPE();
	
	// Column name CA_BD 
    public static final String COLUMNNAME_CA_BD = "CA_BD";
	// Set CA_BD	 
	public void setCA_BD (String CA_BD);
	// Get CA_BD	  
	public String getCA_BD();
	
	//End of Added for FAN CASING
	
	// Column name STADYN 
    public static final String COLUMNNAME_STADYN = "STADYN";
	// Set STADYN	 
	public void setSTADYN (String STADYN);
	// Get STADYN	  
	public String getSTADYN();
	
	
	/** Column name GNA **Added on 03Apr2014*/
    public static final String COLUMNNAME_GNA = "GNA";

	/** Set GNA	 **Added on 03Apr2014 */
	public void setGNA (BigDecimal GNA);

	/** Get GNA	  **Added on 03Apr2014*/
	public BigDecimal getGNA();
	
	/** Column name GNB **Added on 03Apr2014*/
    public static final String COLUMNNAME_GNB = "GNB";

	/** Set GNB	 **Added on 03Apr2014 */
	public void setGNB (BigDecimal GNB);

	/** Get GNB	  **Added on 03Apr2014*/
	public BigDecimal getGNB();

    /** Column name ADDATTR */
    public static final String COLUMNNAME_ADDATTR = "ADDATTR";
    
  //Column Name FDC_RN_SIZE
  	public static final String COLUMNNAME_FDC_RN_SIZE = "FDC_RN_SIZE";
  	public void setFDC_RN_SIZE (BigDecimal FDC_RN_SIZE);
  	public BigDecimal getFDC_RN_SIZE();

  /** Column name FDC_RN_YN */  //Added 21.02.2023
  	public static final String COLUMNNAME_FDC_RN_YN = "FDC_RN_YN";
  	public void setFDC_RN_YN (boolean FDC_RN_YN);
  	public boolean isFDC_RN_YN();


	/** Set Create Attributes	  */
	public void setADDATTR (String ADDATTR);

	/** Get Create Attributes	  */
	public String getADDATTR();

    /** Column name ADFCODE */
    public static final String COLUMNNAME_ADFCODE = "ADFCODE";

	/** Set ADFCODE	  */
	public void setADFCODE (String ADFCODE);

	/** Get ADFCODE	  */
	public String getADFCODE();

	/** Column name DRIVE **Added on 29.09.2015*/
    public static final String COLUMNNAME_DRIVE = "DRIVE";

	/** Set DRIVE	  */
	public void setDRIVE (String DRIVE);

	/** Get DRIVE	  */
	public String getDRIVE();
	
	/** Column name ULCLASS **Added on 25.09.2016*/
    public static final String COLUMNNAME_ULCLASS = "ULCLASS";

	/** Set ULCLASS	  */
	public void setULCLASS (String ULCLASS);

	/** Get ULCLASS	  */
	public String getULCLASS();
	
	//Act Brand & UL Class Type for BMSD-NEW & BMFSD-NEW  10.03.2020
	public static final String COLUMNNAME_UL_CLASSTYPE = "UL_CLASSTYPE";
	public void setUL_CLASSTYPE (String UL_CLASSTYPE);
	public String getUL_CLASSTYPE();
	
	public static final String COLUMNNAME_UL_ACT = "UL_ACT";
	public void setUL_ACT (String UL_ACT);
	public String getUL_ACT();
	
	public static final String COLUMNNAME_UL_INTEXTACT = "UL_INTEXTACT";
	public void setUL_INTEXTACT (String UL_INTEXTACT);
	public String getUL_INTEXTACT();

	public static final String COLUMNNAME_UL_TEMP = "UL_TEMP";
	public void setUL_TEMP (String UL_TEMP);
	public String getUL_TEMP();
	
	public static final String COLUMNNAME_UL_SPIGOT = "UL_SPIGOT";
	public void setUL_SPIGOT (boolean UL_SPIGOT);
	public boolean isUL_SPIGOT();

	//End of Act Brand & UL Class Type for BMSD-NEW & BMFSD-NEW  10.03.2020

	/** Column name VCD_NONSTD_YN */  //Added 24.02.2020
	public static final String COLUMNNAME_VCD_NONSTD_YN = "VCD_NONSTD_YN";
	public void setVCD_NONSTD_YN (boolean VCD_NONSTD_YN);
	public boolean isVCD_NONSTD_YN();
	
	/** Column name VCD_BLADE */  //Added 24.02.2020
	public static final String COLUMNNAME_VCD_BLADE = "VCD_BLADE";
	public void setVCD_BLADE (String VCD_BLADE);
	public String getVCD_BLADE();
	
	/** Column name SETULCLASS */  //Added 25.09.2016
    public static final String COLUMNNAME_SETULCLASS = "SETULCLASS";

	/** Set SETULCLASS	  */
	public void setSETULCLASS (boolean SETULCLASS);

	/** Get SETULCLASS	  */
	public boolean isSETULCLASS();
	
	/** Column name SETVAVYN */  //Added 14.02.2019
	public static final String COLUMNNAME_SETVAVYN = "SETVAVYN";

	/** Set SETVAVYN	  */
	public void setSETVAVYN (boolean SETVAVYN);

	/** Get SETVAVYN	  */
	public boolean isSETVAVYN();

	/** Column name SETGASKET */  //Added 4.11.2021
	public static final String COLUMNNAME_SETGASKET = "SETGASKET";

	/** Set SETGASKET	  */
	public void setSETGASKET (boolean SETGASKET);

	/** Get SETGASKET	  */
	public boolean isSETGASKET();

	
	
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

    /** Column name ALUFILTERYN */
    public static final String COLUMNNAME_ALUFILTERYN = "ALUFILTERYN";

	/** Set ALUFILTERYN	  */
	public void setALUFILTERYN (boolean ALUFILTERYN);

	/** Get ALUFILTERYN	  */
	public boolean isALUFILTERYN();

	/** Column name TBARYN */  //Added on 23.02.2014
    public static final String COLUMNNAME_TBARYN = "TBARYN";

	/** Set TBARYN	  */
	public void setTBARYN (boolean TBARYN);

	/** Get TBARYN	  */
	public boolean isTBARYN();
	
	/** Column name LVBYAREAYN */  //Added on 09.04.2014
    public static final String COLUMNNAME_LVBYAREAYN = "LVBYAREAYN";

	/** Set LVBYAREAYN	  */
	public void setLVBYAREAYN (boolean LVBYAREAYN);

	/** Get LVBYAREAYN	  */
	public boolean isLVBYAREAYN();
	
    /** Column name B */
    public static final String COLUMNNAME_B = "B";

	/** Set B	  */
	public void setB (BigDecimal B);

	/** Get B	  */
	public BigDecimal getB();

    /** Column name CURVEYN */
    public static final String COLUMNNAME_CURVEYN = "CURVEYN";

	/** Set CURVEYN	  */
	public void setCURVEYN (boolean CURVEYN);

	/** Get CURVEYN	  */
	public boolean isCURVEYN();

    /** Column name C_Charge_ID */
    public static final String COLUMNNAME_C_Charge_ID = "C_Charge_ID";

	/** Set Charge.
	  * Additional document charges
	  */
	public void setC_Charge_ID (int C_Charge_ID);

	/** Get Charge.
	  * Additional document charges
	  */
	public int getC_Charge_ID();

	public I_C_Charge getC_Charge() throws RuntimeException;

    /** Column name C_QUOTATIONLINE_ID */
    public static final String COLUMNNAME_C_QUOTATIONLINE_ID = "C_QUOTATIONLINE_ID";

	/** Set Quotation Line	  */
	public void setC_QUOTATIONLINE_ID (int C_QUOTATIONLINE_ID);

	/** Get Quotation Line	  */
	public int getC_QUOTATIONLINE_ID();

    /** Column name C_QUOTATION_ID */
    public static final String COLUMNNAME_C_QUOTATION_ID = "C_QUOTATION_ID";

	/** Set Quotation	  */
	public void setC_QUOTATION_ID (int C_QUOTATION_ID);

	/** Get Quotation	  */
	public int getC_QUOTATION_ID();

	public I_C_QUOTATION getC_QUOTATION() throws RuntimeException;

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

    /** Column name DAMPERYN */
    public static final String COLUMNNAME_DAMPERYN = "DAMPERYN";

	/** Set Damper Y/N	  */
	public void setDAMPERYN (boolean DAMPERYN);

	/** Get Damper Y/N	  */
	public boolean isDAMPERYN();

    /** Column name DEFLECTION */
    public static final String COLUMNNAME_DEFLECTION = "DEFLECTION";

	/** Set DEFLECTION	  */
	public void setDEFLECTION (String DEFLECTION);

	/** Get DEFLECTION	  */
	public String getDEFLECTION();

    /** Column name DISC_PCNT */
    public static final String COLUMNNAME_DISC_PCNT = "DISC_PCNT";

	/** Set Discount %	  */
	public void setDISC_PCNT (BigDecimal DISC_PCNT);

	/** Get Discount %	  */
	public BigDecimal getDISC_PCNT();

    /** Column name DOORLH */
    public static final String COLUMNNAME_DOORLH = "DOORLH";

	/** Set LH	  */
	public void setDOORLH (int DOORLH);

	/** Get LH	  */
	public int getDOORLH();

    /** Column name DOORLHA */
    public static final String COLUMNNAME_DOORLHA = "DOORLHA";

	/** Set LHA	  */
	public void setDOORLHA (int DOORLHA);

	/** Get LHA	  */
	public int getDOORLHA();

    /** Column name DOORLHR */
    public static final String COLUMNNAME_DOORLHR = "DOORLHR";

	/** Set LHR	  */
	public void setDOORLHR (int DOORLHR);

	/** Get LHR	  */
	public int getDOORLHR();

    /** Column name DOORLHRA */
    public static final String COLUMNNAME_DOORLHRA = "DOORLHRA";

	/** Set LHRA	  */
	public void setDOORLHRA (int DOORLHRA);

	/** Get LHRA	  */
	public int getDOORLHRA();

    /** Column name DOORLOCATION */
    public static final String COLUMNNAME_DOORLOCATION = "DOORLOCATION";

	/** Set Location	  */
	public void setDOORLOCATION (String DOORLOCATION);

	/** Get Location	  */
	public String getDOORLOCATION();

    /** Column name DOORRH */
    public static final String COLUMNNAME_DOORRH = "DOORRH";

	/** Set RH	  */
	public void setDOORRH (int DOORRH);

	/** Get RH	  */
	public int getDOORRH();

    /** Column name DOORRHA */
    public static final String COLUMNNAME_DOORRHA = "DOORRHA";

	/** Set RHA	  */
	public void setDOORRHA (int DOORRHA);

	/** Get RHA	  */
	public int getDOORRHA();

    /** Column name DOORRHR */
    public static final String COLUMNNAME_DOORRHR = "DOORRHR";

	/** Set RHR	  */
	public void setDOORRHR (int DOORRHR);

	/** Get RHR	  */
	public int getDOORRHR();

    /** Column name DOORRHRA */
    public static final String COLUMNNAME_DOORRHRA = "DOORRHRA";

	/** Set RHRA	  */
	public void setDOORRHRA (int DOORRHRA);

	/** Get RHRA	  */
	public int getDOORRHRA();

    /** Column name DOORTYPE */
    public static final String COLUMNNAME_DOORTYPE = "DOORTYPE";

	/** Set Door Type	  */
	public void setDOORTYPE (String DOORTYPE);

	/** Get Door Type	  */
	public String getDOORTYPE();

    /** Column name EQUGRIDYN */
    public static final String COLUMNNAME_EQUGRIDYN = "EQUGRIDYN";

	/** Set EQUGRIDYN	  */
	public void setEQUGRIDYN (boolean EQUGRIDYN);

	/** Get EQUGRIDYN	  */
	public boolean isEQUGRIDYN();

    /** Column name FINISH */
    public static final String COLUMNNAME_FINISH = "FINISH";

	/** Set FINISH	  */
	public void setFINISH (String FINISH);

	/** Get FINISH	  */
	public String getFINISH();

    /** Column name GETPRICE */
    public static final String COLUMNNAME_GETPRICE = "GETPRICE";

	/** Set GETPRICE	  */
	public void setGETPRICE (String GETPRICE);

	/** Get GETPRICE	  */
	public String getGETPRICE();

    /** Column name HORVER */
    public static final String COLUMNNAME_HORVER = "HORVER";

	/** Set HORVER	  */
	public void setHORVER (String HORVER);

	/** Get HORVER	  */
	public String getHORVER();

    /** Column name INSSCRNYN */
    public static final String COLUMNNAME_INSSCRNYN = "INSSCRNYN";

	/** Set INSSCRNYN	  */
	public void setINSSCRNYN (boolean INSSCRNYN);

	/** Get INSSCRNYN	  */
	public boolean isINSSCRNYN();

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

    /** Column name IsBOM */
    public static final String COLUMNNAME_IsBOM = "IsBOM";

	/** Set Bill of Materials.
	  * Bill of Materials
	  */
	public void setIsBOM (boolean IsBOM);

	/** Get Bill of Materials.
	  * Bill of Materials
	  */
	public boolean isBOM();

    /** Column name Line */
    public static final String COLUMNNAME_Line = "Line";

	/** Set Line No.
	  * Unique line for this document
	  */
	public void setLine (int Line);

	/** Get Line No.
	  * Unique line for this document
	  */
	public int getLine();

    /** Column name MANTYPE */
    public static final String COLUMNNAME_MANTYPE = "MANTYPE";

	/** Set Manufacture Type	  */
	public void setMANTYPE (String MANTYPE);

	/** Get Manufacture Type	  */
	public String getMANTYPE();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public I_M_Product getM_Product() throws RuntimeException;

    /** Column name NETPRICE */
    public static final String COLUMNNAME_NETPRICE = "NETPRICE";

	/** Set NETPRICE	  */
	public void setNETPRICE (BigDecimal NETPRICE);

	/** Get NETPRICE	  */
	public BigDecimal getNETPRICE();

    /** Column name PRODDESC */
    public static final String COLUMNNAME_PRODDESC = "PRODDESC";

	/** Set PRODDESC	  */
	public void setPRODDESC (String PRODDESC);

	/** Get PRODDESC	  */
	public String getPRODDESC();

    /** Column name Price */
    public static final String COLUMNNAME_Price = "Price";

	/** Set Price.
	  * Price
	  */
	public void setPrice (BigDecimal Price);

	/** Get Price.
	  * Price
	  */
	public BigDecimal getPrice();

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Quantity.
	  * Quantity
	  */
	public void setQty (BigDecimal Qty);

	/** Get Quantity.
	  * Quantity
	  */
	public BigDecimal getQty();

    /** Column name REMARKS */
    public static final String COLUMNNAME_REMARKS = "REMARKS";

	/** Set REMARKS	  */
	public void setREMARKS (String REMARKS);

	/** Get REMARKS	  */
	public String getREMARKS();

    /** Column name SUPPLY */
    public static final String COLUMNNAME_SUPPLY = "SUPPLY";

	/** Set SUPPLY	  */
	public void setSUPPLY (BigDecimal SUPPLY);

	/** Get SUPPLY	  */
	public BigDecimal getSUPPLY();

    /** Column name TOTALLINE */
    public static final String COLUMNNAME_TOTALLINE = "TOTALLINE";

	/** Set TOTALLINE	  */
	public void setTOTALLINE (BigDecimal TOTALLINE);

	/** Get TOTALLINE	  */
	public BigDecimal getTOTALLINE();

    /** Column name Type */
    public static final String COLUMNNAME_Type = "Type";

	/** Set Type.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
	public void setType (String Type);

	/** Get Type.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
	public String getType();

	/** Column name ISOD */
    public static final String COLUMNNAME_ISOD = "ISOD";
    
    /** Set ISOD.
	  * Type of Validation (SQL, Java Script, Java Language)
	  */
    public void setISOD (boolean ISOD);

	/** Get ISOD.
	  * IS OD ITEM
	  */
	public boolean ISOD();


    
	
    /** Column name UNIT */
    public static final String COLUMNNAME_UNIT = "UNIT";

	/** Set UNIT	  */
	public void setUNIT (String UNIT);

	/** Get UNIT	  */
	public String getUNIT();
	
	/** Column name BUNIT */
    public static final String COLUMNNAME_BUNIT = "BUNIT";

	/** Set BUNIT	  */
	public void setBUNIT (String BUNIT);

	/** Get BUNIT	  */
	public String getBUNIT();
	
	/** Column name SETBUNIT */
    public static final String COLUMNNAME_SETBUNIT = "SETBUNIT";

	/** Set SETBUNIT.  */
	public void setSETBUNIT (boolean SETBUNIT);

	/** Get SETBUNIT  */
	public boolean getSETBUNIT();

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

    /** Column name WAYS */
    public static final String COLUMNNAME_WAYS = "WAYS";

	/** Set WAYS	  */
	public void setWAYS (String WAYS);

	/** Get WAYS	  */
	public String getWAYS();
	
	/** Column name AIRWAY */
    public static final String COLUMNNAME_AIRWAY = "AIRWAY";
	/** Set AIRWAY	  */
	public void setAIRWAY (String AIRWAY);
	/** Get AIRWAY	  */
	public String getAIRWAY();
	
}
