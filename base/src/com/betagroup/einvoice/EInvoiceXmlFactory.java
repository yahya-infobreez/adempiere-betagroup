package com.betagroup.einvoice;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.security.InvalidParameterException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MCharge;
import org.compiere.model.MClient;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MLocation;
import org.compiere.model.MOrder;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.compiere.model.MProduct;
import org.compiere.model.MTax;
import org.compiere.util.Env;
import org.etsi.uri._01903.v1_3.CertIDType;
import org.etsi.uri._01903.v1_3.DigestAlgAndValueType;
import org.etsi.uri._01903.v1_3.QualifyingProperties;
import org.etsi.uri._01903.v1_3.SignedProperties;
import org.etsi.uri._01903.v1_3.SignedSignatureProperties;
import org.etsi.uri._01903.v1_3.SigningCertificate;
import org.w3._2000._09.xmldsig_.DigestMethod;
import org.w3._2000._09.xmldsig_.KeyInfo;
import org.w3._2000._09.xmldsig_.Object;
import org.w3._2000._09.xmldsig_.Reference;
import org.w3._2000._09.xmldsig_.Signature;
import org.w3._2000._09.xmldsig_.SignatureValue;
import org.w3._2000._09.xmldsig_.SignedInfo;
import org.w3._2000._09.xmldsig_.Transform;
import org.w3._2000._09.xmldsig_.Transforms;
import org.w3._2000._09.xmldsig_.X509Data;
import org.w3._2000._09.xmldsig_.X509IssuerSerialType;
import org.w3c.dom.Document;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.AddressType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.AttachmentType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.BillingReference;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.CountryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.CustomerPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.DeliveryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.DocumentReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.FinancialAccountType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.InvoiceLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.MonetaryTotalType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyIdentification;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyLegalEntity;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyTaxScheme;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PaymentMeans;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PriceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.SupplierPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxCategoryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxSubtotal;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxTotalType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EmbeddedDocumentBinaryObject;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ID;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InstructionNote;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.Note;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaymentMeansCode;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaymentNote;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxExemptionReason;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ExtensionContent;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.ExtensionURI;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtension;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensions;
import oasis.names.specification.ubl.schema.xsd.commonsignaturecomponents_2.UBLDocumentSignatures;
import oasis.names.specification.ubl.schema.xsd.invoice_2.Invoice;
import oasis.names.specification.ubl.schema.xsd.signatureaggregatecomponents_2.SignatureInformation;

public class EInvoiceXmlFactory {

