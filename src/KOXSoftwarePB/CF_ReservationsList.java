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
public class CF_ReservationsList extends javax.swing.JFrame {

/**
 *
 * Konstruktor tworzacy nową ramke
 */
    public CF_ReservationsList() throws ParseException {
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
    public void SzukanieRezerwacji(DefaultTableModel model) throws Exception{
        Connection myConn = MySQLConnection.getConnection();
        String Input=(String) ReservationList_Options_ComboBox.getSelectedItem();
        int i=0;
        String time="";
        LocalDate date;
        String sqlQuery="";
        int cardNumberTemp=0;
        String nameTemp="";
        String surnameTemp="";
        String serviceTemp="";
        int clientID=0;
        int reservationID=0;
        LocalDate today = LocalDate.now( ZoneId.of( "Europe/Warsaw" ) ) ;
        String output = today.toString(); 
        switch (Input) {
        case "Dziś":
        sqlQuery="SELECT KLI_CARD_NUMBER,KLI_NAME,KLI_SURNAME,CEN_DESCRIPTION,REZ_DATE,REZ_TIME,REZ_ID FROM rezerwacje INNER JOIN klienci ON rezerwacje.REZ_CLIENT_ID=klienci.KLI_CARD_NUMBER INNER JOIN cennik ON rezerwacje.REZ_SERVICE_ID=cennik.CEN_ID WHERE REZ_DATE LIKE '"+output+"' ;";
        break;
        case "3 dni":
        LocalDate days3=today.plusDays(3);
        String output2 = days3.toString();
        sqlQuery="SELECT KLI_CARD_NUMBER,KLI_NAME,KLI_SURNAME,CEN_DESCRIPTION,REZ_DATE,REZ_TIME,REZ_ID FROM rezerwacje INNER JOIN klienci ON rezerwacje.REZ_CLIENT_ID=klienci.KLI_CARD_NUMBER INNER JOIN cennik ON rezerwacje.REZ_SERVICE_ID=cennik.CEN_ID WHERE REZ_DATE >= CAST('"+output+"' AS DATE) AND REZ_DATE <= CAST('"+output2+"' AS DATE);";        
        break;
        case "7 dni":
        LocalDate days7=today.plusDays(7);
        String output3 = days7.toString();  
        sqlQuery="SELECT KLI_CARD_NUMBER,KLI_NAME,KLI_SURNAME,CEN_DESCRIPTION,REZ_DATE,REZ_TIME,REZ_ID FROM rezerwacje INNER JOIN klienci ON rezerwacje.REZ_CLIENT_ID=klienci.KLI_CARD_NUMBER INNER JOIN cennik ON rezerwacje.REZ_SERVICE_ID=cennik.CEN_ID WHERE REZ_DATE >= CAST('"+output+"' AS DATE) AND REZ_DATE <= CAST('"+output3+"' AS DATE);";        
        break;
        case "14 dni":
        LocalDate days14=today.plusDays(14);
        String output4 = days14.toString();  
        sqlQuery="SELECT KLI_CARD_NUMBER,KLI_NAME,KLI_SURNAME,CEN_DESCRIPTION,REZ_DATE,REZ_TIME,REZ_ID FROM rezerwacje INNER JOIN klienci ON rezerwacje.REZ_CLIENT_ID=klienci.KLI_CARD_NUMBER INNER JOIN cennik ON rezerwacje.REZ_SERVICE_ID=cennik.CEN_ID WHERE REZ_DATE >= CAST('"+output+"' AS DATE) AND REZ_DATE <= CAST('"+output4+"' AS DATE);";        
        break;
        }
        
        try (Statement stmt = myConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
          ResultSet rs = stmt.executeQuery(sqlQuery)) {
            if (rs.next()==false){
                showMessageDialog(null,"Brak rezerwacji pasujacych do podanych kryteriów");
                return;
            }
            rs.beforeFirst();
            ReservationList_OptionsDelete_ComboBox1.removeAllItems();
        while (rs.next()) {
          model.addRow(new Object[7]);
          cardNumberTemp=rs.getInt("KLI_CARD_NUMBER");
          nameTemp=rs.getString("KLI_NAME");
          surnameTemp=rs.getString("KLI_SURNAME");
          reservationID=rs.getInt("REZ_ID");
          date=rs.getDate("REZ_DATE").toLocalDate();;
          time=rs.getString("REZ_TIME");
          String time2=time.split(":")[0];
          String time3=time.split(":")[1];
          time=time2+":"+time3;
          serviceTemp=rs.getString("CEN_DESCRIPTION");
          ReservationList_ClientData_Table.setValueAt(reservationID, i, 0);
          ReservationList_ClientData_Table.setValueAt(date, i, 1);
          ReservationList_ClientData_Table.setValueAt(time, i, 2);
          ReservationList_ClientData_Table.setValueAt(serviceTemp, i, 3);
          ReservationList_ClientData_Table.setValueAt(cardNumberTemp, i, 4);
          ReservationList_ClientData_Table.setValueAt(nameTemp, i, 5);
          ReservationList_ClientData_Table.setValueAt(surnameTemp, i, 6);
          ReservationList_OptionsDelete_ComboBox1.addItem(reservationID);
          i++;
        }
         } catch (SQLException e) {
               showMessageDialog(null, "Problem z polaczeniem");
    }
    }

    public void deleteClient() throws Exception{
        try {
            int reservationID=(int)ReservationList_OptionsDelete_ComboBox1.getSelectedItem();
            Connection myConn = MySQLConnection.getConnection();
            Statement statement = myConn.createStatement();
            int deleteCount = statement.executeUpdate("DELETE FROM rezerwacje WHERE REZ_ID="+reservationID+";");
            String message="Usunięto rezerwacje: \n Numer rezerwacji: "+reservationID;
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
        ClientFind_Header = new javax.swing.JLabel();
        ReservationList_Header_Label = new javax.swing.JLabel();
        ReservationList_Header2_Label = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ReservationList_ClientData_Table = new javax.swing.JTable();
        ReservationList_NoChanges_Button = new javax.swing.JButton();
        ReservationList_Close_Button = new javax.swing.JButton();
        ReservationList_ShowData_Button = new javax.swing.JButton();
        ReservationList_Options_ComboBox = new javax.swing.JComboBox();
        ReservationList_Header_Label1 = new javax.swing.JLabel();
        ReservationList_Header_Label2 = new javax.swing.JLabel();
        ReservationList_OptionsDelete_ComboBox1 = new javax.swing.JComboBox();
        ReservationList_Delete_Button1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ClientFind_Header.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        ClientFind_Header.setForeground(new java.awt.Color(255, 153, 153));
        ClientFind_Header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ClientFind_Header.setText("Stacja paliw");
        ClientFind_Header.setToolTipText("");

        ReservationList_Header_Label.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        ReservationList_Header_Label.setForeground(new java.awt.Color(0, 204, 204));
        ReservationList_Header_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ReservationList_Header_Label.setText("Wybierz okres czasu:");

        ReservationList_Header2_Label.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        ReservationList_Header2_Label.setForeground(new java.awt.Color(0, 204, 204));
        ReservationList_Header2_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ReservationList_Header2_Label.setText("Rezerwacje mycia");

        ReservationList_ClientData_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Nr Rezerwacji", "Data", "Godzina", "Usługa", "Numer karty", "Imie", "Nazwisko"
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
        jScrollPane2.setViewportView(ReservationList_ClientData_Table);
        if (ReservationList_ClientData_Table.getColumnModel().getColumnCount() > 0) {
            ReservationList_ClientData_Table.getColumnModel().getColumn(0).setResizable(false);
            ReservationList_ClientData_Table.getColumnModel().getColumn(1).setResizable(false);
            ReservationList_ClientData_Table.getColumnModel().getColumn(2).setResizable(false);
            ReservationList_ClientData_Table.getColumnModel().getColumn(3).setResizable(false);
            ReservationList_ClientData_Table.getColumnModel().getColumn(4).setResizable(false);
            ReservationList_ClientData_Table.getColumnModel().getColumn(5).setResizable(false);
            ReservationList_ClientData_Table.getColumnModel().getColumn(6).setResizable(false);
        }

        ReservationList_NoChanges_Button.setBackground(new java.awt.Color(255, 0, 0));
        ReservationList_NoChanges_Button.setText("Wróc do menu głownego");
        ReservationList_NoChanges_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReservationList_NoChanges_ButtonActionPerformed(evt);
            }
        });

