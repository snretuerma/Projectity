package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

import game.Game;
import game.GameController;
import game.KeyInputHandler;
import game.NetworkPlayer;
import game.Player;
import game.Texture;
import network.packets.ConnectPacket;
import network.packets.DisconnectPacket;
import network.packets.Packet;
import network.packets.Packet.PacketTypes;
import network.packets.StatePacket;

public class GameServer extends Thread{
	private DatagramSocket socket;
	private Game game;
	ArrayList<NetworkPlayer> playerList = new ArrayList<NetworkPlayer>();
	GameController controller;
	Player player;
	
	public GameServer(Game game, Texture texture, KeyInputHandler input, GameController controller, Player player){
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
				NetworkPlayer netplayer = new NetworkPlayer(game, 100.0, 100.0, ((ConnectPacket) packet).getUsername(), game.input, game.texture, address, port);
				this.addConnection(netplayer, (ConnectPacket) packet);
				System.out.println("SERVER >> [" +address.getHostAddress()+" : " + port +"] " + ((ConnectPacket) packet).getUsername() + " Connected");
				break;
				
			case DISCONNECT:
				packet = new DisconnectPacket(data);
				System.out.println("SERVER >> [" +address.getHostAddress()+" : " + port +"] " + ((DisconnectPacket) packet).getUsername() + " Disconnected");
				this.removeConnection((DisconnectPacket) packet);				
				break;
			
			case STATE:
				packet = new StatePacket(data);
				//System.out.println(((StatePacket)packet).getUsername() + " moved " + ((StatePacket)packet).getX() + ", " + ((StatePacket)packet).getY() );
				this.handleState((StatePacket)packet);
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
		for(NetworkPlayer nplayer : playerList){
			send(data, nplayer.getAddress(), nplayer.getPort());
		}
	}

	public void addConnection(NetworkPlayer netpl, ConnectPacket packet) {
		boolean connected = false;
		//System.out.println("\n|addConnection() method debug print| \n Size: " + playerList.size() + " Port: " + netpl.getPort() + " Username: " + netpl.getUsername() + "\n");
		for(NetworkPlayer p : this.playerList){
			if(netpl.getUsername().equalsIgnoreCase(p.getUsername())){
				if(p.getAddress() == null){
					p.setAddress(netpl.getAddress());
				}
				
				if(p.getPort() == -1){
					p.setPort(netpl.getPort());
				}
				connected = true;
			}				
			else{
				// send packet containing the new players data to the current player in the iteration
				send(packet.getData(), p.getAddress(), p.getPort());

				// send a packet to the new player about the existing player
				ConnectPacket pack = new ConnectPacket(p.getUsername(), p.getX(), p.getY());
				send(pack.getData(), netpl.getAddress(), netpl.getPort());
			}	
		}
		if(!connected){
			this.playerList.add(netpl);
		}
	}
	
	
	public NetworkPlayer getPlayer(String username){
		for(NetworkPlayer player : playerList){
			if(player.getUsername().equals(username)){
				return player;
			}
		}
		return null;
	}
	
	public int getPlayerIndex(String username){
		int index = 0;
		for(NetworkPlayer player : playerList){
			if(player.getUsername().equals(username)){
				break;
			}
			index++;
		}
		 return index;
	}
	
	public void removeConnection(DisconnectPacket packet) {
		this.playerList.remove(getPlayerIndex(packet.getUsername()));
		packet.writeData(this);
	}
	
	private void handleState(StatePacket packet){
		if(getPlayer(packet.getUsername())!=null){
			int playerIndex = getPlayerIndex(packet.getUsername());
			this.playerList.get(playerIndex).x = packet.getX();
			this.playerList.get(playerIndex).y = packet.getY();
			packet.writeData(this);
		}
	}
}