	public static Invoice createInvoiceXml(MInvoice minvoice) throws Exception {
		if(minvoice.get_ID() <= 0 || !MInvoice.DOCSTATUS_Completed.equals(minvoice.getDocStatus())) {
			throw new Exception("Invalid Invoice document");
		}
			
		Invoice invoice = new Invoice();
		// KSA – EN16931 (BR-KSA-EN16931) // cbc:ProfileID
		invoice.setProfileID("reporting:1.0"); // Hardcoded value
		// BR-KSA-F-06 // 2.1 Invoice Reference Number (IRN) // cbc:ID
		invoice.setID(minvoice.getDocumentNo());
		// BR-KSA-03 // cbc:UUID
		invoice.setUUID(minvoice.getUUID());
		Timestamp invoiceIssueTime = minvoice.getInvoiceIssueTime() != null ? minvoice.getInvoiceIssueTime() : minvoice.getDateInvoiced();
		// BR-KSA-04, BR-KSA-F-01	// cbc:IssueDate
		invoice.setIssueDate(invoiceIssueTime.toLocalDateTime().toLocalDate());
		// BR-KSA-70	// cbc:IssueTime
		invoice.setIssueTime(invoiceIssueTime.toLocalDateTime());
		
		// BT-3 = BR-KSA-06, BR-KSA-07, BR-KSA-31 // cbc:InvoiceTypeCode / @name 
		/* As per UN/CEFACT code list 1001
		 * Structure:
			NNPNESB
			where
			NN (positions 1 and 2) = invoice subtype:
			- 01 for tax invoice
			- 02 for simplified tax invoice
			P (position 3) = 3rd Party invoice transaction, 0 for false, 1 for true
			N (position 4) = Nominal invoice transaction, 0 for false, 1 for true
			E (position 5) = Exports invoice transaction, 0 for false, 1 for true
			S (position 6) = Summary invoice transaction, 0 for false, 1 for true
			B (position 7) = Self-billed invoice transaction, ) for false, 1 for true
		 */
		String invoiceTypeCode = null;
		if(minvoice.isSimplifiedInvoice()) {
			invoiceTypeCode = "02";
		} else { // Standard Inv.
			invoiceTypeCode = "01";
		}
		
		// TODO "3rd Party invoice", "Nominal invoice"
		invoiceTypeCode += "0";
		// TODO "Nominal invoice"
		invoiceTypeCode += "0";
		
		// Export Invoice
		if(minvoice.isExportInvoice()) {
			invoiceTypeCode += "1";
		} else {
			invoiceTypeCode += "0";
		}
		
		// TODO "Summary invoice":
			invoiceTypeCode += "0";
		// TODO "Self-billed invoice":
			invoiceTypeCode += "0";		

		String invoiceType;
		String invoiceDocType = minvoice.getC_DocType().getName();
		if(invoiceDocType.equals("AR Invoice")) {
			invoiceType = "388"; // Sales Invoice
		} else if(invoiceDocType.equals("AR Credit Memo")) { //("ARC".equals(minvoice.getC_DocType().getDocBaseType())) {
			invoiceType = "381"; // Credit Note
		} else if(invoiceDocType.equals("AR Debit Memo")) {
			invoiceType = "383"; // Debit Note
		} else  if(invoiceDocType.equals("Prepayment Invoice (Customer)")) { //if("PPI".equals(minvoice.getC_DocType().getDocBaseType())) {
			invoiceType = "386"; // Prepayment Invoice // TODO
		} else {
			throw new AdempiereException("Invalid invoice type for eInvoice");
		}
		invoice.setInvoiceTypeCode(invoiceTypeCode, invoiceType);
		
		// BR-KSA-F-06 	// cbc:Note // Optional
		Note note = new Note();
		note.setValue(minvoice.getDescription());
		invoice.getNotes().add(note);// Not found
		
		// Document Currency Code // cbc:DocumentCurrencyCode // BR-05
		String currency = minvoice.getCurrencyISO();
		invoice.setDocumentCurrencyCode(currency);
//		 Tax currency code // cbc:TaxCurrencyCode //BR-KSA-EN16931-02,BR-KSA-68
		invoice.setTaxCurrencyCode("SAR"); // hardcoded, as it is the only value allowed
		// TODO tax should be set in invoice as SAR, irrespective of Invoice currency
		
		// Purchase order ID	// cac:OrderReference/cbc:ID 	// BR-KSA-F-06 // Optional // 1:1
		invoice.setOrderReference(minvoice.getPOReference());
		
		// Billing reference ID  = The sequential number (Invoice number BT-1) of the original invoice(s) 
		// that the credit/debit note is related to. // cac:BillingReference / cac:InvoiceDocumentReference / cbc:ID 
		// Mandatory for Credit Note/Debit Note. 1:n 		
		if(invoiceDocType.equals("AR Credit Memo") || invoiceDocType.equals("AR Debit Memo")) {
			// forEach() {
			DocumentReferenceType refType = new DocumentReferenceType();
			if(minvoice.getRef_Invoice_ID() <= 0) {
				throw new Exception("Original Invoice Reference missing for Credit/Debit note");
			}
			MInvoice originalInvoice = MInvoice.get(minvoice.getCtx(), minvoice.getRef_Invoice_ID());
			refType.setID(new ID(originalInvoice.getDocumentNo())); 
			String refInvoiceType = "388"; // Sales Invoice
			String docType = originalInvoice.getC_DocType().getName();
			if(docType.equals("AR Credit Memo")) { //("ARC".equals(minvoice.getC_DocType().getDocBaseType())) {
				refInvoiceType = "381"; // Credit Note
			} else if(docType.equals("AR Debit Memo")) {
				refInvoiceType = "383"; // Debit Note
			} else  if(docType.equals("Prepayment Invoice (Customer)")) { //if("PPI".equals(minvoice.getC_DocType().getDocBaseType())) {
				refInvoiceType = "386"; // Prepayment Invoice // TODO
			}
			refType.setDocumentType(refInvoiceType);
			refType.setIssueDate(originalInvoice.getDateInvoiced().toLocalDateTime().toLocalDate());
			BillingReference billingReference = new BillingReference();
			billingReference.setInvoiceDocumentReference(refType); // setDebitNoteDocumentReference(value); CreditNoteDocumentReference(value); 
			invoice.getBillingReferences().add(billingReference);
			// }
		}
		
		// Contract ID // cac:ContractDocumentReference / cbc:ID 	// Optional = Skip
		
		// KSA-16 = Invoice counter value 	// cac:AdditionalDocumentReferene / cbc:UUID where cac:AdditionalDocumentReferene / cbc:ID = ICV
		// BR-KSA-34 = The invoice counter value (KSA-16) contains only digits. ==> Means use ID?
		DocumentReferenceType counterValue = new DocumentReferenceType();
		counterValue.setUUID(String.valueOf(minvoice.getICV()));
		counterValue.setID("ICV");
		invoice.getAdditionalDocumentReferences().add(counterValue);
		
		// KSA-13 = Previous invoice hash	// hash must be base64 encoded SHA256.	// BR-KSA-26, BR-KSA-61 
		/* 	cac:AdditionalDocumentReference / cac:Attachment / cbc:EmbeddedDocumentBinaryObject 
			 where
			 cac:AdditionalDocumentReference / cbc:ID = PIH
			 cac:AdditionalDocumentReference / cac:Attachment / cbc:EmbeddedDocumentBinaryObject /@mimeCode = text/plain
		*/
		MInvoice previousInvoice = MInvoice.get(minvoice.getCtx(), minvoice.getPreviousInvoice_ID());
		String prevHashString = previousInvoice.getInvoiceHash(); 
		if(prevHashString == null) {
			/* For the first invoice, the previous invoice hash is
			"NWZlY2ViNjZmZmM4NmYzOGQ5NTI3ODZjNmQ2OTZjNzljMmRiYzIzOWRkNGU5MWI0NjcyOWQ3M2EyN2ZiNTdlOQ==",
			the equivalent for base64 encoded SHA256 of "0" (zero)) character.
			 */
			prevHashString = "NWZlY2ViNjZmZmM4NmYzOGQ5NTI3ODZjNmQ2OTZjNzljMmRiYzIzOWRkNGU5MWI0NjcyOWQ3M2EyN2ZiNTdlOQ==";
//			prevHashString = Base64.getEncoder().encodeToString(QRUtil.generateSHA256Hash("0".getBytes()).getBytes());
		}
		AttachmentType prevHashAttach = new AttachmentType();
		prevHashAttach.setEmbeddedDocumentBinaryObject(prevHashString, "text/plain");
		DocumentReferenceType previousHash = new DocumentReferenceType();
		previousHash.setAttachment(prevHashAttach);
		previousHash.setID("PIH");
		invoice.getAdditionalDocumentReferences().add(previousHash);

		MOrg org = MOrg.get(minvoice.getCtx(), minvoice.getAD_Org_ID());
		MOrgInfo orgInfo = MOrgInfo.get(minvoice.getCtx(), minvoice.getAD_Org_ID(), null);
				
		// Other Seller ID	// BT-29, BT-29-1	//  cac:AccountingSupplierParty / cac:Party / cac:PartyIdentification / cbc:ID	// BR-KSA-08
		// where attribute schemeID is according to the description // TODO cross-check cac:AccountingSellerParty is used in PDF doc
		/*
		 * Other seller ID is one of the list:
			- Commercial registration number with "CRN" as schemeID
			- MOMRAH license with "MOM" as schemeID
			- MHRSD license with "MLS" as schemeID
			- 700 Number with "700" as schemeID
			- MISA license with "SAG" as schemeID
			- Other OD with "OTH" as schemeID
			In case multiple IDs exist then one of the above must be entered following the sequence specified above
		 */

		PartyType party = new PartyType();
		PartyIdentification partyId = new PartyIdentification();
		partyId.setID(new ID("CRN", org.getCRN())); // Hardcode as ONLY CRN is used
		party.getPartyIdentifications().add(partyId);
		
		// Seller Address BT-35, BT-36, BT-37, BT-38, BT-39, BT-40, 
		// BR_KSA-09 Seller address must contain street	name (BT-35), building number (KSA-17), postal code (BT-38), city
		// (BT-37), District (KSA-3), countrycode (BT-40).
		// Optional: Additional Street Name, Plot Identification, 
		AddressType address = new AddressType();
		MLocation orgLocation = (MLocation) orgInfo.getC_Location(); 
		address.setStreetName(org.getStreetName()); 
//		address.setAdditionalStreetName(buyerLocation.getStreetName2()); 
		address.setBuildingNumber(org.getBuildingNo()); 
		address.setPlotIdentification(org.getAdditionalNo()); 
		address.setCityName(orgLocation.getCity());
		address.setPostalZone(org.getPostalZip()); 
		address.setCountrySubentity(orgLocation.getC_Region().getName());
		address.setCitySubdivisionName(org.getDistrict()); 
		CountryType country = new CountryType(orgLocation.getC_Country().getCountryCode(),
				orgLocation.getC_Country().getName());
		address.setCountry(country);		
		party.setPostalAddress(address);

		
		// BT-31 VAT number (registered) or Group VAT number	//BR-KSA-39, BR-KSA-40
		/*
		 * cac:AccountingSupplierParty / cac:Party / cac:PartyTaxScheme / cbc:CompanyID
			where  cac:AccountingSupplierParty / cac:Party / cac:PartyTaxScheme / cac:TaxScheme = VAT
		 */
		MClient client = MClient.get(minvoice.getCtx(), minvoice.getAD_Client_ID());
		PartyTaxScheme taxScheme = new PartyTaxScheme();
		taxScheme.setCompanyID(client.getVatNumber()); //orgInfo.getTaxID());
		taxScheme.setTaxScheme("VAT");
		party.getPartyTaxSchemes().add(taxScheme);
		
		// BT-27 Seller name	// 
		// cac:AccountingSupplierParty / cac:Party / cac:PartyLegalEntity / cbc:RegistrationName
		PartyLegalEntity legalEntity = new PartyLegalEntity();
		legalEntity.setRegistrationName(org.getName2() != null ? org.getName2() : client.getName2()); // Legal name (in arabic) and client name may be different
		party.getPartyLegalEntities().add(legalEntity);		
		
		SupplierPartyType supplierParty = new SupplierPartyType();
		supplierParty.setParty(party);
		invoice.setAccountingSupplierParty(supplierParty);
		
		// BT-48	Buyer's VAT 	// BR-KSA-44, BR-KSA-46
		// cac:AccountingCustomerParty / cac:Party / cac:PartyTaxScheme / cbc:CompanyID
		// where  cac:AccountingCustomerParty / cac:Party / cac:PartyTaxScheme / cac:TaxScheme = VAT
		MBPartner bp = (MBPartner) minvoice.getC_BPartner();	
		//VAT_NUMBER to be copied to Invoice for immutability
		String vatNumber = minvoice.getVatNumber() != null ? minvoice.getVatNumber() : bp.getVatNumber();

		PartyType party2 = new PartyType();
		if(!minvoice.isSimplifiedInvoice()) {
			PartyTaxScheme taxScheme2 = new PartyTaxScheme();
			taxScheme2.setCompanyID(vatNumber);
			taxScheme2.setTaxScheme("VAT");
			party2.getPartyTaxSchemes().add(taxScheme2);
		}
		
		// BT-46, BT-46-1 Other buyer ID // BR-KSA-14, BR-KSA-49, BR-KSA-81
		// cac:AccountingCustomerParty / cac:Party / cac:PartyIdentification / cbc:ID where attribute "schemeID" is according to the description
		/*
		 * Other Buyer ID, required only if buyer is not VAT registered, then one of the following must be provided:
			- Tax Identification Number "TIN" as schemeID
			- Commercial registration number with "CRN" as schemeID
			- MOMRAH license with "MOM" as schemeID
			- MHRSD license with "MLS" as schemeID
			- 700 Number with "700" as schemeID
			- MISA license with "SAG" as schemeID
			- National ID with "NAT" as schemeID
			- GCC ID with "GCC" as schemeID
			- Iqama Number with "IQA" as schemeID
			- Passport ID with "PAS" as schemeID
			- Other ID with "OTH" as schemeID
			In case multiple IDs exist then one of the above must be entered following the sequence specified above 
		 */
			if(bp.getLicenseNo() != null) { // Optional if VAT ID is present
				PartyIdentification party2Id = new PartyIdentification();
				party2Id.setID(new ID(convertSchemeID(bp.getLicenseScheme()), bp.getLicenseNo()));
				party2.getPartyIdentifications().add(party2Id);
			} else {
				if(minvoice.isSimplifiedInvoice()) {
					MOrder morder = (MOrder) minvoice.getC_Order();
					if("VATEX-SA-EDU".equals(morder.getVatExceptionReason()) || "VATEX-SA-HEA".equals(morder.getVatExceptionReason())) {
						throw new Exception("Trade License and name mandatory in case of exempted tax");
					}
				}
			}

				
		// Buyer address  BT-50, BT-51, KSA-18, KSA-19, BT-52, BT-53, BT-54, KSA-4, BT-55
		// BR-KSA-10, BR-KSA-63
		/*
		 cac:AccountingCustomerParty / cac:Party / cac:PostalAddress / cbc:StreetName
		 cac:AccountingCustomerParty / cac:Party / cac:PostalAddress / cbc:AdditionalStreetName // Optional
		cac:AccountingCustomerParty / cac:Party / cac:PostalAddress / cbc:BuildingNumber
		cac:AccountingCustomerParty / cac:Party / cac:PostalAddress / cbc:PlotIdentification ===> AdditionalNo  // Optional
		cac:AccountingCustomerParty / cac:Party / cac:PostalAddress / cbc:CityName
		cac:AccountingCustomerParty / cac:Party / cac:PostalAddress / cbc:PostalZone
		cac:AccountingCustomerParty / cac:Party / cac:PostalAddress / cbc:CountrySubentity ==> Region/State // Optional
		cac:AccountingCustomerParty / cac:Party / cac:PostalAddress / cbc:CitySubdivisionName ==> District
		cac:AccountingCustomerParty / cac:Party / cac:PostalAddress / cac:Country / cbc:IdentificationCode
		 */
		if(!minvoice.isSimplifiedInvoice()) { // Skip address for simplified Invoice
			AddressType address2 = new AddressType();
			MBPartnerLocation buyerLocation = (MBPartnerLocation) minvoice.getC_BPartner_Location();		
			address2.setStreetName(buyerLocation.getStreetName()); // Field added in BPLocation table
	//		address2.setAdditionalStreetName(buyerLocation.getStreetName2()); // Field added in BPLocation table
			address2.setBuildingNumber(buyerLocation.getBuildingNo()); // Field added in BPLocation table
			address2.setPlotIdentification(buyerLocation.getAdditionalNo()); // Field added in BPLocation table
			address2.setCityName(buyerLocation.getC_Location().getCity());
			address2.setPostalZone(buyerLocation.getPostalZip()); // Field added in BPLocation table
			address2.setCountrySubentity(buyerLocation.getC_Location().getC_Region().getName());
			address2.setCitySubdivisionName(buyerLocation.getDistrict()); // Field added in BPLocation table
			CountryType country2 = new CountryType(buyerLocation.getC_Location().getC_Country().getCountryCode(),
					buyerLocation.getC_Location().getC_Country().getName());
			address2.setCountry(country2);		
			party2.setPostalAddress(address2);
		}

		
		// BT-27 Buyer name	//// BR-KSA-25, BR-KSA-42, BR-KSA-71, BR-KSA-F-06 // Mandatory for B2B or Simplified & Exempted/Summary
		// cac:AccountingCustomerParty / cac:Party /  cac:PartyLegalEntity / cbc:RegistrationName
		PartyLegalEntity legalEntity2 = new PartyLegalEntity();
		legalEntity2.setRegistrationName(minvoice.getC_BPartner().getName());
		party2.getPartyLegalEntities().add(legalEntity2);
 
		CustomerPartyType customerParty = new CustomerPartyType();
		customerParty.setParty(party2);
		invoice.setAccountingCustomerParty(customerParty);
		
		// KSA-5	Supply date	// BR-KSA-15, BR-KSA-F-01, BR-KSA-72 // Mandatory for B2B, Simplified-Summary
		// 	cac:Delivery / cbc:ActualDeliveryDate	// format YYYY-MM-DD
		// TODO Is it allowed to be greater than Invoice Date? Then how to fill it in Invoice?
		DeliveryType delivery = new DeliveryType();
		// Use DateInvoiced itself, as shipment record is created same time as POS order is used.
		// Real shipment may be done later. It is not captured in eInvoice
		delivery.setActualDeliveryDate(// minvoice.getDateDelivered().toLocalDateTime().toLocalDate());
				minvoice.getDateInvoiced().toLocalDateTime().toLocalDate()); 
		// KSA-24 Supply end date	// BR-KSA-F-01, BR-KSA-35, BR-KSA-36, BR-KSA-72	// format YYYY-MM-DD // Mandatory for Continuous Supplies. 
		// cac:Delivery / cbc:LatestDeliveryDate
//		delivery.setLatestDeliveryDate(minvoice.getDateDeliveredLast().toLocalDateTime().toLocalDate());	// Not used
			// minvoice.getDateInvoiced().toLocalDateTime().toLocalDate()); // for testing
		invoice.getDeliveries().add(delivery);

		
		// BT-81 Payment means type code // BR-KSA-16 // UNTDID 4461	// OPTIONAL;
		// cac:PaymentMeans / cbc:PaymentMeansCode 
		// 10    In cash, Credit = 30, Direct Debit = 49, Credit card = 54, Debit card = 55, 60    Promissory note, 68    Online payment service
		PaymentMeans paymentMeans = new PaymentMeans();
		paymentMeans.setPaymentMeansCode(new PaymentMeansCode("30")); // Hardcode Credit for now
		
		// KSA-10 Reasons for issuance of credit / debit note //BR-KSA-17
		// cac:PaymentMeans / cbc:InstructionNote
		/*
		 Reasons for issuance of credit / debit note as per Article 40 (paragraph 1) and Article 54 (3) of KSA VAT regulations, a Credit and Debit Note is issued for these 5 instances:
		- Cancellation or suspension of the supplies after its occurrence either wholly or partially (تم إلغاء أو وقف التوريد بعد حدوثه أو اعتباره كلياً أو جزئياً)
		- In case of essential change or amendment in the supply, which leads to the change of the VAT due (وجود تغيير أو تعديل جوهري في طبيعة التوريد بحيث يؤدي الى تغيير الضريبة المستحقة)
		- Amendment of the supply value which is pre-agreed upon between the supplier and consumer (تم الاتفاق على تعديل قيمة التوريد مسبقاً)
		- In case of goods or services refund. (عند ترجيع السلع أو الخدمات)
		- In case of change in Seller's or Buyer's information (عند التعديل على بيانات المورد أو المشتري)
		 */
		if(invoiceDocType.equals("AR Credit Memo") || invoiceDocType.equals("AR Debit Memo")) {
			InstructionNote returnNote = new InstructionNote();
			returnNote.setValue(minvoice.getReturnReasonText());
			returnNote.setLanguageID("AR"); // TODO Confirm
			paymentMeans.getInstructionNotes().add(returnNote);
		}

		// KSA-22 Payment terms // Optional. Free Text	
		// cac:PaymentMeans / cac:PayeeFinancialAccount / cbc:PaymentNote
		PaymentNote paymentNote = new PaymentNote(minvoice.getC_PaymentTerm().getName());
		FinancialAccountType financialAccountType = new FinancialAccountType();
		financialAccountType.getPaymentNotes().add(paymentNote);
		paymentMeans.setPayeeFinancialAccount(financialAccountType);

		// BT-84 Payment account identifier		// IBAN Number	// Optional // Skip
		// cac:PaymentMeans / cac:PayeeFinancialAccount / cbc:ID
		
		invoice.getPaymentMeans().add(paymentMeans);

		/*
		 * Note: RoundOff is also added as an InvoiceLine. Hence the totals shown in invoice header includes this amount.
		 * This should be adjusted and roundOff shown separately 
		 */
		BigDecimal roundOffAmt = minvoice.getRoundOffAmt(); 
		
		// Document level allowance indicator	= 'false'/’True’
		// cac:AllowanceCharge / cbc:ChargeIndicator
		
		// BT-94 Document level allowance percentage
		// cac:AllowanceCharge / cbc:MultiplierFactorNumeric With cbc:ChargeIndicator="false"
		
		// Document level allowance amount // BR-KSA-F-04, BR-KSA-EN16931-03
		// cac:AllowanceCharge / cbc:Amount with cbc:ChargeIndicator="false"
		
		// BT-5 Currency for document level allowance amount
		// cac:AllowanceCharge / cbc:Amount @currencyID
		
		// BT-93 Allowance/Charge base amount
		// cac:AllowanceCharge / cbc:BaseAmount with cbc:ChargeIndicator="false"
		
		// BT-95 Document level allowance VAT category code // BR-32, BR-O-13, BR-CL-18 // BR-KSA-18
		// UNCL5305 code list (AE=	Vat Reverse Charge, E=Exempt from Tax, S=Standard rate, Z=Zero rated goods, 
		// O="Not subject to VAT", (other names used = 'Zero rated', 'Exempt from VAT', Standard Rate, ..) 
		// TODO AE = not supported as per BR-KSA-18. 
		// cac:AllowanceCharge / cac:TaxCategory / cbc:ID With cbc:ChargeIndicator="false"
		
		// BT-96 Document level allowance VAT rate // BR-S-06, BR-Z-06, BR-E-06 // BR-KSA-12, BR-KSA-DEC-02 
		// cac:AllowanceCharge / cac:TaxCategory / cbc:Percent With cbc:ChargeIndicator="false"

		// BT-97 Reason for the document level allowance //	Optional
		// cac:AllowanceCharge/cbc:AllowanceChargeReason With cbc:ChargeIndicator="false"
		// KSA-21 Tax scheme ID = Use “VAT”	// 	
		// cac:AllowanceCharge / cac:TaxCategory / cac:TaxScheme / cbc:ID

		// BT-106 Net Amount	// 	BR-KSA-F-04
		// cac:LegalMonetaryTotal / cbc:LineExtensionAmount
		// BT-5 Currency for sum of invoice line net amount // BR-KSA-CL-02 // ISO 4217 alpha-3
		// cac:LegalMonetaryTotal / cbc:LineExtensionAmount @currencyID
		MonetaryTotalType monetaryTotalType = new MonetaryTotalType();
		BigDecimal prepaidAmt = minvoice.getAdvanceTotal(); //minvoice.getAdvanceAmt().add(minvoice.getAdvanceTax());
		boolean isAdvanceNotApplicable = minvoice.isPrepaymentInvoice() 
				|| invoiceDocType.equals("AR Credit Memo") || invoiceDocType.equals("AR Debit Memo");
		monetaryTotalType.setLineExtensionAmount((isAdvanceNotApplicable ? minvoice.getTotalLines() :
					minvoice.getTotalLinesBeforeAdvance()) //
				.subtract(roundOffAmt), currency); // TODO Should exclude charge/allowance
		

		// BT-107 Sum of allowances on document level // Optional // Sum of Line Level allowances
		// cac:LegalMonetaryTotal / cbc:AllowanceTotalAmount
		// BT-5 Currency for sum of allowances on document level	// 
		// cac:LegalMonetaryTotal / cbc:AllowanceTotalAmount @currencyID
		monetaryTotalType.setAllowanceTotalAmount(minvoice.getTotalAllowances(), currency);
		
		// BT-108 Sum of charges on document level // Conditional
		// cac:LegalMonetaryTotal / cbc:ChargeTotalAmount
		// BT-5 Currency for sum of charges on document level
		// cac:LegalMonetaryTotal / cbc:ChargeTotalAmount @currencyID
		monetaryTotalType.setChargeTotalAmount(minvoice.getTotalCharges(), currency);

		// BT-109 Invoice total amount without VAT	// Net Amount - Doclevel allowance + DocLevel charge
		// cac:LegalMonetaryTotal / cbc:TaxExclusiveAmount 
		// TODO Calculation depends on How charge/discount is handled at Document level		
		// BT-5 Currency for invoice total amount without VAT
		// cac:LegalMonetaryTotal / cbc:TaxExclusiveAmount @currencyID
		monetaryTotalType.setTaxExclusiveAmount((isAdvanceNotApplicable ? minvoice.getTotalLines() :
			minvoice.getTotalLinesBeforeAdvance())
				.subtract(roundOffAmt), currency);
		
		
		// Pre-Paid amount	// Optional
		// cac:LegalMonetaryTotal / cbc:PrepaidAmount
		// Currency for pre-paid amount	// Optional
		// cac:LegalMonetaryTotal / cbc:PrepaidAmount @currencyID
		monetaryTotalType.setPrepaidAmount(prepaidAmt, currency);
		
		// BT-112 Invoice total amount with VAT		// BR-KSA-F-04
		// If VAT total is not entered, Gross Total to be entered Statement - "Amount includes VAT"
		// cac:LegalMonetaryTotal / cbc:TaxInclusiveAmount		
		// Currency for invoice total amount with VAT
		// cac:LegalMonetaryTotal / cbc:TaxInclusiveAmount @currencyID
		// Note: This amount excludes RoundOff. Round off is shown separately
		monetaryTotalType.setTaxInclusiveAmount(minvoice.getGrandTotal()
				.add(prepaidAmt) // GrandTotal is the Payable amount after Advance is adjusted
				.subtract(roundOffAmt), currency); // RoundOff is considered extra

		
		// Rounding amount		// Optional
		// cac:LegalMonetaryTotal/cbc:PayableRoundingAmount
		// Currency for Rounding amount	// Optional		
		// cac:LegalMonetaryTotal/cbc:PayableRoundingAmount @currencyID
		monetaryTotalType.setPayableRoundingAmount(roundOffAmt, currency);

		
		// BT-115 Amount due for payment // BR-15 Always REQUIRED
		// cac:LegalMonetaryTotal / cbc:PayableAmount		
		// BT-5 Currency for amount due for payment
		// cac:LegalMonetaryTotal / cbc:PayableAmount @currencyID
		// GrandTotal field has new Payable Amt after adjusting Advance Amount
		monetaryTotalType.setPayableAmount(minvoice.getGrandTotal(), currency); 
		// Note: openAmt(creditmemoAdjusted=false), as we always want the amount +ve)
		// TODO openAmt() is valid after allocation is done for pre-payment, 
		
		// Finally add to Invoice
		invoice.setLegalMonetaryTotal(monetaryTotalType);
		
		java.util.Map<String, InvoiceTax> taxMap = processLines(invoice, minvoice); 
		
		processTax(invoice, minvoice, taxMap); // This should be called after #processLines()
		
		String[] signPair = fillSignatureDetails(invoice, org);
		String invoiceSignature = signPair[1];
		String invoiceHash = signPair[0];
		minvoice.setInvoiceHash(invoiceHash);

		// KSA-14 = Invoice QR code	//  BR-KSA-27 // base64Binary.
		/* cac:AdditionalDocumentReference / cac:Attachment / cbc:EmbeddedDocumentBinaryObject		 
		 where
		 cac:AdditionalDocumentReference / cbc:ID = QR
		 cac:AdditionalDocumentReference / cac:Attachment / cbc:EmbeddedDocumentBinaryObject /@mimeCode = text/plain
		*/
		// Set QR etc in case of Simplified Invoice. For Tax Invoice, it will be provided by ZATCA after uploading
		if(minvoice.isSimplifiedInvoice()) {
			String companyName = org.getName2() != null ? org.getName2() : client.getName2();			
			// Derive ECDSA Signature of ZATCA certificate
			byte[] cert2 = Base64.getDecoder().decode(org.getCertificate());
			X509Certificate x509Cert = DigitalSignatureHelper.decode(new String(cert2));
			// decoded.getSignature() contains the ECDSA signature
			byte[] edcsaPublicKey = x509Cert.getPublicKey().getEncoded();
			String qrString = QRUtil.generateQR(companyName, client.getVatNumber(), invoiceIssueTime, 
				minvoice.getGrandTotal(), minvoice.getTaxTotal(), invoiceHash, 
				invoiceSignature, edcsaPublicKey, x509Cert.getSignature());
			
			minvoice.setQRCode(qrString);
	
			DocumentReferenceType qrCode = new DocumentReferenceType();
			AttachmentType attachQrCode = new AttachmentType();
			attachQrCode.setEmbeddedDocumentBinaryObject(minvoice.getQRCode(), "text/plain");
			qrCode.setAttachment(attachQrCode);
			qrCode.setID("QR");
			invoice.getAdditionalDocumentReferences().add(qrCode);
		}
		minvoice.saveEx();
		return invoice;
	}
	
