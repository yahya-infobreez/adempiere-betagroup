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

/** Generated Interface for C_ProjectReceiptLine
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS
 */
public interface I_C_ProjectReceiptLine 
{

    /** TableName=C_ProjectReceiptLine */
    public static final String Table_Name = "C_ProjectReceiptLine";

    /** AD_Table_ID=1000001 */
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

    /** Column name C_ProjectLine_ID */
    public static final String COLUMNNAME_C_ProjectLine_ID = "C_ProjectLine_ID";

	/** Set Project Line.
	  * Task or step in a project
	  */
	public void setC_ProjectLine_ID (int C_ProjectLine_ID);

	/** Get Project Line.
	  * Task or step in a project
	  */
	public int getC_ProjectLine_ID();

	public I_C_ProjectLine getC_ProjectLine() throws RuntimeException;

    /** Column name C_ProjectReceiptLine_ID */
    public static final String COLUMNNAME_C_ProjectReceiptLine_ID = "C_ProjectReceiptLine_ID";

	/** Set C_ProjectReceiptLine	  */
	public void setC_ProjectReceiptLine_ID (int C_ProjectReceiptLine_ID);

	/** Get C_ProjectReceiptLine	  */
	public int getC_ProjectReceiptLine_ID();

    /** Column name C_ProjectReceipt_ID */
    public static final String COLUMNNAME_C_ProjectReceipt_ID = "C_ProjectReceipt_ID";

	/** Set Project Receipt	  */
	public void setC_ProjectReceipt_ID (int C_ProjectReceipt_ID);

	/** Get Project Receipt	  */
	public int getC_ProjectReceipt_ID();

	public I_C_ProjectReceipt getC_ProjectReceipt() throws RuntimeException;

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

    /** Column name PlannedPrice */
    public static final String COLUMNNAME_PlannedPrice = "PlannedPrice";

	/** Set Planned Price.
	  * Planned price for this project line
	  */
	public void setPlannedPrice (BigDecimal PlannedPrice);

	/** Get Planned Price.
	  * Planned price for this project line
	  */
	public BigDecimal getPlannedPrice();

    /** Column name QTYRECEIVED */
    public static final String COLUMNNAME_QTYRECEIVED = "QTYRECEIVED";

	/** Set QTYRECEIVED	  */
	public void setQTYRECEIVED (BigDecimal QTYRECEIVED);

	/** Get QTYRECEIVED	  */
	public BigDecimal getQTYRECEIVED();

    /** Column name TOTALLINE */
    public static final String COLUMNNAME_TOTALLINE = "TOTALLINE";

	/** Set TOTALLINE	  */
	public void setTOTALLINE (BigDecimal TOTALLINE);

	/** Get TOTALLINE	  */
	public BigDecimal getTOTALLINE();

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
}
