package game;

import java.awt.Graphics;
import java.util.LinkedList;

public class GameController {
	private LinkedList<Entity> entityList = new LinkedList<Entity>();
	Entity entity;
	
	private Texture texture;
	private Game game;
	private KeyInput input;
	
	public GameController(Game game, KeyInput input,  Texture texture){
		this.game = game;
		this.input = input;
		this.texture = texture;
	}
	
	public void update(){
		for(int i = 0; i < entityList.size(); i++){
			if(input!=null){
				entity = entityList.get(i);
				entity.update();
			}
			
		}
	}
	
	public void render(Graphics g){
		for(int i = 0; i < entityList.size(); i++){
			entity = entityList.get(i);
			entity.render(g);
		}
	}
	
	public void addEntity(Entity e){
		entityList.add(e);
	}
	
	public void removeEntity(Entity e){
		entityList.remove(e);
	}
}
