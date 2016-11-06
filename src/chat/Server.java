package chat;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server{
	private static ServerSocket serverSocket = null;
	private static Socket client = null;
	private static final ArrayList<Socket> clientList = new ArrayList<Socket>();
	
	public static void main(String[] args){
		Scanner scanner = new Scanner(System.in);
		System.out.print("Port: ");
		int port = Integer.parseInt(scanner.nextLine());
		System.out.println("Listening to port " + port);
			
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
			System.out.println("Invalid port");
			try {
				serverSocket.close();
				client.close();
			} catch (IOException e1) {}
			clientList.clear();
			
		}
		scanner.close();
	}
}
