package com.java;

public class Application {
    private int id;
    private int userId;
    private int jobId;
    private double tfIdfScore;
    
    public Application() {}
    
    public Application(int userId, int jobId) {
        this.userId = userId;
        this.jobId = jobId;
        this.tfIdfScore = 0.0;
    }
    
    public Application(int id, int userId, int jobId, double tfIdfScore) {
        this.id = id;
        this.userId = userId;
        this.jobId = jobId;
        this.tfIdfScore = tfIdfScore;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public int getJobId() { return jobId; }
    public void setJobId(int jobId) { this.jobId = jobId; }
    
    public double getTfIdfScore() { return tfIdfScore; }
    public void setTfIdfScore(double tfIdfScore) { this.tfIdfScore = tfIdfScore; }
}