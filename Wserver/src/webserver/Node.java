package webserver;

public class Node {
	
		Integer element; // Value stored in node
	    int level;
	    double xValue, yValue;
	    double xOldValue, yOldValue;
	    int height;		
	    Node left, right; // Left and right child

		Node(int val) 
		{
			element = val;
			left = null;
			right = null;
		}
                Node(int val,double x, double y,int pr_level) 
		{
			element = val;
			left = null;
			right = null;
                        xValue = xOldValue = x;
                        yValue = yOldValue = y;
                        level = pr_level;
                        System.out.println(val+":"+x+" = "+y);
                }
		Node(Integer val, Node leftChild, Node rightChild) 
		{
			element = val;
			left = leftChild;
			right = rightChild;
			xValue = xOldValue = 0.0;
                        yValue = yOldValue = 0.0;
			height = 0;
		}
		void resetHeight()
        {
          int leftHeight = getHeight(left);
          int rightHeight = getHeight(right);      
          height = 1 + Math.max(leftHeight, rightHeight);
        }

		int getHeight(Node tree) {
			 if (tree == null) return -1; 
		     else return tree.height;       
		}   
		public Node() 
		{
			// TODO Auto-generated constructor stub
		}
}