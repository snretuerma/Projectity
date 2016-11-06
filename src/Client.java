import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{
	private static boolean connected = true;
	private static Scanner scanner = null;
	private static Socket client = null;
	private static DataOutputStream dataOutput = null;
	private static DataInputStream dataInput = null;
	
	public static void main(String[] args){
		try{
			scanner = new Scanner(System.in);
			String servername = "localhost";
			System.out.print("Port: ");
			int port = Integer.parseInt(scanner.nextLine());
			
			try{
				client = new Socket(servername, port);
				System.out.println("Connecting to " + servername + " on port " + port );
				System.out.println("Connected to " + client.getRemoteSocketAddress());
				
				dataOutput = new DataOutputStream(client.getOutputStream());
				dataInput = new DataInputStream(client.getInputStream());
				
				if(client!=null && dataOutput!=null && dataInput!=null){
					new Thread(new Client()).start();
					
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

	@Override
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
	
}
