package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.net.InetAddress;
import java.util.ArrayList;

import network.packets.ShootPacket;
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
	private int score;
	private int status;
	private String lastCollided = null;
	private ArrayList<Bullet> projectile = new ArrayList<Bullet>();
	
	public Player(Game game, double x, double y, char direction, float health, int status, String username, int score, KeyInputHandler input, Texture texture, InetAddress address, int port){
		super(game, x, y);
		this.username = username;
		this.input = input;
		this.texture = texture;
		this.address = address;
		this.port = port;
		this.score = score;
		this.prevX = x;
		this.prevY = y;
		this.direction = direction;
		this.health = health;
		this.status = status;
	}
	
	public void update(){
			// for smoother movement
			x+=velocityX;
			y+=velocityY;
			float bumpDamage = hasCollidedEnemy();
			float hitDamage = hasCollidedProjectile();
			// send packet only when there are movements in the player or if the player is damaged
			if(prevX != x || prevY != y || bumpDamage > 0 || hitDamage> 0 || this.health == 0 || status == 1){
				prevX = x;
				prevY = y;
				
				// for player collision damage
				if(bumpDamage == (float)0.2){
				
					
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
					
					if(this.health > 0){
						this.health -=bumpDamage;
					}else if(health < 0){
						respawn();
					}
					
				// for projectile damage
				}else if(hitDamage == (float)5.0){
					this.health -=5;
				}
				
				if(this.health < 0){
					respawn();
				}
				
				StatePacket packet = new StatePacket(this.getUsername(), this.x, this.y, this.direction, this.health, this.status, this.score);
				packet.writeData(game.client);
				
			}
			
//			if(!projectile.isEmpty()){
//				for(Bullet bullet : projectile){
//					bullet.update();
//				}
//			}
			
			// for window boundary detection
			if(x <= 0) x = 0;
			if(x>=game.width*game.scale-12) x=game.width*game.scale-13;
			if(y <= 0) y = 0;
			if(y >=740) y = 740;
			
	}

	public void render(Graphics g){
		if(direction == 'u'){
			g.drawImage(texture.playerUp, (int)x, (int)y, null);
		}else if(direction == 'd'){
			g.drawImage(texture.playerDown, (int)x, (int)y, null);
		}else if(direction == 'l'){
			g.drawImage(texture.playerLeft, (int)x, (int)y, null);
		}else if(direction == 'r'){
			g.drawImage(texture.playerRight, (int)x, (int)y, null);
		}
		
		
		// @ TODO render different texture for enemy players to differentiate player controlled unit
		
		 g.setColor(Color.gray);
		 g.fillRect((int)this.x-9, (int)this.y-15, 50, 5);
		 g.setColor(Color.green);
		 g.fillRect((int)this.x-9, (int)this.y-15, (int)this.health/2, 5);
		 g.setColor(Color.white);
		 g.drawRect((int)this.x-9, (int)this.y-15, 50, 5);
		 
//		 if(!projectile.isEmpty()){
//				for(Bullet bullet : projectile){
//					bullet.render(g);
//				}
//			}
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
	
	public float hasCollidedEnemy(){
		float damage = (float)0;
		if(GamePhysics.collision(this, this.game.getGameController().getEntityList()) != 0){
			damage = (float) 0.2;
		}
		
		return damage;
	}
	
	public float hasCollidedProjectile(){
		float damage = (float)0;
		if(GamePhysics.projectileCollision(this, this.game.getGameController().getProjectileList(), this.game.getGameController().getEntityList()) != 0){
			damage = (float) 5;
			
		}
		
		return damage;
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
		state+=this.status;
		
		return state;
	}
	
	
	public String getUsername(){
		return username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public void setStatus(int status){
		this.status = status;
	}
	
	public int getStatus(){
		return status;
	}
	
	public int getScore(){
		return this.score;
	}
	
	public void setScore(int score){
		this.score = score;
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
	
	public void isShooting(int status){
		setStatus(status);
		//projectile.add(new Bullet(x, y, texture, direction, username));
		
	}
	
	public ArrayList<Bullet> getProjectileList(){
		return this.projectile;
	}
	
	public Texture getTexture(){
		return this.texture;
	}
	
	
}
