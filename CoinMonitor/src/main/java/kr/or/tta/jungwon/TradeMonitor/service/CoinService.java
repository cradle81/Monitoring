package kr.or.tta.jungwon.TradeMonitor.service;

import java.util.List;

import kr.or.tta.jungwon.TradeMonitor.db.dao.KrwBtcDao;
import kr.or.tta.jungwon.TradeMonitor.db.vo.CoinVO;

public interface CoinService {
	
	 
	 public List <CoinVO> selectAll();
	 public void insertCoin(CoinVO coinVO);
	 public void insertCoins(List <? extends CoinVO> coinVOList);
	 public CoinVO selectOne(int SN);
	 
	

}
