package com.java;

import java.sql.SQLException;
import java.util.*;

public class JobSystem {
    private UserDAO userDAO;
    private JobDAO jobDAO;
    private ApplicationDAO applicationDAO;
    private User currentUser;
    private Scanner sc;
    
    public JobSystem() {
        this.userDAO = new UserDAO();
        this.jobDAO = new JobDAO();
        this.applicationDAO = new ApplicationDAO();
        this.sc = new Scanner(System.in);
    }
    
    void register() {
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();
        System.out.print("Role (JobSeeker/Recruiter): ");
        String role = sc.nextLine();
        String resume = "";
        if (role.equalsIgnoreCase("JobSeeker")) {
            System.out.print("Enter Resume Text (include skills): ");
            resume = sc.nextLine();
        }
        
        User user = new User(0, name, email, pass, role);
        user.setResume(resume);
        
        try {
            int id = userDAO.createUser(user);
            if (id > 0) {
                System.out.println("Registration successful! ");
            } else {
                System.out.println("Registration failed!");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    void login() {
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();
        
        try {
            User user = userDAO.getUserByEmailAndPassword(email, pass);
            if (user != null) {
                currentUser = user;
                System.out.println("Welcome " + user.getName() + " (" + user.getRole() + ")");
            } else {
                System.out.println("Invalid credentials");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    void postJob() {
        if (currentUser == null || !currentUser.getRole().equals("Recruiter")) {
            System.out.println("Only recruiter can post jobs");
            return;
        }
        
        System.out.print("Job Title: ");
        String title = sc.nextLine();
        System.out.print("Location: ");
        String location = sc.nextLine();
        System.out.print("Salary: ");
        int salary = sc.nextInt();
        sc.nextLine();
        System.out.print("Required Skills (comma-separated, e.g., java,python,sql): ");
        String skills = sc.nextLine();
        
        Job job = new Job(0, title, location, salary, currentUser.getId(), skills);
        
        try {
            int id = jobDAO.createJob(job);
            if (id > 0) {
                System.out.println("Job posted successfully! Job ID: " + id);
            } else {
                System.out.println("Failed to post job!");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    void viewJobs() {
        try {
            List<Job> jobs = jobDAO.getAllJobs();
            if (jobs.isEmpty()) {
                System.out.println("No jobs available");
                return;
            }
            
            for (Job j : jobs) {
                int count = applicationDAO.getApplicationCount(j.getId());
                System.out.println("ID: " + j.getId() + " | " + j.getTitle() + " | " + 
                                 j.getLocation() + " | Salary: " + j.getSalary() + 
                                 " | Skills: " + j.getSkills() + " | Applicants: " + count);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    void searchJob() {
        System.out.print("Enter keyword: ");
        String key = sc.nextLine().toLowerCase();
        
        try {
            List<Job> jobs = jobDAO.searchJobs(key);
            if (jobs.isEmpty()) {
                System.out.println("No jobs found matching: " + key);
                return;
            }
            
            for (Job j : jobs) {
                System.out.println("ID: " + j.getId() + " | " + j.getTitle() + " | " + 
                                 j.getLocation() + " | Salary: " + j.getSalary() + 
                                 " | Skills: " + j.getSkills());
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    void applyJob() {
        if (currentUser == null || !currentUser.getRole().equals("JobSeeker")) {
            System.out.println("Only job seekers can apply");
            return;
        }
        
        viewJobs();
        System.out.print("Enter Job ID to apply: ");
        int jobId = sc.nextInt();
        sc.nextLine();
        
        try {
            if (applicationDAO.hasApplied(currentUser.getId(), jobId)) {
                System.out.println("You have already applied for this job!");
                return;
            }
            
            Application app = new Application(currentUser.getId(), jobId);
            if (applicationDAO.createApplication(app)) {
                System.out.println("Application submitted!");
            } else {
                System.out.println("Failed to submit application!");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    void updateResume() {
        if (currentUser == null || !currentUser.getRole().equals("JobSeeker")) {
            System.out.println("Only job seekers can update resume");
            return;
        }
        
        System.out.print("Enter resume text (include skills): ");
        String resume = sc.nextLine();
        
        try {
            if (userDAO.updateResume(currentUser.getId(), resume)) {
                currentUser.setResume(resume);
                System.out.println("Resume updated successfully!");
            } else {
                System.out.println("Failed to update resume!");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    void viewApplicants() {
        if (currentUser == null || !currentUser.getRole().equals("Recruiter")) {
            System.out.println("Only recruiter can view applicants");
            return;
        }
        
        System.out.print("Enter Job ID: ");
        int jobId = sc.nextInt();
        sc.nextLine();
        
        try {
            Job job = jobDAO.getJobById(jobId);
            if (job == null) {
                System.out.println("Job not found");
                return;
            }
            
            List<Application> applications = applicationDAO.getApplicationsByJobId(jobId);
            if (applications.isEmpty()) {
                System.out.println("No applicants yet.");
                return;
            }
            
            System.out.println("\nApplicants for: " + job.getTitle());
            System.out.println("----------------------------------------");
            
            int count = 1;
            for (Application app : applications) {
                User applicant = userDAO.getUserById(app.getUserId());
                if (applicant != null) {
                    System.out.println(count + ". Name: " + applicant.getName() + " | Email: " + applicant.getEmail());
                    count++;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    void rankResumesForJob() {
        if (currentUser == null || !currentUser.getRole().equals("Recruiter")) {
            System.out.println("Only recruiter can rank resumes");
            return;
        }
        
        System.out.print("Enter Job ID to rank applicants: ");
        int jobId = sc.nextInt();
        sc.nextLine();
        
        try {
            Job job = jobDAO.getJobById(jobId);
            if (job == null) {
                System.out.println("Job not found");
                return;
            }
            
            List<Application> jobApplications = applicationDAO.getApplicationsByJobId(jobId);
            if (jobApplications.isEmpty()) {
                System.out.println("No applicants for this job yet.");
                return;
            }
            
            List<String> allResumes = userDAO.getAllResumes();
            Map<Integer, Double> applicantScores = new HashMap<>();
            
            for (Application app : jobApplications) {
                User applicant = userDAO.getUserById(app.getUserId());
                if (applicant == null || applicant.getResume().isEmpty()) {
                    applicantScores.put(app.getUserId(), 0.0);
                    continue;
                }
                
                double score = calculateTFIDFScore(applicant.getResume(), 
                                                   job.getRequiredSkills(), 
                                                   allResumes);
                app.setTfIdfScore(score);
                applicationDAO.updateTfIdfScore(app.getId(), score);
                applicantScores.put(app.getUserId(), score);
            }
            
            List<Map.Entry<Integer, Double>> sortedScores = new ArrayList<>(applicantScores.entrySet());
            sortedScores.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));
            
            System.out.println("\n=== Resume Ranking for Job: " + job.getTitle() + " ===");
            System.out.println("Required Skills: " + job.getSkills());
            System.out.println("\nRanking (based on TF-IDF score):");
            System.out.println("----------------------------------------");
            
            int rank = 1;
            for (Map.Entry<Integer, Double> entry : sortedScores) {
                User applicant = userDAO.getUserById(entry.getKey());
                if (applicant != null) {
                    System.out.printf("%d. %s | Email: %s | Score: %.4f\n", 
                                    rank++, applicant.getName(), applicant.getEmail(), entry.getValue());
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    private double calculateTFIDFScore(String resume, List<String> requiredSkills, List<String> allResumes) {
        if (resume == null || resume.trim().isEmpty() || allResumes == null || allResumes.isEmpty()) {
            return 0.0;
        }
        
        String[] resumeWords = resume.toLowerCase().split("\\W+");
        if (resumeWords.length == 0) {
            return 0.0;
        }
        
        int totalDocs = allResumes.size();
        double totalScore = 0.0;
        
        for (String skill : requiredSkills) {
            skill = skill.toLowerCase().trim();
            
            int docFrequency = 0;
            for (String doc : allResumes) {
                String[] docWords = doc.toLowerCase().split("\\W+");
                for (String word : docWords) {
                    if (word.equals(skill)) {
                        docFrequency++;
                        break;
                    }
                }
            }
            
            int termFrequency = 0;
            for (String word : resumeWords) {
                if (word.equals(skill)) {
                    termFrequency++;
                }
            }
            
            if (termFrequency > 0) {
                double tf = (double) termFrequency / resumeWords.length;
                double idf = Math.log((double) (totalDocs + 1) / (docFrequency + 1));
                totalScore += tf * idf;
            }
        }
        
        return Math.round(totalScore * 10000.0) / 10000.0;
    }
    
    public void menu() {
        while (true) {
            if (currentUser == null) {
                System.out.println("\n=== Job Portal ===");
                System.out.println("1 Register");
                System.out.println("2 Login");
                System.out.println("0 Exit");
                System.out.print("Choice: ");
                int ch = sc.nextInt();
                sc.nextLine();
                switch (ch) {
                    case 1: register(); break;
                    case 2: login(); break;
                    case 0: 
                        System.out.println("Goodbye!");
                        return;
                    default: System.out.println("Invalid choice");
                }
            } else if (currentUser.getRole().equals("Recruiter")) {
                System.out.println("\n=== Recruiter Menu ===");
                System.out.println("1 Post Job");
                System.out.println("2 View Jobs");
                System.out.println("3 View Applicants");
                System.out.println("4 Rank Resumes for Job (TF-IDF)");
                System.out.println("5 Logout");
                System.out.print("Choice: ");
                int ch = sc.nextInt();
                sc.nextLine();
                switch (ch) {
                    case 1: postJob(); break;
                    case 2: viewJobs(); break;
                    case 3: viewApplicants(); break;
                    case 4: rankResumesForJob(); break;
                    case 5: currentUser = null; break;
                    default: System.out.println("Invalid choice");
                }
            } else {
                System.out.println("\n=== Job Seeker Menu ===");
                System.out.println("1 View Jobs");
                System.out.println("2 Search Job");
                System.out.println("3 Apply Job");
                System.out.println("4 Update Resume");
                System.out.println("5 Logout");
                System.out.print("Choice: ");
                int ch = sc.nextInt();
                sc.nextLine();
                switch (ch) {
                    case 1: viewJobs(); break;
                    case 2: searchJob(); break;
                    case 3: applyJob(); break;
                    case 4: updateResume(); break;
                    case 5: currentUser = null; break;
                    default: System.out.println("Invalid choice");
                }
            }
        }
    }
}