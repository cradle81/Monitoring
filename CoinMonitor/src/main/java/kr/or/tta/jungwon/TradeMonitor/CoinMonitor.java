package kr.or.tta.jungwon.TradeMonitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.or.tta.jungwon.TradeMonitor.db.vo.CoinVO;
import kr.or.tta.jungwon.TradeMonitor.db.vo.KRWCoinVO;
import kr.or.tta.jungwon.TradeMonitor.db.vo.KrwBtcVO;
import kr.or.tta.jungwon.TradeMonitor.http.HttpConnection;
import kr.or.tta.jungwon.TradeMonitor.service.*;

public class CoinMonitor {
	
	private static final String codeListUrl = "https://s3.ap-northeast-2.amazonaws.com/crix-production/crix_master";
	private static final String halfHourURL="https://crix-api-endpoint.upbit.com/v1/crix/candles/minutes/30";
	private static final String minURL="https://crix-api-endpoint.upbit.com/v1/crix/candles/minutes/1";
	
	private int getStatCount=1;
	
	public ArrayList <String> codeList;
	
	public HttpConnection hc;
	
	public CoinMonitor()
	{
		this.codeList = new ArrayList <String>();
		this.hc = new HttpConnection();
	}
	
	public List getCodes()
	{
		List list = new ArrayList();
		HttpConnection hc = new HttpConnection();
		String response = hc.httpConnection(codeListUrl, null);
		

		
		//System.out.println(response);
		
		JSONParser jsonParser = new JSONParser();
        
        JSONArray jsonArray;
		try {
			jsonArray = (JSONArray) jsonParser.parse(response);
			
			Iterator <JSONObject> it = jsonArray.iterator();
			while (it.hasNext())
			{
				JSONObject jsonObject = it.next();
				String code = (String)jsonObject.get("code");
				String marketState = (String)jsonObject.get("marketState");
				String tradeStatus = (String)jsonObject.get("tradeStatus");
				
				//System.out.println(code);
				// 업비스 코드 + 원화, 거래상태가 활성화 된 녀석들만 가져오기//
				/*
				if (marketState.equals("ACTIVE") //&& tradeStatus.equals("ACTIVE") 
						&&  code.contains("CRIX.UPBIT.KRW"))
				*/	
				// 임시 디버깅으로 인해서 비트코인만 가져오기
				if (marketState.equals("ACTIVE") //&& tradeStatus.equals("ACTIVE") 
						&&  code.contains("CRIX.UPBIT.KRW"))		
				codeList.add(code);
			}
		    System.out.println("Code 개수 : "+ codeList.size() );
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return list;
	}
	public static void main(String [] args) throws ClassNotFoundException, InterruptedException
	{
		CoinMonitor cm = new CoinMonitor();
		
		//코드 리스트 가져오기
		List list=cm.getCodes(); 
		
		//임시로 클래스 로딩 테스트
		System.out.println(Class.forName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy"));
		
		
		int interval = 60;
		while(true)
		{
			//cm.get_30min_stat();
			cm.get_1min_stat();
			Thread.sleep(interval * 1000);
		}
		
		
		
	}
	public void get_1min_stat()
	{
		Map <String,String> params = new HashMap <String,String>();
		
		
		
		for (String code : codeList)
		{
			params.put("code", code);
			params.put("count", getStatCount+"");
			String response = hc.httpConnection(minURL, params);
			JSONParser jsonParser = new JSONParser();		        
			JSONArray jsonArray;

			Gson gson = new Gson();
			
			//JDBC 서비스 생성
			CoinService krwService = new KrwService();
			try 
			{
				

		        jsonArray = (JSONArray)jsonParser.parse(response);
		        //가져온 데이터가 없는 경우는 패스 
		        if (jsonArray.isEmpty() == false )
		        {
			        //Coin30VO coin = new KrwBtc30VO();
			        List <KRWCoinVO> listCoin = new ArrayList <KRWCoinVO>();
			        System.out.println(jsonArray.toArray());
			        listCoin = gson.fromJson(jsonArray.toJSONString(), new TypeToken<List<KRWCoinVO>>(){}.getType());
			        
			        
			        //지랄같군
			        
			        Iterator it = listCoin.iterator();
			        while(it.hasNext())
			        {
			        	CoinVO coinVO= (CoinVO)it.next();
			        	coinVO.setName(code.split("-")[1]);
			        } 
			        
			        // insert DB 코인
			        krwService.insertCoins(listCoin);
		        }
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	

	public void get_30min_stat()
	{
		for (String code : codeList)
		{

				Map <String,String> params = new HashMap <String,String>();
				params.put("code", code);
				params.put("count", getStatCount+"");
				String response = hc.httpConnection(halfHourURL, params);
				//System.out.println(response);

				JSONParser jsonParser = new JSONParser();		        
				JSONArray jsonArray;
				
				// Conver to Java from json
				Gson gson = new Gson();
				
				//JDBC 서비스 생성
				KrwBtcService kbservice = new KrwBtcService();
		         
				try {
					

			        jsonArray = (JSONArray)jsonParser.parse(response);
			        //가져온 데이터가 없는 경우는 패스 
			        if (jsonArray.isEmpty() == false )
			        {
			        //Coin30VO coin = new KrwBtc30VO();
			        List listCoin = new ArrayList <CoinVO>();
			        	System.out.println(jsonArray.toArray());
			        listCoin = gson.fromJson(jsonArray.toJSONString(), new TypeToken<List<KrwBtcVO>>(){}.getType());
			        
			        System.out.println(listCoin);
			        
			        // insert DB 코인
			        kbservice.insertCoins(listCoin);
			        
			        //candleDateTime,candleDateTimeKst,openingPrice,highPrice,
			        //lowPrice,tradePrice,candleAccTradeVolume,candleAccTradePrice,timestamp,unit
			        /*
			        JSONObject jsonObject = (JSONObject)jsonArray.get(0);
			        String candleDateTime = (String)jsonObject.get("candleDateTime");
			        String candleDateTimeKst = (String)jsonObject.get("candleDateTimeKst");
			        
			        int openingPrice = (Integer)jsonObject.get("openingPrice");
			        int highPrice = (Integer)jsonObject.get("highPrice");
			        int lowPrice = (Integer)jsonObject.get("lowPrice");
			        int tradePrice = (Integer)jsonObject.get("tradePrice");
			        double candleAccTradeVolume = (Double)jsonObject.get("candleAccTradeVolume");
			        double candleAccTradePrice = (Double)jsonObject.get("candleAccTradePrice");
			        
			        int timestamp = (Integer)jsonObject.get("timestamp");
			        int unit = (Integer)jsonObject.get("unit");
			        
			        
			        
			        
			        
			        
			        System.out.printf("Code = %s , Time = %s, 가격= %f, 거래량 =%f \n", code, candleDateTimeKst, tradePrice,candleAccTradeVolume);

			        */
			        }
			        
					
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block 
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	}
	
}