	/**
	 * Signing steps
	<p><b>Step 1: Generate Invoice Hash</b>
	To generate the invoice hash below steps can be followed:

    <li>Open the invoice XML file.
    <li>Remove the tags mentioned in the table below using the XPath.
    <li>Remove the XML version.
    <li>Canonicalize the Invoice using the C14N11 standard.
    <li>Hash the new invoice body using SHA-256 (output). e.g.:a11b6fe587a50f7daffe3a7fb42dcccf- 32b43ee9b37d9f252d04243e54c11a3f
    <li>Encode the hashed invoice using base64 (output)  Using HEX-to Base64 Encoder e.g.:oRtv5YelD32v/jp/tC3MzzK0PumzfZ8lLQQkPlTBGj8= 
    
    <p>Note: All these values will be used in later steps.
    
    <p><b>Step 2: Generate Digital Signature </b>
    <li> Generate private key from CSR config file (you can refer to openssl commands, or readme file on SDK)
    <li> Sign the generated invoice hash (in SHA-256 format not encoded with base64) with ECDSA using the private key (output). 
    
    <p><b>Step 3: Generate Certificate Hash</b>
    <li> Hash the certificate using SHA-256 (output). 
    <li> Encode the hashed x509 certificate using base64 (ENCODER BASE64 ) (output).
    
    <p><b>Step 4: Populate the Signed Properties Output</b>
    <li>Open the invoice before 1st step (before getting tags removed).
    <li>Refer to the below table to fill mentioned fields with their corresponding values using the related Xpath,
     (if there are any old values already exist in the fields, please make sure to remove all of them and replace them with the new values only).
    <li>To get X509 Serial number, decode the X509 certificate the value will be printed in the decoded result.

	 * @param invoice
	 * @param org
	 */

