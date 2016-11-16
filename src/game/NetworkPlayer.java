package game;

import java.net.InetAddress;

public class NetworkPlayer extends Player{

	public NetworkPlayer(double x, double y, String username, KeyInput input,  Texture texture, InetAddress address, int port) {
		super(x, y, username, input, texture, address, port);
	}
	
	@Override
	public void update(){
		super.update();
	}

}
