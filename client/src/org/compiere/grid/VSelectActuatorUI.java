
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

import java.awt.BorderLayout;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.VetoableChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

//import org.adempiere.webui.window.FDialog;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.grid.ed.VLocator;
import org.compiere.grid.ed.VLookup;
import org.compiere.minigrid.IMiniTable;
import org.compiere.minigrid.MiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MLocator;
import org.compiere.model.MLocatorLookup;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MProduct;
import org.compiere.model.Query;
import org.compiere.swing.CEditor;
import org.compiere.swing.CPanel;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;


public class VSelectActuatorUI extends SelectActuatorActuator implements ActionListener, VetoableChangeListener
{
	private static final int WINDOW_CUSTOMER_RETURN = 53097;
	private static final int WINDOW_RETURN_TO_VENDOR = 53098;
	private VSelectActuatorDialog dialog;
	public VSelectActuatorUI(GridTab mTab)
	{
		super(mTab);
		log.info(getGridTab().toString());
		
		dialog = new VSelectActuatorDialog(this, getGridTab().getWindowNo(), true);
		
		p_WindowNo = getGridTab().getWindowNo();

		try
		{
			if (!dynInit())
				return;
			jbInit();

			setInitOK(true);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "", e);
			setInitOK(false);
		}
		AEnv.positionCenterWindow(Env.getWindow(p_WindowNo), dialog);
	}   //  VCreateFrom
	
	/** Window No               */
	private int p_WindowNo;

	/**	Logger			*/
	private CLogger log = CLogger.getCLogger(getClass());
	private JLabel prLabel = new JLabel();
	private VLookup prField;
	
	private VLookup qline;
	
	private JButton searchButton = new JButton();
	private JLabel lblonoffmod = new JLabel();
	private JComboBox onoffmodField = new JComboBox();
	private JLabel lblvolt = new JLabel();
    private JComboBox voltField = new JComboBox();
	private JLabel lblArea = new JLabel();
	private JTextField areaField = new JTextField();
	private JCheckBox withswitch = new JCheckBox();
	private JCheckBox withspringret = new JCheckBox();
	
	/**
	 *  Dynamic Init
	 *  @throws Exception if Lookups cannot be initialized
	 *  @return true if initialized
	 */
	public boolean dynInit() throws Exception
	{
		log.config("");
		
		super.dynInit();
		dialog.setTitle(getTitle());
		withswitch.setSelected(false);
		withswitch.addActionListener(this);
		withspringret.setSelected(false);
		withspringret.addActionListener(this);
		//searchButton.setText("Search Actuator");
		//searchButton.addActionListener(this);
		initArea();
		onoffmodField.addActionListener(this);
		voltField.addActionListener(this);
		
		areaField.setEditable(false);
		
		return true;
	}   //  dynInit
    
	/**
	 *  Static Init.
	 *  <pre>
	 *  parameterPanel
	 *      parameterBankPanel
	 *      parameterStdPanel
	 *          bPartner/order/invoice/shopment/licator Label/Field
	 *  dataPane
	 *  southPanel
	 *      confirmPanel
	 *      statusBar
	 *  </pre>
	 *  @throws Exception
	 */
    private void jbInit() throws Exception
    {
    	boolean isRMAWindow = ((getGridTab().getAD_Window_ID() == WINDOW_RETURN_TO_VENDOR) || (getGridTab().getAD_Window_ID() == WINDOW_CUSTOMER_RETURN)); 
    	
    	lblonoffmod.setText("OnOff / Modulating");
    	lblvolt.setText(Msg.getElement(Env.getCtx(), "Volt", false));
    	//lblArea.setText("Area(m2) ");
    	
    	withswitch.setText("With Auxiliary Switch [Y/N]");
    	withspringret.setText("With Spring Return [Y/N]");
    	
    	KeyNamePair pp1 = new KeyNamePair(0,"ON/OFF");
    	onoffmodField.addItem(pp1);
    	KeyNamePair pp = new KeyNamePair(1,"MODULATING");
    	onoffmodField.addItem(pp);
    	
    	KeyNamePair pv = new KeyNamePair(230,"230V");
    	voltField.addItem(pv);
    	KeyNamePair pv1 = new KeyNamePair(24,"24V");
    	voltField.addItem(pv1);
    	    	
    	CPanel parameterPanel = dialog.getParameterPanel();
    	parameterPanel.setLayout(new BorderLayout());
    	CPanel parameterStdPanel = new CPanel(new GridBagLayout());
    	parameterPanel.add(parameterStdPanel, BorderLayout.CENTER);

    	
    	parameterStdPanel.add(lblvolt, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
        			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        parameterStdPanel.add(voltField,  new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0
        			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));

        parameterStdPanel.add(lblonoffmod, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    	parameterStdPanel.add(onoffmodField, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
    				,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    	
        parameterStdPanel.add(lblArea, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
    			,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5,5,5, 5), 0, 0));
    	parameterStdPanel.add(areaField, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
    	
    	parameterStdPanel.add(withswitch, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
    			,GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        parameterStdPanel.add(withspringret, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
                ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
     //   parameterStdPanel.add(searchButton, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0
    	//		,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        
        
        if (PrdVal.equalsIgnoreCase("EBP1") | PrdVal.equalsIgnoreCase("EBP2") | PrdVal.equalsIgnoreCase("EBP3") |
			      PrdVal.equalsIgnoreCase("EBP4") | PrdVal.equalsIgnoreCase("EBP5") | PrdVal.equalsIgnoreCase("EBP6") |
			 	  PrdVal.equalsIgnoreCase("EBP7") | PrdVal.equalsIgnoreCase("EBP8") |PrdVal.equalsIgnoreCase("SDVBP")
			 	  | PrdVal.equalsIgnoreCase("SDVBPE"))
        {
        	
        	withspringret.setEnabled(false);
        	onoffmodField.removeAllItems();
        	onoffmodField.addItem(pp);
        	
        }
    	
    }   //  jbInit

	/*************************************************************************/

	private boolean 	m_actionActive = false;
	
	/**
	 *  Action Listener
	 *  @param e event
	 */
	public void actionPerformed(ActionEvent e)
	{
		log.config("Action=" + e.getActionCommand());
		
		if (m_actionActive)
			return;
		m_actionActive = true;
		log.config("Action=" + e.getActionCommand());
		
		if (e.getSource().equals(onoffmodField))
		{
			dialog.getMiniTable().getModel().removeTableModelListener(dialog);
			
			KeyNamePair pp = (KeyNamePair)onoffmodField.getSelectedItem();
			
			if (pp != null)
			if( pp.getKey()==0)
				OnOffMod="O";
			else
				OnOffMod="M";
			loadActuator();
		}
		
		if (e.getSource().equals(voltField))
		{
			dialog.getMiniTable().getModel().removeTableModelListener(dialog);
			
			KeyNamePair pp = (KeyNamePair)voltField.getSelectedItem();
			
			if (pp != null)
				volt = pp.getKey();
			loadActuator();
		}
		if (e.getSource().equals(withspringret))
		{
			dialog.getMiniTable().getModel().removeTableModelListener(dialog);
			
			if(withspringret.isSelected()==true)
				fspring='T';
			else
				fspring='F';
			loadActuator();
		}
		
		if (e.getSource().equals(withswitch))
		{
			dialog.getMiniTable().getModel().removeTableModelListener(dialog);
			
			if(withswitch.isSelected()==true) 
				fswitch='T';
			else
				fswitch='F';	
			
			loadActuator();
		}
		
		if (e.getSource().equals(searchButton))
		{
			//searchButton.setEnabled(false);
			
			loadActuator();
			//searchButton.setEnabled(true);
		}
		
		m_actionActive = false;
	}   //  actionPerformed

	/**
	 *  Change Listener
	 *  @param e event
	 */
	public void vetoableChange (PropertyChangeEvent e)
	{
		log.config(e.getPropertyName() + "=" + e.getNewValue());

		//  BPartner - load Order/Invoice/Shipment
		if (e.getPropertyName().equals("OK"))
		{
			int C_BPartner_ID = ((Integer)e.getNewValue()).intValue();
			//initBPOrderDetails (C_BPartner_ID, false);
		}
		dialog.tableChanged(null);
	}   //  vetoableChange
	
	/**************************************************************************
	 *  Load BPartner Field
	 *  @param forInvoice true if Invoices are to be created, false receipts
	 *  @throws Exception if Lookups cannot be initialized
	 */
	protected void initArea() throws Exception
	{
		//  load BPartner
		int AD_Column_ID = 1000455;        //  C_quotationline.M_Product_ID
		BigDecimal arp = null;
		MLookup lookup = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.Search);
		prField = new VLookup ("M_Product_ID", true, false, true, lookup);

		 AD_Column_ID=1000453;
		MLookup lookup1 = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.Search);
		qline = new VLookup ("C_QUOTATIONLINE_ID", true, false, true, lookup);

		int qline_id=Env.getContextAsInt(Env.getCtx(), p_WindowNo, "C_QUOTATIONLINE_ID");
		
		int M_Product_ID = Env.getContextAsInt(Env.getCtx(), p_WindowNo, "M_Product_ID");
		String sqlprd = " select Value from m_product where m_product_id = ? ";
		
		PrdVal = DB.getSQLValueString(null, sqlprd, M_Product_ID);
		  if (PrdVal.equalsIgnoreCase("SDVBP")
			 	  | PrdVal.equalsIgnoreCase("SDVBPE")| PrdVal.equalsIgnoreCase("VDR") | PrdVal.equalsIgnoreCase("VDR-SMC") 
			 	  | PrdVal.equalsIgnoreCase("VDRBB"))
	 	  {
	 		String dqString = " select A from C_QUOTATIONLINE where C_QUOTATIONLINE_ID = ? ";
	 	 	String AvalS = DB.getSQLValueString(null, dqString, qline_id);    
	 		float Aval=((Float.parseFloat(AvalS))/2);
	 		 
	 		dqString = " select unit from C_QUOTATIONLINE where C_QUOTATIONLINE_ID = ? ";
		 	String unitval = DB.getSQLValueString(null, dqString, qline_id);    
		 	if(unitval.equals("M"))
		 	{
		 		Aval=(float) (Aval*.001);
		 		
		 	}
		 	else if (unitval.equals("I"))
		 	{
		 		Aval=(float) (Aval*0.0254);
		 		
		 	}
		 	areav=(float) (3.14*(Aval*Aval));
		 	actqty = new BigDecimal(1);
		 	arp = new BigDecimal(areav);
			arp=arp.setScale(3, BigDecimal.ROUND_HALF_UP);
	 	  }
	 	  else  if (PrdVal.equalsIgnoreCase("EBP1") | PrdVal.equalsIgnoreCase("EBP2") | PrdVal.equalsIgnoreCase("EBP3") |
			      PrdVal.equalsIgnoreCase("EBP4"))
	 	  
	 	  {
	 		  	areav=1;
	 		  	actqty = new BigDecimal(1);
	 		  	arp = new BigDecimal(areav);
	 		  	arp=arp.setScale(3, BigDecimal.ROUND_HALF_UP);
	 	  }
	 	 else  if (PrdVal.equalsIgnoreCase("EBP5") | PrdVal.equalsIgnoreCase("EBP6") |PrdVal.equalsIgnoreCase("EBP7") |
	 			 PrdVal.equalsIgnoreCase("EBP8"))
	 	  
	 	  {
	 		 	areav=2;  //changed from 2sq to 1sqm as per Shankar email
	 		 	actqty = new BigDecimal(1);
	 		 	arp = new BigDecimal(areav);
	 		 	arp=arp.setScale(3, BigDecimal.ROUND_HALF_UP);
	 	  }
	 	  else
	 	  {
	 		String dqString = " select A from C_QUOTATIONLINE where C_QUOTATIONLINE_ID = ? ";
		 	String Aht = DB.getSQLValueString(null, dqString, qline_id);    
		 	float Ahtv=((Float.parseFloat(Aht)));
		 		 
		 	dqString = " select B from C_QUOTATIONLINE where C_QUOTATIONLINE_ID = ? ";
			String Bwd = DB.getSQLValueString(null, dqString, qline_id);    
			float Bwdv=((Float.parseFloat(Bwd)));
			 		
	 		dqString = " select unit from C_QUOTATIONLINE where C_QUOTATIONLINE_ID = ? ";
		 	String unitval = DB.getSQLValueString(null, dqString, qline_id);    
		 	if(unitval.equalsIgnoreCase("M"))
		 	{
		 		Ahtv=(float) (Ahtv*.001);
		 		Bwdv=(float) (Bwdv*.001);
		 		
		 	}
		 	else if (unitval.equalsIgnoreCase("I"))
		 	{
		 		Ahtv=(float) (Ahtv*0.0254);
		 		Bwdv=(float) (Bwdv*0.0254);
		 		
		 	}
		 	areav=Ahtv*Bwdv;
		 	arp = new BigDecimal(areav);
			arp=arp.setScale(3, BigDecimal.ROUND_HALF_UP);
	 	  }
		//lblArea.setText("Area(m2) ");
		String ar=Float.toString(areav);
		areaField.setText(arp.toString()); 
		 if (PrdVal.equalsIgnoreCase("AUVCDF") | PrdVal.equalsIgnoreCase("AUVCDB") |
			PrdVal.equalsIgnoreCase("AUVCDH") | PrdVal.equalsIgnoreCase("AUVCDS") |
			PrdVal.equalsIgnoreCase("AGVCDF") | PrdVal.equalsIgnoreCase("AGVCDB") | PrdVal.equalsIgnoreCase("AGVCDS") |
			PrdVal.equalsIgnoreCase("LVCDF") | PrdVal.equalsIgnoreCase("LVCDH") |
		    PrdVal.equalsIgnoreCase("3VCDF") | PrdVal.equalsIgnoreCase("3VCDB") |
		    PrdVal.equalsIgnoreCase("3VCDH") | PrdVal.equalsIgnoreCase("3VCDS") | 
		    PrdVal.equalsIgnoreCase("PI3VCD") | PrdVal.equalsIgnoreCase("PIAVCD"))
				{
				   String dqString = " select A from C_QUOTATIONLINE where C_QUOTATIONLINE_ID = ? ";
				   String Aht = DB.getSQLValueString(null, dqString, qline_id);    
				   float Ahtv=((Float.parseFloat(Aht)));
				 		 
				   dqString = " select B from C_QUOTATIONLINE where C_QUOTATIONLINE_ID = ? ";
				   String Bwd = DB.getSQLValueString(null, dqString, qline_id);    
				   float Bwdv=((Float.parseFloat(Bwd)));
					 		
			 	   dqString = " select unit from C_QUOTATIONLINE where C_QUOTATIONLINE_ID = ? ";
				   String unitval = DB.getSQLValueString(null, dqString, qline_id); 
			   if (Bwdv>0 &  Ahtv>0)
			    {
					double aMM=0,bMM=0;
					BigDecimal actQty=Env.ZERO;
					if (unitval.equalsIgnoreCase("I"))
	 				{
		 				aMM = Math.round(Ahtv*25);
		 				bMM = Math.round(Bwdv*25);
	 				}
					else if (unitval.equalsIgnoreCase("M"))
					{
						aMM = Ahtv;
						bMM = Bwdv;
					}
					if (aMM<=1200 & bMM<=1200)
						actQty = new BigDecimal(1);
					else if (aMM>1200 & bMM<=1200)
						actQty = new BigDecimal(2);
					else if (aMM<=1200 & bMM>1200)
						actQty = new BigDecimal(2);
					else if (aMM>1200 & bMM>1200)
						actQty = new BigDecimal(4);
					
					actqty=actQty;
					actqty=actQty;
					areav=areav/actQty.floatValue();
					ar=Float.toString(areav);
					arp = new BigDecimal(ar);
					arp=arp.setScale(3, BigDecimal.ROUND_HALF_UP);
					if (actQty.intValue() >1)
					{
						lblArea.setText("");
					lblArea.setText("Multi Section Area(m2) ");
					}
					else
					{
				 		
					lblArea.setText(" Area(m2) ");
					}
					
				}
							
			}
		 	else
			{
		 		
			lblArea.setText(" Area(m2) ");
			}
			areaField.setText(arp.toString()); 	
	}   //  initBPartner

	

	/**
	 *  Load Data - Order
	 *  @param C_Order_ID Order
	 *  @param forInvoice true if for invoice vs. delivery qty
	 *  @param M_Locator_ID
	 */
	protected void loadActuator ()
	{
		loadTableOIS(getActuatorData());
	}
	
	
	
	/**
	 *  Load Order/Invoice/Shipment data into Table
	 *  @param data data
	 */
	protected void loadTableOIS (Vector<?> data)
	{
		//  Remove previous listeners
		dialog.getMiniTable().getModel().removeTableModelListener(dialog);
		//  Set Model
		DefaultTableModel model = new DefaultTableModel(data, getOISColumnNames());
		model.addTableModelListener(dialog);
		dialog.getMiniTable().setModel(model);
		configureMiniTable(dialog.getMiniTable());
	
	}   //  loadOrder
	
	public void showWindow()
	{
		dialog.setVisible(true);
	}
	
	public void closeWindow()
	{
		dialog.dispose();
	}
	public void okWindow()
	{
		//dialog.dispose();
	}
	

	
	

}
