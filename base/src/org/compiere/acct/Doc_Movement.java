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
package org.compiere.acct;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.compiere.model.MAccount;
import org.compiere.model.MAcctSchema;
import org.compiere.model.MCostDetail;
import org.compiere.model.MMovement;
import org.compiere.model.MMovementLine;
import org.compiere.model.ProductCost;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *  Post Invoice Documents.
 *  <pre>
 *  Table:              M_Movement (323)
 *  Document Types:     MMM
 *  </pre>
 *  @author Jorg Janke
 *  @author Armen Rizal, Goodwill Consulting
 * 			<li>BF [ 1745154 ] Cost in Reversing Material Related Docs
 *  @version  $Id: Doc_Movement.java,v 1.3 2006/07/30 00:53:33 jjanke Exp $
 */
public class Doc_Movement extends Doc
{
	private int				m_Reversal_ID = 0;
	private String			m_DocStatus = "";
	
	/**
	 *  Constructor
	 * 	@param ass accounting schemata
	 * 	@param rs record
	 * 	@param trxName trx
	 */
	public Doc_Movement (MAcctSchema[] ass, ResultSet rs, String trxName)
	{
		super (ass, MMovement.class, rs, DOCTYPE_MatMovement, trxName);
	}   //  Doc_Movement

	/**
	 *  Load Document Details
	 *  @return error message or null
	 */
	protected String loadDocumentDetails()
	{
		setC_Currency_ID(NO_CURRENCY);
		MMovement move = (MMovement)getPO();
		setDateDoc (move.getMovementDate());
		setDateAcct(move.getMovementDate());
		m_Reversal_ID = move.getReversal_ID();//store original (voided/reversed) document
		m_DocStatus = move.getDocStatus();
		//	Contained Objects
		p_lines = loadLines(move);
		log.fine("Lines=" + p_lines.length);
		return null;
	}   //  loadDocumentDetails

	/**
	 *	Load Invoice Line
	 *	@param move move
	 *  @return document lines (DocLine_Material)
	 */
	private DocLine[] loadLines(MMovement move)
	{
		ArrayList<DocLine> list = new ArrayList<DocLine>();
		MMovementLine[] lines = move.getLines(false);
		for (int i = 0; i < lines.length; i++)
		{
			MMovementLine line = lines[i];
			DocLine docLine = new DocLine (line, this);
			docLine.setQty(line.getMovementQty(), false);
			docLine.setReversalLine_ID(line.getReversalLine_ID());
			log.fine(docLine.toString());
			list.add (docLine);
		}

		//	Return Array
		DocLine[] dls = new DocLine[list.size()];
		list.toArray(dls);
		return dls;
	}	//	loadLines

	/**
	 *  Get Balance
	 *  @return balance (ZERO) - always balanced
	 */
	public BigDecimal getBalance()
	{
		BigDecimal retValue = Env.ZERO;
		return retValue;
	}   //  getBalance

