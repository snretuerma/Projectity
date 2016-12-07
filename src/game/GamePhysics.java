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
	
	
	public static float projectileCollision(Entity entity, ArrayList<Bullet> projectileList, ArrayList<Entity> entityList){
		for(int i = 0; i < projectileList.size(); i++){
			if(!projectileList.isEmpty()){
				if(entity.getBounds().intersects(projectileList.get(i).getBounds())){
					//System.out.println("Projectile username: " + projectileList.get(i).getUsername() + " Entity Username: " + entity.getUsername());
					if(!entity.getUsername().equals(projectileList.get(i).getUsername())){
						//System.out.println(entity.getUsername()+ ((Player) entity).getHealth());
						if(((Player) entity).getHealth()-5 <= 0){
							//System.out.println(entity.getUsername() + " was killed by " + projectileList.get(i).getUsername());
							
							for(int j = 0; j < entityList.size(); j++){
								if(projectileList.get(i).getUsername().equals(entityList.get(j))){
									((Player) entityList.get(j)).setScore(((Player) entityList.get(j)).getScore()+1);
									System.out.println(((Player) entityList.get(j)).getScore());
								}
							}
						}
						projectileList.remove(projectileList.get(i));
						return (float) 5;
					}
					
				}else if(projectileList.get(i).getX() < 0 || projectileList.get(i).getX() > 800 || projectileList.get(i).getY() > 740 || projectileList.get(i).getY() < 0){
					projectileList.remove(projectileList.get(i));
				}
			}
			
		}
		
		return (float) 0;
	}
	
	private int getPlayerIndex(ArrayList<Entity> entityList, Entity player){
		int index = 0;
		String username = player.getUsername();
		for(Entity p : entityList){
			if(p.getUsername().equals(username)){
				break;
			}
			index++;
		}
		return index;
	}
	
	private int getProjectileIndex(ArrayList<Bullet> projectileList, Bullet bullet){ 
		int index = 0;
		String username = bullet.getUsername();
		for(Entity b : projectileList){
			if(b.getUsername().equals(username)){
				break;
			}
			index++;
		}
		return index;
	}
}
