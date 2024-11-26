package com.betagroup.einvoice;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ZatcaSDKProcessHelper {

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

}
