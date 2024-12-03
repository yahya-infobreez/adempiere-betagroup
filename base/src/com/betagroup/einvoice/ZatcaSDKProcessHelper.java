package com.betagroup.einvoice;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.PrivateKey;
import java.security.PublicKey;

public class ZatcaSDKProcessHelper {

	/*
	 *
	 How to generate ECDSA keys with secp256k1
	 https://techdocs.akamai.com/iot-token-access-control/docs/generate-ecdsa-keys 
	 
	 Commands:
	 openssl ecparam -name secp256k1 -genkey -noout -out ec-secp256k1-priv-key.pem
	 openssl ec -in ec-secp256k1-priv-key.pem -pubout > ec-secp256k1-pub-key.pem
	 
	 */
	
			
	public static File generateSignedXmlFile(File inFile, File outFile) throws Exception {
		FileOutputStream out = null;
	    try {
	    	// Pass the XML file to ZATCA SDK. Output file will be created by the SDK
			ProcessBuilder builder = new ProcessBuilder("fatoora", "-sign", "-signedInvoice", outFile.getAbsolutePath(),
					"-invoice", inFile.getAbsolutePath());
//			Map<String, String> envMap = new HashMap<String, String>() {{
//	            put("key1", "value1");
//			}};			
//			builder.environment(envMap);

//			builder.directory(new File("working_directory"));

			Process process = builder.start();

			// Wait for the process to complete
			int exitCode = process.waitFor();
			ZatcaSDKProcessHelper.printConsole(process, exitCode);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return outFile; // outFile will be filled by the process
	}
	
	
	static void printConsole(Process process, int exitCode) throws InterruptedException, IOException {
		if (exitCode == 0) {
		    System.out.println("Process completed successfully.");		        
		} else {
			System.out.println("Process exited with error code: " + exitCode);
		}
		// Print Out & Error irrespective of status. // Status returned is not proper too
		String line;
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	
		while ((line = reader.readLine()) != null) {
		    System.out.println(line);
		}
	
		// Print Error output
		System.err.println("**** Error console *******");
		BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		while ((line = errorReader.readLine()) != null) {
		    System.err.println(line);
		}
	}
	
	public static void validateXml(File inputFile) throws Exception {
		try {
	    	
	    	// Pass the XML file to ZATCA SDK. Output file will be created by the SDK
			ProcessBuilder builder = new ProcessBuilder("fatoora", "-validate",
					"-invoice", inputFile.getAbsolutePath());
//			Map<String, String> envMap = new HashMap<String, String>() {{
//	            put("key1", "value1");
//			}};			
//			builder.environment(envMap);

//			builder.directory(new File("working_directory"));

			Process process = builder.start();
			// Wait for the process to complete
			int exitCode = process.waitFor();
			ZatcaSDKProcessHelper.printConsole(process, exitCode);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}	    
	}
	
	public static byte[] getCompressedPublicKey(String privateKeyBase64) throws Exception {		
    	File privateKeyFile = File.createTempFile("Key", ".pem");
	    try(FileOutputStream out = new FileOutputStream(privateKeyFile)) {	    	
	    	out.write("-----BEGIN PRIVATE KEY-----\n".getBytes());
	    	out.write(privateKeyBase64.getBytes());
	    	out.write("\n-----END PRIVATE KEY-----".getBytes());
	    	out.close();
		    return getCompressedPublicKey(privateKeyFile);
	    } finally {
	    	privateKeyFile.delete();
	    }
	}

	public static byte[] getCompressedPublicKey(File privateKeyFile) throws Exception {	
	    // Call openssl to convert the PrivateKey to compressed (DER encoded) public key
	    	// openssl ec -in pvt.pem -pubout -conv_form compressed
		try {
			ProcessBuilder builder = new ProcessBuilder("openssl", "ec", "-in", privateKeyFile.getAbsolutePath(),
					"-pubout", "-conv_form", "compressed", "-outform", "DER");

			Process process = builder.start();

			// Wait for the process to complete
			int exitCode = process.waitFor();
			
			// Print Out & Error irrespective of status. // Status returned is not proper too
			String line;
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];

            int bytesRead;
            while ((bytesRead = process.getInputStream().read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.close();
		
			// Print Error output
			System.err.println("**** Error console *******  Exit code: " + exitCode + "\n");
			BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while ((line = errorReader.readLine()) != null) {
			    System.err.println(line);
			}


            privateKeyFile.delete();
			return outputStream.toByteArray();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	    throw null;
	}

}
