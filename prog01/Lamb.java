package prog01;

public class Lamb {
  String[] array = { "Mary", "had", "a", "little", "lamb",
                     null, null, null, null, null };
  int size = 5;
  static int counter = 0;
  /** Insert a word into the array at the specified index.
      pre: size < array.length
      @param word The word to insert.
      @param index The index at which to insert it.
      post: Word is inserted, pushing words from index+1 to size-1 forward.
      size incremented by 1. */
  void insert (String word, int index) {
	if(size < array.length) 
	{ 
	   for(int i = size; i >= index+1; i--) 
	  {
		  array[i] = array[i-1];
	  }
	 
	  array[index] = word;
	  size++;
	}
	else
	{	/* 
			1st: Create a temporary array to hold values of orginal array.
			2nd: Copy items from original array to temporary array.
			3rd: Call insert() on the current word and index
			
		*/
		String[] temp = new String [array.length/2 + array.length];
		for(int i = 0; i < array.length; i++) {
			temp[i]= array[i];
		}
		array = temp;
		insert(word,index);
	}
  }

  /** Print the words in array from 0 to size-1. */
  void print () {
    for (int i = 0; i < size; i++)
      System.out.print(array[i] + " ");
      System.out.println();
  }

  public static void main (String[] args) {
    Lamb lamb = new Lamb();
    lamb.print();     
    lamb.insert("very", 3); 
    lamb.print();
    lamb.insert("only", 1);
    lamb.print();
    lamb.insert("Doctor", 0);
    lamb.print();
    lamb.insert("chop", 8);
    lamb.print();
    lamb.insert("eaten", 4);
    lamb.print();
    
    System.out.println("Final sentence should be:");
    System.out.println("Doctor Mary only had eaten a very little lamb chop");
  }
}
