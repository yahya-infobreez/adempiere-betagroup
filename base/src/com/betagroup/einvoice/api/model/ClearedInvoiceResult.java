package com.betagroup.einvoice.api.model;


public class ClearedInvoiceResult extends InvoiceResult {
	String clearedInvoice;

	public String getClearedInvoice() {
		return clearedInvoice;
	}

	public void setClearedInvoice(String clearedInvoice) {
		this.clearedInvoice = clearedInvoice;
	}

	@Override
	public String toString() {
		return "ClearedInvoiceResult [clearedInvoice=" + clearedInvoice + "]";
	}
	
}
