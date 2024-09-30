package org.compiere.process;


import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.logging.Level;

import org.compiere.model.MClient;
import org.compiere.util.DB;
import org.compiere.util.EMail;

/*import java.io.File;
import java.util.HashMap;
import java.math.BigDecimal;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.compiere.db.CConnection;*/
public class Process_Generate_Project extends SvrProcess {

	private int AD_PInstance_ID    = 0;
	private int AD_Client_ID       = 0;
	private int AD_User_ID         = 0;
	private int Record_Id          = 0;

	public PreparedStatement pstmt = null;
	private int	p_customer_ID 	   = 0;

	protected void prepare() {
		AD_PInstance_ID = getAD_PInstance_ID();
		AD_Client_ID 	= getAD_Client_ID();
		AD_User_ID 		= getAD_User_ID();
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)	
				;
			else if (name.equals("C_BPartner_ID"))
				p_customer_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	} 

	protected String doIt() throws Exception {
		String DocNo=null, PrjVal=null;
		Integer C_Project_ID=0,  SalesRep_ID=0,PrjType_ID=0;
		Double NetAmt = 0.0;
		Double GrAmt  = 0.0;
		Double TaxAmt = 0.0;
		Double PrjAmt = 0.0;

		//Check BPartner is selected or not
		String sql_rec = "Select Record_ID FROM ad_pinstance where ad_pinstance_id = ? ";
		int Record_Id = DB.getSQLValue(get_TrxName(), sql_rec, AD_PInstance_ID);
		String sql_bp = "select nvl(c_bpartner_id,0) from c_quotation where c_quotation_id= ? ";
		int bpid = DB.getSQLValue(get_TrxName(), sql_bp, Record_Id);

		if (bpid == 0)
		{
			String sql_upd = "Update C_Quotation set C_BPartner_ID = " + p_customer_ID + " where c_quotation_id= " + Record_Id + " ";
			int no = DB.executeUpdate(sql_upd, get_TrxName()); 
			boolean com = DB.commit(true, get_TrxName());
		}
		String sql_bpname = "Select name from c_bpartner where c_bpartner_id = ?";
		String bpname = DB.getSQLValueString(get_TrxName(), sql_bpname, bpid);
		int no=0;
		String sql = "{call proc_gen_project(?)}";
		try {
			CallableStatement cstmt = DB.prepareCall(sql);
			cstmt.setInt(1,AD_PInstance_ID);
			no=cstmt.executeUpdate();
			cstmt.close();
			addLog(0, null, null, "Process Completed Successfully");
		} catch (Exception e) {
			String s=e.getLocalizedMessage();
			System.out.println("Error : " + s);
			String s1[] = s.split(":");
			addLog(0, null, null, "Process Stopped");
			addLog(0, null, null, s1[1]);
		}

		if (no>0)
		{
			String sqlQtn = "SELECT cq.documentno,cq.salesrep_id,cq.c_project_id,cq.c_projecttype_id,cq.createdby,cp.value, cq.netamt, cp.gramt, cp.taxamt, cp.committedamt" +
					" FROM c_quotation cq Left JOIN c_project cp ON cp.c_project_id=cq.c_project_id " +
					" WHERE cq.c_quotation_id= ?";
			PreparedStatement pstmtQtn = null;
			ResultSet rsQtn = null;
			try 
			{
				pstmtQtn = DB.prepareStatement(sqlQtn, "DSPL");
				pstmtQtn.setInt(1, Record_Id);
				rsQtn = pstmtQtn.executeQuery();

				if(rsQtn.next()) 
				{
					DocNo 	     = rsQtn.getString(1);
					SalesRep_ID  = rsQtn.getInt(2);
					C_Project_ID = rsQtn.getInt(3);
					PrjType_ID 	 = rsQtn.getInt(4);
					PrjVal 		 = rsQtn.getString(6);
					NetAmt 		 = rsQtn.getBigDecimal(7).doubleValue();
					GrAmt 		 = rsQtn.getBigDecimal(8).doubleValue();
					TaxAmt 		 = rsQtn.getBigDecimal(9).doubleValue();
					PrjAmt 		 = rsQtn.getBigDecimal(10).doubleValue();
				}
				rsQtn.close();
				pstmtQtn.close();
			}
			catch(Exception e){  } // end of fn_getcelladdr
			finally { DB.close(rsQtn, pstmtQtn);   }	
			if (PrjType_ID == 1000002)   //GRILLS
			{
				MClient client = MClient.get(getCtx());

				String useremail_id = "";
				useremail_id = DB.getSQLValueString(get_TrxName(),"select nvl(email, emailuser) from ad_user where ad_user_id=?",AD_User_ID);
				String sqlfrom = "select nvl(Requestuser,RequestEMAIL) from AD_Client where AD_Client_ID= ?" ;
				String email_from = DB.getSQLValueString(get_TrxName(), sqlfrom, AD_Client_ID);
				String sqlfromPWD = "select nvl(RequestUSERPW,' ') from AD_Client where AD_Client_ID= ?" ;
				String fromPWD = DB.getSQLValueString(get_TrxName(), sqlfromPWD, AD_Client_ID);

				if (useremail_id  != null) {
					System.out.println("mail " + useremail_id);
					EMail email = client.createEMail(email_from,useremail_id,
							"Order # " + PrjVal + " - "+bpname,
							"Dear Colleague, \n \n Please find the order generated as below : "
									+ "\n \n Order No : "+ PrjVal + "\n Quotation No. : " + DocNo
									+ "\n Customer : " + bpname
									+ "\n Order Amount (with out VAT) : " + String.format("%,.2f", GrAmt)
									+ "\n Order Amount (with VAT) : "  + String.format("%,.2f", PrjAmt)
									+ "\n Net Amount for Beta : " + String.format("%,.2f", GrAmt*0.975*0.65) 
									+ "\n \n \n Thanks & Regards \n \n (Auto generated mail from Adempiere ERP)",false,fromPWD);
					email.send();
				}
			}
		}

		/*String outFileName = "/opt/Adempiere/reports/Temp/Order_" + PrjVal +".pdf";

		String[] email_id = new String[1];


		if (PrjType_ID == 1000010)   //DOORS
		{
			email_id[0] = DB.getSQLValueString(get_TrxName(),
					"select PRODDOOREMAIL from ad_client where ad_client_id=?",AD_Client_ID);
			fileName="/opt/Adempiere/reports/DoorLf_Fr.jasper";
		}
		else if (PrjType_ID == 1000002)  //GRILLS
		{
			email_id[0] = DB.getSQLValueString(get_TrxName(),
					"select PRODGRILLEMAIL from ad_client where ad_client_id=?",AD_Client_ID);
			fileName="/opt/Adempiere/reports/PRJ_GD_QTN.jasper";
		}

		String sqlfrom = "select nvl(EMAIL,EMAILUSER) from AD_User where AD_User_ID= ?" ;
		String email_from = DB.getSQLValueString(get_TrxName(), sqlfrom, User_ID);
		String sqlfromPWD = "select nvl(EMAILUSERPW,' ') from AD_User where AD_User_ID= ?" ;
		String fromPWD = DB.getSQLValueString(get_TrxName(), sqlfromPWD, User_ID);
		System.out.println("QTN _ID " + Record_Id + "C_Project_ID "+ C_Project_ID);
		if (PrjType_ID == 1000010)   //DOORS
		{
			String sql2 = "{call BETA_DOORQTN_PRINT(?)}";
			try 
			{
				CallableStatement cstmt = DB.prepareCall(sql2);
				cstmt.setInt(1,AD_PInstance_ID);
				cstmt.executeUpdate();
				cstmt.close();
			} catch (Exception e) {
				addLog(0, null, null,e.getMessage());
				return "";
			}
		}
		else if (PrjType_ID == 1000002)  //GRILLS
		{
			String sql2 = "{call Beta_Qtn_GD_Proc(?,?,?,?)}";
			try 
			{
				CallableStatement cstmt = DB.prepareCall(sql2);
				cstmt.setInt(1, C_Project_ID);
				cstmt.setString(2, "QTN");
				cstmt.setInt(3, Record_Id);
				cstmt.setString(4, "Y");
				cstmt.executeUpdate();
				cstmt.close();
			} catch (Exception e) {
				addLog(0, null, null,e.getMessage());
				return "";
			}
		} 

		HashMap mm = new HashMap();
		JasperPrint prnt = JasperFillManager.fillReport(fileName, mm,
				CConnection.get().getConnection(false, 8));
		JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, prnt);
		exporter.exportReport();

		File f = new File(outFileName);*/
		return " ";
	}
}
