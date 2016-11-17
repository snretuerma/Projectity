package network.packets;

import network.GameClient;
import network.GameServer;

public class ConnectPacket extends Packet{

	private String username = null;
	private double y;
	private double x;
	
	// for data retrieval
	public ConnectPacket(byte[] data) {
		super(1);
		String[] stateData = readData(data).split("/");
		this.username = stateData[0];
		this.x = Double.parseDouble(stateData[1]);
		this.y = Double.parseDouble(stateData[2]);
	}
	
	// when sending from client when instantiated
	public ConnectPacket(String username, double x, double y){
		super(1);
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
		return ("1"+this.username+"/"+getX()+"/"+getY()).getBytes();
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
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
}
