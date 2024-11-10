package com.betagroup.einvoice;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class QRUtil {

	
    /**
	 * Generate QR code string Using TLVToBase64 conversion
     * @param name
     * @param vatNo
     * @param time
     * @param grandTotal
     * @param vatAmt
     * @param hash
     * @param ecdsaSign
     * @param ecdsaPublicKey
     * @param zatcaSignForPublicKey Only for simplified invoices. 
     * 		<p>In order to get the value for Tag9 i.e. the Digital Signature of the Certificate please follow the steps below: 
    		<li>Get a hold of your device’s PCSID (You get this once you have successfully onboarded the device)
    		<li>Decode the PCSID. One available online tool is: CSR Decoder and Certificate Decoder | CSR Checker | Certificate Checker

     * @return
     */
    public static String generateQR(String name, String vatNo, Timestamp time, BigDecimal grandTotal, BigDecimal vatAmt, /* Phase-1 fields */
    		String hash, String ecdsaSign, String ecdsaPublicKey, String zatcaSignForPublicKey /* phase-2 fields */ ) {
		String date = getStringFromTimestamp(time, "yyyy-MM-dd'T'HH:mm:ss'Z'");
		String qr = QRUtil.convertUsingTLVToBase64(name, vatNo, date, grandTotal.toPlainString(), vatAmt.toPlainString(),
				hash, ecdsaSign, ecdsaPublicKey, zatcaSignForPublicKey);
		return qr;
    }
    
    
    public static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HHmmss.SSSX");	
	/** Utility function to get XML date from LocalTime */
	public static  XMLGregorianCalendar getXmlTime(LocalDateTime datetime) throws DatatypeConfigurationException {
//		String datetimeString = datetime.atOffset(ZoneOffset.UTC).format(timeFormatter);
        XMLGregorianCalendar calendar = //DatatypeFactory.newInstance().newXMLGregorianCalendar(datetimeString);
        		DatatypeFactory.newInstance().newXMLGregorianCalendar(datetime.getYear(), datetime.getMonthValue(),
        				datetime.getDayOfMonth(), datetime.getHour(), datetime.getMinute(), datetime.getSecond(),
        				BigDecimal.valueOf(datetime.getNano()).scaleByPowerOfTen(-6).stripTrailingZeros().intValue(), 0 /*UTC*/);
		return calendar;
	}
    
    public static String generateQR(String name, String vatNo, Timestamp time, BigDecimal grandTotal, BigDecimal vatAmt /* Phase-1 fields */
    		/*  */ ) {
		String date = getStringFromTimestamp(time, "yyyy-MM-dd'T'HH:mm:ss'Z'");
		String qr = QRUtil.convertUsingTLVToBase64(name, vatNo, date, grandTotal.toPlainString(), vatAmt.toPlainString());
		return qr;
    }
    
    
    public static String generateSHA256Hash(byte[] input) throws NoSuchAlgorithmException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(input);
        // Convert byte array to hexadecimal string
        return convertToHexString(hashBytes);
    }
    
    // hexToASCII(convertToHexString(new byte[]) == copy the bytes !!
//	public static String convertUsingTLVToBase64(String...params) {
//        String retValue = "";
//        StringBuffer hexString = new StringBuffer();
//        try {
//            for(int i=0; i < params.length; i++) {
//                hexString.append(hexToASCII(convertToHexString(new byte[]{(byte)(i+1),(byte)params[i].getBytes().length}))  + params[i]);
//            }
//            Encoder encoder = Base64.getEncoder(); 
//            retValue=encoder.encodeToString(hexString.toString().getBytes()); 
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return retValue;
//    }
	
    /**
	 * Generate QR code string Using TLVToBase64 conversion
     * @param name
     * @param vatNo
     * @param time
     * @param grandTotal
     * @param vatAmt
     * @param .....
     * @return
     */
	public static String convertUsingTLVToBase64(String...params) {
        String retValue = "";
        ByteBuffer buffer = ByteBuffer.allocate(1024); // Max 700 characters
        try {
            for(int i=0; i < params.length; i++) {
            	if(params[i] == null)
            		break; // The last tag is conditional - applicable only for Simplified Invoices
            	byte[] data = params[i].getBytes();
                buffer.put(new byte[]{(byte)(i+1),(byte)data.length});
                buffer.put(data);
            }

            byte[] filledBytes = new byte[buffer.position()];
            buffer.flip();
            buffer.get(filledBytes);
            retValue=Base64.getEncoder().encodeToString(filledBytes); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retValue;
    }
	

	public static ArrayList<String> decodeQR(String qr) {
        Decoder decoder = Base64.getDecoder(); 
        byte[] decoded1 = decoder.decode(qr); 
        ArrayList<String> qrData = new ArrayList<String>();
        try {
        	byte[] qrBytes = decoded1; //convertHexStringToBinary(new String(decoded1));
            for(int i=0; i < qrBytes.length; ) {
            	int len = qrBytes[i+1] & 0x00FF;
            	String qrEntry = new String(qrBytes, i+2, len, StandardCharsets.UTF_8);
            	qrData.add(qrEntry);
            	i += 2 + len;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return qrData;
    }
	
    private static String hexToASCII(String hexValue) {
        StringBuilder output = new StringBuilder("");
        for (int i = 0; i < hexValue.length(); i += 2) {
            String str = hexValue.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }

    /**************************
     *  Convert Byte Array to Hex String
     *  @param bytes bytes
     *  @return HexString
     */
    public static String convertToHexString (byte[] bytes)
    {
        //      see also Util.toHex
        int size = bytes.length;
        StringBuffer buffer = new StringBuffer(size*2);
        for(int i=0; i<size; i++)
        {
            // convert byte to an int
            int x = bytes[i];
            // account for int being a signed type and byte being unsigned
            if (x < 0)
            x += 256;
            String tmp = Integer.toHexString(x);
            // pad out "1" to "01" etc.
            if (tmp.length() == 1)
            	buffer.append("0");
            buffer.append(tmp);
        }
        return buffer.toString();
    }   //  convertToHexString
    
    /**
     * Convert Hex Ascii String to binary
     * @param hex
     * @return
     */
    public static byte[] convertHexStringToBinary (String hex)
    {
        //      see also Util.toHex
        // Check if the length is even
        if (hex.length() % 2 != 0) {
            hex = "0" + hex; // Pad with leading zero if necessary
        }

        int length = hex.length();
        byte[] data = new byte[length / 2];

        for (int i = 0; i < length; i += 2) {
            // Convert each pair of hex characters to a byte
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                                 + Character.digit(hex.charAt(i + 1), 16));
        }

        return data;
    }   

	
	public static  String getStringFromTimestamp(Timestamp dateTime, String dateformat){
		DateTimeFormatter formatter;
		if(dateformat == null)
			formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME; // "yyyy-MM-dd'T'HH:mm:ss"; //ISO_DATE_TIME; // ISO_INSTANT for UTC with Z suffix
		else {
			formatter = DateTimeFormatter.ofPattern(dateformat); 
		}
		if(dateTime == null || dateTime.toString().equals(""))
			return null;
//		Instant instant = dateTime.toInstant();
		LocalDateTime localDateTime = dateTime.toLocalDateTime();
		ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Riyadh")); //systemDefault()); // Is this needed? 
		String stringDate = formatter.format(zonedDateTime);
		return stringDate;
	}
	
	
}
