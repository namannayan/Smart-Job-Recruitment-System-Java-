# Job Portal System - JDBC & MySQL

A console-based Job Recruitment System built with Java, JDBC, and MySQL. It allows recruiters to post jobs and rank applicants using TF-IDF scoring, while job seekers can search, apply, and manage their resumes.

---

## 🚀 Features

### 🔐 Authentication
- User registration with role selection (JobSeeker / Recruiter)
- Secure login using email and password

### 👨‍💼 Recruiter Features
- Post new jobs with title, location, salary, and required skills
- View all posted jobs with applicant count
- View list of applicants for a specific job
- Rank applicants using TF-IDF algorithm based on resume match

### 👩‍💻 Job Seeker Features
- View all available jobs
- Search jobs by keyword (title or skills)
- Apply to jobs (duplicate application prevention)
- Update personal resume text

---

## 💾 Data Persistence
- All data stored in MySQL database
- Tables created automatically on first run
- Foreign key relationships maintain data integrity

---

## 🛠️ Technologies Used

| Technology | Purpose |
|-----------|--------|
| Java 11 | Core application logic |
| JDBC | Database connectivity |
| MySQL 8.0 | Relational database |
| Maven | Dependency management & build |
| Eclipse IDE | Development environment |

---

## 📁 Project Structure

job-portal-jdbc/
├── pom.xml
├── src/
│ └── main/
│ └── java/
│ └── com/
│ └── java/
│ ├── Main.java
│ ├── User.java
│ ├── Job.java
│ ├── Application.java
│ ├── DatabaseUtil.java
│ ├── UserDAO.java
│ ├── JobDAO.java
│ ├── ApplicationDAO.java
│ └── JobSystem.java
└── README.md
