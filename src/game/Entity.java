package game;

import java.awt.Graphics;
import java.awt.Rectangle;

public interface Entity {
	
	public void update();
	
	public void render(Graphics g);
	
	public double getX();
	public double getY();
	public void setX(double x);
	public void setY(double y);
	public String getUsername();
	public void setUsername(String username);
	public Rectangle getBounds();

}
