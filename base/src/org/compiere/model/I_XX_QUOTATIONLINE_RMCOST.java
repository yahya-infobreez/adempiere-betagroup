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

/** Generated Interface for XX_QUOTATIONLINE_RMCOST
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS
 */
public interface I_XX_QUOTATIONLINE_RMCOST 
{

    /** TableName=XX_QUOTATIONLINE_RMCOST */
    public static final String Table_Name = "XX_QUOTATIONLINE_RMCOST";

    /** AD_Table_ID=1000027 */
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

    /** Column name COST_FACTOR */
    public static final String COLUMNNAME_COST_FACTOR = "COST_FACTOR";

	/** Set Cost Factor	  */
	public void setCOST_FACTOR (BigDecimal COST_FACTOR);

	/** Get Cost Factor	  */
	public BigDecimal getCOST_FACTOR();

    /** Column name C_QUOTATIONLINE_ID */
    public static final String COLUMNNAME_C_QUOTATIONLINE_ID = "C_QUOTATIONLINE_ID";

	/** Set Quotation Line	  */
	public void setC_QUOTATIONLINE_ID (int C_QUOTATIONLINE_ID);

	/** Get Quotation Line	  */
	public int getC_QUOTATIONLINE_ID();

	public I_C_QUOTATIONLINE getC_QUOTATIONLINE() throws RuntimeException;

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

    /** Column name RM_AMOUNT */
    public static final String COLUMNNAME_RM_AMOUNT = "RM_AMOUNT";

	/** Set RM_AMOUNT	  */
	public void setRM_AMOUNT (BigDecimal RM_AMOUNT);

	/** Get RM_AMOUNT	  */
	public BigDecimal getRM_AMOUNT();

    /** Column name RM_PRODUCT_ID */
    public static final String COLUMNNAME_RM_PRODUCT_ID = "RM_PRODUCT_ID";

	/** Set RM_PRODUCT_ID	  */
	public void setRM_PRODUCT_ID (int RM_PRODUCT_ID);

	/** Get RM_PRODUCT_ID	  */
	public int getRM_PRODUCT_ID();

	public I_M_Product getRM_PRODUCT() throws RuntimeException;

    /** Column name RM_QUANTITY */
    public static final String COLUMNNAME_RM_QUANTITY = "RM_QUANTITY";

	/** Set RM_QUANTITY	  */
	public void setRM_QUANTITY (BigDecimal RM_QUANTITY);

	/** Get RM_QUANTITY	  */
	public BigDecimal getRM_QUANTITY();

    /** Column name STDCOST */
    public static final String COLUMNNAME_STDCOST = "STDCOST";

	/** Set Standard Cost	  */
	public void setSTDCOST (BigDecimal STDCOST);

	/** Get Standard Cost	  */
	public BigDecimal getSTDCOST();

    /** Column name UNITCOST */
    public static final String COLUMNNAME_UNITCOST = "UNITCOST";

	/** Set Unit Cost	  */
	public void setUNITCOST (BigDecimal UNITCOST);

	/** Get Unit Cost	  */
	public BigDecimal getUNITCOST();

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

    /** Column name XX_QUOTATIONLINE_RMCOST_ID */
    public static final String COLUMNNAME_XX_QUOTATIONLINE_RMCOST_ID = "XX_QUOTATIONLINE_RMCOST_ID";

	/** Set Quotation Line BOM	  */
	public void setXX_QUOTATIONLINE_RMCOST_ID (int XX_QUOTATIONLINE_RMCOST_ID);

	/** Get Quotation Line BOM	  */
	public int getXX_QUOTATIONLINE_RMCOST_ID();
}
