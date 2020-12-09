package prog06;

import java.util.*;

/** This is an implementation of a queue as a circular queue using arrays
 * @author Francisco Belliard
 */
public class CircularAQueue<E> extends AbstractQueue<E> implements Queue<E>{

//Class members

//Holds the index to front of queue
private int front;

//Holds the index to the rear of queue
private int rear;

//Holds the index to the next position
private int next;

//Holds the default size of queue
private static final int DEFAULT_CAPACITY = 10;

//Holds the max capacity of the queue
int maxCapacity;

//Holds the items in the queue
private E [] theItems;

public CircularAQueue(int size)
{//{{{
	front = -1;
	rear = -1;
	maxCapacity = size;
	theItems = (E[]) new Object[maxCapacity];
}//}}}

public CircularAQueue()
{//{{
  this(DEFAULT_CAPACITY);
}//}}

//Adds an element to the rear of the queue also known as enqueue
public boolean offer(E item)
{//{{{
  	//If queue is empty set front and rear indices to 1st index of array
	if(isEmpty())
	{//{{{ front = rear = 0
		front = rear = 0;
	}//}}}
	else //otherwise increment the rear index
	{ //{{{
		rear = (rear+1) % theItems.length; 
	} //}}}

	if(isFull()) //reallocate the array if its full
	{//{{{ if the queue is full reallocate
		reallocate();
	}//}}}
	
	//Append the element to the end of the queue
	theItems[rear] = item;
	return true;
}//}}}

//Removes and returns and item from the front of the queue
//also known as dequeue.
public E poll()
{//{{{


	next = (front + 1) % theItems.length; //Get the pointer to the next index in queue

	if(isEmpty())//return null if empty
	  {//{{{ 
		 return null;
	  }//}}}
	E element = theItems[front]; //get the element at the front of queue
   	
	if(rear == front) //if the front and rear element of the circular queue are equal
	{				  //then the queue is empty (front & rear = -1)
	  front = rear = -1;
	}
	else //otherwise the increment the front index
	{
	  front = next;
	}

	return element; 
	
}//}}}

//Retrieves but does not remove the front of the list
public E peek()
{//{{{
	if(isEmpty())
	{//{{{
	  return null;
	}//}}}
	return theItems[front];
}//}}}

//Returns true if the queue is at capacity
public boolean isFull()
{//{{{ 
	return rear == theItems.length -1; //if the rear index is equal to the size-1
}//}}}								   //then the queue is full

//Returns true if queue is empty
public boolean isEmpty()
{//{{{
	return (front == -1 && rear == -1); 
}//}}}

//Reallocates the queue to store more elements
public void reallocate()
{//{{{
	maxCapacity = theItems.length*2;
  	E[] temp = Arrays.copyOf(theItems,maxCapacity);
	theItems = temp;
}//}}}

@Override
public int size() {
	return rear;
}

@Override
public Iterator<E> iterator() {
	// TODO Auto-generated method stub
	return new iter();
}

private class iter implements Iterator<E>
{//{{{
	int index; //to hold the current index
	int nextIndex; //to hold the next index in the queue

	public iter()//Constructor for iterator
	{//{{{
		if(isEmpty())
		{
			//if the queue is empty return -1
			index = -1;
		}
		else
		{
			//if the queue is not empty, then index = front of the queue
			index = front;
		}
	}//}}}

	@Override
	public boolean hasNext() //if there is an element to iterate
	{//{{{

		if(index == -1) //if base case return false
		{
			//return false if there are no more elements in queue
			return false;
		}
		return true; //if not at the base case, return true to iterate through elements
	}//}}}

	@Override
	public E next() //the next element to iterate
	{//{{{
		if(!hasNext()) {
			throw new NoSuchElementException();
		}
		//get the element at the index
		E element = theItems[index];

		if(index == rear) //Base case
		{
			//if at the last element, then the next index is -1 because queue is empty
			index = -1;
		}
		else
		{
			//increment index
			index = (index + 1) % theItems.length;
		}
		return element;
	}//}}}
	
	@Override
    public void remove () {
    throw new UnsupportedOperationException();
  }
	
}//}}}


}


