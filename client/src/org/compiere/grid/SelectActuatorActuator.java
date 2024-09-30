
/******************************************************************************
 * Copyright (C) 2009 Low Heng Sin                                            *
 * Copyright (C) 2009 Idalica Corporation                                     *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/

package org.compiere.grid;

import java.math.BigDecimal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.JOptionPane;

//import org.adempiere.webui.window.FDialog;
import org.compiere.apps.ADialog;
import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MLocator;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MProduct;
import org.compiere.model.MRMA;
import org.compiere.model.MRMALine;
import org.compiere.model.MWarehouse;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.process.SvrProcess;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;

/**
 *  Create Invoice Transactions from PO Orders or Receipt
 *
 *  @author Jorg Janke
 *  @version  $Id: VCreateFromShipment.java,v 1.4 2006/07/30 00:51:28 jjanke Exp $
 * 
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * 			<li>BF [ 1896947 ] Generate invoice from Order error
 * 			<li>BF [ 2007837 ] VCreateFrom.save() should run in trx
 */
public class SelectActuatorActuator extends SelectActuator 
{

	
	public int volt =230;
	public String PrdVal="";
	public String OnOffMod ="O";
	public char fswitch='F';
	public char narea='F';
	public char fspring='F';
	public float areav;
	public BigDecimal actqty=Env.ZERO;
	Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	/**
	 *  Protected Constructor
	 *  @param mTab MTab
	 */
	public SelectActuatorActuator(GridTab mTab)
	{
		super(mTab);
		log.info(mTab.toString());
	}   //  VCreateFromShipment

