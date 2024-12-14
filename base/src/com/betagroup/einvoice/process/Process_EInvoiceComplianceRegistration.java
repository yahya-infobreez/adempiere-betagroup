package com.betagroup.einvoice.process;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;

import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.MOrg;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.CLogger;

import com.betagroup.einvoice.ZatcaApiHelper;
import com.betagroup.einvoice.api.model.CSRResponse;

public class Process_EInvoiceComplianceRegistration extends SvrProcess {

	private int p_AD_Org_ID;
	private String p_UserName;
	private String p_Password;
	private String p_CSRFile;
	private String p_PrivateKeyFile;
	private String p_Otp;	
	
	private static CLogger s_log = CLogger.getCLogger(Process_EInvoiceComplianceRegistration.class);


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
			else if (name.equals("UserName"))
				p_UserName = (String) para[i].getParameter();
			else if (name.equals("Password"))
				p_Password = (String) para[i].getParameter();	
			else if (name.equals("OTP"))
				p_Otp = (String) para[i].getParameter();	
			else if (name.equals("CSRFile"))
				p_CSRFile = (String) para[i].getParameter();	
			else if (name.equals("PrivateKeyFile"))
				p_PrivateKeyFile = (String) para[i].getParameter();
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
		Path privatKeyFile = Paths.get(p_PrivateKeyFile);
		byte[] csrData = Files.readAllBytes(csrFile);
		byte[] privateKeyData = Files.readAllBytes(privatKeyFile);
		
		// Also save the privatekey. Public key will be derived from Private Key or from certificate on demand
		org.setPrivateKey(new String(privateKeyData));
//		PrivateKey privateKey = DigitalSignatureHelper.getPrivateKey(new String(privateKeyData)); 		
//		PublicKey publicKey = DigitalSignatureHelper.getPublicKeyFromPrivateKey(privateKey); 
//	    String publicKeyEncoded = Base64.getEncoder().encodeToString(ZatcaSDKProcessHelper.getCompressedPublicKey(privatKeyFile.toFile()));		
//		org.setPublicKey(publicKeyEncoded);
		
		ZatcaApiHelper apiHelper = new ZatcaApiHelper();
		apiHelper.setAuth(p_UserName, p_Password);
		
		CSRResponse response = apiHelper.requestCCSID(new String(csrData), p_Otp);
		System.out.print(response);
		
		if(response != null && ("ISSUED".equalsIgnoreCase(response.getDispositionMessage()) 
				|| "NOT_COMPLIANT".equalsIgnoreCase(response.getDispositionMessage()))) {
			org.setZatcaRequestID(response.getRequestID());
			org.setCertificate(response.getBinarySecurityToken()); // Base64 Encoded
			org.setZatcaSecret(response.getSecret());
			org.setZatcaStatus(response.getDispositionMessage()); // ISSUED / NOT_COMPLIANT
			org.setZatcaIsProduction(false);

		} else {
			String msg = "Error: CSR Registration with FATOORA portal failed. "
					+ response != null ? response.getErrors().toString() : "Reason unknown";
			s_log.saveError("ZatcaRegistrationFailed", msg);
			return msg;
		}
		org.saveEx();
		return "Success";
	}

}
