package prog07;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

import prog02.*;
//import prog02.UserInterface;

/** Word step class @author Francisco Belliard */
public class WordStep {

	//List of words to use
	List<String> words = new ArrayList<String>(); 
	
	//User interface either GUI or ConsoleUI
	static UserInterface ui = new GUI("");
	
	/** Compares two strings and returns true
	 * if string has same length and is off by 1 char
	 * @param str1 1st string
	 * @param str2 2nd string
	 * @return returns true if strings are same length and differ by a single char
	 */
	static boolean offBy1(String str1, String str2)
	{//{{{
		int mistakesFound = 0;
		if(str1.length() == str2.length())
		{
			for(int i = 0; i < str1.length(); i++)
			{
				if(str1.charAt(i) != str2.charAt(i))
					mistakesFound++;
			}
			return mistakesFound == 1;
		}
		
	  return false;
	}//}}}


	public static void main(String [] args)
	{//{{{
		
		//Create and initialize a new game
		WordStep game = new WordStep();
		
		//Ask user for file name and store the file name
		String filename = ui.getInfo("Enter file name");
		
		//Load the words from a file
		if(game.loadWords(filename))
			ui.sendMessage("Successfully loaded " + filename);
		
		String start = ui.getInfo("Enter a starting word");
		
		if(game.find(start) == -1){
			ui.sendMessage("Word not listed");
			return;
		}
		
		
		String target = ui.getInfo("Enter ending word");
		
		if(game.find(target) == -1){
			ui.sendMessage("Word not listed");
			return;
		}
		
		
		if(target == null) { ui.sendMessage("Not a word");  }
		
		if(start.length() != target.length() || start.equals(target))
			ui.sendMessage("Invalid entry");
		else {
			//UI Menu interface
			String[] commands = { "Human plays.", "Computer plays." };
			
			int c = ui.getCommand(commands);
			
			switch(c) {
				case 0: 
					game.play(start,target);
					break;
					
				case(1):
					game.solve(start,target);
					break;
					
				default:
				break;
			}
		
		}
		
	}//}}}
	
	void play(String start, String target)
	{//{{{
	 	while(true)
	 	{
	 		ui.sendMessage("Current word: " + start + " Target word: " + target);
	 		
	 		String nextWord = ui.getInfo("Enter next word: ");
	 		if(nextWord == null) return;
	 		
	 		if(find(nextWord) == -1)
	 		{
	 			ui.sendMessage("Word not listed");
	 		}
	 		else if(offBy1(start,nextWord))
	 		{
	 			start = nextWord;
	 		}
	 		else
	 		{
	 			ui.sendMessage("Invalid word");
	 		}
	 		
			if(start.equals(target))
			{
				 ui.sendMessage("You win!");
				 return;
			}
				 
	 		
	 	}
	}//}}}
	
	/** Reads in and loads a list of words from a file
	 * 
	 * @param filename The name of the file to load
	 * @Return returns true if success false if failed
	 */
	boolean loadWords(String filename)
	{//{{{
		File file = new File(filename);
		Scanner scanner;
		try {
//			 System.out.println("Working dir: " +  System.getProperty("user.dir"));
			 scanner = new Scanner(file);
			 if(file.exists())
			 {
				 while(scanner.hasNext())
				 {
					 String next = scanner.next();
					words.add(next);
				 }
				 scanner.close();
				 return true;
			 }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			ui.sendMessage("File not found");
		}
		 return false;
	}//}}}
	
	int find(String word)
	{//{{{
		int low = 0;
		int high = words.size();
		int middle = (low + high) / 2;
		
		while(low <= high)
		{
			middle = (low + high) / 2;
			if(word.compareTo(words.get(middle)) < 0)
			{
				high = middle - 1;
			}
			else if(word.compareTo(words.get(middle)) > 0)
			{
				low = middle + 1;
			}
			else
			{
				return middle;
			}
		}
		
		return -1;
	}//}}}
	
	
	void solve(String start, String target) 
	{
		List<String> list = words;
		int [] parents = new int[words.size()];
		Arrays.fill(parents,-1);
		
		IndexComparator comp = new IndexComparator(parents,target);
		
		Queue<Integer> queue = new Heap<Integer>(new IndexComparator(parents,target));

//		Queue<Integer> queue = new PriorityQueue<Integer>(new IndexComparator(parents,target));
		queue.offer(find(start));
		
		String currentWord;
		int count = 0;
		
		String result;
		int index;
		while(queue.size() > 0) 
		{
			currentWord = words.get(queue.poll());
			 count++;
			 
			 for(int i = 0; i < words.size(); i++)
			 {
				 if(!(words.get(i).equals(start)) && offBy1(currentWord, words.get(i)) && (parents[i] == -1 || numSteps(parents, find(currentWord)) + 1 < numSteps(parents, i)))  
			      {
					 	if (parents[i] != -1)
					 	{
					 		queue.remove(i);
					 	}
					    
					 	parents[i] = find(currentWord);
					    queue.offer(i);

					 	if(words.get(i).equals(target))
					 	{
					 		   ui.sendMessage("Go to " + target + " from " + start);
					 		   
					 		    result = target;
					 			int y = find(target);
					 			
					
					 			while(parents[y] != -1)
					 			{
					 				int place = parents[y];
					 				result =  words.get(place) + "\n" + result;
					 				y = place;
					 			}
					
					 			ui.sendMessage(result);
					 			ui.sendMessage("Poll count " + count + " times!");
					 			ui.sendMessage(start + " was " + numSteps(parents, find(target)) + " steps away from " + target);
					 			ui.sendMessage(start + " and " + target + " differ by " + numDifferent(start, target) + " letters");
					 			return;
					  }
				}
			 }
		 }
					ui.sendMessage("No solution was found."); 
			
	}
	
	static int numSteps (int[] parents, int index)
 	 { 
 		 int i = index;
 		 int counter = 0;
 		 
 		 while (parents[i] != -1 )
 		 {
 			 i = parents[i];
 			 counter++;
 		 }
 		 
 		 return counter;
 	 }
	
	static int numDifferent(String str, String str2)
 	 {
 		 if(str.length() != str2.length())
 			 return -1;
 		 
 		 int count = 0;  		 
 		 
 		 for (int i = 0; i < str.length(); i++)
 		 {
 			 if (str.charAt(i) != str2.charAt(i)) 
 				 	count++;
 		 }
 		 
 		 return count; 		 
 	 }
	
	
	public class IndexComparator implements Comparator<Integer>
 	 {
 		 int[] parents;  
 		 String target;
 		 
 		  public IndexComparator (int[] parents, String target) 
 		  {
 			 this.parents = parents;
 			 this.target = target;
 		  } 
 		  
 		  public int sumNums(int index)
 		  {
 			  int difference = numDifferent(words.get(index), target);
 			  int steps = numSteps(parents, index);
 			  
 			  int sum = difference + steps;
 			
 			  return sum;
 		  }
 		  
 		  
 		 @Override
 		public int compare(Integer index, Integer index2) {
 			// TODO Auto-generated method stub
// 			 if (sumNums(index) < sumNums(index2))
// 					return -1;
// 				  else if (sumNums(index) == sumNums(index2))
// 					  return 0;
// 				  else
// 					  return 1; 
 			 return sumNums(index) - sumNums(index2);
 		}
 		  
 	 }
	
public void print(String message) { System.out.println(message);}
public void print(int message) { System.out.println(message);}

	
}

