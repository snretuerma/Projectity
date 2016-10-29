import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args){
		try{
			Scanner scanner = new Scanner(System.in);
			String servername = "localhost";
			System.out.print("Port: ");
			int port = Integer.parseInt(scanner.nextLine());
			try{
				Socket client = new Socket(servername, port);
				System.out.println("Connecting to " + servername + " on port " + port );
				System.out.println("Connected to " + client.getRemoteSocketAddress());
				boolean connected = true;
				while(connected){
					try{
						System.out.print("Message: ");
						String message = scanner.nextLine();
						OutputStream toServer = client.getOutputStream();
						DataOutputStream dataOutput = new DataOutputStream(toServer);
						dataOutput.writeUTF(message);
						
						InputStream fromServer = client.getInputStream();
						DataInputStream dataInput = new DataInputStream(fromServer);
						System.out.println(dataInput.readUTF());
						
					}catch(Exception e){
						client.close();
						scanner.close();
						connected = false;
						System.out.println("Disconnected from server");
					}
				}
				
			}catch(Exception e){
				System.out.println("Can't connect to server");
				scanner.close();
			}
		}catch(Exception e){
			System.out.println("Usage: java Client <servername> <port>");
		}
		
	}
}
