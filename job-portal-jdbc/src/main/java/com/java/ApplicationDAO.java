package com.java;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {
    
    public boolean createApplication(Application application) throws SQLException {
        String sql = "INSERT INTO applications (user_id, job_id, tfidf_score) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, application.getUserId());
            pstmt.setInt(2, application.getJobId());
            pstmt.setDouble(3, application.getTfIdfScore());
            
            return pstmt.executeUpdate() > 0;
        }
    }
    
    public boolean hasApplied(int userId, int jobId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM applications WHERE user_id = ? AND job_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            pstmt.setInt(2, jobId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    public List<Application> getApplicationsByJobId(int jobId) throws SQLException {
        List<Application> applications = new ArrayList<>();
        String sql = "SELECT * FROM applications WHERE job_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, jobId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Application app = new Application(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("job_id"),
                        rs.getDouble("tfidf_score")
                    );
                    applications.add(app);
                }
            }
        }
        return applications;
    }
    
    public int getApplicationCount(int jobId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM applications WHERE job_id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, jobId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    
    public boolean updateTfIdfScore(int applicationId, double score) throws SQLException {
        String sql = "UPDATE applications SET tfidf_score = ? WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, score);
            pstmt.setInt(2, applicationId);
            
            return pstmt.executeUpdate() > 0;
        }
    }
}