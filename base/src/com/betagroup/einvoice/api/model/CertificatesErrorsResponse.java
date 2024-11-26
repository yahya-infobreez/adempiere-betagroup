package com.betagroup.einvoice.api.model;

import java.util.List;

public class CertificatesErrorsResponse {
	List<MessageModel> errors;

	public List<MessageModel> getErrors() {
		return errors;
	}

	public void setErrors(List<MessageModel> errors) {
		this.errors = errors;
	}

	@Override
	public String toString() {
		return "CertificatesErrorsResponse [errors=" + errors + "]";
	}
	
}
