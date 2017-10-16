package Clientmain;


import Clientmain.Packet.Packet2Answer;
import Clientmain.Packet.*;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

public class CNetworkListener extends Listener {
	private Client client;
	
	public void init(Client client) {
		this.client = client;		
	}
	public void connected(Connection connection) {
		Log.info("[CLIENT] You are connected");				
	}

	public void disconnected(Connection connection) {
		Log.info("[CLIENT] You are disconnected");
	}	
	public void received(Connection c, Object o) {		
			if(o instanceof Packet2Answer)
			{
				System.out.println("server says");
				boolean answer = ((Packet2Answer)o).accepted;
				
				if(answer)
				{				
					System.out.println("Server accepts");
				}						
			}
			if(o instanceof Packet4Theroot)
			{
				Wclient.temproot = ((Packet4Theroot)o).Tehroots;				
			}
			if(o instanceof Packet6insertconfirm)
			{
				Wclient.insertconfirmer = ((Packet6insertconfirm)o).insertok;				
			}			
			if(o instanceof Packet8delconfirm)
			{
				Wclient.deleteconfirmer = ((Packet8delconfirm)o).delok;			
			}
			if(o instanceof Packet10searchconfirm)
			{
				Wclient.searchconfirmer = ((Packet10searchconfirm)o).searchok;			
			}			
			client.close();
	}	
	

}
