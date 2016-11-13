package game;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	private BufferedImage image;
	
	public SpriteSheet(BufferedImage image){
		this.image = image;
	}
	
	// get subimage in the spritesheet
	public BufferedImage getImage(int col, int row, int width, int height){
		BufferedImage img = image.getSubimage((col*32)-32, (row*32)-32, width, height);			// getting a 32 x 32 pixels cell
		return img;
	}
	
}