	/**
	 * Fill signature information in XML
	 * @param invoice
	 * @param org
	 * @return String[] {invoiceHashBase64, signatureBase64 };
	 * @throws Exception 
	 */
	private static String[] fillSignatureDetails(Invoice invoice, MOrg org) throws Exception {
		// TODO Setting this may be done by ZATCA SDK
		// KSA-15 = Digital Identity Standards, BR-KSA-28, BR-KSA-29, BR-KSA-30, BR-KSA-60
		 /* In the UBL extension
			ext:UBLExtensions / ext:UBLExtension / ext:ExtensionContent / sig:UBLDocumentSignatures 
			xmlns:sig=   "urn:oasis:names:specification:ubl:schema:xsd:CommonSignatureComponents-2" 
			xmlns:sac="urn:oasis:names:specification:ubl:schema:xsd:SignatureAggregateComponents-2"
			xmlns:sbc=   "urn:oasis:names:specification:ubl:schema:xsd:SignatureBasicComponents-2"
			xmlns:xades="urn:oasis:names:specification:ubl:schema:xsd:XAdESv141-2"  / 
					sac:SignatureInformation / 
			           cbc:ID = urn:oasis:names:specification:ubl:signature:1 (default value)
			           sbc:ReferencedSignatureID = urn:oasis:names:specification:ubl:signature:Invoice (default value)
			           ds:Signature - here is XMLDsig + XAdES XML generated by the EU XAdES tool for the cryptographic stamp
						
			In the main UBL
			cac:Signature / cbc:ID = urn:oasis:names:specification:ubl:signature:Invoice (default value)			
			cac:Signature / cbc:SignatureMethod = urn:oasis:names:specification:ubl:dsig:enveloped:xades (default value)
		 */

		SignatureInformation signInfo = new SignatureInformation();
		signInfo.setID(new ID("urn:oasis:names:specification:ubl:signature:1"));
		signInfo.setReferencedSignatureID("urn:oasis:names:specification:ubl:signature:Invoice");
		SignedInfo signedInfo = new SignedInfo();
		signedInfo.setCanonicalizationMethod("http://www.w3.org/2006/12/xml-c14n11");
		signedInfo.setSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256");
		
		// Step 1: Generate Invoice Hash
		byte[] invoiceHash = generateHash(invoice); 
		String invoiceHashBase64 = Base64.getEncoder().encodeToString(invoiceHash);

		// Fill reference block
		Reference reference = new Reference();
		// Reference (ID=invoiceSignedData)
		reference.setId("invoiceSignedData");
		Transforms transaforms = new Transforms();
		// Transform (XPath = not ext:UBLExtensions)
		transaforms.getTransforms().add(new Transform("http://www.w3.org/TR/1999/REC-xpath-19991116", "not(//ancestor-or-self::ext:UBLExtensions)"));
		// Transform (XPath = not cac:Signature)
		transaforms.getTransforms().add(new Transform("http://www.w3.org/TR/1999/REC-xpath-19991116", "not(//ancestor-or-self::cac:Signature)"));
		// Transform (XPath = not cac:AdditionalDocumentReference[cbc:ID='QR'])
		transaforms.getTransforms().add(new Transform("http://www.w3.org/TR/1999/REC-xpath-19991116", "not(//ancestor-or-self::cac:AdditionalDocumentReference[cbc:ID='QR'])"));
		// Transform (c14n11)
		transaforms.getTransforms().add(new Transform("http://www.w3.org/2006/12/xml-c14n11", null));
		reference.setTransforms(transaforms);
		
		// DigestMethod = sha256
		reference.setDigestMethod(new DigestMethod("http://www.w3.org/2001/04/xmlenc#sha256"));
		// DigestValue 
		reference.setDigestValue(invoiceHashBase64);	// TODO Confirm if Invoice Hash is right here
		signedInfo.getReferences().add(reference);
		
//	    Step 2: Generate Digital Signature </b>
//	    Generate private key from CSR config file (you can refer to openssl commands, or readme file on SDK)
//	    Sign the generated invoice hash (in SHA-256 format not encoded with base64) with ECDSA using the private key (output). 
		PrivateKey privateKey = DigitalSignatureHelper.getPrivateKey(org.getPrivateKey());
		java.security.Signature signature = java.security.Signature.getInstance("SHA256withECDSA");
		signature.initSign(privateKey);
		signature.update(QRUtil.convertToHexString(invoiceHash).getBytes()); // not encoded with base64, but to Hex
		byte[] digitalSignature = signature.sign();
        // Encode the signature to Base64 for easy display
        String signatureBase64 = Base64.getEncoder().encodeToString(digitalSignature);
        
//        Step 3: Generate Certificate Hash
//        Hash the certificate using SHA-256 (output). 
//        Encode the hashed x509 certificate using base64 (ENCODER BASE64 ) (output).
        /*
         * X509 Certificate( After completing CCSID API, it will return (binary security token),
         * take this value and decode it using base 64, the output is X509 certificate.) 
         */
        byte[] x509Certificate = Base64.getDecoder().decode(org.getCertificate());
        byte[] certificateHashHex = QRUtil.generateHashHex(x509Certificate);
        String certificateHashHexBase64 = Base64.getEncoder().encodeToString(certificateHashHex);       

        // Step 5: Generate Signed Properties Hash
		SignedProperties signedProperties = getSignedProperties(certificateHashHexBase64, new String(x509Certificate));	
		// Create hash later.. after adding to document and then extracting. 
		// Marshalling SignedProperties directly causes issues with namespaces and indentation, which affects hash value
		// Extract Signed Properties and then update SignatureProperties hash
		byte[] signedPropertiesData = extractSignedProperties(signedProperties);
//		ByteArrayOutputStream out = new ByteArrayOutputStream();
//		marshalJaxb(signedProperties, out, true);

		// Second Reference = SignatureProperties
		String signedPropertiesHash = Base64.getEncoder().encodeToString(QRUtil.generateHashHex(signedPropertiesData));
		Reference reference2 = new Reference();
		reference2.setType("http://www.w3.org/2000/09/xmldsig#SignatureProperties");
		reference2.setURI("#xadesSignedProperties");
		reference2.setDigestMethod(new DigestMethod("http://www.w3.org/2001/04/xmlenc#sha256"));
		reference2.setDigestValue(signedPropertiesHash); // Already base64 encoded
		signedInfo.getReferences().add(reference2); 
		
		// Step 6: Populate The UBL Extensions Output		
		Signature dsSignature = new Signature(); 
		dsSignature.setId("signature");
		dsSignature.setSignedInfo(signedInfo);			
		dsSignature.setSignatureValue(new SignatureValue(signatureBase64));	

		// Derive ECDSA Signature of ZATCA certificate
		X509Certificate x509Cert = DigitalSignatureHelper.decode(new String(x509Certificate));
		String x509Base64 = Base64.getEncoder().encodeToString(x509Cert.getEncoded());
		X509Data x509Data = new X509Data().addX509Certificate(x509Base64);
		KeyInfo keyInfo = new KeyInfo(); 
		keyInfo.getContent().add(x509Data);
		dsSignature.setKeyInfo(keyInfo);
		Object dsObject = new Object(); 
		dsObject.getContent().add(new QualifyingProperties("signature", signedProperties));
		dsSignature.getObjects().add(dsObject);
		signInfo.setSignature(dsSignature); 
		
		UBLDocumentSignatures ublDocSign = new UBLDocumentSignatures();
		ublDocSign.getSignatureInformations().add(signInfo);
		
		// Add cac:Signature block 
		oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.Signature invoiceSign
			= new oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.Signature();
		invoiceSign.setID("urn:oasis:names:specification:ubl:signature:Invoice");
		invoiceSign.setSignatureMethod("urn:oasis:names:specification:ubl:dsig:enveloped:xades");
		invoice.getSignatures().add(invoiceSign);
		
		org.w3c.dom.Element rootElement = marshalToDocument(ublDocSign).getDocumentElement();
		ExtensionContent extContent = new ExtensionContent();
		extContent.setAny(rootElement); 
		
		UBLExtensions extensions = new UBLExtensions();
		UBLExtension extension1 = new UBLExtension();
		extension1.setExtensionURI(new ExtensionURI("urn:oasis:names:specification:ubl:dsig:enveloped:xades"));
		extension1.setExtensionContent(extContent);
		extensions.getUBLExtensions().add(extension1);
		invoice.setUBLExtensions(extensions);
		return new String[] {invoiceHashBase64, signatureBase64 };
	}

	
	private static SignedProperties getSignedProperties(String certificateHashBase64, String certificate) throws Exception {
		// Extract Issuer and serial number from certificate
		X509Certificate certificate2 = DigitalSignatureHelper.decode(certificate);
		
		SignedProperties sp = new SignedProperties();
		sp.setId("xadesSignedProperties");
		SignedSignatureProperties ssp = new SignedSignatureProperties();
		ssp.setSigningTime(LocalDateTime.now()); // Current time
		SigningCertificate sc = new SigningCertificate();
		CertIDType cert = new CertIDType();
		cert.setCertDigest(new DigestAlgAndValueType("http://www.w3.org/2001/04/xmlenc#sha256", certificateHashBase64));
		cert.setIssuerSerial(new X509IssuerSerialType(certificate2.getIssuerDN().getName(), certificate2.getSerialNumber().toString()));
		sc.getCerts().add(cert);
		ssp.setSigningCertificate(sc);
		sp.setSignedSignatureProperties(ssp);
		return sp;
	}