	/**
	 *  Dynamic Init
	 *  @return true if initialized
	 */
	public boolean dynInit() throws Exception
	{
		log.config("");
		setTitle(Msg.getElement(Env.getCtx(), "C_QUOTATIONLINE_ID", false) + " .. " + Msg.translate(Env.getCtx(), "SelectActuator"));
		
		return true;
	}   //  dynInit

	
	/**
	 *  Load PBartner dependent Order/Invoice/Shipment Field.
	 *  @param C_BPartner_ID BPartner*/
	public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }
	/**
	 * Load Actuator details
	 * @param 
	 */
	protected Vector<Vector<Object>> getActuatorData()
	{
		//m_invoice = new MInvoice(Env.getCtx(), C_Invoice_ID, null); // save
		//p_order = null;
		//m_rma = null;
		
		Vector<Vector<Object>> data2 = new Vector<Vector<Object>>();
		data2.removeAllElements();
		if(narea=='T')
		{
			StringBuffer sql2 = new StringBuffer("SELECT " // Entered UOM
					+"distinct(area),brand from Beta_qtn_actuator where   "
					+" isActive ='Y' and	volt=? "
					+ " and OnOffMod=? "
					+ " ");
				if (fswitch=='T')
					sql2.append(" and AUXSWITCHYN='Y'");
					
				else
					sql2.append(" and AUXSWITCHYN='N'");
				if(fspring=='T')
				{
					sql2.append(" and SPRINGRETYN='Y'");
				}
				else
				{
					if (PrdVal.equalsIgnoreCase("MPRD")) 
						sql2.append(" and SPRINGRETYN='Y'");
					else
						sql2.append(" and SPRINGRETYN='N'");
				}
				sql2.append(" order by brand");
			PreparedStatement pstmt2 = null;
			
			ResultSet rs2 = null;
			//System.out.print("if--Actuator--Actuator******"+narea+"&&&&&"+volt+"on/off==="+OnOffMod);
			//System.out.print("@@@@@####sql#######@@@@"+sql2);
			try
			{
				pstmt2 = DB.prepareStatement(sql2.toString(), null);
				pstmt2.setInt(1, volt);
				pstmt2.setString(2, OnOffMod);
				rs2 = pstmt2.executeQuery();
				
				while (rs2.next())
				{
					Vector<Object> line = new Vector<Object>(2);
					Float varea = rs2.getFloat(1) ;
					varea=round(varea,2);
					String vbrand = rs2.getString("brand");
					line.add(varea); // 4-area
					line.add(vbrand); // 5-brand
					data2.add(line);
				}
				
			}
			catch (SQLException e)
			{
				log.log(Level.SEVERE, sql2.toString(), e);
				//throw new DBException(e, sql);
			}
		    finally
		    {
		    	DB.close(rs2, pstmt2);
		    	rs2 = null; pstmt2 = null;
		    }
		}
		else
		{
			StringBuffer sql2 = new StringBuffer("SELECT " // Entered UOM
					+"min(area),brand from Beta_qtn_actuator where   "
					+" isActive ='Y' and	volt=? "
					+ " and OnOffMod=? "
					+ "and area>=? ");
				if (fswitch=='T')
					sql2.append(" and AUXSWITCHYN='Y'");
					
				else
					sql2.append(" and AUXSWITCHYN='N'");
				if(fspring=='T')
				{
					sql2.append(" and SPRINGRETYN='Y'");
				}
				else
				{
					if (PrdVal.equalsIgnoreCase("MPRD")) 
						sql2.append(" and SPRINGRETYN='Y'");
					else
						sql2.append(" and SPRINGRETYN='N'");
				}
				sql2.append(" group by brand order by brand");
			PreparedStatement pstmt2 = null;
			ResultSet rs2 = null;
			//System.out.print("else--Actuator--Actuator******"+narea+"&&&&&"+volt);
			
			try
			{
				pstmt2 = DB.prepareStatement(sql2.toString(), null);
				pstmt2.setInt(1, volt);
				pstmt2.setString(2, OnOffMod);
				pstmt2.setFloat(3, areav);
				rs2 = pstmt2.executeQuery();
				
				while (rs2.next())
				{
					Vector<Object> line = new Vector<Object>(2);
					Float varea = rs2.getFloat(1) ;
					varea=round(varea,2);
					String vbrand = rs2.getString("brand") ;
					line.add(varea); // 4-area
					line.add(vbrand); // 5-brand
					data2.add(line);
				}
				
			}
			catch (SQLException e)
			{
				log.log(Level.SEVERE, sql2.toString(), e);
				//throw new DBException(e, sql);
			}
		    finally
		    {
		    	DB.close(rs2, pstmt2);
		    	rs2 = null; pstmt2 = null;
		    }
		}
		data.removeAllElements();
		
		for (int i = 0; i < data2.size() ; i++)
		{
			
		StringBuffer sql = new StringBuffer("SELECT " // Entered UOM
				+"Beta_qtn_actuator_ID,name, nm,runningtime,exclbpvavyn,springretyn from Beta_qtn_actuator where   "
				+"isActive ='Y' and volt=? "
				+ " and OnOffMod=? and area=? and brand=?");
		if (fswitch=='T')
			sql.append(" and AUXSWITCHYN='Y'");
			
		else
			sql.append(" and AUXSWITCHYN='N'");
		if(fspring=='T')
		{
			sql.append(" and SPRINGRETYN='Y'");
		}
		else
		{
			if (PrdVal.equalsIgnoreCase("MPRD")) 
				sql.append(" and SPRINGRETYN='Y'");
			else
				sql.append(" and SPRINGRETYN='N'");
		}
		sql.append(" order by name");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), null);
			pstmt.setInt(1, volt);
			pstmt.setString(2, OnOffMod);
			pstmt.setFloat(3, (Float) data2.get(i).get(0)); 
			pstmt.setString(4, ((String) data2.get(i).get(1)));
			//System.out.print("******"+OnOffMod+"&&&&&"+volt);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Vector<Object> line = new Vector<Object>(3);
				
				KeyNamePair pp = new KeyNamePair(rs.getInt("Beta_qtn_actuator_ID"), rs.getString("name").trim());
				                       
				String nm = rs.getString("nm") ;
				String trime = rs.getString("runningtime") ;
				line.add(new Boolean(false));   // 0-Selection
				line.add(pp);  //NAME
				line.add(nm); // 2-nm
				line.add(trime); // 3-trime
				String vavyn=rs.getString("exclbpvavyn");
				String spret=rs.getString("springretyn");
				if(PrdVal.equalsIgnoreCase("EBP1") | PrdVal.equalsIgnoreCase("EBP2") | PrdVal.equalsIgnoreCase("EBP3") |
					      PrdVal.equalsIgnoreCase("EBP4") | PrdVal.equalsIgnoreCase("EBP5") | PrdVal.equalsIgnoreCase("EBP6") |PrdVal.equalsIgnoreCase("EBP7") |
				 			 PrdVal.equalsIgnoreCase("EBP8") | PrdVal.equalsIgnoreCase("SDVBP")
					 	  | PrdVal.equalsIgnoreCase("SDVBPE"))
				{
					if(vavyn.equalsIgnoreCase("Y"))
					{
						
					}
					else
					{
						//System.out.print("******"+line+"&&&&&");
						
						data.add(line);
					}
					
				}
				
				else if (PrdVal.equalsIgnoreCase("MPRD"))
				{
					if (spret.equalsIgnoreCase("Y"))
						data.add(line);
				}
				
				else
				{
					//System.out.print("******"+line+"&&&&&");
					data.add(line);
				}
				
				
			}
		
		}
		catch (SQLException e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
			//throw new DBException(e, sql);
		}
	    finally
	    {
	    	DB.close(rs, pstmt);
	    	rs = null; pstmt = null;
	    }
		}
		return data;
	}
	
	
	/**
	 *  List number of rows selected
	 */
	public void info()
	{

	}   
	
	protected Vector<String> getOISColumnNames()
	{
		//  Header Info
	    Vector<String> columnNames = new Vector<String>(7);
	    columnNames.add("Select");
	    columnNames.add(" Name      ");
	    columnNames.add( "Nm   ");
	    columnNames.add("Running Time ");
	       
	    return columnNames;
	}

	
	protected void configureMiniTable (IMiniTable miniTable)
	{
		miniTable.setColumnClass(0, Boolean.class, false);     //  Selection
		miniTable.setColumnClass(1, String.class, false);  //  Nmae
		miniTable.setColumnClass(2, String.class, true);   //  Nm
		miniTable.setColumnClass(3, String.class, true); //  Running Time
		miniTable.setColumnReadOnly(1, true);
		miniTable.setColumnReadOnly(2, true);
		miniTable.setColumnReadOnly(3, true);
		miniTable.autoSize();
		
	}

	/**
	 *  Save - Create Invoice Lines
	 *  @return true if saved
	 */
	public boolean save(IMiniTable miniTable, String trxName)
	{
		
		// Get Shipment
		int C_QUOTATIONLINE_ID = ((Integer) getGridTab().getValue("C_QUOTATIONLINE_ID")).intValue();
		
		int count=0;
		for (int i = 0; i < data.size() ; i++)
		{
			if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue()) {
				count++;
			}
		}
		if(count>1)
		{
			return false;
		}
		else
		{
		// Lines
			for (int i = 0; i < data.size() ; i++)
			{
			  
				if (((Boolean)miniTable.getValueAt(i, 0)).booleanValue()) 
				{
					// variable values
						KeyNamePair pp = (KeyNamePair) data.get(i).get(1) ; // Actuator id
					int actuator_id = pp.getKey();
					
					
					
					
					try {
						String updateQuery = "UPDATE C_QUOTATIONLINE set Beta_qtn_actuator_ID =? where C_QUOTATIONLINE_ID=?";
						PreparedStatement ps = DB.prepareStatement(updateQuery);
						ps.setInt(1,actuator_id);
						ps.setInt(2,C_QUOTATIONLINE_ID);
						int p=ps.executeUpdate();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					try {
						String updateQuery = "UPDATE C_QUOTATIONLINE set actQty =? where C_QUOTATIONLINE_ID=?";
						PreparedStatement ps = DB.prepareStatement(updateQuery);
						ps.setBigDecimal(1,actqty);
						ps.setInt(2,C_QUOTATIONLINE_ID);
						int p=ps.executeUpdate();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			
				}   //   if selected
			}   //  for all rows
			return true;	
		}
			
		
	}   //  saveInvoice

	
	

}

