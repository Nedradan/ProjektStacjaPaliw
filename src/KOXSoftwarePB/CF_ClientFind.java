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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.table.DefaultTableModel;



/**
 *
 * Ramka słuząca do wyszukiwania studentów
 */
public class CF_ClientFind extends javax.swing.JFrame {

/**
 *
 * Konstruktor tworzacy nową ramke
 */
    public CF_ClientFind() {
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
    public void SzukanieStudenta(DefaultTableModel model) throws Exception{
        Connection myConn = MySQLConnection.getConnection();
        String Input=(String) ClientFind_Options_ComboBox.getSelectedItem();
        String what = "";
        int fromInt=1;
        int i=0;
        String fromString="";
        String sqlQuery="";
        int cardNumberTemp=0;
        String nameTemp="";
        String surnameTemp="";
        String peselTemp="";
        String loginTemp="";
        String passwordTemp="";
        if("".equals(ClientFind_Value_Text.getText()))
            sqlQuery="SELECT KLI_CARD_NUMBER,KLI_NAME,KLI_SURNAME,KLI_PESEL,LOG_LOGIN,LOG_PASSWORD FROM login INNER JOIN klienci ON login.lOG_UID=klienci.KLI_LOGIN_ID;";
        else{
        switch (Input) {
        case "Numer_karty":
        try {
                fromInt = Integer.parseInt(ClientFind_Value_Text.getText());
            }
        catch(NumberFormatException e) {
			showMessageDialog(null,"To nie jest liczba!");                        
                        ClientFind_Value_Text.setText("");
                        return;
		}
        what="klienci.KLI_CARD_NUMBER";
        sqlQuery="SELECT KLI_CARD_NUMBER,KLI_NAME,KLI_SURNAME,KLI_PESEL,LOG_LOGIN,LOG_PASSWORD FROM login INNER JOIN klienci ON login.lOG_UID=klienci.KLI_LOGIN_ID WHERE "+what+" LIKE '"+fromInt+"' ;";
        break;
        case "Imie":
        what="klienci.KLI_NAME";
        fromString=ClientFind_Value_Text.getText();
        sqlQuery="SELECT KLI_CARD_NUMBER,KLI_NAME,KLI_SURNAME,KLI_PESEL,LOG_LOGIN,LOG_PASSWORD FROM login INNER JOIN klienci ON login.lOG_UID=klienci.KLI_LOGIN_ID WHERE "+what+" LIKE '"+fromString+"';";
        break;
        case "Nazwisko":
        what="klienci.KLI_SURNAME";
        fromString=ClientFind_Value_Text.getText();
        sqlQuery="SELECT KLI_CARD_NUMBER,KLI_NAME,KLI_SURNAME,KLI_PESEL,LOG_LOGIN,LOG_PASSWORD FROM login INNER JOIN klienci ON login.lOG_UID=klienci.KLI_LOGIN_ID WHERE "+what+" LIKE '"+fromString+"';";
        break;
        case "Login":
        what="login.LOG_LOGIN";
        fromString=ClientFind_Value_Text.getText();
        sqlQuery="SELECT KLI_CARD_NUMBER,KLI_NAME,KLI_SURNAME,KLI_PESEL,LOG_LOGIN,LOG_PASSWORD FROM login INNER JOIN klienci ON login.lOG_UID=klienci.KLI_LOGIN_ID WHERE "+what+" LIKE '"+fromString+"';";
        break;
        }
        }
        try (Statement stmt = myConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
          ResultSet rs = stmt.executeQuery(sqlQuery)) {
            if (rs.next()==false){
                showMessageDialog(null,"Brak KLIENTA pasującego do podanych kryteriów");
                return;
            }
            rs.beforeFirst();
        while (rs.next()) {
          model.addRow(new Object[6]);
          cardNumberTemp=rs.getInt("KLI_CARD_NUMBER");
          nameTemp=rs.getString("KLI_NAME");
          surnameTemp=rs.getString("KLI_SURNAME");
          peselTemp=rs.getString("KLI_PESEL");
          loginTemp=rs.getString("LOG_LOGIN");
          passwordTemp=rs.getString("LOG_PASSWORD");
          ClientFind_ClientData_Table.setValueAt(cardNumberTemp, i, 0);
          ClientFind_ClientData_Table.setValueAt(nameTemp, i, 1);
          ClientFind_ClientData_Table.setValueAt(surnameTemp, i, 2);
          ClientFind_ClientData_Table.setValueAt(peselTemp, i, 3);
          ClientFind_ClientData_Table.setValueAt(loginTemp, i, 4);
          ClientFind_ClientData_Table.setValueAt(passwordTemp, i, 5);
          i++;
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

        ClientFind_Header = new javax.swing.JLabel();
        ClientFind_Header_Label = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ClientFind_Value_Text = new javax.swing.JTextPane();
        StudentEdit_Header2_Label = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ClientFind_ClientData_Table = new javax.swing.JTable();
        ClientFind_NoChanges_Button = new javax.swing.JButton();
        ClientFind_Close_Button = new javax.swing.JButton();
        ClientFind_ShowData_Button = new javax.swing.JButton();
        ClientFind_Options_ComboBox = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ClientFind_Header.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        ClientFind_Header.setForeground(new java.awt.Color(255, 153, 153));
        ClientFind_Header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ClientFind_Header.setText("Stacja paliw");
        ClientFind_Header.setToolTipText("");

        ClientFind_Header_Label.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        ClientFind_Header_Label.setForeground(new java.awt.Color(0, 204, 204));
        ClientFind_Header_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ClientFind_Header_Label.setText("Szukaj po");

        jScrollPane1.setViewportView(ClientFind_Value_Text);

        StudentEdit_Header2_Label.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        StudentEdit_Header2_Label.setForeground(new java.awt.Color(0, 204, 204));
        StudentEdit_Header2_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        StudentEdit_Header2_Label.setText("Wyniki Wyszukiwania");

        ClientFind_ClientData_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null}
            },
            new String [] {
                "Numer_karty", "Imie", "Nazwisko", "Pesel", "Login", "Hasło"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(ClientFind_ClientData_Table);
        if (ClientFind_ClientData_Table.getColumnModel().getColumnCount() > 0) {
            ClientFind_ClientData_Table.getColumnModel().getColumn(0).setResizable(false);
            ClientFind_ClientData_Table.getColumnModel().getColumn(1).setResizable(false);
            ClientFind_ClientData_Table.getColumnModel().getColumn(2).setResizable(false);
            ClientFind_ClientData_Table.getColumnModel().getColumn(3).setResizable(false);
            ClientFind_ClientData_Table.getColumnModel().getColumn(4).setResizable(false);
            ClientFind_ClientData_Table.getColumnModel().getColumn(5).setResizable(false);
        }

        ClientFind_NoChanges_Button.setBackground(new java.awt.Color(255, 0, 0));
        ClientFind_NoChanges_Button.setText("Wróc do menu głownego");
        ClientFind_NoChanges_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientFind_NoChanges_ButtonActionPerformed(evt);
            }
        });

