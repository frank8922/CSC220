package prog12;

import java.util.*;


public class Binge implements SearchEngine {
	
	//the hard disk directory for the pages
	HardDisk<PageFile> pageDisk = new HardDisk<PageFile>();
	
	//the hard disk directory for the words
	HardDisk<List<Long>> wordDisk = new HardDisk<List<Long>>();
	
	//maps a word to an index using a hashmap
	WordTable wordToIndex = new WordTable();
	//maps a url to an index
	PageTrie urlToIndex = new PageTrie();
	
	/** Indexes word into hard disk */
	public Long indexWord(String word)
	{
		Long index = wordDisk.newFile();
		List<Long> pages = new ArrayList<Long>();
		
		System.out.println("indexing word " + index + "(" + word + ")" + pages);
		wordDisk.put(index,pages);
		wordToIndex.put(word,index);
		
		
		return index;
	}

	/** Indexes page into hard disk */
	public Long indexPage(String url)
	{
		Long index = pageDisk.newFile();
		PageFile pagefile = new PageFile(index,url);
		pageDisk.put(index,pagefile);
		urlToIndex.put(url,index);
		System.out.println("indexing page " + pagefile);
		return index;
	}
	
	/** Gathers all the URLs and words from every page and
	 * 	indexes them.
	 */
	@Override
	public void gather(Browser browser, List<String> startingURLs) {
		// TODO Auto-generated method stub

		Queue<Long> pagesToBeIndexed = new ArrayDeque<Long>();
		for(String url : startingURLs)
		{
			//if we havent seen the webpage, add it to the queue
			if(!urlToIndex.containsKey(url))
			{
				pagesToBeIndexed.offer(indexPage(url));
			}
		}
		
		while(!pagesToBeIndexed.isEmpty())
		{
			Long index = pagesToBeIndexed.poll();
			Long pageIndex;
			Long wordIndex;
			
			//get the page file for the given url
			PageFile pgfile = pageDisk.get(index);

			//load the url in the browser
			if(browser.loadPage(pgfile.url))
			{
				//sets will be used to check if the page has already has been seen
				//if not we will increase its reference count
				Set<Long> pIndicies = new HashSet<Long>(); //to hold the page indices all of the page indicies
				Set<Long> wordIndicies = new HashSet<Long>(); //to hold the word indices
				
				//get all the url links on the current page
				List<String> urls = browser.getURLs();

				//print them out
				System.out.println("urls " + urls);
				
				//for each url, check if it has been seen before
				//then either add it to the queue of pages to be indexed
				//other wise get the index of the url (because you saw it already therefore it has been indexed)
				for(String url : urls)
				{
					if(!urlToIndex.containsKey(url))
					{
						//index the page
						pageIndex = indexPage(url);

						//add it to the queue of websites to be indexed
						pagesToBeIndexed.offer(pageIndex);
					}
					else
					{
						//get the index since it was already indexed
						pageIndex = urlToIndex.get(url);
					}
					//add the page index to the set
					pIndicies.add(pageIndex);
				}
				
				//for each page index in the set
				//increment the reference count for the page
				for(Long j : pIndicies)
				{
					pageDisk.get(j).incRefCount();
					System.out.println("inc ref " + pageDisk.get(j));
				}
				
				System.out.println("words " + browser.getWords());
				
				//for each word in the web page, 
				//index the word if it hasn't already been seen
				for(String word: browser.getWords())
				{
					if(!wordToIndex.containsKey(word))
					{
						wordIndex = indexWord(word);
					}
					else
					{
						wordIndex = wordToIndex.get(word);
					}
					
					if(!wordIndicies.contains(wordIndex))
					{
						wordIndicies.add(wordIndex);
						wordDisk.get(wordIndex).add(index);
						System.out.println("add page " + wordIndex + "(" + word + ")" + wordDisk.get(wordIndex));
					}
				}
				
				
			}
		}
		
		System.out.println("pageDisk\n" + pageDisk);
		System.out.println("urlToIndex\n" + urlToIndex);
		System.out.println("wordDisk\n" + wordDisk);
		System.out.println("wordToIndex\n" + wordToIndex);
		
		urlToIndex.write(pageDisk);
		wordToIndex.write(wordDisk);
	}

	@Override
	public String[] search(List<String> keyWords, int numResults) {
		// TODO Auto-generated method stub
		
		return new String[0];
	}

	
}
