package org.compiere.process;
import java.math.BigDecimal;

import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.compiere.db.CConnection;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.jdesktop.swingx.MultiSplitLayout.Split;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;


public class Process_GenJson_Invoice extends SvrProcess{
	private int AD_PInstance_ID=0;
	private int Record_Id=0;
	private int AD_Client_ID = 0;
	public PreparedStatement pstmt = null;
	protected void prepare()
	{
		AD_PInstance_ID = getAD_PInstance_ID();
		AD_Client_ID = getAD_Client_ID();
		Record_Id =  getRecord_ID();
	}
	
	protected String doIt() throws Exception
	{	
		
		int doctypeid =  0;
		String docno = null;
		String custName = null;
		BigDecimal grTotal = Env.ZERO;
		BigDecimal taxVal = Env.ZERO;
		String invSQL = "select documentno, c_doctype_id,nvl(bp.name2,bp.name), grandtotal ,grandtotal-totallines as taxval  from c_invoice ci "
				+ " inner join c_bpartner bp on bp.c_bpartner_id=ci.c_bpartner_id where c_invoice_id = ?";
		try{
			PreparedStatement ps = null;
			ResultSet rs = null;
			ps = DB.prepareStatement(invSQL, "DSPL");
			ps.setInt(1, Record_Id);

			rs = ps.executeQuery();
			if (rs.next())
			{
				docno =  rs.getString(1);
				doctypeid = rs.getInt(2);
				custName = rs.getString(3);
				grTotal = rs.getBigDecimal(4);
				taxVal = rs.getBigDecimal(5);
			}
			ps.close();
			rs.close();
		}

		catch (Exception e)
		{
			String msg = e.getLocalizedMessage();
			System.out.println("Error : " + msg );
			String msg1[] = msg.split(":");
			addLog(0, null, null, "Process Stopped");
			addLog(0, null, null, msg1[1]);
		}

		//JSON file creation
//YAHYA		try {
			
//				JSONArray list = new JSONArray();
//				FileWriter file = new FileWriter("/opt/Adempiere/InvArchive/test.json");
//
//				JSONObject obj = new JSONObject();
//				obj.put("taxvalue", taxVal);
//				obj.put("grandtotal", grTotal);
//				obj.put("customer", custName);
//				obj.put("docid", doctypeid);
//				obj.put("id", docno);		
//				
//	            list.add(obj);
//	            
//	            file.write(list.toString());
//	            file.flush();
//	            file.close();
//
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
        

		return " ";
		
	}
	
}
