package game;

import java.net.InetAddress;

public class NetworkPlayer extends Player{

	public NetworkPlayer(Game game, double x, double y, String username, KeyInputHandler input,  Texture texture, InetAddress address, int port) {
		super(game, x, y, username, input, texture, address, port);
	}
	
	@Override
	public void update(){
		super.update();
	}

}
