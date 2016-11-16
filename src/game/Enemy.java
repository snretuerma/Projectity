package game;

import java.awt.Graphics;

public class Enemy implements Entity{
	private double x, y;
	private Texture texture = null;
	
	public Enemy(double x, double y, Texture texture){
		this.x = x;
		this.y = y;
		this.texture = texture;
	}
	
	public void update(){
		y += 5;
	}
	
	public void render(Graphics g){
		g.drawImage(texture.enemy, (int)x, (int)y, null);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
