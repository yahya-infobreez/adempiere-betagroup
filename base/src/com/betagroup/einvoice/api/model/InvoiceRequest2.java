package com.betagroup.einvoice.api.model;

public class InvoiceRequest2 {
	InvoiceRequest value;
	String summary; // Values: "Standard Invoice", "Simplified Invoice"

	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public InvoiceRequest getValue() {
		return value;
	}
	public void setValue(InvoiceRequest value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "InvoiceRequest2 [value=" + value + ", summary=" + summary + "]";
	}
	
}
