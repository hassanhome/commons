package hesheng.test.commons.http;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStore.LoadStoreParameter;
import java.security.KeyStore.ProtectionParameter;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.RSAPublicKeySpec;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.swing.KeyStroke;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import hesheng.commons.http.HttpUtils;
import hesheng.commons.http.MyHttpConfig;

public class TestHttp {
	
	@Test
	public void test10() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException{
		 String javaHome = System.getProperty("java.home"); 
		 
		 String acPath = javaHome+"/lib/security/cacerts";
		 KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		 FileInputStream fin = new FileInputStream(acPath);
		 ks.load(fin, "changeit".toCharArray()); 
	}
	
	@Test
	public void test9() throws KeyStoreException{
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test 
	public void test8(){

		InputStream stream = this.getClass().getResourceAsStream("/store/mykey.keystore");
		try {
			
			char[] password = "store123".toCharArray();
			 KeyStore keystore = KeyStore.getInstance("JKS"); 
			 keystore.load(stream, password);
			 Certificate cert = CertificateFactory.getInstance("X.509")
			    .generateCertificate(this.getClass().getResourceAsStream("/store/baidu.cer"));
			 System.out.println("--------------");
			 System.out.println(cert.getPublicKey());
			 System.out.println("--------------");
			 
//			 keystore.setCertificateEntry("baidu", cert);
//			keystore.setCertificateEntry("baidu", cert );
			 Certificate crt = keystore.getCertificate("hassan");
			 PublicKey publicKey = crt.getPublicKey();
			 System.out.println(Base64.encodeBase64String(publicKey.getEncoded()));
			 Enumeration<String> aliases = keystore.aliases();
			 System.out.println("while -----");
			 while (aliases.hasMoreElements()){
				 System.out.println(aliases.nextElement());
			 }
			 Provider p = keystore.getProvider(); 
			 
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void test7(){
		Map<String, String> param = new HashMap<>();
//		param.put("user", "小明");
//		param.put("password", "这是密码" );
		try {
			System.out.println(HttpUtils.post("https://www.baidu.com/", param , StandardCharsets.UTF_8));
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
	
	@Test
	public void test5(){
		Map<String, String> param = new HashMap<>();
		param.put("name", "小明");
		param.put("password", "这是密码" );
		System.out.println(HttpUtils.post("http://localhost:8080/spmv/user/sayhello.do", param , StandardCharsets.UTF_8));
	}
	
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
