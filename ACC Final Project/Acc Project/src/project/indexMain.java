package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class indexMain {

    public static void runWebcrawl(){
        webCrawl wC = new webCrawl();
        wC.runWebcrawl();
    }
    public static void createTextFiles() throws IOException{
        HTMLtoText.createTextFiles();
    }

    public static void KMPsearchfile(String fz, String x) throws IOException{
        KMPSearch.runKMPsearch(fz,x);
    }
    
    public static void createTrie(){
    	
    }

public static void spellCheck(String pattern) throws IOException {
		String filename="dictionary.txt";
		File file = new File(filename);
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			ArrayList<String> dictonarywords = new ArrayList<String>();
			String str=null;
			while((str= reader.readLine())!=null)
			{
				dictonarywords.add(str);
			}
			
			int ed,MinimumDistance=10, secondMinimumDist=10;
			int sugWordOne=0;
			for(int i = 0; i<dictonarywords.size();i++){
				String dw=dictonarywords.get(i);
				ed = EditDistance.minDistance(dw, pattern);
				if(ed<secondMinimumDist) {
					if(ed<MinimumDistance) {
						MinimumDistance=ed;
						sugWordOne=i;
					}
				
				}
			}
			System.out.println(" Entered Keyword is not present, Instead search for "+dictonarywords.get(sugWordOne));
		}	
	}

    public static void suggestions(String pattern) {
		try {
			spellCheck(pattern);
		}catch (Exception e) {
			System.out.println("Exception:" + e);
		}
	}

    public static void main(String[] args) throws IOException{
        runWebcrawl();
        createTextFiles();
        Scanner scanner = new Scanner(System.in);
        Trie trie = new Trie(); 
        String file_path="text_Files";
        Trie.create_whole_trie(file_path,trie);
        System.out.println("Enter the Word to Search:");
        String word= scanner.nextLine();
        String l = Trie.priority(trie.search(word));
        
        while(l == null){
            System.out.println("The word "+word+" is not found");
            System.out.println(" Word suggestions :");
			Dict.createDictionary();
            suggestions(word);
            System.out.println("Enter the Word to Search:");
            word= scanner.nextLine();
            l = Trie.priority(trie.search(word));
        }
            System.out.println("The maximum number of occurences of the word: "+word+" is in "+l);
            KMPsearchfile(l,word);       
    }
    
}
