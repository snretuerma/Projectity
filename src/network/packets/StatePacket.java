package network.packets;

import network.GameClient;
import network.GameServer;

public class StatePacket extends Packet{

	private String username = null;
	private double y;
	private double x;
	
	// for data retrieval
	public StatePacket(byte[] data) {
		super(3);
		String[] stateData = readData(data).split("/");
		this.username = stateData[0];
		this.x = Double.parseDouble(stateData[1]);
		this.y = Double.parseDouble(stateData[2]);
	}
	
	// when sending from client when instantiated
	public StatePacket(String username, double x, double y){
		super(3);
		this.username = username;
		this.x = x;
		this.y = y;
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
		return ("3"+this.username+'/'+this.x+'/'+this.y).getBytes();
	}
	
	
	public String getUsername(){
		return username;
	}
	
	public double getX(){
		return this.x;
	}
	
	public double getY(){
		return this.y;
	}
	
}
