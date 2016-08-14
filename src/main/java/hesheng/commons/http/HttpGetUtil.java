package hesheng.commons.http;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpGetUtil {
	
	private static final String DEFAULT_ENCODING = "UTF-8";
	

	public static  HttpResponse getResponseByGet(String url, Map<String,String> params) throws URISyntaxException, ClientProtocolException, IOException{
		return getResponseByGet(url, params, DEFAULT_ENCODING);
	}

	public static  HttpResponse getResponseByGet(String url, Map<String,String> params, String encoding) throws URISyntaxException, ClientProtocolException, IOException{
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			URIBuilder uriBuilder = new URIBuilder(url);
			if (null != params){
				for ( Entry<String, String> entry : params.entrySet()){
					uriBuilder.setParameter(entry.getKey(), entry.getValue());
				}
			}

			if (null != encoding){
				uriBuilder.setCharset(Charset.forName(encoding));
			}
			HttpGet httpGet = new HttpGet(uriBuilder.build());
			httpClient = HttpClients.createDefault();
			response = httpClient.execute(httpGet);
			
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
	
	public static String get(String url, Map<String,String> params) throws ClientProtocolException, URISyntaxException, IOException{
		return get(url, params, DEFAULT_ENCODING);
	}
	
	public static String get(String url, Map<String,String> params, String encoding) throws ClientProtocolException, URISyntaxException, IOException{
		HttpResponse httpResponse = getResponseByGet(url, params, encoding);
		return httpResponse.getBody();
	}
}
