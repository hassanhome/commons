package hesheng.commons.crypto;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class AESCoder {
	public static final String KEY_ALGORITHM = "AES";
	
 
	private int keySize;
	
	/**
	 * 默认1234567890123456
	 */
	private String cipherAlgorithm ;
	
	
	byte[] key;
	 
	IvParameterSpec ips = null;
	
	public AESCoder(String cipherAlgorithm,int keySize) throws NoSuchAlgorithmException{ 
		this.cipherAlgorithm = cipherAlgorithm;
		this.keySize = keySize;
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		kg.init(this.keySize);
		SecretKey secretKey = kg.generateKey();
		this.key = secretKey.getEncoded(); 
		this.ips = new IvParameterSpec("1234567890123456".getBytes(StandardCharsets.UTF_8));
	}
	
	public AESCoder(String cipherAlgorithm,int keySize, String key, String iv) throws NoSuchAlgorithmException{
		this.cipherAlgorithm = cipherAlgorithm;
		this.keySize = keySize;
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		kg.init(this.keySize); 
		this.key =Base64.decodeBase64(key);
		if (null != iv && iv.getBytes(StandardCharsets.UTF_8).length == 16){
			this.ips = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
		}else{
			this.ips = new IvParameterSpec("1234567890123456".getBytes(StandardCharsets.UTF_8));
		} 
	}
	
	
	public String decrpty(String data, String charsetStr) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException{
		Charset charset = Charset.forName(charsetStr);
		return this.decrpty(data, charset);
	}
	
	public String decrpty(String data, Charset charset) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		byte[] inData = Base64.decodeBase64(data);
		Cipher cipher = Cipher.getInstance(this.cipherAlgorithm);
		Key k = toKey();
		cipher.init(Cipher.DECRYPT_MODE, k , ips);
		byte[] outData = cipher.doFinal(inData);
		return new String(outData,charset);
	}
	
	public String encrypt(String data, String charsetStr) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
		Charset charset = Charset.forName(charsetStr);
		return this.encrypt(data, charset);
	}
	
	public String encrypt(String data, Charset charset) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException  {
		Key k = toKey();
		Cipher cipher = Cipher.getInstance(this.cipherAlgorithm);
		cipher.init(Cipher.ENCRYPT_MODE, k, ips);
		byte[] inputData = data.getBytes(charset); 
		byte[] res = cipher.doFinal(inputData );
		return Base64.encodeBase64String(res);
	}
	
	public String getBase64EncodedKey(){
		return Base64.encodeBase64String(this.key);
	}


	private Key toKey() {
		return new SecretKeySpec(this.key, KEY_ALGORITHM);
	}
	 
}
