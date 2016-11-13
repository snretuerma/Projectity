package gamesetup;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class GameServer implements Runnable{
	private int port = (Integer) null;
	private DatagramSocket serverSocket = null;
	private GameState state = null;
	Thread gameServerThread = new Thread(this);
	
	public GameServer(int port){
		this.port = port;
		try{
			serverSocket = new DatagramSocket(this.port);
			
		}catch(Exception e){
			System.out.println("Could not listen to port " + this.port);
		}
		state = new GameState();
		System.out.println("Game starting on port " + this.port);
		gameServerThread.start();
	}
	
	public void broadcastState(Tank player, String message){
		DatagramPacket packet = null;
		byte[] buffer = message.getBytes();
		// @TO DO broadcast game state to all players
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Game Server Thread Running");
	}
	
}
