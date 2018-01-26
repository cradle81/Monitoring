package kr.or.tta.jungwon.TradeMonitor.service;

import java.util.List;

import kr.or.tta.jungwon.TradeMonitor.db.dao.*;
import kr.or.tta.jungwon.TradeMonitor.db.vo.*;

public class KrwService implements CoinService {

	private CoinDao dao = KrwCoinDao.getInstance();
	
	public List<CoinVO> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public void insertCoins(List<? extends CoinVO> coins) {
		// TODO Auto-generated method stub
		dao.insertCoins(coins);
		
	}
	public void insertCoin(CoinVO coinVO) {
		// TODO Auto-generated method stub
		
	}
	 

	public CoinVO selectOne(int SN) {
		// TODO Auto-generated method stub
		return null;
	}


}
