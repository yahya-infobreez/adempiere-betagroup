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

/** Generated Interface for Beta_Doors
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS
 */
public interface I_Beta_Doors 
{

    /** TableName=Beta_Doors */
    public static final String Table_Name = "Beta_Doors";

    /** AD_Table_ID=1000033 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

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

    /** Column name Beta_Doors_ID */
    public static final String COLUMNNAME_Beta_Doors_ID = "Beta_Doors_ID";

	/** Set Beta Doors Table (Fadi Table)	  */
	public void setBeta_Doors_ID (int Beta_Doors_ID);

	/** Get Beta Doors Table (Fadi Table)	  */
	public int getBeta_Doors_ID();

    /** Column name CLOSER */
    public static final String COLUMNNAME_CLOSER = "CLOSER";

	/** Set CLOSER	  */
	public void setCLOSER (BigDecimal CLOSER);

	/** Get CLOSER	  */
	public BigDecimal getCLOSER();

    /** Column name CODE1 */
    public static final String COLUMNNAME_CODE1 = "CODE1";

	/** Set CODE1	  */
	public void setCODE1 (String CODE1);

	/** Get CODE1	  */
	public String getCODE1();

    /** Column name CODEA */
    public static final String COLUMNNAME_CODEA = "CODEA";

	/** Set CODEA	  */
	public void setCODEA (String CODEA);

	/** Get CODEA	  */
	public String getCODEA();

    /** Column name CODEB */
    public static final String COLUMNNAME_CODEB = "CODEB";

	/** Set CODEB	  */
	public void setCODEB (String CODEB);

	/** Get CODEB	  */
	public String getCODEB();

    /** Column name CODEC */
    public static final String COLUMNNAME_CODEC = "CODEC";

	/** Set CODEC	  */
	public void setCODEC (String CODEC);

	/** Get CODEC	  */
	public String getCODEC();

    /** Column name CODED */
    public static final String COLUMNNAME_CODED = "CODED";

	/** Set CODED	  */
	public void setCODED (String CODED);

	/** Get CODED	  */
	public String getCODED();

    /** Column name CODEE */
    public static final String COLUMNNAME_CODEE = "CODEE";

	/** Set CODEE	  */
	public void setCODEE (String CODEE);

	/** Get CODEE	  */
	public String getCODEE();

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

    /** Column name DEAD */
    public static final String COLUMNNAME_DEAD = "DEAD";

	/** Set DEAD	  */
	public void setDEAD (BigDecimal DEAD);

	/** Get DEAD	  */
	public BigDecimal getDEAD();

    /** Column name DEST1 */
    public static final String COLUMNNAME_DEST1 = "DEST1";

	/** Set DEST1	  */
	public void setDEST1 (String DEST1);

	/** Get DEST1	  */
	public String getDEST1();

    /** Column name DEST2 */
    public static final String COLUMNNAME_DEST2 = "DEST2";

	/** Set DEST2	  */
	public void setDEST2 (String DEST2);

	/** Get DEST2	  */
	public String getDEST2();

    /** Column name DOORNO */
    public static final String COLUMNNAME_DOORNO = "DOORNO";

	/** Set DOORNO	  */
	public void setDOORNO (String DOORNO);

	/** Get DOORNO	  */
	public String getDOORNO();

    /** Column name DOORUC */
    public static final String COLUMNNAME_DOORUC = "DOORUC";

	/** Set DOORUC	  */
	public void setDOORUC (BigDecimal DOORUC);

	/** Get DOORUC	  */
	public BigDecimal getDOORUC();

    /** Column name Discount */
    public static final String COLUMNNAME_Discount = "Discount";

	/** Set Discount %.
	  * Discount in percent
	  */
	public void setDiscount (BigDecimal Discount);

	/** Get Discount %.
	  * Discount in percent
	  */
	public BigDecimal getDiscount();

    /** Column name EMBEDDING */
    public static final String COLUMNNAME_EMBEDDING = "EMBEDDING";

	/** Set EMBEDDING	  */
	public void setEMBEDDING (BigDecimal EMBEDDING);

	/** Get EMBEDDING	  */
	public BigDecimal getEMBEDDING();

    /** Column name EXPANSION */
    public static final String COLUMNNAME_EXPANSION = "EXPANSION";

	/** Set EXPANSION	  */
	public void setEXPANSION (BigDecimal EXPANSION);

	/** Get EXPANSION	  */
	public BigDecimal getEXPANSION();

    /** Column name FL */
    public static final String COLUMNNAME_FL = "FL";

	/** Set FL	  */
	public void setFL (BigDecimal FL);

	/** Get FL	  */
	public BigDecimal getFL();

