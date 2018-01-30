package kr.or.tta.jungwon.TradeMonitor.core;

public interface TickData {
	
	public void loadData();
	public void mon_1min();
	public void mon_5min();
	public void mon_30min();
	public void mon_60min();
	public void mon_1day();
	

}
