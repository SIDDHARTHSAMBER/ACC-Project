package project;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.jsoup.*;
import java.util.Objects;
import java.util.PriorityQueue;


// TrieNode Class Functionalities 
//children character , trienode 
// freq_table integer file number integer occurences 
// constructor allocate children hash map but not freq_table hashmap  
class TrieNode {
    private Map<Character, TrieNode> children;
    private boolean isEndOfWord;
    private Map<Integer,Integer> freq_table;
    private int user_searched;

    public TrieNode() {
        children = new HashMap<>();
        freq_table=new HashMap<>();
        user_searched=0;
    }
    // Tracking User searches  
    public int  get_search_times() {
        return user_searched;
    }
    public void update_search_time() {
        user_searched=user_searched+1;
    }
    // Tracking trie children
    public Map<Character, TrieNode> getChildren() {
        return children;
    }
    // Tracking freq_table 
    public void setfreqt(int x,int y) {
    	freq_table.put(x, y);
    }
    public int  getfreqt(int x) {
    	if(freq_table.get(x)==null)
    	{
    		return 0;
    		// return 0 if table has no entry for x 
    	}
        return freq_table.get(x);
        // else it returns the corresponding value 
    } 
    public  Map<Integer,Integer> get_full_table() {
        return freq_table;
        // it return whole table or hashmap , it could be empty or filled 
    } 
}



public class Trie {
    private TrieNode root;

    //constructor initialize the root trie node 
    public Trie() {
        root = new TrieNode();
        
    }

    // Insertion logic 
    /*
     * current TrieNode is set to root 
     * for each character of word , it's children map is checked for character if absent then new trienode mapping is created 
     * then current node is set to that newly created trinode mapping or oldone if it exists 
     * So in the end trinode associated with last character is set 
     * So if you put getchildren to it it will be child of last character 
     * we check the frequency table of last character , if file number is already there we put 1 , means word inserted 
     * else we increment it 
     * */
    public void insert(String word,int file_number) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current.getChildren().putIfAbsent(ch, new TrieNode());
            current = current.getChildren().get(ch);
        }
        if(current.getfreqt(file_number)==0)
        {
        	// 0 means that there there is no entry 
        	current.setfreqt(file_number, 1);
        }
        else
        {	
        	int temp =current.getfreqt(file_number)+1;
        	current.setfreqt(file_number,temp);
        }
    }
    
    // Searching logic
    /*
     * current TrieNode is set to root 
     * for each character of word , it's children map is checked for character if absent then null is returend else the corresponding trinode is returned
     * if somehow currentnode becomes null function returns null 
     * else function returns the frequency table of the last character node  
     * */
    public Map<Integer,Integer> search(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current = current.getChildren().get(ch); 
            if (current == null) {
                return null;
            }
        }
        	current.update_search_time();
        	return current.get_full_table();
    }
    
    public boolean startsWith(String prefix) {
        TrieNode current = root;
        for (char ch : prefix.toCharArray()) {
            current = current.getChildren().get(ch);
            if (current == null) {
                return false;
            }
        }
        return true;
    }

    

    
    // if there is no node corresponding to last chracter of prefix it returns null 
    // if there is node corresponding to last character it returns that node
    
    /*
     * current TrieNode is set to root 
     * for each character of word , it's children map is checked for character if absent then null is returend else the corresponding trinode is returned
     * if somehow currentnode then function returns null , representing that word is not there in trie
     * else it returns the trinode of the last character node  
     * */
    public TrieNode search2(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current = current.getChildren().get(ch);
            if (current == null) {
                return null;
            }
        }
        	return current;
    }
    
    // Autocomplete logic 
    /*
     * suggestion List is created and search2 is called to get the node , if prefix does not exist in trie node it return empty list 
     * else that trinode associated with last character , prefix word and suggestion list is passed to collectsuggestions 
     * */
    

    public List<String> autocomplete(String word) {
        List<String> suggestions = new ArrayList<>();
        TrieNode node = search2(word);
        if (node != null) {
            collectSuggestions(node, word, suggestions);
        }
        return suggestions;
    }
    
    /*
    	if freqeuncy table is not empty it means that the word exist in trie in that case we will add prefix  
    	for each of the entry in children map i.e for each chracter , we will go deep 
    	we will keep on adding the key to prefix and changing the trinode as we traverse
     * */
    
    private void collectSuggestions(TrieNode node, String prefix, List<String> suggestions) {
    	// if frequncy table is not empty it means that the word exist in trie 
    	// in that case we will add prefix  
        if (!node.get_full_table().isEmpty()) {
        	// If we type the same word which is there in trie it will suggest that also 
            suggestions.add(prefix);
        }
        
        // Here we are using DFS , traversing frequency table depth wise for suggesting words 
        // traverse our freq_table and if it is not null then add the character word traverse to children of that node 
        // if end of word then add it to suggestion 
        
        for (Map.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
            Character key = entry.getKey();
            TrieNode value = entry.getValue();
            if(value!=null)
             	collectSuggestions(value, prefix + key, suggestions);
        }
         
        }
    
    /// Debug function just to check what the frequency table contains 
    
    private static void print(Map<Integer,Integer> a)
    {
    	if(a==null)
    	{
    		System.out.println("Word Not Found");
    		return;
    	}
    	a.forEach((key, value) -> System.out.println(key + " : " + value));
    	
    }
