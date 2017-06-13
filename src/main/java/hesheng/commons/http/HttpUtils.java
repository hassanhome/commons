package hesheng.commons.http;

import java.io.FileInputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.KeyStore.TrustedCertificateEntry;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.swing.KeyStroke;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtils {
	private static Logger logger = Logger.getLogger(HttpUtils.class);

	public static String post(String url, Map<String, String> param, Charset charset){
		Long t = System.currentTimeMillis(); 
		
		RequestBuilder requestBuilder = RequestBuilder.post(url) 
	              .setCharset(charset) 
	              .setConfig(MyHttpConfig.getRequestConfig() ) ;
		
		for (Map.Entry<String, String> item : param.entrySet()){
			requestBuilder.addParameter(item.getKey(), item.getValue());
		}
		
		HttpUriRequest request = requestBuilder.build(); 
	  
		CloseableHttpResponse resp = null;
		try {
			KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType()); 
			keystore.load(HttpUtils.class.getResourceAsStream("/store/mykey.keystore"), "store123".toCharArray());
			Certificate crt = keystore.getCertificate("hassan");
			
			
			
			 String javaHome = System.getProperty("java.home"); 
			 
			 String acPath = javaHome+"/lib/security/cacerts";
			 KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			 FileInputStream fin = new FileInputStream(acPath);
			 ks.load(fin, "changeit".toCharArray()); 
			 ks.setCertificateEntry("hesheng_hassan", crt);
			 
			SSLContext sslContext = SSLContexts.custom()
					                           .loadTrustMaterial(ks, null) 
//					                           .loadTrustMaterial(keystore)
					                           .build();
			 SSLContext sslnew = SSLContext.getInstance("SSL");
			 sslnew.init(null, null, new SecureRandom());
//			 TrustManagerFactory fac = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
//			 TrustManager[] tms = fac.getTrustManagers();
//			 System.out.println(tms.length);
			System.out.println(sslnew.getProtocol());  
//			 sslnew.init(null, null, new SecureRandom());
			resp = HttpClients.custom()
					          .setRetryHandler(MyHttpConfig.getHttpRequestRetryHandler()) 
					          .setSSLContext(sslContext ) 
//					          .setConnectionManager(MyHttpConfig.getPoolingHttpClientConnectionManager())
					          .build()
					          .execute(request);
			return EntityUtils.toString(resp.getEntity(), StandardCharsets.UTF_8);
		} catch (Exception e) { 
			logger.error("http请求"+url+"异常", e);
			e.printStackTrace();
		} finally {
			Long use = (System.currentTimeMillis()-t) ;
			
			System.out.println("耗时"+use+"毫秒"); 
			IOUtils.closeQuietly(resp);
		}
		
		return "error";
	}
}
