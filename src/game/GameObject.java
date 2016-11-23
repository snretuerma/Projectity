package game;

import java.awt.Rectangle;

public class GameObject {
	public double x, y;
	public Game game;
	
	public GameObject(Game game, double x, double y){
		this.game = game;
		this.x = x; 
		this.y = y;
	}
	
	public Rectangle getBounds(int width, int height){
		return new Rectangle((int)x, (int)y, width, height);
	}
}
