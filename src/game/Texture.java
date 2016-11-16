/*
 * Texture class for loading the images for the texture of each game component to prevent reloading the images for every component created
 */

package game;

import java.awt.image.BufferedImage;

public class Texture {
	
	public BufferedImage player, bullet, enemy;
	private SpriteSheet spritesheet = null;
	
	public Texture(Game game){
		spritesheet = new SpriteSheet(game.getSpriteSheet());
		getTextures();
	}
	
	public void getTextures(){
		player = spritesheet.getImage(1, 1, 32, 32);
		bullet = spritesheet.getImage(2, 1, 32, 32);
		enemy = spritesheet.getImage(3, 1, 32, 32);
	}
	
	
}
