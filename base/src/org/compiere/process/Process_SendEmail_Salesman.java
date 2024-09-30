package org.compiere.process;
import java.util.HashMap;
import org.compiere.db.CConnection;
import org.compiere.model.MClient;
import org.compiere.util.DB;
import org.compiere.util.EMail;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Process_SendEmail_Salesman extends SvrProcess
{
	private int AD_PInstance_ID = 0;
	private int Record_ID 		= 0;
	private int AD_Client_ID 	= 0;
	private BigDecimal ord_id = null;
	private BigDecimal prjtype_id = null;

	protected void prepare()
	{
		AD_PInstance_ID = getAD_PInstance_ID();
		AD_Client_ID 	= getAD_Client_ID();
		Record_ID 		= getRecord_ID();
	}

	@Override
	protected String doIt() throws Exception 
	{
		String emailFrom  	   = null; String fromPWD 	      = null; 
		String emailTo    	   = null; String salesmanName    = null;
		String documentno 	   = null; BigDecimal grandTotal  = null;
		String orderno         = null;
		BigDecimal salesrep_id = null; String custname   	  = null;
		BigDecimal war_id 	   = null;
		
		
		String emailToSQL 	   = "select email, nvl(description,name) from ad_user where ad_user_id = ?";
		String emailFromSQL    = "select requestuser, requestuserpw from AD_client where ad_client_id = ?" ;
		String invoiceSQL      = "select i.documentno, i.grandtotal, i.salesrep_id, bp.name, p.value,i.c_order_id,"
				+ "p.c_projecttype_id,p.m_warehouse_id  from c_invoice i "
				+ "left outer join c_bpartner bp on i.c_bpartner_id =  bp.c_bpartner_id "
				+ "left outer join c_project p on i.c_project_id = p.c_project_id where i.c_invoice_id= ?";
		try
		{
			PreparedStatement ps = null;
			ResultSet rs 		 = null;
			ps = DB.prepareStatement(invoiceSQL, "DSPL");
			ps.setInt(1, Record_ID);
			rs = ps.executeQuery();
			if (rs.next())
			{
				documentno  = rs.getString(1);
				grandTotal  = rs.getBigDecimal(2);
				salesrep_id = rs.getBigDecimal(3);
				custname    = rs.getString(4);
				orderno     = rs.getString(5);
				ord_id      = rs.getBigDecimal(6);
				prjtype_id  = rs.getBigDecimal(7);
				war_id      = rs.getBigDecimal(8);
			}
			ps.close();
			rs.close();
		}
		catch(Exception e)
		{
			String s 	= e.getLocalizedMessage();
			String s1[] = s.split(":");
			System.out.println("Error :"+ s);
			addLog(0, null, null, "Process Stopped");
			addLog(0, null, null, s1[1]);
		}

		try
		{
			PreparedStatement pstmntCl= null;
			ResultSet rsCl= null;

			pstmntCl = DB.prepareStatement(emailFromSQL, "DSPL");
			pstmntCl.setInt(1, AD_Client_ID);
			rsCl= pstmntCl.executeQuery();
			if (rsCl.next())
			{
				emailFrom  = rsCl.getString(1);
				fromPWD    = rsCl.getString(2);
			}
			pstmntCl.close();
			rsCl.close();
		}
		catch (Exception e)
		{
			String s = e.getLocalizedMessage();
			System.out.println("Error :"+ s);
			String s1[] = s.split(":");
			addLog(0, null, null, "Process Stopped");
			addLog(0, null, null, s1[1]);
		}
		try 
		{
			PreparedStatement ps  = null;
			ResultSet rs 		  = null;
			ps = DB.prepareStatement(emailToSQL, "DSPL");
			ps.setBigDecimal(1,salesrep_id);
			rs = ps.executeQuery();

			if (rs.next())
			{
				emailTo =  rs.getString(1);
				salesmanName =  rs.getString(2);
			}
			ps.close();
			rs.close();

		}
		catch (Exception e)
		{
			String s = e.getLocalizedMessage();
			System.out.println("Error :"+ s);
			String s1[] = s.split(":");
			addLog(0, null, null, "Process Stopped");
			addLog(0, null, null, s1[1]);
		}
		String mailContent     = "Dear Colleague, " +"\n\n Please find the attached Sales Invoice - "+   documentno    +
				                 "\n Customer Name - " + custname +
								 "\n Order No - " + orderno + 
								 "\n Grand Total - " + grandTotal;
		
		
		HashMap<String,Object> mm = new HashMap<String,Object>();
		char str = 'N';
		char strLogo = 'Y';
		
		if (prjtype_id.doubleValue()==1000010 && war_id.doubleValue()==1000002)
		{
			mm.put("C_Order_ID", ord_id);
			mm.put("DUPYN", String.valueOf(str));
			mm.put("LOGOYN", String.valueOf(strLogo));
			String outFileName=  "/opt/Adempiere/SalesInvoice-Salesman/SalInvPrint_Door - " + Record_ID +".pdf";
			JasperPrint prnt = JasperFillManager.fillReport("/opt/Adempiere/reports/SalInvPrint_Door.jasper", mm,
					CConnection.get().getConnection(false, 8));
			JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, prnt);
			exporter.exportReport();

			File f = new File(outFileName);
			MClient client = MClient.get(getCtx());

			if (emailFrom  != null) 
			{
				EMail email = client.createEMail(emailFrom.trim(), emailTo.trim(),
						"Sales Invoice - " + documentno ,mailContent + "  \n \n \n Thanks & Regards" + "\n \n Stores & Delivery Section"+ "\n (Auto generated mail from Adempiere ERP)",false,fromPWD);
				email.addAttachment(f);
				email.send();
				addLog(0,null,null, "Email sent successfully");
			}
		}	
		else
		{
			mm.put("C_Invoice_ID", BigDecimal.valueOf(Record_ID));
			mm.put("DUPYN", String.valueOf(str));
			mm.put("LOGOYN", String.valueOf(strLogo));
			String outFileName=  "/opt/Adempiere/SalesInvoice-Salesman/SalInvPrint_wodis - " + Record_ID +".pdf";
			JasperPrint prnt = JasperFillManager.fillReport("/opt/Adempiere/reports/SalInvPrint_wodis.jasper", mm,
					CConnection.get().getConnection(false, 8));
			JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, prnt);
			exporter.exportReport();

			File f = new File(outFileName);
			MClient client = MClient.get(getCtx());

			if (emailFrom  != null) 
			{
				EMail email = client.createEMail(emailFrom.trim(), emailTo.trim(),
						"Sales Invoice - " + documentno ,mailContent + "  \n \n \n Thanks & Regards" + "\n \n Stores & Delivery Section"+ "\n (Auto generated mail from Adempiere ERP)",false,fromPWD);
				email.addAttachment(f);
				email.send();
				addLog(0,null,null, "Email sent successfully");
			}
		}
		
		String updateSQL = null;
		updateSQL 		 = "update c_invoice set btn_sendemailsalesman = 'Y' where c_invoice_id = " + Record_ID;

		try 
		{
			DB.executeUpdate(updateSQL, get_TrxName());
		}

		catch (Exception e)
		{
			String s = e.getLocalizedMessage();
			System.out.println("Error : " + s);
			String s1[] = s.split(":");
			addLog(0, null, null, "Process Stopped");
			addLog(0, null, null, s1[1]);
		}
		return " ";
	}
}

