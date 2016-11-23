package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.net.InetAddress;

import network.packets.StatePacket;

public class Player extends GameObject implements Entity{
	private double velocityX = 0;
	private double velocityY = 0;
	private Texture texture = null;
	private char direction;
	private String username;
	public InetAddress address; 
	public int port;
	private KeyInputHandler input = null;
	private double prevX;
	private double prevY;
	private float health;
	int type;
	
	public Player(Game game, double x, double y, char direction, float health, int type, String username, KeyInputHandler input, Texture texture, InetAddress address, int port){
		super(game, x, y);
		this.username = username;
		this.input = input;
		this.texture = texture;
		this.address = address;
		this.port = port;
		this.prevX = x;
		this.prevY = y;
		this.direction = direction;
		this.health = health;
		this.type = type;
	}
	
	public void update(){
			// for smoother movement
			x+=velocityX;
			y+=velocityY;

			// send packet only when there are movements in the player
			if(prevX != x || prevY != y){
				prevX = x;
				prevY = y;
				StatePacket packet = new StatePacket(this.getUsername(), this.x, this.y, this.direction, this.health, this.type);
				packet.writeData(game.client);					
			}
			
			if(game.getGameController().getEntityList().size() > 1){
				if(hasCollided() == 1){
					if(this.direction == 'u'){
						//this.direction = 'd';
						this.y = y + 5;
						this.x = x + 2;
					}else if(this.direction == 'd'){
						//this.direction = 'u';
						this.y = y - 5;
						this.x = x - 2;
					}else if(this.direction == 'r'){
						//this.direction = 'l';
						this.y = y - 2;
						this.x = x - 5;
					}else if(this.direction == 'l'){
						//this.direction = 'r';
						this.y = y + 2;
						this.x = x + 5;
					}
					this.health -=0.2;
					//System.out.println("Collision Detected | ("+ x+ "," + y +") | " +"("+ prevX + "," + prevY+") " + "Current HP: " + this.health + " Direction: " + this.direction);
				}
			}
			
			// for window boundary detection
			if(x <= 0) x = 0;
			if(x>=757) x=757;
			if(y <= 0) y = 0;
			if(y >= 555) y = 555;
			
	}
	
	public void render(Graphics g){
		g.drawImage(texture.enemy, (int)x, (int)y, null);
		// @ TODO render different texture for enemy players to differentiate player controlled unit
	
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
	
	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}
	
	public int hasCollided(){
		if(GamePhysics.collision(this, this.game.getGameController().getEntityList())){
			return 1;
		}
		return 0;
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
	
	public void setType(int type){
		this.type = type;
	}
	
	public int getType(){
		return type;
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

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}
}
