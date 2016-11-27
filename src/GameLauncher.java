import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import chat.ChatGUI;
import game.Game;
import game.WindowHandler;

@SuppressWarnings("serial")
public class GameLauncher extends JFrame implements WindowListener{
	Game game;
	public GameLauncher(){
		super("TANK2D");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setPreferredSize(new Dimension(1000,1000));
		JPanel gamePanel = new JPanel();
		game = new Game();
		gamePanel.add(game);
		
//		JPanel chatpane = new JPanel();
//		chatpane.setPreferredSize(new Dimension(300, 1000));
//		ChatGUI chat = new ChatGUI();
//		chatpane.add(chat);
		this.add(gamePanel);
//		this.add(chatpane);
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args){
		GameLauncher launcher = new GameLauncher();
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		game.windowHandler.windowClosed(e);
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
