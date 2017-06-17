package hesheng.commons.crypto;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

import org.apache.commons.codec.binary.Base64;

public class Signaturer {
	
	private Signature signature;
	private PrivateKey privateKey;
	private PublicKey publicKey;
	private String algorithm;
	
	public Signaturer(String algorithm,PrivateKey privateKey, PublicKey publicKey) throws NoSuchAlgorithmException{
		this(null, algorithm, privateKey, publicKey);
	}
	
	 
	public Signaturer(Provider provider,String algorithm,PrivateKey privateKey, PublicKey publicKey) throws NoSuchAlgorithmException{
		this.privateKey = privateKey;
		this.publicKey = publicKey; 
		this.algorithm = algorithm;
		if (null != provider   ){
			signature = Signature.getInstance(this.algorithm, provider);
		} else {
			signature = Signature.getInstance(this.algorithm);
		}
	}
	
 
	
	public String signToBase64String(String source,Charset charset) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException{
		return Base64.encodeBase64String(sign(source, charset));
	}

	public byte[] sign(String source,Charset charset) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
		byte[] data = source.getBytes(charset);
		signature.initSign(privateKey);
		signature.update(data);
		return signature.sign();
	}
	
	
	public boolean verity(String source,Charset charset, byte[] sign ) throws InvalidKeyException, SignatureException{
		byte[] data = source.getBytes(charset);
		signature.initVerify(publicKey);
		signature.update(data );
		return signature.verify(sign);
	}
	
	public boolean verity(String source,Charset charset, String signEncodeByBase64 ) throws InvalidKeyException, SignatureException{
		byte[] sign = Base64.decodeBase64(signEncodeByBase64);
		return verity(source, charset, sign);
	}
}
