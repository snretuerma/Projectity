import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import gamesetup.Game;

@SuppressWarnings("serial")
public class Main extends JFrame implements ActionListener{
	private JButton setupButton = null;
	private JTextField portField = null;
	
	public Main(){
		super("Setup");
		this.setPreferredSize(new Dimension(400,150));
		this.setVisible(true);
		JPanel setupPanel = new JPanel();
		setupPanel.setLayout(new BorderLayout());
		setupPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
		this.setContentPane(setupPanel);
		
		JPanel inputPanel = new JPanel();
		JLabel portLabel = new JLabel("Port ");
		portField = new JTextField();
		portField.setPreferredSize(new Dimension(200, 25));
		inputPanel.add(portLabel);
		inputPanel.add(portField);
		
		JPanel buttonPanel = new JPanel();
		setupButton = new JButton("OK");
		setupButton.addActionListener(this);
		buttonPanel.add(setupButton);
		
		setupPanel.add(inputPanel, BorderLayout.PAGE_START);
		setupPanel.add(buttonPanel, BorderLayout.PAGE_END);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
	}
	
	public static void main(String[] args){
		new Main();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if(button == setupButton){
			try{
				int port = Integer.parseInt(portField.getText());
				System.out.println("Port input: " + port);
				
				this.setVisible(false);
				
				try
		        {
		            
					AppGameContainer app = new AppGameContainer(new Game());
		            app.setDisplayMode(500, 500, false);
		            app.start();
		        }
		        catch (SlickException slickError)
		        {
		        	slickError.printStackTrace();
		        }
			}catch(Exception err){
				System.out.println("Invalid port number");
			}
			
		}
		
	}
}
