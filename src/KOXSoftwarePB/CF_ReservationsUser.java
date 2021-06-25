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
public class CF_ReservationsUser extends javax.swing.JFrame {

/**
 *
 * Konstruktor tworzacy nową ramke
 */
    public CF_ReservationsUser() throws ParseException {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        String n[]={"Nr rezerwacji","Data","Godzina","Usługa","Nr karty","Imie","Nazwisko"};
        DefaultTableModel model=new DefaultTableModel(null,n);
        ReservationUser_ClientData_Table.setModel(model);
        try {
            SzukanieRezerwacji(model);
        } catch (Exception ex) {
            Logger.getLogger(CF_ReservationsUser.class.getName()).log(Level.SEVERE, null, ex);
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
    public void SzukanieRezerwacji(DefaultTableModel model) throws Exception{
        Connection myConn = MySQLConnection.getConnection();
        int i=0;
        String time="";
        LocalDate date;
        String sqlQuery="";
        int loginID=LoginSession.UID;
        String nameTemp="";
        String surnameTemp="";
        String serviceTemp="";
        int cardNumberTemp;
        int clientID=0;
        int reservationID=0;
        LocalDate today = LocalDate.now( ZoneId.of( "Europe/Warsaw" ) ) ;
        String output = today.toString(); 
        LocalDate days3=today.plusDays(3);
        String output2 = days3.toString();
        
        sqlQuery="SELECT KLI_CARD_NUMBER,KLI_NAME,KLI_SURNAME,CEN_DESCRIPTION,REZ_DATE,REZ_TIME,REZ_ID "
                + "FROM rezerwacje "
                + "INNER JOIN klienci ON rezerwacje.REZ_CLIENT_ID=klienci.KLI_CARD_NUMBER "
                + "INNER JOIN cennik ON rezerwacje.REZ_SERVICE_ID=cennik.CEN_ID "
                + "WHERE KLI_LOGIN_ID="+loginID+" "
                + "AND REZ_DATE >= CAST('"+output+"' AS DATE);";
        
        try (Statement stmt = myConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
          ResultSet rs = stmt.executeQuery(sqlQuery)) {
            ReservationUser_ID_ComboBox1.removeAllItems();
            ReservationUser_Time_ComboBox2.removeAllItems();
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
          ReservationUser_ClientData_Table.setValueAt(reservationID, i, 0);
          ReservationUser_ClientData_Table.setValueAt(date, i, 1);
          ReservationUser_ClientData_Table.setValueAt(time, i, 2);
          ReservationUser_ClientData_Table.setValueAt(serviceTemp, i, 3);
          ReservationUser_ClientData_Table.setValueAt(cardNumberTemp, i, 4);
          ReservationUser_ClientData_Table.setValueAt(nameTemp, i, 5);
          ReservationUser_ClientData_Table.setValueAt(surnameTemp, i, 6);
          ReservationUser_ID_ComboBox1.addItem(reservationID);
          i++;
        }
         } catch (SQLException e) {
               showMessageDialog(null, "Problem z polaczeniem");
    }
    }
     public void deleteRez() throws Exception{
        try {
            int reservationID=(int)ReservationUser_ID_ComboBox1.getSelectedItem();
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
        ReservationUser_Header = new javax.swing.JLabel();
        ReservationUser_Header2_Label = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ReservationUser_ClientData_Table = new javax.swing.JTable();
        ReservationList_NoChanges_Button = new javax.swing.JButton();
        ReservationList_Close_Button = new javax.swing.JButton();
        ReservationUser_Header_Label1 = new javax.swing.JLabel();
        ReservationUser_Header_Label2 = new javax.swing.JLabel();
        ReservationUser_ID_ComboBox1 = new javax.swing.JComboBox();
        ReservationList_Add_Button1 = new javax.swing.JButton();
        ReservationUser_Header_Label3 = new javax.swing.JLabel();
        ReservationUser_Time_ComboBox2 = new javax.swing.JComboBox();
        ReservationUser_Header_Label4 = new javax.swing.JLabel();
        ReservationUser_Service_ComboBox3 = new javax.swing.JComboBox();
        ReservationUser_Header_Label5 = new javax.swing.JLabel();
        ReservationUser_Date_ComboBox2 = new javax.swing.JComboBox();
        ReservationUser_Header_Label6 = new javax.swing.JLabel();
        ReservationList_Delete_Button2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ReservationUser_Header.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        ReservationUser_Header.setForeground(new java.awt.Color(255, 153, 153));
        ReservationUser_Header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ReservationUser_Header.setText("Stacja paliw");
        ReservationUser_Header.setToolTipText("");

        ReservationUser_Header2_Label.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        ReservationUser_Header2_Label.setForeground(new java.awt.Color(0, 204, 204));
        ReservationUser_Header2_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ReservationUser_Header2_Label.setText("Moje rezerwacje mycia");

        ReservationUser_ClientData_Table.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(ReservationUser_ClientData_Table);
        if (ReservationUser_ClientData_Table.getColumnModel().getColumnCount() > 0) {
            ReservationUser_ClientData_Table.getColumnModel().getColumn(0).setResizable(false);
            ReservationUser_ClientData_Table.getColumnModel().getColumn(1).setResizable(false);
            ReservationUser_ClientData_Table.getColumnModel().getColumn(2).setResizable(false);
            ReservationUser_ClientData_Table.getColumnModel().getColumn(3).setResizable(false);
            ReservationUser_ClientData_Table.getColumnModel().getColumn(4).setResizable(false);
            ReservationUser_ClientData_Table.getColumnModel().getColumn(5).setResizable(false);
            ReservationUser_ClientData_Table.getColumnModel().getColumn(6).setResizable(false);
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

        ReservationUser_Header_Label1.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        ReservationUser_Header_Label1.setForeground(new java.awt.Color(0, 204, 204));
        ReservationUser_Header_Label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ReservationUser_Header_Label1.setText("Odwołaj termin mycia");

        ReservationUser_Header_Label2.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        ReservationUser_Header_Label2.setForeground(new java.awt.Color(0, 204, 204));
        ReservationUser_Header_Label2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ReservationUser_Header_Label2.setText("Dzień:");

        ReservationUser_ID_ComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReservationUser_ID_ComboBox1ActionPerformed(evt);
            }
        });

        ReservationList_Add_Button1.setBackground(new java.awt.Color(255, 255, 102));
        ReservationList_Add_Button1.setText("Zarezerwuj");
        ReservationList_Add_Button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReservationList_Add_Button1ActionPerformed(evt);
            }
        });

        ReservationUser_Header_Label3.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        ReservationUser_Header_Label3.setForeground(new java.awt.Color(0, 204, 204));
        ReservationUser_Header_Label3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ReservationUser_Header_Label3.setText("Godzina:");

        ReservationUser_Time_ComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReservationUser_Time_ComboBox2ActionPerformed(evt);
            }
        });

        ReservationUser_Header_Label4.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        ReservationUser_Header_Label4.setForeground(new java.awt.Color(0, 204, 204));
        ReservationUser_Header_Label4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ReservationUser_Header_Label4.setText("Rodzaj");

        ReservationUser_Service_ComboBox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mycie", "Mycie z woskowaniem" }));
        ReservationUser_Service_ComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReservationUser_Service_ComboBox3ActionPerformed(evt);
            }
        });

        ReservationUser_Header_Label5.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        ReservationUser_Header_Label5.setForeground(new java.awt.Color(0, 204, 204));
        ReservationUser_Header_Label5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ReservationUser_Header_Label5.setText("Zarezerwuj termin mycia");

        ReservationUser_Date_ComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReservationUser_Date_ComboBox2ActionPerformed(evt);
            }
        });

        ReservationUser_Header_Label6.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        ReservationUser_Header_Label6.setForeground(new java.awt.Color(0, 204, 204));
        ReservationUser_Header_Label6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ReservationUser_Header_Label6.setText("ID:");

        ReservationList_Delete_Button2.setBackground(new java.awt.Color(255, 255, 102));
        ReservationList_Delete_Button2.setText("Odwołaj");
        ReservationList_Delete_Button2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReservationList_Delete_Button2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ReservationUser_Header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ReservationUser_Header2_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(ReservationList_NoChanges_Button, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                        .addComponent(ReservationList_Close_Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(ReservationUser_Header_Label5, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ReservationUser_Header_Label1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(19, 19, 19)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ReservationUser_Header_Label4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ReservationUser_Service_ComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ReservationList_Add_Button1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ReservationUser_Header_Label3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(ReservationUser_Header_Label2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ReservationUser_Date_ComboBox2, 0, 154, Short.MAX_VALUE)
                            .addComponent(ReservationUser_Time_ComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ReservationUser_Header_Label6, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ReservationList_Delete_Button2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ReservationUser_ID_ComboBox1, 0, 154, Short.MAX_VALUE))
                .addGap(70, 70, 70))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(ReservationUser_Header, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ReservationUser_Header2_Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ReservationUser_Header_Label1)
                    .addComponent(ReservationUser_Header_Label5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ReservationUser_Header_Label2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ReservationUser_Date_ComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(ReservationUser_ID_ComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ReservationUser_Header_Label6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(ReservationUser_Header_Label3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ReservationUser_Time_ComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(ReservationList_Delete_Button2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ReservationUser_Header_Label4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ReservationUser_Service_ComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ReservationList_Add_Button1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
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
        new  CF_MainMenuUser().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_ReservationList_NoChanges_ButtonActionPerformed
    /**
     * Metoda sluząca zakończeniu działania aplikacji
     */
    private void ReservationList_Close_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReservationList_Close_ButtonActionPerformed
        Runtime.getRuntime().exit(0);
    }//GEN-LAST:event_ReservationList_Close_ButtonActionPerformed

    private void ReservationList_Add_Button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReservationList_Add_Button1ActionPerformed
        
        String n[]={"Nr rezerwacji","Data","Godzina","Usługa","Nr karty","Imie","Nazwisko"};
        DefaultTableModel model=new DefaultTableModel(null,n);
        ReservationUser_ClientData_Table.setModel(model);
        try {
            SzukanieRezerwacji(model);
        } catch (Exception ex) {
            Logger.getLogger(CF_ReservationsUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_ReservationList_Add_Button1ActionPerformed

    private void ReservationUser_ID_ComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReservationUser_ID_ComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReservationUser_ID_ComboBox1ActionPerformed

    private void ReservationUser_Time_ComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReservationUser_Time_ComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReservationUser_Time_ComboBox2ActionPerformed

    private void ReservationUser_Service_ComboBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReservationUser_Service_ComboBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReservationUser_Service_ComboBox3ActionPerformed

    private void ReservationUser_Date_ComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReservationUser_Date_ComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ReservationUser_Date_ComboBox2ActionPerformed

    private void ReservationList_Delete_Button2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReservationList_Delete_Button2ActionPerformed
        try {
            deleteRez();        // TODO add your handling code here:
        } catch (Exception ex) {
            Logger.getLogger(CF_ReservationsList.class.getName()).log(Level.SEVERE, null, ex);
        }
        String n[]={"Nr rezerwacji","Data","Godzina","Usługa","Nr karty","Imie","Nazwisko"};
        DefaultTableModel model=new DefaultTableModel(null,n);
        ReservationUser_ClientData_Table.setModel(model);
         try {
            SzukanieRezerwacji(model);
         } catch (Exception ex) {
            Logger.getLogger(CF_ReservationsList.class.getName()).log(Level.SEVERE, null, ex);
         }// TODO add your handling code here:
    }//GEN-LAST:event_ReservationList_Delete_Button2ActionPerformed

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
            java.util.logging.Logger.getLogger(CF_ReservationsUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CF_ReservationsUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CF_ReservationsUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CF_ReservationsUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JButton ReservationList_Add_Button1;
    private javax.swing.JButton ReservationList_Close_Button;
    private javax.swing.JButton ReservationList_Delete_Button2;
    private javax.swing.JButton ReservationList_NoChanges_Button;
    public javax.swing.JTable ReservationUser_ClientData_Table;
    private javax.swing.JComboBox ReservationUser_Date_ComboBox2;
    private javax.swing.JLabel ReservationUser_Header;
    private javax.swing.JLabel ReservationUser_Header2_Label;
    private javax.swing.JLabel ReservationUser_Header_Label1;
    private javax.swing.JLabel ReservationUser_Header_Label2;
    private javax.swing.JLabel ReservationUser_Header_Label3;
    private javax.swing.JLabel ReservationUser_Header_Label4;
    private javax.swing.JLabel ReservationUser_Header_Label5;
    private javax.swing.JLabel ReservationUser_Header_Label6;
    private javax.swing.JComboBox ReservationUser_ID_ComboBox1;
    private javax.swing.JComboBox ReservationUser_Service_ComboBox3;
    private javax.swing.JComboBox ReservationUser_Time_ComboBox2;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
