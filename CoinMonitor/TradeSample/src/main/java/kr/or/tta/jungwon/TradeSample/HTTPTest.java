package kr.or.tta.jungwon.TradeSample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DecompressingHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


public class HTTPTest { 
	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
	private static final String ENCODING_GZIP = "gzip";

	public static void main(String[] args) throws Exception {

		HTTPTest http = new HTTPTest(); 

		System.out.println("Testing 1 - Send Http GET request");
		http.httpConnection();
		//http.httpsConnection();
		http.https2Connection(); 

		//System.out.println("\nTesting 2 - Send Http POST request");
		//http.sendPost();

	}
	public void httpConnection() throws Exception {
		

		
		
		
		
		
		URI uri  = null;
		URIBuilder builder=new URIBuilder();
		String url = "http://s3.ap-northeast-2.amazonaws.com/crix-production/crix_master";
		builder = new URIBuilder(url);
		uri = builder.build();
		System.out.println(uri);
		
		HttpClient httpClient = new DecompressingHttpClient();		
		//HttpGet httpGet = new HttpGet(url);
		HttpGet httpGet = new HttpGet();

		// add request header
		httpGet.addHeader("User-Agent", USER_AGENT);
		
		
		
		//set URL
		httpGet.setURI(uri);
		
		//excute URL
		HttpResponse response = httpClient.execute(httpGet);
		
			
		
		
		System.out.println("\nSending 'GET' request to URL : " + uri);
		System.out.println("Response Code : " + 
                       response.getStatusLine().getStatusCode());
		

		
		HttpEntity entity = response.getEntity();

		
		
		
		String responseString = EntityUtils.toString(entity);
		
		
		System.out.println("============================");
		System.out.println(responseString);
		System.out.println("============================");
				
	}
	public void httpsConnection() throws Exception {
		URI uri  = null;
		URIBuilder builder=new URIBuilder();
		String url = "s3.ap-northeast-2.amazonaws.com/crix-production/crix_master";
		builder = new URIBuilder(url);
		uri = builder.build();
		System.out.println(uri);
		
		HttpClient httpClient = new DecompressingHttpClient();
		

	   // SSL setting
		
		 // SSL setting
		   SSLContext context = SSLContext.getInstance("TLSv1.2");
		   context.init(null, new TrustManager[]{new javax.net.ssl.X509TrustManager() {
		      
		      
		      public X509Certificate[] getAcceptedIssuers() {
		       return null;
		      }
		      
		      
		      public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		      }
		      
		      
		      public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		      }
		     }
		   }, null);
		   
		   
		
		
		   // SSL setting
 	   		
	    TrustStrategy trustStrategy = new TrustStrategy() {

	        public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
	            for (X509Certificate cert: chain) {
	                System.err.println(cert);
	            }
	            return false;
	        }

	    };

	    //SSLSocketFactory sslsf = new SSLSocketFactory("TLS", null, null, , null,
	    		
	    SSLSocketFactory sslsf = new SSLSocketFactory(context);

	    Scheme https = new Scheme("https", 443, sslsf);
	    httpClient.getConnectionManager().getSchemeRegistry().register(https);
	    
		HttpGet httpGet = new HttpGet();
		HttpHost target = new HttpHost("s3.ap-northeast-2.amazonaws.com", 443, "https");

		// add request header
		httpGet.addHeader("User-Agent", USER_AGENT);
		
		
		
		//set URL
		httpGet.setURI(uri);
		
		//excute URL
		HttpResponse response = httpClient.execute(target,httpGet);	    

		System.out.println("\nSending 'GET' request to URL : " + uri);
		System.out.println("Response Code : " + 
                       response.getStatusLine().getStatusCode());
		

		
		HttpEntity entity = response.getEntity();

		
		String responseString = EntityUtils.toString(entity);
				
		System.out.println("============================");
		System.out.println(responseString);
		System.out.println("============================");
	}
	public void https2Connection() throws Exception
	{


		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
		 
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
		 
		ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
		 		
		HttpClient client = new DecompressingHttpClient(new DefaultHttpClient(ccm, params));
				
				
				
		String getURL = "https://s3.ap-northeast-2.amazonaws.com/crix-production/crix_master";
		HttpGet get = new HttpGet(getURL);
		HttpResponse responseGet = client.execute(get);
		HttpEntity resEntityGet = responseGet.getEntity();
		 
		if (resEntityGet != null) {
			System.out.println("============================");
			
			System.out.println(EntityUtils.toString(resEntityGet));
			System.out.println("============================");
		   
		}
	}
	
	public void httpsConnection(String url, HashMap param)
	{
	 // 레지시터리
		
     // 
		
	 //압축 응답 확인
	}
}
