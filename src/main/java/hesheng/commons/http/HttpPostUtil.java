package hesheng.commons.http;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpPostUtil {
	public static HttpResponse getResponseByPost(String url, Map<String,String> params, String encoding) throws URISyntaxException, ClientProtocolException, IOException{
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> parameters = new ArrayList<>();
			if (null != params){
				for ( Entry<String, String> entry : params.entrySet()){
					NameValuePair nameValuePair = new BasicNameValuePair(entry.getKey(), entry.getValue());
					parameters.add(nameValuePair);
				}
			}
			HttpEntity entity = new UrlEncodedFormEntity(parameters, encoding);
			httpPost.setEntity(entity );
			
			httpClient = HttpClients.createDefault();
			response = httpClient.execute(httpPost);
			
			HttpResponse httpResponse = new HttpResponse();
			httpResponse.setStatus(response.getStatusLine());
			httpResponse.setHeader(response.getAllHeaders()); 
			httpResponse.setBody(EntityUtils.toString(response.getEntity(),encoding));
			return httpResponse;
		} finally {
			HttpClientUtils.closeQuietly(response);
			HttpClientUtils.closeQuietly(httpClient);
		}
	}
	
	
	public static HttpResponse getResponseByPost(String url, String jsonParams, String encoding) throws URISyntaxException, ClientProtocolException, IOException{
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			StringEntity entity = new StringEntity(null==jsonParams?"":jsonParams, encoding);
			entity.setContentType("application/json");
			entity.setContentEncoding(encoding);
			httpPost.setEntity(entity );
			
			httpClient = HttpClients.createDefault();
			response = httpClient.execute(httpPost);
			
			HttpResponse httpResponse = new HttpResponse();
			httpResponse.setStatus(response.getStatusLine());
			httpResponse.setHeader(response.getAllHeaders()); 
			httpResponse.setBody(EntityUtils.toString(response.getEntity(),encoding));
			return httpResponse;
		} finally {
			HttpClientUtils.closeQuietly(response);
			HttpClientUtils.closeQuietly(httpClient);
		}
	}
}
