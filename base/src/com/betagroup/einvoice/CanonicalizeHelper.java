package com.betagroup.einvoice;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

import oasis.names.specification.ubl.schema.xsd.invoice_2.Invoice;

public class CanonicalizeHelper {
    
    public static byte[] canonicalize(Document document) throws Exception {
        // Create a Transformer to convert the Document to a canonical form
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");

        // Transform the Document to a byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        transformer.transform(new DOMSource(document), new StreamResult(outputStream));
        return outputStream.toByteArray();
    }
    

}