    /** Column name FLCOST */
    public static final String COLUMNNAME_FLCOST = "FLCOST";

	/** Set FLCOST	  */
	public void setFLCOST (BigDecimal FLCOST);

	/** Get FLCOST	  */
	public BigDecimal getFLCOST();

    /** Column name FLUSH */
    public static final String COLUMNNAME_FLUSH = "FLUSH";

	/** Set FLUSH	  */
	public void setFLUSH (BigDecimal FLUSH);

	/** Get FLUSH	  */
	public BigDecimal getFLUSH();

    /** Column name FOAMCOST */
    public static final String COLUMNNAME_FOAMCOST = "FOAMCOST";

	/** Set FOAMCOST	  */
	public void setFOAMCOST (BigDecimal FOAMCOST);

	/** Get FOAMCOST	  */
	public BigDecimal getFOAMCOST();

    /** Column name FOS */
    public static final String COLUMNNAME_FOS = "FOS";

	/** Set FOS	  */
	public void setFOS (BigDecimal FOS);

	/** Get FOS	  */
	public BigDecimal getFOS();

    /** Column name FRNFR */
    public static final String COLUMNNAME_FRNFR = "FRNFR";

	/** Set FRNFR	  */
	public void setFRNFR (String FRNFR);

	/** Get FRNFR	  */
	public String getFRNFR();

    /** Column name HEIGHT */
    public static final String COLUMNNAME_HEIGHT = "HEIGHT";

	/** Set HEIGHT	  */
	public void setHEIGHT (BigDecimal HEIGHT);

	/** Get HEIGHT	  */
	public BigDecimal getHEIGHT();

    /** Column name HINGECOVER */
    public static final String COLUMNNAME_HINGECOVER = "HINGECOVER";

	/** Set HINGECOVER	  */
	public void setHINGECOVER (BigDecimal HINGECOVER);

	/** Get HINGECOVER	  */
	public BigDecimal getHINGECOVER();

    /** Column name HINGES */
    public static final String COLUMNNAME_HINGES = "HINGES";

	/** Set HINGES	  */
	public void setHINGES (BigDecimal HINGES);

	/** Get HINGES	  */
	public BigDecimal getHINGES();

    /** Column name HINGP */
    public static final String COLUMNNAME_HINGP = "HINGP";

	/** Set HINGP	  */
	public void setHINGP (BigDecimal HINGP);

	/** Get HINGP	  */
	public BigDecimal getHINGP();

    /** Column name INDICATOR */
    public static final String COLUMNNAME_INDICATOR = "INDICATOR";

	/** Set INDICATOR	  */
	public void setINDICATOR (BigDecimal INDICATOR);

	/** Get INDICATOR	  */
	public BigDecimal getINDICATOR();

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

    /** Column name JAMB */
    public static final String COLUMNNAME_JAMB = "JAMB";

	/** Set JAMB	  */
	public void setJAMB (BigDecimal JAMB);

	/** Get JAMB	  */
	public BigDecimal getJAMB();

    /** Column name JAMBREL */
    public static final String COLUMNNAME_JAMBREL = "JAMBREL";

	/** Set JAMBREL	  */
	public void setJAMBREL (BigDecimal JAMBREL);

	/** Get JAMBREL	  */
	public BigDecimal getJAMBREL();

    /** Column name JAMBTYPE */
    public static final String COLUMNNAME_JAMBTYPE = "JAMBTYPE";

	/** Set JAMBTYPE	  */
	public void setJAMBTYPE (String JAMBTYPE);

	/** Get JAMBTYPE	  */
	public String getJAMBTYPE();

    /** Column name LH */
    public static final String COLUMNNAME_LH = "LH";

	/** Set LH	  */
	public void setLH (BigDecimal LH);

	/** Get LH	  */
	public BigDecimal getLH();

    /** Column name LHA */
    public static final String COLUMNNAME_LHA = "LHA";

	/** Set LHA	  */
	public void setLHA (BigDecimal LHA);

	/** Get LHA	  */
	public BigDecimal getLHA();

    /** Column name LHR */
    public static final String COLUMNNAME_LHR = "LHR";

	/** Set LHR	  */
	public void setLHR (BigDecimal LHR);

	/** Get LHR	  */
	public BigDecimal getLHR();

    /** Column name LHRA */
    public static final String COLUMNNAME_LHRA = "LHRA";

	/** Set LHRA	  */
	public void setLHRA (BigDecimal LHRA);

	/** Get LHRA	  */
	public BigDecimal getLHRA();

    /** Column name LOCATION */
    public static final String COLUMNNAME_LOCATION = "LOCATION";

