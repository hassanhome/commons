package hesheng.commons.crypto;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

import javax.crypto.spec.IvParameterSpec;

public class AESCoder extends SymmetricCoder {
	private static final String KEY_ALGORITHM = "AES";
	
	/**
	 * iv 默认是 1234567890123456
	 * @param cipherAlgorithm
	 * @param keySize
	 * @throws NoSuchAlgorithmException
	 */
	public AESCoder(String cipherAlgorithm,int keySize,String iv) throws NoSuchAlgorithmException  { 
		super(KEY_ALGORITHM, cipherAlgorithm, keySize, iv); 
		super.setIps(new IvParameterSpec("1234567890123456".getBytes(StandardCharsets.UTF_8)));
	}
	
	public AESCoder(String cipherAlgorithm, String key, String iv) throws NoSuchAlgorithmException  {
		super(KEY_ALGORITHM,cipherAlgorithm, key, iv); 
	}
	
}
