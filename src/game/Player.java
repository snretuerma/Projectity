package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Player {
	private double x;
	private double y;
	private double velocityX = 0;
	private double velocityY = 0;
	
	private BufferedImage playerSprite = null;
	
	public Player(double x, double y, Game game){
		this.x = x;
		this.y = y;
		SpriteSheet spriteSheet = new SpriteSheet(game.getSpriteSheet());
		playerSprite = spriteSheet.getImage(1, 1, 32, 32);
	}
	
	public void update(){
		// for smoother movement
		x+=velocityX;
		y+=velocityY;
		
		if(x <= 0) x = 0;
		if(x>=780) x=780;
		if(y <= 0) y = 0;
		if(y >= 570) y = 570;
		
	}
	
	public void render(Graphics g){
		g.drawImage(playerSprite, (int)x, (int)y, null);
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
