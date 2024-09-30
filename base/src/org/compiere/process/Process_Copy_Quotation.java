package org.compiere.process;

//Copy Quotation (Full) - Grills & Door Quotation  Created By Subair on 20.04.2014

import java.sql.CallableStatement;
import java.util.logging.Level;

import org.compiere.util.DB;

public class Process_Copy_Quotation extends SvrProcess{
	
	private int AD_PInstance_ID = 0;
	private int p_customer_ID = 0;
	private int p_qtn_ID = 0;
	private int p_user_ID = 0;
	
	protected void prepare()
	{
		AD_PInstance_ID = getAD_PInstance_ID();
		p_user_ID = getAD_User_ID();
		
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) 
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_BPartner_ID"))
				p_customer_ID = para[i].getParameterAsInt();
			else if (name.equals("C_QUOTATION_ID"))
				p_qtn_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}
	
	protected String doIt() throws Exception 
	{
		String sql = "{call Beta_Qtn_Copy(?)}";
		try {
			CallableStatement cstmt = DB.prepareCall(sql);
			cstmt.setInt(1, AD_PInstance_ID);
			int no=cstmt.executeUpdate();
			cstmt.close();
			
			//Get New Qtn Doc No
			String SqlQtn = "SELECT max(documentno) FROM c_quotation WHERE copyfromqtn=? AND createdby=?";
			String newQtn = DB.getSQLValueString(get_TrxName(), SqlQtn, p_qtn_ID,p_user_ID);
			
			addLog(0, null, null, "Quotation Copied Successfully. New Quotaion # " + newQtn);
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
