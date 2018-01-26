package kr.or.tta.jungwon.TradeMonitor.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.*;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class HttpConnection {
	
	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
	private static final String ENCODING_GZIP = "gzip";	
	
	public String httpConnection(String url, Map params)
	{
		String response="";
		CloseableHttpResponse cHResponse=null;		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet;

		httpGet = new HttpGet(makeURI(url,params));
	
		//debug
		
		
		
	
				
		try 
		{
			 cHResponse = httpclient.execute(httpGet);			
			 //System.out.println(cHResponse.getStatusLine());
			 HttpEntity entity = cHResponse.getEntity();
		     // do something useful with the response body
		     // and ensure it is fully consumed
			 response = EntityUtils.toString(entity);
			 EntityUtils.consume(entity);
			    
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			try {
				cHResponse.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return response;
	}
	public URI makeURI(String url, Map <String,String> params)
	{
		
		URI uri=null;

		
		try {
			URIBuilder uriBuilder = new URIBuilder(url);
			String scheme = url.split(":")[0];		
			if (scheme.equals("https"))
			{
				uriBuilder.setScheme(scheme);
			}
			else
			{
				uriBuilder.setScheme("http");
			}
						
			if (params !=null)
			{
				Iterator<String> keys = params.keySet().iterator();
				List <NameValuePair> nvps = new ArrayList <NameValuePair>();
				while(keys.hasNext())
				{
					String key = keys.next();
					nvps.add(new BasicNameValuePair(key,params.get(key)));
				}
				uriBuilder.setParameters(nvps);	
			}			
			//System.out.println(uriBuilder.toString());
			uri= uriBuilder.build();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return uri;
		
		
	}
	public static void main(String[] args )
	
	{
		HashMap <String,String> params= new HashMap();
		HttpConnection httpConnection = new HttpConnection();
		String str= httpConnection.httpConnection("http://s3.ap-northeast-2.amazonaws.com/crix-production/crix_master", null);
		System.out.println(str);
	}
}
