package test.functional.einvoice;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

import org.compiere.model.MInvoice;
import org.compiere.util.Env;
import com.betagroup.einvoice.CanonicalizeHelper;
import com.betagroup.einvoice.EInvoiceXmlFactory;
import com.betagroup.einvoice.ZatcaApiHelper;
import com.betagroup.einvoice.api.model.CSRResponse;
import com.betagroup.einvoice.api.model.InvoiceResult;

import oasis.names.specification.ubl.schema.xsd.invoice_2.Invoice;
import test.AdempiereTestCase;

public class EInvoiceAPITest extends AdempiereTestCase {
	@Override
	protected void setUp() throws Exception {
		testPropertiesFileName = "beta-test.properties";
		super.setUp();
	}
	
	public void testCSRRequest() {
		try {
			String userName = "TUlJQ1BUQ0NBZU9nQXdJQkFnSUdBWXp6Z0VoTk1Bb0dDQ3FHU000OUJBTUNNQlV4RXpBUkJnTlZCQU1NQ21WSmJuWnZhV05wYm1jd0hoY05NalF3TVRFd01UTXhNVFUwV2hjTk1qa3dNVEE1TWpFd01EQXdXakIxTVFzd0NRWURWUVFHRXdKVFFURVdNQlFHQTFVRUN3d05VbWw1WVdSb0lFSnlZVzVqYURFbU1DUUdBMVVFQ2d3ZFRXRjRhVzExYlNCVGNHVmxaQ0JVWldOb0lGTjFjSEJzZVNCTVZFUXhKakFrQmdOVkJBTU1IVlJUVkMwNE9EWTBNekV4TkRVdE16azVPVGs1T1RrNU9UQXdNREF6TUZZd0VBWUhLb1pJemowQ0FRWUZLNEVFQUFvRFFnQUVvV0NLYTBTYTlGSUVyVE92MHVBa0MxVklLWHhVOW5QcHgydmxmNHloTWVqeThjMDJYSmJsRHE3dFB5ZG84bXEwYWhPTW1Obzhnd25pN1h0MUtUOVVlS09Cd1RDQnZqQU1CZ05WSFJNQkFmOEVBakFBTUlHdEJnTlZIUkVFZ2FVd2dhS2tnWjh3Z1p3eE96QTVCZ05WQkFRTU1qRXRWRk5VZkRJdFZGTlVmRE10WldReU1tWXhaRGd0WlRaaE1pMHhNVEU0TFRsaU5UZ3RaRGxoT0dZeE1XVTBORFZtTVI4d0hRWUtDWkltaVpQeUxHUUJBUXdQTXprNU9UazVPVGs1T1RBd01EQXpNUTB3Q3dZRFZRUU1EQVF4TVRBd01SRXdEd1lEVlFRYURBaFNVbEpFTWpreU9URWFNQmdHQTFVRUR3d1JVM1Z3Y0d4NUlHRmpkR2wyYVhScFpYTXdDZ1lJS29aSXpqMEVBd0lEU0FBd1JRSWhBSUY4akljeHp2Q3lxVURUcDVPbXY3MlVweFBBTG1vUnl0OURZMjRqV21CUUFpQTBiYVo2WXJwcDV5SjRhaG9vb1czK09hOGtrYjMxZXZBb0hkdmdEODA2M3c9PQ==";
			String password = "PKoGsSwpPx236yNS7CWDojV4doe1i0W+5mPodbMEW5k=";
			String csr = "LS0tLS1CRUdJTiBDRVJUSUZJQ0FURSBSRVFVRVNULS0tLS0KTUlJQ0REQ0NBYklDQVFBd1p6RUxNQWtHQTFVRUJoTUNVMEV4RmpBVUJnTlZCQXNNRFZKcGVXRmthQ0JDY21GdQpZMmd4R0RBV0JnTlZCQW9NRDBKRlZFRWdTVTVFVlZOVVVrbEZVekVtTUNRR0ExVUVBd3dkUVVSRlRWQkpSVkpGCkxURXdNUzB6TVRJek5EVTJOemc1TURBd01ETXdWakFRQmdjcWhrak9QUUlCQmdVcmdRUUFDZ05DQUFSbDYzQ0sKTlhJbFlZaW5rVVBYdmV5QTFNbHpCNHdSTDcrSlVvNVhlS3ZsOEVGdU9jQ1JsK3dSazhabjZsdm01VThDbFoyNQpoVFRtKzB5SVRPSkxjVndNb0lIck1JSG9CZ2txaGtpRzl3MEJDUTR4Z2Rvd2dkY3dKQVlKS3dZQkJBR0NOeFFDCkJCY01GVkJTUlZwQlZFTkJMVU52WkdVdFUybG5ibWx1WnpDQnJnWURWUjBSQklHbU1JR2pwSUdnTUlHZE1VSXcKUUFZRFZRUUVERGt4TFVGRVJVMVFTVVZTUlh3eUxUSXdNalI4TXkxbFpESXlaakZrT0MxbE5tRXlMVEV4TVRndApPV0kxT0Mxa09XRTRaakV4WlRRME5XWXhIekFkQmdvSmtpYUprL0lzWkFFQkRBOHpNVEl6TkRVMk56ZzVNREF3Ck1ETXhEVEFMQmdOVkJBd01CREV4TURBeER6QU5CZ05WQkJvTUJsSkpXVUZFU0RFV01CUUdBMVVFRHd3TlRVRk8KVlVaQlExUlZVa2xPUnpBS0JnZ3Foa2pPUFFRREFnTklBREJGQWlBQ1p5YXlCQkYva3dSbm1IbnFoRTJPTEZLSAp5YmFTRXpwL21RYUVyK1c5akFJaEFOMnZoRzRLSVIyN2xqYWpRZHN5Um44UVZHRFdkS2h0UlFxN043WERFc0lFCi0tLS0tRU5EIENFUlRJRklDQVRFIFJFUVVFU1QtLS0tLQo=";
			
			ZatcaApiHelper apiHelper = new ZatcaApiHelper();
			apiHelper.setUsername(userName);
			apiHelper.setPasswd(password);
			
			CSRResponse response = apiHelper.registerCSR(csr);
			System.out.print(response);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	
	public void testComplianceB2BInvoice() {
		String key = "TUlJQ01EQ0NBZGFnQXdJQkFnSUdBWk0vNDBZTU1Bb0dDQ3FHU000OUJBTUNNQlV4RXpBUkJnTlZCQU1NQ21WSmJuWnZhV05wYm1jd0hoY05NalF4TVRFNE1UVXlOelUwV2hjTk1qa3hNVEUzTWpFd01EQXdXakJuTVFzd0NRWURWUVFHRXdKVFFURVdNQlFHQTFVRUN3d05VbWw1WVdSb0lFSnlZVzVqYURFWU1CWUdBMVVFQ2d3UFFrVlVRU0JKVGtSVlUxUlNTVVZUTVNZd0pBWURWUVFEREIxQlJFVk5VRWxGVWtVdE1UQXhMVE14TWpNME5UWTNPRGt3TURBd016QldNQkFHQnlxR1NNNDlBZ0VHQlN1QkJBQUtBMElBQkdYcmNJbzFjaVZoaUtlUlE5ZTk3SURVeVhNSGpCRXZ2NGxTamxkNHErWHdRVzQ1d0pHWDdCR1R4bWZxVytibFR3S1ZuYm1GTk9iN1RJaE00a3R4WEF5amdjSXdnYjh3REFZRFZSMFRBUUgvQkFJd0FEQ0JyZ1lEVlIwUkJJR21NSUdqcElHZ01JR2RNVUl3UUFZRFZRUUVERGt4TFVGRVJVMVFTVVZTUlh3eUxUSXdNalI4TXkxbFpESXlaakZrT0MxbE5tRXlMVEV4TVRndE9XSTFPQzFrT1dFNFpqRXhaVFEwTldZeEh6QWRCZ29Ka2lhSmsvSXNaQUVCREE4ek1USXpORFUyTnpnNU1EQXdNRE14RFRBTEJnTlZCQXdNQkRFeE1EQXhEekFOQmdOVkJCb01CbEpKV1VGRVNERVdNQlFHQTFVRUR3d05UVUZPVlVaQlExUlZVa2xPUnpBS0JnZ3Foa2pPUFFRREFnTklBREJGQWlFQTIwV2dIYVMwZEZXd1Z3OU5pYnZjL3FsL0tqcDVvSDk0UGhDekprTXlQWXNDSUVxb1BzUXFzTk1oTTRRQ2dIUnNBblJCQXN6MW11VnVaQTh1MFg5cEJucjg=";
		String secret = "E3PHhMok4j9q5XWbI4D2wwraLX1crM6nrRRfNdJ+694=";
		
		ZatcaApiHelper apiHelper = new ZatcaApiHelper();
		apiHelper.setUsername(key);
		apiHelper.setPasswd(secret);
		
		Properties ctx = new Properties();
		Env.setContext(ctx, "AD_Client_ID", 1000000);
		Env.setContext(ctx, "AD_Org_ID", 0);
	
		// B2B Invoice
		try {
			MInvoice minvoice = MInvoice.get(ctx, 1075742); // B2B Invoice
			assertNotNull(minvoice);
			Invoice xmlInvoice = EInvoiceXmlFactory.createInvoiceXml(minvoice);
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			EInvoiceXmlFactory.marshalJaxb(xmlInvoice, os, false);
		
			byte[] invoiceData = CanonicalizeHelper.canonicalize(os.toByteArray(), false);
			InvoiceResult response = apiHelper.checkInvoiceCompliance(minvoice.getInvoiceHash(), minvoice.getUUID(), invoiceData);				
			System.out.print(response);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
		
	public void testComplianceB2BCreditNote() {
		String key = "TUlJQ01EQ0NBZGFnQXdJQkFnSUdBWk0vNDBZTU1Bb0dDQ3FHU000OUJBTUNNQlV4RXpBUkJnTlZCQU1NQ21WSmJuWnZhV05wYm1jd0hoY05NalF4TVRFNE1UVXlOelUwV2hjTk1qa3hNVEUzTWpFd01EQXdXakJuTVFzd0NRWURWUVFHRXdKVFFURVdNQlFHQTFVRUN3d05VbWw1WVdSb0lFSnlZVzVqYURFWU1CWUdBMVVFQ2d3UFFrVlVRU0JKVGtSVlUxUlNTVVZUTVNZd0pBWURWUVFEREIxQlJFVk5VRWxGVWtVdE1UQXhMVE14TWpNME5UWTNPRGt3TURBd016QldNQkFHQnlxR1NNNDlBZ0VHQlN1QkJBQUtBMElBQkdYcmNJbzFjaVZoaUtlUlE5ZTk3SURVeVhNSGpCRXZ2NGxTamxkNHErWHdRVzQ1d0pHWDdCR1R4bWZxVytibFR3S1ZuYm1GTk9iN1RJaE00a3R4WEF5amdjSXdnYjh3REFZRFZSMFRBUUgvQkFJd0FEQ0JyZ1lEVlIwUkJJR21NSUdqcElHZ01JR2RNVUl3UUFZRFZRUUVERGt4TFVGRVJVMVFTVVZTUlh3eUxUSXdNalI4TXkxbFpESXlaakZrT0MxbE5tRXlMVEV4TVRndE9XSTFPQzFrT1dFNFpqRXhaVFEwTldZeEh6QWRCZ29Ka2lhSmsvSXNaQUVCREE4ek1USXpORFUyTnpnNU1EQXdNRE14RFRBTEJnTlZCQXdNQkRFeE1EQXhEekFOQmdOVkJCb01CbEpKV1VGRVNERVdNQlFHQTFVRUR3d05UVUZPVlVaQlExUlZVa2xPUnpBS0JnZ3Foa2pPUFFRREFnTklBREJGQWlFQTIwV2dIYVMwZEZXd1Z3OU5pYnZjL3FsL0tqcDVvSDk0UGhDekprTXlQWXNDSUVxb1BzUXFzTk1oTTRRQ2dIUnNBblJCQXN6MW11VnVaQTh1MFg5cEJucjg=";
		String secret = "E3PHhMok4j9q5XWbI4D2wwraLX1crM6nrRRfNdJ+694=";
		
		ZatcaApiHelper apiHelper = new ZatcaApiHelper();
		apiHelper.setUsername(key);
		apiHelper.setPasswd(secret);
		
		Properties ctx = new Properties();
		Env.setContext(ctx, "AD_Client_ID", 1000000);
		Env.setContext(ctx, "AD_Org_ID", 0);
		
		//		B2B Credit Note with Charge 1075743=24902ARC		
		try {
			MInvoice minvoice = MInvoice.get(ctx, 1075743);
			assertNotNull(minvoice);
			Invoice xmlInvoice = EInvoiceXmlFactory.createInvoiceXml(minvoice);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			EInvoiceXmlFactory.marshalJaxb(xmlInvoice, os, false);
		
			byte[] invoiceData = CanonicalizeHelper.canonicalize(os.toByteArray(), false);
			InvoiceResult response = apiHelper.checkInvoiceCompliance(minvoice.getInvoiceHash(), minvoice.getUUID(), invoiceData);				
			System.out.print(response);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	
	// 1075745 - 24903ARC B2B credit note from RMA
		
	public void testComplianceB2BCreditNoteRMA() {
		String key = "TUlJQ01EQ0NBZGFnQXdJQkFnSUdBWk0vNDBZTU1Bb0dDQ3FHU000OUJBTUNNQlV4RXpBUkJnTlZCQU1NQ21WSmJuWnZhV05wYm1jd0hoY05NalF4TVRFNE1UVXlOelUwV2hjTk1qa3hNVEUzTWpFd01EQXdXakJuTVFzd0NRWURWUVFHRXdKVFFURVdNQlFHQTFVRUN3d05VbWw1WVdSb0lFSnlZVzVqYURFWU1CWUdBMVVFQ2d3UFFrVlVRU0JKVGtSVlUxUlNTVVZUTVNZd0pBWURWUVFEREIxQlJFVk5VRWxGVWtVdE1UQXhMVE14TWpNME5UWTNPRGt3TURBd016QldNQkFHQnlxR1NNNDlBZ0VHQlN1QkJBQUtBMElBQkdYcmNJbzFjaVZoaUtlUlE5ZTk3SURVeVhNSGpCRXZ2NGxTamxkNHErWHdRVzQ1d0pHWDdCR1R4bWZxVytibFR3S1ZuYm1GTk9iN1RJaE00a3R4WEF5amdjSXdnYjh3REFZRFZSMFRBUUgvQkFJd0FEQ0JyZ1lEVlIwUkJJR21NSUdqcElHZ01JR2RNVUl3UUFZRFZRUUVERGt4TFVGRVJVMVFTVVZTUlh3eUxUSXdNalI4TXkxbFpESXlaakZrT0MxbE5tRXlMVEV4TVRndE9XSTFPQzFrT1dFNFpqRXhaVFEwTldZeEh6QWRCZ29Ka2lhSmsvSXNaQUVCREE4ek1USXpORFUyTnpnNU1EQXdNRE14RFRBTEJnTlZCQXdNQkRFeE1EQXhEekFOQmdOVkJCb01CbEpKV1VGRVNERVdNQlFHQTFVRUR3d05UVUZPVlVaQlExUlZVa2xPUnpBS0JnZ3Foa2pPUFFRREFnTklBREJGQWlFQTIwV2dIYVMwZEZXd1Z3OU5pYnZjL3FsL0tqcDVvSDk0UGhDekprTXlQWXNDSUVxb1BzUXFzTk1oTTRRQ2dIUnNBblJCQXN6MW11VnVaQTh1MFg5cEJucjg=";
		String secret = "E3PHhMok4j9q5XWbI4D2wwraLX1crM6nrRRfNdJ+694=";
		
		ZatcaApiHelper apiHelper = new ZatcaApiHelper();
		apiHelper.setUsername(key);
		apiHelper.setPasswd(secret);
		
		Properties ctx = new Properties();
		Env.setContext(ctx, "AD_Client_ID", 1000000);
		Env.setContext(ctx, "AD_Org_ID", 0);
			
		try {
			MInvoice minvoice = MInvoice.get(ctx, 1075745);
			assertNotNull(minvoice);
			Invoice xmlInvoice = EInvoiceXmlFactory.createInvoiceXml(minvoice);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			EInvoiceXmlFactory.marshalJaxb(xmlInvoice, os, false);
		
			byte[] invoiceData = CanonicalizeHelper.canonicalize(os.toByteArray(), false);
			InvoiceResult response = apiHelper.checkInvoiceCompliance(minvoice.getInvoiceHash(), minvoice.getUUID(), invoiceData);				
			System.out.print(response);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}
	
	public void testComplianceB2BDebitNote() {
		String key = "TUlJQ01EQ0NBZGFnQXdJQkFnSUdBWk0vNDBZTU1Bb0dDQ3FHU000OUJBTUNNQlV4RXpBUkJnTlZCQU1NQ21WSmJuWnZhV05wYm1jd0hoY05NalF4TVRFNE1UVXlOelUwV2hjTk1qa3hNVEUzTWpFd01EQXdXakJuTVFzd0NRWURWUVFHRXdKVFFURVdNQlFHQTFVRUN3d05VbWw1WVdSb0lFSnlZVzVqYURFWU1CWUdBMVVFQ2d3UFFrVlVRU0JKVGtSVlUxUlNTVVZUTVNZd0pBWURWUVFEREIxQlJFVk5VRWxGVWtVdE1UQXhMVE14TWpNME5UWTNPRGt3TURBd016QldNQkFHQnlxR1NNNDlBZ0VHQlN1QkJBQUtBMElBQkdYcmNJbzFjaVZoaUtlUlE5ZTk3SURVeVhNSGpCRXZ2NGxTamxkNHErWHdRVzQ1d0pHWDdCR1R4bWZxVytibFR3S1ZuYm1GTk9iN1RJaE00a3R4WEF5amdjSXdnYjh3REFZRFZSMFRBUUgvQkFJd0FEQ0JyZ1lEVlIwUkJJR21NSUdqcElHZ01JR2RNVUl3UUFZRFZRUUVERGt4TFVGRVJVMVFTVVZTUlh3eUxUSXdNalI4TXkxbFpESXlaakZrT0MxbE5tRXlMVEV4TVRndE9XSTFPQzFrT1dFNFpqRXhaVFEwTldZeEh6QWRCZ29Ka2lhSmsvSXNaQUVCREE4ek1USXpORFUyTnpnNU1EQXdNRE14RFRBTEJnTlZCQXdNQkRFeE1EQXhEekFOQmdOVkJCb01CbEpKV1VGRVNERVdNQlFHQTFVRUR3d05UVUZPVlVaQlExUlZVa2xPUnpBS0JnZ3Foa2pPUFFRREFnTklBREJGQWlFQTIwV2dIYVMwZEZXd1Z3OU5pYnZjL3FsL0tqcDVvSDk0UGhDekprTXlQWXNDSUVxb1BzUXFzTk1oTTRRQ2dIUnNBblJCQXN6MW11VnVaQTh1MFg5cEJucjg=";
		String secret = "E3PHhMok4j9q5XWbI4D2wwraLX1crM6nrRRfNdJ+694=";
		
		ZatcaApiHelper apiHelper = new ZatcaApiHelper();
		apiHelper.setUsername(key);
		apiHelper.setPasswd(secret);
		
		Properties ctx = new Properties();
		Env.setContext(ctx, "AD_Client_ID", 1000000);
		Env.setContext(ctx, "AD_Org_ID", 0);
			
		try {
			MInvoice minvoice = MInvoice.get(ctx, 1075750);
			assertNotNull(minvoice);
			Invoice xmlInvoice = EInvoiceXmlFactory.createInvoiceXml(minvoice);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			EInvoiceXmlFactory.marshalJaxb(xmlInvoice, os, false);
		
			byte[] invoiceData = CanonicalizeHelper.canonicalize(os.toByteArray(), false);
			InvoiceResult response = apiHelper.checkInvoiceCompliance(minvoice.getInvoiceHash(), minvoice.getUUID(), invoiceData);				
			System.out.print(response);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		
	}

	
}
