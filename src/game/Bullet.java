package game;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Bullet implements Entity{
	private double x;
	private double y;
	private char direction;
	private Texture texture = null;
	private String username;
	
	public Bullet(double x, double y, Texture texture, char direction, String username){
		this.x = x;
		this.y = y;
		this.texture = texture;
		this.direction = direction;
		this.username = username;
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