        ClientFind_Close_Button.setBackground(new java.awt.Color(255, 0, 0));
        ClientFind_Close_Button.setText("Wyłącz program");
        ClientFind_Close_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientFind_Close_ButtonActionPerformed(evt);
            }
        });

        ClientFind_ShowData_Button.setBackground(new java.awt.Color(255, 255, 102));
        ClientFind_ShowData_Button.setText("Pokaż dane");
        ClientFind_ShowData_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientFind_ShowData_ButtonActionPerformed(evt);
            }
        });

        ClientFind_Options_ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Numer_karty", "Imie", "Nazwisko", "Login" }));
        ClientFind_Options_ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientFind_Options_ComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ClientFind_Header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
                    .addComponent(StudentEdit_Header2_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ClientFind_Header_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ClientFind_Options_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(ClientFind_ShowData_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ClientFind_Close_Button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ClientFind_NoChanges_Button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(ClientFind_Header, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ClientFind_Header_Label, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addComponent(ClientFind_Options_ComboBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ClientFind_ShowData_Button)
                .addGap(18, 18, 18)
                .addComponent(StudentEdit_Header2_Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(ClientFind_NoChanges_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ClientFind_Close_Button)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Metoda sluząca wyjściu z ramki
     */
    private void ClientFind_NoChanges_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientFind_NoChanges_ButtonActionPerformed
        new  CF_MainMenuAdmin().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ClientFind_NoChanges_ButtonActionPerformed
    /**
     * Metoda sluząca zakończeniu działania aplikacji
     */
    private void ClientFind_Close_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientFind_Close_ButtonActionPerformed
        Runtime.getRuntime().exit(0);
    }//GEN-LAST:event_ClientFind_Close_ButtonActionPerformed
     /**
     * Metoda pośrednicząca w przeszukiwaniu studentów
     * @param n Wzór tabeli
     * @param model Tabela do wyświetlania wyników
     * @param cnx Obiekt do połącznia z bazą
     */
    private void ClientFind_ShowData_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientFind_ShowData_ButtonActionPerformed
        String n[]={"Numer_karty","Imie","Nazwisko","Pesel","Login","Hasło"};
        DefaultTableModel model=new DefaultTableModel(null,n);
        ClientFind_ClientData_Table.setModel(model);
        try {
            SzukanieStudenta(model);
        } catch (Exception ex) {
            Logger.getLogger(CF_ClientFind.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ClientFind_ShowData_ButtonActionPerformed

    private void ClientFind_Options_ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientFind_Options_ComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ClientFind_Options_ComboBoxActionPerformed

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
            java.util.logging.Logger.getLogger(CF_ClientFind.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CF_ClientFind.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CF_ClientFind.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CF_ClientFind.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
    public javax.swing.JTable ClientFind_ClientData_Table;
    private javax.swing.JButton ClientFind_Close_Button;
    private javax.swing.JLabel ClientFind_Header;
    private javax.swing.JLabel ClientFind_Header_Label;
    private javax.swing.JButton ClientFind_NoChanges_Button;
    private javax.swing.JComboBox ClientFind_Options_ComboBox;
    private javax.swing.JButton ClientFind_ShowData_Button;
    private javax.swing.JTextPane ClientFind_Value_Text;
    private javax.swing.JLabel StudentEdit_Header2_Label;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
