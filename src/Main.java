import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import chat.ChatGUI;

@SuppressWarnings("serial")
public class Main extends JFrame implements ActionListener{
	private JButton serverButton = null;
	private JButton clientButton = null;
	
	public Main(){
		super("Game Setup");
		
		this.setPreferredSize(new Dimension(400,150));
		this.setVisible(true);
		JPanel setupPanel = new JPanel();
		setupPanel.setLayout(new BorderLayout());
		setupPanel.setBorder(new EmptyBorder(20, 30, 20, 30));
		this.setContentPane(setupPanel);
		
		JPanel labelPanel = new JPanel();
		JLabel setupLable = new JLabel("Choose Action:");
		labelPanel.add(setupLable);
		
		JPanel buttonPanel = new JPanel();
		serverButton = new JButton("Create Game");
		serverButton.addActionListener(this);
		serverButton.setPreferredSize(new Dimension(150,30));
		buttonPanel.add(serverButton);
		
		clientButton = new JButton("Join Game");
		clientButton.addActionListener(this);
		clientButton.setPreferredSize(new Dimension(150,30));
		buttonPanel.add(clientButton);
		
		setupPanel.add(labelPanel, BorderLayout.PAGE_START);
		setupPanel.add(buttonPanel, BorderLayout.PAGE_END);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args){
		new Main();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		if(button == serverButton){
//			JPanel serverOptionPanel = new JPanel();
//			JLabel portLabel = new JLabel("Port ");
//			JLabel usernameLabel = new JLabel("Username: ");
//			
//			JTextField portField = new JTextField();
//			portField.setPreferredSize(new Dimension(200, 25));
//			JTextField usernameField = new JTextField();
//			usernameField.setPreferredSize(new Dimension(200, 25));
//			
//			serverOptionPanel.add(portLabel);
//			serverOptionPanel.add(portField);
//			
//			serverOptionPanel.add(usernameLabel);
//			serverOptionPanel.add(usernameField);
//			
//			int result = JOptionPane.showConfirmDialog(null, serverOptionPanel, "Create Game", JOptionPane.OK_CANCEL_OPTION);
//			if(result == JOptionPane.OK_OPTION){
//				System.out.println(Integer.parseInt(portField.getText()));
//				System.out.println(usernameField.getText());
//				if(portField.getText().isEmpty() || usernameField.getText().isEmpty()){
//					// @TODO Put this in a pop up dialog box 
//					System.out.println("Incorrect input");
//				}else{
//					this.dispose();
//					new ChatGUI();
//				}
//				
//			}
			
			JPanel serverOptionPanel = new JPanel();
			JLabel usernameLabel = new JLabel("Username: ");
			
			JTextField usernameField = new JTextField();
			usernameField.setPreferredSize(new Dimension(200, 25));
			
			serverOptionPanel.add(usernameLabel);
			serverOptionPanel.add(usernameField);
			
			int result = JOptionPane.showConfirmDialog(null, serverOptionPanel, "Create Game", JOptionPane.OK_CANCEL_OPTION);
			if(result == JOptionPane.OK_OPTION){
				if(usernameField.getText().isEmpty()){
					// @TODO Put this in a pop up dialog box 
					System.out.println("Incorrect input");
				}else{
					System.out.println("Username: " + usernameField.getText());
					this.dispose();
					new GameLauncher();
				}
				
			}
			
			
			
		}
		
		if(button == clientButton){
			JPanel serverOptionPanel = new JPanel();
			JLabel usernameLabel = new JLabel("Username: ");
			
			JTextField usernameField = new JTextField();
			usernameField.setPreferredSize(new Dimension(200, 25));
			
			serverOptionPanel.add(usernameLabel);
			serverOptionPanel.add(usernameField);
			
			int result = JOptionPane.showConfirmDialog(null, serverOptionPanel, "Create Game", JOptionPane.OK_CANCEL_OPTION);
			if(result == JOptionPane.OK_OPTION){
				System.out.println(usernameField.getText());
				if(usernameField.getText().isEmpty()){
					// @TODO Put this in a pop up dialog box 
					System.out.println("Incorrect input");
				}else{
					this.dispose();
				}
				
			}
		}
		
	}
}
