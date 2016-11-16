package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import game.Game;
import game.KeyInput;
import game.NetworkPlayer;
import game.Texture;
import network.packets.ConnectPacket;
import network.packets.Packet;
import network.packets.Packet.PacketTypes;

public class GameClient extends Thread{
	private InetAddress address;
	private DatagramSocket socket;
	private Game game;
	
	public GameClient(Game game, String address, KeyInput input, Texture texture){
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
			//System.out.println("SERVER >> " + new String(packet.getData()));
//			String message = new String(packet.getData()).trim();
//			if(true){
//				send(message.getBytes());
//			}
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
				System.out.println("CLIENT >> [" +address.getHostAddress()+" : " + port +"] " + ((ConnectPacket) packet).getUsername() + " Joined");
				NetworkPlayer netplayer = new NetworkPlayer(100.0, 100.0, ((ConnectPacket) packet).getUsername(),game.input, game.texture, address, port);
				game.controller.addEntity(netplayer);
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
	
}
