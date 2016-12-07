package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import game.Bullet;
import game.Entity;
import game.Game;
import game.KeyInputHandler;
import game.NetworkBullet;
import game.NetworkPlayer;
import game.Player;
import game.Texture;
import network.packets.ConnectPacket;
import network.packets.DisconnectPacket;
import network.packets.Packet;
import network.packets.Packet.PacketTypes;
import network.packets.ShootPacket;
import network.packets.StatePacket;

public class GameClient extends Thread{
	private InetAddress address;
	private DatagramSocket socket;
	private Game game;
	
	public GameClient(Game game, String address, KeyInputHandler input, Texture texture){
		this.game = game;
		try {
			this.socket = new DatagramSocket();
			this.address = InetAddress.getByName(address);
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
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
	
	public void send(byte[] data){
		DatagramPacket packet = new DatagramPacket(data, data.length, address, 8080);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void parsePacket(byte[] data, InetAddress pAddress, int pPort) {
		String message = new String(data).trim();
//		System.out.println("Packet Message: " + message + " | Packet Type: " + Integer.parseInt(message.substring(0, 1)));
		PacketTypes type = Packet.findPacket(Integer.parseInt(message.substring(0,1)));
		Packet packet = null;
		switch(type){
			case INVALID:
				break;
			
			case CONNECT:
				packet = new ConnectPacket(data);
				handleConnect((ConnectPacket)packet, pAddress, pPort);
				break;
				
			case DISCONNECT:
				packet = new DisconnectPacket(data);
				System.out.println("CLIENT >> [" +pAddress.getHostAddress()+" : " + pPort +"] " + ((DisconnectPacket) packet).getUsername() + " left");
				game.controller.removePlayerEntity(((DisconnectPacket) packet).getUsername());
				break;
			
			case STATE:
				packet = new StatePacket(data);
				handleState((StatePacket)packet);
				break;
				
			case SHOOT:
				packet = new ShootPacket(data);
				handleShooting((ShootPacket)packet);
				break;
				
			default: 
				break;
			
		}
	}
	
	// client side handler of data connection
	private void handleConnect(ConnectPacket packet, InetAddress pAddress, int pPort){
		System.out.println("CLIENT >> [" +pAddress.getHostAddress()+" : " + pPort +"] " + packet.getUsername() + " Joined");
		NetworkPlayer netplayer = new NetworkPlayer(game, packet.getX(), packet.getY(), packet.getDirection(), packet.getHealth(), packet.getStatus(), packet.getUsername(), packet.getScore(), game.input, game.texture, address, pPort);
		// adds the other connected player to the client's entity list
		game.controller.addEntity(netplayer);
		//game.scoreBoardModel.addRow(new Object[]{netplayer.getUsername(), netplayer.getScore()});
	}
	
	private void handleState(StatePacket packet){
		this.game.controller.setState(packet.getUsername(), packet.getX(), packet.getY(), packet.getDirection(), packet.getHealth(), packet.getStatus(), packet.getScore());
//		if(packet.getStatus() == 1){
//			System.out.println("State Changed");
//			game.controller.addProjectile(new Bullet(packet.getX(), packet.getY(), game.texture, packet.getDirection(), packet.getUsername()));
//		}
	}
	
	private void handleShooting(ShootPacket packet){	
//		for(Entity p : game.controller.getEntityList()){
//			if(p.getUsername() != packet.getUsername()){
//				System.out.println("Player Name: " + p.getUsername() + " Projectile Count: " +  ((NetworkPlayer) p).getProjectileList().size());
//				((NetworkPlayer) p).getProjectileList().add(new Bullet(packet.getX(), packet.getY(), game.texture, packet.getDirection(), packet.getUsername()));
//			}
//		}
		
		//for(Entity p : game.controller.getProjectileList()){
			this.game.controller.getProjectileList().add(new Bullet(game, packet.getX(), packet.getY(), game.texture, packet.getDirection(), packet.getUsername()));
		//}
	}
	
}
