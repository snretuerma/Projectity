package game;

import java.net.InetAddress;

public class NetworkPlayer extends Player{

	public NetworkPlayer(Game game, double x, double y, char direction, float health, int type, String username, int score, KeyInputHandler input,  Texture texture, InetAddress address, int port) {
		super(game, x, y, direction, health, type, username, score, input, texture, address, port);
		
	}
	
	@Override
	public void update(){
		super.update();
	}

}
