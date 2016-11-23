package game;

import java.awt.Rectangle;
import java.util.ArrayList;

public class GamePhysics {
	
	public static boolean collision(Entity entity, ArrayList<Entity> entityList){
//		if(entityList.size() > 1){
			for(int i = 0; i < entityList.size(); i++){
				try{
					if(((NetworkPlayer) entityList.get(i)).getBounds().intersects(entity.getBounds()) && ((NetworkPlayer) entity).getUsername() != ((NetworkPlayer) entityList.get(i)).getUsername()){
//						if(((NetworkPlayer) entityList.get(i)).getDirection() == 'u'){
//							((NetworkPlayer) entityList.get(i)).setY(((NetworkPlayer) entityList.get(i)).getY()+5);
//						}else if(((NetworkPlayer) entityList.get(i)).getDirection() == 'd'){
//							((NetworkPlayer) entityList.get(i)).setY(((NetworkPlayer) entityList.get(i)).getY()-5);
//						}else if(((NetworkPlayer) entityList.get(i)).getDirection() == 'r'){
//							((NetworkPlayer) entityList.get(i)).setY(((NetworkPlayer) entityList.get(i)).getX()-5);
//						}else if(((NetworkPlayer) entityList.get(i)).getDirection() == 'l'){
//							((NetworkPlayer) entityList.get(i)).setY(((NetworkPlayer) entityList.get(i)).getX()+5);
//						}
						
						return true;
					}
				}catch(Exception e){}
				
				
			}
			
//		}
		return false;
	}
	
}
