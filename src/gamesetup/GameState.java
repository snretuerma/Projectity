package gamesetup;

import java.util.HashMap;
import java.util.Iterator;

public class GameState {
	private HashMap<String, Tank> client = new HashMap<String, Tank>();
	
	public GameState(){}
	
	public void update(String name, Tank player){
		client.put(name, player);
	}
	
	public String sendState(){
		String playerState = "";
		Iterator i = client.entrySet().iterator();
		while(i.hasNext()){
			String name = (String) i.next();
			String player = client.get(name).toString();
			playerState+=player+"/";
		}
		return playerState;
	}
	
	public HashMap<String, Tank> getClientList(){
		return client;
	}
}
