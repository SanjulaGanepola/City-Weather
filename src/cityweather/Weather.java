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
import javax.swing.table.DefaultTableModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *
 * @author Rohitha
 */
public class Weather extends javax.swing.JFrame {

    static File f = new File("cities.txt");
    DefaultTableModel model;

    /**
     * Creates new form Weather
     */
    public Weather() {
        initComponents();
        model = (DefaultTableModel) jTable1.getModel();
        refreshTable();
    }

    public void refreshTable() {
        ArrayList<ArrayList<String>> current = currentData();
        for (int i = 0; i < current.get(0).size(); i++) {
            model.addRow(new Object[]{current.get(0).get(i), current.get(1).get(i) + "\u2103", current.get(2).get(i), current.get(3).get(i), current.get(4).get(i)});
        }
    }

    public ArrayList currentData() {
        ArrayList<ArrayList<String>> current = new ArrayList<ArrayList<String>>();
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

    public void update() {
        ArrayList<ArrayList<String>> current = currentData();
        PrintWriter pw = null;

        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(f, false)));
            for (int i = 0; i < current.get(0).size(); i++) {
                current.get(1).set(i, find("span#wob_tm[style]", "https://www.google.ca/search?ei=HqAYWsb6E6e_jwSonZaIBQ&q=" + current.get(0).get(i) + "+weather"));
                current.get(2).set(i, find("span#wob_pp", "https://www.google.ca/search?ei=HqAYWsb6E6e_jwSonZaIBQ&q=" + current.get(0).get(i) + "+weather"));
                current.get(3).set(i, find("span#wob_hm", "https://www.google.ca/search?ei=HqAYWsb6E6e_jwSonZaIBQ&q=" + current.get(0).get(i) + "+weather"));
                current.get(4).set(i, find("span#wob_ws", "https://www.google.ca/search?ei=HqAYWsb6E6e_jwSonZaIBQ&q=" + current.get(0).get(i) + "+weather"));
            }

            for (int i = 0; i < current.get(0).size(); i++) {
                pw.println(current.get(0).get(i) + "," + current.get(1).get(i)+ "," + current.get(2).get(i)+ "," + current.get(3).get(i)+ "," + current.get(4).get(i));

            }
            pw.close();

        } catch (IOException ex) {
            System.out.println("Error");
        }
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
            //pw.append(System.getProperty("line.separator"));
            pw.println(newCity + "," + find("span#wob_tm[style]", link) + "," + find("span#wob_pp", link) + "," + find("span#wob_hm", link) + "," + find("span#wob_ws", link));
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

        String link = "https://www.google.ca/search?ei=HqAYWsb6E6e_jwSonZaIBQ&q=" + split + "+weather";

        Document doc = null;
        String html = null;
        String[] splitCompare = null;

        try {
            html = Jsoup.connect(link).get().html();
            doc = Jsoup.parse(html);
            String compare = doc.select("div#wob_loc").text();
            splitCompare = compare.split(",");
        } catch (IOException ex) {
            System.out.println("Error");
        }


        if (splitCompare[0].equalsIgnoreCase(newCity)) {
            add(splitCompare[0], link);
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "City", "Temperature", "Precipitation", "Humidity", "Wind"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
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

        jButton2.setText("ADD →");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Weather Information");

        jButton3.setText("FILTER →");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel2.setText("City:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jButton1)
                    .addComponent(jButton3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Add new city to file
     *
     * @param evt
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        validateCity(jTextField1.getText());
        model.setRowCount(0);
        refreshTable();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * Update information of current cities
     *
     * @param evt
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        update();
        model.setRowCount(0);
        refreshTable();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * Filter data
     *
     * @param evt
     */
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Weather().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
