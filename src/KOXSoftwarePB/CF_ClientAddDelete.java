/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KOXSoftwarePB;

import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.table.DefaultTableModel;

/**
 *
 * Ramka do dodawania i usuwania studentów
 */
public class CF_ClientAddDelete extends javax.swing.JFrame {

    /**
     * Kontruktor tworzący nową ramke
     */
    public CF_ClientAddDelete() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        
    }
    /**
     * Funkcja szukająca id Grupy
     * @param cnx Obiekt do polaczenia z baza danych
     * @param stmt Sluzy do wykonania kwerendy
     * @param rs Słuzy do przechowywania rezultatów kwerendy
     * @throws e Sprawdzenie czy połączenie z bazą przebiegło pomyslnie
     */
    
    
    /**
     * Funckja która dodaje studenta
     * @param cnx Obiekt połącznia z bazą
     * @param model Tabela do wyświetlania danych
     * @param NumerGrupy Zmienna przechowująca grupe Studenta
     * @param Imie Zmienna przechowująca imie Studenta
     * @param Nazwisko Zmienna przechowująca nazwisko Studenta
     * @param Stypendium Zmienna przechowująca stypendium Studenta
     * @param sqlQuery1 Zmienna przechowująca kwerendę1
     * @param sqlQuery2 Zmienna przechowująca kwerendę2
     * @param stx Sluzy do wykonania kwerendy
     * @param rs Słuzy do przechowywania rezultatów kwerendy
     * @param insertGroup Kwerenda dodająca studenta
     * @throws e Sprawdzenie czy połączenie z bazą przebiegło pomyslnie
     */
    public void addClient(String Name,String Surname,String Pesel,int BonusPoints) throws Exception{
        try {
            if(Pesel.length()!=11)
            {
                showMessageDialog(null, "Podaj poprawny Pesel!!!");
                return;
            }
            Connection myConn = MySQLConnection.getConnection();
            Statement statement = myConn.createStatement();
            String login=(String) (Name.charAt(0)+Surname);
            
            int loginID=0;
            int cardNumber=0;
            int insertLogin=statement.executeUpdate("INSERT INTO login (LOG_USERTYPE,LOG_LOGIN,LOG_PASSWORD) VALUES('user','"+login+"','"+Pesel+"');");
            String mySqlQuery = 
                    "SELECT LOG_UID FROM login WHERE LOG_PASSWORD = '"+
                    Pesel+
                    "'";
            PreparedStatement preparedStatement = myConn.prepareStatement(mySqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                loginID= resultSet.getInt("LOG_UID");
            }
            int insertCount = statement.executeUpdate("INSERT INTO klienci (KLI_NAME,KLI_SURNAME,KLI_PESEL,KLI_POINTS,KLI_LOGIN_ID) VALUES('"+Name+"', '"+Surname+"','"+Pesel+"','"+BonusPoints+"','"+loginID+"');");
            mySqlQuery = 
                    "SELECT KLI_CARD_NUMBER FROM klienci WHERE KLI_LOGIN_ID = '"+
                    loginID+
                    "'";
            preparedStatement = myConn.prepareStatement(mySqlQuery);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                cardNumber= resultSet.getInt("KLI_CARD_NUMBER");
            }
            ClientAddDelete_Name_Text.setText("");
            ClientAddDelete_Surname_Text.setText("");
            ClientAddDelete_Pesel_Text.setText("");
            String message="Dodano nowego usera: \n Numer Karty: "+cardNumber+" \n Login: "+login+"\n Hasło: "+Pesel;
            showMessageDialog(null, message);
        } 
       
        catch (SQLException e) {
               showMessageDialog(null, e.getMessage());
               
    }
    }
    /**
     * Funckja która usuwa studenta 
     * @param cnx Obiekt połącznia z bazą
     * @param id Zmienna przechowująca id Studenta
     * @param sqlQuery Zmienna przechowująca kwerendę
     * @param statement Sluzy do wykonania kwerendy
     * @param rs Słuzy do przechowywania rezultatów kwerendy
     * @param deleteCount1 Kwerenda usuwająca studenta z pierwszej tabeli
     * @param deleteCount2 Kwerenda usuwająca studenta z drugiej tabeli
     * @throws e Sprawdzenie czy połączenie z bazą przebiegło pomyslnie
     */
    public void deleteClient(int id) throws Exception{
        try {
            int loginID=0;
            int cardNumber=0;
            String name="";
            String surname="";
            Connection myConn = MySQLConnection.getConnection();
            Statement statement = myConn.createStatement();
            String sqlQuery="SELECT * FROM klienci WHERE KLI_CARD_NUMBER='"+id+"'";
            ResultSet rs = statement.executeQuery(sqlQuery);
            if (rs.next()==false){
                showMessageDialog(null,"Brak klienta o danym numerze karty");
                ClientAddDelete_ID_text.setText("");
                return;
            }else
            {
                cardNumber=rs.getInt("KLI_CARD_NUMBER");
                name=rs.getString("KLI_NAME");
                surname=rs.getString("KLI_SURNAME");
                loginID= rs.getInt("KLI_LOGIN_ID"); 
            }
            int deleteCount1 = statement.executeUpdate("DELETE FROM klienci WHERE KLI_LOGIN_ID="+loginID+";");
            int deleteCount2 = statement.executeUpdate("DELETE FROM login WHERE LOG_UID="+loginID+";");
            ClientAddDelete_ID_text.setText("");
            String message="Usunięto klienta: \n Numer Karty: "+cardNumber+" \n Imie: "+name+"\n Nazwisko: "+surname;
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

        ClientAddDelete_header1 = new javax.swing.JLabel();
        ClientAddDelete_Header2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ClientAddDelete_Name_Text = new javax.swing.JTextPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        ClientAddDelete_Surname_Text = new javax.swing.JTextPane();
        ClientAddDelete_AddClient_Button = new javax.swing.JButton();
        ClientAddDelete_NoChanges_Button = new javax.swing.JButton();
        ClientAddDelete_CloseProgram_Button = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        ClientAddDelete_ID_text = new javax.swing.JTextPane();
        jLabel8 = new javax.swing.JLabel();
        ClientAddDelete_DeleteClientID_Button = new javax.swing.JButton();
        ClientAddDelete_BonusPoints_Combo = new javax.swing.JComboBox();
        jScrollPane6 = new javax.swing.JScrollPane();
        ClientAddDelete_Pesel_Text = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ClientAddDelete_header1.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        ClientAddDelete_header1.setForeground(new java.awt.Color(255, 153, 153));
        ClientAddDelete_header1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ClientAddDelete_header1.setText("STACJA PALIW");
        ClientAddDelete_header1.setToolTipText("");

        ClientAddDelete_Header2.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        ClientAddDelete_Header2.setForeground(new java.awt.Color(0, 204, 204));
        ClientAddDelete_Header2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ClientAddDelete_Header2.setText("Dodaj/Usuń Klienta");

        jLabel4.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 204, 204));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Nazwisko:");
        jLabel4.setMaximumSize(new java.awt.Dimension(198, 25));
        jLabel4.setMinimumSize(new java.awt.Dimension(198, 25));

        jLabel5.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 204, 204));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Pesel");

        jLabel6.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 204, 204));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Bonusowe punkty");

        jLabel7.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 204, 204));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Imie:");
        jLabel7.setMaximumSize(new java.awt.Dimension(198, 25));
        jLabel7.setMinimumSize(new java.awt.Dimension(198, 25));

        jScrollPane3.setViewportView(ClientAddDelete_Name_Text);

        jScrollPane5.setViewportView(ClientAddDelete_Surname_Text);

        ClientAddDelete_AddClient_Button.setBackground(new java.awt.Color(255, 255, 102));
        ClientAddDelete_AddClient_Button.setText("DODAJ KLIENTA");
        ClientAddDelete_AddClient_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientAddDelete_AddClient_ButtonActionPerformed(evt);
            }
        });

        ClientAddDelete_NoChanges_Button.setBackground(new java.awt.Color(255, 0, 0));
        ClientAddDelete_NoChanges_Button.setText("Wróc do menu głownego");
        ClientAddDelete_NoChanges_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientAddDelete_NoChanges_ButtonActionPerformed(evt);
            }
        });

        ClientAddDelete_CloseProgram_Button.setBackground(new java.awt.Color(255, 0, 0));
        ClientAddDelete_CloseProgram_Button.setText("Wyłącz program");
        ClientAddDelete_CloseProgram_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientAddDelete_CloseProgram_ButtonActionPerformed(evt);
            }
        });

        jScrollPane4.setViewportView(ClientAddDelete_ID_text);

        jLabel8.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 204, 204));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("NUMER_KARTY");
        jLabel8.setMaximumSize(new java.awt.Dimension(198, 25));
        jLabel8.setMinimumSize(new java.awt.Dimension(198, 25));

        ClientAddDelete_DeleteClientID_Button.setBackground(new java.awt.Color(255, 255, 102));
        ClientAddDelete_DeleteClientID_Button.setText("USUN KLIENTA O DANYM NUMERZE KARTY");
        ClientAddDelete_DeleteClientID_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientAddDelete_DeleteClientID_ButtonActionPerformed(evt);
            }
        });

        ClientAddDelete_BonusPoints_Combo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "5", "10", "15", "20", "25" }));
        ClientAddDelete_BonusPoints_Combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClientAddDelete_BonusPoints_ComboActionPerformed(evt);
            }
        });

        jScrollPane6.setViewportView(ClientAddDelete_Pesel_Text);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ClientAddDelete_Header2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ClientAddDelete_header1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ClientAddDelete_AddClient_Button)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
                                        .addComponent(jScrollPane5)
                                        .addComponent(ClientAddDelete_BonusPoints_Combo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane6))
                                    .addComponent(ClientAddDelete_DeleteClientID_Button)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(133, 133, 133)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 114, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 315, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ClientAddDelete_NoChanges_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ClientAddDelete_CloseProgram_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ClientAddDelete_header1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ClientAddDelete_Header2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(ClientAddDelete_BonusPoints_Combo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ClientAddDelete_AddClient_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ClientAddDelete_DeleteClientID_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(ClientAddDelete_NoChanges_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ClientAddDelete_CloseProgram_Button))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
        /**
     * Metoda pośrednicząca w dodawaniu studenta
     * @param cnx Obiekt połącznia z bazą
     * @param NumerGrupy Zmienna przechowująca grupe Studenta
     * @param Imie Zmienna przechowująca imie Studenta
     * @param Nazwisko Zmienna przechowująca nazwisko Studenta
     * @param Stypendium Zmienna przechowująca stypendium Studenta
     */
    private void ClientAddDelete_AddClient_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientAddDelete_AddClient_ButtonActionPerformed

        String Name=(String)ClientAddDelete_Name_Text.getText();
        String Surname=(String)ClientAddDelete_Surname_Text.getText();
        String Pesel=(String)ClientAddDelete_Pesel_Text.getText();
        String BonusPointsPom=(String)ClientAddDelete_BonusPoints_Combo.getSelectedItem();
        int BonusPoints=Integer.parseInt(BonusPointsPom);
        try {
            addClient(Name,Surname,Pesel,BonusPoints);
        } catch (Exception ex) {
            Logger.getLogger(CF_ClientAddDelete.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_ClientAddDelete_AddClient_ButtonActionPerformed
    /**
     * Metoda sluząca wyjściu z ramki
     */
    private void ClientAddDelete_NoChanges_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientAddDelete_NoChanges_ButtonActionPerformed
        new  CF_MainMenuAdmin().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ClientAddDelete_NoChanges_ButtonActionPerformed
    /**
     * Metoda sluząca zakończeniu działania aplikacji
     */
    private void ClientAddDelete_CloseProgram_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientAddDelete_CloseProgram_ButtonActionPerformed
        Runtime.getRuntime().exit(0);
    }//GEN-LAST:event_ClientAddDelete_CloseProgram_ButtonActionPerformed
    /**
     * Metoda pośrednicząca w usuwaniu studenta
     * @param cnx Obiekt połącznia z bazą
     */
    private void ClientAddDelete_DeleteClientID_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientAddDelete_DeleteClientID_ButtonActionPerformed
        int id=parseInt(ClientAddDelete_ID_text.getText());
        try {
            deleteClient(id);
        } catch (Exception ex) {
            Logger.getLogger(CF_ClientAddDelete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ClientAddDelete_DeleteClientID_ButtonActionPerformed

    private void StudentAddDelete_Money_CheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StudentAddDelete_Money_CheckActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_StudentAddDelete_Money_CheckActionPerformed

    private void ClientAddDelete_BonusPoints_ComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClientAddDelete_BonusPoints_ComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ClientAddDelete_BonusPoints_ComboActionPerformed
    
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
            java.util.logging.Logger.getLogger(CF_ClientAddDelete.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CF_ClientAddDelete.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CF_ClientAddDelete.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CF_ClientAddDelete.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CF_ClientAddDelete().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ClientAddDelete_AddClient_Button;
    private javax.swing.JComboBox ClientAddDelete_BonusPoints_Combo;
    private javax.swing.JButton ClientAddDelete_CloseProgram_Button;
    private javax.swing.JButton ClientAddDelete_DeleteClientID_Button;
    private javax.swing.JLabel ClientAddDelete_Header2;
    private javax.swing.JTextPane ClientAddDelete_ID_text;
    private javax.swing.JTextPane ClientAddDelete_Name_Text;
    private javax.swing.JButton ClientAddDelete_NoChanges_Button;
    private javax.swing.JTextPane ClientAddDelete_Pesel_Text;
    private javax.swing.JTextPane ClientAddDelete_Surname_Text;
    private javax.swing.JLabel ClientAddDelete_header1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    // End of variables declaration//GEN-END:variables
}
