package com.betagroup.einvoice.api.model;

import java.util.List;

public class ValidationResults {
	List<MessageModel> infoMessages;
	List<MessageModel> warningMessages;
	List<MessageModel> errorMessages;

	String reportingStatus;	//REPORTED, "NOT_REPORTED"
	String clearanceStatus; //
	String qrSellertStatus;
	String qrBuyertStatus;
	
	public List<MessageModel> getInfoMessages() {
		return infoMessages;
	}
	public void setInfoMessages(List<MessageModel> infoMessages) {
		this.infoMessages = infoMessages;
	}
	public List<MessageModel> getWarningMessages() {
		return warningMessages;
	}
	public void setWarningMessages(List<MessageModel> warningMessages) {
		this.warningMessages = warningMessages;
	}
	public List<MessageModel> getErrorMessages() {
		return errorMessages;
	}
	public void setErrorMessages(List<MessageModel> errorMessages) {
		this.errorMessages = errorMessages;
	}
	@Override
	public String toString() {
		return "ValidationResults [infoMessages=" + infoMessages + ", warningMessages=" + warningMessages
				+ ", errorMessages=" + errorMessages + ", reportingStatus=" + reportingStatus + ", clearanceStatus="
				+ clearanceStatus + ", qrSellertStatus=" + qrSellertStatus + ", qrBuyertStatus=" + qrBuyertStatus + "]";
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
