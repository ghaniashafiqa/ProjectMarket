/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package market;

import java.sql.*;

/**
 *
 * @author User
 */
public class Database {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/java_market";
    static final String USER = "root";
    static final String PASS = "1234";

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    public ResultSet readQuery(String sqlQuery) {
        try {
            System.out.println(sqlQuery);
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            rs = stmt.executeQuery(sqlQuery);
            
            System.out.println(sqlQuery);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rs;
    }

    public void cudQuery(String sqlQuery) {
        try {
            System.out.println(sqlQuery);
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            stmt.executeUpdate(sqlQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
