Job Portal System - JDBC & MySQL
A console-based Job Recruitment System built with Java, JDBC, and MySQL. It allows recruiters to post jobs and rank applicants using TF-IDF scoring, while job seekers can search, apply, and manage their resumes.

Features
🔐 Authentication
User registration with role selection (JobSeeker / Recruiter)

Secure login using email and password

👨‍💼 Recruiter Features
Post new jobs with title, location, salary, and required skills

View all posted jobs with applicant count

View list of applicants for a specific job

Rank applicants using TF-IDF algorithm based on resume match with required skills

👩‍💻 Job Seeker Features
View all available jobs

Search jobs by keyword (title or skills)

Apply to jobs (duplicate application prevention)

Update personal resume text

💾 Data Persistence
All data stored in MySQL database

Tables created automatically on first run

Foreign key relationships maintain data integrity

Technologies Used
Technology	Purpose
Java 11	Core application logic
JDBC	Database connectivity
MySQL 8.0	Relational database
Maven	Dependency management & build
Eclipse IDE	Development environment
Project Structure
job-portal-jdbc/
├── pom.xml
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── java/
│                   ├── Main.java                 # Entry point
│                   ├── User.java                 # User model
│                   ├── Job.java                  # Job model
│                   ├── Application.java          # Application model
│                   ├── DatabaseUtil.java         # DB connection & table creation
│                   ├── UserDAO.java              # User database operations
│                   ├── JobDAO.java               # Job database operations
│                   ├── ApplicationDAO.java       # Application database operations
│                   └── JobSystem.java            # Main service & menu logic
└── README.md

Module Connections

┌─────────────────────────────────────────────────────────────┐
│                         Main.java                            │
│                    (Application Entry)                        │
└───────────────────────────┬─────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                      JobSystem.java                          │
│            (Business Logic & User Interface)                 │
└───────┬───────────────────┬───────────────────┬─────────────┘
        │                   │                   │
        ▼                   ▼                   ▼
┌───────────────┐   ┌───────────────┐   ┌───────────────┐
│   UserDAO     │   │    JobDAO     │   │ ApplicationDAO│
│  (User CRUD)  │   │  (Job CRUD)   │   │ (App CRUD)    │
└───────┬───────┘   └───────┬───────┘   └───────┬───────┘
        │                   │                   │
        └───────────────────┼───────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                    DatabaseUtil.java                         │
│       (Connection Pooling & Table Auto-Creation)             │
└───────────────────────────┬─────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────────┐
│                     MySQL Database                           │
│  ┌─────────┐    ┌─────────┐    ┌──────────────┐             │
│  │  users  │◄───│  jobs   │    │ applications │             │
│  └─────────┘    └────┬────┘    └──────┬───────┘             │
│                      └────────────────┘                      │
│                    (Foreign Key Relations)                   │
└─────────────────────────────────────────────────────────────┘

Flow Explanation:

Main starts JobSystem which displays menu.

JobSystem uses DAO classes for database operations.

DAO classes use DatabaseUtil.getConnection() to get MySQL connection.

DatabaseUtil automatically creates database and tables on first run.

TF-IDF ranking is computed in JobSystem using resume texts from DB.

Setup Instructions
Prerequisites
Java JDK 11 or higher

MySQL Server 8.0+

Maven (or Eclipse with Maven plugin)

Eclipse IDE (optional)

Database Schema
users
Column	Type	Description
id	INT (PK, AI)	Unique user ID
name	VARCHAR(100)	Full name
email	VARCHAR(100)	Unique login email
password	VARCHAR(100)	Login password
role	VARCHAR(20)	'JobSeeker' or 'Recruiter'
resume	TEXT	Resume text (JobSeeker)
jobs
Column	Type	Description
id	INT (PK, AI)	Unique job ID
title	VARCHAR(200)	Job title
location	VARCHAR(100)	Job location
salary	INT	Annual salary
recruiter_id	INT (FK)	References users(id)
skills	TEXT	Comma-separated required skills
applications
Column	Type	Description
id	INT (PK, AI)	Unique application ID
user_id	INT (FK)	References users(id)
job_id	INT (FK)	References jobs(id)
tfidf_score	DOUBLE	Computed match score
application_date	TIMESTAMP	Auto-set on creation
Foreign Keys:

jobs.recruiter_id → users.id (ON DELETE CASCADE)

applications.user_id → users.id (ON DELETE CASCADE)

applications.job_id → jobs.id (ON DELETE CASCADE)

TF-IDF Ranking Algorithm
The system calculates a relevance score for each applicant's resume against the job's required skills.

Term Frequency (TF): (skill occurrences in resume) / (total words in resume)

Inverse Document Frequency (IDF): log((total resumes + 1) / (resumes containing skill + 1))

Score = Σ (TF × IDF) for each required skill.

Higher scores indicate better skill match.

Future Enhancements
GUI using JavaFX or Swing

Email notifications on application

Resume file upload (.pdf, .docx)

Advanced search filters (location, salary range)

Admin dashboard

REST API using Spring Boot

Troubleshooting
"Access denied for user 'root'@'localhost'"
Ensure MySQL password in DatabaseUtil.java is correct.

Try connecting via command line: mysql -u root -p.

"Public Key Retrieval is not allowed"
Already handled in connection URL (allowPublicKeyRetrieval=true).

"Text Blocks are only available with source level 15"
If using Java 11, replace text block strings (""") with concatenated strings as provided in code.