	/**
	 * Convert Scheme reference to ZATCA code
	 * @param scheme
	 */
	private static String convertSchemeID(String scheme) {
		// 
		String returnValue = null;
		if(scheme != null ) {

			switch(scheme) {
//			case "TIN": 
//				returnValue = "TIN"; break;
//			case "CRN": 
//				returnValue = "CRN"; break;
			case "MOL": 
				returnValue = "MOM"; break;
			case "MLSD": 
				returnValue = "MLS"; break;
//			case "700": 
//				returnValue = "700"; break;
			case "SAGL": 
				returnValue = "SAG"; break;
			case "NAI": 
				returnValue = "NAT"; break;
			case "GCCI": 
				returnValue = "GCC"; break;
			case "IQN": 
				returnValue = "IQA"; break;
			case "PAID": 
				returnValue = "PAS"; break;
//			case "OTH": 
//				returnValue = "OTH"; break;
				default:
					returnValue = scheme;
				break;
			}
		}
		return returnValue;

	}

	private static void processTax(Invoice invoice, MInvoice minvoice, java.util.Map<String, InvoiceTax> taxMap) {
		BigDecimal taxTotalAmt = taxMap.entrySet().stream()
				.map(t->t.getValue().tax)
				.reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
		// BT-110 Invoice total VAT amount	// Σ VAT category tax amount (BT-117) (TODO Note that this is NOT SUM(LineTotal)
		// BR-CO-14, BR-DEC-13, BR-KSA-EN16931-08, BR-KSA-EN16931-09, BR-KSA-F-04
		// cac:TaxTotal / cbc:TaxAmount		
		// BT-5 Currency for total VAT amount	// 
		// cac:TaxTotal / cbc:TaxAmount @currencyID
		TaxTotalType taxTotal = new TaxTotalType();
		taxTotal.setTaxAmount(taxTotalAmt, "SAR"); // TODO This shall be the converted amount in SAR
		invoice.getTaxTotals().add(taxTotal);
		
		// Note: There should be 2 copies of taxTotal. One each with & without subTotal details included
		// TODO The second one may be in Invoice currency
		TaxTotalType taxTotal1 = new TaxTotalType();
		taxTotal1.setTaxAmount(taxTotalAmt, "SAR");		

		// BT-111 Invoice total VAT amount in accounting currency // TODO Duplicate of BT-110??
		// To be used when the VAT accounting currency (BT-6) differs from the Invoice currency code (BT-5)
		// cac:TaxTotal / cbc:TaxAmount					
		// BT-5 Currency for total VAT amount
		// cac:TaxTotal / cbc:TaxAmount @currencyID
		
		
		// BT-116 VAT breakdown/VAT category taxable amount (Grouped by Tax Category)
		/*
			For each distinct combination of VAT category code and VAT rate the calculations are:
			VAT category taxable amount (BT-116) = ∑(Invoice line net amounts (BT-131)) + Document level charge amount (BT-99) − Document level allowance amount (BT-92)
			VAT category tax amount (BT-117) = VAT category taxable amount (BT-116) × (VAT rate (BT-119) ÷ 100)
		*/

//		MInvoiceTax[] taxes = minvoice.getTaxes(false);
//		for (MInvoiceTax tax : taxes) {
//			if(tax.getC_Tax_ID() == 1000005) // TODO remove hardcoding
//				continue; // Special tax added for Round Off etc
//			
//			TaxSubtotal taxSubtotal = getTaxSubTotal(minvoice, (MTax) tax.getC_Tax(), 
//					tax.getTaxBaseAmt(), tax.getTaxAmt());
//			taxTotal1.getTaxSubtotals().add(taxSubtotal);
//		}
		// Need to re-calculate the tax - as InvoiceTax table has values adjusted for pre-payment
		taxMap.entrySet().forEach(t->{
			TaxSubtotal taxSubtotal = getTaxSubTotal(minvoice, t.getValue().mtax, 
					t.getValue().taxable, t.getValue().tax);
			taxTotal1.getTaxSubtotals().add(taxSubtotal);
		});
		invoice.getTaxTotals().add(taxTotal1);
	}

	private static TaxSubtotal getTaxSubTotal(MInvoice minvoice, MTax tax, BigDecimal taxableAmt, BigDecimal taxAmt) {
		TaxSubtotal subTotal = new TaxSubtotal();
		// BR-45, BR-DEC-19, BR-S-08, BR-E-08, BR-Z-08, BR-O-08, BR-CO-18 
		// cac:TaxTotal / cac:TaxSubtotal / cbc:TaxableAmount
		// BT-5 Currency for VAT category taxable amount
		// cac:TaxTotal / cac:TaxSubtotal / cbc:TaxableAmount /@currencyID
		subTotal.setTaxableAmount(taxableAmt, "SAR");
					
		// BT-117 VAT category tax amount
		// BR-46, BR-CO-17, BR-S-09, BR-Z-09, BR-E-09, BR-O-09, BR-DEC-20, BR-CO-18 
		// cac:TaxTotal / cac:TaxSubtotal / cbc:TaxAmount
		// BT-5 Currency for category tax amount
		// cac:TaxTotal / cac:TaxSubtotal / cbc:TaxAmount /@currencyID			
		subTotal.setTaxAmount(taxAmt, "SAR");		
		/*
		 * Reason for not setting Currency. TODO confirm
		 * [BR-KSA-EN16931-09]-Only one tax total (BG-22) without tax subtotals (BG-23) must be provided when tax currency code is provided
		 */
		
		// BT-118 VAT category code // Valid Catogories are: S=Standard rated, Z=Zero rated, E=Exempt from VAT, O=Not subject to VAT
		// BR-47, BR-Z-01, BR-E-01, BR-O-01, BR-CO-18, BR-CL-18
		// cac:TaxTotal / cac:TaxSubtotal / cac:TaxCategory / cbc:ID
		// BT-119 VAT category rate	
		//cac:TaxTotal / cac:TaxSubtotal / cac:TaxCategory / cbc:Percent			
		// KSA-21	Tax scheme ID	// BR-CO-18  = VAT
		// cac:TaxTotal / cac:TaxSubtotal / cac:TaxCategory / cac:TaxScheme / cbc:ID
		TaxCategoryType taxCategory = new TaxCategoryType();
		String taxCategoryCode = getTaxCategoryCode((MTax) tax);
		taxCategory.setID(new ID("UN/ECE 5305", taxCategoryCode));
		if(!"O".equals(taxCategoryCode)) { // Except in case of 'Not subject to VAT'
			taxCategory.setPercent(tax.getRate());
		}
		taxCategory.setTaxScheme("VAT");

		if("E".equals(taxCategoryCode) ||
				"O".equals(taxCategoryCode)) {
			// BT-121 VAT exemption reason code // BR-KSA-23, BR-KSA-24, BR-KSA-69, BR-KSA-CL-04 
			// must exist if tax category is 'Z', or 'E' or ‘O’,
			// cac:TaxTotal / cac:TaxSubtotal / cac:TaxCategory / cbc:TaxExemptionReasonCode
			// BT-120 VAT exemption reason text = Tax treatment applied to the supply	// BR-KSA-83, BR-KSA-F-06
			// cac:TaxTotal / cac:TaxSubtotal /cac:TaxCategory / cbc:TaxExemptionReason
			/* BT-121 VAT exemption reason code Valid values are as follows. TODO Confirm if English/Arabic to be used
			 E = Exempt from Tax التوريدات المعفاة
			 	VATEX-SA-29 Financial services 	الخدمات المالية
			 	VATEX-SA-29-7 Life insurance services	عقد تأمين على الحياة
			 	VATEX-SA-30 Real estate transactions 	 التوريدات العقارية المعفاة من الضريبة
			 S = Standard rate/ التوريدات الخاضعة للضريبة
	
			Z = Zero rated goods 	التوريدات الخاضعة لنسبة الصفر
				VATEX-SA-32		Export of goods صادرات السلع من المملكة
				 VATEX-SA-33 	Export of services صادرات الخدمات من المملكة
				 VATEX-SA-34-1 	The international transport of Goods النقل الدولي للسلع
				VATEX-SA-34-2	international transport of passengers النقل الدولي للركاب
				VATEX-SA-34-3	services directly connected and incidental to a Supply of international passenger transportً
				 				الخدمات المرتبطة مباشرة أو عرضيابتوريد النقل الدولي للركاب			
				VATEX-SA-34-4	Supply of a qualifying means of transport توريد وسائل النقل المؤهلة
				VATEX-SA-34-5	Any services relating to Goods or passenger transportation, as defined in article twenty five of these Regulations
							الخدمات ذات الصلة بنقل السلع أوالركاب، وفقا ً للتعريف الوارد بالمادة الخامسة والعشرين من الالئحةالتنفيذية لنظام ضريبة القيامة
				VATEX-SA-35		Medicines and medical equipment ألدوية والمعدات الطبية
				VATEX-SA-36		Qualifying metals المعادن المؤهلة
				VATEX-SA-EDU 	Private education to citizen الخدمات التعليمية الخاصة للمواطنين
				VATEX-SA-HEA	Private healthcare to citizen الخدمات الصحية الخاصة للمواطنين
				VATEX-SA-MLTRY	supply of qualified military goods توريد السلع العسكرية المؤهلة
				
			O = Services outside scope of tax / Not subject to VAT/ التوريدات الغير خاضعة للضريبة
				VATEX-SA-OOS	Reason = Free Text
				
			 */
			MOrder order = (MOrder) minvoice.getC_Order();
			// TODO Either one of these may be enough
			taxCategory.setTaxExemptionReasonCode(order != null ? order.getVatExceptionReason():"Unknown");
			taxCategory.getTaxExemptionReasons().add(new TaxExemptionReason(order != null ? order.getVatExceptionReasonText():"Unknown"));
		}
		subTotal.setTaxCategory(taxCategory);
		return subTotal;
	}
	
