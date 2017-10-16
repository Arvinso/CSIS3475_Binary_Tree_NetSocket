package Clientmain;
import java.io.IOException;
import java.util.ArrayList;
import Clientmain.Packet.*;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

public class Wclient {

	public static final String Host = "localhost";
	public static final int Port = 6789;
	
	private ArrayList<Integer> list;
	
	public Client client;
	
	public static Node temproot;
	public static boolean insertconfirmer;	
	public static boolean deleteconfirmer;
	public static boolean searchconfirmer;	
        public  String result;
	
	public Wclient()
	{	

        if (list == null) {
            list = new ArrayList<Integer>();
        }
	}	
	
	private void register() 
	{
		Kryo kryo = client.getKryo();
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
	public Node getRoot() {
		
		Packet1getroot rootgetter = new Packet1getroot();
		client = new Client();
		register();			
		CNetworkListener nl = new CNetworkListener();
		nl.init(client);
		client.addListener(nl);	
		client.start();
		
		try {
			client.connect(5000,Host, Port);//timeout,IP,port
		} catch (IOException e) {
			e.printStackTrace();			
		}
		client.sendTCP(rootgetter);
		System.out.println("refresh"+client.toString());	
	 
		 try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	
		
		 client.close();
		 
		return temproot;
	}	
	public boolean insert(int key) {
	
		Packet5gettoinsert getoi = new Packet5gettoinsert();
		client = new Client();
		register();			
		CNetworkListener nl = new CNetworkListener();
		nl.init(client);
		client.addListener(nl);	
		client.start();
		
		try {
			client.connect(5000,Host, Port);//timeout,IP,port
		} catch (IOException e) {
			e.printStackTrace();			
		}
		getoi.candidate = key;
		client.sendTCP(getoi);
		System.out.println("inserting value");	
		
		 try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
		
		 System.out.println("inserting okay?:"+ insertconfirmer);
		
		return insertconfirmer;
	}

	public boolean delete(int key) {
		
		Packet7gettodelete getod = new Packet7gettodelete();
		client = new Client();
		register();			
		CNetworkListener nl = new CNetworkListener();
		nl.init(client);
		client.addListener(nl);	
		client.start();
		
		try {
			client.connect(5000,Host, Port);//timeout,IP,port
		} catch (IOException e) {
			e.printStackTrace();			
		}
		getod.candidate = key;
		client.sendTCP(getod);
		System.out.println("deleting value");	
		 try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		
		 System.out.println("delete okay?:"+ deleteconfirmer);
		
		return deleteconfirmer;		
	}
	public boolean search(int key) {
		
		Packet9gettosearch getos = new Packet9gettosearch();
		client = new Client();
		register();			
		CNetworkListener nl = new CNetworkListener();
		nl.init(client);
		client.addListener(nl);	
		client.start();
		
		try {
			client.connect(5000,Host, Port);//timeout,IP,port
		} catch (IOException e) {
			e.printStackTrace();			
		}
		getos.candidate = key;
		client.sendTCP(getos);
		System.out.println("searching value");	
		 try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		 
		System.out.println("search okay?:"+ searchconfirmer);		
		
		if ( searchconfirmer)
			path(key);
		
		return searchconfirmer;		
	}

	public void clear() {		
		Packet11gettoclear nuke = new Packet11gettoclear();
		client = new Client();
		register();			
		CNetworkListener nl = new CNetworkListener();
		nl.init(client);
		client.addListener(nl);	
		client.start();
		
		try {
			client.connect(5000,Host, Port);//timeout,IP,port
		} catch (IOException e) {
			e.printStackTrace();			
		}
		client.sendTCP(nuke);
		System.out.println("nuke sent"); 		 
		client.close();	
		
	}
        
        public boolean insertLocal(int value) {

        list.clear();

        if (temproot == null) {
            temproot = createNode(value);
        } else {
            Node parent = null;
            Node current = temproot;
            int level = 0;
            while (current != null) {
                level++;
                 if (value < current.element) {
                    parent = current;
                    current = current.left;
                } else if (value > current.element) {
                    parent = current;
                    current = current.right;
                } else {
                    return false;
                }
            }
            if (value < parent.element) {
                parent.left = createNode(value);
                parent.left.level = level;
            } else {
                parent.right = createNode(value);
                parent.right.level = level;
            }
        }
    
        return true;
    }
    
    private Node createNode(int value) {
        Node node = new Node(value);
        return node;
    }
    
	public double getXValue(int key) {		
		 double l_value = 0.0;
           Node current = temproot;
        while (current != null) {
            if (key < current.element) {
                current = current.left;
            } else if (key > current.element) {
                current = current.right;
            } else if ( key == current.element )
            {
                l_value = current.xValue;
                
                break;
            }
        }
        return l_value;
	}

	public double getYValue(int key) {
		 double l_value = 0.0;
           Node current = temproot;
        while (current != null) {
            if (key < current.element) {
                current = current.left;
            } else if (key > current.element) {
                current = current.right;
            } else if ( key == current.element )
            {
                l_value = current.yValue;
                
                break;
            }
        }
        return l_value;
	}

	public ArrayList<Integer> path(Integer value) {
        
		if(list != null )
			list.clear();
        
		Node current = temproot;
        while (current != null) {
            list.add(  current.element);
            if (value < current.element) {
                current = current.left;
            } else if (value > current.element) {
                current = current.right;
            } else {
                break;
            }
        }
        return list;
    }

	 public int getLevel ( int key)
	    {
	            int l_value = 0;
                Node current = temproot;
                while (current != null) {
              if (key < current.element) {
                current = current.left;
            } else if (key > current.element) {
                current = current.right;
            } else if ( key == current.element )
            {
                
                break;
            }
              l_value++;
        }
        return l_value;

	    }
	 
	 public boolean checkItemInSearchList(Integer element) {
		 
		 if ( list== null)
			 return false;
		 
		 return list.contains(element);
	    }
         public void clearList()
         {
             if( list.size() > 0 )
                   list.clear();
         }
         
         public void inorder() {
        result = "";
        inorder(temproot);
    }

    private void inorder(Node btree) {
        if (btree != null) {
            inorder(btree.left);
            System.out.print(btree.element + " ");
            result += btree.element + " ";
            inorder(btree.right);
        }
    }

    public void postorder() {
        result = "";
        postorder(temproot);
    }

    private void postorder(Node root2) {
        if (root2 == null) {
            return;
        }
        result += root2.element + " ";
        System.out.print(root2.element + " ");
        postorder(root2.left);
        postorder(root2.right);
    }

    public void preorder() {
        result = "";
        preorder(temproot);
    }

    private void preorder(Node root) {
        if (root == null) {
            return;
        }
        preorder(root.left);
        preorder(root.right);
        System.out.print(root.element + " ");
        result += root.element + " ";
    }
}
