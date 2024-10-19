package com.betagroup.einvoice;

import java.io.File;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.MBPartner;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrg;
import org.compiere.model.MOrgInfo;
import org.w3._2000._09.xmldsig_.KeyInfo;
import org.w3._2000._09.xmldsig_.Signature;
import org.w3._2000._09.xmldsig_.SignatureValue;
import org.w3._2000._09.xmldsig_.SignedInfo;
import org.w3._2000._09.xmldsig_.X509Data;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.AddressType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.AttachmentType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.BillingReference;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.CountryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.CustomerPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.DeliveryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.DocumentReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.InvoiceLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyIdentification;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyLegalEntity;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyTaxScheme;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.PaymentMeans;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.SupplierPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.TaxTotalType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EmbeddedDocumentBinaryObject;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ID;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IdentificationCode;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InstructionNote;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InvoicedQuantity;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.Note;
import oasis.names.specification.ubl.schema.xsd.invoice_2.Invoice;
import oasis.names.specification.ubl.schema.xsd.signatureaggregatecomponents_2.SignatureInformation;

public class EInvoiceXmlFactory {

	public static Invoice createXmlInvoice(MInvoice minvoice) throws Exception {
		Invoice invoice = new Invoice();
		// KSA – EN16931 (BR-KSA-EN16931) // cbc:ProfileID
		invoice.setProfileID("reporting:1.0"); // Hardcoded value
		// BR-KSA-F-06 // 2.1 Invoice Reference Number (IRN) // cbc:ID
		invoice.setID(minvoice.getDocumentNo());
		// BR-KSA-03 // cbc:UUID
		invoice.setUUID(minvoice.getUUID());
		// BR-KSA-04, BR-KSA-F-01	// cbc:IssueDate
		invoice.setIssueDate(minvoice.getDateInvoiced().toLocalDateTime().toLocalDate());
		// BR-KSA-70	// cbc:IssueTime
		invoice.setIssueTime(minvoice.getDateInvoiced().toLocalDateTime());
		
		// BT-3 = BR-KSA-06, BR-KSA-07, BR-KSA-31 // cbc:InvoiceTypeCode / @name
		/*
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
		} else { // Simplified
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

		invoice.setInvoiceTypeCode(invoiceTypeCode);
		
		// BR-KSA-F-06 	// cbc:Note // Optional
		Note note = new Note();
		note.setValue(minvoice.getDescription());
		invoice.getNotes().add(note);// Not found
		
		// Tax currency code // cbc:TaxCurrencyCode //BR-KSA-EN16931-02,BR-KSA-68
		invoice.setTaxCurrencyCode("SAR"); // hardcoded, as it is the only value allowed
		// TODO tax should be set in invoice as SAR, irrespective of Invoice currency
		
		// Purchase order ID	// cac:OrderReference/cbc:ID 	// BR-KSA-F-06 // Optional // 1:1
		invoice.setOrderReference(minvoice.getPOReference());
		
		// Billing reference ID  = The sequential number (Invoice number BT-1) of the original invoice(s) 
		// that the credit/debit note is related to. // cac:BillingReference / cac:InvoiceDocumentReference / cbc:ID 
		// Mandatory for Credit Note/Debit Note. 1:n 
		
		// forEach() {
		DocumentReferenceType refType = new DocumentReferenceType();
		// TODO
//		refType.setID(new ID(invoice.getReferenceInvoice);
//		refType.setDocumentType(value);
//		refType.setIssueDate(value);
		BillingReference billingReference = new BillingReference();
		billingReference.setInvoiceDocumentReference(refType); // setDebitNoteDocumentReference(value); CreditNoteDocumentReference(value); 
		invoice.getBillingReferences().add(billingReference);
		// }
		
		// Contract ID // cac:ContractDocumentReference / cbc:ID 	// Optional = Skip
		
		// KSA-16 = Invoice counter value 	// cac:AdditionalDocumentReferene / cbc:UUID where cac:AdditionalDocumentReferene / cbc:ID = ICV
		// BR-KSA-34 = The invoice counter value (KSA-16) contains only digits. ==> Means use ID?
		DocumentReferenceType counterValue = new DocumentReferenceType();
		counterValue.setUUID(String.valueOf(minvoice.get_ID()));
		counterValue.setID("ICV");
		invoice.getAdditionalDocumentReferences().add(counterValue);
		
		// KSA-13 = Previous invoice hash	// hash must be base64 encoded SHA256.	// BR-KSA-26, BR-KSA-61 
		/* 	cac:AdditionalDocumentReference / cac:Attachment / cbc:EmbeddedDocumentBinaryObject 
			 where
			 cac:AdditionalDocumentReference / cbc:ID = PIH
			 cac:AdditionalDocumentReference / cac:Attachment / cbc:EmbeddedDocumentBinaryObject /@mimeCode = text/plain
		*/
		DocumentReferenceType previousHash = new DocumentReferenceType();
		EmbeddedDocumentBinaryObject prevHashData = new EmbeddedDocumentBinaryObject();
		prevHashData.setMimeCode("text/plain");
		MInvoice previousInvoice = MInvoice.get(minvoice.getCtx(), minvoice.getPreviousInvoice_ID());
		AttachmentType attachHash = new AttachmentType();
		attachHash.setEmbeddedDocumentBinaryObject(previousInvoice.getInvoiceHash()); // TODO
		previousHash.setAttachment(attachHash);
		previousHash.setID("PIH");
		invoice.getAdditionalDocumentReferences().add(previousHash);
		
		// KSA-14 = Invoice QR code	//  BR-KSA-27 // base64Binary.
		/* cac:AdditionalDocumentReference / cac:Attachment / cbc:EmbeddedDocumentBinaryObject		 
		 where
		 cac:AdditionalDocumentReference / cbc:ID = QR
		 cac:AdditionalDocumentReference / cac:Attachment / cbc:EmbeddedDocumentBinaryObject /@mimeCode = text/plain
		*/
		DocumentReferenceType qrCode = new DocumentReferenceType();
		AttachmentType attachQrCode = new AttachmentType();
		EmbeddedDocumentBinaryObject qrData = new EmbeddedDocumentBinaryObject();
		qrData.setMimeCode("text/plain");
		attachQrCode.setEmbeddedDocumentBinaryObject(minvoice.getQRCode()); // TODO
		qrCode.setAttachment(attachHash);
		qrCode.setID("QR");
		invoice.getAdditionalDocumentReferences().add(qrCode);
		
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
		MOrg org = MOrg.get(minvoice.getCtx(), minvoice.getAD_Org_ID());
		MOrgInfo orgInfo = MOrgInfo.get(minvoice.getCtx(), minvoice.getAD_Org_ID(), null);

		SignatureInformation signInfo = new SignatureInformation();
		signInfo.setID(new ID("urn:oasis:names:specification:ubl:signature:1"));
		signInfo.setReferencedSignatureID("urn:oasis:names:specification:ubl:signature:Invoice");
		Signature sign = new Signature(); 
		SignedInfo signedInfo = new SignedInfo();
		signedInfo.setCanonicalizationMethod("http://www.w3.org/2006/12/xml-c14n11");
		signedInfo.setSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#ecdsa-sha256");
		sign.setSignedInfo(signedInfo);	
		
		sign.setSignatureValue(new SignatureValue("sinature-value-placeholder")); 	//minvoice.getSignature()));	
		KeyInfo keyInfo = new KeyInfo(org.getCertificate()); 

		sign.setKeyInfo(keyInfo);	// TODO
		signInfo.setSignature(sign); // TODO Actual Signature
		
		oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.Signature invoiceSign
			= new oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.Signature();
		invoiceSign.setID("urn:oasis:names:specification:ubl:signature:Invoice");
		invoiceSign.setSignatureMethod("urn:oasis:names:specification:ubl:dsig:enveloped:xades");
		invoice.getSignatures().add(invoiceSign);
		
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
		partyId.setID(new ID(org.getCRN()));
		partyId.setSchemeID("CRN"); // Hardcode as ONLY CRN is used
		party.getPartyIdentifications().add(partyId);
		
		// Seller Address BT-35, BT-36, BT-37, BT-38, BT-39, BT-40, 
		// BR_KSA-09 Seller address must contain street	name (BT-35), building number (KSA-17), postal code (BT-38), city
		// (BT-37), District (KSA-3), countrycode (BT-40).
		// Optional: Additional Street Name, Plot Identification, 

		
		// BT-31 VAT number (registered) or Group VAT number	//BR-KSA-39, BR-KSA-40
		/*
		 * cac:AccountingSupplierParty / cac:Party / cac:PartyTaxScheme / cbc:CompanyID
			where  cac:AccountingSupplierParty / cac:Party / cac:PartyTaxScheme / cac:TaxScheme = VAT
		 */
		PartyTaxScheme taxScheme = new PartyTaxScheme();
		taxScheme.setCompanyID(orgInfo.getTaxID());
		taxScheme.setTaxScheme("VAT");
		party.getPartyTaxSchemes().add(taxScheme);
		
		// BT-27 Seller name	// 
		// cac:AccountingSupplierParty / cac:Party / cac:PartyLegalEntity / cbc:RegistrationName
		PartyLegalEntity legalEntity = new PartyLegalEntity();
		legalEntity.setRegistrationName(org.getName());
		party.getPartyLegalEntities().add(legalEntity);
		
		
		SupplierPartyType supplierParty = new SupplierPartyType();
		supplierParty.setParty(party);
		invoice.setAccountingSupplierParty(supplierParty);
		
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
		PartyIdentification party2Id = new PartyIdentification();
		MBPartner bp = (MBPartner) minvoice.getC_BPartner();
		party2Id.setID(new ID(bp.getLicenseNo()));
		party2Id.setSchemeID(convertSchemeID(bp.getLicenseScheme()));
		PartyType party2 = new PartyType();
		party2.getPartyIdentifications().add(party2Id);
				
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
		AddressType address2 = new AddressType();
		MBPartnerLocation buyerLocation = (MBPartnerLocation) minvoice.getC_BPartner_Location();
		
		address2.setStreetName(buyerLocation.getStreetName()); // Field added in BPLocation table
//		address2.setAdditionalStreetName(buyerLocation.getStreetName2()); // Field added in BPLocation table
		address2.setBuildingNumber(buyerLocation.getBuildingNo()); // Field added in BPLocation table
		address2.setPlotIdentification(buyerLocation.getAdditionalNo()); // Field added in BPLocation table
		address2.setCityName(buyerLocation.getC_Location().getCity());
		address2.setPostalZone(buyerLocation.getPostalZip()); // buyerLocation.getC_Location().getPostal());
		address2.setCountrySubentity(buyerLocation.getC_Location().getC_Region().getName());
		address2.setCitySubdivisionName(buyerLocation.getDistrict()); // Field added in BPLocation table
		CountryType country2 = new CountryType(buyerLocation.getC_Location().getC_Country().getCountryCode(),
				buyerLocation.getC_Location().getC_Country().getName());
		address2.setCountry(country2);		
		party2.setPostalAddress(address2);
		
		// BT-48	Buyer's VAT 	// BR-KSA-44, BR-KSA-46
		// cac:AccountingCustomerParty / cac:Party / cac:PartyTaxScheme / cbc:CompanyID
		// where  cac:AccountingCustomerParty / cac:Party / cac:PartyTaxScheme / cac:TaxScheme = VAT
		PartyTaxScheme taxScheme2 = new PartyTaxScheme();
		taxScheme2.setCompanyID(orgInfo.getTaxID());
		taxScheme2.setTaxScheme("VAT");
		party2.getPartyTaxSchemes().add(taxScheme);
		
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
		// 
		delivery.setActualDeliveryDate(// minvoice.getDateDelivered().toLocalDateTime().toLocalDate());
				// Use DateInvoiced itself, as shipment may be done later. TODO Can we enter future date instead?
				minvoice.getDateInvoiced().toLocalDateTime().toLocalDate()); 
		// KSA-24 Supply end date	// BR-KSA-F-01, BR-KSA-35, BR-KSA-36, BR-KSA-72	// format YYYY-MM-DD // Mandatory for Continuous Supplies. 
		// cac:Delivery / cbc:LatestDeliveryDate
//		delivery.setLatestDeliveryDate(minvoice.getDateDeliveredLast().toLocalDateTime().toLocalDate());	// Not used
			// minvoice.getDateInvoiced().toLocalDateTime().toLocalDate()); // for testing
		invoice.getDeliveries().add(delivery);

		
		// BT-81 Payment means type code // BR-KSA-16 // UNTDID 4461	// OPTIONAL;
		// cac:PaymentMeans / cbc:PaymentMeansCode 
		// 10    In cash, Credit = 30, Direct Debit = 49, Credit card = 54, Debit card = 55, 60    Promissory note, 68    Online payment service
		// TODO 
		
		// KSA-10 Reasons for issuance of credit / debit note //BR-KSA-17
		// cac:PaymentMeans / cbc:InstructionNote
		// TODO Shoud it be English/Arabic
		/*
		 Reasons for issuance of credit / debit note as per Article 40 (paragraph 1) and Article 54 (3) of KSA VAT regulations, a Credit and Debit Note is issued for these 5 instances:
		- Cancellation or suspension of the supplies after its occurrence either wholly or partially (تم إلغاء أو وقف التوريد بعد حدوثه أو اعتباره كلياً أو جزئياً)
		- In case of essential change or amendment in the supply, which leads to the change of the VAT due (وجود تغيير أو تعديل جوهري في طبيعة التوريد بحيث يؤدي الى تغيير الضريبة المستحقة)
		- Amendment of the supply value which is pre-agreed upon between the supplier and consumer (تم الاتفاق على تعديل قيمة التوريد مسبقاً)
		- In case of goods or services refund. (عند ترجيع السلع أو الخدمات)
		- In case of change in Seller's or Buyer's information (عند التعديل على بيانات المورد أو المشتري)
		 */
		PaymentMeans paymentMeans = new PaymentMeans();
		InstructionNote returnNote = new InstructionNote();
		returnNote.setValue(minvoice.getReturnReasonString());
		paymentMeans.getInstructionNotes().add(returnNote);

		// KSA-22 Payment terms // Optional. Free Text	
		// cac:PaymentMeans / cac:PayeeFinancialAccount / cbc:PaymentNote

		// BT-84 Payment account identifier		// IBAN Number	// Optional
		// cac:PaymentMeans / cac:PayeeFinancialAccount / cbc:ID
		
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

		// BT-107 Sum of allowances on document level // Optional // Sum of Line Level allowances
		// cac:LegalMonetaryTotal / cbc:AllowanceTotalAmount

		// BT-5 Currency for sum of allowances on document level	// 
		// cac:LegalMonetaryTotal / cbc:AllowanceTotalAmount @currencyID

		// BT-109 Invoice total amount without VAT	// Net Amount - Doclevel allowance + DocLevel charge
		// cac:LegalMonetaryTotal / cbc:TaxExclusiveAmount 
		// TODO Calculation depends on How charge/discount is handled at Document level
		
		// BT-5 Currency for invoice total amount without VAT
		// cac:LegalMonetaryTotal / cbc:TaxExclusiveAmount @currencyID

		// BT-110 Invoice total VAT amount	// Σ VAT category tax amount (BT-117) (TODO Note that this is NOT SUM(LineTotal)
		// BR-CO-14, BR-DEC-13, BR-KSA-EN16931-08, BR-KSA-EN16931-09, BR-KSA-F-04
		// cac:TaxTotal / cbc:TaxAmount
		
		// BT-5 Currency for total VAT amount	// 
		// cac:TaxTotal / cbc:TaxAmount @currencyID

		// BT-111 Invoice total VAT amount in accounting currency // TODO Duplicate of BT-110??
		// To be used when the VAT accounting currency (BT-6) differs from the Invoice currency code (BT-5)
		// cac:TaxTotal / cbc:TaxAmount			
		
		// BT-5 Currency for total VAT amount
		// cac:TaxTotal / cbc:TaxAmount @currencyID

		// BT-112 Invoice total amount with VAT		// BR-KSA-F-04
		// If VAT total is not entered, Gross Total to be entered Statement - "Amount includes VAT"
		// cac:LegalMonetaryTotal / cbc:TaxInclusiveAmount
		
		// Currency for invoice total amount with VAT
		// cac:LegalMonetaryTotal / cbc:TaxInclusiveAmount @currencyID
		
		// Pre-Paid amount	// Optional
		// cac:LegalMonetaryTotal / cbc:PrepaidAmount

		// Currency for pre-paid amount	// Optional
		// cac:LegalMonetaryTotal / cbc:PrepaidAmount @currencyID
		
		// Rounding amount		// Optional
		// cac:LegalMonetaryTotal/cbc:PayableRoundingAmount

		// Currency for Rounding amount	// Optional		
		// cac:LegalMonetaryTotal/cbc:PayableRoundingAmount @currencyID

		
		// BT-115 Amount due for payment // REQUIRED iF Pre-Paid amount/Rounding amount is populated
		// cac:LegalMonetaryTotal / cbc:PayableAmount
		
		// BT-5 Currency for amount due for payment
		// cac:LegalMonetaryTotal / cbc:PayableAmount @currencyID

		
		processTaxBreakdown(invoice, minvoice);

		processLines(invoice, minvoice); 
		
		return invoice;
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

	private static void processTaxBreakdown(Invoice invoice, MInvoice minvoice) {
		// BT-116 VAT breakdown/VAT category taxable amount (Grouped by Tax Category)
		/*
			For each distinct combination of VAT category code and VAT rate the calculations are:
			VAT category taxable amount (BT-116) = ∑(Invoice line net amounts (BT-131)) + Document level charge amount (BT-99) − Document level allowance amount (BT-92)
			VAT category tax amount (BT-117) = VAT category taxable amount (BT-116) × (VAT rate (BT-119) ÷ 100)
		*/
		// BR-45, BR-DEC-19, BR-S-08, BR-E-08, BR-Z-08, BR-O-08, BR-CO-18 
		// cac:TaxTotal / cac:TaxSubtotal / cbc:TaxableAmount
		invoice.getTaxTotals().add(new TaxTotalType());
		minvoice.getTaxes(false);

		// BT-5 Currency for VAT category taxable amount
		// cac:TaxTotal / cac:TaxSubtotal / cbc:TaxableAmount /@currencyID
		
		// BT-117 VAT category tax amount
		// BR-46, BR-CO-17, BR-S-09, BR-Z-09, BR-E-09, BR-O-09, BR-DEC-20, BR-CO-18 
		// cac:TaxTotal / cac:TaxSubtotal / cbc:TaxAmount

		// BT-5 Currency for category tax amount
		// cac:TaxTotal / cac:TaxSubtotal / cbc:TaxAmount /@currencyID

		// BT-118 VAT category code
		// BR-47, BR-Z-01, BR-E-01, BR-O-01, BR-CO-18, BR-CL-18
		// cac:TaxTotal / cac:TaxSubtotal / cac:TaxCategory / cbc:ID

		// BT-119 VAT category rate	
		//cac:TaxTotal / cac:TaxSubtotal / cac:TaxCategory / cbc:Percent
 
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

		// KSA-21	Tax scheme ID	// BR-CO-18  = VAT
		// cac:TaxTotal / cac:TaxSubtotal / cac:TaxCategory / cac:TaxScheme / cbc:ID
	}

	private static void processLines(Invoice invoice, MInvoice minvoice) {
		for (MInvoiceLine minvoiceline : minvoice.getLines()) {
			
			invoice.getInvoiceLines().add(new InvoiceLineType());
		// BT-126	Invoice line identifier		// BR-21, BR-16	// 
		// cac:InvoiceLine /  cbc:ID

		// Prepayment Data as Invoice Line to be added, if If Pre-Paid amount (BT-113) is included
		// KSA-26 	Prepayment ID // BR-KSA-73 // The sequential number (Invoice number BT-1) of the associated Prepayment invoice(s).
		// cac:InvoiceLine / cac:DocumentReference / cbc:ID

		// KSA-27 Prepayment UUID	// BR-KSA-73	// Optional
		// cac:InvoiceLine / cac:DocumentReference / cbc:UUID
		
		// Prepayment Issue Date (KSA-28) – Issue date (BT-2) of the prepayment invoice(s)
		// cac:InvoiceLine / cac:DocumentReference / cbc:IssueDate
		
		// Prepayment Issue Time (KSA-29) – Issue time (KSA-25) of the prepayment invoice(s)
		// cac:InvoiceLine / cac:DocumentReference / cbc:IssueTime

		// Prepayment Document Type	Code (KSA-30) – Invoice type code (BT-3) must be 386
		// cac:InvoiceLine / cac:DocumentReference / cbc:DocumentTypeCode

		// BT-129 Invoiced quantity	// BR-22
		// cac:InvoiceLine / cbc:InvoicedQuantity

		// BT-130 Invoiced quantity unit of measure	// Optional
		// cac:InvoiceLine / cbc:InvoicedQuantity @ unitCode

		// BT-131 Invoice line net amount	// BR-24	// BR-KSA-EN16931-11,  BR-KSA-F-04, BR-KSA-82
		// cac:InvoiceLine  / cbc:LineExtensionAmount 

		// BT-5 Currency for invoice line net amount	 // BR-KSA-CL-02
		// cac:InvoiceLine  / cbc:LineExtensionAmount @currencyID

		// Invoice line allowance indicator
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
		
		// Invoice line Charge indicator
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

		// KSA-11 VAT line amount
		// cac:InvoiceLine / cac:TaxTotal / cbc:TaxAmount

		// BT-5 Currency for VAT line amount
		// cac:InvoiceLine / cac:TaxTotal / cbc:TaxAmount @currencyID

		// KSA-12 Line amount inclusive VAT ?? // TODO CROSS CHECK
		// cac:InvoiceLine / cac:TaxTotal / cbc:RoundingAmount

		// Currency for line amount inclusive VAT
		// cac:InvoiceLine / cac:TaxTotal / cbc:RoundingAmount @curencyID

		// KSA-31 Prepayment VAT Category Taxable Amount
		// cac:InvoiceLine / cac:TaxTotal / cac:TaxSubtotal / cbc:TaxableAmount

		// BT-5 Currency for Prepayment VAT category Taxable Amount
		// cac:InvoiceLine / cac:TaxTotal / cac:TaxSubtotal / cbc:TaxAmount @currencyID

		// BT-153 Item name		// BR-25
		// cac:InvoiceLine / cac:Item / cbc:Name

		// BT-156 Item Buyer's identifier
		// cac:InvoiceLine /  cac:Item / cac:BuyersItemIdentification / cbc:ID
		
		// BT-155 Item Seller's identifier
		// cac:InvoiceLine /  cac:Item / cac:SellersItemIdentification / cbc:ID

		// BT-157 Item standard identifier
		// cac:InvoiceLine /  cac:Item / cac:StandardItemIdentification / cbc:ID

		// BT-146 Item net price
		// cac:InvoiceLine / cac:Price / cbc:PriceAmount

		// BT-5 Currency for item net price
		// cac:InvoiceLine / cac:Price / cbc:PriceAmount @ currencyID

		// BT-151 Invoiced item VAT category code
		// cac:InvoiceLine / cac:Item / cac:ClassifiedTaxCategory / cbc:ID

		// BT-152 Invoiced item VAT rate
		// cac:InvoiceLine / cac:Item / cac:ClassifiedTaxCategory / cbc:Percent

		// KSA-33 Prepayment VAT Category Code
		// cac:InvoiceLine / cac:TaxTotal / cac:TaxSubtotal / cac:TaxCategory /  cbc:ID

		// KSA-34 Prepayment VAT rate
		// cac:InvoiceLine / cac:TaxTotal / cac:TaxSubtotal / cac:TaxCategory / cbc:Percent

		// KSA-21 Tax scheme ID
		// cac:InvoiceLine / cac:Item / cac:ClassifiedTaxCategory / cac:TaxScheme / cbc:ID

		// BT-149 Item price base quantity
		// cac:InvoiceLine / cac:Price / cbc:BaseQuantity
		
		// BT-150 Item price base quantity unit code
		// cac:InvoiceLine / cac:Price / cbc:BaseQuantity @unitCode

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
		}
	}
	
	public static void writeXml(Invoice invoice, OutputStream out) throws Exception {
		// Create a Marshaller object
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Invoice.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			// Marshal the root element to an XML document
	
			marshaller.marshal(invoice, out);
		} catch (JAXBException e) {
			throw new Exception("Failed to save XML Invoice");
		}
	}
}
