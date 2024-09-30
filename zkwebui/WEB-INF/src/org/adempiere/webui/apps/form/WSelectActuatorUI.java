
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

package org.adempiere.webui.apps.form;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Checkbox;
import org.adempiere.webui.component.Grid;
import org.adempiere.webui.component.GridFactory;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.ListModelTable;
import org.adempiere.webui.component.Listbox;
import org.adempiere.webui.component.ListboxFactory;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Row;
import org.adempiere.webui.component.Rows;
import org.adempiere.webui.editor.WEditor;
import org.adempiere.webui.editor.WLocatorEditor;
import org.adempiere.webui.editor.WSearchEditor;
import org.adempiere.webui.editor.WStringEditor;
import org.adempiere.webui.event.ValueChangeEvent;
import org.adempiere.webui.event.ValueChangeListener;
import org.adempiere.webui.window.FDialog;
import org.compiere.apps.ADialog;
import org.compiere.grid.SelectActuatorActuator;
import org.compiere.grid.ed.VLookup;
import org.compiere.model.GridTab;
import org.compiere.model.MInvoice;
import org.compiere.model.MLocatorLookup;
import org.compiere.model.MLookup;
import org.compiere.model.MLookupFactory;
import org.compiere.model.MProduct;
import org.compiere.model.MRMA;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zul.Space;

public class WSelectActuatorUI extends SelectActuatorActuator implements EventListener, ValueChangeListener
{
	private static final int WINDOW_CUSTOMER_RETURN = 53097;

	private static final int WINDOW_RETURN_TO_VENDOR = 53098;

	private WSelectActuatorWindow window;
	
