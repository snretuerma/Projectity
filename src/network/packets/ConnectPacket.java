package network.packets;

import network.GameClient;
import network.GameServer;

public class ConnectPacket extends Packet{

	private String username = null;
	private double y;
	private double x;
	private char direction;
	private float health;
	private int status;
	private int score;
	// for data retrieval
	public ConnectPacket(byte[] data) {
		super(1);
		String[] stateData = readData(data).split("/");
		this.username = stateData[0];
		this.x = Double.parseDouble(stateData[1]);
		this.y = Double.parseDouble(stateData[2]);
		this.direction = stateData[3].charAt(0);
		this.health = Float.parseFloat(stateData[4]);
		this.status = Integer.parseInt(stateData[5]);
		this.score = Integer.parseInt(stateData[6]);
	}
	
	// when sending from client when instantiated
	public ConnectPacket(String username, double x, double y, char direction, float health, int status, int score){
		super(1);
		this.username = username;
		this.x = x; 
		this.y = y;
		this.direction = direction;
		this.health = health;
		this.status = status;
		this.score = score;
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
		return ("1"+this.username+"/"+getX()+"/"+getY()+"/"+getDirection()+"/"+getHealth()+"/"+getStatus()+"/"+getScore()).getBytes();
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

	public void setStatus(int status){
		this.status = status;
	}
	
	public int getStatus() {
		return this.status;
	}
	
	public void setScore(int score){
		this.score = score;
	}
	
	public int getScore(){
		return this.score;
	}
	
}
