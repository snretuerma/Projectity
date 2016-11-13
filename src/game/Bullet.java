package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class Bullet {
	private double x;
	private double y;
	Game game;
	
	BufferedImage image;
	
	
	public Bullet(double x, double y, Game game){
		this.x = x;
		this.y = y;
		this.game = game;
		
		SpriteSheet spriteSheet = new SpriteSheet(game.getSpriteSheet());
		image = spriteSheet.getImage(2, 1, 32, 32);
	}

	public void update(){
		y-=5;
	}
	
	public void render(Graphics g){
		g.drawImage(image, (int)x, (int)y, null);
	}
}
