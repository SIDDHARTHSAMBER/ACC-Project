package project;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.jsoup.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.jsoup.Jsoup;

public class HTMLtoText {

    public static HashMap<String, String> textContentMap = new HashMap<>();

    public static void textFileCreator(String fileName) throws IOException {
        File files = new File("html_Files/" + fileName);
        org.jsoup.nodes.Document document = Jsoup.parse(files, "UTF-8");
        String st = document.text();
        textContentMap.put(fileName, st);

        String fileNamealt = fileName.replaceFirst("[.][^.]+$", "");
        PrintWriter out = new PrintWriter("text_Files/" + fileNamealt + ".txt");
        out.println(st);
        out.close();
    }

    public static void createTextFiles() throws IOException {
        File folder = new File("html_Files");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                textFileCreator(listOfFiles[i].getName());
            }
        }
        System.out.println("\n HTML Files are converted to text files successfully.");
    }

    public static void main(String[] args) throws IOException {
        
    }
}