	public WSelectActuatorUI(GridTab tab) 
	{
		super(tab);
		log.info(getGridTab().toString());
		
		window = new WSelectActuatorWindow(this, getGridTab().getWindowNo());
		
		p_WindowNo = getGridTab().getWindowNo();

		try
		{
			if (!dynInit())
				return;
			zkInit();
			setInitOK(true);
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, "", e);
			setInitOK(false);
		}
		AEnv.showWindow(window);
	}
	
	/** Window No               */
	private int p_WindowNo;

	/**	Logger			*/
	private CLogger log = CLogger.getCLogger(getClass());
	
    protected Label prLabel = new Label();
  	protected WSearchEditor prField;
	//String PrdVal="";
	protected WSearchEditor qline;
	
	//private JButton searchButton = new JButton();
	protected Label lblonoffmod = new Label();
	protected Label lblvolt = new Label();
    
	protected Listbox onoffmodField = ListboxFactory.newDropdownListbox();
	
	protected Listbox voltField = ListboxFactory.newDropdownListbox();
	
	protected Label lblArea = new Label();
	//protected Label areaField = new Label();
	//protected WStringEditor areaField = new WStringEditor();
	protected Checkbox withswitch =  new Checkbox();
	protected Checkbox withspringret =  new Checkbox();
	protected Checkbox rmvarea =  new Checkbox();

	/**
	 *  Dynamic Init
	 *  @throws Exception if Lookups cannot be initialized
	 *  @return true if initialized
	 */
	public boolean dynInit() throws Exception
	{
		log.config("");
		super.dynInit();
		window.setTitle(getTitle());
				
		withswitch.setSelected(false);
		withswitch.addActionListener(this);
		withspringret.setSelected(false);
		withspringret.addActionListener(this);
		rmvarea.setSelected(false);
		rmvarea.addActionListener(this);
		//searchButton.setText(Msg.getMsg(Env.getCtx(), "Search Actuator"));
		//searchButton.addActionListener(this);
		//areaField = new WStringEditor ("area", false, false, true, 10, 30, null, null);
		
		initArea();
		onoffmodField.addActionListener(this);
		voltField.addActionListener(this);
		voltField.setWidth("20");
		//areaField.setReadWrite(false); 
		
		return true;
	}   //  dynInit
	
	protected void zkInit() throws Exception
	{
    	boolean isRMAWindow = ((getGridTab().getAD_Window_ID() == WINDOW_RETURN_TO_VENDOR) || (getGridTab().getAD_Window_ID() == WINDOW_CUSTOMER_RETURN)); 

    	lblonoffmod.setText("OnOff / Modulating");
    	lblvolt.setText(Msg.getElement(Env.getCtx(), "Volt", false));
    	//lblArea.setText(Msg.translate(Env.getCtx(), "Area(m2) "));
    	
    	withswitch.setText("With Auxiliary Switch [Y/N]");
    	withspringret.setText("With Spring Return [Y/N]");
    	rmvarea.setText("Remove Areawise filtering [Y/N]");
    	
    	KeyNamePair pp1 = new KeyNamePair(0,"ON/OFF");
    	onoffmodField.addItem(pp1);
    	KeyNamePair pp = new KeyNamePair(1,"MODULATING");
    	onoffmodField.addItem(pp);
    	
    	KeyNamePair pv = new KeyNamePair(230,"230V");
    	voltField.addItem(pv);
    	KeyNamePair pv1 = new KeyNamePair(24,"24V");
    	voltField.addItem(pv1);
    	
		Borderlayout parameterLayout = new Borderlayout();
		parameterLayout.setHeight("110px");
		parameterLayout.setWidth("100%");
    	Panel parameterPanel = window.getParameterPanel();
		parameterPanel.appendChild(parameterLayout);
		
		Grid parameterStdLayout = GridFactory.newGridLayout();
    	Panel parameterStdPanel = new Panel();
		parameterStdPanel.appendChild(parameterStdLayout);

		Center center = new Center();
		parameterLayout.appendChild(center);
		center.appendChild(parameterStdPanel);
		
		
		
		parameterStdPanel.appendChild(parameterStdLayout);
		Rows rows = (Rows) parameterStdLayout.newRows();
		Row row = rows.newRow();
		row.appendChild(lblArea);
		//row.appendChild(lblArea.rightAlign());
		//row.appendChild(new Space());
		row.appendChild(lblvolt.rightAlign());
		row.appendChild(voltField);
		//row.appendChild(lblonoffmod.rightAlign());
		row.appendChild(lblonoffmod);
		row.appendChild(onoffmodField);
		
		row = rows.newRow();
		row.appendChild(withswitch);
		//row.appendChild(new Space());
		row = rows.newRow();
		row.appendChild(withspringret);
		row = rows.newRow();
		row.appendChild(rmvarea);
		
		if (PrdVal.equalsIgnoreCase("EBP1") | PrdVal.equalsIgnoreCase("EBP2") | PrdVal.equalsIgnoreCase("EBP3") |
			PrdVal.equalsIgnoreCase("EBP4") | PrdVal.equalsIgnoreCase("EBP5") | PrdVal.equalsIgnoreCase("EBP6") |
			PrdVal.equalsIgnoreCase("EBP7") | PrdVal.equalsIgnoreCase("EBP8") |PrdVal.equalsIgnoreCase("SDVBP")
			| PrdVal.equalsIgnoreCase("SDVBPE"))
       {
      	
			withspringret.setEnabled(false);
			onoffmodField.removeAllItems();
			onoffmodField.addItem(pp);
			OnOffMod="M";
       }
	   if (PrdVal.equalsIgnoreCase("MPRD"))
	   {
		   withspringret.setSelected(true);
		   withspringret.setEnabled(false);
	   }
		
		loadActuator();
	}

	private boolean 	m_actionActive = false;
	
	/**
	 *  Action Listener
	 *  @param e event
	 * @throws Exception 
	 */
	public void onEvent(Event e) throws Exception
	{
		if (m_actionActive)
			return;
		m_actionActive = true;
		
		if (e.getTarget().equals(onoffmodField))
		{
			KeyNamePair pp = onoffmodField.getSelectedItem().toKeyNamePair();
			if (pp == null )
				;
			if( pp.getKey()==0)
				OnOffMod="O";
			else
				OnOffMod="M";
			loadActuator();
			
		}
		
		if (e.getTarget().equals(voltField))
		{
				
			KeyNamePair pp =voltField.getSelectedItem().toKeyNamePair();
			
			if (pp != null)
				volt = pp.getKey();
			loadActuator();
		}
		if (e.getTarget().equals(withspringret))
		{
			
			if(withspringret.isSelected()==true)
				fspring='T';
			else
				fspring='F';
			loadActuator();
		}
		
		if (e.getTarget().equals(withswitch))
		{
			if(withswitch.isSelected()==true) 
				fswitch='T';
			else
				fswitch='F';	
			
			loadActuator();
		}
		if (e.getTarget().equals(rmvarea))
		{
			if(rmvarea.isSelected()==true) 
				narea='T';
			else
				narea='F';	
			//System.out.print("loadActuator******"+narea+"&&&&&");
			
			loadActuator();
		}
		
		m_actionActive = false;
		
	}
	
	
	/**
	 *  Change Listener
	 *  @param e event
	 */
	public void valueChange (ValueChangeEvent e)
	{
		log.config(e.getPropertyName() + "=" + e.getNewValue());

		//  BPartner - load Order/Invoice/Shipment
		if (e.getPropertyName().equals("C_BPartner_ID"))
		{
			int C_BPartner_ID = ((Integer)e.getNewValue()).intValue();
			//initBPOrderDetails (C_BPartner_ID, true);
		}
		window.tableChanged(null);
	}   //  vetoableChange
	
	
	
	protected void initArea() throws Exception
	{
			//  load BPartner
		BigDecimal arp = null;
		int AD_Column_ID = 1000455;        //  C_quotationline.M_Product_ID
		
		///////////////////////////
		MLookup lookup = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.Search);
		
		prField = new WSearchEditor ("M_Product_ID", true, false, true, lookup);
		
		//////////////////////////
		//MLookup lookup = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.Search);
		//prField = new VLookup ("M_Product_ID", true, false, true, lookup);
		
		
		
		AD_Column_ID=1000453;
		MLookup lookup1 = MLookupFactory.get (Env.getCtx(), p_WindowNo, 0, AD_Column_ID, DisplayType.Search);
		qline = new WSearchEditor ("C_QUOTATIONLINE_ID", true, false, true, lookup1);
		
		
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
	 		  areav=2;                //changed from 2sqm to 1sqm on 22/4/2019 as per Shankar email
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
	 		
	 	 String ar="Area(m2)  :  "+arp;
	 	 ar=Float.toString(areav);
		 if (PrdVal.equalsIgnoreCase("AUVCDF") | PrdVal.equalsIgnoreCase("AUVCDB") |
			PrdVal.equalsIgnoreCase("AUVCDH") | PrdVal.equalsIgnoreCase("AUVCDS") |
			PrdVal.equalsIgnoreCase("AGVCDF") | PrdVal.equalsIgnoreCase("AGVCDB") | PrdVal.equalsIgnoreCase("AGVCDS") |
			PrdVal.equalsIgnoreCase("LVCDF") | PrdVal.equalsIgnoreCase("LVCDH") |
		    PrdVal.equalsIgnoreCase("3VCDF") | PrdVal.equalsIgnoreCase("3VCDB") |
		    PrdVal.equalsIgnoreCase("3VCDH") | PrdVal.equalsIgnoreCase("3VCDS") | 
		    PrdVal.equalsIgnoreCase("PI3VCD") | PrdVal.equalsIgnoreCase("PIAVCD") | PrdVal.equalsIgnoreCase("MPRD"))
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
					if (actQty.intValue() >1)
					{
						areav=areav/actQty.floatValue();
						arp = new BigDecimal(areav);
						arp=arp.setScale(3, BigDecimal.ROUND_HALF_UP);
						lblArea.setText("");
						ar="Multi Section Area(m2)  :  "+arp;
					}
					else
					{
						arp = new BigDecimal(areav);
						arp=arp.setScale(3, BigDecimal.ROUND_HALF_UP);
						lblArea.setText("");
						ar="Area(m2)  :  "+arp;
					}
					
				}
			  
			 }
		 else{
			 lblArea.setText("");
			 ar="Area(m2)  :  "+arp;
		 	}
		 
		lblArea.setText(Msg.translate(Env.getCtx(), new String(ar)));
			 
		 
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
		
		window.getWListbox().clear();
		
		//  Remove previous listeners
		window.getWListbox().getModel().removeTableModelListener(window);
		//  Set Model
		ListModelTable model = new ListModelTable(data);
		model.addTableModelListener(window);
		window.getWListbox().setData(model, getOISColumnNames());
		//
		
		configureMiniTable(window.getWListbox());
	}   //  loadOrder
	
	public void showWindow()
	{
			window.setVisible(true);
	}
	
	public void closeWindow()
	{
		window.dispose();
	}
}
