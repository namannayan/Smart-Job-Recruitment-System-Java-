package com.java;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDAO {
    
    public int createJob(Job job) throws SQLException {
        String sql = "INSERT INTO jobs (title, location, salary, recruiter_id, skills) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, job.getTitle());
            pstmt.setString(2, job.getLocation());
            pstmt.setInt(3, job.getSalary());
            pstmt.setInt(4, job.getRecruiterId());
            pstmt.setString(5, job.getSkills());
            
            pstmt.executeUpdate();
            
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }
    
    public List<Job> getAllJobs() throws SQLException {
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM jobs ORDER BY id DESC";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Job job = new Job(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("location"),
                    rs.getInt("salary"),
                    rs.getInt("recruiter_id"),
                    rs.getString("skills")
                );
                jobs.add(job);
            }
        }
        return jobs;
    }
    
    public Job getJobById(int id) throws SQLException {
        String sql = "SELECT * FROM jobs WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Job(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("location"),
                        rs.getInt("salary"),
                        rs.getInt("recruiter_id"),
                        rs.getString("skills")
                    );
                }
            }
        }
        return null;
    }
    
    public List<Job> searchJobs(String keyword) throws SQLException {
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM jobs WHERE LOWER(title) LIKE ? OR LOWER(skills) LIKE ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + keyword.toLowerCase() + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Job job = new Job(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("location"),
                        rs.getInt("salary"),
                        rs.getInt("recruiter_id"),
                        rs.getString("skills")
                    );
                    jobs.add(job);
                }
            }
        }
        return jobs;
    }
}