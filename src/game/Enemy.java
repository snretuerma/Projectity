package game;

import java.awt.Graphics;

public class Enemy {
	private double x, y;
	private Textures texture = null;
	
	public Enemy(double x, double y, Textures texture){
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
}
