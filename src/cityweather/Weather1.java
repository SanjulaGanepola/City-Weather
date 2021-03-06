/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cityweather;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Sanjula
 */
public class Weather1 extends javax.swing.JFrame implements DocumentListener {

    static File f = new File("cities.txt");
    DefaultTableModel model;

    TableRowSorter<TableModel> rowSorter = null;

    //Helper class for changing from file to array and array to file
    FileArrayListManipulation access = new FileArrayListManipulation();

    /**
     * Creates new form Weather
     */
    public Weather1() {
        initComponents();

        jTable1.setShowGrid(true);
        model = (DefaultTableModel) jTable1.getModel();
        //refreshTable();

        rowSorter = new TableRowSorter<>(jTable1.getModel());
        jTable1.setRowSorter(rowSorter);
        jTextField2.getDocument().addDocumentListener(this);
    }

    public static ArrayList getCities(String cityFile) {
        Utilities utility = new Utilities();
        return utility.readTxtFile(cityFile);
    }

    public static ArrayList getWeatherData(String weatherDataFile) {
        Utilities utility = new Utilities();
        return utility.readTxtFile(weatherDataFile);
    }

    public static String createWeatherUrl(String city) {
        String urlPrefix = "https://www.google.ca/search?q=";
        String urlSuffix = "+weather";
        String urlReturn = urlPrefix + city + urlSuffix;
        return urlReturn;
    }

    public void refreshTable() {
        model.setRowCount(0);
        ArrayList<ArrayList<String>> current = access.fileToArrayList("cities.txt");
        for (int i = 0; i < current.get(0).size(); i++) {
            model.addRow(new Object[]{current.get(0).get(i), current.get(1).get(i) + "\u2103", current.get(2).get(i), current.get(3).get(i), current.get(4).get(i)});
        }
    }

    public void update() {
        ArrayList<ArrayList<String>> current = access.fileToArrayList("cities.txt");
        for (int i = 0; i < current.get(0).size(); i++) {
            //Update temp
            current.get(1).set(i, find("span#wob_tm[style]", "https://www.google.ca/search?ei=HqAYWsb6E6e_jwSonZaIBQ&q=" + current.get(0).get(i) + "+weather"));
            //Update precipitation
            current.get(2).set(i, find("span#wob_pp", "https://www.google.ca/search?ei=HqAYWsb6E6e_jwSonZaIBQ&q=" + current.get(0).get(i) + "+weather"));
            //Update humidity
            current.get(3).set(i, find("span#wob_hm", "https://www.google.ca/search?ei=HqAYWsb6E6e_jwSonZaIBQ&q=" + current.get(0).get(i) + "+weather"));
            //Update wind
            current.get(4).set(i, find("span#wob_ws", "https://www.google.ca/search?ei=HqAYWsb6E6e_jwSonZaIBQ&q=" + current.get(0).get(i) + "+weather"));
        }

        access.arrayListToFile(current);
    }

    public String find(String location, String link) {
        Document doc = null;
        String html = null;
        try {
            html = Jsoup.connect(link).get().html();
            doc = Jsoup.parse(html);
            return doc.select(location).text();
        } catch (IOException ex) {
            return "Error";
        }
    }

    public void add(String newCity, String link) {
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("cities.txt", true)));
            System.out.println(find("div.today_nowcard-temp", link));
            //pw.println(newCity + "," + find("span#wob_tm[style]", link) + "," + find("span#wob_pp", link) + "," + find("span#wob_hm", link) + "," + find("span#wob_ws", link));
            pw.close();
        } catch (IOException ex) {
            System.out.println("Error");
        }
    }

    public void validateCity(String newCity) {
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

        String googleLink = "https://www.google.ca/search?ei=HqAYWsb6E6e_jwSonZaIBQ&q=" + split + "+weather";

        Document doc = null;
        String html = null;
        String[] splitCompare = null;

        try {
            html = Jsoup.connect(googleLink).get().html();
            doc = Jsoup.parse(html);
            String compare = doc.select("div#wob_loc").text();
            splitCompare = compare.split(",");
        } catch (IOException ex) {
            System.out.println("Error");
        }

        if (splitCompare[0].equalsIgnoreCase(newCity)) {
            String weatherChannel = doc.select("td._Hif > a").get(0).attr("href");
            String build = "https://weather.com/en-CA/weather/today/" + weatherChannel.substring(24) + "," + weatherChannel.substring(24);
            System.out.println(build);
            System.out.println(weatherChannel);
            add(splitCompare[0], weatherChannel);
        } else {
            jTextField1.setText("");
            System.out.println("Error");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "City", "Temperature", "Precipitation", "Humidity", "Wind"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jTable1.setToolTipText("");
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.setFocusable(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
        }

        jButton1.setText("UPDATE DATA");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("ADD CITY →");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Weather Information");

        jLabel3.setText("FILTER CITIES →");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(jButton2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextField2)
                                        .addGap(16, 16, 16)
                                        .addComponent(jButton1)))
                        .addGap(8, 8, 8))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel1)
                        .addGap(8, 8, 8)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton2)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3))
                        .addGap(8, 8, 8))
        );

        pack();
    }// </editor-fold>                        

    /**
     * Add new city to file
     *
     * @param evt
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        validateCity(jTextField1.getText());
        refreshTable();
    }

    /**
     * Update information of current cities
     *
     * @param evt
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        update();
        refreshTable();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Weather.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Weather.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Weather.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Weather.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form 
         java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
         new Weather().setVisible(true);
         }
         });
         */
        Utilities utility = new Utilities();

        ArrayList<String> cities = getCities("city.txt");
        ArrayList<String> dataList = getWeatherData("DataTags.txt");
        File f = new File("cityInfo.txt");
        f.delete();
        for (int i = 0; i < cities.size(); i++) {

            String page = utility.getHtml(createWeatherUrl(cities.get(i)));
            //System.out.println(page);
            for (int j = 0; j < dataList.size(); j++) {
                String dataElement = dataList.get(j);
                String[] split = dataElement.split(",");
                //System.out.println(cities.get(i) + " " + split[0]+ " " + split[1]+ " " + split[2]);
                String weatherType = split[0];
                String start = split[1];
                String end = split[2];
                String weatherElement = utility.extractContent(page, start, end);
                System.out.println(cities.get(i) + " " + split[0] + " " + split[1] + " " + split[2] + " ::::" + weatherElement);
                utility.writeFile("cityInfo.csv", cities.get(i), weatherElement);
            }
        }
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration                   

    @Override
    public void insertUpdate(DocumentEvent e) {
        String text = jTextField2.getText();
        System.out.println(text);

        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + text));
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        String text = jTextField2.getText();
        System.out.println(text);

        if (text.trim().length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)^" + text));
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
