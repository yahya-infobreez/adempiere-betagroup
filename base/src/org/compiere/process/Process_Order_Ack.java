package org.compiere.process;
import java.io.File;

import java.math.BigDecimal;

import java.sql.CallableStatement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.compiere.db.CConnection;
import org.compiere.model.MClient;
import org.compiere.util.DB;
import org.compiere.util.EMail;
import org.compiere.util.Env;

public class Process_Order_Ack extends SvrProcess{
	
	private int AD_PInstance_ID = 0;
	private BigDecimal Record_Id = Env.ZERO;
	private int AD_User_ID = 0;
	private int AD_Client_ID = 0;
	
	protected void prepare()
	{
		AD_PInstance_ID = getAD_PInstance_ID();
		AD_Client_ID = getAD_Client_ID();
		String sqlRec ="SELECT p_number FROM ad_pinstance_para WHERE parametername='C_Project_ID' and ad_pinstance_id=?";
		Record_Id = DB.getSQLValueBD(get_TrxName(), sqlRec, AD_PInstance_ID);
		AD_User_ID = getAD_User_ID();
		
	}
	
	protected String doIt() throws Exception
	{
		int no=0;
		String PrjVal=null, ReviseYN="N", mailSub =null,mailContent=null,email_idcc=null;
		Integer salesrep_id=0,PrjType_ID=0;
		
		String sql = "{call BETA_SENDORDACK_PROC(?)}";
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
		
		//Added by Subair on 15.09.2015  For Revised option in the print
		String sqlRevise = "select REVISE_SCHDELDATE_YN FROM c_project WHERE c_project_id= ?" ;
		ReviseYN = DB.getSQLValueString(get_TrxName(), sqlRevise, Record_Id);
		if (ReviseYN.equalsIgnoreCase("Y"))
		{
			mailSub = "Revised Order Acknowledgment  # " ;
			mailContent = "Dear Sir, \n \n Please find the revised acknowledgment for the order # ";
		}
		else
		{
			mailSub = "Order Acknowledgment  # ";
			mailContent = "Dear Sir, \n \n Please find the acknowledgment for the order # ";
		}
		//End of Revised option in the print  
		
		if (no>0)
		{
			String sqlQtn = "SELECT value,c_projecttype_id,salesrep_id FROM c_project WHERE c_project_id=?";
			PreparedStatement pstmtQtn = null;
			ResultSet rsQtn = null;
			try 
			{
				pstmtQtn = DB.prepareStatement(sqlQtn, "DSPL");
				pstmtQtn.setBigDecimal(1, Record_Id);
				rsQtn = pstmtQtn.executeQuery();
			
				if(rsQtn.next()) 
				{
					PrjVal = rsQtn.getString(1);
					PrjType_ID = rsQtn.getInt(2);
					salesrep_id=rsQtn.getInt(3);
				}
				rsQtn.close();
		   		pstmtQtn.close();
			 }
			catch(Exception e){  } // end of fn_getcelladdr
			finally { DB.close(rsQtn, pstmtQtn);   }
			
			//String fileName="C:\\Adempiere\\Reports\\OrderAck.jasper";
			//String outFileName = "C:\\Adempiere\\Temp\\Order_Ack_" + PrjVal +".pdf";

			String fileName="/opt/Adempiere/reports/OrderAck.jasper";
			String outFileName = "/opt/Adempiere/Temp/Order_Ack_" + PrjVal +".pdf";
			
			String[] email_id = new String[1];
			String sqlsal = "select nvl(EMAIL,EMAILUSER) from AD_User where AD_User_ID= ?" ;
			email_id[0] = DB.getSQLValueString(get_TrxName(), sqlsal, salesrep_id);
			
		/*	//Attach CC for ADF orders
			if (salesrep_id ==1001316)  //Mohd. Kamil 
			{
				email_idcc = DB.getSQLValueString(get_TrxName(),
						"select ADFCCMAIL from ad_client where ad_client_id=?",AD_Client_ID);
			}
			//End of Attach CC for ADF orders    */
			
			String sqlfrom = "select nvl(EMAIL,EMAILUSER) from AD_User where AD_User_ID= ?" ;
			String email_from = DB.getSQLValueString(get_TrxName(), sqlfrom, AD_User_ID);
			String sqlfromPWD = "select nvl(EMAILUSERPW,' ') from AD_User where AD_User_ID= ?" ;
			String fromPWD = DB.getSQLValueString(get_TrxName(), sqlfromPWD, AD_User_ID);
			
			HashMap<String,BigDecimal> mm = new HashMap<String,BigDecimal>();
    		mm.put("C_Project_ID", Record_Id);
			JasperPrint prnt = JasperFillManager.fillReport(fileName, mm,
					CConnection.get().getConnection(false, 8));
			JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, prnt);
			exporter.exportReport();

			File f = new File(outFileName);
			MClient client = MClient.get(getCtx());
			
			for (int i = 0; i < email_id.length; i++) {
				if (email_id[i] != null) {
					System.out.println("mail " + email_id[i]);
					EMail email = client.createEMail(email_from,email_id[i],
								  mailSub + PrjVal + "",mailContent + PrjVal + ""
								  + " . \n \n \n Thanks & Regards \n \n (Auto generated mail from Adempiere ERP)",false,fromPWD);
					email.addAttachment(f);
					
				/*	if (salesrep_id ==1001316)  //Mohd. Kamil 
						email.addCc(email_idcc);   */
					
					email.send();
					
				}
			}
		}
		
		return "";
	}
}
