import javax.swing.JFrame;

import chat.ChatGUI;

public class GUITestMain extends JFrame{

	private static final long serialVersionUID = 1L;
	GUITestMain(){
		super("Test Frame");
		this.setVisible(true);
		
		this.add(new ChatGUI());
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
	}
	public static void main(String[] args){
		new GUITestMain();
	}
}