        ReservationList_Close_Button.setBackground(new java.awt.Color(255, 0, 0));
        ReservationList_Close_Button.setText("Wyłącz program");
        ReservationList_Close_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReservationList_Close_ButtonActionPerformed(evt);
            }
        });

        ReservationList_ShowData_Button.setBackground(new java.awt.Color(255, 255, 102));
        ReservationList_ShowData_Button.setText("Pokaż dane");
        ReservationList_ShowData_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReservationList_ShowData_ButtonActionPerformed(evt);
            }
        });

        ReservationList_Options_ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Dziś", "3 dni", "7 dni", "14 dni" }));
        ReservationList_Options_ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReservationList_Options_ComboBoxActionPerformed(evt);
            }
        });

        ReservationList_Header_Label1.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        ReservationList_Header_Label1.setForeground(new java.awt.Color(0, 204, 204));
        ReservationList_Header_Label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ReservationList_Header_Label1.setText("Usuń rezerwacje");

        ReservationList_Header_Label2.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        ReservationList_Header_Label2.setForeground(new java.awt.Color(0, 204, 204));
        ReservationList_Header_Label2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ReservationList_Header_Label2.setText("ID:");

        ReservationList_OptionsDelete_ComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReservationList_OptionsDelete_ComboBox1ActionPerformed(evt);
            }
        });

        ReservationList_Delete_Button1.setBackground(new java.awt.Color(255, 255, 102));
        ReservationList_Delete_Button1.setText("Usuń");
        ReservationList_Delete_Button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReservationList_Delete_Button1ActionPerformed(evt);
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
                            .addComponent(ClientFind_Header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ReservationList_Header2_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ReservationList_Close_Button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ReservationList_NoChanges_Button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(ReservationList_Header_Label, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(ReservationList_Options_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ReservationList_ShowData_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(ReservationList_Header_Label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(242, 242, 242)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ReservationList_Delete_Button1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ReservationList_Header_Label2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ReservationList_OptionsDelete_ComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(ClientFind_Header, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ReservationList_Header_Label)
                            .addComponent(ReservationList_ShowData_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(ReservationList_Options_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(38, 38, 38)
                .addComponent(ReservationList_Header2_Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ReservationList_Header_Label1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ReservationList_Header_Label2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ReservationList_OptionsDelete_ComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ReservationList_Delete_Button1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(ReservationList_NoChanges_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ReservationList_Close_Button)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Metoda sluząca wyjściu z ramki
     */
    private void ReservationList_NoChanges_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReservationList_NoChanges_ButtonActionPerformed
        new  CF_MainMenuAdmin().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ReservationList_NoChanges_ButtonActionPerformed
    /**
     * Metoda sluząca zakończeniu działania aplikacji
     */
    private void ReservationList_Close_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReservationList_Close_ButtonActionPerformed
        Runtime.getRuntime().exit(0);
    }//GEN-LAST:event_ReservationList_Close_ButtonActionPerformed
     /**
     * Metoda pośrednicząca w przeszukiwaniu studentów
     * @param n Wzór tabeli
     * @param model Tabela do wyświetlania wyników
     * @param cnx Obiekt do połącznia z bazą
     */
    private void ReservationList_ShowData_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReservationList_ShowData_ButtonActionPerformed
        String n[]={"Nr rezerwacji","Data","Godzina","Usługa","Nr karty","Imie","Nazwisko"};
        DefaultTableModel model=new DefaultTableModel(null,n);
        ReservationList_ClientData_Table.setModel(model);
        try {
            SzukanieRezerwacji(model);
        } catch (Exception ex) {
            Logger.getLogger(CF_ReservationsList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ReservationList_ShowData_ButtonActionPerformed

    private void ReservationList_Options_ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReservationList_Options_ComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReservationList_Options_ComboBoxActionPerformed

    private void ReservationList_OptionsDelete_ComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReservationList_OptionsDelete_ComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReservationList_OptionsDelete_ComboBox1ActionPerformed

    private void ReservationList_Delete_Button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReservationList_Delete_Button1ActionPerformed
        try {
            deleteClient();        // TODO add your handling code here:
        } catch (Exception ex) {
            Logger.getLogger(CF_ReservationsList.class.getName()).log(Level.SEVERE, null, ex);
        }
        String n[]={"Nr rezerwacji","Data","Godzina","Usługa","Nr karty","Imie","Nazwisko"};
        DefaultTableModel model=new DefaultTableModel(null,n);
        ReservationList_ClientData_Table.setModel(model);
         try {
            SzukanieRezerwacji(model);
         } catch (Exception ex) {
            Logger.getLogger(CF_ReservationsList.class.getName()).log(Level.SEVERE, null, ex);
         }
    }//GEN-LAST:event_ReservationList_Delete_Button1ActionPerformed

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
            java.util.logging.Logger.getLogger(CF_ReservationsList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CF_ReservationsList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CF_ReservationsList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CF_ReservationsList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ClientFind_Header;
    public javax.swing.JTable ReservationList_ClientData_Table;
    private javax.swing.JButton ReservationList_Close_Button;
    private javax.swing.JButton ReservationList_Delete_Button1;
    private javax.swing.JLabel ReservationList_Header2_Label;
    private javax.swing.JLabel ReservationList_Header_Label;
    private javax.swing.JLabel ReservationList_Header_Label1;
    private javax.swing.JLabel ReservationList_Header_Label2;
    private javax.swing.JButton ReservationList_NoChanges_Button;
    private javax.swing.JComboBox ReservationList_OptionsDelete_ComboBox1;
    private javax.swing.JComboBox ReservationList_Options_ComboBox;
    private javax.swing.JButton ReservationList_ShowData_Button;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
