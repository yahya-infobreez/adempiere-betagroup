package com.betagroup.einvoice;

import java.util.Base64;
import org.compiere.model.MSysConfig;
import org.compiere.util.KeyNamePair;
import com.betagroup.einvoice.api.model.CSRRequest;
import com.betagroup.einvoice.api.model.CSRResponse;
import com.betagroup.einvoice.api.model.ClearedInvoiceResult;
import com.betagroup.einvoice.api.model.InvoiceRequest;
import com.betagroup.einvoice.api.model.InvoiceResult;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ZatcaApiHelper extends GenericApi {
	/*
	E-Invoicing API endpoints

	A. FATOORA portal (production)
	
	● Onboarding APIs:
	https://gw-fatoora.zatca.gov.sa/e-invoicing/core/compliance (Request a pre-compliance CSID)
	https://gw-fatoora.zatca.gov.sa/e-invoicing/core/compliance/invoices (Run compliance checks for reporting and clearance)
	https://gw-fatoora.zatca.gov.sa/e-invoicing/core/production/csids (Request or renew a production CSID)
	
	● Core Solution APIs:
	https://gw-fatoora.zatca.gov.sa/e-invoicing/core/invoices/reporting/single (Reporting API for Invoices, Credit and Debit notes)
	https://gw-fatoora.zatca.gov.sa/e-invoicing/core/invoices/clearance/single (Clearance API for Invoices, Credit and Debit notes)
	
	B. FATOORA Simulation portal
	● Onboarding APIs:
	https://gw-fatoora.zatca.gov.sa/e-invoicing/simulation/compliance (Request a pre-compliance CSID)
	https://gw-fatoora.zatca.gov.sa/e-invoicing/simulation/compliance/invoices (Run compliance checks for reporting and clearance)
	https://gw-fatoora.zatca.gov.sa/e-invoicing/simulation/production/csids (Request or renew a production CSID)
	
	● Core Solution APIs:
	https://gw-fatoora.zatca.gov.sa/e-invoicing/simulation/invoices/reporting/single (Reporting API for Invoices, Credit and Debit notes)
	https://gw-fatoora.zatca.gov.sa/e-invoicing/simulation/invoices/clearance/single (Clearance API for Invoices, Credit and Debit notes)
	
	● Note, since FATOORA Simulation portal is an independent environment when generating the CSR value, on the CSR Configuration use below values:
	
	● For FATOORA Portal
	a. Openssl: certificateTemplateName = ASN1:PRINTABLESTRING:ZATCA-Code-Signing
	b. Fatoora command: default value
	
	● For FATOORA Simulation Portal
	a. Use openssl with certificateTemplateName = ASN1:PRINTABLESTRING:PREZATCA- Code-Signing
	 */
	public static String FATOORA_API_SIMULATION_URL = "https://gw-fatoora.zatca.gov.sa/e-invoicing/simulation";
	public static String FATOORA_API_DEVELOPER_PORTAL_URL = "https://gw-fatoora.zatca.gov.sa/e-invoicing/developer-portal";
	public static String FATOORA_API_PRODUCTION_URL = "https://gw-fatoora.zatca.gov.sa/e-invoicing/core";

	/** Possible values are 
	 * <li>sim</li>
	 * <li>dev</li>
	 * <li>prod</li>
	 */
	public static String FATOORA_API_TARGET= "FATOORA_API_TARGET";
	
	/**
	 * Issues an X509 Compliance Cryptographic Stamp Identifier (CCSID/Certificate) (CSID) based on submitted CSR.(complianceCertificate)
		This is a compliance CSID (CCSID) that is issued by the einvoicing system as it is a prerequisite to complete the compliance steps. 
		The CCSID is sent in the authentication certificate header in the compliance api calls.
	 */
	public static String API_ComplianceCSIDCertificate = "/compliance";
	
	/**
	 * It performs compliance checks on einvoice documents such as:
			Standard invoice.
			Standard debit note.
			Standard credit note.
			Simplified Invoice.
			Simplified credit note.
			Simplified debit note
	 * @body InvoiceRequest
	 */
	public static String API_ComplianceInvoice = "/compliance/invoices";
	
	/** 
	 * Clears a single Standard invoice, credit note, or debit note. 
	 * Specifically, it accepts standard invoice, credit note, or debit note encoded in base64 and validates it to ensure the below.
	 * On successful validation, the API then signs the invoice, applies a QR code and returns back.
	 */
	public static String API_ClearanceModelEndpointS = "/invoices/clearance/single";
	
	/** 
	 * Issues an X509 Production Cryptographic Stamp Identifier (PCSID/Certificate) (CSID) based on submitted CSR
	 * returns a Base64 encoded X509 certificate
	 * 
	 * This end point is also used for renewal. Renews an X509 Certificate (CSID) based on submitted CSR. (renewCertificatesUsingPOST)
	 * Body: CSRRequest
	 */
	public static String API_CryptographicStampIdentifierCertificateEndpointS = "/production/csids";
	
	/**
	 * Report simplified Invoice
	 * @body InvoiceRequest2
	 */
	public static String API_ReportingModelEndpointS = "/invoices/reporting/single";
	
	private String baseUrl;
	
	
	public ZatcaApiHelper() {
		// Get the target portal from Sysconfig
		String apiTarget = MSysConfig.getValue(FATOORA_API_TARGET, "dev");
		switch(apiTarget) {
		case "sim":
			baseUrl = FATOORA_API_SIMULATION_URL;
			break;
		case "dev":
			baseUrl = FATOORA_API_DEVELOPER_PORTAL_URL;
			break;
		case "prod":
			default:
			baseUrl = FATOORA_API_PRODUCTION_URL;
			break;			
		}
	}
	
	
	/**
	 * Invoke Register CSR API
	 * Requires userName, password to be set. @see {@link #setUsername(String)}, {@link #setPasswd(String)}
	 * @param csr 
	 * @return
	 * @throws Exception
	 */
	public CSRResponse requestCCSID(String csr, String otp) throws Exception {
		CSRRequest data = new CSRRequest();
		data.setCsr(csr);
		String url = baseUrl+API_ComplianceCSIDCertificate;
        addHeader("OTP", otp);
		
		KeyNamePair result = invokePostApi(url, data);
		
		System.out.println("Result: " + result);
		// 428 = Renewed. Need to redo compliance
		if(result.getKey() == 200 || result.getKey() == 202) {
			String resultData = result.getName();
			if(resultData != null && resultData.startsWith("{") && resultData.endsWith("}")) { // Is Json
		        ObjectMapper objectMapper = new ObjectMapper();
		        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				CSRResponse response = objectMapper.readValue(resultData, CSRResponse.class);		
				return response;
			} else {
				throw new Exception("CSR Request failed. " + result);
			}
		} else {
			throw new Exception("CSR Request failed. " + result);
		}
	}
	
	public CSRResponse requestPCSID(String complianceRequestID) throws Exception {
		CSRRequest data = new CSRRequest();
		data.setCompliance_request_id(complianceRequestID);
		String url = baseUrl+API_CryptographicStampIdentifierCertificateEndpointS;
		// Add additional headers to the request
		addHeader("CurrentCCSID", userName); // The token is already set as user		
		
		KeyNamePair result = invokePostApi(url, data);
		
		System.out.println("Result: " + result);
		// 428 = Renewed. Need to redo compliance
		if(result.getKey() == 200 || result.getKey() == 202 || result.getKey() == 428) {
			String resultData = result.getName();
			if(resultData != null && resultData.startsWith("{") && resultData.endsWith("}")) { // Is Json
		        ObjectMapper objectMapper = new ObjectMapper();
		        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				CSRResponse response = objectMapper.readValue(resultData, CSRResponse.class);		
				return response;
			} else {
				throw new Exception("CSR Request failed. " + result);
			}
		} else {
			throw new Exception("CSR Request failed. " + result);
		}
	}
	
	public CSRResponse renewPCSID(String csr, String otp) throws Exception {
		CSRRequest data = new CSRRequest();
		data.setCsr(csr);
		String url = baseUrl+API_CryptographicStampIdentifierCertificateEndpointS;
		// Add additional headers to the request
		addHeader("CurrentCCSID", userName); // The token is already set as user		
        addHeader("OTP", otp);
		
		KeyNamePair result = invokePatchApi(url, data);
		
		System.out.println("Result: " + result);
		// 428 = Renewed. Need to redo compliance
		if(result.getKey() == 200 || result.getKey() == 202 || result.getKey() == 428) {
			String resultData = result.getName();
			if(resultData != null && resultData.startsWith("{") && resultData.endsWith("}")) { // Is Json
		        ObjectMapper objectMapper = new ObjectMapper();
		        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				CSRResponse response = objectMapper.readValue(resultData, CSRResponse.class);		
				return response;
			} else {
				throw new Exception("CSR Renewal Request failed. " + result);
			}
		} else {
			throw new Exception("CSR Renewal Request failed. " + result);
		}
	}
	
	public InvoiceResult checkInvoiceCompliance(String hash, String uuid, byte[] invoiceData) throws Exception {
		InvoiceRequest invoiceRequest = new InvoiceRequest();
		invoiceRequest.setInvoiceHash(hash);
		invoiceRequest.setUuid(uuid);
		invoiceRequest.setInvoice(Base64.getEncoder().encodeToString(invoiceData));

		KeyNamePair result = invokePostApi(baseUrl+API_ComplianceInvoice, invoiceRequest);
		
		System.out.println("Result: " + result);
		if(result.getKey() == 200 || result.getKey() == 202) {
			String resultData = result.getName();
			if(resultData != null && resultData.startsWith("{") && resultData.endsWith("}")) { // Is Json
		        ObjectMapper objectMapper = new ObjectMapper();
		        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		        InvoiceResult response = objectMapper.readValue(resultData, InvoiceResult.class);		
				return response;
			} else {
				throw new Exception("Invoice Compliance check  failed. " + result);
			}
		} else {
			throw new Exception("Invoice Compliance check failed. " + result);
		}
	}
	
	public ClearedInvoiceResult submitInvoiceForClearance(String hash, String uuid, byte[] invoiceData) throws Exception {
//		InvoiceRequest2 request = new InvoiceRequest2();
		InvoiceRequest invoiceRequest = new InvoiceRequest();
		invoiceRequest.setInvoiceHash(hash);
		invoiceRequest.setUuid(uuid);
		invoiceRequest.setInvoice(Base64.getEncoder().encodeToString(invoiceData));
//		request.setValue(invoiceRequest);
//		request.setSummary("Standard Invoice"); //"Standard Invoice", "Simplified Invoice"
		
		addHeader("Clearance-Status", "1"); // 0 = Disabled, 1= Enabled

		KeyNamePair result = invokePostApi(baseUrl+API_ClearanceModelEndpointS, invoiceRequest); //request);
		
		System.out.println("Result: " + result);
		if(result.getKey() == 200 || result.getKey() == 202) {
			String resultData = result.getName();
			if(resultData != null && resultData.startsWith("{") && resultData.endsWith("}")) { // Is Json
		        ObjectMapper objectMapper = new ObjectMapper();
		        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		        ClearedInvoiceResult response = objectMapper.readValue(resultData, ClearedInvoiceResult.class);		
				return response;
			} else {
				throw new Exception("Invoice clearance failed. " + result);
			}
		} else {
			throw new Exception("Invoice clearance failed. " + result);
		}
	}

	
	public InvoiceResult submitInvoiceForReporting(String hash, String uuid, byte[] invoiceData) throws Exception {
//		InvoiceRequest2 request = new InvoiceRequest2();
		InvoiceRequest invoiceRequest = new InvoiceRequest();
		invoiceRequest.setInvoiceHash(hash);
		invoiceRequest.setUuid(uuid);
		invoiceRequest.setInvoice(Base64.getEncoder().encodeToString(invoiceData));
//		request.setValue(invoiceRequest);
//		request.setSummary("Simplified Invoice"); //"Standard Invoice", "Simplified Invoice"
		addHeader("Clearance-Status", "0"); // 0 = Disabled, 1= Enabled
		KeyNamePair result = invokePostApi(baseUrl+API_ReportingModelEndpointS, invoiceRequest); //request);
		
		System.out.println("Result: " + result);
		if(result.getKey() == 200 || result.getKey() == 202) {
			String resultData = result.getName();
			if(resultData != null && resultData.startsWith("{") && resultData.endsWith("}")) { // Is Json
		        ObjectMapper objectMapper = new ObjectMapper();
		        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		        InvoiceResult response = objectMapper.readValue(resultData, InvoiceResult.class);		
				return response;
			} else {
				throw new Exception("Invoice reporting failed. " + result);
			}
		} else {
			throw new Exception("Invoice reporting failed. " + result);
		}
	}
}
