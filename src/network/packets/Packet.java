package network.packets;

import network.GameClient;
import network.GameServer;

public abstract class Packet {
	public static enum PacketTypes{
		INVALID(0), CONNECT(1), DISCONNECT(2), STATE(3);
		
		private int packetID;
		private PacketTypes(int packetID){
			this.packetID = packetID;
		}
		
		public int getID(){
			return packetID;
		}
	}
	
	public byte packetID;
	
	public Packet(int packetID){
		this.packetID = (byte)packetID;
	}
	
	public abstract void writeData(GameClient client);
	public abstract void writeData(GameServer server);
	
	public String readData(byte[] data){
		String message = new String(data).trim();
		return message.substring(1);					// disregard 1st character 
	}
	
	public abstract byte[] getData();
	
	public static PacketTypes findPacket(int packetID){
		for(PacketTypes p : PacketTypes.values()){
			if(p.getID() == packetID){					// find the packet type of the sent ID in the enum and return it else return the INVALID packet type
				return p;
			}
		}
		
		return PacketTypes.INVALID;
	}
}
