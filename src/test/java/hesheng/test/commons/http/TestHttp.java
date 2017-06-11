package hesheng.test.commons.http;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpException;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import hesheng.commons.http.MyHttpConfig;

public class TestHttp {
	
	@Test
	public void test4() {
		Long t = System.currentTimeMillis(); 
		
		HttpUriRequest request = RequestBuilder.post("http://localhost:8080/spmv/user/sayhello.do")
	              .addParameter("name", "小明")
	              .addParameter("password", "这是密码" )
	              .setCharset(StandardCharsets.UTF_8) 
	              .setConfig(MyHttpConfig.getRequestConfig() ) 
	              .build();
	
	  
		CloseableHttpResponse resp = null;
		try {
			resp = HttpClients.custom()
					          .setRetryHandler(MyHttpConfig.getHttpRequestRetryHandler())
					          .setConnectionManager(MyHttpConfig.getPoolingHttpClientConnectionManager())
					          .build()
					          .execute(request);
			System.out.println(EntityUtils.toString(resp.getEntity(), StandardCharsets.UTF_8));
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) { 
			e.printStackTrace();
		} finally {
			Long use = (System.currentTimeMillis()-t) ;
			
			System.out.println("耗时"+use+"毫秒");
			IOUtils.closeQuietly(resp);
		}
	}


	
	
	 
}
