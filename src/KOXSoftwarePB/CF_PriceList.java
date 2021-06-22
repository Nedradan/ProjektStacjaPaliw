/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KOXSoftwarePB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.table.DefaultTableModel;

/**
 *
 * Ramka służąca edycji studentów
 */
public class CF_PriceList extends javax.swing.JFrame {

    /**
     * Konstruktor tworzący nową ramke
     * @param cnx Obiekt do polaczenia z baza danych
     * @param stmt Sluzy do wykonania kwerendy
     */
    public CF_PriceList() {
        try {
            initComponents();
            setLocationRelativeTo(null);
            setResizable(false);
            PriceList_UpdatePriceList_Button.setVisible(false);
            jLabel4.setVisible(false);
            Connection myConn = MySQLConnection.getConnection();
            try {
                Statement stmt = myConn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM cennik");
                while (rs.next()) {
                    String Name=rs.getString("CEN_DESCRIPTION");
                    PriceList_Desc_ComboBox.addItem(Name);
                }
            String n[]={"Usługa","Cena"};
            DefaultTableModel model=new DefaultTableModel(null,n);
            PriceList_PriceData_Table.setModel(model);     
            PokazywanieDanychCennika(model); 
            }catch (SQLException e) {
               showMessageDialog(null, "Problem z połączeniem z bazą");
            }
            /**
             * Funckja która wyświetla studenta po id
             * @param cnx Obiekt połącznia z bazą
             * @param model Tabela do wyświetlania danych
             * @param i Licznik, zmienna służaca dodawaniu kolejnych wierszy
             * @param fromInt Zmienna przechowująca id Studenta
             * @param sqlQuery Zmienna przechowująca kwerendę
             * @param what Zmienna przechowująca typ pola po którym wyszukujemy wartość
             * @param stmt Sluzy do wykonania kwerendy
             * @param rs Słuzy do przechowywania rezultatów kwerendy
             * @param ShowStudent Obiekt klasy CStudenciP
             * @throws e Sprawdzenie czy połączenie z bazą przebiegło pomyslnie
             */
        
        
    
    } catch (SQLException e) {
          showMessageDialog(null, "Problem z połączeniem z bazą");
    } catch (Exception ex) {
            Logger.getLogger(CF_PriceList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void PokazywanieDanychCennika(DefaultTableModel model) throws Exception{
        String what = "";
        int counter=0;
        String sqlQuery="";
        double priceTemp=0;
        String nameTemp="";
        String newPrice="";
        String newPrice2="";
        Connection myConn = MySQLConnection.getConnection();
        
        
        try {
            Statement stmt = myConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            what="cennik.CEN_DESCRIPTION";                
            sqlQuery="SELECT * FROM cennik;";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                    model.addRow(new Object[2]);
                    priceTemp=rs.getDouble("CEN_PRICE");
                    nameTemp=rs.getString("CEN_DESCRIPTION");
                    PriceList_PriceData_Table.setValueAt(nameTemp, counter, 0);
                    PriceList_PriceData_Table.setValueAt(priceTemp, counter, 1);
                    
                    if(!(nameTemp.equals("Mycie")||nameTemp.equals("Mycie z woskowaniem"))){
                        newPrice=String.valueOf(priceTemp)+" zł/l";
                        PriceList_PriceData_Table.setValueAt(nameTemp, counter, 0);
                        PriceList_PriceData_Table.setValueAt(newPrice, counter, 1);
                    }else
                    {
                        newPrice2=String.valueOf(priceTemp)+" zł";
                        PriceList_PriceData_Table.setValueAt(nameTemp, counter, 0);
                        PriceList_PriceData_Table.setValueAt(newPrice2, counter, 1);
                        
                    }
                    
                    counter++;
            }
            }
        catch (SQLException e) {
               showMessageDialog(null, "Problem z połaczeniem");
    }
            
    }
    /**
     * Funckja która wyświetla studenta po id
     * @param cnx Obiekt połącznia z bazą
     * @param model Tabela do wyświetlania danych
     * @param i Licznik, zmienna służaca dodawaniu kolejnych wierszy
     * @param fromInt Zmienna przechowująca id Studenta
     * @param sqlQuery Zmienna przechowująca kwerendę
     * @param what Zmienna przechowująca typ pola po którym wyszukujemy wartość
     * @param stmt Sluzy do wykonania kwerendy
     * @param rs Słuzy do przechowywania rezultatów kwerendy
     * @param ShowStudent Obiekt klasy CStudenciP
     * @throws e Sprawdzenie czy połączenie z bazą przebiegło pomyslnie
     */

    /**
     * Funckja która wyświetla studenta po id
     * @param cnx Obiekt połącznia z bazą
     * @param model Tabela do wyświetlania danych
     * @param i Licznik, zmienna służaca dodawaniu kolejnych wierszy
     * @param fromInt Zmienna przechowująca id Studenta
     * @param sqlQuery Zmienna przechowująca kwerendę
     * @param what Zmienna przechowująca typ pola po którym wyszukujemy wartość
     * @param stmt Sluzy do wykonania kwerendy
     * @param rs Słuzy do przechowywania rezultatów kwerendy
     * @param ShowStudent Obiekt klasy CStudenciP
     * @throws java.lang.Exception
     * @throws e Sprawdzenie czy połączenie z bazą przebiegło pomyslnie
     */

    /**
     * Funckja która wyświetla studenta po id
     * @param cnx Obiekt połącznia z bazą
     * @param model Tabela do wyświetlania danych
     * @param i Licznik, zmienna służaca dodawaniu kolejnych wierszy
     * @param fromInt Zmienna przechowująca id Studenta
     * @param sqlQuery Zmienna przechowująca kwerendę
     * @param what Zmienna przechowująca typ pola po którym wyszukujemy wartość
     * @param stmt Sluzy do wykonania kwerendy
     * @param rs Słuzy do przechowywania rezultatów kwerendy
     * @param ShowStudent Obiekt klasy CStudenciP
     * @return
     * @throws java.lang.Exception
     * @throws e Sprawdzenie czy połączenie z bazą przebiegło pomyslnie
     */
    public void EdytowanieDanychCennika() throws Exception{
        String what = "";
        int counter=0;
        String sqlQuery="";
        double priceTemp=0;
        String nameTemp="";
        String newPrice="";
        Connection myConn = MySQLConnection.getConnection();
        
        
        try {
            String Name=(String)PriceList_Desc_ComboBox.getSelectedItem();
            Statement stmt = myConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            what="cennik.CEN_DESCRIPTION";                
            sqlQuery="SELECT * FROM cennik WHERE "+what+" LIKE '"+Name+"' ;";
            ResultSet rs = stmt.executeQuery(sqlQuery);
            while (rs.next()) {
                    priceTemp=rs.getDouble("CEN_PRICE");
                    nameTemp=rs.getString("CEN_DESCRIPTION");
                    if(!(nameTemp.equals("Mycie")||nameTemp.equals("Mycie z woskowaniem")))            
                        jLabel4.setVisible(true);
                    newPrice=String.valueOf(priceTemp);
                    PriceList_Price_Text.setText(newPrice);
                    PriceList_UpdatePriceList_Button.setVisible(true);
                    counter++;
            }
            }
        catch (SQLException e) {
               showMessageDialog(null, "Problem z połaczeniem");
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

        ClientEdit_Header = new javax.swing.JLabel();
        PriceList_Label2 = new javax.swing.JLabel();
        PriceList_Header_Label = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        PriceList_PriceData_Table = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        PriceList_Price_Text = new javax.swing.JTextPane();
        PriceList_UpdatePriceList_Button = new javax.swing.JButton();
        PriceList_NoChanges_Button = new javax.swing.JButton();
        PriceList_Close_Button = new javax.swing.JButton();
        PriceList_ShowData_Button = new javax.swing.JButton();
        PriceList_Desc_ComboBox = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ClientEdit_Header.setFont(new java.awt.Font("Monospaced", 1, 24)); // NOI18N
        ClientEdit_Header.setForeground(new java.awt.Color(255, 153, 153));
        ClientEdit_Header.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ClientEdit_Header.setText("STACJA PALIW");
        ClientEdit_Header.setToolTipText("");

        PriceList_Label2.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        PriceList_Label2.setForeground(new java.awt.Color(0, 204, 204));
        PriceList_Label2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PriceList_Label2.setText("Co chcesz edytować?");

        PriceList_Header_Label.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        PriceList_Header_Label.setForeground(new java.awt.Color(0, 204, 204));
        PriceList_Header_Label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PriceList_Header_Label.setText("Aktualny cennik");

        PriceList_PriceData_Table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Usługa", "Cena"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(PriceList_PriceData_Table);

        jLabel4.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 204, 204));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("zł/l");
        jLabel4.setMaximumSize(new java.awt.Dimension(198, 25));
        jLabel4.setMinimumSize(new java.awt.Dimension(198, 25));

        jScrollPane5.setViewportView(PriceList_Price_Text);

        PriceList_UpdatePriceList_Button.setBackground(new java.awt.Color(255, 255, 102));
        PriceList_UpdatePriceList_Button.setText("Zaktualizuj dane");
        PriceList_UpdatePriceList_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PriceList_UpdatePriceList_ButtonActionPerformed(evt);
            }
        });

        PriceList_NoChanges_Button.setBackground(new java.awt.Color(255, 0, 0));
        PriceList_NoChanges_Button.setText("Wróc do menu głownego");
        PriceList_NoChanges_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PriceList_NoChanges_ButtonActionPerformed(evt);
            }
        });

        PriceList_Close_Button.setBackground(new java.awt.Color(255, 0, 0));
        PriceList_Close_Button.setText("Wyłącz program");
        PriceList_Close_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PriceList_Close_ButtonActionPerformed(evt);
            }
        });

        PriceList_ShowData_Button.setBackground(new java.awt.Color(255, 255, 102));
        PriceList_ShowData_Button.setText("Edytuj");
        PriceList_ShowData_Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PriceList_ShowData_ButtonActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Monospaced", 3, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 204, 204));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Edytuj cenę");
        jLabel5.setMaximumSize(new java.awt.Dimension(198, 25));
        jLabel5.setMinimumSize(new java.awt.Dimension(198, 25));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(PriceList_UpdatePriceList_Button, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(PriceList_NoChanges_Button, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                                    .addComponent(PriceList_Close_Button, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(ClientEdit_Header, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(PriceList_Header_Label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(PriceList_Label2, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(PriceList_ShowData_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(271, 271, 271)
                                .addComponent(PriceList_Desc_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ClientEdit_Header, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(PriceList_Header_Label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PriceList_Label2)
                    .addComponent(PriceList_Desc_ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(PriceList_ShowData_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(PriceList_UpdatePriceList_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addComponent(PriceList_NoChanges_Button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PriceList_Close_Button)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Funckja która aktualizuje dane studenta 
     * @param cnx Obiekt połącznia z bazą
     * @param model Tabela do wyświetlania danych
     * @param fromInt Zmienna przechowująca id Studenta
     * @param grpInt Zmienna przechowująca grupe Studenta
     * @param Imie Zmienna przechowująca imie Studenta
     * @param Nazwisko Zmienna przechowująca nazwisko Studenta
     * @param Stypendium Zmienna przechowująca stypendium Studenta
     * @param sqlQuery1 Zmienna przechowująca kwerendę1
     * @param sqlQuery2 Zmienna przechowująca kwerendę2
     * @param sqlQuery3 Zmienna przechowująca kwerendę3
     * @param stmt Sluzy do wykonania kwerendy
     * @param rs Słuzy do przechowywania rezultatów kwerendy
     * @throws e Sprawdzenie czy połączenie z bazą przebiegło pomyslnie
     */
    private void PriceList_UpdatePriceList_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PriceList_UpdatePriceList_ButtonActionPerformed
        String priceGetTemp= PriceList_Price_Text.getText();
        String toChange=(String)PriceList_Desc_ComboBox.getSelectedItem();
        double priceGet=0;
        try {
                priceGet= Double.parseDouble(priceGetTemp);
            }
        catch(NumberFormatException e) {
			showMessageDialog(null,"To nie jest liczba!");
                        priceGet=0;
                        PriceList_Price_Text.setText("");
                        return;
		}
        Connection myConn;
        try {
            myConn = MySQLConnection.getConnection();
            Statement stmt = myConn.createStatement();
            String sqlQuery="UPDATE cennik SET CEN_PRICE ="+priceGet+" WHERE CEN_DESCRIPTION LIKE '"+toChange+"';";
            stmt.executeUpdate(sqlQuery);
            showMessageDialog(null,"Pomyślnie zaktualizowano");
            PriceList_Price_Text.setText("");
            PriceList_UpdatePriceList_Button.setVisible(false);
            String n[]={"Usługa","Cena"};
            DefaultTableModel model=new DefaultTableModel(null,n);
            PriceList_PriceData_Table.setModel(model); 
            PokazywanieDanychCennika(model);
            PriceList_UpdatePriceList_Button.setVisible(false);
            
        } catch (Exception ex) {
            Logger.getLogger(CF_PriceList.class.getName()).log(Level.SEVERE, null, ex);
            showMessageDialog(null, "Nie można połączyć z bazą");
        }
        
        
               
    
        
        
    }//GEN-LAST:event_PriceList_UpdatePriceList_ButtonActionPerformed
    /**
     * Metoda sluząca wyjściu z ramki
     */
    private void PriceList_NoChanges_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PriceList_NoChanges_ButtonActionPerformed
        new  CF_MainMenuAdmin().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_PriceList_NoChanges_ButtonActionPerformed
    /**
     * Metoda sluząca zakończeniu działania aplikacji
     */
    private void PriceList_Close_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PriceList_Close_ButtonActionPerformed
        Runtime.getRuntime().exit(0);
    }//GEN-LAST:event_PriceList_Close_ButtonActionPerformed
    /**
     * Metoda pośrednicząca w wyswietlaniu danych studenta po id
     * @param n Wzór tablei
     * @param cnx Obiekt do polaczenia z baza danych
     */
    private void PriceList_ShowData_ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PriceList_ShowData_ButtonActionPerformed
        
        try {
            EdytowanieDanychCennika();
        } catch (Exception ex) {
            Logger.getLogger(CF_PriceList.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_PriceList_ShowData_ButtonActionPerformed
    
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
            java.util.logging.Logger.getLogger(CF_PriceList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CF_PriceList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CF_PriceList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CF_PriceList.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new CF_PriceList().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ClientEdit_Header;
    private javax.swing.JButton PriceList_Close_Button;
    private javax.swing.JComboBox<String> PriceList_Desc_ComboBox;
    private javax.swing.JLabel PriceList_Header_Label;
    private javax.swing.JLabel PriceList_Label2;
    private javax.swing.JButton PriceList_NoChanges_Button;
    private javax.swing.JTable PriceList_PriceData_Table;
    private javax.swing.JTextPane PriceList_Price_Text;
    private javax.swing.JButton PriceList_ShowData_Button;
    private javax.swing.JButton PriceList_UpdatePriceList_Button;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    // End of variables declaration//GEN-END:variables
}
