package game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.UUID;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import network.GameClient;
import network.GameServer;
import network.packets.ConnectPacket;
import network.packets.ShootPacket;

public class Game extends Canvas implements Runnable{

	// Swing components
	private static final long serialVersionUID = 1L;
	public static final int width = 390;
	public static final int height = 400;
	public static final int scale = 2;
	public final String title = "Tankz2D";
	public JFrame frame;
	
	// chatPanel and components
	public JPanel chatPanel;
	
	// infoPanel and components
	public JPanel infoPanel;
	public JPanel timerPanel;
	public JPanel scoreboardPanel;
	public JLabel timerLabel;
	public JLabel timer;
	public JLabel playerListLabel;
	
	private boolean running = false;
	private Thread thread;		
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	private BufferedImage background = null;
	private String username;
	public boolean shooting = false;
	public int bulletIndex = 0;
	
	public Texture texture = null;
	public Player player;
	public Bullet[] bullet = new Bullet[5];
	public GameController controller = null;
	public KeyInputHandler input = null;
	public WindowHandler windowHandler;
	
	/** Network Sample **/ 
	public GameClient client = null;
	public GameServer server = null;
	public Game game;
	
	public void init(){
		requestFocus();
		game = this;
		BufferedImageLoader loader = new BufferedImageLoader();
		
		try {
			spriteSheet = loader.loadImage("/spritesheet.png");
			background = loader.loadImage("/map.png");
		} catch (IOException e) {
			System.out.println("Image not found");
		}
		
		this.input = new KeyInputHandler(this);
		this.windowHandler = new WindowHandler(this);
		texture = new Texture(this);
		username = JOptionPane.showInputDialog(this, "Username");
		player = new NetworkPlayer(game, randomPosition(this.getWidth()*this.scale), randomPosition(this.getHeight()*this.scale), 'u', 100, 0, username, input, texture, null, -1);
		controller = new GameController(this, input, texture);
		controller.addEntity(player);
		
		ConnectPacket  packet = new ConnectPacket(username, player.getX(), player.getY(), player.getDirection(), player.getHealth(), player.getStatus());
		
		if(server != null){
			server.addConnection((NetworkPlayer) player, packet);
		}
		packet.writeData(client);
		
	} 
	
