package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import game.Game;
import game.GameController;
import game.KeyInput;
import game.NetworkPlayer;
import game.Player;
import game.Texture;
import network.packets.ConnectPacket;
import network.packets.Packet;
import network.packets.Packet.PacketTypes;

public class GameServer extends Thread{
	private DatagramSocket socket;
	private Game game;
	ArrayList<NetworkPlayer> playerList = new ArrayList<NetworkPlayer>();
	GameController controller;
	Player player;
	
	public GameServer(Game game, Texture texture, KeyInput input, GameController controller, Player player){
		this.game = game;
		this.controller = controller;
		this.player = player;
		try {
			this.socket = new DatagramSocket(8080);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		while(true){
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
//			String message = new String(packet.getData()).trim();
//			System.out.println("CLIENT >> " + message);
//			if(true){
//				send(message.getBytes(), packet.getAddress(), packet.getPort());
//			}
			
			this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
	}
	
	private void parsePacket(byte[] data, InetAddress address, int port) {
		String message = new String(data).trim();
//		System.out.println("Packet Message: " + message + " | Packet Type: " + Integer.parseInt(message.substring(0, 1)));
		PacketTypes type = Packet.findPacket(Integer.parseInt(message.substring(0,1)));
		Packet packet = null;
		switch(type){
			case INVALID:
				break;
			
			case CONNECT:
				packet = new ConnectPacket(data);
				System.out.println("SERVER >> [" +address.getHostAddress()+" : " + port +"] " + ((ConnectPacket) packet).getUsername() + " Connected");
				NetworkPlayer netplayer = new NetworkPlayer(100.0, 100.0, ((ConnectPacket) packet).getUsername(), game.input, game.texture, address, port);
				this.addConnection(netplayer, (ConnectPacket) packet);
//				if(netplayer != null){
//					this.playerList.add(netplayer);
//					game.controller.addEntity(netplayer);
//					game.player = netplayer;
//				}
				
				break;
				
			case DISCONNECT:
				break;
				
			default: 
				break;
			
		}
	}

	public void send(byte[] data, InetAddress address, int port){
		DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// broadcast message of a client to all the connected clients
	public void broadcast(byte[] data){
		for(NetworkPlayer np : playerList){
			send(data, np.getAddress(), np.getPort());
		}
	}

	public void addConnection(NetworkPlayer player2, ConnectPacket packet) {
		boolean connected = false;
//		System.out.println("Size: " + playerList.size() + " Port: " + player2.getPort() + " Username: " + player2.getUsername());
		for(NetworkPlayer p : playerList){
			if(player2.getUsername().equalsIgnoreCase(p.getUsername())){
				if(p.getAddress() == null){
					p.setAddress(player2.getAddress());
				}
				
				if(p.getPort() == -1){
					p.setPort(player2.getPort());
					System.out.println(p.getPort());
				}
				connected = true;
			}				
			else{
//				ConnectPacket loginPacket = new ConnectPacket(p.getUsername());
				send(packet.getData(), p.getAddress(), p.getPort());
				// send the player data to the server
				packet = new ConnectPacket(p.getUsername());
				send(packet.getData(), player2.getAddress(), player2.getPort());
			}	
		}
		if(!connected){
			this.playerList.add(player2);
		}
	}
	
		
}
