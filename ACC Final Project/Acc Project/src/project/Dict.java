package project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.TreeMap;

public class Dict {
	public static TreeMap<String,String> dictionary(String filename,TreeMap<String,String> aListWords) throws Exception{
		File file = new File(filename);
		@SuppressWarnings("resource")
		BufferedReader br = new BufferedReader(new FileReader(file));
		String s;
		String words[] = null;
		
		while((s = br.readLine()) != null) {
		String tempStr = s.replaceAll("[^a-zA-Z]", "-").toLowerCase();
				words=tempStr.split("-");
				for(int i=0;i<words.length;i++)
					aListWords.put(words[i],words[i]);
		}
		return aListWords;
	}
	
	public static void createDictionary() {
		File directory = new File("ACC-Project-main//ACC Final Project//Acc Project//text_Files");
		String[] children = directory.list();
		TreeMap<String,String> aListWords = new TreeMap<String,String>();
		for (String str:children)
		{
			String strx = str.substring(str.lastIndexOf(".")+1);
			if (strx.equals("txt"))
			{
				try {
					dictionary("ACC-Project-main//ACC Final Project//Acc Project//text_Files" + "/"+ str,aListWords);
					FileWriter writer = new FileWriter("dictionary.txt"); 
					for(String s: aListWords.values()) {
					  writer.write(s + System.lineSeparator());
					}
					writer.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}