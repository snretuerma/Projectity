package game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	public static final int width = 390;
	public static final int height = width/12*9;
	public static final int scale = 2;
	public final String title = "Tankz2D";
	private boolean running = false;
	private Thread thread;		
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	private BufferedImage background = null;
	
	private boolean shooting = false;
	
	private Textures texture = null;
	private Player player = null;
	private GameController cartridge = null;
	
	
	public void init(){
		requestFocus();
		BufferedImageLoader loader = new BufferedImageLoader();
		
		try {
			spriteSheet = loader.loadImage("/zzz.png");
			background = loader.loadImage("/map.png");
		} catch (IOException e) {
			System.out.println("Image not found");
		}
		
		addKeyListener(new KeyInput(this));
		texture = new Textures(this);
		player = new Player(200, 200, texture);
		cartridge = new GameController(this, texture);
	}
	
	private synchronized void start(){
		if(running ){
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
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
		final double maxfps = 60.0;
		double nsec = 1000000000/maxfps;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		long timer = System.currentTimeMillis();
		
		while(running){
			long time = System.nanoTime();
			delta += (time - prevtime)/nsec;	
			prevtime = time;								// update the time 
			if(delta >= 1){
				update();									// do updates per second
				updates++;
				delta--;
			}
			
			render();										// render frames
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("Updates : " + updates + " FPS: " + frames);
				
				// reset updates and frame counter
				updates = 0;
				frames = 0;
			}
			
		}
		stop();
	}
	
	private void update(){
		player.update();
		cartridge.update();
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
		 cartridge.render(graphics);
		 ///////////////////////////////////////////////////
		 graphics.dispose();
		 bufferStrategy.show();
	}
	
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_RIGHT){
			player.setVelocityX(3);
		}else if(key == KeyEvent.VK_LEFT){
			player.setVelocityX(-3);
		}else if(key == KeyEvent.VK_DOWN){
			player.setVelocityY(3);
		}else if(key == KeyEvent.VK_UP){
			player.setVelocityY(-3);
		}else if(key == KeyEvent.VK_SPACE && !shooting){
			shooting = true;
			cartridge.addBullet(new Bullet(player.getX(), player.getY(), texture));
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
	
	
	public static void main(String[] args){
		Game game = new Game();
		
		game.setPreferredSize(new Dimension(width*scale, height*scale));
		game.setMaximumSize(new Dimension(width*scale, height*scale));
		game.setMinimumSize(new Dimension(width*scale, height*scale));
		
		JFrame	frame = new JFrame(game.title);
		frame.add(game);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		game.start();
	}
	
}
