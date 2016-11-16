package game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInputHandler extends KeyAdapter{
	
	Game game;
	
	public KeyInputHandler(Game game){
		this.game = game;
		game.addKeyListener(this);
	}
	
	public void keyPressed(KeyEvent e){
		game.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e){
		game.keyReleased(e);
	}
	
}
