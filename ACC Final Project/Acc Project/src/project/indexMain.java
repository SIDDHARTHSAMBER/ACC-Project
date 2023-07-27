package project;

import java.io.IOException;

public class indexMain {

    public static void runWebcrawl(){
        webCrawl wC = new webCrawl();
        wC.runWebcrawl();
    }
    public static void createTextFiles() throws IOException{
        HTMLtoText.createTextFiles();
    }

    

    public static void main(String[] args) throws IOException{
        runWebcrawl();
        createTextFiles();
    }
    
}
