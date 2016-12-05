package network.packets;

import network.GameClient;
import network.GameServer;

public class ShootPacket extends Packet{

	private String username = null;
	private double y;
	private double x;
	private char direction;
	private int status;
	// for data retrieval
	public ShootPacket(byte[] data) {
		super(4);
		String[] stateData = readData(data).split("/");
		this.username = stateData[0];
		this.x = Double.parseDouble(stateData[1]);
		this.y = Double.parseDouble(stateData[2]);
		this.direction = stateData[3].charAt(0);
		this.status = Integer.parseInt(stateData[4]);
	}
	
	// when sending from client when instantiated
	public ShootPacket(String username, double x, double y, char direction, int status){
		super(4);
		this.username = username;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.status = status;
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
		return ("4"+this.username+'/'+this.x+'/'+this.y+"/"+getDirection()+"/"+getStatus()).getBytes();
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
	
	public char getDirection(){
		return this.direction;
	}
	
	public void setDirection(char direction){
		this.direction = direction;
	}
	
	public void setStatus(int status){
		this.status = status;
	}
	
	public int getStatus(){
		return this.status;
	}
}
