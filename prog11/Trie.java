package prog11;

import java.util.*;
import prog02.*;

public class Trie <V>
  extends AbstractMap<String, V> {

  private class Entry <V>
    implements Map.Entry<String, V> {
    String key;
    V value;
    
    Entry (String key, V value) {
      this.key = key;
      this.value = value;
      this.sub = key;
    }

    Entry (String sub, String key, V value) {
      this.key = key;
      this.value = value;
      this.sub = sub;
    }

    Entry (String sub, String key, V value, List<Entry<V>> list) {
      this.key = key;
      this.value = value;
      this.sub = sub;
      this.list = list;
    }

    public String getKey () { return key; }
    public V getValue () { return value; }
    public V setValue (V newValue) {
      V oldValue = value;
      value = newValue;
      return oldValue;
    }

    String sub;
    List<Entry<V>> list = new ArrayList<Entry<V>>();
  }
  
  private List<Entry<V>> list = new ArrayList<Entry<V>>();
  private int size;

  public int size () { return size; }

  /**
   * Find the entry with the given key.
   * @param key The key to be found.
   * @param iKey The current starting character index in the key.
   * @param list The list of entrys to search for the key.
   * @return The entry with that key or null if not there.
   */
  private Entry<V> find (String key, int iKey, List<Entry<V>> list) {
    // EXERCISE:
    int iEntry = 0;
    
    //Go through each letter of search key and compare with each letter of subkey
    //While the first letters of the key are not equal, go to the next sub word
//    while(list.get(iEntry).sub.charAt(0) < key.charAt(iKey) && iEntry < list.size())
//    {
//    	iEntry++;
//    }
    while(iEntry < list.size() && list.get(iEntry).sub.charAt(0) < key.charAt(iKey))
    {
        iEntry++;
	}
    
    //if the first letters are not equal or it is the end of the search key
    // word is not in list therefore return null
//    if(list.get(iEntry).sub.charAt(0) > key.charAt(iKey) || iKey == key.length())
//    {
//    	return null;
//    }
    if(iEntry == list.size() || list.get(iEntry).sub.charAt(0) > key.charAt(iKey)) 
    {
        return null;
    }
    
    //if at this point then letters are still equal
    //get current word being compared
    Entry<V> entry = list.get(iEntry);
    //get the index of the subword, remember the first letter was already
    //compared above so start at 1
    int iSub = 0;
    //Same goes for iKey, since the first letter was already commpared
    //therefore incremment iKey
    do {
    	iKey++;
    	iSub++;
    }
    while(iKey < key.length() && iSub < entry.sub.length() && entry.sub.charAt(iSub) == key.charAt(iKey)); 
    
	//if the index of letter in search key (iKey) is at the end of search key (key),
	//and the index of the letter in the subword (iSub) is at the end of the subword (entry.sub),
    //then it must be the word being searched, so return entry
    if(iSub < entry.sub.length() - 1) {
    	return null;
    }
    
    if(iKey == key.length()) {
        if(entry.key == null) {
            return null;
        } else {
            return entry;
        }
      }
    
    if(entry.list == null) {
        return null;
    } else {
        return find(key, iKey, entry.list);
    }

   
  }    

  public boolean containsKey (Object key) {
    Entry<V> entry = find((String) key, 0, list);
    return entry != null && entry.key != null;
  }
  
  public V get (Object key) {
    Entry<V> entry = find((String) key, 0, list);
    if (entry != null && entry.key != null)
      return entry.value;
    return null;
  }
  
  public boolean isEmpty () { return size == 0; }
  
  private V put (String key, int iKey, V value, List<Entry<V>> list) {
    // EXERCISE:
    int iEntry = 0;
    
    
    //search the subword and see if it matches key	
//    while(list.get(iEntry).sub.charAt(0) < key.charAt(iKey) && iEntry < list.size())
//    {
//    	iEntry++;
//    }
    while(iEntry < list.size() && key.charAt(iKey) > list.get(iEntry).sub.charAt(0)){
    	iEntry++;
    }
    
//    if(list.get(iEntry).sub.charAt(0) > key.charAt(iKey) || iEntry == list.size())
//    {
//    	Entry<V> newEntry = new Entry<V>(key.substring(iKey),key,value);
//    	list.add(iEntry, newEntry);
//    	size++;
//    	return null;
//    }
    if(iEntry == list.size() || key.charAt(iKey) < list.get(iEntry).sub.charAt(0))
    {
        Entry <V> entry = new Entry(key.substring(iKey), key, value);
            list.add(iEntry, entry);
                 size++;
                   return null;
    }	

    Entry<V> entry = list.get(iEntry);
    int iSub = 0;
    // If we are here, then character iKey of key and iSub of entry.sub
    // must match.  Increment both iKey and iSum until that is not true.
    ///
    
    do {
        iKey++;
        iSub++;
    }while (iKey < key.length() && 
    		iSub < entry.sub.length() && 
    		key.charAt(iKey) == entry.sub.charAt(iSub));


    if(iSub < entry.sub.length())
    {
		Entry<V> unmatchedSub = new Entry<V>(entry.sub.substring(iSub),key,value);

		entry.sub = key.substring(0, iSub);
		entry.list = new ArrayList<Entry<V>>();
		entry.key = null;
		entry.value = null;
		entry.list.add(unmatchedSub);
    }
    
    if(iKey == key.length())
    {
    	if(entry.key == null)
    	{
    		entry.key = key;
    		entry.value = value;
    		size++;
    		return null;
    	}
    	else
    	{
    		return entry.setValue(value);
    	}
    }


    if(entry.list == null)
    {
    	entry.list = new ArrayList<Entry<V>>();
    }
    



    ///
    return put(key,iKey,value,entry.list);
  }
  
  public V put (String key, V value) {
	  //it first gets called on list iKey = 0
    return put(key, 0, value, list);
  }      
  
  public V remove (Object keyAsObject) {
    return null;
  }

  private V remove (String key, int iKey, List<Entry<V>> list) {
    return null;
  }

  private Iterator<Map.Entry<String, V>> entryIterator () {
    return new EntryIterator();
  }

  private class EntryIterator implements Iterator<Map.Entry<String, V>> {
	 //IDEA: Each list either has a sub-list or not,
	 //Since each list also has an iterator (polymorphism), we can iterate
	 //through each list and their sublist essentially iterating through
	 //the entire data structure
	  
    // EXERCISE
    Stack<Iterator<Entry<V>>> stack = new Stack<Iterator<Entry<V>>>();

    EntryIterator () {
    	//push the list on the stack in order to check each entry
    	//Remember tries = list[item[subitem,subitem[subsubitem[]]]]
      stack.push(list.iterator());
    }

    public boolean hasNext () {
      // EXERCISE
      // While the iterator at the top of the stack has not next, pop it.
      // Return true if you have not popped all the iterators.
      ///
    	
    //If the stack isn't empty and the list at the top of the stack
    //hasNext method returns true, indicating there is something to process,
    //Continue processing elements
    	while(stack.isEmpty() == false && !stack.peek().hasNext())
    	{
    		 stack.pop();
    	}
    	
      ///
      return !stack.empty();
    }

    public Map.Entry<String, V> next () {
      if (!hasNext())
        throw new NoSuchElementException();
      Entry<V> entry = null;
      // EXERCISE
      // Set entry to the next of the top of the stack.
      // If its list is not null, push its iterator.
      // Repeat those two steps if entry.key is null.
      ///

//      while(entry.key == null)
//      {
//    	  entry = stack.peek().next();
//    	  if(entry.list != null)
//    	  {
//    		  stack.push(entry.list.iterator());
//    	  }
//    	  
//      }

      do{ entry=stack.peek().next();
      if (entry.list != null)
    	  stack.push(entry.list.iterator());
      }
      while(entry.key == null);
      
      ///
      return entry;
    }

    public void remove () {}
  }

  public Set<Map.Entry<String, V>> entrySet() { return new EntrySet(); }

  private class EntrySet extends AbstractSet<Map.Entry<String, V>> {
    public int size() { return size; }

    public Iterator<Map.Entry<String, V>> iterator () {
      return entryIterator();
    }

    public void remove () {}
  }

  public String toString () {
	  //calls toString(list, indent) with the indent of 0
    return toString(list, 0);
  }
  
  private String toString (List<Entry<V>> list, int indent) {
    String ind = "";
    // Add indent number of spaces to ind:
    ///
    for(int i = 0; i < indent; i++)
    {
    	ind += " ";
    }

    ///
    String s = "";
    
    //use a foreach loop after iterator works to loop through each
    //entry in the list
    for (Entry<V> entry : list) {
      // Append indented entry (sub, key, and value) and newline ("\n") to s.
      ///
    //for the first iteration print the entry
    s += ind + " " + entry.sub + " " + entry.key + " " + entry.value + "\n";
      ///
      // If there is a sublist, append its toString to s.
      // What value of indent should you used?
      // bob null null
      //    by bobby 7
      //    cat bobcat 8
      // What additional indent would make by and cat line up just past bob?
      ///
    
    //if the entry contains a sublist of entries, recurse toString on it
    if(entry.list != null)
    {
    	//if the list is not null, call toString(list,indent)
    	//with the list = entry.list (i.e the entry's sub-list)
    	//and set the indent = entry.sub.length (i.e the subwords length)
    	s += toString(entry.list,indent + entry.sub.length());
    }

      ///
    }
    return s;
  }

  void test () {
    Entry<Integer> bob = new Entry<Integer>("bob", null, null);
    list.add((Entry<V>) bob);
    Entry<Integer> by = new Entry<Integer>("by", "bobby", 0);
    bob.list.add(by);
    Entry<Integer> ca = new Entry<Integer>("ca", null, null);
    bob.list.add(ca);
    Entry<Integer> lf = new Entry<Integer>("lf", "bobcalf", 1);
    ca.list.add(lf);
    Entry<Integer> t = new Entry<Integer>("t", "bobcat", 2);
    ca.list.add(t);
    Entry<Integer> catdog = new Entry<Integer>("catdog", "catdog", 3);
    list.add((Entry<V>) catdog);
    size = 4;
  }

  public static void main (String[] args) {
    Trie<Integer> trie = new Trie<Integer>();
    trie.test();
    System.out.println(trie);

    UserInterface ui = new ConsoleUI();

    String[] commands = { "toString", "containsKey", "get", "put", "size", "entrySet", "remove", "quit" };
    String key = null;
    int value = -1;

    while (true) {
      int command = ui.getCommand(commands);
      switch (command) {
      case 0:
        ui.sendMessage(trie.toString());
        break;
      case 1:
        key = ui.getInfo("key: ");
        if (key == null) {
          ui.sendMessage("null key");
          break;
        }
        ui.sendMessage("containsKey(" + key + ") = " + trie.containsKey(key));
        break;
      case 2:
        key = ui.getInfo("key: ");
        if (key == null) {
          ui.sendMessage("null key");
          break;
        }
        ui.sendMessage("get(" + key + ") = " + trie.get(key));
        break;
      case 3:
        key = ui.getInfo("key: ");
        if (key == null) {
          ui.sendMessage("null key");
          break;
        }
        try {
          value = Integer.parseInt(ui.getInfo("value: "));
        } catch (Exception e) {
          ui.sendMessage(value + "not an integer");
          break;
        }
        ui.sendMessage("put(" + key + "," + value + ") = " + trie.put(key, value));
        break;
      case 4:
        ui.sendMessage("size() = " + trie.size());
        break;
      case 5: {
        String s = "";
        for (Map.Entry<String, Integer> entry : trie.entrySet())
          s += entry.getKey() + " " + entry.getValue() + "\n";
        ui.sendMessage(s);
        break;
      }
      case 6:
        break;
      case 7:
        return;
      }
    }
  }
}

