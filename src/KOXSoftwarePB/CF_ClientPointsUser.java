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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.table.DefaultTableModel;



/**
 *
 * Ramka słuząca do wyszukiwania studentów
 */
public class CF_ClientPointsUser extends javax.swing.JFrame {

/**
 *
 * Konstruktor tworzacy nową ramke
 */
    public CF_ClientPointsUser() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        String n[]={"Numer_karty","Imie","Nazwisko","Punkty"};
        DefaultTableModel model=new DefaultTableModel(null,n);
        ClientPointsUser_ClientData_Table.setModel(model);
        try {
            PokazywanieKlienta(model);
        } catch (Exception ex) {
            Logger.getLogger(CF_ClientPoints.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
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
    public void PokazywanieKlienta(DefaultTableModel model) throws Exception{
        Connection myConn = MySQLConnection.getConnection();
        String what = "";
        int fromInt=LoginSession.UID;
        String fromString="";
        String sqlQuery="";
        int cardNumberTemp=0;
        String nameTemp="";
        String surnameTemp="";
        int pointsTemp=0;
        what="klienci.KLI_LOGIN_ID";
        sqlQuery="SELECT KLI_CARD_NUMBER,KLI_NAME,KLI_SURNAME,KLI_POINTS FROM klienci WHERE "+what+" LIKE '"+fromInt+"' ;";
        
        try (Statement stmt = myConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
          ResultSet rs = stmt.executeQuery(sqlQuery)) {
          while (rs.next()) {
            model.addRow(new Object[4]);
            cardNumberTemp=rs.getInt("KLI_CARD_NUMBER");
            nameTemp=rs.getString("KLI_NAME");
            surnameTemp=rs.getString("KLI_SURNAME");
            pointsTemp=rs.getInt("KLI_POINTS");
            ClientPointsUser_ClientData_Table.setValueAt(cardNumberTemp, 0, 0);
            ClientPointsUser_ClientData_Table.setValueAt(nameTemp, 0, 1);
            ClientPointsUser_ClientData_Table.setValueAt(surnameTemp, 0, 2);
            ClientPointsUser_ClientData_Table.setValueAt(pointsTemp, 0, 3);

        }
         } catch (SQLException e) {
               showMessageDialog(null, "Problem z polaczeniem");
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

        jToggleButton1 = new javax.swing.JToggleButton();
        ClientPointsUser_Header = new javax.swing.JLabel();
        ClientPointsUser_Header2_Label = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ClientPointsUser_ClientData_Table = new javax.swing.JTable();
        ClientPointsUser_NoChanges_Button = new javax.swing.JButton();
        ClientPointsUser_Close_Button = new javax.swing.JButton();

        jToggleButton1.setText("jToggleButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ClientPointsUser_Header.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        ClientPointsUser_Header.setForeground(new java.awt.Color(255, 153, 153));
        ClientPointsUser_Header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ClientPointsUser_Header.setText("Stacja paliw");
        ClientPointsUser_Header.setToolTipText("");

        ClientPointsUser_Header2_Label.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        ClientPointsUser_Header2_Label.setForeground(new java.awt.Color(0, 204, 204));
        ClientPointsUser_Header2_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ClientPointsUser_Header2_Label.setText("Ilość punktów");

        ClientPointsUser_ClientData_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Numer_karty", "Imie", "Nazwisko", "Punkty"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(ClientPointsUser_ClientData_Table);
        if (ClientPointsUser_ClientData_Table.getColumnModel().getColumnCount() > 0) {
            ClientPointsUser_ClientData_Table.getColumnModel().getColumn(0).setResizable(false);
            ClientPointsUser_ClientData_Table.getColumnModel().getColumn(1).setResizable(false);
            ClientPointsUser_ClientData_Table.getColumnModel().getColumn(2).setResizable(false);
        }

        ClientPointsUser_NoChanges_Button.setBackground(new java.awt.Color(255, 0, 0));
        ClientPointsUser_NoChanges_Button.setText("Wróc do menu głownego");
        ClientPointsUser_NoChanges_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientPointsUser_NoChanges_ButtonActionPerformed(evt);
            }
        });

        ClientPointsUser_Close_Button.setBackground(new java.awt.Color(255, 0, 0));
        ClientPointsUser_Close_Button.setText("Wyłącz program");
        ClientPointsUser_Close_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientPointsUser_Close_ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ClientPointsUser_Header, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                    .addComponent(ClientPointsUser_Header2_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ClientPointsUser_NoChanges_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ClientPointsUser_Close_Button, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)))
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(ClientPointsUser_Header, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ClientPointsUser_Header2_Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(ClientPointsUser_NoChanges_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ClientPointsUser_Close_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Metoda sluząca wyjściu z ramki
     */
    private void ClientPointsUser_NoChanges_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientPointsUser_NoChanges_ButtonActionPerformed
        new  CF_MainMenuUser().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ClientPointsUser_NoChanges_ButtonActionPerformed
    /**
     * Metoda sluząca zakończeniu działania aplikacji
     */
    private void ClientPointsUser_Close_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientPointsUser_Close_ButtonActionPerformed
        Runtime.getRuntime().exit(0);
    }//GEN-LAST:event_ClientPointsUser_Close_ButtonActionPerformed

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
            java.util.logging.Logger.getLogger(CF_ClientPointsUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CF_ClientPointsUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CF_ClientPointsUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CF_ClientPointsUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    public javax.swing.JTable ClientPointsUser_ClientData_Table;
    private javax.swing.JButton ClientPointsUser_Close_Button;
    private javax.swing.JLabel ClientPointsUser_Header;
    private javax.swing.JLabel ClientPointsUser_Header2_Label;
    private javax.swing.JButton ClientPointsUser_NoChanges_Button;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables
}
