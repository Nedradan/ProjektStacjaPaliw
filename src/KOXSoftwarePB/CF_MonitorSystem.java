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
public class CF_MonitorSystem extends javax.swing.JFrame {

/**
 *
 * Konstruktor tworzacy nową ramke
 */
    public CF_MonitorSystem() throws ParseException {
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
    public void SzukaniePomiarow(DefaultTableModel model) throws Exception{
        Connection myConn = MySQLConnection.getConnection();
        String Input=(String) MonitorSystem_Options_ComboBox.getSelectedItem();
        String Input2=(String) MonitorSystem_Options2_ComboBox.getSelectedItem();
        String Input3=(String) MonitorSystem_Options3_ComboBox.getSelectedItem();
        int i=0;
        int howManyLogs=45;
        String sqlQuery="";
        int logID=0;
        String time="";
        LocalDate date;
        String nameTemp="";
        String serviceTemp="";
        String valueTemp="";
        String metricTemp="";
        if (!("Wszystkie".equals(Input3)))
            howManyLogs=Integer.parseInt(Input3);
        sqlQuery="SELECT ODC_ID,ODC_DATE,ODC_TIME,ODC_VALUE,CEN_DESCRIPTION,CZUJ_NAME,CZUJ_METRIC "
                + "FROM czujniki "
                + "INNER JOIN odczyty ON czujniki.CZUJ_ID=odczyty.ODC_SOURCE "
                + "INNER JOIN cennik ON czujniki.CZUJ_SOURCE_ID=cennik.CEN_ID "
                + "WHERE CEN_DESCRIPTION LIKE '"+Input+"' AND CZUJ_NAME LIKE '"+Input2+"' "
                + "ORDER BY ODC_DATE DESC,ODC_TIME DESC LIMIT "+howManyLogs+";";        
        try (Statement stmt = myConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
          ResultSet rs = stmt.executeQuery(sqlQuery)) {
            if (rs.next()==false){
                showMessageDialog(null,"Brak odczytów pasujacych do podanych kryteriów");
                return;
            }
            rs.beforeFirst();
            MonitorSystem_OptionsDelete_ComboBox1.removeAllItems();
        while (rs.next()) {
          model.addRow(new Object[7]);
          logID=rs.getInt("ODC_ID");
          date=rs.getDate("ODC_DATE").toLocalDate();;
          time=rs.getString("ODC_TIME");
          String time2=time.split(":")[0];
          String time3=time.split(":")[1];
          time=time2+":"+time3;
          nameTemp=rs.getString("CEN_DESCRIPTION");
          serviceTemp=rs.getString("CZUJ_NAME");
          valueTemp=rs.getString("ODC_VALUE");
          metricTemp=rs.getString("CZUJ_METRIC");
          String value=valueTemp+" "+metricTemp;
          String warning="Wszystko OK";
          int valueCounter=Integer.parseInt(valueTemp);
          switch (metricTemp){
              case "bar":
                  if (!(3<=valueCounter&&valueCounter<6))
                      warning="ALARM!!!";
                  break;
              case "%":
                  if (!(30<=valueCounter))
                      warning="ALARM!!!";
                  break;
              case"°C":
                  if (!(12<=valueCounter&&valueCounter<=14))
                      warning="ALARM!!!";
                  break;
          }
          
          
          MonitorSystem_SystemData_Table.setValueAt(logID, i, 0);
          MonitorSystem_SystemData_Table.setValueAt(date, i, 1);
          MonitorSystem_SystemData_Table.setValueAt(time, i, 2);
          MonitorSystem_SystemData_Table.setValueAt(nameTemp, i, 3);
          MonitorSystem_SystemData_Table.setValueAt(serviceTemp, i, 4);
          MonitorSystem_SystemData_Table.setValueAt(value, i, 5);
          MonitorSystem_SystemData_Table.setValueAt(warning, i, 6);
          MonitorSystem_OptionsDelete_ComboBox1.addItem(logID);
          i++;
        }
         } catch (SQLException e) {
               showMessageDialog(null, "Problem z polaczeniem");
    }
    }

    public void deleteLog() throws Exception{
        try {
            int logID=(int)MonitorSystem_OptionsDelete_ComboBox1.getSelectedItem();
            Connection myConn = MySQLConnection.getConnection();
            Statement statement = myConn.createStatement();
            int deleteCount = statement.executeUpdate("DELETE FROM odczyty WHERE ODC_ID="+logID+";");
            String message="Usunięto log: \n Numer logu: "+logID;
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
        MonitorSystem_Header_Label = new javax.swing.JLabel();
        MonitorSystem_Header2_Label = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        MonitorSystem_SystemData_Table = new javax.swing.JTable();
        MonitorSystem_NoChanges_Button = new javax.swing.JButton();
        MonitorSystem_Close_Button = new javax.swing.JButton();
        MonitorSystem_Options_ComboBox = new javax.swing.JComboBox();
        MonitorSystem_Header_Label1 = new javax.swing.JLabel();
        MonitorSystem_Header_Label2 = new javax.swing.JLabel();
        MonitorSystem_OptionsDelete_ComboBox1 = new javax.swing.JComboBox();
        MonitorSystem_Delete_Button1 = new javax.swing.JButton();
        MonitorSystem_ShowData_Button1 = new javax.swing.JButton();
        MonitorSystem_Options3_ComboBox = new javax.swing.JComboBox();
        MonitorSystem_Header_Label3 = new javax.swing.JLabel();
        MonitorSystem_Options2_ComboBox = new javax.swing.JComboBox();
        MonitorSystem_Header_Label4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ClientFind_Header.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        ClientFind_Header.setForeground(new java.awt.Color(255, 153, 153));
        ClientFind_Header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ClientFind_Header.setText("Stacja paliw");
        ClientFind_Header.setToolTipText("");

        MonitorSystem_Header_Label.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        MonitorSystem_Header_Label.setForeground(new java.awt.Color(0, 204, 204));
        MonitorSystem_Header_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MonitorSystem_Header_Label.setText("Wybierz surowiec");

        MonitorSystem_Header2_Label.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        MonitorSystem_Header2_Label.setForeground(new java.awt.Color(0, 204, 204));
        MonitorSystem_Header2_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MonitorSystem_Header2_Label.setText("Monitoring czujników");

        MonitorSystem_SystemData_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID pomiaru", "Data", "Godzina", "Surowiec", "Rodzaj pomiaru", "Wartość", "Uwagi"
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
        jScrollPane2.setViewportView(MonitorSystem_SystemData_Table);
        if (MonitorSystem_SystemData_Table.getColumnModel().getColumnCount() > 0) {
            MonitorSystem_SystemData_Table.getColumnModel().getColumn(0).setResizable(false);
            MonitorSystem_SystemData_Table.getColumnModel().getColumn(1).setResizable(false);
            MonitorSystem_SystemData_Table.getColumnModel().getColumn(2).setResizable(false);
            MonitorSystem_SystemData_Table.getColumnModel().getColumn(3).setResizable(false);
            MonitorSystem_SystemData_Table.getColumnModel().getColumn(4).setResizable(false);
            MonitorSystem_SystemData_Table.getColumnModel().getColumn(5).setResizable(false);
            MonitorSystem_SystemData_Table.getColumnModel().getColumn(6).setResizable(false);
        }

        MonitorSystem_NoChanges_Button.setBackground(new java.awt.Color(255, 0, 0));
        MonitorSystem_NoChanges_Button.setText("Wróc do menu głownego");
        MonitorSystem_NoChanges_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MonitorSystem_NoChanges_ButtonActionPerformed(evt);
            }
        });

