package kr.or.tta.jungwon.TradeMonitor.service;

import java.util.List;

import kr.or.tta.jungwon.TradeMonitor.db.dao.CoinDao;
import kr.or.tta.jungwon.TradeMonitor.db.dao.KrwBtcDao;
import kr.or.tta.jungwon.TradeMonitor.db.vo.CoinVO;

public class KrwBtcService implements CoinService{
	private CoinDao dao = KrwBtcDao.getInstance();

	public List<CoinVO> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public void insertCoin(CoinVO coin30vo) {
		// TODO Auto-generated method stub
		dao.insertKRWBTC30(coin30vo);
				
	}
	public void insertCoins(List<CoinVO> Listcoin30vo) {
		// TODO Auto-generated method stub
		dao.insertListKRWBTC30(Listcoin30vo);
				
	}	

	public CoinVO selectOne(int SN) {
		// TODO Auto-generated method stub
		return null;
	}
}
