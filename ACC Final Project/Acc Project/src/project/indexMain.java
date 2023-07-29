package project;

import java.io.IOException;
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
        
        if(l == null){
            System.out.println("The word "+word+" is not found");
        }
        else{
            System.out.println("The maximum number of occurences of the word: "+word+" is in "+l);
            KMPsearchfile(l,word);}
    }
    
}
