package com.betagroup.einvoice.api.model;

import java.util.List;

/**
 * 1- Create private key

openssl ecparam -name secp256k1 -genkey -noout -out ec-secp256k1-priv-key.pem

2- Create public key

openssl ec -in ec-secp256k1-priv-key.pem -pubout > ec-secp256k1-pub-key.pem

3- Create CSR

openssl req -new -sha256 -key ec-secp256k1-priv-key.pem -extensions v3_req -config config.cnf -out my.csr


Alternatively
# Create config file, in the following path, by copying template
# create Private key and CSR using following command
fatoora -csr -csrConfig /home/yahya/ZATCA/zatca-einvoicing-sdk-238-R3.3.6/Data/Input/csr-config-betagroup.properties -pem -

# Extract the Public Key: Use the following command to extract the public key from the ECDSA private key:
openssl ec -in private_key.pem -pubout -out public_key.pem

# Create Self-signed certificate
openssl x509 -req -days 365 -in generated-csr-20241107064928.csr -signkey generated-private-key-20241107064928.key -out certificate.pem

# Decode certificate
openssl x509 -in certificate.pem -text -noout

 * @author yahya
 *
 */
public class CSRRequest {
	String csr;
	String compliance_request_id;

	public String getCsr() {
		return csr;
	}

	public void setCsr(String csr) {
		this.csr = csr;
	}

	public String getCompliance_request_id() {
		return compliance_request_id;
	}

	public void setCompliance_request_id(String compliance_request_id) {
		this.compliance_request_id = compliance_request_id;
	}

	@Override
	public String toString() {
		return "CSRRequest [csr=" + csr + ", compliance_request_id=" + compliance_request_id + "]";
	}
	
	
}