	private static String getTaxCategoryCode(MTax tax) {
		String taxCategoryCode = "";
		switch(tax.getC_TaxCategory().getName()) {
			case "Standard rated": taxCategoryCode="S"; break;
			case "Zero rated": taxCategoryCode="Z"; break;
			case "Exempt from VAT": taxCategoryCode="E"; break;
			case "Not subject to VAT": taxCategoryCode="O"; break;
		}
		return taxCategoryCode;
	}

	static class InvoiceTax {
		String taxCategory;
		MTax mtax;
		BigDecimal taxable;
		BigDecimal tax;
		
		public InvoiceTax(String taxCategory, MTax mtax) {
			this.taxCategory = taxCategory;
			taxable = BigDecimal.ZERO;
			tax = BigDecimal.ZERO;
			this.mtax = mtax;
		}
	}
	
	private static java.util.Map<String,InvoiceTax> processLines(Invoice invoice, MInvoice minvoice) throws Exception {
		HashMap<String, InvoiceTax> taxMap = new HashMap<String, EInvoiceXmlFactory.InvoiceTax>(); 
		MCharge prepaymentCharge = minvoice.getPrepaymentCharge(); // used in Prepayment invoices
		if(prepaymentCharge == null) {
			throw new AdempiereException("Prepayment Charge not defined");
		}
		
		MProduct prepaymentAdjustmentProduct = minvoice.getPrepaymentAdjustmentProduct();
		if(prepaymentAdjustmentProduct == null) {
			throw new AdempiereException("Prepayment Adjustment product not defined");
		}
		for (MInvoiceLine mline : minvoice.getLines()) {
			if(mline.isRoundOffLine())
				continue; // RoundOff handled separately
			InvoiceLineType line = new InvoiceLineType();
			// BT-126	Invoice line identifier		// BR-21, BR-16	// 
			// cac:InvoiceLine /  cbc:ID
			line.setID(new ID(String.valueOf(mline.getLine())));
			String currency = minvoice.getCurrencyISO();	
			
			boolean isPrepaymentInvoice = minvoice.isPrepaymentInvoice();
			boolean isPrepaymentLine = isPrepaymentInvoice && mline.getC_Charge_ID() > 0 && prepaymentCharge != null 
					&& prepaymentCharge.get_ID() == mline.getC_Charge_ID();
			
			boolean isPrepaymentAdjustmentLine = mline.getM_Product_ID() > 0 && prepaymentAdjustmentProduct != null 
					&& prepaymentAdjustmentProduct.get_ID() == mline.getM_Product_ID();
			
			// Handle lines in Prepayment Invoice
			if(isPrepaymentAdjustmentLine) { // Handle pre-payment adjustment lines in real invoices
				List<MInvoice> prepayInvoiceList = minvoice.getRefAdvanceInvoices(); 
				if(prepayInvoiceList == null) {
					throw new AdempiereException("Reference to Prepayment Invoice missing");
				}
				// TODO Multiple references allowed. 
				for(MInvoice prepayDoc:prepayInvoiceList) { 	
					DocumentReferenceType prepayRef = new DocumentReferenceType();
					// Prepayment Data as Invoice Line to be added, if If Pre-Paid amount (BT-113) is included
					// KSA-26 	Prepayment ID // BR-KSA-73 // The sequential number (Invoice number BT-1) of the associated Prepayment invoice(s).
					// cac:InvoiceLine / cac:DocumentReference / cbc:ID
					prepayRef.setID(prepayDoc.getDocumentNo());
			
					// KSA-27 Prepayment UUID	// BR-KSA-73	// Optional
					// cac:InvoiceLine / cac:DocumentReference / cbc:UUID
					prepayRef.setUUID(prepayDoc.getUUID());
					
					Timestamp invoiceIssueTime = prepayDoc.getInvoiceIssueTime() != null ? prepayDoc.getInvoiceIssueTime() : prepayDoc.getDateInvoiced();
					// Prepayment Issue Date (KSA-28) – Issue date (BT-2) of the prepayment invoice(s)
					// cac:InvoiceLine / cac:DocumentReference / cbc:IssueDate
					prepayRef.setIssueDate(invoiceIssueTime.toLocalDateTime().toLocalDate());
					
					// Prepayment Issue Time (KSA-29) – Issue time (KSA-25) of the prepayment invoice(s)
					// cac:InvoiceLine / cac:DocumentReference / cbc:IssueTime
					prepayRef.setIssueTime(invoiceIssueTime.toLocalDateTime());
			
					// Prepayment Document Type	Code (KSA-30) – Invoice type code (BT-3) must be 386
					// cac:InvoiceLine / cac:DocumentReference / cbc:DocumentTypeCode
					prepayRef.setDocumentTypeCode("386");
			
					// Add reference to line
					line.getDocumentReferences().add(prepayRef);
				}
					
				// BT-129 Invoiced quantity	// BR-22
				// cac:InvoiceLine / cbc:InvoicedQuantity	
				// BT-130 Invoiced quantity unit of measure	// Optional
				// cac:InvoiceLine / cbc:InvoicedQuantity @ unitCode
				line.setInvoicedQuantity(BigDecimal.ZERO.setScale(2), "PCE"); // Hardcode qty & unit
				
				// BT-131 Invoice line net amount	// BR-24	// BR-KSA-EN16931-11,  BR-KSA-F-04, BR-KSA-82
				// cac:InvoiceLine  / cbc:LineExtensionAmount 	
				// BT-5 Currency for invoice line net amount	 // BR-KSA-CL-02
				// cac:InvoiceLine  / cbc:LineExtensionAmount @currencyID
				line.setLineExtensionAmount(BigDecimal.ZERO.setScale(2), currency); // Hardcode Net Amount to zero
			} else { // TODO Confirm line settings for Pre-payment invoice
				// BT-129 Invoiced quantity	// BR-22
				// cac:InvoiceLine / cbc:InvoicedQuantity	
				// BT-130 Invoiced quantity unit of measure	// Optional
				// cac:InvoiceLine / cbc:InvoicedQuantity @ unitCode
				line.setInvoicedQuantity(mline.getQtyEntered(), mline.getC_UOM().getUOMSymbol()); // No UoM conversion
				
				// BT-131 Invoice line net amount	// BR-24	// BR-KSA-EN16931-11,  BR-KSA-F-04, BR-KSA-82
				// cac:InvoiceLine  / cbc:LineExtensionAmount 	
				// BT-5 Currency for invoice line net amount	 // BR-KSA-CL-02
				// cac:InvoiceLine  / cbc:LineExtensionAmount @currencyID
				line.setLineExtensionAmount(mline.getLineNetAmt().setScale(2), currency);
			}
	
			// Invoice line allowance indicator //*** SKIP - as not applicable
			// cac:InvoiceLine / cac:AllowanceCharge / cbc:ChargeIndicator	
			// BT-138 Invoice line allowance percentage
			// cac:InvoiceLine / cac:AllowanceCharge / cbc:MultiplierFactorNumeric With cbc:ChargeIndicator="false"	
			// BT-139 Reason for the invoice line allowance 
			// cac:InvoiceLine / cac:AllowanceCharge / cbc:AllowanceChargeReason With cbc:ChargeIndicator="false"	
			// BT-140 Code for the reason for invoice line allowance
			// cac:InvoiceLine / cac:AllowanceCharge / cbc:AllowanceChargeReasonCode With cbc:ChargeIndicator="false"	
			// BT-136 Invoice line allowance amount (discount or rebate amount)
			// cac:InvoiceLine / cac:AllowanceCharge / cbc:Amount With cbc:ChargeIndicator="false"			
			// BT-5 Currency for line allowance amount
			// cac:InvoiceLine / cac:AllowanceCharge / cbc:Amount @currencyID		
			
			// Invoice line Charge indicator	//*** Skip as not applicable
			// cac:InvoiceLine / cac:AllowanceCharge / cbc:ChargeIndicator			
			// BT-137 Invoice line allowance base amount
			// cac:InvoiceLine / cac:AllowanceCharge / cbc:Amount With cbc:ChargeIndicator="false"	
			// BT-5 Currency for invoice line allowance base amount
			// cac:InvoiceLine / cac:AllowanceCharge / cbc:Amount @currencyID	
			
			// Invoice line Charge indicator
			// cac:InvoiceLine / cac:AllowanceCharge / cbc:ChargeIndicator			
			// BT-143 Invoice line charge percentage
			// cac:InvoiceLine / cac:AllowanceCharge / cbc:MultiplierFactorNumeric With cbc:ChargeIndicator="true"
	
			// BT-141 Invoice line charge amount 
			// cac:InvoiceLine / cac:AllowanceCharge / cbc:Amount With cbc:ChargeIndicator="true"			
			// BT-5 Currency for Invoice line charge amount
			// cac:InvoiceLine / cac:AllowanceCharge / cbc:Amount @currencyID
	
			// BT-142 Invoice line charge base amount
			// cac:InvoiceLine / cac:AllowanceCharge / cbc:BaseAmount With cbc:ChargeIndicator="true"	
			// BT-5 Currency for invoice line charge base amount
			// cac:InvoiceLine / cac:AllowanceCharge / cbc:BaseAmount @currencyID
	
			// BT-144 Reason for invoice line charge 
			// cac:InvoiceLine / cac:AllowanceCharge / cbc:AllowanceChargeReason With cbc:ChargeIndicator="true"	
			// BT-145 Code for the reason for invoice line charge 
			// cac:InvoiceLine / cac:AllowanceCharge / cbc:AllowanceChargeReasonCode With cbc:ChargeIndicator="true"
	
			TaxTotalType lineTax = new TaxTotalType();
			if(!isPrepaymentAdjustmentLine) {
				// KSA-11 VAT line amount
				// cac:InvoiceLine / cac:TaxTotal / cbc:TaxAmount
				// BT-5 Currency for VAT line amount
				// cac:InvoiceLine / cac:TaxTotal / cbc:TaxAmount @currencyID
				lineTax.setTaxAmount(mline.getTaxAmt().setScale(2), currency);	
		
				// KSA-12 Line amount inclusive VAT ?? // TODO CROSS CHECK
				// cac:InvoiceLine / cac:TaxTotal / cbc:RoundingAmount	
				// Currency for line amount inclusive VAT
				// cac:InvoiceLine / cac:TaxTotal / cbc:RoundingAmount @curencyID
				// Line Total is including Tax, in case of Exclusive pricelist. // TODO Handle inclusive pricelist
				lineTax.setRoundingAmount(mline.getLineTotalAmt().setScale(2), currency); 
				
				// Fill TaxMap to get real tax
				// Fill the MAP to adjust InvoiceTax table later
				MTax mtax = (MTax) mline.getC_Tax();
				String taxCategory = getTaxCategoryCode(mtax);
				InvoiceTax invoiceTax = taxMap.get(taxCategory);
				if(invoiceTax == null) {
					invoiceTax = new InvoiceTax(taxCategory, mtax);
				}
				invoiceTax.taxable = invoiceTax.taxable.add(mline.getLineNetAmt());
				invoiceTax.tax = invoiceTax.tax.add(mline.getTaxAmt());
				taxMap.put(taxCategory, invoiceTax);
			} else {
				// KSA-11 VAT line amount
				// cac:InvoiceLine / cac:TaxTotal / cbc:TaxAmount
				// BT-5 Currency for VAT line amount
				// cac:InvoiceLine / cac:TaxTotal / cbc:TaxAmount @currencyID
				lineTax.setTaxAmount(BigDecimal.ZERO.setScale(2), currency); // Hardcode Net Amount to zero
		
				// KSA-12 Line amount inclusive VAT ?? // TODO CROSS CHECK
				// cac:InvoiceLine / cac:TaxTotal / cbc:RoundingAmount	
				// Currency for line amount inclusive VAT
				// cac:InvoiceLine / cac:TaxTotal / cbc:RoundingAmount @curencyID
				// Line Total is including Tax, in case of Exclusive pricelist. // TODO Handle inclusive pricelist
				lineTax.setRoundingAmount(BigDecimal.ZERO.setScale(2), currency); // Hardcode Net Amount to zero
				
				// KSA-31 Prepayment VAT Category Taxable Amount
				// cac:InvoiceLine / cac:TaxTotal / cac:TaxSubtotal / cbc:TaxableAmount	
				// BT-5 Currency for Prepayment VAT category Taxable Amount
				// cac:InvoiceLine / cac:TaxTotal / cac:TaxSubtotal / cbc:TaxAmount @currencyID
				//----
				// KSA-33 Prepayment VAT Category Code
				// cac:InvoiceLine / cac:TaxTotal / cac:TaxSubtotal / cac:TaxCategory /  cbc:ID	
				// KSA-34 Prepayment VAT rate
				// cac:InvoiceLine / cac:TaxTotal / cac:TaxSubtotal / cac:TaxCategory / cbc:Percent	
				TaxSubtotal taxSubtotal = getTaxSubTotal(minvoice, (MTax) mline.getC_Tax(), 
						mline.getLineNetAmt().negate(), mline.getTaxAmt().negate()); // Amount should be submitted +ve
								
				lineTax.getTaxSubtotals().add(taxSubtotal);	
			}
			
			line.getTaxTotals().add(lineTax);
			
	
			// BT-153 Item name		// BR-25
			// cac:InvoiceLine / cac:Item / cbc:Name
			line.setItem(mline.getName());
	
			// BT-156 Item Buyer's identifier
			// cac:InvoiceLine /  cac:Item / cac:BuyersItemIdentification / cbc:ID			
			// BT-155 Item Seller's identifier
			// cac:InvoiceLine /  cac:Item / cac:SellersItemIdentification / cbc:ID	
			// BT-157 Item standard identifier
			// cac:InvoiceLine /  cac:Item / cac:StandardItemIdentification / cbc:ID
	
			// BT-151 Invoiced item VAT category code
			// cac:InvoiceLine / cac:Item / cac:ClassifiedTaxCategory / cbc:ID	
			// BT-152 Invoiced item VAT rate
			// cac:InvoiceLine / cac:Item / cac:ClassifiedTaxCategory / cbc:Percent
			// KSA-21 Tax scheme ID
			// cac:InvoiceLine / cac:Item / cac:ClassifiedTaxCategory / cac:TaxScheme / cbc:ID
			MTax tax = (MTax) mline.getC_Tax();
			TaxCategoryType taxCategory = new TaxCategoryType(getTaxCategoryCode(tax), tax.getRate());
			line.getItem().getClassifiedTaxCategories().add(taxCategory);
			
			PriceType priceType = new PriceType();
			// BT-146 Item net price
			// cac:InvoiceLine / cac:Price / cbc:PriceAmount
			// BT-5 Currency for item net price
			// cac:InvoiceLine / cac:Price / cbc:PriceAmount @ currencyID
			priceType.setPriceAmount(isPrepaymentAdjustmentLine? BigDecimal.ZERO.setScale(2).setScale(2) : mline.getPriceEntered(), currency); // Unit Price
	
			
			// BT-149 Item price base quantity
			// cac:InvoiceLine / cac:Price / cbc:BaseQuantity			
			// BT-150 Item price base quantity unit code
			// cac:InvoiceLine / cac:Price / cbc:BaseQuantity @unitCode
			priceType.setBaseQuantity(Env.ONE, mline.getC_UOM().getUOMSymbol()); // No UoM conversion
	
			// Price allowance indicator
			// cac:InvoiceLine / cac:Price /  cac:AllowanceCharge / cbc:ChargeIdicator
	
			// BT-147 Item price discount
			// cac:InvoiceLine / cac:Price /  cac:AllowanceCharge / cbc:Amount
	
			// BT-5 Currency for item price discount
			// cac:InvoiceLine / cac:Price /  cac:AllowanceCharge / cbc:Amount @ currencyID
	
			// BT-148 Item gross price
			// cac:InvoiceLine / cac:Price /  cac:AllowanceCharge / cbc:BaseAmount
	
			// BT-5 Currency for item gross prices
			// cac:InvoiceLine / cac:Price /  cac:AllowanceCharge / cbc:BaseAmount @ currencyID
						
			line.setPrice(priceType);

			invoice.getInvoiceLines().add(line);
		}
		return taxMap;
	}
	

	
	
