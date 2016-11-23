package game;

import java.awt.Graphics;
import java.util.ArrayList;

public class GameController {
	private ArrayList<Entity> entityList = new ArrayList<Entity>();
	
	private Texture texture;
	private Game game;
	private KeyInputHandler input;
	
	public GameController(Game game, KeyInputHandler input,  Texture texture){
		this.game = game;
		this.input = input;
		this.texture = texture;
	}
	
	// for thread safe access of the entity list
	public synchronized ArrayList<Entity> getEntityList(){
		return entityList;
	}
	
	public void update(){
		for(Entity entity : getEntityList()){
			entity.update();
		}
	}
	
	public void render(Graphics g){
		for(Entity entity : getEntityList()){
			entity.render(g);
		}
	
	}
	
	public void addEntity(Entity e){		
//		if(game.player.getUsername()!=((Player) e).getUsername()){
//			
//			((Player) e).setType(1);
//			System.out.println("Added Username: " + ((Player) e).getUsername() + "| Class type: " + ((Player) e).getType());
//		}
		getEntityList().add(e);
		
//		System.out.println("Player List");
//		for(Entity b : getEntityList()){
//			if(b instanceof NetworkPlayer){
//				System.out.println(((NetworkPlayer) b).getUsername() + " Class type: " + ((NetworkPlayer) b).getType());
//			}
//		}
//		
	}
	
	public void removeEntity(Entity e){
		getEntityList().remove(e);
	}
	
	public synchronized void removePlayerEntity(String username){
		int index = 0;
		for(Entity e : getEntityList()){
			if(e instanceof NetworkPlayer &&((NetworkPlayer) e).getUsername().equals(username)){
				break;
			}
			index++;
		}
		this.getEntityList().remove(index);
	}
	
	private int getPlayerIndex(String username){
		int index = 0;
		for(Entity e : getEntityList()){
			if(e instanceof NetworkPlayer &&((NetworkPlayer) e).getUsername().equals(username)){
				break;
			}
			index++;
		}
		return index;
	}
	
	
	
	public void setState(String username, double x, double y, char direction, float health, int type){
		int index = getPlayerIndex(username);
		this.getEntityList().get(index).setX(x);
		this.getEntityList().get(index).setY(y);
		((NetworkPlayer) this.getEntityList().get(index)).setDirection(direction);
		((NetworkPlayer) this.getEntityList().get(index)).setHealth(health);
		((NetworkPlayer) this.getEntityList().get(index)).setType(type);
		

	}
	
}
