package chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ChatGUI extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JButton send = null;
	private TextArea inputArea;
	
	/**
	 * 
	 */
	public ChatGUI(){
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(400,800));
		this.setMaximumSize(new Dimension(400,800));
		this.setMinimumSize(new Dimension(400,800));
		
		
		JPanel listPanel = new JPanel();
		listPanel.setPreferredSize(new Dimension(100,600));
		listPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
		//listPanel.setBackground(Color.YELLOW);
		TextArea listArea = new TextArea();
		listArea.setPreferredSize(new Dimension(90, 670));
		listArea.setBackground(Color.WHITE);
		listArea.setEditable(false);
		listPanel.add(listArea);
		
		
		JPanel chatPanel = new JPanel();
		//chatPanel.setBackground(Color.MAGENTA);
		chatPanel.setBorder(new EmptyBorder(10, 20, 10, 20));
		
		// chat text field
		TextArea chatArea = new TextArea();
		chatArea.setPreferredSize(new Dimension(290,670));
		chatArea.setEditable(false);
		chatArea.setBackground(Color.WHITE);
		chatPanel.add(chatArea);
		
		// panel to hold components for the user input
		JPanel inputPanel = new JPanel();
		inputPanel.setPreferredSize(new Dimension(400,100));
		inputPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
		inputPanel.setLayout(new BorderLayout());
		//inputPanel.setBackground(Color.PINK);
		
		
		// user input field
		inputArea = new TextArea();
		inputArea.setPreferredSize(new Dimension(310,100));
		
		// send button
		send = new JButton("Send");
		send.setPreferredSize(new Dimension(70,50));
		send.addActionListener(this);
		
		inputPanel.add(inputArea, BorderLayout.WEST);
		inputPanel.add(send, BorderLayout.EAST);
		
		this.add(listPanel, BorderLayout.EAST);
		this.add(chatPanel, BorderLayout.CENTER);
		this.add(inputPanel, BorderLayout.PAGE_END);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if(e.getSource() == send){
			System.out.println("Send pressed");
		}
	}
}
