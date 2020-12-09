package prog08;
import java.util.*;

import prog07.BST;
//import prog07.BST.Node;
import prog04.DLLEntry;

public class DLLTreeEx <K extends Comparable<K>, V>
  extends AbstractMap<K, V> {

  private class Node <K extends Comparable<K>, V>
    implements Map.Entry<K, V> {
    K key;
    V value;
  
    Node left, right;
    int size = 1; // size of subtree with this node as root

    Node previous, next;
    
    Node (K key, V value) {
      this.key = key;
      this.value = value;
    }

    public K getKey () { return key; }
    public V getValue () { return value; }
    public V setValue (V newValue) {
      V oldValue = value;
      value = newValue;
      return oldValue;
    }
  }
  
  private Node theRoot, tail, head;
  private int size;

  public int size () { 
    if (theRoot == null)
      return 0;
    return theRoot.size;
  }

  /**
   * Find the node with the given key.
   * @param key The key to be found.
   * @return The node with that key.
   */
  private Node<K, V> find(K key, Node<K,V> root) 
  {
    // EXERCISE
    // return null; // not correct

	   // This is from prog07		 
      //Recursion causes the roots to change till it is either found or returns null    
	  //keys provide access to actual value    
	//if tree is empty or if the element is not there it will return null 
		  if (root == null)
			  return null;
		  //if key equal to root, return root
		  if (key.compareTo(root.key)== 0) {
			  return root;
		  } 
		  //if equal to left, return left
		  else if (key.compareTo(root.key) < 0) {
	           return (find (key, root.left));	 
		  }
		  //if equal to right, return right 
		  else if (key.compareTo(root.key) > 0) {
	          return (find(key, root.right));	 
	      }
		  
	   return null;
  }    

  
  
  public boolean containsKey (Object key) {
    return find((K) key, theRoot) != null;
  }
  
  public V get (Object key) {
    Node<K, V> node = find((K) key, theRoot);
    if (node != null)
      return node.value;
    return null;
  }
  
  
  
  public boolean isEmpty () { return theRoot == null; }

  /**
   * Add node between previous and next in the DLL.
   */
  private void addDLL (Node<K,V> node, Node<K,V> previous, Node<K,V> next) {
    // EXERCISE

	node.next = next;
	node.previous = previous;
	 
	//if you go left, if firsts place as head, else place it as prev
	if(previous == null) 
	     head = node;
	else
	   previous.next = node;
	 
	//if you go right, if firsts place as tail, else place it as next
	if(next == null)
		tail = node;
	else
		next.previous = node;
  }

  
  
  /**
   * Add key,value pair to tree rooted at root.
   * Return root of modified tree.
   */
  private Node<K,V> add (K key, V value, Node<K,V> root) {
    // EXERCISE

	        //prog 7
	        //is root is null make new node by recursion	
			//if root is null return a new node. That means that you found its correct place  
	  if (root == null) 
			return new Node<K,V> (key, value); 
		
		if (key.compareTo(root.key) < 0) {
			//recursively add and replace 
			root.left = add(key,value,root.left);
			
			if(root.left.next == null)
				addDLL(root.left, root.previous, root);
		}
		else if (key.compareTo(root.key) > 0) {
			//recursively add and replace
			root.right = add(key, value, root.right);
			
			if(root.right.previous == null)
				addDLL(root.right, root, root.next);
		}

		//#10.1 
		root.size++;
		
		if (size(root.left) > root.size * (2/3))
			rebalance(getMinimum(root.left), size(root.left)); 
		
		if (size(root.right) > root.size * (2/3))
			rebalance(getMinimum(root.right), size(root.right)); 
		
	    return root;
  }
  
  
  /**
   * Return the size of the tree rooted at root, using root.size if it
   * is not an empty tree.
   */
  private int size (Node<K,V> root) {
    if (root == null)
      return 0;
    return root.size;
  }

  /**
   * Return the root of a balanced tree made from the nodes in a DLL
   * with head head and size elements.
   */
  private Node<K,V> rebalance (Node<K,V> head, int size) {
    // EXERCISE
   
	  Node<K,V> left;
	  Node<K,V> right;
	  
	//#10.2
     if(size == 1) 
     {
         head.left = null;
         head.right = null;
         head.size = 1;
         
         return head;
     }

     //setting size by combining right and left subtree 
     left = rebalanceLeft(head, size - size/2);
     right = rebalance(theRoot.next, size/2);
     
     theRoot.size = right.size + left.size + 1;
     
    return theRoot;
  }

  /**
   * Return the left half of a balanced tree made from the nodes in a
   * DLL with head head and size elements: the root and left subtree
   * are correct, but the right subtree is empty.
   */
  private Node<K,V> rebalanceLeft (Node<K,V> head, int size) {
    // EXERCISE

	//#10.2
	     if(size == 1) 
	     {
	         head.left = null;
	         head.right = null;
	         head.size = 1;
	         
	         return head;
	     }

	     
	     
	     
    return theRoot;
  }

  
  
  public V put (K key, V value) {
    // EXERCISE
	    
	    // prog 7 
		//find the node and assign to variable to compare
		Node <K,V> place = find(key, theRoot); 
	     
		//if node is there change the value  
		if (place != null) { 
			place.setValue(value);
		}
		//if not then add c
		else {
			theRoot = add(key, value, theRoot); 
			size++;
		}
		 
		//setting head and tail if empty 
		if(theRoot.previous == null)
			head = theRoot;
		if(theRoot.next == null)
			tail = theRoot; 
		 

    return null;
  }      
  
  
  
  public V remove (Object keyAsObject) {
      // EXERCISE
       K key = (K) keyAsObject;
      //return null; // not correct

      //from prog 7
      //find element that you want to delete 
      Node<K,V> newNode = find(key, theRoot);
      //if empty then return null
      if (newNode == null)
    	return null;
      //else remove it 
      theRoot = remove(key, theRoot);
      //decrease size
      size--;
     
    return newNode.value;
    }

  
  
  /**
   * Remove the node with key from the tree with root.  Return the
   * root of the resulting tree.
   */
  private Node<K,V> remove (K key, Node<K,V> root) {
    // EXERCISE










    return root;
  }

  /**
   * Remove node from a DLL.
   */
  private void removeDLL (Node<K,V> node) {
    // EXERCISE











  }

  /**
   * Remove root of tree rooted at root.
   * Return root of a BST of the remaining nodes.
   */
  private Node<K,V> removeRoot (Node<K,V> root) {
    // IMPLEMENT using getMinimum and removeMinimum
    // Node returned by getMinimum becomes the new root.
    //return null; // not correct

	//prog 07 
     //if left and right subtree empty return other
	  if(root.right == null)
		  return root.left;
      if(root.left == null)
  	  return root.right;
    
       //assign variable to leftmost node
       Node<K, V> newRoot = getMinimum(root.right);
      //removeMinimum(root.right);
    
        newRoot.right = removeMinimum(root.right);
        newRoot.left = root.left;
    
    root = newRoot;
    return root;
}

  //prog 07
  // IMPLEMENT getMinimum and removeMinimum
  private Node getMinimum( Node root)
  {
	  //if left subtree empty return root
	  if (root.left == null)
		  return root;
	  
	  return getMinimum(root.left);  
	  //return root; 
  }

 private Node removeMinimum( Node root)
 {
	  //if left subtree is empty return right
	  if (root.left == null)
		  return root.right;
	  
	  root.left = removeMinimum(root.left);
	  
	  return root; 
 }



  protected class Iter implements Iterator<Map.Entry<K, V>> {
    Node<K, V> next;
    
    Iter () {
      next = head;
    }
    
    public boolean hasNext () { return next != null; }
    
    public Map.Entry<K, V> next () {
      Map.Entry<K, V> ret = next;
      next = next.next;
      return ret;
    }
    
    public void remove () {
      throw new UnsupportedOperationException();
    }
  }
  
  protected class Setter extends AbstractSet<Map.Entry<K, V>> {
    public Iterator<Map.Entry<K, V>> iterator () {
      return new Iter();
    }
    
    public int size () { return DLLTreeEx.this.size(); }
  }
  
  public Set<Map.Entry<K, V>> entrySet () { return new Setter(); }
  
  public String toString () {
    return toString(theRoot, 0);
  }
  
  private String toString (Node root, int indent) {
    if (root == null)
      return "";
    String ret = toString(root.right, indent + 2);
    for (int i = 0; i < indent; i++)
      ret = ret + "  ";
    ret = ret + root.key + ":" + root.value + ":" + root.size + "\n";
    ret = ret + toString(root.left, indent + 2);
    return ret;
  }

  public String listString () {
    if (head == null)
      return "";
    String s = head.key + ":" + head.value;
    for (Node<K,V> node = head.next; node != null; node = node.next)
      s = s + " " + node.key + ":" + node.value;
    return s;
  }

  public static void main (String[] args) {
    DLLTree<Character, Integer> tree = new DLLTree<Character, Integer>();
    String s = "abcdefghijklmnopqrstuvwxyz";
    
    for (int i = 0; i < s.length(); i++) {
      tree.put(s.charAt(i), i);
      System.out.print(tree);
      System.out.println("-------------");
    }
    System.out.println("line 425");

    System.out.println("list: " + tree.listString());
    System.out.println("-------------");

    for (int i = 0; i < s.length(); i++) {
      tree.remove(s.charAt(i));
      System.out.print(tree);
      System.out.println("-------------");
    }

    System.out.println("list: " + tree.listString());
  }
}

	
	
