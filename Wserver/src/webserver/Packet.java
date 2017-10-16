package webserver;
public class Packet {

	public static class Packet0Init{}	
	public static class Packet1getroot{}
	public static class Packet2Answer{boolean accepted = false;}	
	public static class Packet3Message{String message;}
	public static class Packet4Theroot{Node Tehroots;}
	public static class Packet5gettoinsert{int candidate;}
	public static class Packet6insertconfirm{boolean insertok = false;}
	public static class Packet7gettodelete{int candidate;}
	public static class Packet8delconfirm{boolean delok = false;}
	public static class Packet9gettosearch{int candidate;}
	public static class Packet10searchconfirm{boolean searchok = false;}
	public static class Packet11gettoclear{}	
}