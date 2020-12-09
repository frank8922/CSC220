package prog06;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
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
			return mistakesFound <= 1;
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
	 		if(find(nextWord) == -1 || nextWord == null)
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
		Queue<Integer> queue = new LinkedQueue<Integer>();
		queue.offer(find(start));
		
		String currentWord;
		
		String result;
		int index;
		while(queue.size() > 0) 
		{
			int frontQueue = queue.poll();
			currentWord = list.get(frontQueue);
			for(int i = 0; i < words.size(); i++)
			{
				if(offBy1(currentWord,words.get(i)) && (parents[i] == -1) && !words.get(i).equals(start))
				{
					queue.offer(i);
					parents[i] = frontQueue;
				
					
				if(words.get(i).equals(target))
				{
					index = find(target);
					result = target + "\n";
					
					while(parents[index] != -1)
					{
						result += words.get(parents[index]) + "\n";
						index = parents[index];
					}
					ui.sendMessage(result);
					return;
					
				}	
				}
				
			}
		
			
		}
		
		
		
	}
	
public void print(String message) { System.out.println(message);}
public void print(int message) { System.out.println(message);}
	
}

