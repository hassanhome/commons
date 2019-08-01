package hesheng.commons.crypto;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
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
	/**
	 * 密钥加密算法
	 */
	private String keyAlgorithm;
 
	/**
	 * ciper加密算法
	 */
	private String cipherAlgorithm ;
	
	/**
	 * 密钥
	 */
	private SecretKey secretKey;
	 
	/**
	 * iv
	 */
	private IvParameterSpec ips ;
	
	public SymmetricCoder(String keyAlgorithm, String cipherAlgorithm,int keySize,String iv) throws NoSuchAlgorithmException{ 
		this.keyAlgorithm = keyAlgorithm;
		this.cipherAlgorithm = cipherAlgorithm; 
		KeyGenerator kg = KeyGenerator.getInstance(keyAlgorithm);
		kg.init(keySize);
		this.secretKey = kg.generateKey();  
		if (null != iv){
			this.ips = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
		}
	}
	
	public SymmetricCoder(String keyAlgorithm,String cipherAlgorithm, String key, String iv) {
		this.keyAlgorithm = keyAlgorithm;
		this.cipherAlgorithm = cipherAlgorithm;  
		this.secretKey = toKey(Base64.decodeBase64(key), keyAlgorithm); 
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
		cipher.init(Cipher.DECRYPT_MODE, this.secretKey , ips);
		byte[] outData = cipher.doFinal(inData);
		return new String(outData,charset);
	}
	
	public String encrypt(String data, String charsetStr) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException{
		Charset charset = Charset.forName(charsetStr);
		return this.encrypt(data, charset);
	}
	
	public String encrypt(String data, Charset charset) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException  {
		Cipher cipher = Cipher.getInstance(this.cipherAlgorithm);
		cipher.init(Cipher.ENCRYPT_MODE, this.secretKey, ips);
		byte[] inputData = data.getBytes(charset); 
		byte[] res = cipher.doFinal(inputData );
		return Base64.encodeBase64String(res);
	}
	
    public String getBase64EncodedKey(){
    	return Base64.encodeBase64String(this.secretKey.getEncoded());
    }


	private SecretKey toKey(byte[] keyByteArray, String keyAlgorithm) {
		return new SecretKeySpec(keyByteArray, keyAlgorithm);
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


	public IvParameterSpec getIps() {
		return ips;
	}

	void setIps(IvParameterSpec ips) {
		this.ips = ips;
	}

	public SecretKey getSecretKey() {
		return secretKey;
	}


	
	
	 
}
