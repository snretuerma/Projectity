package gamesetup;

import java.util.Random;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

public class Game extends BasicGame{
	private TiledMap map = null;
	Terrain terrain = new Terrain();
	private Panzer tank = null;
	private Image sprite = null;
	int posx = 0, posy = 0;
	
	public Game() {
		super("TankzZz");
	}

	@Override
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		map.render(0, 0);
		//graphics.drawImage(sprite, posx, posy);
		tank.render(container, graphics);
		
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		map = new TiledMap(terrain.generate());
		tank = new Panzer(new Vector2f(randomPosition(map.getHeight()*10),randomPosition(map.getWidth()*10)));	// generate a client player at position
	}

	@Override
	public void update(GameContainer container, int time) throws SlickException {
		tank.update(container, time);
		
	}
	
	public int randomPosition(int size){
		System.out.println("Measure: " + size);
		Random random = new Random();
		int position = random.nextInt((size-0) + 1)+0;
		return position;
	}
	
	
}	
