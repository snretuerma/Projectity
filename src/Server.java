import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
//import java.util.ArrayList;
import java.util.Scanner;

public class Server extends Thread{
	//private static ArrayList<Thread> clientThreadList = new  ArrayList<Thread>();
	private static ServerSocket serverSocket;
	public static Socket client;
	
	public Server(Socket client) throws IOException{
		Server.client = client;
	}
	
	public void run(){
		boolean connected = true;
		while(connected){
			try {
				DataInputStream in = new DataInputStream(client.getInputStream());
				String message = in.readUTF();
				DataOutputStream out = new DataOutputStream(client.getOutputStream());
				out.writeUTF(client.getLocalAddress() + ": " + message);
			} catch (IOException e1) {
				System.out.println(client.getLocalAddress() + " disconnected");
				connected = false;
				try{
					client.close();
				}catch(Exception e){
					
				}
				
			}
			
		}
	}
	
	public static void main(String[] args){
		try{
			Scanner scanner = new Scanner(System.in);
			System.out.print("Port: ");
			int port = Integer.parseInt(scanner.nextLine());
			System.out.println("Listening to port " + port);
			scanner.close();
			serverSocket = new ServerSocket(port);
			boolean connected = true;
			while(connected){
				client = serverSocket.accept();
				if(client.isConnected()){
					System.out.println(client.getLocalAddress() + " connected");
					Thread clientThread = new Server(client);
					//clientThreadList.add(clientThread);
					clientThread.start();	
				}
				
			}
			
		}catch(Exception e){
			System.out.println("Invalid port input");
		}
	}
}
