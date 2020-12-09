package prog05;

import java.util.EmptyStackException;
import java.util.List;
import java.util.ArrayList;

/** Implementation of the interface StackInt<E> using a List.
*   @author vjm
*/

public class ListStack<E> implements StackInt<E> {
  // Data Fields
  /** Storage for stack. */
  List<E> theData;

  /** Initialize theData to an empty List. */
  public ListStack() {
    theData = new ArrayList<E>();
  }

  /** Pushes an item onto the top of the stack and returns the item
      pushed.
      @param obj The object to be inserted.
      @return The object inserted.
   */
  public E push (E obj) {
    theData.add(obj);
    return obj;
  }

	/** Returns the object at the top of the stack without removing it
	  or changing the stack.
	  @return The object at the top of the stack.
	  @throws EmptyStackException if stack is empty.
	*/
	@Override
	public E peek() {
		// TODO Auto-generated method stub
		if(empty()) {
			throw new EmptyStackException();
		}
		E head = theData.get(theData.size()-1);
		return head;
	}

	/** Returns the object at the top of the stack and removes it.
    post: The stack has one less item.
    @return The object at the top of the stack.
    @throws EmptyStackException if stack is empty.
 */
	@Override
	public E pop() {
		// TODO Auto-generated method stub
		if(empty()) {
			throw new EmptyStackException();
		}	
		E head = theData.remove(theData.size()-1);
		return head; 
	}
	
	/** Returns true if the stack is empty; otherwise it returns false.
    @return true if the stack is empty; false if not
	 */
	@Override
	public boolean empty() {
		// TODO Auto-generated method stub
		if(theData.size() == 0)
			return true;
		
		return false;
	}

  /**** EXERCISE ****/


}
