package hesheng.test.commons.http;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import hesheng.commons.http.HttpUtils;

public class TestHttp {
	
	@Test
	public void test8(){ 
		try {
			System.out.println(HttpUtils.post("https://hassan:8080/swallow/user/testconn2.do", "这是发送的请求体" , StandardCharsets.UTF_8));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
 
	
	@Test
	public void test7(){  
		try {
			System.out.println(HttpUtils.post("https://www.baidu.com/", "" , StandardCharsets.UTF_8));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test6(){
		Map<String, String> param = new HashMap<>();
		param.put("user", "小明");
		param.put("password", "这是密码" );
		try {
			System.out.println(HttpUtils.post("https://hassan:8080/swallow/user/testconn.do", param , StandardCharsets.UTF_8));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
 
 


	
	
	 
}