//
    public static int extractfile(String fileName) {
        // Find the position of the last occurrence of "."
        int dotIndex = fileName.lastIndexOf('.');
        // Extract the word before the ".txt" extension
        String wordBeforeExtension = fileName.substring(0, dotIndex);
        return Integer.parseInt(wordBeforeExtension);
    }

    
	private static String[] processFile(File file)
	{
		// convert File to words and add words to Trie
        try (FileReader fileReader = new FileReader(file)) {
        	StringBuilder stringBuilder = new StringBuilder();
        	int character;
        	while ((character = fileReader.read()) != -1) {
                stringBuilder.append((char) character);
            }
            String text= stringBuilder.toString(); 
            //String[] words = text.split("\\s+");
            String[] words = text.split("[^a-zA-Z]+");
            
            for (String word : words) {
                //System.out.println(word);
            }
            return words;
        } catch (IOException e) {
            e.printStackTrace();
        }
        // how about if we put this print line and return statemtn in catch block ? 
        System.out.println("File not processed");
		return null;
	}
	
	private static void create_trie(String[] file,Trie trie,int file_number)
	{
		for (String word : file) {
			trie.insert(word,file_number);
        }
		
		///
		try {
			
			FileWriter writer = new FileWriter("dictionary.txt",true); 
			for(String s: file) {
			  writer.write(s + System.lineSeparator());
			}
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		///
	}
	
    public static String k;
    public static int max=0;
    public static String priority( Map<Integer,Integer> freq_table) {
       
    	// Create Comparator Descending order 
        Comparator<Map.Entry<Integer, Integer>> valueComparator = (entry1, entry2) -> entry2.getValue() - entry1.getValue();

        //Initialize a PriorityQueue with the custom comparator
        PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = new PriorityQueue<>(valueComparator);
        
        
        //Add each key-value pair to the max-heap
        if(freq_table!=null)
        {
        	if(freq_table.isEmpty())
        	{
        		return null;
        	}
        	else
        	{
        		maxHeap.addAll(freq_table.entrySet());	
        	}
        }
        else if(freq_table==null)
        {        		
        		return null;
        }
        	
        	

        //Retrieve elements from max heap
        max=0;
        while (!maxHeap.isEmpty()) {
            Map.Entry<Integer, Integer> entry = maxHeap.poll();
            int key = entry.getKey();
            int value = entry.getValue();
            if(value>=max){max=value;k=key+".txt";}
            else if(value<max){}
            System.out.println("File Number: " + key + ", Frequency: " + value);
        }
        return k;
    }
    
    public static void create_whole_trie(String file_path,Trie trie)
    {
    	try {
    		File folder = new File(file_path);
  	 	   File[] files = folder.listFiles();
  	 	  
  	        if (files != null) {
  	            for (File file : files) {
  	                if (file.isFile()) {
  	                	create_trie(processFile(file),trie,extractfile(file.getName()));	
  	                }
  	            }
  	        }   
  	    }
  	    catch (Exception e) {
  	 	     e.printStackTrace();
  	 	   }	
    }

    public static void main(String[] args) {
        Trie trie = new Trie(); 
        String file_path="text_Files";
        
        create_whole_trie(file_path,trie);
        
        // Searching news in trie
        System.out.println("Searching in Trie");
        Trie.print(trie.search("news"));
        System.out.println();
        
        
        // Printing the priority Queue
        System.out.println("Priority Queue printing");
        priority(trie.search("news"));
        System.out.println();
        
        
        // Printing Auto suggestion for an
        System.out.println("Auto Suggestion");
        List<String>suggestions =trie.autocomplete("an");
        for (String suggest : suggestions) {
            System.out.println(suggest);
        }
        System.out.println();

    }
}


