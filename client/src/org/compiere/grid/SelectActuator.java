
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

import org.compiere.minigrid.IMiniTable;
import org.compiere.model.GridTab;
import org.compiere.model.MOrder;
import org.compiere.model.MRMA;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

public abstract class SelectActuator implements ISelectActuator
{
	/**	Logger			*/
	protected CLogger log = CLogger.getCLogger(getClass());


	private GridTab gridTab;

	private String title;

	private boolean initOK = false;

	public SelectActuator(GridTab gridTab) {
		this.gridTab = gridTab;
	}

	public abstract boolean dynInit() throws Exception;

	public abstract void info();

	public abstract boolean save(IMiniTable miniTable, String trxName);

	/**
	 *	Init OK to be able to make changes?
	 *  @return on if initialized
	 */
	public boolean isInitOK()
	{
		return initOK;
	}

	public void setInitOK(boolean initOK)
	{
		this.initOK = initOK;
	}

	

	public void showWindow()
	{

	}

	public void closeWindow()
	{

	}

	public GridTab getGridTab()
	{
		return gridTab;
	}

	/**
	 * Get Warehouse from window's context
	 * @return warehouse id
	 */
	public int getM_Warehouse_ID()
	{
		return Env.getContextAsInt(Env.getCtx(), gridTab.getWindowNo(), "M_Warehouse_ID");
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}

