package hesheng.test.commons.crypto;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import hesheng.commons.crypto.SymmetricCoder;

public class SymmetricCoderTest {
	
	@Test
	public void test2(){
		try {
			Security.addProvider(new BouncyCastleProvider());
			SymmetricCoder coder = new SymmetricCoder("DESede","DESede/ECB/PKCS5Padding", "2l0mXSle+PdhsIDs4+wfj1SGDaKSJiX3", null);
			System.out.println(coder.decrpty("buf5dxy8bS4=", StandardCharsets.UTF_8));
			System.out.println(coder.getSecretKey().getAlgorithm());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test1(){
		try {
			Security.addProvider(new BouncyCastleProvider());
			SymmetricCoder coder = new SymmetricCoder("DESede", "DESede/ECB/PKCS5Padding", 168, null);
			System.out.println(coder.encrypt("你好", StandardCharsets.UTF_8)); 
			System.out.println(Base64.encodeBase64String(coder.getSecretKey().getEncoded()));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
