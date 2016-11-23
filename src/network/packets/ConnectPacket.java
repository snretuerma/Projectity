package network.packets;

import network.GameClient;
import network.GameServer;

public class ConnectPacket extends Packet{

	private String username = null;
	private double y;
	private double x;
	private char direction;
	private float health;
	private int type;
	
	// for data retrieval
	public ConnectPacket(byte[] data) {
		super(1);
		String[] stateData = readData(data).split("/");
		this.username = stateData[0];
		this.x = Double.parseDouble(stateData[1]);
		this.y = Double.parseDouble(stateData[2]);
		this.direction = stateData[3].charAt(0);
		this.health = Float.parseFloat(stateData[4]);
		this.type = Integer.parseInt(stateData[5]);
		
	}
	
	// when sending from client when instantiated
	public ConnectPacket(String username, double x, double y, char direction, float health, int type){
		super(1);
		this.username = username;
		this.x = x; 
		this.y = y;
		this.direction = direction;
		this.health = health;
		this.type = type;
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
		return ("1"+this.username+"/"+getX()+"/"+getY()+"/"+getDirection()+"/"+getHealth()+"/"+getType()).getBytes();
	}
	
	
	public String getUsername(){
		return this.username;
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
	
	public char getDirection(){
		return this.direction;
	}
	
	public void setDirection(char direction){
		this.direction = direction;
	}
	
	public float getHealth() {
		return this.health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}

	public void settype(int type){
		this.type = type;
	}
	
	public int getType() {
		return this.type;
	}
	
	
}
