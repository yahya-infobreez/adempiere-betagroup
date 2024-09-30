package org.compiere.process;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.compiere.util.DB;
import org.compiere.util.Env;
import java.util.*;

public class Process_GetHexDec_SalInv extends SvrProcess{

	private int AD_PInstance_ID = 0;

	protected void prepare()
	{
		AD_PInstance_ID = getAD_PInstance_ID();
	}

	protected String doIt() throws Exception 
	{

		String sql_rec = "Select Record_ID FROM ad_pinstance where ad_pinstance_id = ? ";
		int Record_Id = DB.getSQLValue(get_TrxName(), sql_rec, AD_PInstance_ID);
		String sql1 = "select dateinvoiced , grandtotal ,grandtotal-totallines as value5 from c_invoice where c_invoice_id=?";
		String value1= "";
		String value2= "";
		//String value3 = "";
		Date value3= new Date();
		BigDecimal value4 = Env.ZERO;
		BigDecimal value5 = Env.ZERO;    				
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;

		pstmt1 = DB.prepareStatement(sql1, "DSPL");
		pstmt1.setInt(1, Record_Id);
		rs1 = pstmt1.executeQuery();

		while (rs1.next())
		{	
			value1= "Beta Industrial Business Company";
			value2= "311263180100003";
		    value3 = rs1.getTimestamp(1);
			//value3 = "2022-04-25";
			value4 = rs1.getBigDecimal(2);
			value5 = rs1.getBigDecimal(3);
		}

		String v4 = value4.toString();
		String v5 = value5.toString();
		try{
			String Hex = StrToHexa(value1, value2, value3.toString(), v4, v5);		
			addLog( Hex);
		}
	
	catch (Exception e) {
		String s = e.getLocalizedMessage();
		System.out.println("Error : " + s);
		String s1[] = s.split(":");
		addLog(0, null, null, "Process Stopped");
		addLog(0, null, null, s1[1]);
	}

return "";
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

