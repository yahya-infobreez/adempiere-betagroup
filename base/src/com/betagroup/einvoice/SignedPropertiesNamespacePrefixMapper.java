package com.betagroup.einvoice;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

public class SignedPropertiesNamespacePrefixMapper extends NamespacePrefixMapper {
	
	

    @Override

    public String[] getPreDeclaredNamespaceUris() {
        return new String[] {
        		"http://uri.etsi.org/01903/v1.3.2#",
        		"urn:oasis:names:specification:ubl:schema:xsd:Invoice-2" 
        	}; // Your namespace URI
    }
    
	@Override
	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
		if("http://uri.etsi.org/01903/v1.3.2#".equals(namespaceUri)) {
        	return "xades";
		} else if("http://www.w3.org/2000/09/xmldsig#".equals(namespaceUri)) {
            	return "ds";
        } else if("urn:oasis:names:specification:ubl:schema:xsd:Invoice-2".equals(namespaceUri)) {
        	return "";
        }

        return suggestion; // Default suggestion if not matched
	}

	public String getPackages() {
		String[] packages = new String[] {
        		"org.w3._2000._09.xmldsig_",
        		"org.etsi.uri._01903.v1_3"
        		};
        		
        return Arrays.asList(packages).stream().collect(Collectors.joining(":"));
	}
}
