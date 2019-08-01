package hesheng.commons.crypto;

import java.security.NoSuchAlgorithmException;

public class DESCoder extends SymmetricCoder {
	
	
	public DESCoder(String keyAlgorithm,String cipherAlgorithm,int keySize) throws NoSuchAlgorithmException   { 
		this(keyAlgorithm,cipherAlgorithm, keySize, null);  
	}
	
	public DESCoder(String keyAlgorithm, String cipherAlgorithm, String key) {
		this(keyAlgorithm,cipherAlgorithm, key, null); 
	}
	
	private DESCoder(String keyAlgorithm, String cipherAlgorithm, int keySize, String iv) throws NoSuchAlgorithmException  {
		super(keyAlgorithm, cipherAlgorithm, keySize, iv); 
	}
	
	private DESCoder(String keyAlgorithm, String cipherAlgorithm, String key, String iv) {
		super(keyAlgorithm,cipherAlgorithm, key, iv); 
	}
	
}
