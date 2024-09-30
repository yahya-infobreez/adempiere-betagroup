package org.compiere.process;
import java.io.File;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



import org.compiere.db.CConnection;
import org.compiere.model.MClient;
import org.compiere.util.DB;
import org.compiere.util.EMail;

public class Process_Project_Cancel extends SvrProcess{
	
	private int AD_PInstance_ID = 0;
	private int Record_Id = 0;
	private int AD_Client_ID = 0;
	private int AD_User_ID = 0;
	
	protected void prepare()
	{
		
		AD_PInstance_ID = getAD_PInstance_ID();
		Record_Id = getRecord_ID();
		AD_Client_ID = getAD_Client_ID();
		
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) 
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("AD_User_ID"))
				AD_User_ID = para[i].getParameterAsInt();
		}
	}
	
	protected String doIt() throws Exception
	{
	
		int no=0;
		String PrjVal=null;
		Integer PrjType_ID=0;
		
		String sql = "{call BETA_CANCELORDER_PROC(?)}";
		try {
			CallableStatement cstmt = DB.prepareCall(sql);
			cstmt.setInt(1, AD_PInstance_ID);
			no=cstmt.executeUpdate();
			cstmt.close();
			addLog(0, null, null, "Process Completed Successfully");
		} catch (Exception e) {
			String s = e.getLocalizedMessage();
			System.out.println("Error : " + s);
			String s1[] = s.split(":");
			addLog(0, null, null, "Process Stopped");
			addLog(0, null, null, s1[1]);
		}
		System.out.println("no "+no);
		
		if (no>0)
		{
			    String sqlQtn = "SELECT value,c_projecttype_id FROM c_project WHERE c_project_id=?";
				PreparedStatement pstmtQtn = null;
				ResultSet rsQtn = null;
				try 
				{
					pstmtQtn = DB.prepareStatement(sqlQtn, "DSPL");
					pstmtQtn.setInt(1, Record_Id);
					rsQtn = pstmtQtn.executeQuery();
				
					if(rsQtn.next()) 
					{
						PrjVal = rsQtn.getString(1);
						PrjType_ID = rsQtn.getInt(2);
					}
					rsQtn.close();
			   		pstmtQtn.close();
				 }
				catch(Exception e){  } 
				finally { DB.close(rsQtn, pstmtQtn);   }

				String[] email_id = new String[1];
				
				//Production Email id
				email_id[0] = DB.getSQLValueString(get_TrxName(),
						"select PRODEMAIL from c_projecttype where c_projecttype_id=?",PrjType_ID);
				//End of Production Email id
				
				String sqlfrom = "select nvl(EMAIL,EMAILUSER) from AD_User where AD_User_ID= ?" ;
				String email_from = DB.getSQLValueString(get_TrxName(), sqlfrom, AD_User_ID);
				String sqlfromPWD = "select nvl(EMAILUSERPW,' ') from AD_User where AD_User_ID= ?" ;
				String fromPWD = DB.getSQLValueString(get_TrxName(), sqlfromPWD, AD_User_ID);
				
				MClient client = MClient.get(getCtx());

				for (int i = 0; i < email_id.length; i++) {
					if (email_id[i] != null) {
						System.out.println("mail " + email_id[i]);
						EMail email = client.createEMail(email_from,email_id[i],
									  "Order Cancellation # " + PrjVal + "",
									  "Dear Sir, \n \n Please note that, Order No# " + PrjVal + " has been cancelled."
									  + " . \n \n \n Thanks & Regards \n \n (Auto generated mail from Adempiere ERP)",false,fromPWD);
						email.send();
					}
				}
		    }
			return "";
	}
}
