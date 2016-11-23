import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import chat.ChatGUI;
import game.Game;

@SuppressWarnings("serial")
public class GameLauncher extends JFrame{
	public GameLauncher(){
		super("TANK2D");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setPreferredSize(new Dimension(1000,1000));
		JPanel gamePanel = new JPanel();
		Game game = new Game();
		gamePanel.add(game);
		
		JPanel chatpane = new JPanel();
		chatpane.setPreferredSize(new Dimension(300, 1000));
		ChatGUI chat = new ChatGUI();
		chatpane.add(chat);
		this.add(gamePanel);
		this.add(chatpane);
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args){
		new GameLauncher();
	}
}
