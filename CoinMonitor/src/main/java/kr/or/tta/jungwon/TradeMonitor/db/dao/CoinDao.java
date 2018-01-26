package kr.or.tta.jungwon.TradeMonitor.db.dao;

import java.util.List;

import kr.or.tta.jungwon.TradeMonitor.db.vo.CoinVO;

public interface CoinDao {
	public List <CoinDao> selectAll();
	public CoinDao selectOne(int SN);
	public void insertCoin(CoinVO coin);
	public void insertCoins(List <? extends CoinVO> coins);
	
    	
}
