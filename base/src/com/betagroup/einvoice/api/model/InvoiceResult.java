package com.betagroup.einvoice.api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;

public class InvoiceResult {
	String invoiceHash;
	@JsonAlias({"reportingStatus", "clearanceStatus"})
	String status;
	List<MessageModel> warnings;
	List<MessageModel> errors;
	ValidationResults validationResults;
	
	public String getInvoiceHash() {
		return invoiceHash;
	}
	public void setInvoiceHash(String invoiceHash) {
		this.invoiceHash = invoiceHash;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<MessageModel> getWarnings() {
		return warnings;
	}
	public void setWarnings(List<MessageModel> warnings) {
		this.warnings = warnings;
	}
	public List<MessageModel> getErrors() {
		return errors;
	}
	public void setErrors(List<MessageModel> errors) {
		this.errors = errors;
	}
	
	public ValidationResults getValidationResults() {
		return validationResults;
	}
	public void setValidationResults(ValidationResults validationResults) {
		this.validationResults = validationResults;
	}
	@Override
	public String toString() {
		return "InvoiceResult [invoiceHash=" + invoiceHash + ", status=" + status + ", warnings=" + warnings
				+ ", errors=" + errors + ", validationResults=" + validationResults + "]";
	}
	
	
	
	/* TODO Also shows another model
	 
	 {"validationResults":{"infoMessages":[{"type":"INFO","code":"XSD_ZATCA_VALID","category":"XSD
validation","message":"Complied with UBL 2.1 standards in line with ZATCA
specifications","status":"PASS"}],"warningMessages":[],"errorMessages":[{"type":"ERROR","code":"BR-KSA-37","category":"
KSA","message":"The seller address building number must contain 4 digits.","status":"ERROR"},{"type":"ERROR","code":"B
R-KSA-09","category":"KSA","message":"Seller address must contain additional number (KSA-23), street name (BT-35), buil
ding number (KSA-17), postal code (BT-38), city (BT-37), Neighborhood (KSA-3), country code (BT-40).For more
information please access this link: https://www.address.gov.sa/en/address-format/overview","status":"ERROR"}],"status":"E
RROR"},"reportingStatus":"NOT_REPORTED","clearanceStatus":null,"qrSellertStatus":null,"qrBuyertStatus":null}

	 * 
	 */
}
