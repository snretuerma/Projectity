package gamesetup;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class GameFrame extends BasicGame{
	private TiledMap map1 = null;
	private float posx, posy;
	public GameFrame(){
		super("TankzZzZ");
	}

	@Override
	public void render(GameContainer container, Graphics sprite) throws SlickException {
		// TODO Auto-generated method stub
		map1.render(0, 0);
		sprite.fillOval(posx*32, posy*32, 32, 32);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		// TODO Auto-generated method stub
		map1 = new TiledMap("components/map1.tmx");
		posx = 5;
		posy = 5;
		
	}

	@Override
	public void update(GameContainer container, int arg1) throws SlickException {
		// TODO Auto-generated method stub
		int objectLayer = map1.getLayerIndex("Terrain");
		map1.getTileId(0, 0, objectLayer);
		container.getInput().enableKeyRepeat();
		if(container.getInput().isKeyPressed(Input.KEY_RIGHT)){
			posx = (float) (posx+0.5);
		}else if(container.getInput().isKeyPressed(Input.KEY_LEFT)){
			posx = (float) (posx-0.5);
		}else if(container.getInput().isKeyPressed(Input.KEY_UP)){
			posy = (float) (posy-0.5);
		}else if(container.getInput().isKeyPressed(Input.KEY_DOWN)){
			posy = (float) (posy+0.5);
		}
	}
	
	
}
