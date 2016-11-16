package network.packets;

import network.GameClient;
import network.GameServer;

public class DisconnectPacket extends Packet{

	private String username = null;
	private double y;
	private double x;
	
	// for data retrieval
	public DisconnectPacket(byte[] data) {
		super(2);
		this.username = readData(data);
	}
	
	// when sending from client when instantiated
	public DisconnectPacket(String username){
		super(2);
		this.username = username;
	}
	
	@Override
	public void writeData(GameClient client) {
		client.send(getData());
	}
	@Override
	public void writeData(GameServer server) {
		server.broadcast(getData());
	}

	@Override
	public byte[] getData() {
		return ("2"+this.username).getBytes();
	}
	
	
	public String getUsername(){
		return username;
	}
}
