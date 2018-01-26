package kr.or.tta.jungwon.TradeMonitor.db.mybatis;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class TradeSqlSessionFactory {
	
	public static SqlSessionFactory ssf;
	
	static {
		String resource = "config/mybatis-config.xml";
		InputStream inputStream;
		try {
			inputStream = Resources.getResourceAsStream(resource);
			ssf = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
    public static SqlSessionFactory getSqlSessionFactory()
    {
	        return ssf;
	}	
	

}
