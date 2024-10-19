package test.functional;

import java.io.FileOutputStream;
import java.util.Properties;

import org.compiere.model.MInvoice;
import org.compiere.util.Env;

import com.betagroup.einvoice.EInvoiceXmlFactory;

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
			xmlInvoice = EInvoiceXmlFactory.createXmlInvoice(minvoice);
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
		MInvoice minvoice = MInvoice.get(ctx, 1000000);
		assertNotNull(minvoice);
		try {
			Invoice xmlInvoice = EInvoiceXmlFactory.createXmlInvoice(minvoice);
			assertNotNull(xmlInvoice);
		
			FileOutputStream out;
			out = new FileOutputStream("test-einvoice.xml");
			EInvoiceXmlFactory.writeXml(xmlInvoice, out);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

}
