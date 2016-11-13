package game;

import java.awt.Graphics;
import java.util.LinkedList;

public class Cartridge {
	private LinkedList<Bullet> container = new LinkedList<Bullet>();
	
	private Bullet shot;
	
	private Game game;
	
	public Cartridge(Game game){
		this.game = game;
	}
	
	public void update(){
		for(int i = 0; i < container.size(); i++){
			shot = container.get(i);
			shot.update();
		}
	}
	
	public void render(Graphics g){
		for(int i = 0; i < container.size(); i++){
			shot = container.get(i);
			shot.render(g);
		}
	}
	
	public void addBullet(Bullet bullet){
		container.add(bullet);
	}
	
	public void removeBullet(Bullet bullet){
		container.remove(bullet);
	}
}
