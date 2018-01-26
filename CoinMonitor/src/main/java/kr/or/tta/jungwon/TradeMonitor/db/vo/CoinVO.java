package kr.or.tta.jungwon.TradeMonitor.db.vo;

import java.sql.Timestamp;
import java.util.Date;

public abstract class CoinVO {
	String name;
	Timestamp candleDateTime;
	Timestamp candleDateTimeKst;
	int openingPrice;
	int highPrice;
	int lowPrice;
	int tradePrice;
	double candleAccTradeVolume;
	double candleAccTradePrice;

	public String getName()
	{
		return this.name;
	}
	public void setName(String name)
	{ 
		this.name=name;
	}

	
	public Timestamp getCandleDateTime() {
		return candleDateTime;
	}
	public void setCandleDateTime(Timestamp candleDateTime) {
		this.candleDateTime = candleDateTime;
	}
	public Timestamp getCandleDateTimeKst() {
		return candleDateTimeKst;
	}
	public void setCandleDateTimeKst(Timestamp candleDateTimeKst) {
		this.candleDateTimeKst = candleDateTimeKst;
	}
	public int getOpeningPrice() {
		return openingPrice;
	}
	public void setOpeningPrice(int openingPrice) {
		this.openingPrice = openingPrice;
	}
	public int getHighPrice() {
		return highPrice;
	}
	public void setHighPrice(int highPrice) {
		this.highPrice = highPrice;
	}
	public int getLowPrice() {
		return lowPrice;
	}
	public void setLowPrice(int lowPrice) {
		this.lowPrice = lowPrice;
	}
	public int getTradePrice() {
		return tradePrice;
	}
	public void setTradePrice(int tradePrice) {
		this.tradePrice = tradePrice;
	}
	public double getCandleAccTradeVolume() {
		return candleAccTradeVolume;
	}
	public void setCandleAccTradeVolume(double candleAccTradeVolume) {
		this.candleAccTradeVolume = candleAccTradeVolume;
	}
	public double getCandleAccTradePrice() {
		return candleAccTradePrice;
	}
	private void setCandleAccTradePrice(double candleAccTradePrice) {
		this.candleAccTradePrice = candleAccTradePrice;
	}
	private long getTimestamp() {
		return timestamp;
	}
	private void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}
	private int getUnit() {
		return unit;
	}
	private void setUnit(int unit) {
		this.unit = unit;
	}
	long timestamp;
	int unit; 
	public String toString()
	{		
		return "Name= " + this.getName() + ", tradePrice= " + this.getTradePrice() + ", candleAccTradeVolume= " + this.getCandleAccTradeVolume();
	}
}