	/** Set LOCATION	  */
	public void setLOCATION (String LOCATION);

	/** Get LOCATION	  */
	public String getLOCATION();

    /** Column name LOCKCOVERC */
    public static final String COLUMNNAME_LOCKCOVERC = "LOCKCOVERC";

	/** Set LOCKCOVERC	  */
	public void setLOCKCOVERC (BigDecimal LOCKCOVERC);

	/** Get LOCKCOVERC	  */
	public BigDecimal getLOCKCOVERC();

    /** Column name LSHAPECOST */
    public static final String COLUMNNAME_LSHAPECOST = "LSHAPECOST";

	/** Set LSHAPECOST	  */
	public void setLSHAPECOST (BigDecimal LSHAPECOST);

	/** Get LSHAPECOST	  */
	public BigDecimal getLSHAPECOST();

    /** Column name LV */
    public static final String COLUMNNAME_LV = "LV";

	/** Set LV	  */
	public void setLV (BigDecimal LV);

	/** Get LV	  */
	public BigDecimal getLV();

    /** Column name LVCOST */
    public static final String COLUMNNAME_LVCOST = "LVCOST";

	/** Set LVCOST	  */
	public void setLVCOST (BigDecimal LVCOST);

	/** Get LVCOST	  */
	public BigDecimal getLVCOST();

    /** Column name MORTISE */
    public static final String COLUMNNAME_MORTISE = "MORTISE";

	/** Set MORTISE	  */
	public void setMORTISE (BigDecimal MORTISE);

	/** Get MORTISE	  */
	public BigDecimal getMORTISE();

    /** Column name NO */
    public static final String COLUMNNAME_NO = "NO";

	/** Set NO	  */
	public void setNO (BigDecimal NO);

	/** Get NO	  */
	public BigDecimal getNO();

    /** Column name NOTES */
    public static final String COLUMNNAME_NOTES = "NOTES";

	/** Set NOTES	  */
	public void setNOTES (String NOTES);

	/** Get NOTES	  */
	public String getNOTES();

    /** Column name PANIC */
    public static final String COLUMNNAME_PANIC = "PANIC";

	/** Set PANIC	  */
	public void setPANIC (BigDecimal PANIC);

	/** Get PANIC	  */
	public BigDecimal getPANIC();

    /** Column name PANICV */
    public static final String COLUMNNAME_PANICV = "PANICV";

	/** Set PANICV	  */
	public void setPANICV (BigDecimal PANICV);

	/** Get PANICV	  */
	public BigDecimal getPANICV();

    /** Column name POWDER */
    public static final String COLUMNNAME_POWDER = "POWDER";

	/** Set POWDER	  */
	public void setPOWDER (String POWDER);

	/** Get POWDER	  */
	public String getPOWDER();

    /** Column name POWDERCOST */
    public static final String COLUMNNAME_POWDERCOST = "POWDERCOST";

	/** Set POWDERCOST	  */
	public void setPOWDERCOST (BigDecimal POWDERCOST);

	/** Get POWDERCOST	  */
	public BigDecimal getPOWDERCOST();

    /** Column name PP */
    public static final String COLUMNNAME_PP = "PP";

	/** Set PP	  */
	public void setPP (BigDecimal PP);

	/** Get PP	  */
	public BigDecimal getPP();

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

    /** Column name RH */
    public static final String COLUMNNAME_RH = "RH";

	/** Set RH	  */
	public void setRH (BigDecimal RH);

	/** Get RH	  */
	public BigDecimal getRH();

    /** Column name RHA */
    public static final String COLUMNNAME_RHA = "RHA";

	/** Set RHA	  */
	public void setRHA (BigDecimal RHA);

	/** Get RHA	  */
	public BigDecimal getRHA();

    /** Column name RHR */
    public static final String COLUMNNAME_RHR = "RHR";

	/** Set RHR	  */
	public void setRHR (BigDecimal RHR);

	/** Get RHR	  */
	public BigDecimal getRHR();

    /** Column name RHRA */
    public static final String COLUMNNAME_RHRA = "RHRA";

	/** Set RHRA	  */
	public void setRHRA (BigDecimal RHRA);

	/** Get RHRA	  */
	public BigDecimal getRHRA();

    /** Column name ROCKCOST */
    public static final String COLUMNNAME_ROCKCOST = "ROCKCOST";

	/** Set ROCKCOST	  */
	public void setROCKCOST (BigDecimal ROCKCOST);

	/** Get ROCKCOST	  */
	public BigDecimal getROCKCOST();

    /** Column name SDDD */
    public static final String COLUMNNAME_SDDD = "SDDD";

	/** Set SDDD	  */
	public void setSDDD (String SDDD);

