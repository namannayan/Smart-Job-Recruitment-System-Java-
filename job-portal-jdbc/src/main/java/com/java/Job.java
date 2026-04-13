package com.java;

import java.util.Arrays;
import java.util.List;

public class Job {
    private int id;
    private String title;
    private String location;
    private int salary;
    private int recruiterId;
    private String skills;
    private List<String> requiredSkills;
    
    public Job() {}
    
    public Job(int id, String title, String location, int salary, int recruiterId, String skills) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.salary = salary;
        this.recruiterId = recruiterId;
        this.skills = skills;
        this.requiredSkills = Arrays.asList(skills.toLowerCase().split("\\s*,\\s*"));
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    
    public int getSalary() { return salary; }
    public void setSalary(int salary) { this.salary = salary; }
    
    public int getRecruiterId() { return recruiterId; }
    public void setRecruiterId(int recruiterId) { this.recruiterId = recruiterId; }
    
    public String getSkills() { return skills; }
    public void setSkills(String skills) { 
        this.skills = skills;
        this.requiredSkills = Arrays.asList(skills.toLowerCase().split("\\s*,\\s*"));
    }
    
    public List<String> getRequiredSkills() { return requiredSkills; }
}