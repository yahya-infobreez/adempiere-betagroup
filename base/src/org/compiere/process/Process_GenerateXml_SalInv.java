package org.compiere.process;
import java.io.File;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.MOrg;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.betagroup.einvoice.EInvoiceXmlFactory;
import com.betagroup.einvoice.QRUtil;

import oasis.names.specification.ubl.schema.xsd.invoice_2.Invoice;

import java.util.*;


public class Process_GenerateXml_SalInv extends SvrProcess{

	private int AD_PInstance_ID=0;
	private int Record_Id=0;
	private int AD_Client_ID = 0;
	public PreparedStatement pstmt = null;
	protected void prepare()
	{
		AD_PInstance_ID = getAD_PInstance_ID();
		AD_Client_ID = getAD_Client_ID();
		Record_Id =  getRecord_ID();
	}

	protected String doIt() throws Exception 
	{
		MInvoice minvoice = MInvoice.get(getCtx(), Record_Id);
		MOrg org = MOrg.get(getCtx(), minvoice.getAD_Org_ID());
		MClient client = MClient.get(getCtx(), minvoice.getAD_Client_ID());
		Invoice invoiceXml = EInvoiceXmlFactory.createInvoiceXml(minvoice);
		File signedXml = EInvoiceXmlFactory.generateSignedXmlFile(invoiceXml);
		invoiceXml = EInvoiceXmlFactory.loadXml(signedXml);
		String invoiceHash = EInvoiceXmlFactory.generateHash(invoiceXml);
		minvoice.setInvoiceHash(invoiceHash);
		String signature = EInvoiceXmlFactory.getInvoiceSignature(invoiceXml);
		String companyName = org.getName2() != null ? org.getName2() : client.getName2();
		String qrString = QRUtil.generateQR(companyName, client.getVatNumber(), minvoice.getInvoiceIssueTime(), 
				minvoice.getGrandTotal(), minvoice.getTaxTotal(), minvoice.getInvoiceHash(), 
				signature, org.getPublicKey(), org.getCertificate());
		minvoice.setQRCode(qrString);
		minvoice.saveEx();
		
		return null;
	}
}

