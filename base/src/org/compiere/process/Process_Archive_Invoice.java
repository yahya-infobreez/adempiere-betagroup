package org.compiere.process;
import java.math.BigDecimal;

import java.nio.charset.StandardCharsets;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.compiere.db.CConnection;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.jdesktop.swingx.MultiSplitLayout.Split;

public class Process_Archive_Invoice extends SvrProcess{
	private int AD_PInstance_ID=0;
	private int Record_Id=0;
	private int AD_Client_ID = 0;
	public PreparedStatement pstmt = null;
	protected void prepare(){
		AD_PInstance_ID = getAD_PInstance_ID();
		AD_Client_ID = getAD_Client_ID();
		Record_Id =  getRecord_ID();
	}
	protected String doIt() throws Exception {
		String docno = null;
		String value1 = "Beta Industrial Business Company";
		String value2 = "311263180100003";
		Date value3   = new Date();
		BigDecimal value4 = Env.ZERO;
		BigDecimal value5 = Env.ZERO;  
		String v4 = "";
		String v5 ="";

		int doctypeid =  0;
		String invSQL = "select documentno, c_doctype_id,dateinvoiced , grandtotal ,grandtotal-totallines as value5  from c_invoice where c_invoice_id = ?";
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

				value3 = rs.getTimestamp(3);
				value4 = rs.getBigDecimal(4);
				value5 = rs.getBigDecimal(5);
			}
			v4 = value4.toString();
			v5 = value5.toString();

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

		try{
			String base64 = StrToHexa(value1, value2, value3.toString(), v4, v5);
			String sql = "{call BETA_UpdQRCode_SalInv(?,?)}";
			try 
			{
				CallableStatement cstmt = DB.prepareCall(sql);
				cstmt.setInt(1, Record_Id);
				cstmt.setString(2, base64);
				int no=cstmt.executeUpdate();
				cstmt.close();
				addLog(0, null, null, "Process Completed Successfully");
			} 
			catch (Exception e) 
			{
				String s = e.getLocalizedMessage();
				System.out.println("Error : " + s);
				String s1[] = s.split(":");
				addLog(0, null, null, "Process Stopped");
				addLog(0, null, null, s1[1]);
			}
			/*String updBase64 = "update C_Invoice set base64Str ='" + base64 + "' where c_invoice_id ="+ Record_Id;
			int upd64 = DB.executeUpdate(updBase64, get_TrxName()); 
			 */
			//	addLog( Hex);
		}
		catch (Exception e)
		{
			String s = e.getLocalizedMessage();
			System.out.println("Error : " + s);
			String s1[] = s.split(":");
			addLog(0, null, null, "Process Stopped");
			addLog(0, null, null, s1[1]);
		}
		String fileName = null;
		if (doctypeid == 1000003)
			fileName = "/opt/Adempiere/reports/SalInvPrint.jasper";
		else if (doctypeid == 1000005)
			fileName = "/opt/Adempiere/reports/Credit_Debit_Note.jasper";
		String outFileName = "/opt/Adempiere/InvArchive/" + docno +".pdf";
		try
		{
			HashMap<String,BigDecimal> mm = new HashMap<String, BigDecimal>();
			mm.put("C_Invoice_ID", BigDecimal.valueOf(Record_Id));
			//mm.put("LOGOYN", BigDecimal.valueOf('Y'));
			JasperPrint prnt = JasperFillManager.fillReport(fileName, mm,
					CConnection.get().getConnection(false, 8));
			JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();
			exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outFileName);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, prnt);
			exporter.exportReport();
			addLog(0,null,null, "Invoice Successfully Archived");
			String UpdArchQuery = "update C_Invoice set btn_invarch = 'Y' where c_invoice_id ="+  Record_Id;
			int updArch = DB.executeUpdate(UpdArchQuery, get_TrxName()); 
		}
		catch (Exception e)
		{
			String msg = e.getLocalizedMessage();
			System.out.println("Error : " + msg );
			String msg1[] = msg.split(":");
			addLog(0, null, null, "Process Stopped");
			addLog(0, null, null, msg1[1]);
		}

		return " ";
	}
	public String StrToHexa(String value1, String value2, String value3, String value4, String value5)throws DecoderException 
	{

		String hexStr ="" ;
		for (int i=1; i<=5;i++)
		{
			String tlv1="";
			switch (i)
			{
			case 1:
				tlv1 =convertStringToHexa(i, value1);
				break;
			case 2:
				tlv1 =convertStringToHexa(i, value2);
				break;
			case 3:
				tlv1=convertStringToHexa(i, value3);
				break;
			case 4:
				tlv1=convertStringToHexa(i, value4);
				break;
			case 5:
				tlv1=convertStringToHexa(i, value5);
				break;
			default:
				System.out.println("Error");
			}
			hexStr = hexStr+tlv1;
		}

		byte[] decodedHex = Hex.decodeHex(hexStr.toCharArray());
		String base64Str = Base64.encodeBase64String(decodedHex);
		return base64Str;
	}

	public static String convertStringToHexa(int tag, String value)
	{
		String  t= Integer.toHexString(tag);
		value = value.replaceAll(",", "");
		int len = value.length();

		String l= Integer.toHexString(len);
		if(l.length()<2)
		{
			l = 0+l;
		}
		byte[] valueBytes = value.getBytes(StandardCharsets.UTF_8);
		String v = Hex.encodeHexString(valueBytes);
		String finalHexString = (0+t)+(l)+v;
		return finalHexString;
	}
}