	public static Invoice loadXml(InputStream in) throws Exception {
		// Create a Marshaller object
//		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Invoice.class);
			Unmarshaller umarshaller = jaxbContext.createUnmarshaller();

			// Marshal the root element to an XML document	
			Invoice invoice = (Invoice) umarshaller.unmarshal(in);
			return invoice;
//		} catch (JAXBException e) {
//			throw e; //new Exception("Failed to save XML Invoice", e);
//		}
	}
	
	public static byte[] generateHash(Invoice invoice) throws Exception {
//		Invoice invoiceCopy = invoice; //deepCopyJaxb(invoice);
//		// Remove *[local-name()=׳Invoice׳]//*[local-name()=׳UBLExtensions׳]
//		invoiceCopy.setUBLExtensions(null);	
//		// remove QR. //*[local-name()=׳AdditionalDocumentReference׳] [cbc:ID[normalize-space(text()) = ׳QR׳]]
//		List<DocumentReferenceType> refList = invoiceCopy.getAdditionalDocumentReferences();
//		DocumentReferenceType qrReference = null;
//		for(DocumentReferenceType x: refList) {
//			if(x.getID() != null && "QR".equals(x.getID().getValue())) {
//				qrReference = x;
//				break;
//			}			
//		}
//		if(qrReference != null)
//			invoiceCopy.getAdditionalDocumentReferences().remove(qrReference); 
//		
//		// [local-name()=׳Invoice׳]//*[local-name()=׳Signature׳]
//		invoiceCopy.getSignatures().clear();
		
		byte[] canonicalXml = canonicalize(invoice, true);
		return QRUtil.generateSHA256Hash(canonicalXml);
//		return QRUtil.generateHashHex(canonicalXml);
	}

	
	/*
	 * public static File generateHash(Invoice invoice) throws Exception { String
	 * fileName = "eInvoice" + invoice.getID().getValue() .replaceAll("\\s+", "_")
	 * .replaceAll("\\\\", "_") .replaceAll("/", "_"); File inputFile = new
	 * File(System.getProperty("java.io.tmpdir"), fileName + ".xml"); File outFile =
	 * new File(System.getProperty("java.io.tmpdir"), fileName + "-signed.xml");
	 * 
	 * FileOutputStream out = null; try { // Write the Invoice XML into tmp file
	 * first BufferedOutputStream outStream1 = new BufferedOutputStream(new
	 * FileOutputStream(inputFile)); writeXml(invoice, outStream1);
	 * outStream1.close();
	 * 
	 * // Pass the XML file to ZATCA SDK. Output file will be created by the SDK
	 * ProcessBuilder builder = new ProcessBuilder("fatoora", "-sign",
	 * "-signedInvoice", outFile.getAbsolutePath(), "-invoice",
	 * inputFile.getAbsolutePath()); // Map<String, String> envMap = new
	 * HashMap<String, String>() {{ // put("key1", "value1"); // }}; //
	 * builder.environment(envMap);
	 * 
	 * // builder.directory(new File("working_directory"));
	 * 
	 * Process process = builder.start();
	 * 
	 * // Wait for the process to complete int exitCode = process.waitFor();
	 * printConsole(process, exitCode); } catch (InterruptedException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } catch (Exception e) {
	 * e.printStackTrace(); throw e; } return outFile; // outFile will be filled by
	 * the process }
	 */
	
