package prog05;
import java.util.Arrays;
import java.util.EmptyStackException;

/** Implementation of the interface StackInt<E> using an array.
*   @author vjm
*/

public class ArrayStack<E> implements StackInt<E> {
  // Data Fields
  /** Storage for stack. */
  E[] theData;

  /** Index to top of stack. */
  int top = -1; // initially -1 because there is no top

  private static final int INITIAL_CAPACITY = 2;

  /** Construct an empty stack with the default initial capacity. */
  public ArrayStack () {
    theData = (E[])new Object[INITIAL_CAPACITY];
  }

  /** Pushes an item onto the top of the stack and returns the item
      pushed.
      @param obj The object to be inserted.
      @return The object inserted.
   */
  public E push (E obj) {
    top++;

    if (top == theData.length)
      reallocate();

    theData[top] = obj;

    return obj;
  }

  /** Returns the object at the top of the stack and removes it.
      post: The stack is one item smaller.
      @return The object at the top of the stack.
      @throws EmptyStackException if stack is empty.
   */
  public E pop () {
    if (empty())
      throw new EmptyStackException();

    /**** EXERCISE ****/
		E head = theData[top];
		top--;
	return head;
  }

  /** Returns the object at the top of the stack without removing it.
      post: The stack remains unchanged.
      @return The object at the top of the stack.
      @throws EmptyStackException if stack is empty.
   */
  public E peek () {
			if(empty())
				throw new EmptyStackException();
    /**** EXERCISE ****/
			E head = theData[top];
			return head;

  }

  /**** EXERCISE ****/

	public void reallocate() {
		E [] newData; 
		int PREV_CAPACITY = theData.length;
		theData = newData = Arrays.copyOf(theData,PREV_CAPACITY * 2);
	 }

	/** Returns true if the stack is empty; otherwise it returns false.
	@return true if the stack is empty; false if not
	*/
	public boolean empty(){
			return top == -1;
	}


}
