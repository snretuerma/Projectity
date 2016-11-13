package game;

import java.awt.Graphics;

public class Player extends GameObject{
	private double velocityX = 0;
	private double velocityY = 0;
	private Textures texture;
	
	public Player(double x, double y, Textures texture){
		super(x, y);
		this.texture = texture;
	}
	
	public void update(){
		// for smoother movement
		x+=velocityX;
		y+=velocityY;
		
		// for window boundary detection
		if(x <= 0) x = 0;
		if(x>=780) x=780;
		if(y <= 0) y = 0;
		if(y >= 570) y = 570;
		
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
}
