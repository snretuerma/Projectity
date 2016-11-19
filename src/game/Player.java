package game;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.net.InetAddress;

import network.packets.StatePacket;

public class Player extends GameObject implements Entity{
	private double velocityX = 0;
	private double velocityY = 0;
	private Texture texture = null;
	private char direction = 'u';
	private String username;
	public InetAddress address; 
	public int port;
	private KeyInputHandler input = null;
	private double prevX;
	private double prevY;
	
	public Player(Game game, double x, double y, String username, KeyInputHandler input, Texture texture, InetAddress address, int port){
		super(game, x, y);
		this.username = username;
		this.input = input;
		this.texture = texture;
		this.address = address;
		this.port = port;
		this.prevX = x;
		this.prevY = y;
	}
	
	public void update(){
//		if(input!= null){
			// for smoother movement
			x+=velocityX;
			y+=velocityY;
			
			// send packet only when there are movements in the player
			if(prevX != x || prevY != y){
				prevX = x;
				prevY = y;
				StatePacket packet = new StatePacket(this.getUsername(), this.x, this.y);
				packet.writeData(game.client);
			}
			
			// for window boundary detection
			if(x <= 0) x = 0;
			if(x>=757) x=757;
			if(y <= 0) y = 0;
			if(y >= 555) y = 555;
//		}
			
			
			
	}
	
	public void render(Graphics g){
		g.drawImage(texture.player, (int)x, (int)y, null);
	}
	
	public void setX(double x){
		this.x = x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public void setVelocityX(double velocityX){
		this.velocityX = velocityX;
	}
	
	public void setVelocityY(double velocityY){
		this.velocityY = velocityY;
	}
	
	public double getVelocityX(){
		return velocityX;
	}
	
	public double getVelocityY(){
		return velocityY;
	}
	
	public char getDirection(){
		return direction;
	}
	
	public void setDirection(char direction){
		this.direction = direction;
	}
	
	public String getState(){
		String state = "";
		state+=getX()+"/";
		state+=getY()+"/";
		state+=getDirection();
		
		// @TODO add health to player state
		
		return state;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setAddress(InetAddress address){
		this.address = address;
	}
	
	public InetAddress getAddress(){
		return address;
	}

	public void setPort(int port){
		this.port = port;
	}
	
	public int getPort() {
		return port;
	}
	
	public KeyInputHandler getInput(){
		return input;
	}
}
