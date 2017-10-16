package webserver;

import java.io.IOException;

import webserver.Packet.*;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class Webserver {
	private Server server;	
	public Webserver() throws IOException{
		server = new Server();
		registerPackets();
		server.addListener(new SNetworkListener());
		server.bind(6789);
		server.start();
		System.out.println("Server ready");		
	}
	
	private void registerPackets(){
		Kryo kryo = server.getKryo();	
		kryo.register(Packet0Init.class);
		kryo.register(Packet1getroot.class);
		kryo.register(Packet2Answer.class);
		kryo.register(Packet3Message.class);
		kryo.register(Packet4Theroot.class);
		kryo.register(Packet5gettoinsert.class);
		kryo.register(Packet6insertconfirm.class);
		kryo.register(Packet7gettodelete.class);
		kryo.register(Packet8delconfirm.class);
		kryo.register(Packet9gettosearch.class);
		kryo.register(Packet10searchconfirm.class);
		kryo.register(Packet11gettoclear.class);		
		kryo.register(Node.class);
	}	
	public static void main(String[] args) {
		try {
			new Webserver();
			Log.set(Log.LEVEL_DEBUG);			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
