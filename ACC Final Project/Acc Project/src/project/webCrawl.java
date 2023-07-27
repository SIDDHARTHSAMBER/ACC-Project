package project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class webCrawl {

    private HashSet<String> links;
    private static final int MAX_DEPTH = 400;
    private List<String> filteredLinks = new ArrayList<>();  
    
    // Regular expressions to exclude certain URLs
    private final String[] excludeRegexPatterns = {
            "^https?://login.*",
            "^https?://ca\\.help.*",
            "^https?://ca\\.mail.*",
            "^https?://io.*",
            "^https?://*mail*",
            "^https?://mail.*",
            "^https?://help.*",
            "^https?://search.*",
            "^https?://r.*"
    };

    private int count;

    public webCrawl() {
        links = new HashSet<>();
    }

    public void getlinks(String myURL) {
        if ((!links.contains(myURL))) {
            try {
                if (links.add(myURL)) {
                    int i = filteredLinks.size() + 1;
                    String myString = Integer.toString(i);
                    String str = myURL;
                    saveUrl(myString, str);
                    if (isValidLink(myURL)) {
                        filteredLinks.add(myURL);
                    }
                }
                Document my_document = Jsoup.connect(myURL).get();
                Elements link_on_page = my_document.select("a[href]");
                for (Element page : link_on_page) {
                    if (filteredLinks.size() < 25 && count < MAX_DEPTH) {
                        count++;
                        getlinks(page.attr("abs:href"));
                    }
                }
            } catch (IOException e) {
                System.err.println("For '" + myURL + "': " + e.getMessage());
            }
        }
    }

    private boolean isValidLink(String link) {
        for (String excludeRegexPattern : excludeRegexPatterns) {
            if (Pattern.matches(excludeRegexPattern, link)) {
                return false;
            }
        }
        return true;
    }

    public void saveUrl(final String filename, final String urlString) {
        // Code to save URLs to files goes here
        {
			try 
			{
				URL url = new URL(urlString);
				BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream()));
				
				String str = filename + ".html";
		
				BufferedWriter write = new BufferedWriter(new FileWriter("html_Files/" + str));
		
				String line;
				while((line = read.readLine()) != null) 
				{
					write.write(line);
				}
				read.close();
				write.close();
				//System.out.println("Files have been stored.");
		
			}
			
	
			catch(MalformedURLException mue) 
			{
				System.out.println("Malformed URL Exception");
			}
			catch(IOException ie) 
			{
				System.out.println("IOException");
			}
		}
    }

    public void PageLinks(String link) {
        getlinks(link);
    }

    public List<String> FilteredLinks() {
        return filteredLinks;
    }

    public void runWebcrawl(){
         webCrawl crawl = new webCrawl();
        crawl.PageLinks("https://ca.news.yahoo.com/");
        List<String> filteredLinks = crawl.FilteredLinks();
        System.out.println("Filtered Links:");
        for (String link : filteredLinks) {
            System.out.println(link);
        }
    }

    public static void main(String[] asd) {
       
    }
}
