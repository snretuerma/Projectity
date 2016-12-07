package chat;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ChatThread extends Thread{
	private DataInputStream input = null;
	private DataOutputStream output = null;
	private Socket client = null;
	private static ArrayList<Socket> clientList =  new ArrayList<Socket>();
	@SuppressWarnings("static-access")
	public ChatThread(Socket client, ArrayList<Socket> clientList){
		this.client = client;
		this.clientList = clientList;
	}
	
	@SuppressWarnings("static-access")
	public void run(){		
		ArrayList<Socket> clientList = this.clientList;
		try{
			input = new DataInputStream(client.getInputStream());
			output = new DataOutputStream(client.getOutputStream());
			//output.writeUTF("Username: ");
			//String username = input.readUTF().trim();
			
			for(int i = 0; i < clientList.size(); i++){
				DataOutputStream broadcast = new DataOutputStream(clientList.get(i).getOutputStream());
				//broadcast.writeUTF("Server : " + username + " Connected");
			}
			
			while(true){
				String message = input.readUTF();
				
				for(int i = 0; i < clientList.size(); i++){
					DataOutputStream broadcast = new DataOutputStream(clientList.get(i).getOutputStream());
					broadcast.writeUTF(message);
				}
			}
			
		}catch(Exception e){
			System.out.println(client.getLocalSocketAddress() + " Disconnected");
			try{
				output.close();
				input.close();
				clientList.remove(clientList.indexOf(client));
				client.close();
			}catch(Exception e1){
				clientList.remove(clientList.indexOf(client));
			}
			
		}
		
	}
	
}
