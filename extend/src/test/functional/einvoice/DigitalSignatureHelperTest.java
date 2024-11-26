package test.functional.einvoice;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
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

public class DigitalSignatureHelperTest extends TestCase {
	
	public void testDecodeX509() throws Exception {
//		String cert = "TUlJQ0x6Q0NBZGFnQXdJQkFnSUdBWk03UUsrQU1Bb0dDQ3FHU000OUJBTUNNQlV4RXpBUkJnTlZCQU1NQ21WSmJuWnZhV05wYm1jd0hoY05NalF4TVRFM01UYzFNVFV3V2hjTk1qa3hNVEUyTWpFd01EQXdXakJuTVFzd0NRWURWUVFHRXdKVFFURVdNQlFHQTFVRUN3d05VbWw1WVdSb0lFSnlZVzVqYURFWU1CWUdBMVVFQ2d3UFFrVlVRU0JKVGtSVlUxUlNTVVZUTVNZd0pBWURWUVFEREIxQlJFVk5VRWxGVWtVdE1UQXhMVE14TWpNME5UWTNPRGt3TURBd016QldNQkFHQnlxR1NNNDlBZ0VHQlN1QkJBQUtBMElBQkdYcmNJbzFjaVZoaUtlUlE5ZTk3SURVeVhNSGpCRXZ2NGxTamxkNHErWHdRVzQ1d0pHWDdCR1R4bWZxVytibFR3S1ZuYm1GTk9iN1RJaE00a3R4WEF5amdjSXdnYjh3REFZRFZSMFRBUUgvQkFJd0FEQ0JyZ1lEVlIwUkJJR21NSUdqcElHZ01JR2RNVUl3UUFZRFZRUUVERGt4TFVGRVJVMVFTVVZTUlh3eUxUSXdNalI4TXkxbFpESXlaakZrT0MxbE5tRXlMVEV4TVRndE9XSTFPQzFrT1dFNFpqRXhaVFEwTldZeEh6QWRCZ29Ka2lhSmsvSXNaQUVCREE4ek1USXpORFUyTnpnNU1EQXdNRE14RFRBTEJnTlZCQXdNQkRFeE1EQXhEekFOQmdOVkJCb01CbEpKV1VGRVNERVdNQlFHQTFVRUR3d05UVUZPVlVaQlExUlZVa2xPUnpBS0JnZ3Foa2pPUFFRREFnTkhBREJFQWlBOTNjMWV3UkJNVlBpYkNoUWVnbUdqTWdIMUFEeVJIb0JtaGhBQzJuZ3RoZ0lnSzVaeFFRdEZETmZ2Y0Yzc0RSMFcvZHIzWVRaMmNLYmhGNUVLSEY3ZVVxWT0=";
		String cert = "TUlJQ01EQ0NBZGFnQXdJQkFnSUdBWk0vNDBZTU1Bb0dDQ3FHU000OUJBTUNNQlV4RXpBUkJnTlZCQU1NQ21WSmJuWnZhV05wYm1jd0hoY05NalF4TVRFNE1UVXlOelUwV2hjTk1qa3hNVEUzTWpFd01EQXdXakJuTVFzd0NRWURWUVFHRXdKVFFURVdNQlFHQTFVRUN3d05VbWw1WVdSb0lFSnlZVzVqYURFWU1CWUdBMVVFQ2d3UFFrVlVRU0JKVGtSVlUxUlNTVVZUTVNZd0pBWURWUVFEREIxQlJFVk5VRWxGVWtVdE1UQXhMVE14TWpNME5UWTNPRGt3TURBd016QldNQkFHQnlxR1NNNDlBZ0VHQlN1QkJBQUtBMElBQkdYcmNJbzFjaVZoaUtlUlE5ZTk3SURVeVhNSGpCRXZ2NGxTamxkNHErWHdRVzQ1d0pHWDdCR1R4bWZxVytibFR3S1ZuYm1GTk9iN1RJaE00a3R4WEF5amdjSXdnYjh3REFZRFZSMFRBUUgvQkFJd0FEQ0JyZ1lEVlIwUkJJR21NSUdqcElHZ01JR2RNVUl3UUFZRFZRUUVERGt4TFVGRVJVMVFTVVZTUlh3eUxUSXdNalI4TXkxbFpESXlaakZrT0MxbE5tRXlMVEV4TVRndE9XSTFPQzFrT1dFNFpqRXhaVFEwTldZeEh6QWRCZ29Ka2lhSmsvSXNaQUVCREE4ek1USXpORFUyTnpnNU1EQXdNRE14RFRBTEJnTlZCQXdNQkRFeE1EQXhEekFOQmdOVkJCb01CbEpKV1VGRVNERVdNQlFHQTFVRUR3d05UVUZPVlVaQlExUlZVa2xPUnpBS0JnZ3Foa2pPUFFRREFnTklBREJGQWlFQTIwV2dIYVMwZEZXd1Z3OU5pYnZjL3FsL0tqcDVvSDk0UGhDekprTXlQWXNDSUVxb1BzUXFzTk1oTTRRQ2dIUnNBblJCQXN6MW11VnVaQTh1MFg5cEJucjg=";
		byte[] cert2 = Base64.getDecoder().decode(cert.getBytes());
		X509Certificate decoded = DigitalSignatureHelper.decode(new String(cert2));
		System.out.println(decoded);
		
		System.out.println(new String(decoded.getSignature()));
	}
	
	
	public void testGetPublicKey() throws Exception {		
		String privateKeyEncoded = "MIGNAgEAMBAGByqGSM49AgEGBSuBBAAKBHYwdAIBAQQgQQAi3mxIxffMKt9+4YvRJ8qaI0VTP0JcAJTw+6AHrZKgBwYFK4EEAAqhRANCAARl63CKNXIlYYinkUPXveyA1MlzB4wRL7+JUo5XeKvl8EFuOcCRl+wRk8Zn6lvm5U8ClZ25hTTm+0yITOJLcVwM";

		PrivateKey privateKey = DigitalSignatureHelper.getPrivateKey(privateKeyEncoded);
		PublicKey publicKey = DigitalSignatureHelper.getPublicKeyFromPrivateKey(privateKey);
	
		System.out.println(publicKey);
		System.out.println(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
		
	}
	
	public void testGenearteKeyPair() throws Exception {
		
		KeyPair pair = DigitalSignatureHelper.generateKeyPair();
	      System.out.println("Private Key: " + pair.getPrivate());
	      System.out.println("Public Key: " + pair.getPublic());
	      
	      String privateKeyEncoded = Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded());
	      String publicKeyEncoded = Base64.getEncoder().encodeToString(pair.getPublic().getEncoded());
	      System.out.println("Private Key: " + privateKeyEncoded);
	      System.out.println("Public Key: " + publicKeyEncoded);
//	      
//	      
//	      PrivateKey privateKey = DigitalSignatureHelper.getPrivateKey(privateKeyEncoded);
//	      System.out.println("Private Key: " + privateKey);
//	      PublicKey publicKey = DigitalSignatureHelper.getPublicKeyFromPrivateKey(privateKey);
//			System.out.println("Public Key: " + publicKey);
//			System.out.println(Base64.getEncoder().encodeToString(publicKey.getEncoded()));
	      
	}
}
