package kr.or.tta.jungwon.TradeMonitor.user;

public class TelegramUser implements User {

	private  int CHAT_ID;
    private  int BOT_ID;
    public String token;
    
    public TelegramUser(int chat_id, int bot_id, String token)
    {
    	this.CHAT_ID = chat_id;
    	this.BOT_ID = bot_id;
    	this.token=token;
    }
    
    
    
    
    
    public int getChat_id()
    {
    	return CHAT_ID;
    }
    public int getBot_id()
    {
    	return BOT_ID;
    }
    public String getToken()
    {
    	return this.token;
    }
    
    public String toString()
    {
    	return "chat_id="+this.getChat_id()+" Bot_id="+getBot_id()+ "Tocen = "+getToken();
    }

}
