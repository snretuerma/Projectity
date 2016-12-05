/*
 * Texture class for loading the images for the texture of each game component to prevent reloading the images for every component created
 */

package game;

import java.awt.image.BufferedImage;

public class Texture {
	
	public BufferedImage playerUp, playerDown, playerLeft, playerRight, bulletUp, bulletDown, bulletRight, bulletLeft, enemy;
	private SpriteSheet spritesheet = null;
	
	public Texture(Game game){
		spritesheet = new SpriteSheet(game.getSpriteSheet());
		getTextures();
	}
	
	public void getTextures(){
		playerUp = spritesheet.getImage(1, 1, 32, 32);
		playerDown  = spritesheet.getImage(1, 2, 32, 32);
		playerRight  = spritesheet.getImage(1, 3, 32, 32);
		playerLeft  = spritesheet.getImage(1, 4, 32, 32);
		bulletUp = spritesheet.getImage(2, 1, 32, 32);
		bulletDown = spritesheet.getImage(2, 2, 32, 32);
		bulletRight = spritesheet.getImage(2, 3, 32, 32);
		bulletLeft = spritesheet.getImage(2, 4, 32, 32);
	}
	
	
}