	/**
	 * 
	 * @param <T>
	 * @param object
	 * @return
	 * @throws IOException 
	 */
	private static <T> T deepCopyJaxb(T object) throws Exception {
//		  try {
//			Class<T> clazz = (Class<T>) object.getClass();
//		    JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
//		    JAXBElement<T> contentObject = new JAXBElement<T>(new QName(clazz.getSimpleName()), clazz, object);
//		    JAXBSource source = new JAXBSource(jaxbContext, contentObject);
//		    return jaxbContext.createUnmarshaller().unmarshal(source, clazz).getValue();
//		  } catch (JAXBException e) {
//		      throw new RuntimeException(e);
//		  }
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		marshalJaxb(object, out, false);
		try (InputStream in = new ByteArrayInputStream(out.toByteArray())) {
			T x = (T) unmarshalJaxb(object.getClass(), in);		
			return x;
		}
	}
	
	
	public static <T> T unmarshalJaxb(Class<T> clazz, InputStream in) {
		  try {
		    JAXBContext jaxbContext = JAXBContext.newInstance(clazz);		    
		    java.lang.Object object = jaxbContext.createUnmarshaller().unmarshal(in);
		    if(clazz.isAssignableFrom(object.getClass())) {
		    	return (T)object;
		    } else {
		    	throw new InvalidParameterException("Invalid JAXB class for XML");
		    }
		  } catch (JAXBException e) {
		      throw new RuntimeException(e);
		  }
	}

	private static void marshalDocument(Document document, OutputStream out) throws Exception {
	    // Now format the Document
	    TransformerFactory transformerFactory = TransformerFactory.newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // Set indent amount

	    // Output the formatted XML
	    DOMSource source = new DOMSource(document);
	    StreamResult result = new StreamResult(out); // or any other output stream
	    transformer.transform(source, result);
		
	}

	public static <T> void marshalJaxb(T jaxbObject, OutputStream out, boolean isFragment) throws Exception {
		  try {
			  CustomNamespacePrefixMapper customMapper = new CustomNamespacePrefixMapper();
			  JAXBContext jaxbContext = null;
			  if(!(jaxbObject instanceof Invoice)) {
					jaxbContext = JAXBContext.newInstance(customMapper.getPackages());
			  } else {
				  jaxbContext = JAXBContext.newInstance(jaxbObject.getClass());
			  }
			Marshaller marshaller = jaxbContext.createMarshaller();
		    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		    marshaller.setProperty(Marshaller.JAXB_FRAGMENT, isFragment?Boolean.TRUE:Boolean.FALSE);

		    // Set the custom NamespacePrefixMapper
		    marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", customMapper);
			marshaller.marshal(jaxbObject, out);	
		  } catch (JAXBException e) {
		      throw new RuntimeException(e);
		  }
//		marshalDocument(marshalToDocument(jaxbObject), out);
	}	

    public static Document marshalToDocument(java.lang.Object jaxbObject) throws Exception {
		CustomNamespacePrefixMapper customMapper = new CustomNamespacePrefixMapper();
        JAXBContext jaxbContext = JAXBContext.newInstance(customMapper.getPackages());
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE); // To remove xml version
        marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", customMapper);

        // Create a Document to hold the marshalled XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        // Marshal the JAXB object into the Document
        marshaller.marshal(jaxbObject, document);
        return document;
    }
	
    public static byte[] canonicalize(Invoice invoiceXml, boolean strip) throws Exception {
    	
//    	Document doc = marshalToDocument(invoiceXml);
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
    	marshalJaxb(invoiceXml, out, false);

		byte[] processedXml = out.toByteArray();
		if(strip) {
			String xsltPathStripped = "/resources/invoice-stripped.xsl"; // Removes the specified blocks for creating Hash & Signature
			processedXml = CanonicalizeHelper.transform(processedXml, xsltPathStripped);
		}  
		String processedXml2 = new String(processedXml).replaceAll("xmlns=\"\" ", "").replaceAll("xmlns:ns10=\"urn:oasis:names:specification:ubl:schema:xsd:Invoice-2\" ", "");

		byte[] finalXml = ZatcaSDKProcessHelper.formatXml(processedXml2.getBytes());
		// Make final corrections to namespaces, etc
		String finalXmlString = new String(finalXml)
				.replaceFirst("<sig:UBLDocumentSignatures.*>", "<sig:UBLDocumentSignatures>")
				.replaceFirst("<ds:Signature Id=\"signature\">", "<ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Id=\"signature\">")
				.replaceFirst("<xades:QualifyingProperties Target=\"signature\">", 
						"<xades:QualifyingProperties xmlns:xades=\"http://uri.etsi.org/01903/v1.3.2#\" Target=\"signature\">");

		finalXml = ZatcaSDKProcessHelper.canonicalizeXml(finalXmlString.getBytes(), true);
//    	byte[] finalXml2 = CanonicalizeHelper.canonicalize(out.toByteArray(), strip);
		
//		// Make final corrections to namespaces, etc
//		String finalXmlString = new String(finalXml)
//				.replaceFirst("<sig:UBLDocumentSignatures.*>", "<sig:UBLDocumentSignatures>")
//				.replaceFirst("<ds:Signature Id=\"signature\">", "<ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" Id=\"signature\">")
//				.replaceFirst("<xades:QualifyingProperties Target=\"signature\">", 
//						"<xades:QualifyingProperties xmlns:xades=\"http://uri.etsi.org/01 903/v1.3.2#\" Target=\"signature\">");
    	return finalXml;//String.getBytes();
    }

    /**
     * Extract canonical form of SignedProperties tag from the QualifyingProperties/Invoice xml
     * @param signedPropertiesXml
     * @return
     * @throws Exception
     */
    public static <T> byte[] extractSignedProperties(T signedPropertiesXml) throws Exception {    	
    	ByteArrayOutputStream out = new ByteArrayOutputStream();
//    	marshalSignedProperties(signedPropertiesXml, out);
    	marshalJaxb(signedPropertiesXml, out, false);
		String xsltPath = "/resources/signedProperties.xsl";

		byte[] inputXml = ZatcaSDKProcessHelper.formatXml(out.toByteArray());
		inputXml = ZatcaSDKProcessHelper.canonicalizeXml(inputXml, false);
    	byte[] canonicalXml = CanonicalizeHelper.transform(inputXml, xsltPath);
    	
    	
    	// Expected out format
    			//      String signedPropertiesData = 
//    			"                                    <xades:SignedProperties xmlns:xades=\"http://uri.etsi.org/01903/v1.3.2#\" Id=\"xadesSignedProperties\">\n" + 
//    			"                                        <xades:SignedSignatureProperties>\n" + 
//    			"                                            <xades:SigningTime>2024-12-11T11:33:47</xades:SigningTime>\n" + 
//    			"                                            <xades:SigningCertificate>\n" + 
//    			"                                                <xades:Cert>\n" + 
//    			"                                                    <xades:CertDigest>\n" + 
//    			"                                                        <ds:DigestMethod xmlns:ds=\\\"http://www.w3.org/2000/09/xmldsig#\\\" Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/>\n" + 
//    			"                                                        <ds:DigestValue xmlns:ds=\\\"http://www.w3.org/2000/09/xmldsig#\\\">MHdLMEVWZGNsV1dZeGVpS3UwaFdSU1ZXcGF1S0FmZXN1Vm9HblVabUpJVT0=</ds:DigestValue>\n" + 
//    			"                                                    </xades:CertDigest>\n" + 
//    			"                                                    <xades:IssuerSerial>\n" + 
//    			"                                                        <ds:X509IssuerName xmlns:ds=\\\"http://www.w3.org/2000/09/xmldsig#\\\">CN=PRZEINVOICESCA4-CA, DC=extgazt, DC=gov, DC=local</ds:X509IssuerName>\n" + 
//    			"                                                        <ds:X509SerialNumber xmlns:ds=\\\"http://www.w3.org/2000/09/xmldsig#\\\">379112742831380471835263969587287663520528387</ds:X509SerialNumber>\n" + 
//    			"                                                    </xades:IssuerSerial>\n" + 
//    			"                                                </xades:Cert>\n" + 
//    			"                                            </xades:SigningCertificate>\n" + 
//    			"                                        </xades:SignedSignatureProperties>\n" + 
//    			"                                    </xades:SignedProperties>";
    	String signedPropString = new String(canonicalXml);
    	
    	// Do post-processing to adjust namespace usage and spaces
    			// TODO But samples from ZATCA shows first line not indented..
			signedPropString = signedPropString.replaceFirst("<xades:SignedProperties.*Id=\"xadesSignedProperties\">",
					"<xades:SignedProperties xmlns:xades=\"http://uri.etsi.org/01903/v1.3.2#\" Id=\"xadesSignedProperties\">")
    			.replace("<ds:DigestMethod", "<ds:DigestMethod xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\"")
    			.replace("<ds:DigestValue>", "<ds:DigestValue xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">")
    			.replace("<ds:X509IssuerName>", "<ds:X509IssuerName xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">")
    			.replace("<ds:X509SerialNumber>", "<ds:X509SerialNumber xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\">")
    			.replaceAll("\n", "").replaceAll(">\\s+<", "><")
    			.trim();
//    	    	signedPropString = "                                    ".concat(signedPropString); // indent first line by 9 tabs/36 spaces. Other lines are already indented
//    	signedPropString = Arrays.asList(signedPropString.split("\n")).stream()
//    		.map(l->"                                    ".concat(l)).collect(Collectors.joining("\n")); // Prefix 9 tabs (36 spaces)

			byte[] signedPropData = signedPropString.trim().getBytes();
//			byte[] signedPropData = ZatcaSDKProcessHelper.canonicalizeXml(signedPropString.getBytes(), true);

    	Files.write(File.createTempFile("signedProperties", ".xml").toPath(), signedPropData); 
    	return signedPropData;
    }
    
	/**
	 * Extract the Invoice signature from XML
	 * @param invoiceXml
	 * @return
	 */
	public static String getInvoiceSignature(Invoice invoiceXml) { // TODO Wrong..
		oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.Signature x =
				(invoiceXml.getSignatures() != null && !invoiceXml.getSignatures().isEmpty()) ? invoiceXml.getSignatures().get(0) : null;
		
		if(x == null)
			return null;
		AttachmentType attachment = x.getDigitalSignatureAttachment();
		EmbeddedDocumentBinaryObject binaryData = attachment != null ? attachment.getEmbeddedDocumentBinaryObject(): null;
		String invoiceSign = binaryData != null && binaryData.getValue() != null ? new String(binaryData.getValue()) : null;
		return invoiceSign;
	}
	
	

}
