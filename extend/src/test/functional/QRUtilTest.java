package test.functional;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import com.betagroup.einvoice.QRUtil;

import junit.framework.TestCase;

public class QRUtilTest extends TestCase {

	public void testGenerateQR() {
		String qr = QRUtil.generateQR("Company name", "300012345678903", Timestamp.valueOf("2024-01-01 00:00:00"), BigDecimal.valueOf(1000.55), BigDecimal.valueOf(99.59));
		System.out.println("QR = " + qr);
		qr = QRUtil.generateQR("Company name", "312345678903", Timestamp.valueOf("2024-01-01 00:00:00"), BigDecimal.valueOf(1000.55), BigDecimal.valueOf(99.59));
		System.out.println("QR = " + qr);	
	}
	
	public void testGenerateQR2() {
		String date = "2024-01-01T00:00:00Z";
		String qr2 = QRUtil.convertUsingTLVToBase64("Company name", "300012345678903", date, "1000.55", "99.59");
		System.out.println("QR2 = " + qr2);	
		String qr3 = QRUtil.generateQR("Company name", "300012345678903", Timestamp.valueOf("2024-01-01 00:00:00"), BigDecimal.valueOf(1000.55), BigDecimal.valueOf(99.59));
		System.out.println("QR3 = " + qr3);
		System.out.println(QRUtil.decodeQR(qr2));
		System.out.println(QRUtil.decodeQR(qr3));
		assertEquals(qr2, qr3);
	}

	public void testGetStringFromTimestamp() {
		String dateString = QRUtil.getStringFromTimestamp(Timestamp.from(Instant.now()), null);
		System.out.println("Instant date: " + dateString);
		dateString = QRUtil.getStringFromTimestamp(Timestamp.valueOf(LocalDateTime.now()), null);
		System.out.println("Local Date: " + dateString);
		dateString = QRUtil.getStringFromTimestamp(Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Asia/Kolkata"))), null);
		System.out.println("Riyadh Local date: " + dateString);
		dateString = QRUtil.getStringFromTimestamp(Timestamp.valueOf("2024-01-01 00:00:00"), "yyy-MM-dd'T'HH:mm:ss");
		System.out.println("Custom date: " + dateString);
	}

	public void testConvertHex() {
		String hex = "499602d2f0a20";	
		System.out.println("Hex: " + hex);
		byte[] binary = QRUtil.convertHexStringToBinary(hex);
		System.out.println("Binary length: " + binary.length);
		String hex2 = QRUtil.convertToHexString(binary);		
		System.out.println("converted: " + hex2);
		assertEquals(hex, hex2);
	}
	
	public void testDecodeQR() {
		String qr = QRUtil.generateQR("Company name", "300012345678903", Timestamp.valueOf("2024-01-01 00:00:00"), BigDecimal.valueOf(1000.55), BigDecimal.valueOf(99.59));
		System.out.println("QR = " + qr);
		ArrayList<String> qrData = QRUtil.decodeQR(qr);
		System.out.println(qrData);
	}
}
