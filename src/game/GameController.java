package game;

import java.awt.Graphics;
import java.util.LinkedList;

public class GameController {
	private LinkedList<Bullet> container = new LinkedList<Bullet>();
	private LinkedList<Enemy> enemy = new LinkedList<Enemy>();
	private Bullet shot;
	private Enemy spawnedEnemy;
	private Textures texture;
	private Game game;
	
	public GameController(Game game, Textures texture){
		this.game = game;
		this.texture = texture;
		for(int x = 0; x < (Game.width * Game.scale); x+=64){
			addEnemy(new Enemy(x, 0, texture));
		}
	}
	
	public void update(){
		for(int i = 0; i < container.size(); i++){
			shot = container.get(i);
			
			if(shot.getY() < 0 || shot.getX() < 0 || shot.getX() > 780 || shot.getY() > 570){
				removeBullet(shot);
			}
			
			shot.update();
		}
		
		for(int i = 0; i < enemy.size(); i++){
			spawnedEnemy = enemy.get(i);
			spawnedEnemy.update();
		}
	}
	
	public void render(Graphics g){
		for(int i = 0; i < container.size(); i++){
			shot = container.get(i);
			shot.render(g);
		}
		
		for(int i = 0; i < enemy.size(); i++){
			spawnedEnemy = enemy.get(i);
			spawnedEnemy.render(g);
		}
	}
	
	public void addBullet(Bullet bullet){
		container.add(bullet);
	}
	
	public void removeBullet(Bullet bullet){
		container.remove(bullet);
	}
	
	public void addEnemy(Enemy e){
		enemy.add(e);
	}
	
	public void removeEnemy(Enemy e){
		enemy.remove(e);
	}
	
}
