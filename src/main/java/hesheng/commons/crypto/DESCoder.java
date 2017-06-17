package hesheng.commons.crypto;

import java.security.NoSuchAlgorithmException;

public class DESCoder extends SymmetricCoder {
	
	
	public DESCoder(String keyAlgorithm,String cipherAlgorithm,int keySize) throws NoSuchAlgorithmException   { 
		this(keyAlgorithm,cipherAlgorithm, keySize, null);  
	}
	
	public DESCoder(String keyAlgorithm, String cipherAlgorithm, String key) throws NoSuchAlgorithmException  {
		this(keyAlgorithm,cipherAlgorithm, key, null); 
	}
	
	public DESCoder(String keyAlgorithm, String cipherAlgorithm,int keySize,String iv) throws NoSuchAlgorithmException  { 
		super(keyAlgorithm, cipherAlgorithm, keySize, iv); 
	}
	
	public DESCoder(String keyAlgorithm, String cipherAlgorithm, String key, String iv) throws NoSuchAlgorithmException  {
		super(keyAlgorithm,cipherAlgorithm, key, iv); 
	}
	
}
