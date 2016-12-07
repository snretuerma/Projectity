package chat;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

import game.Game;

public class Client extends Thread{
	private static boolean connected = true;
	private static Scanner scanner = null;
	private static Socket client = null;
	private static DataOutputStream dataOutput = null;
	private static DataInputStream dataInput = null;
	public Game game;

	
	public Client(Game game){
		this.game = game;
	};
	
	public void run(){
		try{
			scanner = new Scanner(System.in);
			String servername = "localhost";
			int port = 8888;
			
			try{
				client = new Socket(servername, port);
				System.out.println("Connecting to " + servername + " on port " + port );
				System.out.println("Connected to " + client.getRemoteSocketAddress());
				
				dataOutput = new DataOutputStream(client.getOutputStream());
				dataInput = new DataInputStream(client.getInputStream());
				
				if(client!=null && dataOutput!=null && dataInput!=null){
					new Thread(){
			            public void run() {
			            	try{
			        			while(connected){
			        				System.out.println(dataInput.readUTF());
			        			}
			        			
			        		}catch(Exception e){
			        			connected = false;
			        			System.out.println("Disconnected from the server");
			        		}	
			            }
			        }.start();;
					
					while(connected){
						try{
							dataOutput.writeUTF(scanner.nextLine());
						}catch(Exception e){
							System.out.println("Disconnected from server");
						}
					}
					
					client.close();
					scanner.close();
					dataOutput.close();
					dataInput.close();
				}
				
			}catch(Exception e){
				System.out.println("Can't connect to server");
				scanner.close();
			}
		}catch(Exception e){
			System.out.println("Usage: java Client <servername> <port>");
		}
		
	}

//	@Override
//	public void run() {
//		try{
//			while(connected){
//				System.out.println(dataInput.readUTF());
//			}
//			
//		}catch(Exception e){
//			connected = false;
//			System.out.println("Disconnected from the server");
//		}	
//	}
	
}
