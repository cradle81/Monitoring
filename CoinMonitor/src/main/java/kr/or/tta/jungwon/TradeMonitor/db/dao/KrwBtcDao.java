package kr.or.tta.jungwon.TradeMonitor.db.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.tta.jungwon.TradeMonitor.db.mybatis.TradeSqlSessionFactory;
import kr.or.tta.jungwon.TradeMonitor.db.vo.CoinVO;

public class KrwBtcDao implements CoinDao {
	private SqlSession sqlSession;
	private String nameSpace="kr.or.tta.jungwon.TradeMonitor";
	SqlSessionFactory factory = TradeSqlSessionFactory.getSqlSessionFactory();
	
	private static CoinDao dao;
	public KrwBtcDao(){
		
		
	}

	public static CoinDao getInstance(){
	        if(dao == null){
	            dao = new KrwBtcDao();
	        }
	         
	        return dao;
	    }
	public List<CoinDao> selectAll() {
		// TODO Auto-generated method stub
		SqlSession session = factory.openSession();
		session.selectList(nameSpace+".selectALL");
		session.close();
		return null;
	}

	public CoinDao selectOne(int SN) {
		// TODO Auto-generated method stub
		SqlSession session = factory.openSession();
		session.selectOne(nameSpace+".selectOne", SN);
		session.close();
		return null;
	}

	public void insertCoin(CoinVO kb30) {
		// TODO Auto-generated method stub
		SqlSession session = factory.openSession();
		session.insert(nameSpace+".insertKRWBTC30",kb30);
		session.commit();
		session.close();
	}
	public void insertCoins(List <CoinVO> listKb30)
	{
		SqlSession session = factory.openSession();
		session.insert(nameSpace+".insertListKRWBTC30",listKb30);
		session.commit();
		session.close();
	}
	

}
