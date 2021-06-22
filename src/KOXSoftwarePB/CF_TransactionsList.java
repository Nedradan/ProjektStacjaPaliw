/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KOXSoftwarePB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.table.DefaultTableModel;



/**
 *
 * Ramka słuząca do wyszukiwania studentów
 */
public class CF_TransactionsList extends javax.swing.JFrame {

/**
 *
 * Konstruktor tworzacy nową ramke
 */
    public CF_TransactionsList() throws ParseException {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        
        
        
        
        
    }
    /**
     * Metoda służąca do przeszukiwania studentow 
     * @param cnx Obiekt połącznia z bazą
     * @param model Tabela do wyświetlania danych
     * @param i Licznik, zmienna służaca dodawaniu kolejnych wierszy
     * @param fromInt Zmienna przechowująca id Studenta
     * @param sqlQuery Zmienna przechowująca kwerendę
     * @param what Zmienna przechowująca typ pola po którym wyszukujemy wartość
     * @param fromString Zmienna przechowujaca kryterium wyszukiwania
     * @param stmt Sluzy do wykonania kwerendy
     * @param rs Słuzy do przechowywania rezultatów kwerendy
     * @throws e Sprawdzenie czy połączenie z bazą przebiegło pomyslnie
     */
    public void SzukanieTransakcji(DefaultTableModel model) throws Exception{
        Connection myConn = MySQLConnection.getConnection();
        String Input=(String) TransactionList_Options_ComboBox.getSelectedItem();
        int i=0;
        String time="";
        LocalDate date;
        String sqlQuery="";
        int cardNumberTemp=0;
        String nameTemp="";
        String surnameTemp="";
        String billTemp="";
        int transactionID=0;
        LocalDate today = LocalDate.now( ZoneId.of( "Europe/Warsaw" ) ) ;
        String output = today.toString(); 
        switch (Input) {
        case "Dziś":
        sqlQuery="SELECT KLI_CARD_NUMBER,KLI_NAME,KLI_SURNAME,TRA_DATE,TRA_TIME,TRA_ID,TRA_BILL "
                + "FROM transakcje "
                + "INNER JOIN klienci ON transakcje.TRA_CLIENT_ID=klienci.KLI_CARD_NUMBER "
                + "WHERE TRA_DATE LIKE '"+output+"' ;";
        break;
        case "3 dni":
        LocalDate days3=today.minusDays(3);
        String output2 = days3.toString();
        sqlQuery="SELECT KLI_CARD_NUMBER,KLI_NAME,KLI_SURNAME,TRA_DATE,TRA_TIME,TRA_ID,TRA_BILL "
                + "FROM transakcje "
                + "INNER JOIN klienci ON transakcje.TRA_CLIENT_ID=klienci.KLI_CARD_NUMBER "
                + "WHERE TRA_DATE <= CAST('"+output+"' AS DATE) AND TRA_DATE >= CAST('"+output2+"' AS DATE);";        
        break;
        case "7 dni":
        LocalDate days7=today.minusDays(7);
        String output3 = days7.toString();  
        sqlQuery="SELECT KLI_CARD_NUMBER,KLI_NAME,KLI_SURNAME,TRA_DATE,TRA_TIME,TRA_ID,TRA_BILL "
                + "FROM transakcje "
                + "INNER JOIN klienci ON transakcje.TRA_CLIENT_ID=klienci.KLI_CARD_NUMBER "
                + "WHERE TRA_DATE <= CAST('"+output+"' AS DATE) AND TRA_DATE >= CAST('"+output3+"' AS DATE);";         
        break;
        case "14 dni":
        LocalDate days14=today.minusDays(14);
        String output4 = days14.toString();  
        sqlQuery="SELECT KLI_CARD_NUMBER,KLI_NAME,KLI_SURNAME,TRA_DATE,TRA_TIME,TRA_ID,TRA_BILL "
                + "FROM transakcje "
                + "INNER JOIN klienci ON transakcje.TRA_CLIENT_ID=klienci.KLI_CARD_NUMBER "
                + "WHERE TRA_DATE <= CAST('"+output+"' AS DATE) AND TRA_DATE >= CAST('"+output4+"' AS DATE);";        
        break;
        }
        
        try (Statement stmt = myConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
          ResultSet rs = stmt.executeQuery(sqlQuery)) {
            if (rs.next()==false){
                showMessageDialog(null,"Brak rezerwacji pasujacych do podanych kryteriów");
                return;
            }
            rs.beforeFirst();
            TransactionList_OptionsDelete_ComboBox1.removeAllItems();
        while (rs.next()) {
          model.addRow(new Object[7]);
          cardNumberTemp=rs.getInt("KLI_CARD_NUMBER");
          nameTemp=rs.getString("KLI_NAME");
          surnameTemp=rs.getString("KLI_SURNAME");
          transactionID=rs.getInt("TRA_ID");
          date=rs.getDate("TRA_DATE").toLocalDate();;
          time=rs.getString("TRA_TIME");
          String time2=time.split(":")[0];
          String time3=time.split(":")[1];
          time=time2+":"+time3;
          billTemp=Double.toString(rs.getDouble("TRA_BILL"));
          billTemp+=" zł";
          TransactionList_ClientData_Table.setValueAt(transactionID, i, 0);
          TransactionList_ClientData_Table.setValueAt(date, i, 1);
          TransactionList_ClientData_Table.setValueAt(time, i, 2);
          TransactionList_ClientData_Table.setValueAt(cardNumberTemp, i, 3);
          TransactionList_ClientData_Table.setValueAt(nameTemp, i, 4);
          TransactionList_ClientData_Table.setValueAt(surnameTemp, i, 5);
          TransactionList_ClientData_Table.setValueAt(billTemp, i, 6);
          TransactionList_OptionsDelete_ComboBox1.addItem(transactionID);
          i++;
        }
         } catch (SQLException e) {
               showMessageDialog(null, "Problem z polaczeniem");
    }
    }

