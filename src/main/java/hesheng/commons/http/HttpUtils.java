package hesheng.commons.http;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {
	private static Logger logger = Logger.getLogger(HttpUtils.class);
	
	private static KeyStore keystore = null;
	private static final String SSL = "SSL";

	private HttpUtils() {
	}

	private static KeyStore getTrustKeyStore() throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException{
		if (null != keystore){
			return keystore;
		}
		synchronized (HttpUtils.class) {
			if (null != keystore){
				return keystore;
			}
			
			 String javaHome = System.getProperty("java.home"); 
			 String acPath = javaHome+"/lib/security/cacerts";
			 File destFile = new File("hassan_tmp_trust_store.keystore");
			 File srcFile = new File(acPath);
			 FileUtils.deleteQuietly(destFile);
			 //复制ca证书
			 FileUtils.copyFile(srcFile, destFile );
			 

			try( FileInputStream fin = new FileInputStream(acPath) ) {
				 keystore = KeyStore.getInstance(KeyStore.getDefaultType());
				 keystore.load(fin, "changeit".toCharArray()); 
				
				 //添加私有证书
				 KeyStore keystoreTmp = KeyStore.getInstance(KeyStore.getDefaultType()); 
				 keystoreTmp.load(HttpUtils.class.getResourceAsStream("/store/mykey.keystore"), "store123".toCharArray());
				 Certificate crt = keystoreTmp.getCertificate("hassan");
				 keystore.setCertificateEntry("hesheng_hassan", crt);
				return keystore;
			}

		}
	}

	public static String post(String url, Map<String, String> param, Charset charset) {
		List<NameValuePair> parameters = new ArrayList<>();
		if (null != param){
			for (String entry : param.keySet()){
				BasicNameValuePair pair = new BasicNameValuePair(entry, param.get(entry));
				parameters.add(pair);
			}
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters ,charset);
		return post(url, entity, charset);
	}
	

	public static String post(String url, String requestBody, Charset charset){
		StringEntity entity = new StringEntity(requestBody, charset);
		return post(url, entity, charset);
	}
	
	
	private static String post(String url, HttpEntity entity, Charset charset){
		Long t = System.currentTimeMillis(); 
		
		RequestBuilder requestBuilder = RequestBuilder.post(url) 
	              .setCharset(charset) 
	              .setConfig(MyHttpConfig.getRequestConfig() ) ;
		 
		requestBuilder.setEntity(entity);
		
		HttpUriRequest request = requestBuilder.build(); 
	  
		CloseableHttpResponse resp = null;
		try {
			SSLContext sslContext = SSLContexts.custom()
					                           .loadTrustMaterial(getTrustKeyStore(), null)  
					                           .build();

			SSLContext sslnew = SSLContext.getInstance(SSL);
			 sslnew.init(null, null, new SecureRandom());
			resp = HttpClients.custom()
					          .setRetryHandler(MyHttpConfig.getHttpRequestRetryHandler()) 
					          .setSSLContext(sslContext ) 
//					          .setConnectionManager(MyHttpConfig.getPoolingHttpClientConnectionManager())
					          .build()
					          .execute(request);
			return EntityUtils.toString(resp.getEntity(), StandardCharsets.UTF_8);
		} catch (Exception e) { 
			logger.error("http请求"+url+"异常", e);
		} finally {
			long use = (System.currentTimeMillis()-t) ;
			logger.info("耗时"+use+"毫秒");
			IOUtils.closeQuietly(resp);
		}
		
		return "error";
	}
}
