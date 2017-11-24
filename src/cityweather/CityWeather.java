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

            while (s.hasNext()) {
                String city = s.nextLine();
                String link = "https://www.google.ca/search?biw=1196&bih=949&ei=-FAYWujfH8aKjwSo262ICA&q=toronto" + city + "+weather&oq=toronto+weather&gs_l=psy-ab.3..35i39k1l2j0i67k1j0i20i263k1j0l6.18728.20320.0.20448.15.13.0.0.0.0.152.1144.3j7.10.0....0...1c.1.64.psy-ab..5.10.1142...0i131i67k1j0i131k1.0.Y8wOam42qDc";
                System.out.println(city + ": " + findTemp(link)+"\u2103");                
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

    }

    public static String findTemp(String link) {
        Document doc = null;
        String html = null;
        try {
            html = Jsoup.connect(link).get().html();
            doc = Jsoup.parse(html);
            return doc.select("span#wob_tm[style]").text();
        } catch (IOException ex) {
            return "Error";
        }
    }
}
