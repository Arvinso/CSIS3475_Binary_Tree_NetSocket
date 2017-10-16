package webserver;

import webserver.Packet.Packet4Theroot;
import webserver.Packet.*;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

public class SNetworkListener extends Listener {
	
	public static BinaryNumTree mainbasetree = new BinaryNumTree();
	
	public void connected(Connection arg0) {
		Log.info("[SERVER] client trying to connect");
		System.out.println("[SERVER] client trying to connect"+arg0.getRemoteAddressTCP());			
		}
	
	public void disconnected(Connection arg0) {
		Log.info("[SERVER] client trying to disconnect");
	}

	public void received(Connection c, Object o) {
		if(o instanceof Packet0Init){
			System.out.println("request from client to init client side");
			
			Packet2Answer reply = new Packet2Answer();
			reply.accepted = true;
			c.sendTCP(reply);
		}	
		if(o instanceof Packet1getroot){					
			Node sendroot = new Node();
			sendroot =  mainbasetree.getRoot();			
			Packet4Theroot packroot = new Packet4Theroot();
			packroot.Tehroots = sendroot;				
			c.sendTCP(packroot);
		}
		if(o instanceof Packet5gettoinsert){
			int insertcandidate = ((Packet5gettoinsert)o).candidate;
			System.out.println("client: wants to insert: "+ insertcandidate);
			boolean insertconform = mainbasetree.insert( insertcandidate);
			System.out.println("insert succes?:"+insertconform);
			Packet6insertconfirm sendIconfirmation = new Packet6insertconfirm();
			sendIconfirmation.insertok = insertconform;			
			c.sendTCP(sendIconfirmation);
		}
		if(o instanceof Packet7gettodelete){
			int delcandidate = ((Packet7gettodelete)o).candidate;
			System.out.println("client: wants to delete: "+ delcandidate);
			boolean deleteconform = mainbasetree.delete( delcandidate);
			System.out.println("delete succes?:" + deleteconform);
			Packet8delconfirm sendDconfirmation = new Packet8delconfirm();
			sendDconfirmation.delok = deleteconform;
			c.sendTCP(sendDconfirmation);
		}
		if(o instanceof Packet9gettosearch){
			int searchcandidate = ((Packet9gettosearch)o).candidate;
			System.out.println("client: wants to search:"+ searchcandidate);
			boolean searchconform = mainbasetree.search( searchcandidate);
			System.out.println("search succes?:"+ searchconform);
			Packet10searchconfirm sendSconfirmation = new Packet10searchconfirm();
			sendSconfirmation.searchok = searchconform;
			c.sendTCP(sendSconfirmation);			
		}
		if(o instanceof Packet11gettoclear){
			System.out.println("Nuke received");
			mainbasetree.clear();
			System.out.println("Tree Nuked");
		}		
	}
}
