package gamesetup;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Projectile {
	private Vector2f position;
	private Vector2f direction;
	private int time = 0;
	private static int maxTime = 5000;
	private boolean alive = true;
	
	
	public Projectile(Vector2f position, Vector2f direction){
		this.position = position;
		this.direction = direction;
	}
	
	public Projectile(){
		alive = false;
	}
	
	public void update (int t){
		if(alive){
			Vector2f travelSpeed = direction.copy().scale((t/1000.0f));
			position.add(travelSpeed);
			time += t;
			
			if(time > maxTime){
				alive = false;
			}
		}
	}
	
	public void render(GameContainer container, Graphics graphics){
		if(alive){
			graphics.setColor(Color.pink);
			graphics.fillOval(position.getX()-10, position.getY()-10, 20, 20);
		}
		
	}
	
	public boolean isAlive(){
		return alive;
	}
	
}
