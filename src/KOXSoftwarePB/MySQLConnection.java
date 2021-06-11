/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KOXSoftwarePB;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Majey
 */
public class MySQLConnection {
    public static Connection getConnection(String userName,String password) throws Exception{
        String hostName = "jdbc:mysql://fabryczna7.ddns.net:3307/";
        String dbName = "jasiek";
        String dbUrl = hostName+dbName;
        
        String hostUsername = userName;
        String hostPassword = password;
        
        Class.forName("com.mysql.jdbc.Driver");
        Connection myConn = (Connection)DriverManager.getConnection(dbUrl, hostUsername, hostPassword);

        return myConn;
    }
}
