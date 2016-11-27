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
			float damage = hasCollided();
			
			// send packet only when there are movements in the player or if the player is damaged
			if(prevX != x || prevY != y || damage > 0 || this.health == 0){
				prevX = x;
				prevY = y;
				
				// for player collision damage
				if(damage == (float)0.2){
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
					this.health -=damage;
				
				// for projectile damage
				}else if(damage == (float)5.0){
					this.health -=5;
					System.out.println(this.username + " (" +this.health+") was hit");					
				}
				
				if(this.health == 0){
					respawn();
				}
				
				StatePacket packet = new StatePacket(this.getUsername(), this.x, this.y, this.direction, this.health, this.type);
				packet.writeData(game.client);					
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
		 g.setColor(Color.gray);
		 g.fillRect((int)this.x-9, (int)this.y-15, 50, 5);
		 g.setColor(Color.green);
		 g.fillRect((int)this.x-9, (int)this.y-15, (int)this.health/2, 5);
		 g.setColor(Color.white);
		 g.drawRect((int)this.x-9, (int)this.y-15, 50, 5);
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
	
	public float hasCollided(){
		return GamePhysics.collision(this, this.game.getGameController().getEntityList());
	}
	
	private void respawn() {
		this.health = 100;
		this.y = game.randomPosition(game.height*game.scale);
		this.x = game.randomPosition(game.width*game.scale);
		this.direction = 'd';
	}
	
	public String getState(){
		String state = "";
		state+=this.username+"/";
		state+=this.x+"/";
		state+=this.y+"/";
		state+=this.direction+"/";
		state+=this.health+"/";
		state+=this.type;
		
		return state;
	}
	
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username){
		this.username = username;
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