	/** Get SDDD	  */
	public String getSDDD();

    /** Column name SHOP */
    public static final String COLUMNNAME_SHOP = "SHOP";

	/** Set SHOP	  */
	public void setSHOP (BigDecimal SHOP);

	/** Get SHOP	  */
	public BigDecimal getSHOP();

    /** Column name SILENCER */
    public static final String COLUMNNAME_SILENCER = "SILENCER";

	/** Set SILENCER	  */
	public void setSILENCER (BigDecimal SILENCER);

	/** Get SILENCER	  */
	public BigDecimal getSILENCER();

    /** Column name SL */
    public static final String COLUMNNAME_SL = "SL";

	/** Set SL	  */
	public void setSL (BigDecimal SL);

	/** Get SL	  */
	public BigDecimal getSL();

    /** Column name STANDARD */
    public static final String COLUMNNAME_STANDARD = "STANDARD";

	/** Set STANDARD	  */
	public void setSTANDARD (String STANDARD);

	/** Get STANDARD	  */
	public String getSTANDARD();

    /** Column name STOPER */
    public static final String COLUMNNAME_STOPER = "STOPER";

	/** Set STOPER	  */
	public void setSTOPER (BigDecimal STOPER);

	/** Get STOPER	  */
	public BigDecimal getSTOPER();

    /** Column name T */
    public static final String COLUMNNAME_T = "T";

	/** Set T	  */
	public void setT (BigDecimal T);

	/** Get T	  */
	public BigDecimal getT();

    /** Column name THFRAME */
    public static final String COLUMNNAME_THFRAME = "THFRAME";

	/** Set THFRAME	  */
	public void setTHFRAME (BigDecimal THFRAME);

	/** Get THFRAME	  */
	public BigDecimal getTHFRAME();

    /** Column name THLEAF */
    public static final String COLUMNNAME_THLEAF = "THLEAF";

	/** Set THLEAF	  */
	public void setTHLEAF (BigDecimal THLEAF);

	/** Get THLEAF	  */
	public BigDecimal getTHLEAF();

    /** Column name TONNAGE */
    public static final String COLUMNNAME_TONNAGE = "TONNAGE";

	/** Set TONNAGE	  */
	public void setTONNAGE (BigDecimal TONNAGE);

	/** Get TONNAGE	  */
	public BigDecimal getTONNAGE();

    /** Column name TOTALFRAME */
    public static final String COLUMNNAME_TOTALFRAME = "TOTALFRAME";

	/** Set TOTALFRAME	  */
	public void setTOTALFRAME (BigDecimal TOTALFRAME);

	/** Get TOTALFRAME	  */
	public BigDecimal getTOTALFRAME();

    /** Column name TOTALHARD */
    public static final String COLUMNNAME_TOTALHARD = "TOTALHARD";

	/** Set TOTALHARD	  */
	public void setTOTALHARD (BigDecimal TOTALHARD);

	/** Get TOTALHARD	  */
	public BigDecimal getTOTALHARD();

    /** Column name TOTALLEAF */
    public static final String COLUMNNAME_TOTALLEAF = "TOTALLEAF";

	/** Set TOTALLEAF	  */
	public void setTOTALLEAF (BigDecimal TOTALLEAF);

	/** Get TOTALLEAF	  */
	public BigDecimal getTOTALLEAF();

    /** Column name UPFRAME */
    public static final String COLUMNNAME_UPFRAME = "UPFRAME";

	/** Set UPFRAME	  */
	public void setUPFRAME (BigDecimal UPFRAME);

	/** Get UPFRAME	  */
	public BigDecimal getUPFRAME();

    /** Column name UPLEAF */
    public static final String COLUMNNAME_UPLEAF = "UPLEAF";

	/** Set UPLEAF	  */
	public void setUPLEAF (BigDecimal UPLEAF);

	/** Get UPLEAF	  */
	public BigDecimal getUPLEAF();

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

    /** Column name VP */
    public static final String COLUMNNAME_VP = "VP";

	/** Set VP	  */
	public void setVP (BigDecimal VP);

	/** Get VP	  */
	public BigDecimal getVP();

    /** Column name VPCOST */
    public static final String COLUMNNAME_VPCOST = "VPCOST";

	/** Set VPCOST	  */
	public void setVPCOST (BigDecimal VPCOST);

	/** Get VPCOST	  */
	public BigDecimal getVPCOST();

    /** Column name WIDTH */
    public static final String COLUMNNAME_WIDTH = "WIDTH";

	/** Set WIDTH	  */
	public void setWIDTH (BigDecimal WIDTH);

	/** Get WIDTH	  */
	public BigDecimal getWIDTH();
}
