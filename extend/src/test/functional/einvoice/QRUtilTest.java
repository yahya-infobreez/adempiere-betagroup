package test.functional.einvoice;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;

import com.betagroup.einvoice.DigitalSignatureHelper;
import com.betagroup.einvoice.QRUtil;

import junit.framework.TestCase;

public class QRUtilTest extends TestCase {

	public void testGenerateQR() {
		String qr = QRUtil.generateQR("Company name", "300012345678903", Timestamp.valueOf("2024-01-01 00:00:00"), BigDecimal.valueOf(1000.55), BigDecimal.valueOf(99.59));
		System.out.println("QR = " + qr);
		String qr2 = QRUtil.generateQR("Company name", "312345678903", Timestamp.valueOf("2024-01-01 00:00:00"), BigDecimal.valueOf(1000.55), BigDecimal.valueOf(99.59));
		System.out.println("QR = " + qr);	
		System.out.println(QRUtil.decodeQR(qr));
		System.out.println(QRUtil.decodeQR(qr2));
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
	
	
	public void testDecodeQR2() {
//		String qr3 = "AQtOb29yIFRyYXZlbAIPMzAwNDY0NTc4OTAwMDAzAxMyMDI0LTAyLTIwVDE0OjQwOjAwBAgxMTUwMC4wMAUHMTUwMC4wMAYsWG1Ud1c5cFpaa3crV2NwRlJiRVpGTTI1Szc1SEpoYUlrUGZUVFBUVWxrdz0HYE1FUUNJQldQVU16ems3M3E4ZDVtYjRKOWU1enhhTTk2dGJrampSYU1rdFpDbUVHcUFpQVh1TW01eExJb005VUllS1Z1SlZUZVlpdWNSaklDYzlnNFZ3Y1BtS1M3dmc9PQhYMFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAE03LVZW5PUPVF99KjcbZgITm3RppsWgmGjTgiiAIfbPYIVSc0itWMn7XE6Tu9vYrOFWXHgYbx9cKqowTvMJHrGQ==";
////		String qr3 = "AS3vu7vYq9mB2LQg2YfZidmK2LnYs9mB2YLZh9i02YUg2KTYrtip2K3YtNmJ2LoCDzMxMjM0NTY3ODkwMjM0MwMUMjAyNC0xMS0yNVQxMzoyOToyMFoEBDU3LjUFAzcuNQYsaXp1cTdlQU1XZlhCRUdybmhzMlplRnlJYWpzcEZuR1liYnl2WkROR293Yz0HYE1FVUNJUURCM1M5c3NnMW9NeGFaVVRWalQ3aXFMWmNYNHl6YWVKQ09nTS9xV0YxdnFnSWdZUnYxYW45M1Y5WDRqSExjTGVGMjFNNGs2TnRyenBNVWNjcSs3eTFhOUxJPQh4TUZZd0VBWUhLb1pJemowQ0FRWUZLNEVFQUFvRFFnQUVaZXR3aWpWeUpXR0lwNUZEMTczc2dOVEpjd2VNRVMrL2lWS09WM2lyNWZCQmJqbkFrWmZzRVpQR1orcGI1dVZQQXBXZHVZVTA1dnRNaUV6aVMzRmNEQT09CUcwRQIhANtFoB2ktHRVsFcPTYm73P6pfyo6eaB/eD4QsyZDMj2LAiBKqD7EKrDTITOEAoB0bAJ0QQLM9ZrlbmQPLtF/aQZ6/A==";
//		System.out.println("QR = " + qr3);
//		ArrayList<String> qrData3 = QRUtil.decodeQR(qr3);
//		qrData3.stream().forEach(d-> {System.out.println(d);});
//		
//		String qr1 = "AQtOb29yIFRyYXZlbAIPMzAwNDY0NTc4OTAwMDAzAxMyMDI0LTAyLTIwVDExOjM2OjAwBAgxMTUwMC4wMAUHMTUwMC4wMAYsYWhwazRMYTE3ZFRadkQwZzFtN1N6dDZwVGR1eFpzcVljSEx4c1lkd2FBVT0HYE1FUUNJSEI4WkRXOTJBazNUUWEvZURSMlZWRHVHUHh6Y1QwZ01EdUc4NkY4UUtkZkFpQi9qOFg4N2VjdEdmZUYxSmE3cG0wMTF6WGdrd1FWeTBiSG5JQU1kbUM3NVE9PQhYMFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAE03LVZW5PUPVF99KjcbZgITm3RppsWgmGjTgiiAIfbPYIVSc0itWMn7XE6Tu9vYrOFWXHgYbx9cKqowTvMJHrGQ==";
//		System.out.println("QR = " + qr1);
//		ArrayList<String> qrData = QRUtil.decodeQR(qr1);
//		qrData.stream().forEach(d-> {System.out.println(d);});
		
		String qr2 = "AS3vu7vYq9mB2LQg2YfZidmK2LnYs9mB2YLZh9i02YUg2KTYrtip2K3YtNmJ2LoCDzMxMjM0NTY3ODkwMjM0MwMUMjAyNC0xMS0yNVQxMzoyOToyMFoEBDU3LjUFAzcuNQYsaXp1cTdlQU1XZlhCRUdybmhzMlplRnlJYWpzcEZuR1liYnl2WkROR293Yz0HYE1FVUNJUUNIM3lzbUVrc2hQd001dFd2N2MvMkUyVXFYQUpsREZFM2pxY0tZT09JZmVBSWdKU0dvcmtXUmlmSFhSdDJJWHllT0NWTXppVUp1OUFPa3JBdjJUdXZROGl3PQg4MDYwEAYHKoZIzj0CAQYFK4EEAAoDIgADjLz08/vu+gFyqwZR1EMDkxzsFywS/LEuczkK+1SqyBgJRzBFAiBIs3hQCJ8GLcG6oH984MBlrEzrJkONjKrPJ8Y3Rym8YwIhANlTEfCKUFzogROABTL+EMFMRcmYrPG6RxQaHKuHR+6n";
		System.out.println("QR = " + qr2);
		ArrayList<String> qrData2 = QRUtil.decodeQR(qr2);
		qrData2.stream().forEach(d-> {System.out.println(d);});
	}

}
