package org.compiere.process;

//Generate PO From Requisition - Created By Subair on 13.01.2016

import java.sql.CallableStatement;
import java.util.logging.Level;

import org.compiere.util.DB;

public class Process_GenPo_Requisition extends SvrProcess{
	
	private int AD_PInstance_ID = 0;
	private int p_user_ID = 0;
	
	protected void prepare()
	{
		AD_PInstance_ID = getAD_PInstance_ID();
		p_user_ID = getAD_User_ID();
		
	}
	
	protected String doIt() throws Exception 
	{
		String sql_rec = "Select Record_ID FROM ad_pinstance where ad_pinstance_id = ? ";
    	int Record_Id = DB.getSQLValue(get_TrxName(), sql_rec, AD_PInstance_ID);
    	
		String sql = "{call Beta_GenPO_Requisition(?)}";
		try {
			CallableStatement cstmt = DB.prepareCall(sql);
			cstmt.setInt(1, AD_PInstance_ID);
			int no=cstmt.executeUpdate();
			cstmt.close();
			
			//Get New PO Doc No
			String SqlReq = "SELECT nvl(lastordgen,'N') FROM m_requisition WHERE m_requisition_id=?";
			String newOrd = DB.getSQLValueString(get_TrxName(), SqlReq, Record_Id);
			if (newOrd.equalsIgnoreCase("N"))
				addLog(0, null, null, "No orders generated");
			else
				addLog(0, null, null, "Purchase Order(s) generated successfully. The new order(s): " + newOrd);
		} catch (Exception e) {
			String s = e.getLocalizedMessage();
			System.out.println("Error : " + s);
			String s1[] = s.split(":");
			addLog(0, null, null, "Process Stopped");
			addLog(0, null, null, s1[1]);
		}
		
		return "";
	}
}

