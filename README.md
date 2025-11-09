# Java Spring Boot Swagger Template (Enterprise Edition)

Welcome to the **Java Spring Boot Swagger Template** — an enterprise‑ready starter kit designed for developers who are new to Java, Spring Boot, REST APIs, or backend development in general.  
This template mirrors the simplicity and developer experience of the Go template you used previously, but is implemented entirely in **Java + Spring Boot**.

The goal of this project is simple:

✅ Give developers a **ready-to-run**, production-grade Java service  
✅ Include **Swagger API documentation** out of the box  
✅ Provide a **PostgreSQL or in-memory fallback** system  
✅ Allow developers to extend this template to build full enterprise microservices  
✅ Allow your CI/CD pipeline to build & deploy workloads **without developers modifying the Dockerfile**  

---

# 🚀 What This Template Provides

This template includes:

### ✅ Fully working REST API
- `GET /api/v1/users`
- `GET /api/v1/users/{id}`
- `POST /api/v1/users`

### ✅ Automatic Swagger UI
- Available at:  
  **http://localhost:8081/swagger-ui.html**

### ✅ Dual Storage Mode (Enterprise‑Friendly)
1. **PostgreSQL Mode**  
   If environment variables are provided (`db_host`, `db_user`, `db_pass`, `db_name`, `db_port`), the service:  
   - Connects to Postgres  
   - Runs a lightweight auto-migration (`CREATE TABLE IF NOT EXISTS users`)  
   - Stores all users in a real database  

2. **In‑Memory Mode (Default)**  
   If DB configuration is missing or invalid, the service **falls back automatically** to fast in-memory storage.  
   - Great for local development  
   - Nothing to install  
   - Very fast  
   - Data resets on restart

### ✅ Enterprise-Ready Dockerfile
A production-ready container image is built using:  
- Multi-stage Gradle build  
- Java 21 runtime  
- Zero modification needed by developers  
- Your pipeline will use this Dockerfile as-is  

✅ **Please do NOT modify the Dockerfile.**  
This ensures your CI/CD pipeline can build and deploy consistently.

---

# 📦 Project Structure Explained (Beginner-Friendly)

```
java-spring-swagger-template
├── Dockerfile                   → Container build file (DO NOT MODIFY)
├── build.gradle                 → Dependency & build configuration
├── settings.gradle              → Project name
├── src
│   ├── main
│   │   ├── java/com/example/template
│   │   │   ├── DemoApplication.java       → Main entry point
│   │   │   ├── config
│   │   │   │   └── AppConfig.java         → Chooses DB or in-memory mode
│   │   │   ├── controller
│   │   │   │   ├── UserController.java    → User API endpoints
│   │   │   │   └── HealthController.java  → /health endpoint
│   │   │   ├── model
│   │   │   │   └── User.java              → Entity model
│   │   │   ├── dto
│   │   │   │   └── CreateUserRequest.java → Request payload
│   │   │   └── service
│   │   │       ├── UserService.java       → Abstraction
│   │   │       └── impl
│   │   │           ├── InMemoryUserService.java
│   │   │           └── JdbcUserService.java
│   └── resources
│       └── application.properties → Basic Spring config
```

If you're new to Java, here's what matters:

### ✅ **Spring Boot**  
A framework that removes complexity and lets you build production Java services very quickly.

### ✅ **Gradle**  
A build tool (like Maven but easier) that:
- downloads dependencies  
- compiles code  
- runs tests  
- builds JAR files  
- builds Docker images in this template

### ✅ **Swagger (SpringDoc)**  
Automatically generates API docs & UI.

---

# 🛠 Tools You Must Install Before Running

Install the following:

## ✅ Java 21
Check:
```bash
java -version
```
If missing:
```bash
brew install openjdk@21
```

## ✅ Gradle
```bash
brew install gradle
```

## ✅ Docker (for container builds)
Download Docker Desktop from:  
https://www.docker.com/products/docker-desktop/

## ✅ Optional: PostgreSQL (Local)
You can run Postgres using Docker:
```bash
docker run --name pg-users   -e POSTGRES_USER=postgres   -e POSTGRES_PASSWORD=postgres   -e POSTGRES_DB=usersdb   -p 5433:5432   -v pgdata:/var/lib/postgresql/data   -d postgres:15
```

---

# ▶️ How to Run Locally

## ✅ Option 1 — Run in In‑Memory Mode (No Database Needed)
This is the default.

```bash
gradle bootRun
```

Open:
- Swagger → http://localhost:8081/swagger-ui.html  
- GET users → http://localhost:8081/api/v1/users  

---

## ✅ Option 2 — Run with PostgreSQL (Local or Remote)

Set the environment variables:

```bash
export db_host=localhost
export db_user=postgres
export db_pass=postgres
export db_name=usersdb
export db_port=5433
```

Run the app:

```bash
gradle --stop   # refresh daemon env
gradle bootRun
```

Watch for this log:
```
Connected to Postgres at localhost:5433 / usersdb
UserService -> Using JDBC backend
```

Now your API persists data in a real DB.

---

# 🐳 Running Through Docker

Build the image:
```bash
docker build -t java-template-app .
```

Run (in-memory mode):
```bash
docker run -d -p 8081:8081 --name java-template java-template-app
```

Run (with Postgres):
```bash
docker run -d -p 8081:8081   --name java-template   -e db_host=host.docker.internal   -e db_user=postgres   -e db_pass=postgres   -e db_name=usersdb   -e db_port=5433   java-template-app
```

---

# 🧱 How To Extend This Template (Enterprise Guidance)

You may add:

✅ New API routes  
✅ New controllers  
✅ New service classes  
✅ New models  
✅ Business logic  
✅ Database queries  
✅ Integrations (Kafka, Redis, etc.)  
✅ Authentication / Authorization  
✅ CI/CD workflows (your own repo)  

You **should NOT modify**:

❌ `Dockerfile`  
❌ `build.gradle` (except adding dependencies)  
❌ `DemoApplication.java` (unless necessary)  

### ✅ Why?
Your enterprise CI/CD pipeline expects the structure as-is.  
If developers modify the Dockerfile, the workload may fail to build or deploy.

---

# 🛠 Where to Contribute Code

Developers should add code here:

```
src/main/java/com/example/template/
```

Recommended developer workflow:

1. Clone this template  
2. Create a new feature branch  
3. Add routes/models/services  
4. Test locally  
5. Push to your repo  
6. Your pipeline picks up the code and builds the Docker image  
7. Deploy to Kubernetes/Cloud environment  

---

# ✅ Testing Your API

### Create a user:
```bash
curl -X POST http://localhost:8081/api/v1/users   -H "Content-Type: application/json"   -d '{"name":"Ada Lovelace","email":"ada@example.com"}'
```

### List users:
```bash
curl http://localhost:8081/api/v1/users
```

---

# ✅ Troubleshooting (Beginner Friendly)

### ❌ App uses in-memory mode, not Postgres  
Fix: Ensure environment variables are exported **before** running Gradle.

### ❌ Postgres container restarts and data resets  
Fix: Always use a Docker volume:
```
-v pgdata:/var/lib/postgresql/data
```

### ❌ Swagger not loading  
Fix: Ensure app is running on port 8081:
```
gradle bootRun
```

### ❌ “Connection refused” when using Docker  
Use:
```
db_host=host.docker.internal
```

---

# ✅ Final Notes for Users

- The folder structure is intentionally simple.
- Developers must **not** modify the Dockerfile.
- Your Sandbox pipelines will build and deploy this service.
- This project is designed to scale with you:
  - Add gRPC, Kafka, Redis, S3, etc.
  - Plug into your infrastructure.

