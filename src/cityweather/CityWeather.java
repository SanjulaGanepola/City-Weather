/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CityWeather;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        while (true) {

            System.out.println("1) Current");
            System.out.println("2) Update");
            Scanner s = new Scanner(System.in);

            int choice = s.nextInt();
            switch (choice) {
                //Current
                case 1:
                    ArrayList<ArrayList<String>> current = currentData();
                    for (int i = 0; i < current.get(0).size(); i++) {
                        System.out.print(current.get(0).get(i) + ": ");
                        System.out.println(current.get(1).get(i));
                    }
                    break;
                //Update
                case 2:
                    update();
                    break;
            }
        }
    }

    public static ArrayList currentData() {

        File f = new File("cities.txt");
        ArrayList<ArrayList<String>> current = new ArrayList<ArrayList<String>>();
        //name
        current.add(new ArrayList<String>());
        //temp
        current.add(new ArrayList<String>());
        Scanner s = null;
        try {
            s = new Scanner(f);

            while (s.hasNext()) {
                String info[] = s.nextLine().split(",");
                current.get(0).add(info[0]);
                current.get(1).add(info[1]);
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return current;
    }

    public static void update() {

        ArrayList<ArrayList<String>> current = currentData();
        File f = new File("cities.txt");
        PrintWriter pw = null;

        try {
            pw = new PrintWriter(new FileWriter(f, false));
            for (int i = 0; i < current.get(0).size(); i++) {
                String newTemp = findTemp("https://www.google.ca/search?biw=1196&bih=949&ei=-FAYWujfH8aKjwSo262ICA&q=toronto" + current.get(0).get(i) + "+weather&oq=toronto+weather&gs_l=psy-ab.3..35i39k1l2j0i67k1j0i20i263k1j0l6.18728.20320.0.20448.15.13.0.0.0.0.152.1144.3j7.10.0....0...1c.1.64.psy-ab..5.10.1142...0i131i67k1j0i131k1.0.Y8wOam42qDc");
                    current.get(1).set(i, newTemp);
            }
            
            for (int i = 0; i < current.get(0).size(); i++) {
                pw.println(current.get(0).get(i) + ","+current.get(1).get(i));
                
            }
            pw.close();

        } catch (IOException ex) {
            System.out.println("Error");
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
