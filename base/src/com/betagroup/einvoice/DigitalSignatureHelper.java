package com.betagroup.einvoice;

import java.io.ByteArrayInputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECParameterSpec;
import org.bouncycastle.jce.spec.ECPublicKeySpec;
import org.bouncycastle.math.ec.ECPoint;

public class DigitalSignatureHelper {

	public static String base64Encode(String data) {
        Encoder encoder = Base64.getEncoder(); 
		return encoder.encodeToString(data.getBytes());
	}
	

	public static PrivateKey getPrivateKey(String encodedKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
		// TODO Save encrypted key in DB and decode it here
		// Sign the Invoice hash using EDCSA
		String privateKeyPEM = encodedKey.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");
		byte[] keyBytes = Base64.getDecoder().decode(privateKeyPEM);
        // Step 3: Create a PKCS8EncodedKeySpec from the byte array
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        // Step 4: Use KeyFactory to generate the PrivateKey object
        KeyFactory keyFactory = KeyFactory.getInstance("EC"); // Use "EC" for ECDSA
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
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
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
        keyPairGenerator.initialize(ecSpec, new SecureRandom());

        // Generate the key pair
        KeyPair keyPair = keyPairGenerator.generateKeyPair();    

        // Extract the private and public keys
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        System.out.println("Private Key: " + privateKey);
        System.out.println("Public Key: " + publicKey);
        return new KeyPair(publicKey, privateKey);
	}
	
	public static PublicKey getPublicKeyFromPrivateKey(byte[] privateKeyBytes) throws Exception {
        // Load the private key
		KeyFactory keyFactory = KeyFactory.getInstance("ECDSA", "BC");
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
        

        // Derive the public key
        ECParameterSpec ecSpec = ECNamedCurveTable.getParameterSpec("secp256k1");
        ECPoint Q = ecSpec.getG().multiply(((org.bouncycastle.jce.interfaces.ECPrivateKey) privateKey).getD());

        ECPublicKeySpec pubSpec = new ECPublicKeySpec(Q, ecSpec);
        PublicKey publicKeyGenerated = keyFactory.generatePublic(pubSpec);
        System.out.println("Derived Public Key: " + publicKeyGenerated);
        return publicKeyGenerated;
	}
	
}
