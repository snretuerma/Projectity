package game;

import java.awt.Graphics;

public class Bullet {
	private double x;
	private double y;
	private Textures texture = null;
	
	public Bullet(double x, double y, Textures texture){
		this.x = x;
		this.y = y;
		this.texture = texture;
	}

	public void update(){
		y-=5;
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
}
