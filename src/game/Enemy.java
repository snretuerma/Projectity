package game;

import java.awt.Graphics;
import java.awt.Rectangle;

public class Enemy {
	private double x, y;
	private int type;
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

//	@Override
//	public void setX(double x) {
//		this.x = x;
//	}
//
//	@Override
//	public void setY(double y) {
//		this.y = y;
//	}
//
//	@Override
//	public Rectangle getBounds() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
