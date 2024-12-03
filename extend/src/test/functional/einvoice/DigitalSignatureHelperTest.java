package test.functional.einvoice;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Base64;

import com.betagroup.einvoice.DigitalSignatureHelper;
import com.betagroup.einvoice.QRUtil;
import com.betagroup.einvoice.ZatcaSDKProcessHelper;
import junit.framework.TestCase;

public class DigitalSignatureHelperTest extends TestCase {
	
	public void testDecodeX509() throws Exception {
//		String cert = "TUlJQ0x6Q0NBZGFnQXdJQkFnSUdBWk03UUsrQU1Bb0dDQ3FHU000OUJBTUNNQlV4RXpBUkJnTlZCQU1NQ21WSmJuWnZhV05wYm1jd0hoY05NalF4TVRFM01UYzFNVFV3V2hjTk1qa3hNVEUyTWpFd01EQXdXakJuTVFzd0NRWURWUVFHRXdKVFFURVdNQlFHQTFVRUN3d05VbWw1WVdSb0lFSnlZVzVqYURFWU1CWUdBMVVFQ2d3UFFrVlVRU0JKVGtSVlUxUlNTVVZUTVNZd0pBWURWUVFEREIxQlJFVk5VRWxGVWtVdE1UQXhMVE14TWpNME5UWTNPRGt3TURBd016QldNQkFHQnlxR1NNNDlBZ0VHQlN1QkJBQUtBMElBQkdYcmNJbzFjaVZoaUtlUlE5ZTk3SURVeVhNSGpCRXZ2NGxTamxkNHErWHdRVzQ1d0pHWDdCR1R4bWZxVytibFR3S1ZuYm1GTk9iN1RJaE00a3R4WEF5amdjSXdnYjh3REFZRFZSMFRBUUgvQkFJd0FEQ0JyZ1lEVlIwUkJJR21NSUdqcElHZ01JR2RNVUl3UUFZRFZRUUVERGt4TFVGRVJVMVFTVVZTUlh3eUxUSXdNalI4TXkxbFpESXlaakZrT0MxbE5tRXlMVEV4TVRndE9XSTFPQzFrT1dFNFpqRXhaVFEwTldZeEh6QWRCZ29Ka2lhSmsvSXNaQUVCREE4ek1USXpORFUyTnpnNU1EQXdNRE14RFRBTEJnTlZCQXdNQkRFeE1EQXhEekFOQmdOVkJCb01CbEpKV1VGRVNERVdNQlFHQTFVRUR3d05UVUZPVlVaQlExUlZVa2xPUnpBS0JnZ3Foa2pPUFFRREFnTkhBREJFQWlBOTNjMWV3UkJNVlBpYkNoUWVnbUdqTWdIMUFEeVJIb0JtaGhBQzJuZ3RoZ0lnSzVaeFFRdEZETmZ2Y0Yzc0RSMFcvZHIzWVRaMmNLYmhGNUVLSEY3ZVVxWT0=";
//		String cert = "TUlJQ01EQ0NBZGFnQXdJQkFnSUdBWk0vNDBZTU1Bb0dDQ3FHU000OUJBTUNNQlV4RXpBUkJnTlZCQU1NQ21WSmJuWnZhV05wYm1jd0hoY05NalF4TVRFNE1UVXlOelUwV2hjTk1qa3hNVEUzTWpFd01EQXdXakJuTVFzd0NRWURWUVFHRXdKVFFURVdNQlFHQTFVRUN3d05VbWw1WVdSb0lFSnlZVzVqYURFWU1CWUdBMVVFQ2d3UFFrVlVRU0JKVGtSVlUxUlNTVVZUTVNZd0pBWURWUVFEREIxQlJFVk5VRWxGVWtVdE1UQXhMVE14TWpNME5UWTNPRGt3TURBd016QldNQkFHQnlxR1NNNDlBZ0VHQlN1QkJBQUtBMElBQkdYcmNJbzFjaVZoaUtlUlE5ZTk3SURVeVhNSGpCRXZ2NGxTamxkNHErWHdRVzQ1d0pHWDdCR1R4bWZxVytibFR3S1ZuYm1GTk9iN1RJaE00a3R4WEF5amdjSXdnYjh3REFZRFZSMFRBUUgvQkFJd0FEQ0JyZ1lEVlIwUkJJR21NSUdqcElHZ01JR2RNVUl3UUFZRFZRUUVERGt4TFVGRVJVMVFTVVZTUlh3eUxUSXdNalI4TXkxbFpESXlaakZrT0MxbE5tRXlMVEV4TVRndE9XSTFPQzFrT1dFNFpqRXhaVFEwTldZeEh6QWRCZ29Ka2lhSmsvSXNaQUVCREE4ek1USXpORFUyTnpnNU1EQXdNRE14RFRBTEJnTlZCQXdNQkRFeE1EQXhEekFOQmdOVkJCb01CbEpKV1VGRVNERVdNQlFHQTFVRUR3d05UVUZPVlVaQlExUlZVa2xPUnpBS0JnZ3Foa2pPUFFRREFnTklBREJGQWlFQTIwV2dIYVMwZEZXd1Z3OU5pYnZjL3FsL0tqcDVvSDk0UGhDekprTXlQWXNDSUVxb1BzUXFzTk1oTTRRQ2dIUnNBblJCQXN6MW11VnVaQTh1MFg5cEJucjg=";
		String cert = "MIICKDCCAc2gAwIBAgIGAZOKnjBMMAoGCCqGSM49BAMCMBUxEzARBgNVBAMMCmVJbnZvaWNpbmcwHhcNMjQxMjAzMDM0MzU4WhcNMjkxMjAyMjEwMDAwWjBcMQswCQYDVQQGEwJTQTEWMBQGA1UECwwNUml5YWRoIEJyYW5jaDEgMB4GA1UECgwXQkVUQSBJTkRVU1RSSUVTIENPTVBBTlkxEzARBgNVBAMMCkFERi1SSVlBREgwVjAQBgcqhkjOPQIBBgUrgQQACgNCAASMvPTz++76AXKrBlHUQwOTHOwXLBL8sS5zOQr7VKrIGGrCt+YUFGGFn02PPnngCS13a0/y5P3N9/pfHHxI33Gjo4HEMIHBMAwGA1UdEwEB/wQCMAAwgbAGA1UdEQSBqDCBpaSBojCBnzFCMEAGA1UEBAw5MS1BREVNUElFUkV8Mi1CRVRBfDMtZWQyMmYxZDgtZTZhMi0xMTE4LTliNTgtZDlhOGYxMWU0NDVmMR8wHQYKCZImiZPyLGQBAQwPMzk5OTk5OTk5OTAwMDAzMQ0wCwYDVQQMDAQxMTAwMREwDwYDVQQaDAhSRVJBMTIzNDEWMBQGA1UEDwwNTWFudWZhY3R1cmluZzAKBggqhkjOPQQDAgNJADBGAiEA0mZKaRp1oAUQkETr0GIaz+0lIxBGHcCR0he9W6Thnm4CIQDDO3i8l6vsXAx8HYFXvEiHIbkkbIC+zV5t/yFEdMYI3g==";
//		byte[] cert2 = Base64.getDecoder().decode(cert.getBytes());
		byte[] cert2 = cert.getBytes();
		X509Certificate decoded = DigitalSignatureHelper.decode(new String(cert2));
		System.out.println(decoded);
		
		System.out.println("Signature: " + QRUtil.convertToHexString(decoded.getSignature()));
		
		System.out.println("PublicKey: " + QRUtil.convertToHexString(decoded.getPublicKey().getEncoded()));
		System.out.println("PublicKey: " + Base64.getEncoder().encodeToString(decoded.getPublicKey().getEncoded()));
	}
	
	
	public void testGetPublicKey() throws Exception {		
		String privateKeyEncoded = "MIGNAgEAMBAGByqGSM49AgEGBSuBBAAKBHYwdAIBAQQgQQAi3mxIxffMKt9+4YvRJ8qaI0VTP0JcAJTw+6AHrZKgBwYFK4EEAAqhRANCAARl63CKNXIlYYinkUPXveyA1MlzB4wRL7+JUo5XeKvl8EFuOcCRl+wRk8Zn6lvm5U8ClZ25hTTm+0yITOJLcVwM";
		
		PrivateKey privateKey = DigitalSignatureHelper.getPrivateKey(privateKeyEncoded);
		System.out.println(privateKey);
		PublicKey publicKey = DigitalSignatureHelper.getPublicKeyFromPrivateKey(privateKey);
	
		System.out.println("hex: " + QRUtil.convertToHexString(publicKey.getEncoded()));
		System.out.println("base64: " + Base64.getEncoder().encodeToString(publicKey.getEncoded()));
		
//		byte[] publicKeyBin = DigitalSignatureHelper.getCompressedPublicKey(publicKey);
		byte[] publicKeyBin = ZatcaSDKProcessHelper.getCompressedPublicKey(privateKeyEncoded);
		System.out.println("Compressed: " + QRUtil.convertToHexString(publicKeyBin));
	    String publicKeyEncoded = Base64.getEncoder().encodeToString(publicKeyBin);		
	    System.out.println("Compressed base64: " +publicKeyEncoded);
		
	    String toolPublicKey = "MDYwEAYHKoZIzj0CAQYFK4EEAAoDIgACZetwijVyJWGIp5FD173sgNTJcweMES+/iVKOV3ir5fA=";
	    System.out.println("Using Tool: " +  QRUtil.convertToHexString(Base64.getDecoder().decode(toolPublicKey)));
	   
	    
//	    "                                                65eb708a3572256188a79143d7bdec80d4c973078c112fbf89528e5778abe5f0"
//	    "3036301006072a8648ce3d020106052b8104000a0322000265eb708a3572256188a79143d7bdec80d4c973078c112fbf89528e5778abe5f0"
	}
	
	public void testDecodePublicKey() throws Exception {		
		String publicKeyHex = "3056301006072a8648ce3d020106052b8104000a03420004d372d5656e4f50f545f7d2a371b6602139b7469a6c5a09868d382288021f6cf6085527348ad58c9fb5c4e93bbdbd8ace1565c78186f1f5c2aaa304ef3091eb19";
		byte[] publicKey = QRUtil.convertHexStringToBinary(publicKeyHex);
		String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey);
		System.out.println("Public Key = " + publicKeyBase64);
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
