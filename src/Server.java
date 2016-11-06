import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server{
	private static ServerSocket serverSocket = null;
	private static Socket client = null;
	private static final int maxClient = 20;
	private static final ChatThread[] clientThreads = new ChatThread[maxClient];
	
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
					int count;
					for(count = 0; count < maxClient; count++){
						if(clientThreads[count] == null){
							(clientThreads[count] = new ChatThread(client, clientThreads)).start();
							break;
						}
					}
					if(count == maxClient){
						DataOutputStream output = new DataOutputStream(client.getOutputStream());
						output.writeUTF("Can't connect to server");
						output.close();
						client.close();
					}
				}
				
			}
			
		}catch(Exception e){
			System.out.println("Invalid port input");
		}
		scanner.close();
	}
}