    public void deleteTransaction() throws Exception{
        try {
            int reservationID=(int)TransactionList_OptionsDelete_ComboBox1.getSelectedItem();
            Connection myConn = MySQLConnection.getConnection();
            Statement statement = myConn.createStatement();
            int deleteCount = statement.executeUpdate("DELETE FROM transakcje WHERE TRA_ID="+reservationID+";");
            String message="Usunięto transakcje: \n Numer transakcji: "+reservationID;
            showMessageDialog(null, message);
            
            
         } 
        catch (SQLException e) {
               showMessageDialog(null, "Wystapił problem!");
              
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        TransactionList_Header = new javax.swing.JLabel();
        TransactionList_Header_Label = new javax.swing.JLabel();
        TransactionList_Header2_Label = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TransactionList_ClientData_Table = new javax.swing.JTable();
        TransactionList_NoChanges_Button = new javax.swing.JButton();
        TransactionList_Close_Button = new javax.swing.JButton();
        TransactionList_ShowData_Button = new javax.swing.JButton();
        TransactionList_Options_ComboBox = new javax.swing.JComboBox();
        TransactionList_Header_Label1 = new javax.swing.JLabel();
        TransactionList_Header_Label2 = new javax.swing.JLabel();
        TransactionList_OptionsDelete_ComboBox1 = new javax.swing.JComboBox();
        TransactionList_Delete_Button1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        TransactionList_Header.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        TransactionList_Header.setForeground(new java.awt.Color(255, 153, 153));
        TransactionList_Header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TransactionList_Header.setText("Stacja paliw");
        TransactionList_Header.setToolTipText("");

        TransactionList_Header_Label.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        TransactionList_Header_Label.setForeground(new java.awt.Color(0, 204, 204));
        TransactionList_Header_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TransactionList_Header_Label.setText("Wybierz okres czasu:");

        TransactionList_Header2_Label.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        TransactionList_Header2_Label.setForeground(new java.awt.Color(0, 204, 204));
        TransactionList_Header2_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TransactionList_Header2_Label.setText("Lista transakcji");

        TransactionList_ClientData_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Nr Transakcji", "Data", "Godzina", "Numer karty", "Imie", "Nazwisko", "Wartośc rachunku"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(TransactionList_ClientData_Table);
        if (TransactionList_ClientData_Table.getColumnModel().getColumnCount() > 0) {
            TransactionList_ClientData_Table.getColumnModel().getColumn(0).setResizable(false);
            TransactionList_ClientData_Table.getColumnModel().getColumn(1).setResizable(false);
            TransactionList_ClientData_Table.getColumnModel().getColumn(2).setResizable(false);
            TransactionList_ClientData_Table.getColumnModel().getColumn(3).setResizable(false);
            TransactionList_ClientData_Table.getColumnModel().getColumn(4).setResizable(false);
            TransactionList_ClientData_Table.getColumnModel().getColumn(5).setResizable(false);
            TransactionList_ClientData_Table.getColumnModel().getColumn(6).setResizable(false);
        }

        TransactionList_NoChanges_Button.setBackground(new java.awt.Color(255, 0, 0));
        TransactionList_NoChanges_Button.setText("Wróc do menu głownego");
        TransactionList_NoChanges_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TransactionList_NoChanges_ButtonActionPerformed(evt);
            }
        });

        TransactionList_Close_Button.setBackground(new java.awt.Color(255, 0, 0));
        TransactionList_Close_Button.setText("Wyłącz program");
        TransactionList_Close_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TransactionList_Close_ButtonActionPerformed(evt);
            }
        });

        TransactionList_ShowData_Button.setBackground(new java.awt.Color(255, 255, 102));
        TransactionList_ShowData_Button.setText("Pokaż dane");
        TransactionList_ShowData_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TransactionList_ShowData_ButtonActionPerformed(evt);
            }
        });

        TransactionList_Options_ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dziś", "3 dni", "7 dni", "14 dni" }));
        TransactionList_Options_ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TransactionList_Options_ComboBoxActionPerformed(evt);
            }
        });

        TransactionList_Header_Label1.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        TransactionList_Header_Label1.setForeground(new java.awt.Color(0, 204, 204));
        TransactionList_Header_Label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TransactionList_Header_Label1.setText("Usuń transakcje");

        TransactionList_Header_Label2.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        TransactionList_Header_Label2.setForeground(new java.awt.Color(0, 204, 204));
        TransactionList_Header_Label2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TransactionList_Header_Label2.setText("ID:");

        TransactionList_OptionsDelete_ComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TransactionList_OptionsDelete_ComboBox1ActionPerformed(evt);
            }
        });

        TransactionList_Delete_Button1.setBackground(new java.awt.Color(255, 255, 102));
        TransactionList_Delete_Button1.setText("Usuń");
        TransactionList_Delete_Button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TransactionList_Delete_Button1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(TransactionList_Header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(TransactionList_Header2_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TransactionList_Close_Button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TransactionList_NoChanges_Button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(TransactionList_Header_Label, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(TransactionList_Options_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(TransactionList_ShowData_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(TransactionList_Header_Label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(242, 242, 242)
                        .addComponent(TransactionList_Header_Label2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TransactionList_OptionsDelete_ComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(TransactionList_Delete_Button1, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(TransactionList_Header, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TransactionList_Header_Label)
                            .addComponent(TransactionList_ShowData_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(TransactionList_Options_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38)
                .addComponent(TransactionList_Header2_Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(TransactionList_Header_Label1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(TransactionList_Header_Label2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(TransactionList_OptionsDelete_ComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(TransactionList_Delete_Button1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(TransactionList_NoChanges_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TransactionList_Close_Button)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Metoda sluząca wyjściu z ramki
     */
    private void TransactionList_NoChanges_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TransactionList_NoChanges_ButtonActionPerformed
        new  CF_MainMenuAdmin().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_TransactionList_NoChanges_ButtonActionPerformed
    /**
     * Metoda sluząca zakończeniu działania aplikacji
     */
    private void TransactionList_Close_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TransactionList_Close_ButtonActionPerformed
        Runtime.getRuntime().exit(0);
    }//GEN-LAST:event_TransactionList_Close_ButtonActionPerformed
     /**
     * Metoda pośrednicząca w przeszukiwaniu studentów
     * @param n Wzór tabeli
     * @param model Tabela do wyświetlania wyników
     * @param cnx Obiekt do połącznia z bazą
     */
    private void TransactionList_ShowData_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TransactionList_ShowData_ButtonActionPerformed
        String n[]={"Nr transakcji","Data","Godzina","Nr karty","Imie","Nazwisko","Wartość"};
        DefaultTableModel model=new DefaultTableModel(null,n);
        TransactionList_ClientData_Table.setModel(model);
        try {
            SzukanieTransakcji(model);
        } catch (Exception ex) {
            Logger.getLogger(CF_TransactionsList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_TransactionList_ShowData_ButtonActionPerformed

    private void TransactionList_Options_ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TransactionList_Options_ComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TransactionList_Options_ComboBoxActionPerformed

    private void TransactionList_OptionsDelete_ComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TransactionList_OptionsDelete_ComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TransactionList_OptionsDelete_ComboBox1ActionPerformed

    private void TransactionList_Delete_Button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TransactionList_Delete_Button1ActionPerformed
        try {
            deleteTransaction();        // TODO add your handling code here:
        } catch (Exception ex) {
            Logger.getLogger(CF_TransactionsList.class.getName()).log(Level.SEVERE, null, ex);
        }
        String n[]={"Nr transakcji","Data","Godzina","Nr karty","Imie","Nazwisko","Wartość"};
        DefaultTableModel model=new DefaultTableModel(null,n);
        TransactionList_ClientData_Table.setModel(model);
         try {
            SzukanieTransakcji(model);
         } catch (Exception ex) {
            Logger.getLogger(CF_TransactionsList.class.getName()).log(Level.SEVERE, null, ex);
         }
    }//GEN-LAST:event_TransactionList_Delete_Button1ActionPerformed

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
            java.util.logging.Logger.getLogger(CF_TransactionsList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CF_TransactionsList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CF_TransactionsList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CF_TransactionsList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTable TransactionList_ClientData_Table;
    private javax.swing.JButton TransactionList_Close_Button;
    private javax.swing.JButton TransactionList_Delete_Button1;
    private javax.swing.JLabel TransactionList_Header;
    private javax.swing.JLabel TransactionList_Header2_Label;
    private javax.swing.JLabel TransactionList_Header_Label;
    private javax.swing.JLabel TransactionList_Header_Label1;
    private javax.swing.JLabel TransactionList_Header_Label2;
    private javax.swing.JButton TransactionList_NoChanges_Button;
    private javax.swing.JComboBox TransactionList_OptionsDelete_ComboBox1;
    private javax.swing.JComboBox TransactionList_Options_ComboBox;
    private javax.swing.JButton TransactionList_ShowData_Button;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
