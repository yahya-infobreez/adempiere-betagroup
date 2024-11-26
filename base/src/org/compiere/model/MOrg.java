/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	Organization Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MOrg.java,v 1.3 2006/07/30 00:58:04 jjanke Exp $
 */
public class MOrg extends X_AD_Org
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5604686137606338725L;


	/**
	 * 	Get Active Organizations Of Client
	 *	@param po persistent object
	 *	@return array of orgs
	 */
	public static MOrg[] getOfClient (PO po)
	{
		List<MOrg> list = new Query(po.getCtx(), Table_Name, "AD_Client_ID=?", null)
								.setOrderBy(COLUMNNAME_Value)
								.setOnlyActiveRecords(true)
								.setParameters(po.getAD_Client_ID())
								.list();
		for (MOrg org : list)
		{
			s_cache.put(org.get_ID(), org);
		}
		return list.toArray(new MOrg[list.size()]);
	}	//	getOfClient
	
	/**
	 * 	Get Org from Cache
	 *	@param ctx context
	 *	@param AD_Org_ID id
	 *	@return MOrg
	 */
	public static MOrg get (Properties ctx, int AD_Org_ID)
	{
		MOrg retValue = s_cache.get (AD_Org_ID);
		if (retValue != null)
			return retValue;
		retValue = new MOrg (ctx, AD_Org_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (AD_Org_ID, retValue);
		return retValue;
	}	//	get

	/**	Cache						*/
	private static CCache<Integer,MOrg>	s_cache	= new CCache<Integer,MOrg>(Table_Name, 50);
	
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_Org_ID id
	 *	@param trxName transaction
	 */
	public MOrg (Properties ctx, int AD_Org_ID, String trxName)
	{
		super(ctx, AD_Org_ID, trxName);
		if (AD_Org_ID == 0)
		{
		//	setValue (null);
		//	setName (null);
			setIsSummary (false);
		}
	}	//	MOrg

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MOrg (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MOrg

	/**
	 * 	Parent Constructor
	 *	@param client client
	 *	@param name name
	 */
	public MOrg (MClient client, String value, String name)
	{
		this (client.getCtx(), 0, client.get_TrxName());
		setAD_Client_ID (client.getAD_Client_ID());
		setValue (value);
		setName (name);
	}	//	MOrg

	/**	Linked Business Partner			*/
	private Integer 	m_linkedBPartner = null;

	/**
	 *	Get Org Info
	 *	@return Org Info
	 */
	public MOrgInfo getInfo()
	{
		return MOrgInfo.get(getCtx(), getAD_Org_ID(), get_TrxName());
	}	//	getMOrgInfo


	
	/**
	 * 	After Save
	 *	@param newRecord new Record
	 *	@param success save success
	 *	@return success
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (!success)
			return success;
		if (newRecord)
		{
			//	Info
			MOrgInfo info = new MOrgInfo (this);
			info.saveEx();
			//	Access
			MRoleOrgAccess.createForOrg (this);
			MRole role = MRole.getDefault(getCtx(), true);	//	reload
			role.set_TrxName(get_TrxName());
			role.loadAccess(true); // reload org access within transaction
			//	TreeNode
			insert_Tree(MTree_Base.TREETYPE_Organization);
		}
		//	Value/Name change
		if (!newRecord && (is_ValueChanged("Value") || is_ValueChanged("Name")))
		{
			MAccount.updateValueDescription(getCtx(), "AD_Org_ID=" + getAD_Org_ID(), get_TrxName());
			if ("Y".equals(Env.getContext(getCtx(), "$Element_OT"))) 
				MAccount.updateValueDescription(getCtx(), "AD_OrgTrx_ID=" + getAD_Org_ID(), get_TrxName());
		}
		
		return true;
	}	//	afterSave
	
	/**
	 * 	After Delete
	 *	@param success
	 *	@return deleted
	 */
	protected boolean afterDelete (boolean success)
	{
		if (success)
			delete_Tree(MTree_Base.TREETYPE_Organization);
		return success;
	}	//	afterDelete


	/**
	 * 	Get Linked BPartner
	 *	@return C_BPartner_ID
	 */
	public int getLinkedC_BPartner_ID(String trxName)
	{
		if (m_linkedBPartner == null)
		{
			int C_BPartner_ID = DB.getSQLValue(trxName,
				"SELECT C_BPartner_ID FROM C_BPartner WHERE AD_OrgBP_ID=?",
				getAD_Org_ID());
			if (C_BPartner_ID < 0)	//	not found = -1
				C_BPartner_ID = 0;
			m_linkedBPartner = new Integer (C_BPartner_ID);
		}
		return m_linkedBPartner.intValue();
	}	//	getLinkedC_BPartner_ID
	

	/****************** Changes for eInvoice - by YAHYA ****************/
	
	/** Legal name of company. Requires to be Arabic. If branch is having a different name, set it here */
    public static final String COLUMNNAME_NAME2 = "Name2";
    
	public void setName2 (String LegalName)
	{
		set_Value (COLUMNNAME_NAME2, LegalName);
	}

	public String getName2 () 
	{
		return (String)get_Value(COLUMNNAME_NAME2);
	}
	
    public static final String COLUMNNAME_TRDLICENSENO = "TRDLICENSENO";
    
	/**
	 * Set CR Number of the company/org
	 * @return
	 */
	public void setCRN (String CRN)
	{
		set_Value (COLUMNNAME_TRDLICENSENO, CRN);
	}

	/**
	 * Get CR Number of the company/org
	 * @return
	 */
	public String getCRN () 
	{
		return (String)get_Value(COLUMNNAME_TRDLICENSENO);
	}
	
	/**
	 * Digital certificate obtained from ZATCA, used to sign invoices. This shall be unique for EGS.
	 * Here each ORG is assumed to be an EGS.
	 */
	
    public static final String COLUMNNAME_ZATCA_SECRET = "ZATCA_SECRET";
    
	public void setZatcaSecret(String Secret)
	{
		set_Value (COLUMNNAME_ZATCA_SECRET, Secret);
	}

	/** Return the secret key */
	public String getZatcaSecret () 
	{
		return (String)get_Value(COLUMNNAME_ZATCA_SECRET);
	}
	
	/**
	 * Digital certificate obtained from ZATCA, used to sign invoices. This shall be unique for EGS.
	 * Here each ORG is assumed to be an EGS.
	 */
	
    public static final String COLUMNNAME_DIGITALCERTIFICATE = "DIGITALCERTIFICATE";
    
	public void setCertificate(String Certificate)
	{
		set_Value (COLUMNNAME_DIGITALCERTIFICATE, Certificate);
	}

	/** The certificate shall be saved base64 encoded ie. PCSID/CCSID  AS IS without decoding */
	public String getCertificate () 
	{
		return (String)get_Value(COLUMNNAME_DIGITALCERTIFICATE);
	}
	

	/**
	 * Public key used - part of Key Pair
	 */
    public static final String COLUMNNAME_PUBLICKEY = "PUBLICKEY";
    
	public void setPublicKey(String publicKey)
	{
		set_Value (COLUMNNAME_PUBLICKEY, publicKey);
	}

	public String getPublicKey () 
	{
		return (String)get_Value(COLUMNNAME_PUBLICKEY);
	}
	
	/**
	 * Public key used - part of Key Pair
	 */
    public static final String COLUMNNAME_PRIVATEKEY = "PRIVATEKEY";
    
	public void setPrivateKey(String privateKey)
	{
		set_Value (COLUMNNAME_PRIVATEKEY, privateKey);
	}

	public String getPrivateKey () 
	{
		return (String)get_Value(COLUMNNAME_PRIVATEKEY);
	}
	
	/**
	 * Public key used - part of Key Pair
	 */
    public static final String COLUMNNAME_EGS_NAME = "EGS_NAME";
    
	public void setEgsName(String egsName)
	{
		set_Value (COLUMNNAME_EGS_NAME, egsName);
	}

	public String getEgsName () 
	{
		return (String)get_Value(COLUMNNAME_EGS_NAME);
	}
	
	/**
	 * Public key used - part of Key Pair
	 */
    public static final String COLUMNNAME_EGS_SERIALNO = "EGS_SERIALNO";
    
	public void setEgsSerialNo(String EgsSerialNo)
	{
		set_Value (COLUMNNAME_EGS_SERIALNO, EgsSerialNo);
	}

	public String getEgsSerialNo () 
	{
		return (String)get_Value(COLUMNNAME_EGS_SERIALNO);
	}
	
	/**
	 * Street name, added in BP location table
	 */
	public static final String COLUMNNAME_STREET = "STREET";
	

	public String getStreetName() {
		return (String)get_Value(COLUMNNAME_STREET);
	}
	
	public void setStreetName (String StreetName)
	{
		set_Value (COLUMNNAME_STREET, StreetName);
	}

	/**
	 * BuildingNo, added in BP location table
	 */
	public static final String COLUMNNAME_BUILD_NO = "BUILD_NO";
	

	public String getBuildingNo() {
		return (String)get_Value(COLUMNNAME_BUILD_NO);
	}
	
	public void setBuildingNo (String BuildingNo)
	{
		set_Value (COLUMNNAME_BUILD_NO, BuildingNo);
	}
	
	/**
	 * AdditionalNo, added in BP location table
	 */
	public static final String COLUMNNAME_ADDL_NO = "ADDL_NO";
	

	public String getAdditionalNo() {
		return (String)get_Value(COLUMNNAME_ADDL_NO);
	}
	
	public void setAdditionalNo (String AdditionalNo)
	{
		set_Value (COLUMNNAME_ADDL_NO, AdditionalNo);
	}
	
	/**
	 * Postal Zip, added in BP location table (also present in C_Location - but not used)
	 */
	public static final String COLUMNNAME_POSTAL_ZIP = "POSTAL_ZIP";
	

	public String getPostalZip() {
		return (String)get_Value(COLUMNNAME_POSTAL_ZIP);
	}
	
	public void setPostalZip (String PostalZip)
	{
		set_Value (COLUMNNAME_POSTAL_ZIP, PostalZip);
	}
	
	/**
	 * District, added in BP location table
	 */
	public static final String COLUMNNAME_DISTRICT = "DISTRICT";
	

	public String getDistrict() {
		return (String)get_Value(COLUMNNAME_DISTRICT);
	}
	
	public void setDistrict (String District)
	{
		set_Value (COLUMNNAME_DISTRICT, District);
	}
	
}	//	MOrg
