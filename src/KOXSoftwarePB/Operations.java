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
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * 
 */
public class Operations {
    public static boolean isLogin(String username, String password, String usertype, JFrame frame){
        try{
            Connection myConn = MySQLConnection.getConnection();
            String mySqlQuery = 
                    "SELECT LOG_UID, LOG_USERTYPE, LOG_LOGIN, LOG_PASSWORD FROM login WHERE LOG_LOGIN = '"+
                    username+
                    "' AND LOG_PASSWORD = '"+
                    password+
                    "' AND LOG_USERTYPE = '"+
                    usertype+
                    "'";
            PreparedStatement preparedStatement = myConn.prepareStatement(mySqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()){
                LoginSession.UID = resultSet.getInt("LOG_UID");
                LoginSession.Usertype = resultSet.getString("LOG_USERTYPE");
                LoginSession.loginSession = resultSet.getString("LOG_LOGIN");
                LoginSession.passwordSession = resultSet.getString("LOG_PASSWORD");
                return true;
            }
            
        }catch (Exception exception){
            JOptionPane.showMessageDialog(frame, "Database error: " + exception.getMessage());
        }
        
        return false;
    }
}
