package project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KMPSearch {
    static class KMP {
        public static List<Integer> search(String text, String pattern) {
            List<Integer> occurrences = new ArrayList<>();

            int M = pattern.length();
            int N = text.length();

            int[] lps = computeLPSArray(pattern);

            int i = 0; // index for text[]
            int j = 0; // index for pattern[]

            while (i < N) {
                if (pattern.charAt(j) == text.charAt(i)) {
                    i++;
                    j++;
                }

                if (j == M) {
                    occurrences.add(i - j);
                    j = lps[j - 1];
                } else if (i < N && pattern.charAt(j) != text.charAt(i)) {
                    if (j != 0) {
                        j = lps[j - 1];
                    } else {
                        i++;
                    }
                }
            }

            return occurrences;
        }

        private static int[] computeLPSArray(String pattern) {
            int M = pattern.length();
            int[] lps = new int[M];
            int len = 0; // length of the previous longest prefix suffix

            lps[0] = 0;
            int i = 1;

            while (i < M) {
                if (pattern.charAt(i) == pattern.charAt(len)) {
                    len++;
                    lps[i] = len;
                    i++;
                } else {
                    if (len != 0) {
                        len = lps[len - 1];
                    } else {
                        lps[i] = 0;
                        i++;
                    }
                }
            }
            return lps;
        }
    }

    public static void runKMPsearch(String fz, String x){
        
        
        String fileName = "ACC-Project-main//ACC Final Project//Acc Project//text_Files//"+fz; // Replace with the path to your text file
        String searchWord = x; // Replace with the word you want to search

        List<Integer> occurrences = searchWordInFile(fileName, searchWord);
        System.out.println("By using KMPSearch the Occurrences of '" + searchWord + "' in the file: "+fz+" is found at: " + occurrences);
   
        
         }

    public static void main(String[] args) {
      
    }

    public static List<Integer> searchWordInFile(String fileName, String searchWord) {
        List<Integer> occurrences = new ArrayList<>();
        String text = readFileContent(fileName);
        List<Integer> indices = KMP.search(text, searchWord);
        occurrences.addAll(indices);
        return occurrences;
    }

    public static String readFileContent(String fileName) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
