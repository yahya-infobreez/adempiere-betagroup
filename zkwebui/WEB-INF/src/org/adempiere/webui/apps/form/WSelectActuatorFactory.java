package org.adempiere.webui.apps.form;

import java.util.HashMap;
import java.util.logging.Level;

import org.compiere.grid.ISelectActuator;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_QUOTATIONLINE;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

public class WSelectActuatorFactory
{
	/**	Static Logger	*/
	private static CLogger 	s_log = CLogger.getCLogger (WSelectActuatorFactory.class);

	/** Registered classes map (AD_Table_ID -> Class) */
	private static HashMap<Integer, Class<? extends ISelectActuator>> s_registeredClasses = null;

	/**
	 * Register custom VCreateFrom* class
	 * @param ad_table_id
	 * @param cl custom class
	 */
	public static final void registerClass(int ad_table_id, Class<? extends ISelectActuator> cl)
	{
		s_registeredClasses.put(ad_table_id, cl);
		s_log.info("Registered AD_Table_ID="+ad_table_id+", Class="+cl);
	}
	
	static
	{
		// Register defaults:
		s_registeredClasses = new HashMap<Integer, Class<? extends ISelectActuator>>();
		s_registeredClasses.put(I_C_QUOTATIONLINE.Table_ID, WSelectActuatorUI.class);
		
	
	}
	
	/**
	 *  Factory - called from APanel
	 *  @param  mTab        Model Tab for the trx
	 *  @return JDialog
	 */
	public static ISelectActuator create (GridTab mTab)
	{
		//	dynamic init preparation
		
		int AD_Table_ID =1000025;
		//int AD_Table_ID = Env.getContextAsInt(Env.getCtx(), mTab.getWindowNo(), "C_QUOTATIONLINE_ID");
		
		
		ISelectActuator retValue = null;
		Class<? extends ISelectActuator> cl = s_registeredClasses.get(AD_Table_ID);
		if (cl != null)
		{
			try
			{
				java.lang.reflect.Constructor<? extends ISelectActuator> ctor = cl.getConstructor(GridTab.class);
				retValue = ctor.newInstance(mTab);
			}
			catch (Throwable e)
			{
				s_log.log(Level.SEVERE, e.getLocalizedMessage(), e);
				return null;
			}
		}
		if (retValue == null)
		{
			s_log.info("Unsupported AD_Table_ID=" + AD_Table_ID);
			return null;
		}
		return retValue;
	}   //  create
}

