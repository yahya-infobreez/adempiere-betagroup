package test.functional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.compiere.model.MInvoice;
import org.compiere.util.Env;
import org.w3._2000._09.xmldsig_.KeyInfo;
import org.w3._2000._09.xmldsig_.X509Data;

import com.betagroup.einvoice.EInvoiceXmlFactory;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2.AttachmentType;
import oasis.names.specification.ubl.schema.xsd.invoice_2.Invoice;
import test.AdempiereTestCase;

public class EInvoiceXmlFactoryTest extends AdempiereTestCase {
	@Override
	protected void setUp() throws Exception {
		testPropertiesFileName = "beta-test.properties";
		super.setUp();
		//
//		Ini.setProperty(Ini.P_UID,"AdminUser");
//		Ini.setProperty(Ini.P_PWD,"AdminUser");
//		Ini.setProperty(Ini.P_ROLE,"ADF - SAUDI User");
//		Ini.setProperty(Ini.P_CLIENT, "BETA INDUSTRIAL BUSINESS COMPANY");
//		Ini.setProperty(Ini.P_ORG,"RIYADH - BRANCH");
//		Ini.setProperty(Ini.P_WAREHOUSE,"Finished Goods Stock");
//		Ini.setProperty(Ini.P_LANGUAGE,"English");
//
//		Login login = new Login(Env.getCtx());
//		if (!login.batchLogin(null))
//			fail("Login failed");
//		//
//		CLogMgt.setLoggerLevel(Level.WARNING, null);
//		CLogMgt.setLevel(Level.WARNING);
	}
	
	public void testCreateXmlInvoice() {
		Properties ctx = Env.getCtx();
		MInvoice minvoice = MInvoice.get(ctx, 1000);
		assertNotNull(minvoice);
		Invoice xmlInvoice;
		try {
			xmlInvoice = EInvoiceXmlFactory.createInvoiceXml(minvoice);
			assertNotNull(xmlInvoice);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	public void testWriteXml() {
		Properties ctx = new Properties();
		Env.setContext(ctx, "AD_Client_ID", 1000000);
		Env.setContext(ctx, "AD_Org_ID", 0);
		MInvoice minvoice = MInvoice.get(ctx, 1075286);
		// 1075216 = 42235ARI,  1075283=24884ARC, 1075286=42273ARI, 1075323=12634APC
				// 1074762 = 11932ARD, 1074815=16390APD, 1074995=16414APD, 1074714=11925ARD
		assertNotNull(minvoice);
		try {
			Invoice xmlInvoice = EInvoiceXmlFactory.createInvoiceXml(minvoice);
			assertNotNull(xmlInvoice);
		
			FileOutputStream out;
			out = new FileOutputStream("/tmp/test-einvoice.xml");
			EInvoiceXmlFactory.marshalJaxb(xmlInvoice, out, false);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	public void testDeepCopyXml() {
		Properties ctx = new Properties();
		Env.setContext(ctx, "AD_Client_ID", 1000000);
		Env.setContext(ctx, "AD_Org_ID", 0);
		MInvoice minvoice = MInvoice.get(ctx, 1075216);
		assertNotNull(minvoice);
		try {
			Invoice xmlInvoice = EInvoiceXmlFactory.loadXml(new File("/tmp/eInvoice42470ARI.xml"));
			assertNotNull(xmlInvoice);
			
		
			FileOutputStream out;
			out = new FileOutputStream("test-einvoice.xml");
			
			EInvoiceXmlFactory.marshalJaxb(xmlInvoice, out, false);
			out.close();

			
			Invoice copy = (Invoice) EInvoiceXmlFactory.unmarshalJaxb(xmlInvoice.getClass(), new FileInputStream("test-einvoice.xml"));
			FileOutputStream out2 = new FileOutputStream("test-einvoice2.xml");
			EInvoiceXmlFactory.marshalJaxb(copy, out2, false);
			out2.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	
	public void testValidateInvoice() {
		Properties ctx = new Properties();
		Env.setContext(ctx, "AD_Client_ID", 1000000);
		Env.setContext(ctx, "AD_Org_ID", 0);
//		MInvoice minvoice = MInvoice.get(ctx, 1075739);
		MInvoice minvoice = MInvoice.get(ctx, 1075740);
		// 1075739=42470ARI	1075740=42471ARI
		// 1075216 = 42235ARI,  1075283=24884ARC, 1075286=42273ARI, 1075323=12634APC
		// 1074762 = 11932ARD, 1074815=16390APD, 1074995=16414APD, 1074714=11925ARD
		assertNotNull(minvoice);
		try {
			Invoice xmlInvoice = EInvoiceXmlFactory.createInvoiceXml(minvoice);
			assertNotNull(xmlInvoice);
			
			File output = EInvoiceXmlFactory.validateXml(xmlInvoice);			
		} catch (Exception e) {			
			e.printStackTrace();
			fail(e.getMessage());
		} finally {
		}
	}
	
	public void testSignInvoice() {
		Properties ctx = new Properties();
		Env.setContext(ctx, "AD_Client_ID", 1000000);
		Env.setContext(ctx, "AD_Org_ID", 0);
		MInvoice minvoice = MInvoice.get(ctx, 1075216); // 1075216 = 42235ARI,  1075283=24884ARC, 1075286=42273ARI, 1075323=12634APC
		// 1074762 = 11932ARD, 1074815=16390APD, 1074995=16414APD, 1074714=11925ARD
		assertNotNull(minvoice);
		try {
			String fileName = "/tmp/eInvoice42470ARI.xml";
			File inputFile = new File(fileName);
			File outFile = new File(fileName.replace(".xml", "-signed.xml"));
			
			File output = EInvoiceXmlFactory.generateSignedXmlFile(inputFile, outFile);
			System.out.println("Output file = " + output.getAbsolutePath());
			assertTrue(output.exists());
		} catch (Exception e) {			
			e.printStackTrace();
			fail(e.getMessage());
		} finally {
		}
	}
	
	public void testMarshalKeyInfo() {
		String certificateData = "MIIE4zCCBImgAwIBAgITegAALzfpPGY2F7fidQABAAAvNzAKBggqhkjOPQQDAjBiMRUwEwYKCZImiZPy";
		X509Data x509Data = new X509Data().addX509Certificate(certificateData);
		KeyInfo keyInfo = new KeyInfo(); 
		keyInfo.getContent().add(x509Data);
		EInvoiceXmlFactory.marshalJaxb(keyInfo, System.out, true);
	}

	public void testMarshalX509Data() {
		String certificateData = "MIIE4zCCBImgAwIBAgITegAALzfpPGY2F7fidQABAAAvNzAKBggqhkjOPQQDAjBiMRUwEwYKCZImiZPy";
		X509Data x509Data = new X509Data().addX509Certificate(certificateData); 
		EInvoiceXmlFactory.marshalJaxb(x509Data, System.out, true);
	}
	
	public void testAttachment() {
		String value = "Test Data";
		AttachmentType attachment = new AttachmentType();
		try {
			attachment.setEmbeddedDocumentBinaryObject(value, "text/plain");
			EInvoiceXmlFactory.marshalJaxb(attachment, System.out, true);
			String out = new String(attachment.getEmbeddedDocumentBinaryObject().getValue());
	    	System.out.println("In=" + value + ". Out="+ out);
	
			assertEquals(value, out);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.toString());
		}
	}
}