	/**
	 *  Create Facts (the accounting logic) for
	 *  MMM.
	 *  <pre>
	 *  Movement
	 *      Inventory       DR      CR
	 *      InventoryTo     DR      CR
	 *  </pre>
	 *  @param as account schema
	 *  @return Fact
	 */
	public ArrayList<Fact> createFacts (MAcctSchema as)
	{
		//  create Fact Header
		Fact fact = new Fact(this, as, Fact.POST_Actual);
		setC_Currency_ID(as.getC_Currency_ID());

		//  Line pointers
		FactLine dr = null;
		FactLine cr = null;

		for (int i = 0; i < p_lines.length; i++)
		{
			DocLine line = p_lines[i];
			// MZ Goodwill
			// if Inventory Move CostDetail exist then get Cost from Cost Detail 
			BigDecimal costs = line.getProductCosts(as, line.getAD_Org_ID(), true, "M_MovementLine_ID=? AND IsSOTrx='N'");
			// end MZ

			//******************************BETA CODE - USE A/C FROM WAREHOUSE INSTED OF CAT.MASTER**************
/*			Integer LocFrom = (Integer)line.getM_Locator_ID();
			Integer LocTo = (Integer)line.getM_LocatorTo_ID();
			MAccount LocFromAcc = null;
			MAccount LocToAcc = null;
			Integer FromAcc = 0;
			Integer ToAcc = 0;
			
			String sql1 = "Select nvl(W_PRODASSET_ACCT,0) from M_WAREHOUSE_ACCT Where M_WAREHOUSE_ID = ? " ;
			
			PreparedStatement pstmt1 = null;
			ResultSet rs1 = null;
			try
			{
				pstmt1 = DB.prepareStatement(sql1, null);
				pstmt1.setInt(1, LocFrom);
				rs1 = pstmt1.executeQuery();
				if (rs1.next()){
					FromAcc = rs1.getInt(1);
				}
			}
			catch(Exception e){
			}
			
			String sql2 = "Select nvl(W_PRODASSET_ACCT,0) from M_WAREHOUSE_ACCT Where M_WAREHOUSE_ID = ? " ;
			
			PreparedStatement pstmt2 = null;
			ResultSet rs2 = null;
			try
			{
				pstmt2 = DB.prepareStatement(sql2, null);
				pstmt2.setInt(1, LocTo);
				rs2 = pstmt2.executeQuery();
				if (rs2.next()){
					ToAcc = rs2.getInt(1);
				}
			}
			catch(Exception e){
			}
			
			LocFromAcc = line.getAccount(FromAcc,as);
			LocToAcc = line.getAccount(ToAcc, as);
			
*/			
			//******************************BETA CODE END********************************************************
			
			
			
			//  ** Inventory       DR      CR
			dr = fact.createLine(line,
				line.getAccount(ProductCost.ACCTTYPE_P_Asset, as),
				as.getC_Currency_ID(), costs.negate());		//	from (-) CR
			if (dr == null)
				continue;
			dr.setM_Locator_ID(line.getM_Locator_ID());
			dr.setQty(line.getQty().negate());	//	outgoing
			if (m_DocStatus.equals(MMovement.DOCSTATUS_Reversed) && m_Reversal_ID !=0 && line.getReversalLine_ID() != 0)
			{
				//	Set AmtAcctDr from Original Movement
				if (!dr.updateReverseLine (MMovement.Table_ID, 
						m_Reversal_ID, line.getReversalLine_ID(),Env.ONE))
				{
					p_Error = "Original Inventory Move not posted yet";
					return null;
				}
			}
			
			//  ** InventoryTo     DR      CR
			cr = fact.createLine(line,
				line.getAccount(ProductCost.ACCTTYPE_P_Asset, as),
				as.getC_Currency_ID(), costs);			//	to (+) DR
			if (cr == null)
				continue;
			cr.setM_Locator_ID(line.getM_LocatorTo_ID());
			cr.setQty(line.getQty());
			if (m_DocStatus.equals(MMovement.DOCSTATUS_Reversed) && m_Reversal_ID !=0 && line.getReversalLine_ID() != 0)
			{
				//	Set AmtAcctCr from Original Movement
				if (!cr.updateReverseLine (MMovement.Table_ID, 
						m_Reversal_ID, line.getReversalLine_ID(),Env.ONE))
				{
					p_Error = "Original Inventory Move not posted yet";
					return null;
				}
				costs = cr.getAcctBalance(); //get original cost
			}

			//	Only for between-org movements
			if (dr.getAD_Org_ID() != cr.getAD_Org_ID())
			{
				String costingLevel = line.getProduct().getCostingLevel(as);
				if (!MAcctSchema.COSTINGLEVEL_Organization.equals(costingLevel))
					continue;
				//
				String description = line.getDescription();
				if (description == null)
					description = "";
				//	Cost Detail From
				MCostDetail.createMovement(as, dr.getAD_Org_ID(), 	//	locator org
					line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
					line.get_ID(), 0,
					costs.negate(), line.getQty().negate(), true,
					description + "(|->)", getTrxName());
				//	Cost Detail To
				MCostDetail.createMovement(as, cr.getAD_Org_ID(),	//	locator org 
					line.getM_Product_ID(), line.getM_AttributeSetInstance_ID(),
					line.get_ID(), 0,
					costs, line.getQty(), false,
					description + "(|<-)", getTrxName());
			}
		}

		//
		ArrayList<Fact> facts = new ArrayList<Fact>();
		facts.add(fact);
		return facts;
	}   //  createFact

}   //  Doc_Movement
