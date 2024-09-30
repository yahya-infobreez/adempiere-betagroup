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

/** Generated Interface for M_Product_BOM
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS
 */
public interface I_M_Product_BOM 
{

    /** TableName=M_Product_BOM */
    public static final String Table_Name = "M_Product_BOM";

    /** AD_Table_ID=383 */
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

    /** Column name BOMQty */
    public static final String COLUMNNAME_BOMQty = "BOMQty";

	/** Set BOM Quantity.
	  * Bill of Materials Quantity
	  */
	public void setBOMQty (BigDecimal BOMQty);

	/** Get BOM Quantity.
	  * Bill of Materials Quantity
	  */
	public BigDecimal getBOMQty();

    /** Column name BOMType */
    public static final String COLUMNNAME_BOMType = "BOMType";

	/** Set BOM Type.
	  * Type of BOM
	  */
	public void setBOMType (String BOMType);

	/** Get BOM Type.
	  * Type of BOM
	  */
	public String getBOMType();

    /** Column name COSTFACTOR_CELL_ADDR */
    public static final String COLUMNNAME_COSTFACTOR_CELL_ADDR = "COSTFACTOR_CELL_ADDR";

	/** Set Cost Factor Cell From	  */
	public void setCOSTFACTOR_CELL_ADDR (String COSTFACTOR_CELL_ADDR);

	/** Get Cost Factor Cell From	  */
	public String getCOSTFACTOR_CELL_ADDR();

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

    /** Column name FROM_RMPROD_CELL_ADDR */
    public static final String COLUMNNAME_FROM_RMPROD_CELL_ADDR = "FROM_RMPROD_CELL_ADDR";

	/** Set Raw Mat Cell From	  */
	public void setFROM_RMPROD_CELL_ADDR (String FROM_RMPROD_CELL_ADDR);

	/** Get Raw Mat Cell From	  */
	public String getFROM_RMPROD_CELL_ADDR();

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

    /** Column name M_ProductBOM_ID */
    public static final String COLUMNNAME_M_ProductBOM_ID = "M_ProductBOM_ID";

	/** Set BOM Product.
	  * Bill of Material Component Product
	  */
	public void setM_ProductBOM_ID (int M_ProductBOM_ID);

	/** Get BOM Product.
	  * Bill of Material Component Product
	  */
	public int getM_ProductBOM_ID();

	public I_M_Product getM_ProductBOM() throws RuntimeException;

    /** Column name M_Product_BOM_ID */
    public static final String COLUMNNAME_M_Product_BOM_ID = "M_Product_BOM_ID";

	/** Set BOM Line	  */
	public void setM_Product_BOM_ID (int M_Product_BOM_ID);

	/** Get BOM Line	  */
	public int getM_Product_BOM_ID();

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

    /** Column name QTY_CELL_ADDR */
    public static final String COLUMNNAME_QTY_CELL_ADDR = "QTY_CELL_ADDR";

	/** Set Qty Cell From	  */
	public void setQTY_CELL_ADDR (String QTY_CELL_ADDR);

	/** Get Qty Cell From	  */
	public String getQTY_CELL_ADDR();

    /** Column name RMCOST_CELL_ADDR */
    public static final String COLUMNNAME_RMCOST_CELL_ADDR = "RMCOST_CELL_ADDR";

	/** Set Total Cost Cell From	  */
	public void setRMCOST_CELL_ADDR (String RMCOST_CELL_ADDR);

	/** Get Total Cost Cell From	  */
	public String getRMCOST_CELL_ADDR();

    /** Column name STDCOST_CELL_ADDR */
    public static final String COLUMNNAME_STDCOST_CELL_ADDR = "STDCOST_CELL_ADDR";

	/** Set Std Cost Cell From	  */
	public void setSTDCOST_CELL_ADDR (String STDCOST_CELL_ADDR);

	/** Get Std Cost Cell From	  */
	public String getSTDCOST_CELL_ADDR();

    /** Column name TO_COSTFACTOR_CELL_ADDR */
    public static final String COLUMNNAME_TO_COSTFACTOR_CELL_ADDR = "TO_COSTFACTOR_CELL_ADDR";

	/** Set Cost Factor Cell To	  */
	public void setTO_COSTFACTOR_CELL_ADDR (String TO_COSTFACTOR_CELL_ADDR);

	/** Get Cost Factor Cell To	  */
	public String getTO_COSTFACTOR_CELL_ADDR();

    /** Column name TO_QTY_CELL_ADDR */
    public static final String COLUMNNAME_TO_QTY_CELL_ADDR = "TO_QTY_CELL_ADDR";

	/** Set Qty Cell To	  */
	public void setTO_QTY_CELL_ADDR (String TO_QTY_CELL_ADDR);

	/** Get Qty Cell To	  */
	public String getTO_QTY_CELL_ADDR();

    /** Column name TO_RMCOST_CELL_ADDR */
    public static final String COLUMNNAME_TO_RMCOST_CELL_ADDR = "TO_RMCOST_CELL_ADDR";

	/** Set Total RM Cost To	  */
	public void setTO_RMCOST_CELL_ADDR (String TO_RMCOST_CELL_ADDR);

	/** Get Total RM Cost To	  */
	public String getTO_RMCOST_CELL_ADDR();

    /** Column name TO_RMPROD_CELL_ADDR */
    public static final String COLUMNNAME_TO_RMPROD_CELL_ADDR = "TO_RMPROD_CELL_ADDR";

	/** Set Raw Mat Cell To	  */
	public void setTO_RMPROD_CELL_ADDR (String TO_RMPROD_CELL_ADDR);

	/** Get Raw Mat Cell To	  */
	public String getTO_RMPROD_CELL_ADDR();

    /** Column name TO_STDCOST_CELL_ADDR */
    public static final String COLUMNNAME_TO_STDCOST_CELL_ADDR = "TO_STDCOST_CELL_ADDR";

	/** Set Std Cost Cell To	  */
	public void setTO_STDCOST_CELL_ADDR (String TO_STDCOST_CELL_ADDR);

	/** Get Std Cost Cell To	  */
	public String getTO_STDCOST_CELL_ADDR();

    /** Column name TO_UNITCOST_CELL_ADDR */
    public static final String COLUMNNAME_TO_UNITCOST_CELL_ADDR = "TO_UNITCOST_CELL_ADDR";

	/** Set Unit Cost Cell To	  */
	public void setTO_UNITCOST_CELL_ADDR (String TO_UNITCOST_CELL_ADDR);

	/** Get Unit Cost Cell To	  */
	public String getTO_UNITCOST_CELL_ADDR();

    /** Column name UNITCOST_CELL_ADDR */
    public static final String COLUMNNAME_UNITCOST_CELL_ADDR = "UNITCOST_CELL_ADDR";

	/** Set Unit Cost Cell From	  */
	public void setUNITCOST_CELL_ADDR (String UNITCOST_CELL_ADDR);

	/** Get Unit Cost Cell From	  */
	public String getUNITCOST_CELL_ADDR();

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
