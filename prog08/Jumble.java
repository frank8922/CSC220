package prog08;

import java.io.File;
import java.util.Scanner;

import prog02.UserInterface;
import prog02.ConsoleUI;
import prog02.GUI;
import java.util.Map;
import java.util.TreeMap;
import prog02.ArrayBasedPD;
import prog02.SortedPD;
import prog04.SortedDLLPD;
import prog07.BST;

public class Jumble {
  /**
   * Sort the letters in a word.
   * @param word Input word to be sorted, like "computer".
   * @return Sorted version of word, like "cemoptru".
   */
  public static String sort (String word) {
	  //create an array the length of the word to sort
    char[] sorted = new char[word.length()];
    System.out.println("word: " + word);
    
    //iterate through the word by character
    for (int n = 0; n < word.length(); n++) {

    	//store the character 
      char c = word.charAt(n);
    System.out.print(c);
    
    //set the index value to current character index on word
      int i = n;
      
      //while there is something to process in the array sorted[] 
      // and the current character (c) is less than the element at sorted[i-1], swap sorted[
      while (i > 0 && c < sorted[i-1]) {
        sorted[i] = sorted[i-1];
        
        //decrement i in order to properly store c in sorted
        i--;
      }

      //store c in sorted as close as possible to start of stored[0]
      sorted[i] = c;
    }

    //return fully sorted word
    return new String(sorted, 0, word.length());
  }

  public static void main (String[] args) {
    UserInterface ui = new GUI("Jumble Solver");
//     Map<String,String> map = new TreeMap<String,String>();
//     Map<String,String> map = new PDMap(new ArrayBasedPD());
//    Map<String, String> map = new BST<String,String>();
//     Map<String,String> map = new PDMap(new SortedPD());
//     Map<String,String> map = new PDMap(new SortedDLLPD());
  // Map<String,String> map = new BST<String,String>();
     Map<String,String> map = new DLLTree<String,String>();

    Scanner in = null;
    do {
      try {
        in = new Scanner(new File(ui.getInfo("Enter word file.")));
      } catch (Exception e) {
        System.out.println(e);
        System.out.println("Try again.");
      }
    } while (in == null);
	    
    int n = 0;
    long start = System.currentTimeMillis();
    while (in.hasNextLine()) {
    	String word = in.nextLine();
      if (n++ % 1000 == 0)
	      System.out.println(word + " sorted is " + sort(word));
      
      // EXERCISE: Insert an entry for word into map.
      // What is the key?  What is the val
      map.put(sort(word),word);

    }
    long end = System.currentTimeMillis();
   System.out.println("\nStart time: " + (start/1E3) + "\nend time: " + (end/1E3)); 	
   System.out.println("total time: " + Math.abs(end-start)/1E3);
    while (true) {
      String jumble = ui.getInfo("Enter jumble.");
      if (jumble == null)
        return;

     
      // EXERCISE:  Look up the jumble in the map.
      // What key do you use?
      String word = map.get(sort(jumble)); 

      if (word == null)
        ui.sendMessage("No match for " + jumble);
      else
        ui.sendMessage(jumble + " unjumbled is " + word);
    }
  }
}

        
    

