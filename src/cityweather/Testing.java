/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CityWeather;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Sanjula Ganepola
 */
public class Testing {

    static File f = new File("cities.txt");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        while (true) {

            System.out.println("1) Current Weather");
            System.out.println("2) Update Cities");
            System.out.println("3) Add City");
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
                case 3:
                    add();
                    break;
            }
        }
    }

    public static ArrayList currentData() {

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
    
    /*Previously in the weather class
        public ArrayList currentData() {
        ArrayList<ArrayList<String>> current = new ArrayList<ArrayList<String>>();

        FileArrayManipulation access = new FileArrayManipulation();

        access.fileToArray("cities.txt");

        //name
        current.add(new ArrayList<String>());
        //temp
        current.add(new ArrayList<String>());
        //Precipitation
        current.add(new ArrayList<String>());
        //Humidity
        current.add(new ArrayList<String>());
        //Wind
        current.add(new ArrayList<String>());

        Scanner s = null;
        try {
            s = new Scanner(f);

            while (s.hasNext()) {
                String info[] = s.nextLine().split(",");
                current.get(0).add(info[0]);
                current.get(1).add(info[1]);
                current.get(2).add(info[2]);
                current.get(3).add(info[3]);
                current.get(4).add(info[4]);
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return current;
    }
    */

    public static void update() {
        ArrayList<ArrayList<String>> current = currentData();
        PrintWriter pw = null;

        try {
            pw = new PrintWriter(new FileWriter(f, false));
            for (int i = 0; i < current.get(0).size(); i++) {
                String newTemp = findTemp("https://www.google.ca/search?ei=HqAYWsb6E6e_jwSonZaIBQ&q=" + current.get(0).get(i) + "+weather");
                current.get(1).set(i, newTemp);
            }

            for (int i = 0; i < current.get(0).size(); i++) {
                pw.println(current.get(0).get(i) + "," + current.get(1).get(i));

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

    public static void add() {
        Scanner s = new Scanner(System.in);
        String newCity = s.nextLine();
        String[] words = newCity.split(" ");
        String split = "";
        if (words.length > 1) {
            for (int i = 0; i < words.length; i++) {
                split += words[i] + "+";
            }
            split = split.substring(0, newCity.length());
        } else {
            split = newCity;
        }

        try {
            PrintWriter pw = new PrintWriter(new FileWriter(f, true));
            //pw.append(System.getProperty("line.separator"));
            pw.print("\n");
            pw.print(newCity + "," + findTemp("https://www.google.ca/search?ei=HqAYWsb6E6e_jwSonZaIBQ&q=" + split + "+weather"));

            pw.close();
        } catch (IOException ex) {
            System.out.println("Error");
        }
    }
}
