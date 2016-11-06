import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ChatThread extends Thread{
	private DataInputStream input = null;
	private DataOutputStream output = null;
	private Socket client = null;
	//ArrayList<Socket> clientThread = new ArrayList<Socket>();
	private ChatThread[] clientThreads;
	private int maxClient;
	
	public ChatThread(Socket client,ChatThread[] clientThreads){
		this.client = client;
		this.clientThreads = clientThreads;
		maxClient = clientThreads.length;
	}
	
	public void run(){		
		int maxClient = this.maxClient;
		ChatThread[] clientThreads = this.clientThreads;
		try{
			input = new DataInputStream(client.getInputStream());
			output = new DataOutputStream(client.getOutputStream());
			output.writeUTF("Username: ");
			String username = input.readUTF().trim();
			while(true){
				String message = input.readUTF();
				for(int i = 0; i < maxClient; i++){
					if(clientThreads[i]!=null){
						clientThreads[i].output.writeUTF(username + ": " + message);
					}
				}
			}
			
		}catch(Exception e){
			System.out.println("Error");
			try{
				output.close();
				input.close();
				client.close();
			}catch(Exception e1){System.out.println(e1.getStackTrace());}
			
		}
		
	}
	
}
