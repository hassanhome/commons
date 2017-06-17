package hesheng.test.commons.crypto;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import hesheng.commons.crypto.Signaturer;

public class SignatureTest {
	
	@Test
	public void test2(){
		try {
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			InputStream stream = this.getClass().getResourceAsStream("/store/mykey.keystore");
			char[] password = "store123".toCharArray();
			keystore.load(stream, password );  
			PrivateKey privateKey = (PrivateKey)keystore.getKey("hassan", "key123".toCharArray());
			PublicKey publicKey = (PublicKey)keystore.getCertificate("hassan").getPublicKey();
			Signaturer sign = new Signaturer("MD5withRSA", privateKey, null);
			Signaturer veritySign = new Signaturer("MD5withRSA", null , publicKey);
			String signed = sign.signToBase64String("你好呀", StandardCharsets.UTF_8);
			boolean result = veritySign.verity("你好呀", StandardCharsets.UTF_8, signed);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test1(){
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DSA");
			keyPairGenerator.initialize(1024);
			KeyPair keyPair =  keyPairGenerator.generateKeyPair();
			PrivateKey privateKey = keyPair.getPrivate();
			PublicKey publicKey = keyPair.getPublic();
			Signaturer sign = new Signaturer("DSA" ,privateKey, publicKey);
			String signed = sign.signToBase64String("你好呀", StandardCharsets.UTF_8);
			boolean result = sign.verity("你好呀", StandardCharsets.UTF_8, signed);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
