package game;

import java.awt.Rectangle;
import java.util.ArrayList;

public class GamePhysics {
	
	public static float collision(Entity entity, ArrayList<Entity> entityList){
//		if(entityList.size() > 1){
			for(int i = 0; i < entityList.size(); i++){
				if(entityList.get(i) instanceof NetworkPlayer && ((NetworkPlayer) entityList.get(i)).getBounds().intersects(entity.getBounds()) && ((NetworkPlayer) entity).getUsername() != ((NetworkPlayer) entityList.get(i)).getUsername()){
//						if(((NetworkPlayer) entityList.get(i)).getDirection() == 'u'){
//							((NetworkPlayer) entityList.get(i)).setY(((NetworkPlayer) entityList.get(i)).getY()+5);
//						}else if(((NetworkPlayer) entityList.get(i)).getDirection() == 'd'){
//							((NetworkPlayer) entityList.get(i)).setY(((NetworkPlayer) entityList.get(i)).getY()-5);
//						}else if(((NetworkPlayer) entityList.get(i)).getDirection() == 'r'){
//							((NetworkPlayer) entityList.get(i)).setY(((NetworkPlayer) entityList.get(i)).getX()-5);
//						}else if(((NetworkPlayer) entityList.get(i)).getDirection() == 'l'){
//							((NetworkPlayer) entityList.get(i)).setY(((NetworkPlayer) entityList.get(i)).getX()+5);
//						}
					
					return (float) 0.2;
				}
				if(entityList.get(i) instanceof Bullet && ((Bullet) entityList.get(i)).getBounds().intersects(entity.getBounds()) && ((NetworkPlayer) entity).getUsername() != ((Bullet) entityList.get(i)).getUsername()){
//					if(entity instanceof NetworkPlayer){
//						if(((Bullet) entityList.get(i)).getDirection() == 'u'){
//							((NetworkPlayer) entity).setY(((NetworkPlayer) entity).getY()-1);
//							((NetworkPlayer) entity).setHealth(((NetworkPlayer) entity).getHealth()-5);
//						}
//						if(((Bullet) entityList.get(i)).getDirection() == 'd'){
//							((NetworkPlayer) entity).setY(((NetworkPlayer) entity).getY()+1);
//							((NetworkPlayer) entity).setHealth(((NetworkPlayer) entity).getHealth()-5);
//						}
//						if(((Bullet) entityList.get(i)).getDirection() == 'l'){
//							((NetworkPlayer) entity).setX(((NetworkPlayer) entity).getX()-1);
//							((NetworkPlayer) entity).setHealth(((NetworkPlayer) entity).getHealth()-5);
//						}
//						if(((Bullet) entityList.get(i)).getDirection() == 'r'){
//							((NetworkPlayer) entity).setX(((NetworkPlayer) entity).getX()+1);
//							((NetworkPlayer) entity).setHealth(((NetworkPlayer) entity).getHealth()-5);
//						}
//						
//					}
					return 5;
				}
				
				
			}
			
//		}
		return 0;
	}
	
}
