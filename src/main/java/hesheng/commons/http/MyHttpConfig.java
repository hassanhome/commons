package hesheng.commons.http;

import java.io.IOException;

import org.apache.http.HttpRequest;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;

/**
 * httpclient配置
 * @author admin
 *
 */
public class MyHttpConfig {
	
	private static Logger logger = Logger.getLogger(MyHttpConfig.class);
	
	private static RequestConfig requestConfig = null;
	private static PoolingHttpClientConnectionManager pool = null;
	private static HttpRequestRetryHandler retryHandler = null;
	
	/**
	 * 获取定制的重试策略
	 * @return
	 */
	public static HttpRequestRetryHandler getHttpRequestRetryHandler(){
		if (null != retryHandler){
			return retryHandler;
		}
		synchronized (MyHttpConfig.class) {
			if (null != retryHandler){
				return retryHandler;
			}
			
			retryHandler = new HttpRequestRetryHandler() {
				
				@Override
				public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
					
					HttpClientContext clientContext = HttpClientContext.adapt(context);
					HttpRequest request = clientContext.getRequest();

					System.out.println("重新连接ing"+executionCount+"次");
					//最多重试5次
					if (executionCount >= 5) { 
						logger.info("重新连接"+executionCount+"次");
						System.out.println("重新连接"+executionCount+"次");
						return false;
					}
					
					return true;
					//超时
//					if (exception instanceof InterruptedIOException) { 
//						return false;
//					}
//					if (exception instanceof UnknownHostException) {
//						// Unknown host
//						return false;
//					}
//					if (exception instanceof ConnectTimeoutException) { 
//						return false;
//					}
//					if (exception instanceof SSLException) {
//						// SSL handshake exception
//						return false;
//					}
//					HttpClientContext clientContext = HttpClientContext.adapt(context);
//					HttpRequest request = clientContext.getRequest();
//					boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
//					if (idempotent) {
//						// Retry if the request is considered idempotent
//						return true;
//					}
//					return false;
				}
			};
			return retryHandler;
		}
	}

	/**
	 * 获取定制的连接配置
	 * @return
	 */
	public static RequestConfig getRequestConfig(){
		
		if (null != requestConfig){
			return requestConfig;
		} 
		
		
		synchronized (MyHttpConfig.class) {
			if (null != requestConfig){
				return requestConfig;
			}
			
			requestConfig = RequestConfig.custom()
                    .setConnectTimeout(50000)
                    .setConnectionRequestTimeout(10000)
                    .setSocketTimeout(5000) 
                    .build();
			return requestConfig;
		}
		
	}
	
	/**
	 * 获取定制的连接池
	 * @return
	 */
	public static PoolingHttpClientConnectionManager getPoolingHttpClientConnectionManager(){
		
		if (null != pool){
			return pool;
		}
		 
		synchronized (MyHttpConfig.class) {
			//等到获取锁后，再进来时，可能已经初始化了
			if (null != pool){
				return pool;
			}
			pool = new PoolingHttpClientConnectionManager();
			pool.setMaxTotal(200);
			pool.setDefaultMaxPerRoute(20);  
			return pool;
		}
	}
}
