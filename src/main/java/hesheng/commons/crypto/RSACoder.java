package hesheng.commons.crypto;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

public class RSACoder {
	
	private PrivateKey privateKey;
	
	private PublicKey publicKey;
	
	private String cipherAlgorithm;
	
	
	public RSACoder(String cipherAlgorithm, PrivateKey privateKey, PublicKey publicKey){
		this.cipherAlgorithm = cipherAlgorithm;
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	public byte[] descrypt(byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}
	
	
	public String descrypt(String data, Charset charset) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		byte[] descryptByteArray = descrypt(Base64.decodeBase64(data));
		return new String(descryptByteArray, charset);
	}
	
	public byte[] encrypt(byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}
	
	public String encrypt(String data, Charset charset) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		byte[] toEncryptArray = data.getBytes(charset);
		byte[] encryptedArray = encrypt(toEncryptArray);
		return Base64.encodeBase64String(encryptedArray);
	}
}
