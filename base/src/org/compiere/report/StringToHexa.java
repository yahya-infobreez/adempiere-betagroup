package org.compiere.report;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import net.sf.jasperreports.engine.JRDefaultScriptlet;
public  class StringToHexa extends JRDefaultScriptlet
{
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

