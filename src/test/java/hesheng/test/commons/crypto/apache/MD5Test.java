package hesheng.test.commons.crypto.apache;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

public class MD5Test {

	@Test
	public void test1(){
		String source = "你好呀";
		String dig = DigestUtils.md5Hex(source);
		System.out.println("MD5="+dig);
		
		String sha = DigestUtils.sha384Hex(source);
		System.out.println(sha);
		
	}
}
