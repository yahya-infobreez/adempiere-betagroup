package test.functional.einvoice;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Properties;

import org.compiere.model.MInvoice;
import org.compiere.util.Env;
import org.w3._2000._09.xmldsig_.KeyInfo;
import org.w3._2000._09.xmldsig_.X509Data;

import com.betagroup.einvoice.EInvoiceXmlFactory;
import com.betagroup.einvoice.QRUtil;
import com.betagroup.einvoice.ZatcaSDKProcessHelper;

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
		MInvoice minvoice = MInvoice.get(ctx, 1075286);
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
		MInvoice minvoice = MInvoice.get(ctx, 1075742);

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
			FileInputStream in = new FileInputStream("/tmp/eInvoice42470ARI.xml");
			Invoice xmlInvoice = EInvoiceXmlFactory.loadXml(in);
			in.close();
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

	
	public void testValidateInvoiceB2B1() {
		Properties ctx = new Properties();
		Env.setContext(ctx, "AD_Client_ID", 1000000);
		Env.setContext(ctx, "AD_Org_ID", 0);
		MInvoice minvoice = MInvoice.get(ctx, 1075741);
		assertNotNull(minvoice);
		try {
			Invoice xmlInvoice = EInvoiceXmlFactory.createInvoiceXml(minvoice);
			assertNotNull(xmlInvoice);
			String fileName = "eInvoice" + xmlInvoice.getID().getValue()
					.replaceAll("\\s+", "_")
					.replaceAll("\\\\", "_")
					.replaceAll("/", "_");
			File inputFile = new File(System.getProperty("java.io.tmpdir"),  fileName + ".xml");
	    	// Write the Invoice XML into tmp file first
	    	BufferedOutputStream outStream1 = new BufferedOutputStream(new FileOutputStream(inputFile));
	    	EInvoiceXmlFactory.marshalJaxb(xmlInvoice, outStream1, false);
	    	outStream1.close();
			ZatcaSDKProcessHelper.validateXml(inputFile);			
		} catch (Exception e) {			
			e.printStackTrace();
			fail(e.getMessage());
		} finally {
		}
	}	
	
	public void testValidateInvoiceB2B2() {
		Properties ctx = new Properties();
		Env.setContext(ctx, "AD_Client_ID", 1000000);
		Env.setContext(ctx, "AD_Org_ID", 0);
	
		MInvoice minvoice = MInvoice.get(ctx, 1075742);
		// 1075739=42470ARI	1075740=42471ARI
		assertNotNull(minvoice);
		try {
			Invoice xmlInvoice = EInvoiceXmlFactory.createInvoiceXml(minvoice);
			assertNotNull(xmlInvoice);
			String fileName = "eInvoice" + xmlInvoice.getID().getValue()
					.replaceAll("\\s+", "_")
					.replaceAll("\\\\", "_")
					.replaceAll("/", "_");
			File inputFile = new File(System.getProperty("java.io.tmpdir"),  fileName + ".xml");
	    	BufferedOutputStream outStream1 = new BufferedOutputStream(new FileOutputStream(inputFile));
	    	EInvoiceXmlFactory.marshalJaxb(xmlInvoice, outStream1, false);
	    	outStream1.close();
			ZatcaSDKProcessHelper.validateXml(inputFile);			
		} catch (Exception e) {			
			e.printStackTrace();
			fail(e.getMessage());
		} finally {
		}
	}
	
	public void testValidateCreditNoteB2B1() {
		Properties ctx = new Properties();
		Env.setContext(ctx, "AD_Client_ID", 1000000);
		Env.setContext(ctx, "AD_Org_ID", 0);
	
		MInvoice minvoice = MInvoice.get(ctx, 1075745); // Credit Note from RMA
		assertNotNull(minvoice);
		try {
			Invoice xmlInvoice = EInvoiceXmlFactory.createInvoiceXml(minvoice);
			assertNotNull(xmlInvoice);
			
			String fileName = "eInvoice" + xmlInvoice.getID().getValue()
					.replaceAll("\\s+", "_")
					.replaceAll("\\\\", "_")
					.replaceAll("/", "_");
			File inputFile = new File(System.getProperty("java.io.tmpdir"),  fileName + ".xml");
		    	// Write the Invoice XML into tmp file first
		    	BufferedOutputStream outStream1 = new BufferedOutputStream(new FileOutputStream(inputFile));
		    	EInvoiceXmlFactory.marshalJaxb(xmlInvoice, outStream1, false);
		    	outStream1.close();
		    	
			ZatcaSDKProcessHelper.validateXml(inputFile);	
			
		} catch (Exception e) {			
			e.printStackTrace();
			fail(e.getMessage());
		} finally {
		}
	}
	
	public void testValidateDebitNoteB2B1() {
		Properties ctx = new Properties();
		Env.setContext(ctx, "AD_Client_ID", 1000000);
		Env.setContext(ctx, "AD_Org_ID", 0);
	
		MInvoice minvoice = MInvoice.get(ctx, 1075750); //Debit Note-B2C
		assertNotNull(minvoice);
		try {
			Invoice xmlInvoice = EInvoiceXmlFactory.createInvoiceXml(minvoice);
			byte[] invoiceData = EInvoiceXmlFactory.canonicalize(xmlInvoice, false);
			assertNotNull(xmlInvoice);
			
			String fileName = "eInvoice" + xmlInvoice.getID().getValue()
					.replaceAll("\\s+", "_")
					.replaceAll("\\\\", "_")
					.replaceAll("/", "_");
			File inputFile = new File(System.getProperty("java.io.tmpdir"),  fileName + ".xml");
		    	// Write the Invoice XML into tmp file first
		    Files.write(inputFile.toPath(), invoiceData);
		    	
			ZatcaSDKProcessHelper.validateXml(inputFile);	
			
		} catch (Exception e) {			
			e.printStackTrace();
			fail(e.getMessage());
		} finally {
		}
	}
	
	public void testExtractQRCode() {
		Properties ctx = new Properties();
		Env.setContext(ctx, "AD_Client_ID", 1000000);
		Env.setContext(ctx, "AD_Org_ID", 0);
	
		MInvoice minvoice = MInvoice.get(ctx, 1075753); // B2C Invoice
		assertNotNull(minvoice);
		try {
			Invoice xmlInvoice = EInvoiceXmlFactory.createInvoiceXml(minvoice);
			byte[] invoiceData = EInvoiceXmlFactory.canonicalize(xmlInvoice, false);
			assertNotNull(xmlInvoice);

			String qrCode = xmlInvoice.getAdditionalDocumentReferences().stream()
					.filter(ref->"QR".equals(ref.getID().getValue()))
					.map(ref->ref.getAttachment().getEmbeddedDocumentBinaryObject().getValue())
					.findFirst().orElse(null);
			assertNotNull(qrCode);
			ArrayList<String> qrData = QRUtil.decodeQR(qrCode);
			System.out.println(qrData);
		} catch (Exception e) {			
			e.printStackTrace();
			fail(e.getMessage());
		} finally {
		}
	}
	
	public void testValidateB2CInvoice() {
		Properties ctx = new Properties();
		Env.setContext(ctx, "AD_Client_ID", 1000000);
		Env.setContext(ctx, "AD_Org_ID", 0);
	
		MInvoice minvoice = MInvoice.get(ctx, 1075753); // B2C Invoice
		assertNotNull(minvoice);
		try {
			Invoice xmlInvoice = EInvoiceXmlFactory.createInvoiceXml(minvoice);
			byte[] invoiceData = EInvoiceXmlFactory.canonicalize(xmlInvoice, false);
			assertNotNull(xmlInvoice);
			
			String fileName = "eInvoice" + xmlInvoice.getID().getValue()
					.replaceAll("\\s+", "_")
					.replaceAll("\\\\", "_")
					.replaceAll("/", "_");
			File inputFile = new File(System.getProperty("java.io.tmpdir"),  fileName + ".xml");
		    	// Write the Invoice XML into tmp file first
			Files.write(inputFile.toPath(), invoiceData);
		    	
			ZatcaSDKProcessHelper.validateXml(inputFile);	
			
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
		MInvoice minvoice = MInvoice.get(ctx, 1075742); // 1075216 = 42235ARI,  1075283=24884ARC, 1075286=42273ARI, 1075323=12634APC
		// 1074762 = 11932ARD, 1074815=16390APD, 1074995=16414APD, 1074714=11925ARD
		assertNotNull(minvoice);
		try {
			String fileName = "/tmp/eInvoice42470ARI.xml";
			File inputFile = new File(fileName);
			File outFile = new File(fileName.replace(".xml", "-signed.xml"));
			
			File output = ZatcaSDKProcessHelper.generateSignedXmlFile(inputFile, outFile);
			System.out.println("Output file = " + output.getAbsolutePath());
			assertTrue(output.exists());
		} catch (Exception e) {			
			e.printStackTrace();
			fail(e.getMessage());
		} finally {
		}
	}
	
	public void testMarshalKeyInfo() throws Exception {
		String certificateData = "MIIE4zCCBImgAwIBAgITegAALzfpPGY2F7fidQABAAAvNzAKBggqhkjOPQQDAjBiMRUwEwYKCZImiZPy";
		X509Data x509Data = new X509Data().addX509Certificate(certificateData);
		KeyInfo keyInfo = new KeyInfo(); 
		keyInfo.getContent().add(x509Data);
		EInvoiceXmlFactory.marshalJaxb(keyInfo, System.out, true);
	}

	public void testMarshalX509Data() throws Exception {
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
