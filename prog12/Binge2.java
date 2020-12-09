package prog12;

import java.util.*;

public class Binge2 implements SearchEngine {
	
	HardDisk<PageFile> pageDisk = new HardDisk<PageFile>(); //maps indices (long) -> webpage file (PageFile)
	PageTrie pageToIndex = new PageTrie(); //urlToIndex from binge1
	HardDisk<List<Long>> wordDisk = new HardDisk<List<Long>>(); //maps word indices (long) -> a list of pages stored as indices 
																//(this is used to show the pages where the word appears)
																//the indices are from the table from wordtable,
																//and are used to represent the words
	
	WordTable wordToIndex = new WordTable(); //maps words (string) -> indices (longs) using a hashtable

	@Override
	public void gather(Browser browser, List<String> startingURLs) {
		// TODO Auto-generated method stub
		pageToIndex.read(pageDisk);
		wordToIndex.read(wordDisk);
		
	}

	/** Function searches through the page 'database'
	 *  for a keyword
	 *  @param list of keywords (list<string>)
	 *  @param number of results (the size of the queue)
	 *  @return a list of strings that
	 *  represent the all of the pages where the keyword appears
	 *  the list is ordered by relevance
	 */



	@Override
	//#3, declare the 3 variables. Every element in itself is an iterator 
	public String[] search(List<String> keyWords, int numResults) 
	{
		System.out.println("pageDisk \n" + pageDisk);
		System.out.println("urlToIndex \n" + pageToIndex);
		System.out.println("wordFiles \n" + wordDisk);
		System.out.println("wordToIndex" + wordToIndex);
		
		
		// Iterator into list of page ids for each key word.
	    Iterator<Long>[] wordFileIterators = (Iterator<Long>[]) new Iterator[keyWords.size()];
	    
	    // Current page index in each list, just ``behind'' the iterator.
	    long[] currentPageIndices = new long[keyWords.size()];

	    
	    // LEAST popular page is at top of heap so if heap has numResults
	    // elements and the next match is better than the least popular page
	    // in the queue, the least popular page can be thrown away.
	    PriorityQueue<Long> bestPageIndices = new PriorityQueue <Long>(numResults, new PageComparator());

	    //Loop initializes the entries of wordFileIterators
	    int current_pos = 0;
	    for(int i = 0; i < keyWords.size(); i++)
		{
			String word = keyWords.get(i);
			
			//if word has never been mentioned on the web before, places as top position[1] 
			if(!wordToIndex.containsKey(word))
				return new String[0];
			//else list in terms of revalence 
			else
			{
			    long wordIndex = wordToIndex.get(word);
			    //go through the list and check the current position
			    wordFileIterators[current_pos] = wordDisk.get(wordIndex).iterator();
			    current_pos++;
			}		
		}
	    
	    while (getNextPageIndices(currentPageIndices, wordFileIterators))
	    {
			//if all entries are equal save them
	    	if(allEqual(currentPageIndices)) 
	    	{
				bestPageIndices.offer(currentPageIndices[0]);
	    	}
		}
       
	    //#6
	    //create array of Strings to store results
		String[] results = new String[bestPageIndices.size()];
		
		for (int i = results.length - 1; i >= 0; i--) 
		{
			//upload the priority queue to the string in the correct order.  
			results[i] = pageDisk.get(bestPageIndices.poll()).url;
		}
		
	 return results;
	}


	
//	checked inputs and return
	private boolean allEqual(long[] array) {
		long compare = array[0];
		
		
		
		for(int i = 1; i < array.length; i++)
		{
			//if even one result is not equal then return false
			if(compare != array[i])
			{
				return false;
			}
		}
		//otherwise return true
		return true;
	}

	private boolean getNextPageIndices(long[] currentPageIndices, Iterator<Long>[] wordFileIterators) {
		long largestPage = currentPageIndices[0];
		
		if(allEqual(currentPageIndices))
		{
			for(int i = 0; i < wordFileIterators.length; i++)
			{
				if(wordFileIterators[i].hasNext())
				{
					currentPageIndices[i] = wordFileIterators[i].next();
				}
				else
				{
					for(Long index : currentPageIndices)
					{
						if(index > largestPage)
						{
							largestPage = index;
						}
						for(int k = 0; k < currentPageIndices.length; k++)
						{
							if(currentPageIndices[k] < largestPage && wordFileIterators[k].hasNext())
							{
								currentPageIndices[k] = wordFileIterators[k].next();
							}
							else
							{
								return false;
							}
						}
					}
				}
			}
		}

		return true;
	}

	private class PageComparator implements Comparator<Long>
	{
		//Used to compare reference counts
		//the page with the highest reference count is first on the list
		@Override
		public int compare(Long o1, Long o2) {
			int arg0 = pageDisk.get(o1).getRefCount();
			int arg1 = pageDisk.get(o2).getRefCount();
			int diff = arg0 - arg1;
			return diff;
		}
	
	}

}
