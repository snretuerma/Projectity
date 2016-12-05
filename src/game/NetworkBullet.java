package game;

public class NetworkBullet extends Bullet{

	public NetworkBullet(Game game, double x, double y, Texture texture, char direction, String username) {
		super(game, x, y, texture, direction, username);
		
	}
	
	public void update(){
		super.update();
	}
}
