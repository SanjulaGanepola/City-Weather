/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CityWeather;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Sanjula Ganepola
 */
public class CityWeather {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        File f = new File("cities.txt");

        Scanner s = null;
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }

        String city = s.nextLine();
        System.out.println("City: " + city);

        String link = "https://www.theweathernetwork.com/ca/weather/ontario/" + city;

        findTemp(link);

    }

    
    public static int findTemp(String link) {

        Document doc = null;
        try {
            doc = Jsoup.connect(link).get();
            //doc = Jsoup.connect(link).get();
            //doc = Jsoup.connect(link).maxBodySize(0).get();
            
            
            //System.out.println(doc);
            Elements elem = doc.getElementsByClass("forecastitems bx-child");
            System.out.println(elem);

        } catch (IOException ex) {
            System.out.println("a");
        }
        return 1;
    }

    /*
     public static int findTemp1(String link) {
     URL url = null;
     InputStream is = null;
     BufferedReader br;
     String line;

     try {
     url = new URL(link);
     is = url.openStream();  // throws an IOException
     br = new BufferedReader(new InputStreamReader(is));

     while ((line = br.readLine()) != null) {
     System.out.println(line);
     }
     } catch (MalformedURLException ex) {
     System.out.println("1");
     } catch (IOException ex) {
     System.out.println("2");
     }
     return 1;
     }
     */
}
