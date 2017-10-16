package webserver;

import java.util.ArrayList;

public class BinaryNumTree{
	/**
	 * @author WeihuaWang
	 */
	// inner class	
	private Node root;
	private int size;
        private double vGap = 40,hGap = 600;
        String result;
	// constructor an empty BT without parameter
	public BinaryNumTree() {
		root = null;
		size = 0;
	}

	// constructor a BT with array parameter to generate a random BT
	public BinaryNumTree(int[] intArr) {
		for (int i = 0; i < intArr.length; i++)
			insert(intArr[i]);
	}

	public int getSize() {
		return size;
	}

	public Node getRoot() {
		return root;
	}

	public boolean insert(int value)
    {
		boolean insertok;
                int level = 0 ;
		if(search(value))
		{
			insertok = false;		   
		}
		else
		{
			root = insert(root, value,hGap/2, vGap, hGap / 4,level);
			insertok = true;
		}     
       
       return insertok;
    }
	
    
	public Node insert(Node btree, int value)
	{
		 if (btree == null)
	            return new Node(value);        
	        if (value < btree.element)       
	            btree.left = insert(btree.left, value);       
	        else       
	            btree.right = insert(btree.right, value);
	        
	        // Compute heights of the left and right subtrees
			  // and rebalance the tree if needed
	        int leftHeight = root.getHeight(btree.left);
	        int rightHeight = root.getHeight(btree.right);       
	        if (Math.abs(leftHeight - rightHeight) == 2)
	           return balance(btree);
	        else
	        {
	           btree.resetHeight();
	           return btree;
	        }       
	}
	
        public Node insert(Node btree, int value,double x, double y, double hGap, int level)
	{
		 if (btree == null)
                 {
                     
	            return new Node(value,x,y,level);        
                 }
                 if (value < btree.element)       
	            btree.left = insert(btree.left, value,x - hGap, y + vGap, hGap / 2,++level);       
	        else       
	            btree.right = insert(btree.right, value,x + hGap, y + vGap, hGap / 2,++level);	        
                {
	           btree.resetHeight();
	           return btree;
	        }       
	}
	
	private Node balance(Node bTree)
    {
        int rHeight = root.getHeight(bTree.right);
        int lHeight = root.getHeight(bTree.left);
        
        if (rHeight > lHeight)
        {
            Node rightChild = bTree.right;
            int rrHeight = root.getHeight(rightChild.right);
            int rlHeight = root.getHeight(rightChild.left);           
            if (rrHeight > rlHeight)           
                return rrBalance(bTree);            
            else            
                return rlBalance(bTree);            
        }
        else
        {
            Node leftChild = bTree.left;
            int llHeight = root.getHeight(leftChild.left);
            int lrHeight = root.getHeight(leftChild.right);           
            if (llHeight > lrHeight)
                return llBalance(bTree);
            else
                return lrBalance(bTree);            
        }        
    }
	
	private Node rrBalance(Node bTree)
    {       
        Node rightChild = bTree.right;
        Node rightLeftChild = rightChild.left;
        rightChild.left = bTree;
        bTree.right = rightLeftChild;
        bTree.resetHeight();
        rightChild.resetHeight();
        return rightChild;
    }
	
	 private Node rlBalance(Node bTree)
	    {
	        Node root = bTree;
	        Node rNode = root.right;
	        Node rlNode = rNode.left;
	        Node rlrTree = rlNode.right;
	        Node rllTree = rlNode.left;
	        
	        // Build the restructured tree
	        rNode.left = rlrTree;
	        root.right = rllTree;
	        rlNode.left = root;
	        rlNode.right = rNode;
	        
	        // Adjust heights
	        rNode.resetHeight();
	        root.resetHeight();
	        rlNode.resetHeight();
	        
	        return rlNode;        
	    }
	
	 private Node llBalance(Node bTree)
	    {
	        Node leftChild = bTree.left;
	        Node lrTree = leftChild.right;
	        leftChild.right = bTree;
	        bTree.left = lrTree;
	        bTree.resetHeight();
	        leftChild.resetHeight();
	        return leftChild;        
	    }
	
	 private Node lrBalance(Node bTree)
	    {
	        Node root = bTree;
	        Node lNode = root.left;
	        Node lrNode = lNode.right;
	        Node lrlTree = lrNode.left;
	        Node lrrTree = lrNode.right;
	        
	        // Build the restructured tree
	        lNode.right = lrlTree;
	        root.left = lrrTree;
	        lrNode.left = lNode;
	        lrNode.right = root;
	        
	        // Adjust heights
	        lNode.resetHeight();
	        root.resetHeight();
	        lrNode.resetHeight();
			  
	        return lrNode;        
	    }	
	public boolean search(int value) {
		Node current = root;
		while (current != null) {
			if (value < current.element) {
				current = current.left;
			} else if (value > current.element) {
				current = current.right;
			} else
				return true;
		}
		return false;
	}
	
	public ArrayList<Integer> path(int value){
		ArrayList<Integer> list=new ArrayList<Integer>();
		Node current=root;
		while(current!=null){
			list.add(current.element);
			if(value<current.element){
				current=current.left;
			}
			else if(value>current.element){
				current=current.right;
			}
			else
				break;
		}
		return list;
	}

	public boolean delete(int value) {
		Node parent=null;
		Node current=root;
		while(current!=null){
			if(value<current.element){
				parent=current;
				current=current.left;
			}
			else if(value>current.element){
				parent=current;
				current=current.right;
			}
			else
				break;
		}
		if(current==null){
			return false;
		}
		if(current.left==null){
			if(parent==null){
				root=current.right;
                                root.level -= 1;
			}
			else{
				if(value<parent.element){
					parent.left=current.right;
                                       
                                        
				}
				else
                                {	parent.right=current.right;
                                        
			
                                }
                        }
		}
		else{
			Node parOfRightMost=current;
			Node rightMost=current.left;
			while(rightMost.right!=null){
				parOfRightMost=rightMost;
                                parOfRightMost.level -= 1;
                                
                                rightMost=rightMost.right;
                                rightMost.level -= 1;
                        }
			current.element=rightMost.element;
                        //current.element -= 1;
                        
			if(parOfRightMost.right==rightMost)
				parOfRightMost.right=rightMost.left;
			else
				parOfRightMost.left=rightMost.left;
		}
		return true;
	}	

	public void clear() {
		root=null;
		size=0;		
	}
}