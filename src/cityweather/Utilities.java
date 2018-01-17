/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cityweather;

import static cityweather.Weather.f;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

/**
 *
 * @author Rohitha
 */
public class Utilities {

    public ArrayList<String> readTxtFile(String fileName) {
        File f = new File(fileName);

        ArrayList<String> list = new ArrayList<String>();

        Scanner s = null;

        try {
            s = new Scanner(f);

            while (s.hasNextLine()) {
                list.add(s.nextLine());
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return list;
    }

    public String getHtml(String urlString) {
        String html = null;
        String[] splitCompare = null;

        try {
            html = Jsoup.connect(urlString).get().html();

        } catch (IOException ex) {
            System.out.println("Error");
        }
        return html;
    }

    public String extractContent(String text, String start, String end) {
        String[] starting = text.split(start);
        String[] ending = null;
        String returnStr = "";

        if (starting.length > 1) {
            ending = starting[1].split(end);
            returnStr = ending[0];
        }

        return returnStr;
    }

    public void writeFile(String fileName, String city, String temp) {
        PrintWriter pw = null;
        File f = null;

        try {
            f = new File(fileName);
            pw = new PrintWriter(new BufferedWriter(new FileWriter(f, true)));

            pw.println(city+","+temp);
            pw.close();

        } catch (IOException ex) {
            System.out.println("Error");
        }
    }

    /*
    public void arrayListToFile(ArrayList<ArrayList<String>> list) {

        PrintWriter pw = null;

        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(f, false)));
            String info = "";
            for (int i = 0; i < list.get(0).size(); i++) {
                for (int j = 0; j < list.size(); j++) {
                    info += (list.get(j).get(i) + ",");
                }
            }
            pw.println(info.substring(0, info.length()));
            pw.close();

        } catch (IOException ex) {
            System.out.println("Error");
        }
    }
    */
}
