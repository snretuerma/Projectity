package gamesetup;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

public class Game extends BasicGame{
	private TiledMap map = null;
	Terrain terrain = new Terrain();
	private Tank tank = null;
	private Image sprite = null;
	int posx = 0, posy = 0;
	private int port;
	private InetAddress address;
	private String username;
	
	
	public Game(int port, String username) throws UnknownHostException {
		super("TankzZz");
		this.port = port;
		this.username = username;
		this.address = address.getByName("230.0.0.1");
	}

	@Override
	public void render(GameContainer container, Graphics graphics) throws SlickException {
		map.render(0, 0);
		//graphics.drawImage(sprite, posx, posy);
		tank.render(container, graphics);
		
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		map = new TiledMap(terrain.generate());
		tank = new Tank(new Vector2f(randomPosition(map.getHeight()*10),randomPosition(map.getWidth()*10)));	// generate a client player at position
	}

	@Override
	public void update(GameContainer container, int time) throws SlickException {
		tank.update(container, time);
		
	}
	
	public int randomPosition(int size){
		System.out.println("Measure: " + size);
		Random random = new Random();
		int position = random.nextInt((size-0) + 1)+0;
		return position;
	}
	
	public InetAddress getHostAddress(){
		return address;
	}
	
	public int getPort(){
		return port;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String playerStata(){
		String player = "";
		player+=username+"/";
		player+=posx+"/";
		player+=posy+"/";
		player+=tank.getHealth()+"/";
		player+=tank.getKills();
		return player;	
	}
	
}	
