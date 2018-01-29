package kr.or.tta.jungwon.TradeMonitor.alram;

import java.util.List;

public interface PushAlram {
	
	public void sendMessage(List userList, String text);

}
