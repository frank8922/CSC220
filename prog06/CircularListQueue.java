package prog06;

import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.Queue;


/** This is a circular queue implementation using ListQueues
 * @author Francisco Belliard
 * @param <E>
 */
public class CircularListQueue<E> extends AbstractQueue<E> implements Queue<E>{
	
	private static final int DEFAULT_SIZE = 10; //Holds the default size of queue
	private int maxCapacity; //Holds the max capacity of queue
	
	Node head, tail;
	
	public CircularListQueue()
	{
		this(DEFAULT_SIZE);
	}

	public CircularListQueue(int size)
	{
		tail = null;
		maxCapacity = size;
	}
	
	private class Node<E>
	{
		private Node next; 
		private E data;
		
		public Node(E data)
		{
			this.data = data;
		}
		
		public void setNext(Node item)
		{
			next = item;
		}
		
		public Node next()
		{
			return next;
		}
		
		public E getData()
		{
			return data;
		}
		
	}
	
	@Override
	public E remove()
	{//{{{
		// TODO Auto-generated method stub
		return null;
	}//}}}

	@Override
	public E poll()
	{//{{{
		// TODO Auto-generated method stub
		return null;
	}//}}}

	@Override
	public E peek()
	{//{{{
		// TODO Auto-generated method stub
		return null;
	}//}}}

	@Override
	public boolean offer(E e) 
	{//{{{
		Node element;
		if(head == null)
		{
//			tail = element; 
		}
		return false;
	}//}}}
	
	public void addFirst(E item)
	{
		Node element;
		if(tail == null)
		{
			tail = element = new Node(item);
			tail.setNext(tail);
		}
		else
		{
			element = new Node(item);
			element.setNext(tail.next());
			tail.setNext(element);
		}
	}

	@Override
	public Iterator<E> iterator() 
	{//{{{
		// TODO Auto-generated method stub
		return null;
	}//}}}

	@Override
	public int size() 
	{//{{{
		// TODO Auto-generated method stub
		return 0;
	}//}}}

	

}
