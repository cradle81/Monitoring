package kr.or.tta.jungwon.TradeMonitor.db.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.tta.jungwon.TradeMonitor.db.mybatis.TradeSqlSessionFactory;
import kr.or.tta.jungwon.TradeMonitor.db.vo.CoinVO;
import kr.or.tta.jungwon.TradeMonitor.db.vo.KRWCoinVO;

public class KrwCoinDao implements CoinDao {
	private SqlSession sqlSession;
	private String nameSpace="kr.or.tta.jungwon.TradeMonitor";
	SqlSessionFactory factory = TradeSqlSessionFactory.getSqlSessionFactory();
	
	private static CoinDao dao;
	public List<CoinDao> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
	public static CoinDao getInstance(){
        if(dao == null){
            dao = new KrwCoinDao();
        }
         
        return dao;
    }
	public CoinDao selectOne(int SN) {
		// TODO Auto-generated method stub
		return null; 
	}

	public void insertCoins(List<? extends CoinVO> coins) {
		// TODO Auto-generated method stub
		SqlSession session = factory.openSession();
		session.insert(nameSpace+".insertKRWCoin",coins);	
		session.commit();
		session.close();	
	}
	public void insertCoin(CoinVO coin) {
		// TODO Auto-generated method stub
		
	}

}
