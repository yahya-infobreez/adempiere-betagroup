package com.betagroup.einvoice.process;
import java.math.BigDecimal;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrder;
import org.compiere.model.MOrg;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import com.betagroup.einvoice.EInvoiceXmlFactory;
import com.betagroup.einvoice.ZatcaApiHelper;
import com.betagroup.einvoice.api.model.InvoiceResult;

import oasis.names.specification.ubl.schema.xsd.invoice_2.Invoice;

import java.util.logging.Level;


public class Process_EInvoiceCompliance extends SvrProcess{

	private int p_C_Invoice_ID;
	private static CLogger s_log = CLogger.getCLogger(Process_EInvoiceCompliance.class);


	protected void prepare()
	{
		p_C_Invoice_ID = getRecord_ID();
		if(p_C_Invoice_ID == 0) {
			ProcessInfoParameter[] para = getParameter();
			for (int i = 0; i < para.length; i++)
			{
				String name = para[i].getParameterName();
				if (para[i].getParameter() == null)
					;
				else if (name.equals("C_Invoice_ID"))
					p_C_Invoice_ID = ((BigDecimal)para[i].getParameter()).intValue();
				else
					log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
			}
		}
	}

	protected String doIt() throws Exception 
	{
		MInvoice minvoice = null;
		if (p_C_Invoice_ID != 0)
		{
			minvoice = MInvoice.get(getCtx(), p_C_Invoice_ID);
		} 
		if(minvoice == null) {
			throw new Exception("No invoice found ");
		}
		
		MOrg org = MOrg.get(getCtx(), minvoice.getAD_Org_ID());
		MClient client = MClient.get(getCtx(), minvoice.getAD_Client_ID());
		Invoice invoiceXml = EInvoiceXmlFactory.createInvoiceXml(minvoice);
		byte[] invoiceData = EInvoiceXmlFactory.canonicalize(invoiceXml, false);

		// Send for compliance check
		ZatcaApiHelper apiHelper = new ZatcaApiHelper();
		apiHelper.setAuth(org.getCertificate(), org.getZatcaSecret());
		
		InvoiceResult response = apiHelper.checkInvoiceCompliance(minvoice.getInvoiceHash(), minvoice.getUUID(), invoiceData);
		System.out.print(response);
		
		if(response != null) {
			if(response.getErrors() != null && !response.getErrors().isEmpty()) {
				String msg = "Error: Invoice submission for Compliance with FATOORA portal failed. "
						+ response.getErrors().toString();
				s_log.saveError("ZatcaComplianceFailed", msg);
				return msg;
			} else {
				String msg = "Success: Invoice submission for Compliance with FATOORA portal completed successfully. "
						+ minvoice.toString() + "\nResponse: " + response.toString();
				return "Success";
			}
		} else {
			String msg = "Error: Invoice submission for Compliance with FATOORA portal failed. Reason unknown";
			s_log.saveError("ZatcaComplianceFailed", msg);
			return msg;
		}
	}
}

