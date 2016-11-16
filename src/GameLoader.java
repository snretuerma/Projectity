import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import chat.ChatGUI;
import game.Game;

@SuppressWarnings("serial")
public class GameLoader extends JFrame{
	public GameLoader(){
		super("TANK2D");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setPreferredSize(new Dimension(1000,1000));
		JPanel gamePanel = new JPanel();
		Game game = new Game();
		gamePanel.add(game);
		
		ChatGUI chat = new ChatGUI();
		this.add(gamePanel);
		this.add(chat);
		this.pack();
		this.setLocationRelativeTo(null);
	}
}
