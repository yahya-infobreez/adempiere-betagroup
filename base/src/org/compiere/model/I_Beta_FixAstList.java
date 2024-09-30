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

/** Generated Interface for Beta_FixAstList
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS
 */
public interface I_Beta_FixAstList 
{

    /** TableName=Beta_FixAstList */
    public static final String Table_Name = "Beta_FixAstList";

    /** AD_Table_ID=1000014 */
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

    /** Column name FIXAST_BAL */
    public static final String COLUMNNAME_FIXAST_BAL = "FIXAST_BAL";

	/** Set FIXAST_BAL	  */
	public void setFIXAST_BAL (BigDecimal FIXAST_BAL);

	/** Get FIXAST_BAL	  */
	public BigDecimal getFIXAST_BAL();

    /** Column name FIXAST_COST */
    public static final String COLUMNNAME_FIXAST_COST = "FIXAST_COST";

	/** Set FIXAST_COST	  */
	public void setFIXAST_COST (BigDecimal FIXAST_COST);

	/** Get FIXAST_COST	  */
	public BigDecimal getFIXAST_COST();

    /** Column name FIXAST_DESC */
    public static final String COLUMNNAME_FIXAST_DESC = "FIXAST_DESC";

	/** Set FIXAST_DESC	  */
	public void setFIXAST_DESC (String FIXAST_DESC);

	/** Get FIXAST_DESC	  */
	public String getFIXAST_DESC();

    /** Column name FIXAST_MONOPDEP */
    public static final String COLUMNNAME_FIXAST_MONOPDEP = "FIXAST_MONOPDEP";

	/** Set FIXAST_MONOPDEP	  */
	public void setFIXAST_MONOPDEP (BigDecimal FIXAST_MONOPDEP);

	/** Get FIXAST_MONOPDEP	  */
	public BigDecimal getFIXAST_MONOPDEP();

    /** Column name FIXAST_MONTHDEPAMT */
    public static final String COLUMNNAME_FIXAST_MONTHDEPAMT = "FIXAST_MONTHDEPAMT";

	/** Set FIXAST_MONTHDEPAMT	  */
	public void setFIXAST_MONTHDEPAMT (BigDecimal FIXAST_MONTHDEPAMT);

	/** Get FIXAST_MONTHDEPAMT	  */
	public BigDecimal getFIXAST_MONTHDEPAMT();

    /** Column name FIXAST_QTY */
    public static final String COLUMNNAME_FIXAST_QTY = "FIXAST_QTY";

	/** Set FIXAST_QTY	  */
	public void setFIXAST_QTY (BigDecimal FIXAST_QTY);

	/** Get FIXAST_QTY	  */
	public BigDecimal getFIXAST_QTY();

    /** Column name FIXAST_TOTDEPAMT */
    public static final String COLUMNNAME_FIXAST_TOTDEPAMT = "FIXAST_TOTDEPAMT";

	/** Set FIXAST_TOTDEPAMT	  */
	public void setFIXAST_TOTDEPAMT (BigDecimal FIXAST_TOTDEPAMT);

	/** Get FIXAST_TOTDEPAMT	  */
	public BigDecimal getFIXAST_TOTDEPAMT();

    /** Column name FIXAST_TYPE */
    public static final String COLUMNNAME_FIXAST_TYPE = "FIXAST_TYPE";

	/** Set FIXAST_TYPE	  */
	public void setFIXAST_TYPE (String FIXAST_TYPE);

	/** Get FIXAST_TYPE	  */
	public String getFIXAST_TYPE();

    /** Column name FIXAST_VALUE */
    public static final String COLUMNNAME_FIXAST_VALUE = "FIXAST_VALUE";

	/** Set FIXAST_VALUE	  */
	public void setFIXAST_VALUE (String FIXAST_VALUE);

	/** Get FIXAST_VALUE	  */
	public String getFIXAST_VALUE();

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
