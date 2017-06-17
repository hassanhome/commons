package hesheng.commons.http;

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
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtils {
	private static Logger logger = Logger.getLogger(HttpUtils.class);
	
	private static KeyStore keystore = null;
	
	public static KeyStore getTrustKeyStore() throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException{
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
			 
			 FileInputStream fin = null;
			try {
				 keystore = KeyStore.getInstance(KeyStore.getDefaultType());
				 fin = new FileInputStream(acPath);
				 keystore.load(fin, "changeit".toCharArray()); 
				
				 //添加私有证书
				 KeyStore keystoreTmp = KeyStore.getInstance(KeyStore.getDefaultType()); 
				 keystoreTmp.load(HttpUtils.class.getResourceAsStream("/store/mykey.keystore"), "store123".toCharArray());
				 Certificate crt = keystoreTmp.getCertificate("hassan");
				 keystore.setCertificateEntry("hesheng_hassan", crt);
				return keystore;
			}finally {
				IOUtils.closeQuietly(fin);
			}  

		}
	}

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
			SSLContext sslContext = SSLContexts.custom()
					                           .loadTrustMaterial(getTrustKeyStore(), null)  
					                           .build();
			 SSLContext sslnew = SSLContext.getInstance("SSL");
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
			e.printStackTrace();
		} finally {
			Long use = (System.currentTimeMillis()-t) ;
			
			System.out.println("耗时"+use+"毫秒"); 
			IOUtils.closeQuietly(resp);
		}
		
		return "error";
	}
}
