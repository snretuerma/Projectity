/**
 * TEST FRAME 
**/

package gamesetup;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

public class GameFrame extends BasicGame{
	private TiledMap map1 = null;
	private float posx, posy;
	
	// Projectile and Player sprite constructor  
	private Projectile[] bullet = null;
	private int currentFireTime = 0;
	private int fireRate = 250;
	private int currentProjectile = 0;
	
	public GameFrame(){
		super("TankzZzZ");
	}

	@Override
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		//map1.render(0, 0);
		//graphics.fillOval(posx*32, posy*32, 32, 32);
		
		for(Projectile p : bullet){
			p.render(container, graphics);
		}
		
	}

	@Override
	public void init(GameContainer container) throws SlickException {
//		map1 = new TiledMap("components/map1.tmx");
//		posx = 5;
//		posy = 5;
		bullet = new Projectile[8];
		for(int i = 0; i < bullet.length; i++){
			bullet[i] = new Projectile(); // Create set of dead/inactive Projectiles
		}
		
		// (new Vector2f(100,100), new Vector2f(100,100) )
	}

	@Override
	public void update(GameContainer container, int t) throws SlickException {
//		int objectLayer = map1.getLayerIndex("Terrain");
//		map1.getTileId(0, 0, objectLayer);
//		container.getInput().enableKeyRepeat();
//		if(container.getInput().isKeyPressed(Input.KEY_RIGHT)){
//			posx = (float) (posx+0.5);
//		}else if(container.getInput().isKeyPressed(Input.KEY_LEFT)){
//			posx = (float) (posx-0.5);
//		}else if(container.getInput().isKeyPressed(Input.KEY_UP)){
//			posy = (float) (posy-0.5);
//		}else if(container.getInput().isKeyPressed(Input.KEY_DOWN)){
//			posy = (float) (posy+0.5);
//		}
//		else if(container.getInput().isKeyPressed(Input.KEY_SPACE)){
//			System.out.println("fire is pressed");
//		}
//		
		currentFireTime += t;
		//System.out.println(currentFireTime);
		if(currentFireTime > fireRate && container.getInput().isKeyPressed(Input.KEY_SPACE)){
			bullet[currentProjectile] = new Projectile(new Vector2f(0,0), new Vector2f(1000,1000));
			//System.out.println(currentProjectile);
			currentProjectile++;
			if(currentProjectile >= bullet.length){
				currentProjectile = 0;
				currentFireTime = 0;
			}
		}
		
		for(Projectile p : bullet){
			p.update(t);
		}
	}
	
	
}
