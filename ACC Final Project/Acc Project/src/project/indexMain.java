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
        Trie.priority(trie.search(word));
    }
    
}
