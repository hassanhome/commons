package hesheng.test.commons.crypto;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.Test;

import hesheng.commons.crypto.RSACoder;

public class RSATest {
	
	@Test
	public void test2(){
		try {
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			InputStream stream = this.getClass().getResourceAsStream("/store/mykey.keystore");
			char[] password = "store123".toCharArray();
			keystore.load(stream, password );  
			PrivateKey privateKey = (PrivateKey)keystore.getKey("hassan", "key123".toCharArray()); 
			RSACoder coder = new RSACoder("RSA/ECB/PKCS1Padding", privateKey, null);
			String descrypted = coder.descrypt("HFXHepENJ3qT0Xwi1fWDfBIo5fJabCDAWTZkO1OQtYpOD0IiQXgFw4HQRzfnFSEZFsktJhijFGQ58wu9DodXFKwMYvV4IdY5FIsC02KA5MgBppGrlpUADILo2Kcu3zTeWD7oOZSf9CHGDQDwa8fLnkfaYXs+ycHub+BujrjJhnQ=", StandardCharsets.UTF_8);
			System.out.println(descrypted);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	@Test
	public void test1(){
		try {
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
			InputStream stream = this.getClass().getResourceAsStream("/store/mykey.keystore");
			char[] password = "store123".toCharArray();
			keystore.load(stream, password );   
			PublicKey publicKey = (PublicKey)keystore.getCertificate("hassan").getPublicKey();
			RSACoder coder = new RSACoder("RSA/ECB/PKCS1Padding", null, publicKey);
			String encrytped = coder.encrypt("你好呀", StandardCharsets.UTF_8);
			System.out.println(encrytped);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
