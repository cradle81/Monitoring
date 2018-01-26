package kr.or.tta.jungwon.TradeMonitor;

import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kr.or.tta.jungwon.TradeMonitor.http.HttpConnection;

public class KimchiPrimeMonitor {
	
	private static final String codeListUrl = "https://s3.ap-northeast-2.amazonaws.com/crix-production/crix_master";
	private static final String halfHourURL="https://crix-api-endpoint.upbit.com/v1/crix/candles/minutes/30";
	private static final String minURL="https://crix-api-endpoint.upbit.com/v1/crix/candles/minutes/1";
	
	public ArrayList <String> upbit_avail_code_list;
	public ArrayList <String> binace_code_list;
	
	public KimchiPrimeMonitor()
	{
	
	}
	public static void main(String args[])
	{
		KimchiPrimeMonitor km = new KimchiPrimeMonitor();
		System.out.println(km.getCodes());
	}
	public List getCodes()
	{
		List list = new ArrayList()
		list.add("BTC");
		list.add("ETH");
		list.add("ETC");
		list.add("LTC");
		list.add("BCC");
		list.add("DGD");
		list.add("MTL");
		list.add("OMG");
		list.add("QTUM");
		list.add(e)
		

		
		
		
		return list;
	}	
}
