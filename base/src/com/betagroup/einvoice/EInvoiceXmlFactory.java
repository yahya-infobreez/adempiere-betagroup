package com.betagroup.einvoice;

import java.io.File;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.compiere.model.MInvoice;

import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ID;
import oasis.names.specification.ubl.schema.xsd.invoice_2.Invoice;

public class EInvoiceXmlFactory {

	public static Invoice createXmlInvoice(MInvoice minvoice) {
		Invoice invoice = new Invoice();
		ID id = new ID();
		id.setValue("Test ID");
		invoice.setID(id);
		
		return invoice;
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
