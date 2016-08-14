package hesheng.commons.http;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

public class HttpUtilsTest {

	@Test
	public void getStringStringTest1() throws ClientProtocolException, URISyntaxException, IOException{
		String result = HttpUtils.get("https://github.com/", null );
		System.out.println(result);
	}
	
	
	@Test
	public void getResponseTest1() throws ClientProtocolException, URISyntaxException, IOException{
		HttpResponse resp = HttpUtils.getResponseByGet("https://github.com/", null );
		System.out.println(resp.getStatus());
		for (Header header : resp.getHeader()){
			System.out.println(header);
		}
	}
	
	@Test
	public void getResponsebyPostTest1() throws ClientProtocolException, URISyntaxException, IOException{
		String url = "http://localhost:8080/activiti_jpa/person/save.do";
		Map<String, String> params = new HashMap<>();
		String encoding = "UTF-8";
		
		params.put("name", "小化");
		params.put("age", "21");
		HttpResponse resp = HttpUtils.getResponseByPost(url, params, encoding );
		System.out.println(resp.getStatus());
	}
}
