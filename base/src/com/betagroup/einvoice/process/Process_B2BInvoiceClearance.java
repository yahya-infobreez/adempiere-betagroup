package com.betagroup.einvoice.process;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.commons.codec.DecoderException;
import org.compiere.model.MAttachment;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrder;
import org.compiere.model.MOrg;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.betagroup.einvoice.EInvoiceXmlFactory;
import com.betagroup.einvoice.QRUtil;
import com.betagroup.einvoice.ZatcaApiHelper;
import com.betagroup.einvoice.api.model.CSRResponse;
import com.betagroup.einvoice.api.model.ClearedInvoiceResult;
import com.betagroup.einvoice.api.model.InvoiceResult;

import oasis.names.specification.ubl.schema.xsd.invoice_2.Invoice;

import java.util.*;
import java.util.logging.Level;


public class Process_B2BInvoiceClearance extends SvrProcess{

	private int p_C_Invoice_ID;
	private int p_C_Order_ID;
	private static CLogger s_log = CLogger.getCLogger(Process_B2BInvoiceClearance.class);


	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("C_Order_ID"))
				p_C_Order_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else if (name.equals("C_Invoice_ID"))
				p_C_Invoice_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}
	}

	protected String doIt() throws Exception 
	{
		MInvoice minvoice = null;
		MOrder morder = null;
		if (p_C_Invoice_ID != 0)
		{
			minvoice = MInvoice.get(getCtx(), p_C_Invoice_ID);
			morder = (MOrder) minvoice.getC_Order();
			
		} else {
			morder = new MOrder(getCtx(), p_C_Order_ID, null);
			p_C_Invoice_ID = morder.getC_Invoice_ID();
			minvoice = MInvoice.get(getCtx(), p_C_Invoice_ID);
		}
		if(minvoice == null) {
			throw new Exception("No invoice found ");
		}
		
		MOrg org = MOrg.get(getCtx(), minvoice.getAD_Org_ID());
		MClient client = MClient.get(getCtx(), minvoice.getAD_Client_ID());
		Invoice invoiceXml = EInvoiceXmlFactory.createInvoiceXml(minvoice);

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		EInvoiceXmlFactory.marshalJaxb(invoiceXml, out, false);
		byte[] invoiceData = out.toByteArray();

		// Send for compliance check
		ZatcaApiHelper apiHelper = new ZatcaApiHelper();
		apiHelper.setUsername(org.getCertificate());
		apiHelper.setPasswd(org.getZatcaSecret());
		
		ClearedInvoiceResult response = apiHelper.submitB2BInvoiceForClearance(minvoice.getInvoiceHash(), minvoice.getUUID(), invoiceData);
		System.out.print(response);
		
		if(response != null && response.getClearedInvoice() != null) {
//			minvoice.setValidationMsg(response.toString()); // Base64 Encoded
//			if(response.getErrors() != null && !response.getErrors().isEmpty()) {
//				minvoice.seteInvoiceStatus("Invalid");
//			}
			minvoice.saveEx();
			
			// Save the XML
			String xmlFileName = client.getVatNumber()+invoiceXml.getIssueDate().toString()+minvoice.getDocumentNo();
			MAttachment attachment = minvoice.getAttachment();
			if(attachment == null) {
				attachment = minvoice.createAttachment();
			}
			byte[] clearedInvoice = Base64.getDecoder().decode(response.getClearedInvoice()); // Save cleared invoice				
			attachment.addEntry(xmlFileName, clearedInvoice);
			attachment.saveEx();

		} else {
			String msg = "Error: CSR Registration with FATOORA portal failed. "
					+ response != null ? response.getErrors().toString() : "Reason unknown";
			s_log.saveError("ZatcaRegistrationFailed", msg);
			return msg;
		}
		
		
		return null;
	}
}

