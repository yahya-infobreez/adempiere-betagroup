package com.betagroup.einvoice.process;
import java.math.BigDecimal;
import org.apache.activemq.util.ByteArrayInputStream;
import org.compiere.model.MAttachment;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrder;
import org.compiere.model.MOrg;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;
import com.betagroup.einvoice.EInvoiceXmlFactory;
import com.betagroup.einvoice.ZatcaApiHelper;
import com.betagroup.einvoice.api.model.ClearedInvoiceResult;
import com.betagroup.einvoice.api.model.InvoiceResult;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.DocumentReferenceType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.Invoice;

import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;


public class Process_EInvoiceClearanceAndReporting extends SvrProcess{

	private int p_C_Invoice_ID;
	private int p_C_Order_ID;
	private static CLogger s_log = CLogger.getCLogger(Process_EInvoiceClearanceAndReporting.class);


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
				else if (name.equals("C_Order_ID"))
					p_C_Order_ID = ((BigDecimal)para[i].getParameter()).intValue();
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
		
		String status = null;
		if(minvoice.isSimplifiedInvoice()) {
			status = processB2CInvoice(minvoice);
		} else {
			status = processB2BInvoice(minvoice);
		}
		
		return status;
	}

	private String processB2BInvoice(MInvoice minvoice) throws Exception {
		MOrg org = MOrg.get(getCtx(), minvoice.getAD_Org_ID());
		MClient client = MClient.get(getCtx(), minvoice.getAD_Client_ID());
		Invoice invoiceXml = EInvoiceXmlFactory.createInvoiceXml(minvoice);
		byte[] invoiceData = EInvoiceXmlFactory.canonicalize(invoiceXml, false);

		// Send for compliance check
		ZatcaApiHelper apiHelper = new ZatcaApiHelper();
		apiHelper.setAuth(org.getCertificate(), org.getZatcaSecret());
		
		try {	
			ClearedInvoiceResult response = apiHelper.submitInvoiceForClearance(minvoice.getInvoiceHash(), minvoice.getUUID(), invoiceData);
			System.out.print(response);
			
			String status = "PENDING";
			if("CLEARED".equals(response.getStatus())) {
				status = "SUCCESS";
			}
			
			minvoice.setEInvoiceMessage(response.toString()); 
			if(response.getErrors() != null && !response.getErrors().isEmpty()) {
				status = "ERROR";
			} else if(response.getWarnings() != null && !response.getWarnings().isEmpty()) {
				status = "WARNING";
			} else if(response.getValidationResults() != null) {
				if(response.getValidationResults().getErrorMessages() != null
					&& !response.getValidationResults().getErrorMessages().isEmpty()) {
					status = "ERROR";
				} else if(response.getValidationResults().getWarningMessages() != null
							&& !response.getValidationResults().getErrorMessages().isEmpty()) {
					status = "WARNING";
				}
			}
			minvoice.setEInvoiceStatus(status);
			minvoice.saveEx();
			
			if(response.getClearedInvoice() != null) {
				// Save the XML
				byte[] clearedInvoiceData = Base64.getDecoder().decode(response.getClearedInvoice()); // Save cleared invoice				
				saveAttachment(minvoice, client, invoiceXml, clearedInvoiceData);
				
				// Extract the QR Code and save separately
				Invoice clearedInvoice = EInvoiceXmlFactory.loadXml(new ByteArrayInputStream(clearedInvoiceData));
				String qrCode = clearedInvoice.getAdditionalDocumentReferences().stream()
					.filter(ref->"QR".equals(ref.getID().getValue()))
					.map(ref->ref.getAttachment().getEmbeddedDocumentBinaryObject().getValue())
					.findFirst().orElse(null);
				minvoice.setQRCode(qrCode);
			}
			minvoice.saveEx();
			return status;
		} catch(Exception ex) {
			ex.printStackTrace();
			String msg = "Error: Invoice Clearance with FATOORA portal failed. Invoice# " + minvoice.getDocumentNo() + "\n" + ex.getMessage();
			s_log.saveError("Invoice Reporting failed" , msg);
			return msg;
		}
	}
	
	private String processB2CInvoice(MInvoice minvoice) throws Exception {
		MOrg org = MOrg.get(getCtx(), minvoice.getAD_Org_ID());
		MClient client = MClient.get(getCtx(), minvoice.getAD_Client_ID());
		Invoice invoiceXml = EInvoiceXmlFactory.createInvoiceXml(minvoice);
		byte[] invoiceData = EInvoiceXmlFactory.canonicalize(invoiceXml, false);
		
		saveAttachment(minvoice, client, invoiceXml, invoiceData);

		// Send for reporting
		ZatcaApiHelper apiHelper = new ZatcaApiHelper();
		apiHelper.setAuth(org.getCertificate(), org.getZatcaSecret());
		
		try {
			InvoiceResult response = apiHelper.submitInvoiceForReporting(minvoice.getInvoiceHash(), minvoice.getUUID(), invoiceData);
			System.out.print(response);
			
			String status = "PENDING";
			if("REPORTED".equals(response.getStatus())) {
				status = "SUCCESS";
			} 
			minvoice.setEInvoiceMessage(response.toString()); 
			if(response.getErrors() != null && !response.getErrors().isEmpty()) {
				status = "ERROR";
			} else if(response.getWarnings() != null && !response.getWarnings().isEmpty()) {
				status = "WARNING";
			} else if(response.getValidationResults() != null) {
				if(response.getValidationResults().getErrorMessages() != null
					&& !response.getValidationResults().getErrorMessages().isEmpty()) {
					status = "ERROR";
				} else if(response.getValidationResults().getWarningMessages() != null
							&& !response.getValidationResults().getErrorMessages().isEmpty()) {
					status = "WARNING";
				}
			}
			minvoice.setEInvoiceStatus(status);
			minvoice.saveEx();
			return status;
			//}
		} catch(Exception ex) {
			minvoice.setEInvoiceMessage(ex.getMessage()); 
			minvoice.setEInvoiceStatus("PENDING");
			minvoice.saveEx();
			String msg = "Error: Invoice Reporting with FATOORA portal failed. Invoice# " + minvoice.getDocumentNo() + "\n" + ex.getMessage();
			s_log.saveError("Invoice Reporting failed" , msg);
			return msg;
		}
	}

	private void saveAttachment(MInvoice minvoice, MClient client, Invoice invoiceXml, byte[] invoiceData) {
		// Save the XML
		String xmlFileName = client.getVatNumber()
				+"_"+invoiceXml.getIssueDate().getValue().toString().replaceAll("-", "")
				+"T"+invoiceXml.getIssueTime().getValue().replaceAll(":", "")
				+"_"+minvoice.getDocumentNo().replaceAll("[^a-zA-Z0-9]", "-")
				+ ".xml";
		MAttachment attachment = minvoice.getAttachment();
		if(attachment == null) {
			attachment = minvoice.createAttachment();
		}
		attachment.addEntry(xmlFileName, invoiceData);
		attachment.saveEx();
	}
}

