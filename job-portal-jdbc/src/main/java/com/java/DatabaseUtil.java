package com.java;

import java.sql.*;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/job_portal?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456789";
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            createDatabaseAndTables();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void createDatabaseAndTables() {
        String baseUrl = "jdbc:mysql://localhost:3306/?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        
        try (Connection conn = DriverManager.getConnection(baseUrl, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement()) {
            
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS job_portal");
            
        } catch (SQLException e) {
            System.err.println("Error creating database: " + e.getMessage());
            return;
        }
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            String createUsers = 
                "CREATE TABLE IF NOT EXISTS users (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(100) NOT NULL, " +
                "email VARCHAR(100) UNIQUE NOT NULL, " +
                "password VARCHAR(100) NOT NULL, " +
                "role VARCHAR(20) NOT NULL, " +
                "resume TEXT" +
                ")";
            stmt.executeUpdate(createUsers);
            
            String createJobs = 
                "CREATE TABLE IF NOT EXISTS jobs (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "title VARCHAR(200) NOT NULL, " +
                "location VARCHAR(100) NOT NULL, " +
                "salary INT NOT NULL, " +
                "recruiter_id INT NOT NULL, " +
                "skills TEXT NOT NULL, " +
                "FOREIGN KEY (recruiter_id) REFERENCES users(id) ON DELETE CASCADE" +
                ")";
            stmt.executeUpdate(createJobs);
            
            String createApplications = 
                "CREATE TABLE IF NOT EXISTS applications (" +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "user_id INT NOT NULL, " +
                "job_id INT NOT NULL, " +
                "tfidf_score DOUBLE DEFAULT 0.0, " +
                "application_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE, " +
                "FOREIGN KEY (job_id) REFERENCES jobs(id) ON DELETE CASCADE, " +
                "UNIQUE KEY unique_application (user_id, job_id)" +
                ")";
            stmt.executeUpdate(createApplications);
            
        } catch (SQLException e) {
            System.err.println("Error creating tables: " + e.getMessage());
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}