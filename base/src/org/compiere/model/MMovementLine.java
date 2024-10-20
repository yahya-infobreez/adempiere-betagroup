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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.eevolution.model.MDDOrderLine;

/**
 *	Inventory Move Line Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MMovementLine.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MMovementLine extends X_M_MovementLine
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4078367839033015886L;

	/**
	 * 	Standard Cosntructor
	 *	@param ctx context
	 *	@param M_MovementLine_ID id
	 *	@param trxName transaction
	 */
	public MMovementLine (Properties ctx, int M_MovementLine_ID, String trxName)
	{
		super (ctx, M_MovementLine_ID, trxName);
		if (M_MovementLine_ID == 0)
		{
		//	setM_LocatorTo_ID (0);	// @M_LocatorTo_ID@
		//	setM_Locator_ID (0);	// @M_Locator_ID@
		//	setM_MovementLine_ID (0);			
		//	setLine (0);	
		//	setM_Product_ID (0);
			setM_AttributeSetInstance_ID(0);	//	ID
			setMovementQty (Env.ZERO);	// 1
			setTargetQty (Env.ZERO);	// 0
			setScrappedQty(Env.ZERO);
			setConfirmedQty(Env.ZERO);
			setProcessed (false);
		}	
	}	//	MMovementLine

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MMovementLine (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MMovementLine

	/**
	 * 	Parent constructor
	 *	@param parent parent
	 */
	public MMovementLine (MMovement parent)
	{
		this (parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setM_Movement_ID(parent.getM_Movement_ID());
	}	//	MMovementLine
	
	/**
	 * 	Get AttributeSetInstance To
	 *	@return ASI
	 */
	@Override
	public int getM_AttributeSetInstanceTo_ID ()
	{
		int M_AttributeSetInstanceTo_ID = super.getM_AttributeSetInstanceTo_ID();
		if (M_AttributeSetInstanceTo_ID == 0 && (getM_Locator_ID() == getM_LocatorTo_ID()))
			M_AttributeSetInstanceTo_ID = super.getM_AttributeSetInstance_ID();
		return M_AttributeSetInstanceTo_ID;
	}	//	getM_AttributeSetInstanceTo_ID
	
	/**
	 * 	Add to Description
	 *	@param description text
	 */
	public void addDescription (String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}	//	addDescription

	/**
	 * 	Get Product
	 *	@return product or null if not defined
	 */
	public MProduct getProduct()
	{
		if (getM_Product_ID() != 0)
			return MProduct.get(getCtx(), getM_Product_ID());
		return null;
	}	//	getProduct
	
	/**
	 * 	Set Movement Qty - enforce UOM precision 
	 *	@param MovementQty qty
	 */
	@Override
	public void setMovementQty (BigDecimal MovementQty)
	{
		if (MovementQty != null)
		{
			MProduct product = getProduct();
			if (product != null)
			{
				int precision = product.getUOMPrecision(); 
				MovementQty = MovementQty.setScale(precision, BigDecimal.ROUND_HALF_UP);
			}
		}
		super.setMovementQty(MovementQty);
	}	//	setMovementQty
	
	/** Parent							*/
	private MMovement m_parent = null;
	
	/**
	 * get Parent
	 * @return Parent Movement
	 */
	public MMovement getParent() 
	{
		if (m_parent == null)
			m_parent = new MMovement (getCtx(), getM_Movement_ID(), get_TrxName());
		return m_parent;
	}	//	getParent

	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		if (newRecord && getParent().isComplete()) {
			log.saveError("ParentComplete", Msg.translate(getCtx(), "M_MovementLine"));
			return false;
		}
		//	Set Line No
		if (getLine() == 0)
		{
			String sql = "SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM M_MovementLine WHERE M_Movement_ID=?";
			int ii = DB.getSQLValue (get_TrxName(), sql, getM_Movement_ID());
			setLine (ii);
		}
		
		 //either movement between locator or movement between lot
		if (getM_Locator_ID() == getM_LocatorTo_ID() && getM_AttributeSetInstance_ID() == getM_AttributeSetInstanceTo_ID())
		{
			log.saveError("Error", Msg.parseTranslation(getCtx(), "@M_Locator_ID@ == @M_LocatorTo_ID@ and @M_AttributeSetInstance_ID@ == @M_AttributeSetInstanceTo_ID@"));
			return false;
		}

		if (getMovementQty().signum() == 0)
		{
			if (   MMovement.DOCACTION_Void.equals(getParent().getDocAction())
				&& (   MMovement.DOCSTATUS_Drafted.equals(getParent().getDocStatus())
					|| MMovement.DOCSTATUS_Invalid.equals(getParent().getDocStatus())
					|| MMovement.DOCSTATUS_InProgress.equals(getParent().getDocStatus())
					|| MMovement.DOCSTATUS_Approved.equals(getParent().getDocStatus())
					|| MMovement.DOCSTATUS_NotApproved.equals(getParent().getDocStatus())
				   )
				) 
			{
				// [ 2092198 ] Error voiding an Inventory Move - globalqss
				// zero allowed in this case (action Void and status Draft)
			} else {
				log.saveError("FillMandatory", Msg.getElement(getCtx(), "MovementQty"));
				return false;
			}
		}

		//	Qty Precision
		if (newRecord || is_ValueChanged(COLUMNNAME_MovementQty))
			setMovementQty(getMovementQty());
		
		//      Mandatory Instance
		MProduct product = getProduct();
		if (getM_AttributeSetInstance_ID() == 0) {
			if (product != null && product.isASIMandatory(false)) {
				log.saveError("FillMandatory", Msg.getElement(getCtx(), COLUMNNAME_M_AttributeSetInstance_ID));
				return false;
			}
		}
		if (getM_AttributeSetInstanceTo_ID() == 0)
		{
			//instance id default to same for movement between locator 
			if (getM_Locator_ID() != getM_LocatorTo_ID())
			{
				if (getM_AttributeSetInstance_ID() != 0)        //set to from
					setM_AttributeSetInstanceTo_ID(getM_AttributeSetInstance_ID());
			}
			
			if (product != null && product.isASIMandatory(true) && getM_AttributeSetInstanceTo_ID() == 0)
			{
				log.saveError("FillMandatory", Msg.getElement(getCtx(), COLUMNNAME_M_AttributeSetInstanceTo_ID));
				return false;
			}
		}       //      ASI

		/*
		//Validate Product with Prod.Category Vs Warehouse  Dated:21Jul2011
			Integer ProdID = (Integer)getM_Product_ID();
			Integer Wid = (Integer)getM_Locator_ID();
			BigDecimal Sqlcnt = Env.ZERO;
						
			String SqlValid = "Select count(*) from m_product mp inner join m_product_Category mc " +
			"on mc.m_product_category_id=mp.m_product_category_id where mp.m_product_id=? and " +
			"mc.m_warehouse_id=?";
			try {
					Sqlcnt = DB.getSQLValueBD(get_TrxName(), SqlValid,ProdID,Wid);
				} 	catch (Exception e) {
				//
			}
			if (Sqlcnt.doubleValue() <= 0)
			{
				log.saveError("Error", Msg.getMsg(getCtx(), "Not a Valid Product. You should enter a product from the selected warehouse" ));
				return false;
			}
		*/
		
		//Code to validate Trx.qty Vs On Hand Qty         Added by Subair on 11Dec2012
		
		MLocator locator = MLocator.get(getCtx(), getM_Locator_ID());
		
		String sql = "SELECT nvl(SUM(ps.QtyOnHand),0) FROM M_Product_Stock_v ps" +
		" WHERE ps.m_product_id =" + product.getM_Product_ID() + "" +     
		" AND ps.M_Warehouse_ID = " + locator.getM_Warehouse_ID();
		BigDecimal QtyOnHand = Env.ZERO;
		try 
		{
			QtyOnHand = DB.getSQLValueBD(get_TrxName(), sql);		
		} catch (Exception e) {
		//
		}
		if (getMovementQty().compareTo(QtyOnHand) == 1)
		{
			log.saveError("Error", "Transfer Qty (" + getMovementQty() + ") should not be greater than On Hand Qty (" + QtyOnHand + ")");
			return false;
		
		}
		
		BigDecimal TrQty = (BigDecimal)getMovementQty();
		
		if (TrQty.doubleValue() ==0 )
		{
			log.saveError("Error", Msg.getMsg(getCtx(), "Transfer Qty Should not be Zero" ));
			return false;
		}		
		
		//***************BETA CODE END*************************************
		
		return true;
	}	//	beforeSave
	
	/** 
	 *      Set Distribution Order Line. 
	 *      Does not set Quantity! 
	 *      @param oLine order line 
	 *      @param M_Locator_ID locator 
	 *      @param Qty used only to find suitable locator 
	 */ 
	public void setOrderLine (MDDOrderLine oLine, BigDecimal Qty, boolean isReceipt) 
	{ 
		setDD_OrderLine_ID(oLine.getDD_OrderLine_ID()); 
		setLine(oLine.getLine()); 
		//setC_UOM_ID(oLine.getC_UOM_ID()); 
		MProduct product = oLine.getProduct(); 
		if (product == null) 
		{ 
			set_ValueNoCheck(COLUMNNAME_M_Product_ID, null); 
			set_ValueNoCheck(COLUMNNAME_M_AttributeSetInstance_ID, null); 
			set_ValueNoCheck(COLUMNNAME_M_AttributeSetInstanceTo_ID, null); 
			set_ValueNoCheck(COLUMNNAME_M_Locator_ID, null); 
			set_ValueNoCheck(COLUMNNAME_M_LocatorTo_ID, null); 
		} 
		else 
		{ 
			setM_Product_ID(oLine.getM_Product_ID()); 
			setM_AttributeSetInstance_ID(oLine.getM_AttributeSetInstance_ID()); 
			setM_AttributeSetInstanceTo_ID(oLine.getM_AttributeSetInstanceTo_ID()); 
			// 
			if (product.isItem()) 
			{ 
				MWarehouse w = MWarehouse.get(getCtx(), oLine.getParent().getM_Warehouse_ID());
				MLocator locator_inTransit = MLocator.getDefault(w);
				if(locator_inTransit == null)
				{
					throw new AdempiereException("Do not exist Locator for the  Warehouse in transit");
				}
				
				if (isReceipt)
				{
					setM_Locator_ID(locator_inTransit.getM_Locator_ID()); 
					setM_LocatorTo_ID(oLine.getM_LocatorTo_ID()); 
				}
				else 
				{
					setM_Locator_ID(oLine.getM_Locator_ID()); 
					setM_LocatorTo_ID(locator_inTransit.getM_Locator_ID()); 
				}
			} 
			else 
			{	
				set_ValueNoCheck(COLUMNNAME_M_Locator_ID, null); 
				set_ValueNoCheck(COLUMNNAME_M_LocatorTo_ID, null); 
			}	
		} 
	
		setDescription(oLine.getDescription()); 
		this.setMovementQty(Qty);
	}       //      setOrderLine 

	/** 
	 *      Set M_Locator_ID 
	 *      @param M_Locator_ID id 
	 */
	@Override
	public void setM_Locator_ID (int M_Locator_ID) 
	{ 
		if (M_Locator_ID < 0) 
			throw new IllegalArgumentException ("M_Locator_ID is mandatory."); 
		//      set to 0 explicitly to reset 
		set_Value (COLUMNNAME_M_Locator_ID, M_Locator_ID); 
	}       //      setM_Locator_ID 

	/** 
	 *      Set M_LocatorTo_ID 
	 *      @param M_LocatorTo_ID id 
	 */ 
	@Override
	public void setM_LocatorTo_ID (int M_LocatorTo_ID) 
	{ 
		if (M_LocatorTo_ID < 0) 
			throw new IllegalArgumentException ("M_LocatorTo_ID is mandatory."); 
		//      set to 0 explicitly to reset 
		set_Value (COLUMNNAME_M_LocatorTo_ID, M_LocatorTo_ID); 
	}       //      M_LocatorTo_ID 

	/** 
	 *  Get Movement lines Of Distribution Order Line 
	 *  @param ctx context 
	 *  @param DD_OrderLine_ID line 
	 *  @param where optional addition where clause 
	 *  @param trxName transaction 
	 *      @return array of receipt lines 
	 */ 
	public static MMovementLine[] getOfOrderLine (Properties ctx, 
			int DD_OrderLine_ID, String where, String trxName) 
	{
		String whereClause = COLUMNNAME_DD_OrderLine_ID+"=?"; 
		if (where != null && where.length() > 0) 
			whereClause += " AND (" + where + ")";
		//
		List<MMovementLine> list = new Query(ctx, Table_Name, whereClause, trxName)
										.setParameters(DD_OrderLine_ID)
										.list();
		return list.toArray(new MMovementLine[list.size()]);
	}       //      getOfOrderLine 

	public String toString()
	{
		return Table_Name + "[" + get_ID() 
			+ ", M_Product_ID=" + getM_Product_ID()
			+ ", M_ASI_ID=" + getM_AttributeSetInstance_ID()
			+ ", M_ASITo_ID=" + getM_AttributeSetInstanceTo_ID()
			+ ", M_Locator_ID=" + getM_Locator_ID()
			+ ", M_LocatorTo_ID=" + getM_LocatorTo_ID()
			+ "]"
		;
	}	
}	//	MMovementLine
