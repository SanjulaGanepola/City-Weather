/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cityweather;

import static cityweather.Weather.f;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Sanjula Ganepola
 */
public class FileArrayListManipulation {

    public ArrayList<ArrayList<String>> fileToArrayList(String fileName) {
        File f = new File(fileName);

        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();

        Scanner s = null;

        try {
            s = new Scanner(f);

            while (s.hasNextLine()) {
                //Split the line's information into 
                String records[] = s.nextLine().split(",");

                for (int i = 0; i < records.length; i++) {
                    list.add(new ArrayList<String>());
                }
                for (int i = 0; i < records.length; i++) {
                    list.get(i).add(records[i]);
                }
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        return list;
    }

    public void arrayListToFile(ArrayList<ArrayList<String>> list) {

        PrintWriter pw = null;

        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(f, false)));
            String info = "";
            for (int i = 0; i < list.get(0).size(); i++) {
                for (int j = 0; j < list.size(); j++) {
                    info+=(list.get(j).get(i) + ",");
                }
            }
            pw.println(info.substring(0, info.length()));
            pw.close();

        } catch (IOException ex) {
            System.out.println("Error");
        }
    }
}
