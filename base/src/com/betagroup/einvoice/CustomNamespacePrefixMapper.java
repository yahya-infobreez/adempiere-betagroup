package com.betagroup.einvoice;

import java.util.Arrays;
import java.util.stream.Collectors;

import javax.xml.namespace.NamespaceContext;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import com.sun.xml.bind.v2.runtime.output.NamespaceContextImpl;

public class CustomNamespacePrefixMapper extends NamespacePrefixMapper {

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        return new String[] {
        		"urn:oasis:names:specification:ubl:schema:xsd:Invoice-2", 
    			"urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2",
    			"urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2",
    			"urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2",
    			"urn:oasis:names:specification:ubl:schema:xsd:SignatureAggregateComponents-2",
    			"urn:oasis:names:specification:ubl:schema:xsd:SignatureBasicComponents-2",
    			"urn:oasis:names:specification:ubl:schema:xsd:CommonSignatureComponents-2" 
        	}; // Your namespace URI
    }
    
    @Override
    public String[] getPreDeclaredNamespaceUris2() {
    	return new String[] {
//    			"ext", "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2",
//    			"cac", "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2",
//    			"cbc", "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2",
//    			"sac", "urn:oasis:names:specification:ubl:schema:xsd:SignatureAggregateComponents-2",
//    			"sbc", "urn:oasis:names:specification:ubl:schema:xsd:SignatureBasicComponents-2",
//    			"sig", "urn:oasis:names:specification:ubl:schema:xsd:CommonSignatureComponents-2", 
    		};
    }
    
    @Override
    public String[] getContextualNamespaceDecls() {
    	return new String[] {};

    }
    
	@Override
	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        if ("urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2".equals(namespaceUri)) {
            return "ext"; 
        } else if("urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2".equals(namespaceUri)) {
        	return "cac"; 
        } else if("urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2".equals(namespaceUri)) {
        	return "cbc"; 
        } else if("urn:oasis:names:specification:ubl:schema:xsd:SignatureAggregateComponents-2".equals(namespaceUri)) {
        	return "sac"; 
        } else if("urn:oasis:names:specification:ubl:schema:xsd:SignatureBasicComponents-2".equals(namespaceUri)) {
        	return "sbc"; 
        } else if("urn:oasis:names:specification:ubl:schema:xsd:CommonSignatureComponents-2".equals(namespaceUri)) {
        	return "sig"; 
        } else if("http://www.w3.org/2000/09/xmldsig#".equals(namespaceUri)) {
        	return "ds";
        } else if("http://uri.etsi.org/01903/v1.3.2#".equals(namespaceUri)) {
        	return "xades";
        } else if("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2".equals(namespaceUri)) {
        	return "";
//        } else {
//        	return "";
        }

        return suggestion; // Default suggestion if not matched
	}

	public String getPackages() {
		String[] packages = new String[] {
				"oasis.names.specification.ubl.schema.xsd.invoice_2",
        		"oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2",
        		"oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2",
        		"oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2",
        		"oasis.names.specification.ubl.schema.xsd.signatureaggregatecomponents_2",
        		"oasis.names.specification.ubl.schema.xsd.signaturebasiccomponents_2",
        		"oasis.names.specification.ubl.schema.xsd.commonsignaturecomponents_2",
        		"org.w3._2000._09.xmldsig_",
        		"org.etsi.uri._01903.v1_3"
        		};
        		
        return Arrays.asList(packages).stream().collect(Collectors.joining(":"));
	}

}
