package game;

public class GameObject {
	public double x, y;
	public Game game;
	
	public GameObject(Game game, double x, double y){
		this.game = game;
		this.x = x; 
		this.y = y;
	}
}
