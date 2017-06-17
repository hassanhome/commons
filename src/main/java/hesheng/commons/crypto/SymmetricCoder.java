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

/**
 * 对称加密器
 * @author admin
 *
 */
public class SymmetricCoder { 
	
	private String keyAlgorithm;
	
 
	private String cipherAlgorithm ;
	
	
	byte[] key;
	 
	IvParameterSpec ips = null;
	
	public SymmetricCoder(String keyAlgorithm, String cipherAlgorithm,int keySize,String iv) throws NoSuchAlgorithmException{ 
		this.keyAlgorithm = keyAlgorithm;
		this.cipherAlgorithm = cipherAlgorithm; 
		KeyGenerator kg = KeyGenerator.getInstance(keyAlgorithm);
		kg.init(keySize);
		SecretKey secretKey = kg.generateKey();
		this.key = secretKey.getEncoded(); 
		if (null != iv){
			this.ips = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
		}
	}
	
	public SymmetricCoder(String keyAlgorithm,String cipherAlgorithm, String key, String iv) throws NoSuchAlgorithmException{
		this.keyAlgorithm = keyAlgorithm;
		this.cipherAlgorithm = cipherAlgorithm; 
		this.key =Base64.decodeBase64(key); 
		if (null != iv){
			this.ips = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
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
		return new SecretKeySpec(this.key, this.keyAlgorithm);
	}

	public String getKeyAlgorithm() {
		return keyAlgorithm;
	}

	public void setKeyAlgorithm(String keyAlgorithm) {
		this.keyAlgorithm = keyAlgorithm;
	}

	public String getCipherAlgorithm() {
		return cipherAlgorithm;
	}

	public void setCipherAlgorithm(String cipherAlgorithm) {
		this.cipherAlgorithm = cipherAlgorithm;
	}

	public byte[] getKey() {
		return key;
	}

	public void setKey(byte[] key) {
		this.key = key;
	}

	public IvParameterSpec getIps() {
		return ips;
	}

	public void setIps(IvParameterSpec ips) {
		this.ips = ips;
	}
	
	
	
	 
}
