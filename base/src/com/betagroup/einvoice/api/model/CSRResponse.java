package com.betagroup.einvoice.api.model;

import java.util.List;

public class CSRResponse {
	String requestID;
	String dispositionMessage;
	String binarySecurityToken;
	String secret;
	List<MessageModel> errors;
	
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public String getDispositionMessage() {
		return dispositionMessage;
	}
	public void setDispositionMessage(String dispositionMessage) {
		this.dispositionMessage = dispositionMessage;
	}
	public String getBinarySecurityToken() {
		return binarySecurityToken;
	}
	public void setBinarySecurityToken(String binarySecurityToken) {
		this.binarySecurityToken = binarySecurityToken;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	public List<MessageModel> getErrors() {
		return errors;
	}
	public void setErrors(List<MessageModel> errors) {
		this.errors = errors;
	}
	@Override
	public String toString() {
		return "CSRResponse [requestID=" + requestID + ", dispositionMessage=" + dispositionMessage
				+ ", binarySecurityToken=" + binarySecurityToken + ", secret=" + secret + ", errors=" + errors + "]";
	}

	
	
}