	private synchronized void start(){ 
		if(running ){
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
		//int option = JOptionPane.showConfirmDialog(this, "Create Server");
		if(JOptionPane.showConfirmDialog(this, "Create Server") == 0){
			server = new GameServer(this, texture, input, controller, player);
			server.start();
		} 	
		client = new GameClient(this, "localhost", input, texture);
	    client.start();		  
	}
	
	private synchronized void stop(){
		if(!running){
			return;
		}
		running = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.exit(1);
	}
	
	@Override
	public void run() {
		init();
		long prevtime = System.nanoTime();
		final double maxfps = 80.0;
		double nsec = 1000000000/maxfps;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		long timer = System.currentTimeMillis();
		
		while(running){
			long time = System.nanoTime();
			delta += (time - prevtime)/nsec;	
			prevtime = time;								// update the time 
			boolean render = true;
			while(delta >= 1){
				update();									// do updates per second
				updates++;
				delta--;
				render = true;
			}
			try{
				Thread.sleep(2);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			
			if(render){
				frames++;	
				render();										// render frames
			}
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				//System.out.println("Updates : " + updates + " FPS: " + frames);
				
				// reset updates and frame counter
				updates = 0;
				frames = 0;
			}
			
		}
		stop();
	}
	
	private void update(){
		player.update();
		controller.update();
//		 for(Entity p : controller.getEntityList()){
//			 //System.out.println("Player Name: " + p.getUsername() + " Projectile Count: " +  ((Player) p).getProjectileList().size());
//			 if(!((Player) p).getProjectileList().isEmpty()){
//				 for(Bullet b : ((Player) p).getProjectileList()){
//						b.update(); 
//					 }
//			 }
//		 }
		 
	}
	
	private void render(){
		 BufferStrategy bufferStrategy = this.getBufferStrategy();
		 if(bufferStrategy == null){
			 createBufferStrategy(3);						// do a triple buffer of frames
			 return;
		 }
		 Graphics graphics = bufferStrategy.getDrawGraphics();
		 ////////////////////////////////////////////////////
		 graphics.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		 graphics.drawImage(background, 0, 0, null);
		 player.render(graphics);
		 controller.render(graphics);
//		 for(Entity p : controller.getEntityList()){
//			 if(!((Player) p).getProjectileList().isEmpty()){
//				 for(Bullet b : ((Player) p).getProjectileList()){
//						b.render(graphics); 
//					 }
//			 }
//		 }
		 ///////////////////////////////////////////////////
		 graphics.dispose();
		 bufferStrategy.show();
	}
	
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_RIGHT){
			player.setVelocityX(1);
			player.setDirection('r');
		}else if(key == KeyEvent.VK_LEFT){
			player.setVelocityX(-1);
			player.setDirection('l');
		}else if(key == KeyEvent.VK_DOWN){
			player.setVelocityY(1);
			player.setDirection('d');
		}else if(key == KeyEvent.VK_UP){
			player.setVelocityY(-1);
			player.setDirection('u');
		}else if(key == KeyEvent.VK_SPACE && !shooting){
			shooting = true;
			//@ TODO fire projectile - set another entity for bullet?
//			if(shooting == true){
//				controller.addEntity(new Bullet(player.getX(), player.getY(), texture, player.getDirection(), player.getUsername()));
//				bulletIndex++;
//				System.out.println("Bullet: " + bulletIndex + " Player: " + player.getUsername());
//			}
			//System.out.println(bulletIndex);
			if(shooting == true){
				
//				if(bulletIndex < bullet.length){
//					bullet[bulletIndex] = new Bullet(player.getX(), player.getY(), texture, player.getDirection(), player.getUsername(), bulletIndex, UUID.randomUUID().toString());
//					//System.out.println("Bullet: " + bulletIndex + " Player: " + player.getUsername());
//					ShootPacket packet = new ShootPacket(bullet[bulletIndex].getUsername(), bullet[bulletIndex].getX(), bullet[bulletIndex].getY(), bullet[bulletIndex].getDirection(), bullet[bulletIndex].getIndex());
//					packet.writeData(game.client);
//					bulletIndex++;
//				}
//				else{
//					for(int i = 0; i < bullet.length-1; i++){
//						bullet[i] = null;
//					}
//					bulletIndex = 0;
//				}
				player.isShooting(1);
				Bullet bullet = new Bullet(game, player.x, player.y, game.texture, player.getDirection(), player.getUsername());
//				player.getProjectileList().add(bullet);
				controller.getProjectileList().add(bullet);
				ShootPacket packet = new ShootPacket(player.getUsername(), player.x, player.y, player.getDirection(), player.getStatus());
				packet.writeData(client);
			}

			
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_RIGHT){
			player.setVelocityX(0);
		}else if(key == KeyEvent.VK_LEFT){
			player.setVelocityX(0);
		}else if(key == KeyEvent.VK_DOWN){
			player.setVelocityY(0);
		}else if(key == KeyEvent.VK_UP){
			player.setVelocityY(0);
		}else if(key == KeyEvent.VK_SPACE){
			shooting = false;
			player.isShooting(0);
		}
		
		
	}
	
	
	public double randomPosition(int size){
		double pos;
		pos = (Math.random() * ((size - 2)+1)) + 2;
		return pos;
	}
	
	// Getters and Setters for Game class variables
	public BufferedImage getSpriteSheet(){
		return spriteSheet;
	}
	
	public GameController getGameController(){
		return this.controller;
	}
	
	
	public static void main(String[] args){
		Game game = new Game();
		game.infoPanel = new JPanel();
		game.chatPanel = new JPanel();
		
		game.setPreferredSize(new Dimension(width*scale, height*scale));
		game.setMaximumSize(new Dimension(width*scale, height*scale));
		game.setMinimumSize(new Dimension(width*scale, height*scale));
		
		game.frame = new JFrame(game.title);
		game.frame.setLayout(new BorderLayout());
		game.frame.setVisible(true);
		game.frame.setPreferredSize(new Dimension(1400,height*scale));
		game.frame.setMaximumSize(new Dimension(1400, height*scale));
		game.frame.setMinimumSize(new Dimension(1400, height*scale));
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setResizable(false);
		
		// information panel for score and leaderboard
		game.infoPanel.setPreferredSize(new Dimension(220, height*scale));
		game.infoPanel.setMaximumSize(new Dimension(220, height*scale));
		game.infoPanel.setMinimumSize(new Dimension(220, height*scale));
		game.infoPanel.setLayout(new BorderLayout());
		
		game.timerPanel = new JPanel();
		game.timerPanel.setPreferredSize(new Dimension(220, 100));
		game.timerPanel.setMaximumSize(new Dimension(220, 100));
		game.timerPanel.setMinimumSize(new Dimension(220, 100));
		game.timerPanel.setLayout(new BorderLayout());
		game.timerLabel = new JLabel("Timer",  JLabel.CENTER);
		
		
		game.timer = new JLabel("120");
		game.timer.setFont(new Font("Serif", Font.PLAIN, 50));
		game.timer.setHorizontalAlignment(JLabel.CENTER);
		game.timer.setVerticalAlignment(JLabel.CENTER);
		game.timer.setPreferredSize(new Dimension(50,80));
		
		game.timerPanel.add(game.timerLabel, BorderLayout.NORTH);
		game.timerPanel.add(game.timer, BorderLayout.SOUTH);
		//game.timerPanel.setBackground(Color.BLUE);
		
		
		
		game.scoreboardPanel = new JPanel();
		game.scoreboardPanel.setPreferredSize(new Dimension(220, 700));
		game.scoreboardPanel.setMaximumSize(new Dimension(220, 700));
		game.scoreboardPanel.setMinimumSize(new Dimension(220, 700));
		game.scoreboardPanel.setBackground(Color.ORANGE);
		
		
		
		game.infoPanel.add(game.timerPanel, BorderLayout.NORTH);
		game.infoPanel.add(game.scoreboardPanel, BorderLayout.SOUTH);
		//game.infoPanel.setBackground(Color.ORANGE);
		
		
		
		
		game.chatPanel.setPreferredSize(new Dimension(373, height*scale));
		game.chatPanel.setMaximumSize(new Dimension(373, height*scale));
		game.chatPanel.setMinimumSize(new Dimension(373, height*scale));
		game.chatPanel.setBackground(Color.PINK);
		
		game.frame.add(game.infoPanel, BorderLayout.WEST);
		game.frame.add(game, BorderLayout.CENTER);
		game.frame.add(game.chatPanel, BorderLayout.EAST);
		
		game.frame.pack();
		game.frame.setLocationRelativeTo(null);
		
		game.start();
	}
	
}
