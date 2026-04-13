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


---

## ⚙️ Setup Instructions

### 📌 Prerequisites
- Java JDK 11 or higher
- MySQL Server 8.0+
- Maven
- Eclipse IDE (optional)

---

## 🗄️ Database Schema

### users

| Column | Type | Description |
|--------|------|------------|
| id | INT (PK, AI) | Unique user ID |
| name | VARCHAR(100) | Full name |
| email | VARCHAR(100) | Unique email |
| password | VARCHAR(100) | Password |
| role | VARCHAR(20) | JobSeeker / Recruiter |
| resume | TEXT | Resume text |

---

### jobs

| Column | Type | Description |
|--------|------|------------|
| id | INT (PK, AI) | Job ID |
| title | VARCHAR(200) | Job title |
| location | VARCHAR(100) | Location |
| salary | INT | Salary |
| recruiter_id | INT (FK) | References users(id) |
| skills | TEXT | Required skills |

---

### applications

| Column | Type | Description |
|--------|------|------------|
| id | INT (PK, AI) | Application ID |
| user_id | INT (FK) | References users(id) |
| job_id | INT (FK) | References jobs(id) |
| tfidf_score | DOUBLE | Match score |
| application_date | TIMESTAMP | Auto timestamp |

---

### 🔗 Foreign Keys
- jobs.recruiter_id → users.id (ON DELETE CASCADE)
- applications.user_id → users.id (ON DELETE CASCADE)
- applications.job_id → jobs.id (ON DELETE CASCADE)

---

## 🧠 TF-IDF Ranking Algorithm

- **Term Frequency (TF)**  
  = (skill occurrences in resume) / (total words in resume)

- **Inverse Document Frequency (IDF)**  
  = log((total resumes + 1) / (resumes containing skill + 1))

- **Final Score**  
  = Σ (TF × IDF)

👉 Higher score = Better match

---

## 🔮 Future Enhancements
- GUI using JavaFX or Swing
- Email notifications
- Resume upload (.pdf, .docx)
- Advanced filters (location, salary)
- Admin dashboard
- REST API using Spring Boot

---

## ⚠️ Troubleshooting

### ❌ Access denied for MySQL
- Check username/password in `DatabaseUtil.java`
- Try: `mysql -u root -p`

### ❌ Public Key Retrieval Error
- Ensure `allowPublicKeyRetrieval=true` in connection URL

### ❌ Java Version Error
- Replace `"""` text blocks with normal strings for Java 11

---

## 📌 Author
Your Name

---

## ⭐ If you like this project, give it a star!
