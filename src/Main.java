import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import gamesetup.GameFrame;

public class Main {
	public static void main(String[] args){
		try
        {
            AppGameContainer app = new AppGameContainer(new GameFrame());
            app.setDisplayMode(500, 500, false);
            app.start();
        }
        catch (SlickException e)
        {
            e.printStackTrace();
        }
	}
}
