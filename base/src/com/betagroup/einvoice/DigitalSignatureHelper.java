package com.betagroup.einvoice;

import java.io.ByteArrayInputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.math.ec.ECPoint;

public class DigitalSignatureHelper {

	public static String base64Encode(String data) {
        Encoder encoder = Base64.getEncoder(); 
		return encoder.encodeToString(data.getBytes());
	}
	

	public static PrivateKey getPrivateKey(String encodedKey) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		// TODO Save encrypted key in DB and decode it here
		// Sign the Invoice hash using EDCSA
		String privateKeyPEM = encodedKey.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----BEGIN EC PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "").replace("-----END EC PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");
		byte[] keyBytes = Base64.getDecoder().decode(privateKeyPEM);
        // Step 3: Create a PKCS8EncodedKeySpec from the byte array
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        // Step 4: Use KeyFactory to generate the PrivateKey object
        KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC"); // Use "EC" for ECDSA, BC for BouncyCastle
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME); //"BC"
		return privateKey;
	}
	
	public static X509Certificate decode(String x509Certificate) throws Exception {
		String pemFormattedCertificate = "-----BEGIN CERTIFICATE-----\n" +
                x509Certificate + "\n" +
                "-----END CERTIFICATE-----";
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(pemFormattedCertificate.getBytes())) {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
        	X509Certificate cert = (X509Certificate) cf.generateCertificate(inputStream);
        	return cert;
        }		
	}
	
	public static KeyPair generateKeyPair() throws Exception {
		Security.addProvider(new BouncyCastleProvider());
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC"); // Use "EC" for ECDSA, BC for BouncyCastle
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
        keyPairGenerator.initialize(ecSpec, new SecureRandom());

        // Generate the key pair
        KeyPair keyPair = keyPairGenerator.generateKeyPair();    

        // Extract the private and public keys
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
//        System.out.println("Private Key: " + privateKey);
//        System.out.println("Public Key: " + publicKey);
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME); //"BC"

        return new KeyPair(publicKey, privateKey);
	}
	
	public static PublicKey getPublicKeyFromPrivateKey(PrivateKey privateKey) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
        // Step 4: Use KeyFactory to generate the PrivateKey object
        KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC"); // Use "EC" for ECDSA, BC for BouncyCastle
        // Derive the public key
        ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("secp256k1");
        ECPoint Q = ecSpec.getG().multiply(((org.bouncycastle.jce.interfaces.ECPrivateKey) privateKey).getD());

        ECPublicKeySpec pubSpec = new ECPublicKeySpec(Q, ecSpec);
        PublicKey publicKeyGenerated = keyFactory.generatePublic(pubSpec);
        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME); //"BC"
        System.out.println("Derived Public Key: " + publicKeyGenerated);
        return publicKeyGenerated;
	}
	
//    public static byte[] getCompressedPublicKey(PublicKey publicKey) throws Exception {
//		Security.addProvider(new BouncyCastleProvider());
//        KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC"); // Use "EC" for ECDSA, BC for BouncyCastle
//
//        ECPublicKey ecPublicKey = (ECPublicKey) publicKey;
////        ECPoint point = ecPublicKey.getW();
//
//        // Get the parameters of the curve
//
//        java.security.spec.ECParameterSpec ecSpec = ecPublicKey.getParams();
//
//        // Create the ASN.1 structure
//        ASN1ObjectIdentifier oid = new ASN1ObjectIdentifier("1.2.840.10045.3.1.7"); // OID for secp256k1
//        DERBitString pubKeyBitString = new DERBitString(publicKey.getEncoded());
//
//        // Create the SubjectPublicKeyInfo structure
//
//        SubjectPublicKeyInfo subjectPublicKeyInfo = new SubjectPublicKeyInfo(
//                new AlgorithmIdentifier(oid, null),
//                pubKeyBitString
//        );
//
//
//        // Encode to DER
//
//        byte[] encoded = subjectPublicKeyInfo.getEncoded();
//        return encoded;
//        
////        // Cast to ECPublicKey
////        ECPublicKeySpec pubKeySpec = (ECPublicKeySpec) keyFactory.getKeySpec(publicKey, ECPublicKeySpec.class);
////        ECPoint point = pubKeySpec.getQ();
////
////        // Get the x and y coordinates
////        BigInteger x = point.getXCoord().toBigInteger();
////        BigInteger y = point.getYCoord().toBigInteger();
//        
//        
////
////        // Determine if y is even or odd
////        byte[] xBytes = x.toByteArray();
////        byte[] compressedKey = new byte[xBytes.length + 1];
////        compressedKey[0] = (byte) (y.testBit(0) ? 0x03 : 0x02); // 0x02 for even, 0x03 for odd
////        System.arraycopy(xBytes, 0, compressedKey, 1, xBytes.length);
////        Security.removeProvider(BouncyCastleProvider.PROVIDER_NAME); //"BC"
////        return compressedKey;
//
//    }
	
}
