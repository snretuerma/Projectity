import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args){
		try{
			String servername = args[0];
			int port = Integer.parseInt(args[1]);
			Scanner messageScanner = new Scanner(System.in);
			try{
				Socket client = new Socket(servername, port);
				System.out.println("Connecting to " + servername + " on port " + port );
				System.out.println("Connected to " + client.getRemoteSocketAddress());
				boolean connected = true;
				while(connected){
					try{
						
						String message = messageScanner.nextLine();
						OutputStream toServer = client.getOutputStream();
						DataOutputStream dataOutput = new DataOutputStream(toServer);
						dataOutput.writeUTF("Client " + client.getLocalSocketAddress() + " message: " + message);
						
						InputStream fromServer = client.getInputStream();
						DataInputStream dataInput = new DataInputStream(fromServer);
						System.out.println("Server: " + dataInput.readUTF());
						
					}catch(Exception e){
						client.close();
						messageScanner.close();
						connected = false;
						e.printStackTrace();
					}
				}
				
			}catch(Exception e){
				System.out.println("Can't connect to server");
				messageScanner.close();
			}
		}catch(Exception e){
			System.out.println("Usage: java Client <servername> <port>");
		}
		
	}
}
