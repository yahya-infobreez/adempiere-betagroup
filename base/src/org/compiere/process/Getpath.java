package org.compiere.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.compiere.util.Ini;

public class Getpath 
{
		
	/**
	 * @param args
	 * @throws IOException 
	 */
// function used to loading path for iReport images files and subreport files
public static String path(String filename)
	{
		String m_path;
		File REPORT_HOME = null;
		
  	REPORT_HOME = new File(Ini.getAdempiereHome() + File.separator + "reports");
			System.out.println("REPORT_HOME: " + REPORT_HOME.toString() + "/");
		return REPORT_HOME.toString() + "/";
	}
	
	
	public static String ReadProp(String fileloc, String repfile)
	{
	
	String retstr = fileloc + repfile;
	System.out.println("Subreport " + retstr );
	return retstr;

		
	}
	
	}