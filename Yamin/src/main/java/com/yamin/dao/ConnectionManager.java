package com.yamin.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionManager {
    private static String url;
    private static String user;
    private static String pass;

    static {
        try (InputStream in = ConnectionManager.class.getClassLoader().getResourceAsStream("db.properties")) {
            Properties p = new Properties();
            if (in != null) {
                p.load(in);
                url = p.getProperty("db.url");
                user = p.getProperty("db.user");
                pass = p.getProperty("db.password");
            }
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static Connection getConnection() throws Exception {
//        return DriverManager.getConnection(url, user, pass);
//    }
//   
    
    public static Connection getConnection() throws NamingException, SQLException {

        InitialContext context = new InitialContext();      
        DataSource dataSource = (DataSource) context.lookup("java:jboss/datasources/MySqlDS");
        return dataSource.getConnection();
    }
    
}
