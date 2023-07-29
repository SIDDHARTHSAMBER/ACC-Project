package project;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.FileReader;
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
// leaf integer file number integer occurences 
// constructor allocate children hash map but not leaf hashmap  
class TrieNode {
	
    private Map<Character, TrieNode> children;
    private boolean isEndOfWord;
    private Map<Integer,Integer> leaf;

    public TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
        leaf=null;  
    }
    public Map<Character, TrieNode> getChildren() {
        return children;
    }
    public boolean isEndOfWord() {
        return isEndOfWord;
    }
    public void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord;
    }
    public void setleaf(int x,int y) {
    	if(leaf==null)
    	{
    		leaf=new HashMap<>();
    		// we are allocating memory only first entry is inserted
    	}
    	leaf.put(x, y);
    }
    public int  getleaf(int x) {
    	if(this.leaf==null)
    	{
    		// it returns 0 if whole hashMap is NULL
    		return 0;
    	}
    	if(leaf.get(x)==null)
    	{
    		return 0;
    		// it returns 0 if particular file has no entry 
    	}
    	else
        return leaf.get(x);
        // it returns null if there is no value corresponding to x
        // other-wise it returns the value 
    } 
    public  Map<Integer,Integer> get_full_leaf() {
        return leaf;
        // it will be null if whole hashmap is null else it will be something 
    } 
}



public class Trie {
    private TrieNode root;

    //constructor initialize the root trie node 
    public Trie() {
        root = new TrieNode();
        
    }

    public void insert(String word,int file_number) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current.getChildren().putIfAbsent(ch, new TrieNode());
            current = current.getChildren().get(ch);
        }
        current.setEndOfWord(true);
        if(current.getleaf(file_number)==0)
        {
        	// 0 means that it is null, either there is no entry or either it is not a word yet 
        	current.setleaf(file_number, 1);
        }
        else
        {	
        	int temp =current.getleaf(file_number)+1;
        	current.setleaf(file_number,temp);
        }
    }

    public Map<Integer,Integer> search(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current = current.getChildren().get(ch);
            if (current == null) {
                return null;
            }
        }
        if(current.get_full_leaf()==null)
        {
        	return null;
        }
        else
        {
        	return current.get_full_leaf();
        }
    }
    
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
	}
	
    public static String k;
    public static int max=0;
    public static String priority( Map<Integer,Integer> leaf) {
       
        
        // Step 1: Create a custom comparator to order elements based on values in descending order
        Comparator<Map.Entry<Integer, Integer>> valueComparator = (entry1, entry2) -> entry2.getValue() - entry1.getValue();

        // Step 2: Initialize a PriorityQueue with the custom comparator
        PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = new PriorityQueue<>(valueComparator);

        // Step 3: Add each key-value pair to the max-heap (PriorityQueue)
        if(leaf!=null)
        maxHeap.addAll(leaf.entrySet());
        else
        {
        	//System.out.println("Word Not Found");
        	return null;
        }
        	
        	

        // Step 4: Retrieve elements in descending order of values (max-heap property)
        while (!maxHeap.isEmpty()) {
            Map.Entry<Integer, Integer> entry = maxHeap.poll();
            int key = entry.getKey();
            int value = entry.getValue();
            if(value>=max){max=value;k=key+".txt";}
            else if(value<max){}
            //System.out.println("Key: " + key + ", Value: " + value);
            System.out.println("File Number: " + key + ", Frequency: " + value);
        }
        //System.out.println("The file is: "+k);
        return k;
    }
    
    public static void create_whole_trie(String file_path,Trie trie)
    {
    	try {
    		File folder = new File(file_path);
  	 	   //File folder = new File("text_Files");
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
        
        System.out.println("said");
        
        priority(trie.search("said"));
    }
}


