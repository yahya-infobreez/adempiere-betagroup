package com.betagroup.einvoice.process;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.logging.Level;

import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.MOrg;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;

import com.betagroup.einvoice.DigitalSignatureHelper;
import com.betagroup.einvoice.ZatcaApiHelper;
import com.betagroup.einvoice.api.model.CSRResponse;

public class Process_EInvoiceRenewRegistration extends SvrProcess {

	private int p_AD_Org_ID;
	private String p_CSRFile;
	private String p_Otp;	
	private static CLogger s_log = CLogger.getCLogger(Process_EInvoiceRenewRegistration.class);


	@Override
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("AD_Org_ID"))
				p_AD_Org_ID = para[i].getParameterAsInt();
			else if (name.equals("OTP"))
				p_Otp = (String) para[i].getParameter();	
			else if (name.equals("CSRFile"))
				p_CSRFile = (String) para[i].getParameter();				
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}
		if(p_AD_Org_ID <= 0)
			throw new FillMandatoryException("AD_Org_ID");
	}

	@Override
	protected String doIt() throws Exception {
		MOrg org = MOrg.get(getCtx(), p_AD_Org_ID);
		Path csrFile = Paths.get(p_CSRFile);
		byte[] csrData = Files.readAllBytes(csrFile);
		ZatcaApiHelper apiHelper = new ZatcaApiHelper();
		apiHelper.setAuth(org.getCertificate(), org.getZatcaSecret());
		
		CSRResponse response = apiHelper.renewPCSID(new String(csrData), p_Otp);
		System.out.print(response);
		
		if(response != null && ("ISSUED".equalsIgnoreCase(response.getDispositionMessage()) 
				|| "NOT_COMPLIANT".equalsIgnoreCase(response.getDispositionMessage()))) {
			org.setZatcaRequestID(response.getRequestID());
			org.setCertificate(response.getBinarySecurityToken()); // Base64 Encoded
			org.setZatcaSecret(response.getSecret());
			org.setZatcaStatus(response.getDispositionMessage()); // ISSUED / NOT_COMPLIANT
			// In case of renewal, status can be NOT_COMPLIANT. Then need to re-do compliance steps
			org.setZatcaIsProduction("ISSUED".equalsIgnoreCase(response.getDispositionMessage()));
			// Save expiry date separately
			byte[] cert2 = Base64.getDecoder().decode(response.getBinarySecurityToken());
			X509Certificate x509Cert = DigitalSignatureHelper.decode(new String(cert2));
			org.setZatcaExpiry(x509Cert != null ? new Timestamp(x509Cert.getNotAfter().getTime()) : null);

		} else {
			String msg = "Error: CSR Registration renewal with FATOORA portal failed. "
					+ response != null ? response.getErrors().toString() : "Reason unknown";
			s_log.saveError("ZatcaRegistrationFailed", msg);
			return msg;
		}
		org.saveEx();
		return "Success";
	}

}
