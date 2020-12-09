package prog04;



public class SortedDLLPD extends DLLBasedPD{

  /** Add a new entry at a location.
      @param location The location to add the new entry, null to add
      it at the end of the list
      @param name The name in the new entry
      @param number The number in the new entry
      @return the new entry
  */
	protected DLLEntry add (DLLEntry location, String name, String number) {
		DLLEntry entry = new DLLEntry(name, number);

		// EXERCISE
		// Add entry to the end of the list.
		if(head == null) 
			 { head = tail = entry; }
		
		if(location != null) 
		{
			entry.setNext(location);
			entry.setPrevious(location.getPrevious());
			location.setPrevious(entry);


			if(entry.getPrevious() != null) 
			{
				entry.getPrevious().setNext(entry);
			}
			else if(location == head)
			{
				head.setPrevious(entry);
		    	entry.setNext(head);
				head = entry;
			}
		
		}
		else if(tail != entry)
		{
			tail.setNext(entry);
			entry.setPrevious(tail);
			tail = entry;
		}
    return entry;
  }

  /** Find an entry in the directory.
      @param name The name to be found
      @return The entry with the same name or head if it is not there.
  */
  protected DLLEntry find (String name) {
	  DLLEntry entry = head;
    // EXERCISE
    // For each entry in the directory.
    // What is the first?  What is the next?  How do you know you got them all?
		  while(entry != null && entry.getName().compareTo(name) < 0) {
			  entry = entry.getNext();
		  }
      // If this is the entry you want

        // return it.
    return entry; // Name not found.
  }
  
  /** Check if a name is found at a location.
      @param location The location to check
      @param name The name to look for at that location
      @return false, if location is null or it does not have that
      name; true, otherwise.
  */
  protected boolean found (DLLEntry location, String name) {
	  if(location != null) {
		  return location.getName().equals(name);
	  }
	  return false;
  }
  
}
