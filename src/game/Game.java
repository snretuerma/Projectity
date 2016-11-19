package game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import network.GameClient;
import network.GameServer;
import network.packets.ConnectPacket;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	public static final int width = 390;
	public static final int height = width/12*9;
	public static final int scale = 2;
	public final String title = "Tankz2D";
	public JFrame frame;
	private boolean running = false;
	private Thread thread;		
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	private BufferedImage background = null;
	
	private boolean shooting = false;
	
	public Texture texture = null;
	public Player player;
	public GameController controller = null;
	public KeyInputHandler input = null;
	public WindowHandler windowHandler;
	
	/** Network Sample **/ 
	public GameClient client = null;
	public GameServer server = null;
	private Game game;
	
//	public Game(){
//		this.setPreferredSize(new Dimension(width*scale, height*scale));
//		this.setMaximumSize(new Dimension(width*scale, height*scale));
//		this.setMinimumSize(new Dimension(width*scale, height*scale));
//		this.setVisible(true);
//		this.start();
//	}
	
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
		String username = JOptionPane.showInputDialog(this, "Username");
		player = new NetworkPlayer(game, randomPosition(this.getWidth()*this.scale), randomPosition(this.getHeight()*this.scale), username, input, texture, null, -1);
		controller = new GameController(this, input, texture);
		controller.addEntity(player);
		ConnectPacket  packet = new ConnectPacket(username, player.getX(), player.getY());
		
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
		 ///////////////////////////////////////////////////
		 graphics.dispose();
		 bufferStrategy.show();
	}
	
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_RIGHT){
			player.setVelocityX(3);
			player.setDirection('r');
		}else if(key == KeyEvent.VK_LEFT){
			player.setVelocityX(-3);
			player.setDirection('l');
		}else if(key == KeyEvent.VK_DOWN){
			player.setVelocityY(3);
			player.setDirection('d');
		}else if(key == KeyEvent.VK_UP){
			player.setVelocityY(-3);
			player.setDirection('u');
		}else if(key == KeyEvent.VK_SPACE && !shooting){
			shooting = true;
			controller.addEntity(new Bullet(player.getX(), player.getY(), texture, player.getDirection()));
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
		}else if(key == KeyEvent.VK_SPACE ){
			shooting = false;
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
		
		game.setPreferredSize(new Dimension(width*scale, height*scale));
		game.setMaximumSize(new Dimension(width*scale, height*scale));
		game.setMinimumSize(new Dimension(width*scale, height*scale));
		
		game.frame = new JFrame(game.title);
		game.frame.add(game);
		game.frame.setVisible(true);
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setResizable(false);
		
		
		game.frame.pack();
		game.frame.setLocationRelativeTo(null);
		
		game.start();
	}
	
}
