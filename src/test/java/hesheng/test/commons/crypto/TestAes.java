package hesheng.test.commons.crypto;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.cert.CertPathParameters;
import java.util.Map.Entry;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.TrustManagerFactorySpi;
import javax.net.ssl.X509ExtendedTrustManager;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64Encoder;
import org.junit.Test;

import hesheng.commons.crypto.AESCoder;

public class TestAes {
	
	@Test
	public void test6(){
		try {

			TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			System.out.println(TrustManagerFactory.getDefaultAlgorithm());
			TrustManager[] trusts = factory.getTrustManagers();
			
			System.out.println(trusts.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test5(){
		try {
			Security.addProvider(new BouncyCastleProvider());
			MessageDigest md = MessageDigest.getInstance("MD4" );
			md.update("你好".getBytes(StandardCharsets.UTF_8));
			byte[] t = md.digest();
			System.out.println(Base64.encodeBase64String(t));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void test4(){

		Security.addProvider(new BouncyCastleProvider());
//
		Security.addProvider(new BouncyCastleProvider());
		for ( Provider provider:   Security.getProviders()){
			if (!provider.toString().startsWith("BC version")){
				
				continue;
			}
			System.out.println("++++++++++++++++++++++++++");
			System.out.println("【提供者】"+provider);
			System.out.println("------------");
			for (Entry<Object, Object> enrty  : provider.entrySet()){
				System.out.println(enrty.getKey());
			}
			System.out.println("++++++++++++++++++++++++++");
		}
	}

	@Test
	public void test1() throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		AESCoder coder = new AESCoder("AES/CBC/ISO10126Padding", 128);
		String encrypted = coder.encrypt("你好呀", StandardCharsets.UTF_8);
		System.out.println(encrypted);
		String decrypted = coder.decrpty(encrypted, StandardCharsets.UTF_8);
		System.out.println(decrypted); 
		System.out.println(coder.getBase64EncodedKey());
	}
	
	@Test
	public void test2() throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		AESCoder coder = new AESCoder("AES/CBC/ISO10126Padding", 128,"q7qWjSmhUr79pyf0PvjjTQ==","1234567890123456");
		String encrypted = coder.encrypt("你好呀", StandardCharsets.UTF_8);
		System.out.println(encrypted);
		String decrypted = coder.decrpty("OJlyBY+eo3C7hkcIttGVHw==", StandardCharsets.UTF_8);
		System.out.println(decrypted);
	}
}