        MonitorSystem_Close_Button.setBackground(new java.awt.Color(255, 0, 0));
        MonitorSystem_Close_Button.setText("Wyłącz program");
        MonitorSystem_Close_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MonitorSystem_Close_ButtonActionPerformed(evt);
            }
        });

        MonitorSystem_Options_ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Benzyna E95", "Benzyna E98", "ON", "LPG", " " }));
        MonitorSystem_Options_ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MonitorSystem_Options_ComboBoxActionPerformed(evt);
            }
        });

        MonitorSystem_Header_Label1.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        MonitorSystem_Header_Label1.setForeground(new java.awt.Color(0, 204, 204));
        MonitorSystem_Header_Label1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MonitorSystem_Header_Label1.setText("Usuń pomiar");

        MonitorSystem_Header_Label2.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        MonitorSystem_Header_Label2.setForeground(new java.awt.Color(0, 204, 204));
        MonitorSystem_Header_Label2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MonitorSystem_Header_Label2.setText("ID:");

        MonitorSystem_OptionsDelete_ComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MonitorSystem_OptionsDelete_ComboBox1ActionPerformed(evt);
            }
        });

        MonitorSystem_Delete_Button1.setBackground(new java.awt.Color(255, 255, 102));
        MonitorSystem_Delete_Button1.setText("Usuń");
        MonitorSystem_Delete_Button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MonitorSystem_Delete_Button1ActionPerformed(evt);
            }
        });

        MonitorSystem_ShowData_Button1.setBackground(new java.awt.Color(255, 255, 102));
        MonitorSystem_ShowData_Button1.setText("Pokaż dane");
        MonitorSystem_ShowData_Button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MonitorSystem_ShowData_Button1ActionPerformed(evt);
            }
        });

        MonitorSystem_Options3_ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "3", "6", "9", "12", "Wszystkie" }));
        MonitorSystem_Options3_ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MonitorSystem_Options3_ComboBoxActionPerformed(evt);
            }
        });

        MonitorSystem_Header_Label3.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        MonitorSystem_Header_Label3.setForeground(new java.awt.Color(0, 204, 204));
        MonitorSystem_Header_Label3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MonitorSystem_Header_Label3.setText("Ile ostatnich pomiarów?");

        MonitorSystem_Options2_ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Poziom paliwa", "Ciśnienie", "Temperatura", " " }));
        MonitorSystem_Options2_ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MonitorSystem_Options2_ComboBoxActionPerformed(evt);
            }
        });

        MonitorSystem_Header_Label4.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        MonitorSystem_Header_Label4.setForeground(new java.awt.Color(0, 204, 204));
        MonitorSystem_Header_Label4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MonitorSystem_Header_Label4.setText("Co chcesz sprawdzić?");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ClientFind_Header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 333, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(MonitorSystem_Close_Button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MonitorSystem_NoChanges_Button, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(MonitorSystem_Header2_Label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MonitorSystem_Header_Label1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(MonitorSystem_Header_Label3, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(MonitorSystem_Options3_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(MonitorSystem_ShowData_Button1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(232, 232, 232)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(MonitorSystem_Delete_Button1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(MonitorSystem_Header_Label2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(MonitorSystem_OptionsDelete_ComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(MonitorSystem_Header_Label4, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(MonitorSystem_Header_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(MonitorSystem_Options2_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(MonitorSystem_Options_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ClientFind_Header, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MonitorSystem_Options_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MonitorSystem_Header_Label, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MonitorSystem_Options2_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MonitorSystem_Header_Label4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(MonitorSystem_Options3_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MonitorSystem_Header_Label3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addComponent(MonitorSystem_Header2_Label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(MonitorSystem_Header_Label1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(MonitorSystem_Header_Label2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(MonitorSystem_OptionsDelete_ComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(MonitorSystem_Delete_Button1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(MonitorSystem_NoChanges_Button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MonitorSystem_Close_Button))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(MonitorSystem_ShowData_Button1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Metoda sluząca wyjściu z ramki
     */
    private void MonitorSystem_NoChanges_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MonitorSystem_NoChanges_ButtonActionPerformed
        new  CF_MainMenuAdmin().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_MonitorSystem_NoChanges_ButtonActionPerformed
    /**
     * Metoda sluząca zakończeniu działania aplikacji
     */
    private void MonitorSystem_Close_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MonitorSystem_Close_ButtonActionPerformed
        Runtime.getRuntime().exit(0);
    }//GEN-LAST:event_MonitorSystem_Close_ButtonActionPerformed

    private void MonitorSystem_Options_ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MonitorSystem_Options_ComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MonitorSystem_Options_ComboBoxActionPerformed

    private void MonitorSystem_OptionsDelete_ComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MonitorSystem_OptionsDelete_ComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MonitorSystem_OptionsDelete_ComboBox1ActionPerformed

    private void MonitorSystem_Delete_Button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MonitorSystem_Delete_Button1ActionPerformed
        try {
            deleteLog();        // TODO add your handling code here:
        } catch (Exception ex) {
            Logger.getLogger(CF_MonitorSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        String n[]={"Nr odczytu","Data","Godzina","Surowiec","Rozdaj pomiaru","Wartośc","Uwagi"};
        DefaultTableModel model=new DefaultTableModel(null,n);
        MonitorSystem_SystemData_Table.setModel(model);
         try {
            SzukaniePomiarow(model);
         } catch (Exception ex) {
            Logger.getLogger(CF_MonitorSystem.class.getName()).log(Level.SEVERE, null, ex);
         }
    }//GEN-LAST:event_MonitorSystem_Delete_Button1ActionPerformed

    private void MonitorSystem_ShowData_Button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MonitorSystem_ShowData_Button1ActionPerformed
        String n[]={"Nr odczytu","Data","Godzina","Surowiec","Rozdaj pomiaru","Wartośc","Uwagi"};
        DefaultTableModel model=new DefaultTableModel(null,n);
        MonitorSystem_SystemData_Table.setModel(model);
        try {
            SzukaniePomiarow(model);
        } catch (Exception ex) {
            Logger.getLogger(CF_ReservationsList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_MonitorSystem_ShowData_Button1ActionPerformed

    private void MonitorSystem_Options3_ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MonitorSystem_Options3_ComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MonitorSystem_Options3_ComboBoxActionPerformed

    private void MonitorSystem_Options2_ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MonitorSystem_Options2_ComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MonitorSystem_Options2_ComboBoxActionPerformed

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
            java.util.logging.Logger.getLogger(CF_MonitorSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CF_MonitorSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CF_MonitorSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CF_MonitorSystem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JLabel ClientFind_Header;
    private javax.swing.JButton MonitorSystem_Close_Button;
    private javax.swing.JButton MonitorSystem_Delete_Button1;
    private javax.swing.JLabel MonitorSystem_Header2_Label;
    private javax.swing.JLabel MonitorSystem_Header_Label;
    private javax.swing.JLabel MonitorSystem_Header_Label1;
    private javax.swing.JLabel MonitorSystem_Header_Label2;
    private javax.swing.JLabel MonitorSystem_Header_Label3;
    private javax.swing.JLabel MonitorSystem_Header_Label4;
    private javax.swing.JButton MonitorSystem_NoChanges_Button;
    private javax.swing.JComboBox MonitorSystem_Options2_ComboBox;
    private javax.swing.JComboBox MonitorSystem_Options3_ComboBox;
    private javax.swing.JComboBox MonitorSystem_OptionsDelete_ComboBox1;
    private javax.swing.JComboBox MonitorSystem_Options_ComboBox;
    private javax.swing.JButton MonitorSystem_ShowData_Button1;
    public javax.swing.JTable MonitorSystem_SystemData_Table;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
