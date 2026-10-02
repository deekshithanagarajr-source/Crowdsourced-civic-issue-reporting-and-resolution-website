# Civic Issue Reporter

A simple full-stack civic issue reporting web application.

- **Backend:** Java 17 + Spring Boot 3 (REST API, Spring Data JPA)
- **Database:** MySQL 8
- **Frontend:** Vanilla HTML + CSS + JavaScript (no framework)
- **No security framework** — kept intentionally simple for learning.

> ⚠️ This project stores passwords in plain text and has no auth tokens — it is for learning/demo only. Do not deploy to production as-is.

---

## 📁 Folder structure

```
civic-issue-reporter/
├── backend/
│   ├── pom.xml
│   └── src/main/
│       ├── java/com/civic/reporter/
│       │   ├── CivicReporterApplication.java
│       │   ├── controller/        (AuthController, IssueController)
│       │   ├── service/           (UserService, IssueService)
│       │   ├── repository/        (UserRepository, IssueRepository)
│       │   ├── model/             (User, Issue)
│       │   ├── dto/               (Request/Response DTOs)
│       │   └── exception/         (ApiException, GlobalExceptionHandler)
│       └── resources/
│           └── application.properties
├── frontend/
│   ├── index.html        (auto-redirect)
│   ├── login.html
│   ├── register.html
│   ├── dashboard.html
│   ├── create-issue.html
│   ├── view-issues.html
│   ├── styles.css
│   └── app.js
├── db/
│   └── schema.sql
└── README.md
```

---

## 🗄️ MySQL schema (`db/schema.sql`)

```sql
CREATE DATABASE IF NOT EXISTS civic_db CHARACTER SET utf8mb4;
USE civic_db;

CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50)  NOT NULL UNIQUE,
  email    VARCHAR(120) NOT NULL UNIQUE,
  password VARCHAR(120) NOT NULL
);

CREATE TABLE issues (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  title       VARCHAR(150)  NOT NULL,
  description VARCHAR(2000) NOT NULL,
  status      VARCHAR(30)   NOT NULL DEFAULT 'Pending',
  user_id     BIGINT        NOT NULL,
  created_at  DATETIME      NOT NULL,
  CONSTRAINT fk_issues_user FOREIGN KEY (user_id) REFERENCES users(id)
);
```

> Spring Boot will auto-create these tables on first run via `spring.jpa.hibernate.ddl-auto=update`. Running the SQL manually is optional.

---

## 🔌 REST API endpoints

| Method | Endpoint               | Body                                              | Description              |
| ------ | ---------------------- | ------------------------------------------------- | ------------------------ |
| POST   | `/register`            | `{ username, email, password }`                   | Register a new user      |
| POST   | `/login`               | `{ username, password }`                          | Login with credentials   |
| POST   | `/issues/create`       | `{ userId, title, description }`                  | Create a new issue       |
| GET    | `/issues/all`          | —                                                 | List all issues          |
| GET    | `/issues/count`        | —                                                 | Count of all issues      |
| PUT    | `/issues/update/{id}`  | `{ status: "Pending"\|"In Progress"\|"Resolved" }`| Update an issue's status |

All endpoints have `@CrossOrigin(origins = "*")` enabled.

---

## ▶️ How to run

### 1. Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8 running locally
- Any browser
- (Optional) VS Code "Live Server" extension or Python's `http.server`

### 2. Configure the database
Edit `backend/src/main/resources/application.properties` if your MySQL credentials differ:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/civic_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=root
```

### 3. Start the backend
```bash
cd backend
mvn spring-boot:run
```
Backend will be available at **http://localhost:8080**.

### 4. Open the frontend
The simplest way:
```bash
cd frontend
# Python 3
python -m http.server 5500
```
Then open **http://localhost:5500/login.html** (or `index.html`).

You can also just double-click any `.html` file — CORS is enabled on the backend.

### 5. Use the app
1. **Register** a new account.
2. You'll be auto-logged in and redirected to the **Dashboard**.
3. Click **Create Issue** to submit one (status defaults to *Pending*).
4. Click **View Issues** to see the list and change status using the dropdown
   (*Pending → In Progress → Resolved*).

---

## 🧪 Quick API smoke test

```bash
# Register
curl -X POST http://localhost:8080/register \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","email":"alice@example.com","password":"secret123"}'

# Login
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"username":"alice","password":"secret123"}'

# Create issue (use the userId returned from login/register)
curl -X POST http://localhost:8080/issues/create \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"title":"Pothole","description":"Large pothole on Main St"}'

# List issues
curl http://localhost:8080/issues/all

# Update status
curl -X PUT http://localhost:8080/issues/update/1 \
  -H "Content-Type: application/json" \
  -d '{"status":"In Progress"}'
```
