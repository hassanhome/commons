package hesheng.test.commons.crypto;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Test;

import hesheng.commons.crypto.AESCoder;

public class TestAes {

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
