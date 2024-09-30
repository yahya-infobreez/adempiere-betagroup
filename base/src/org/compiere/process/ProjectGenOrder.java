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
package org.compiere.process;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProject;
import org.compiere.model.MProjectLine;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *  Generate Sales Order from Project.
 *
 *	@author Jorg Janke
 *	@version $Id: ProjectGenOrder.java,v 1.3 2006/07/30 00:51:01 jjanke Exp $
 */
public class ProjectGenOrder extends SvrProcess
{
	/**	Project ID from project directly		*/
	private int		m_C_Project_ID = 0;

   private BigDecimal disc_pcnt;
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		m_C_Project_ID = getRecord_ID();
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message (clear text)
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		log.info("C_Project_ID=" + m_C_Project_ID);
		if (m_C_Project_ID == 0)
			throw new IllegalArgumentException("C_Project_ID == 0");
		MProject fromProject = getProject (getCtx(), m_C_Project_ID, get_TrxName());
		Env.setSOTrx(getCtx(), true);	//	Set SO context

		/** @todo duplicate invoice prevention */

		MOrder order = new MOrder (fromProject, true, MOrder.DocSubTypeSO_OnCredit);
		if (!order.save())
			throw new Exception("Could not create Order");

		//	***	Lines ***
		int count = 0;
		
		//	Service Project	
		if (MProject.PROJECTCATEGORY_ServiceChargeProject.equals(fromProject.getProjectCategory()))
		{
			/** @todo service project invoicing */
			throw new Exception("Service Charge Projects are on the TODO List");
		}	//	Service Lines

		else	//	Order Lines
		{
			MProjectLine[] lines = fromProject.getLines ();
			for (int i = 0; i < lines.length; i++)
			{
				
				MOrderLine ol = new MOrderLine(order);
				ol.setLine(lines[i].getLine());
				ol.setDescription(lines[i].getDescription());
				ol.setM_Product_ID(lines[i].getM_Product_ID(), true);
				ol.setQty(lines[i].getPlannedQty().subtract(lines[i].getInvoicedQty()));
			
				// added by DSPL on 20dec2010 , to take disc% from Projectline
				Integer c_projectline_id =lines[i].getC_ProjectLine_ID();
				
				System.out.println("projline : "+ c_projectline_id);
				
				String sql = " Select nvl(disc_pcnt,0),nvl(NETSELLING_PRICE,0),nvl(PRICE_BEFORE_DISC,0)" +
						" from c_projectline where c_projectline_id = ? ";
				
				try
				{
					PreparedStatement pstmt = DB.prepareStatement(sql, get_TrxName());
					pstmt.setInt(1, c_projectline_id);
					ResultSet rs = pstmt.executeQuery();
					
					if (rs.next())
					{
						disc_pcnt = rs.getBigDecimal(1);
						BigDecimal netsell_price = rs.getBigDecimal(2);
						BigDecimal price = rs.getBigDecimal(3);
						ol.setPriceList(price);
						ol.setPriceActual(netsell_price);
						ol.setPriceLimit(price);
						ol.setPriceEntered(netsell_price);
					
					}
					rs.close();
					pstmt.close();
				}
				catch (SQLException e)
				{
					log.log(Level.SEVERE, sql, e);
					return e.getLocalizedMessage();
				}
				
				//modified code ends here
				
				ol.setTax();
				if (ol.save())
					count++;

			}	//	for all lines
			if (lines.length != count)
				log.log(Level.SEVERE, "Lines difference - ProjectLines=" + lines.length + " <> Saved=" + count);
		}	//	Order Lines
		
		return "@C_Order_ID@ " + order.getDocumentNo() + " (" + count + ")";
	}	//	doIt

	/**
	 * 	Get and validate Project
	 * 	@param ctx context
	 * 	@param C_Project_ID id
	 * 	@return valid project
	 * 	@param trxName transaction
	 */
	static protected MProject getProject (Properties ctx, int C_Project_ID, String trxName)
	{
		MProject fromProject = new MProject (ctx, C_Project_ID, trxName);
		if (fromProject.getC_Project_ID() == 0)
			throw new IllegalArgumentException("Project not found C_Project_ID=" + C_Project_ID);
		if (fromProject.getM_PriceList_Version_ID() == 0)
			throw new IllegalArgumentException("Project has no Price List");
		if (fromProject.getM_Warehouse_ID() == 0)
			throw new IllegalArgumentException("Project has no Warehouse");
		if (fromProject.getC_BPartner_ID() == 0 || fromProject.getC_BPartner_Location_ID() == 0)
			throw new IllegalArgumentException("Project has no Business Partner/Location");
		return fromProject;
	}	//	getProject

}	//	ProjectGenOrder
