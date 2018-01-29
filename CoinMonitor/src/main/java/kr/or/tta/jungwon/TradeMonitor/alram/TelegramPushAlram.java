package kr.or.tta.jungwon.TradeMonitor.alram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kr.or.tta.jungwon.TradeMonitor.http.HttpConnection;
import kr.or.tta.jungwon.TradeMonitor.user.TelegramUser;

public class TelegramPushAlram implements PushAlram {

	
	public List userList;
	public String url = "https://api.telegram.org";
	public HttpConnection hc;
	
	public TelegramPushAlram()
	{

		userList = new ArrayList<TelegramUser> ();
		this.hc = new HttpConnection();
		
	}
	public TelegramPushAlram(List userList)
	{

		this.userList = userList;
		this.hc = new HttpConnection();
		
	}
	
	
	
	@Override
	public void sendMessage(List list, String text) {
		// TODO Auto-generated method stub		
		
		Iterator <TelegramUser>itor = list.iterator();
		
		
		while(itor.hasNext())
		{
			TelegramUser user = itor.next();
			System.out.println(user);
			System.out.println(text);
			
			
			url = url+"/bot"+user.getToken();
			
			Map <String,String> params = new HashMap <String,String>();
			params.put("chat_id", user.getChat_id()+"");
			params.put("text","안녕하세요?");
			String response = hc.httpConnection(url, params);
		}

	}
	
	public void sendMessage(String text) {
		// TODO Auto-generated method stub		
		
		Iterator <TelegramUser>itor = userList.iterator();
		
		while(itor.hasNext())
		{
			TelegramUser user = itor.next();
			System.out.println(user);
			System.out.println(text);
			String temp = url+"/bot"+user.getToken()+"/sendMessage";
			
			Map <String,String> params = new HashMap <String,String>();
			params.put("chat_id", user.getChat_id()+"");
			params.put("text",text);
			String response = hc.httpConnection(temp, params);	
			System.out.println(response);
			
		}

	}	

}
