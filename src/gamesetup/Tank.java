package gamesetup;

import java.net.InetAddress;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Tank {
	private Vector2f position;
	private boolean alive = true;
	private Image sprite = null;
	private int health = 100;
	private int kills = 0;
	
	
	public Tank(Vector2f position){
		this.position = position;
		try {
			sprite = new Image("components/sprites/tank1d.png");
		} catch (SlickException e) {
			System.out.println("Error : Character sprite not found");
		}
		
	}
	
	public void render(GameContainer gameContainer, Graphics graphics){
		if(!isAlive()){
			respawn();
		}
		
		else{
			//@TODO Render projectile  
			graphics.drawImage(sprite, position.getX()-10, position.getY()-10);
			graphics.drawString("Position X:" + position.getX(), 40, 30);
			graphics.drawString("Position Y:" + position.getY(), 40, 50);
		}
	}
	
	public void update(GameContainer container, int time){
		container.getInput().enableKeyRepeat();
		if(container.getInput().isKeyPressed(Input.KEY_SPACE)){
			// @TODO Fire projectile
		}
		
		float delta = (float)time+1;
		if(container.getInput().isKeyPressed(Input.KEY_RIGHT)){
			sprite.setRotation(-90);
			position.add(new Vector2f(delta,0));
		}
		
		if(container.getInput().isKeyPressed(Input.KEY_LEFT)){
			sprite.setRotation(90);
			position.add(new Vector2f(-delta,0));
		}
		
		if(container.getInput().isKeyPressed(Input.KEY_UP)){
			sprite.setRotation(180);
			position.add(new Vector2f(0,-delta));
		}
		
		if(container.getInput().isKeyPressed(Input.KEY_DOWN)){
			sprite.setRotation(360);
			position.add(new Vector2f(0,delta));
		}
		
	}
	
	public void setAlive(boolean alive){
		this.alive = alive;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public int getHealth(){
		return this.health;
	}
	
	public void setHealth(int health){
		this.health = health;
	}
	
	public void die(){
		alive = false;
	}
	
	public void setKills(int kills){
		this.kills = kills;
	}
	
	public int getKills(){
		return kills;
	}
	
	public void respawn(){
		health = 100;
	}
	
	
}
