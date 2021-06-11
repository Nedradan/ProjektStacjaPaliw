/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KOXSoftwarePB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * 
 */
public class Operations {
    public static boolean isLogin(String username, String password, String usertype, JFrame frame){
        try{
            Connection myConn = MySQLConnection.getConnection(username,password);
            String mySqlQuery = 
                    "SELECT UID, Usertype, login FROM login WHERE login = '"+
                    username+
                    "' AND passsword = '"+
                    password+
                    "' AND Usertype = '"+
                    usertype+
                    "'";
            PreparedStatement preparedStatement = myConn.prepareStatement(mySqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                LoginSession.UID = resultSet.getInt("UID");
                LoginSession.Usertype = resultSet.getString("Usertype");
                LoginSession.login = resultSet.getString("login");
                
                return true;
            }
            
        }catch (Exception exception){
            JOptionPane.showMessageDialog(frame, "Database error: " + exception.getMessage());
        }
        
        return false;
    }
}
