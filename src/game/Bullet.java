package game;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.UUID;

import network.packets.ShootPacket;

public class Bullet implements Entity{
	private double x;
	private double y;
	private char direction;
	private Texture texture = null;
	private String username;
	private double prevX;
	private double prevY;
	private int index;
	private Game game;
	
	public Bullet(Game game, double x, double y, Texture texture, char direction, String username){
		this.game = game;
		this.x = x;
		this.y = y;
		this.texture = texture;
		this.direction = direction;
		this.username = username;
		this.prevX = this.x;
		this.prevY = this.y;
	}

	public void update(){
		// rate of bullet movement depending on direction
		
			
			if(direction == 'u'){
				y-=5;
			}else if(direction == 'd'){
				y+=5;
			}else if(direction == 'r'){
				x+=5;
			}else if(direction == 'l'){
				x-=5;
			}
			//ShootPacket packet = new ShootPacket(this.getUsername(), this.getX(), this.getY(), this.getDirection(), this.getIndex());
		
	}
	
	public void render(Graphics g){
		g.drawImage(texture.bullet, (int)x, (int)y, null);
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}

	@Override
	public void setX(double x) {
		this.x = x;
		
	}

	@Override
	public void setY(double y) {
		this.y = y;
		
	}
	
	public Rectangle getBounds(){
		return new Rectangle((int)x, (int)y, 32, 32);
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public char getDirection(){
		return this.direction;
	}
	
	public void setDirection(char direction){
		this.direction = direction;
	}
	
}
