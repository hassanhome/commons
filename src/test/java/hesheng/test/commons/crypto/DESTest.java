package hesheng.test.commons.crypto;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import hesheng.commons.crypto.DESCoder;

public class DESTest {
	
	
	@Test
	public void test2(){
		try {
			Security.addProvider(new BouncyCastleProvider());
			DESCoder coder = new DESCoder("DESede","DESede/ECB/PKCS5Padding", "VLXCzaHTT4pzN+lJeQTTQLZ25hrg1tkO" );
			System.out.println(coder.decrpty("rjHzgwZwmZp1Sp3shzni0w==", StandardCharsets.UTF_8));
			System.out.println(coder.getBase64EncodedKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test1(){
		try {
			Security.addProvider(new BouncyCastleProvider());
			DESCoder coder = new DESCoder("DESede","DESede/ECB/PKCS5Padding", 168);
			System.out.println(coder.encrypt("你好呀", StandardCharsets.UTF_8));
			System.out.println(coder.getBase64EncodedKey());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
