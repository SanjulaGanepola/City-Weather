/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cityweather;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Sanjula Ganepola
 */
public class FileArrayManipulation {

    public ArrayList<ArrayList<String>> fileToArray(String fileName) {
        File f = new File(fileName);

        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        
        Scanner s = null;

        try {
            s = new Scanner(f);

            while (s.hasNextLine()) {
                //Split the line's information into 
                String records[] = s.nextLine().split(",");

                for (int i = 0; i < records.length; i++) {
                    data.add(new ArrayList<String>());
                }
                for (int i = 0; i < records.length; i++) {
                    data.get(i).add(records[i]);
                }
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        
        return data;
    }

    public void arrayToFile() {

    }
}
