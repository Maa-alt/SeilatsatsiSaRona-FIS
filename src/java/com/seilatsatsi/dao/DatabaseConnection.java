package com.seilatsatsi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static Connection connection = null;
    
    // Database configuration - Read from environment variables
    private static final String URL = "jdbc:mysql://mysql-2bb4ee1f-mpeoanemaapesa89-5f41.h.aivencloud.com:26541/defaultdb?useSSL=true&requireSSL=true&serverTimezone=UTC&sslMode=REQUIRED";
    private static final String USERNAME = "avnadmin";
    private static final String PASSWORD = System.getenv("DB_PASSWORD");
    
    public static Connection getConnection() {
        try {
            if (PASSWORD == null || PASSWORD.isEmpty()) {
                System.err.println("❌ DB_PASSWORD environment variable not set!");
                return null;
            }
            
            if (connection == null || connection.isClosed()) {
                // Load MySQL Driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                
                // Create connection
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("✅ Database connected successfully!");
            }
            return connection;
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL Driver not found! Add mysql-connector jar to project.");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.err.println("❌ Database connection failed!");
            System.err.println("Error: " + e.getMessage());
            System.err.println("Please check:");
            System.err.println("1. MySQL is running (XAMPP/WAMP)");
            System.err.println("2. Database 'seilatsatsi_fis' exists");
            System.err.println("3. Username/password is correct");
            return null;
        }
    }
    
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
    
    // Test connection method
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Connection test PASSED!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Connection test FAILED: " + e.getMessage());
        }
        return false;
    }
    
    // Main method for testing
    public static void main(String[] args) {
        DatabaseConnection.testConnection();
        DatabaseConnection.closeConnection();
    }
}