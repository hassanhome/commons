package hesheng.commons.http;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpUtils {

	public void getHttpConnManager(){
		HttpClientConnectionManager cm = new  PoolingHttpClientConnectionManager(); 
	}
}
