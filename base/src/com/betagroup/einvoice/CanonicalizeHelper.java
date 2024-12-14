package com.betagroup.einvoice;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.xml.security.Init;
import org.apache.xml.security.c14n.Canonicalizer;
import org.w3c.dom.Document;

public class CanonicalizeHelper {
    
    public static byte[] canonicalize(Document document) throws Exception {
        // Create a Transformer to convert the Document to a canonical form
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty("encoding", "UTF-8");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "no");
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");

        // Transform the Document to a byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        transformer.transform(new DOMSource(document), new StreamResult(outputStream));
        return outputStream.toByteArray();
        
    }
    
    /**
     * Returns canonicalized XML
     * @param xmlDocument  XML data after applying transform using XSLT. 
     * @return
     * @throws Exception
     */
    public static byte[] canonicalize(byte[] xmlDocument) throws Exception {
        Init.init();
        Canonicalizer canon = Canonicalizer.getInstance("http://www.w3.org/2006/12/xml-c14n11");
        byte[] canonOut = canon.canonicalize(xmlDocument);
        return canonOut;
    }
    
    public static byte[] transform(byte[] xmlDocument, String xsltPath) throws Exception {
    	try(ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();) {
	        Transformer transformer = getTransformer(xsltPath, false);
	        StreamResult xmlOutput = new StreamResult(byteArrayOutputStream);
	        ByteArrayInputStream xmlInputStream = new ByteArrayInputStream(xmlDocument);
	        transformer.transform(new StreamSource(xmlInputStream), xmlOutput);
	
	        return byteArrayOutputStream.toByteArray();
    	}
    }

	static TransformerFactory transformerFactory = TransformerFactory.newInstance();
    private static Transformer getTransformer(String xsltPath, boolean indent) throws TransformerConfigurationException, IOException {
        transformerFactory.setAttribute("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
        transformerFactory.setAttribute("http://javax.xml.XMLConstants/property/accessExternalStylesheet", "");
        try (InputStream inputStream = CanonicalizeHelper.class.getResourceAsStream(xsltPath);){
            Transformer transformer = transformerFactory.newTransformer(new StreamSource(inputStream));
            transformer.setOutputProperty("encoding", "UTF-8");
            transformer.setOutputProperty("indent", indent?"yes":"no");
            transformer.setOutputProperty("omit-xml-declaration", "yes");
            return transformer;
        }
    }
}
