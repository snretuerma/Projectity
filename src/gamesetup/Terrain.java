package gamesetup;

import java.util.Random;

public class Terrain {
	private String maps[] = {"components/terrain/Mountain.tmx", "components/terrain/Grass.tmx"};
	
	public String generate(){
		Random random = new Random();
		return maps[random.nextInt((1-0)+1)+0];
	}
	
	
}
