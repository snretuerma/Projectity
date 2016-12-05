package game;

import java.awt.Rectangle;
import java.util.ArrayList;

public class GamePhysics {
	
	public static float collision(Entity entity, ArrayList<Entity> entityList){
//		if(entityList.size() > 1){
			for(int i = 0; i < entityList.size(); i++){
				if(entityList.get(i) instanceof NetworkPlayer && ((NetworkPlayer) entityList.get(i)).getBounds().intersects(entity.getBounds()) && !((NetworkPlayer) entity).getUsername().equals(((NetworkPlayer) entityList.get(i)).getUsername()) ){
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
								
			}
			
//		}
		return (float) 0;
	}
	
	
	public static float projectileCollision(Entity entity, ArrayList<Bullet> projectileList){
		for(int i = 0; i < projectileList.size(); i++){
			if(!projectileList.isEmpty()){
				if(entity.getBounds().intersects(projectileList.get(i).getBounds())){
					//System.out.println("Projectile username: " + projectileList.get(i).getUsername() + " Entity Username: " + entity.getUsername());
					if(!entity.getUsername().equals(projectileList.get(i).getUsername())){
						projectileList.remove(projectileList.get(i));
						return (float) 5;
					}
					
				}
			}
			
		}
		
		return (float) 0;
	}
	
}
