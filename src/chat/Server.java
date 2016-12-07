package chat;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import game.Game;

public class Server extends Thread{
	private static ServerSocket serverSocket = null;
	private static Socket client = null;
	private static final ArrayList<Socket> clientList = new ArrayList<Socket>();
	Game game;
	public Server(Game game){
		this.game = game;
	};
	
	public void run(){
		int port = 8888;
		game.messageArea.append("Listening to port " + port);
			
		try{	
			serverSocket = new ServerSocket(port);
			
			while(true){
				client = serverSocket.accept();
				if(client.isConnected()){
					clientList.add(client);
					(new ChatThread(client, clientList)).start();;
				}
			}
			
		}catch(Exception e){
			game.messageArea.append("Invalid port");
			try {
				serverSocket.close();
				client.close();
			} catch (IOException e1) {}
			clientList.clear();
			
		}
	}
}
