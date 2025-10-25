# java-spring-swagger-template

## 1. What this project is
This repo is a **Java REST API template** that comes with:
- A basic Spring Boot service (Spring Boot = Java web framework)
- Automatically generated API docs using Swagger / OpenAPI
- A runnable local server
- A production-style Docker image

You can use this as a starting point to build real backend services.

This template gives you:
- A health check / sample endpoint
- Built-in Swagger UI so you can test APIs in the browser
- Gradle wrapper so you don’t have to install Gradle manually
- A Dockerfile that builds a runnable container image

> Goal: You clone, you run, you’re live on `http://localhost:<PORT>` with docs.

## 2. Tech stack (in plain English)

### Java
Java is the programming language this service is written in.

### Spring Boot
Spring Boot is a framework for building APIs in Java. It lets you define routes like `/users`, `/login`, etc.

### Swagger / OpenAPI
Swagger/OpenAPI describes your API (endpoints, inputs, responses). Swagger UI allows you to test APIs easily in the browser.

### Gradle
Gradle compiles the code, downloads dependencies, and builds the `.jar` file. The Gradle wrapper lets you run builds without installing Gradle globally.

### Docker
A pre-built Dockerfile is included to containerize your application. **Do not modify the Dockerfile unless you know what you’re doing.**

## 3. Project layout
```
java-spring-swagger-template/
├─ src/
│  ├─ main/java/...        <-- Your Java code
│  ├─ main/resources/...   <-- Config files
│  └─ test/...             <-- Tests
├─ build.gradle
├─ settings.gradle
├─ gradlew / gradlew.bat
├─ gradle/wrapper/
├─ Dockerfile
└─ .gitignore
```

## 4. Prerequisites
- Java 21+ (JDK)
- Terminal or command prompt

No need to install Gradle or Maven manually.

## 5. Run locally (without Docker)

### Step 1: Clone
```bash
git clone https://github.com/Vishalreddy11/java-spring-swagger-template.git
cd java-spring-swagger-template
```

### Step 2: Build
```bash
./gradlew clean build   # Mac/Linux
gradlew.bat clean build # Windows
```

### Step 3: Run
```bash
java -jar build/libs/*.jar
```

Visit `http://localhost:8081/swagger-ui.html` to view Swagger UI.

## 6. Run in dev mode
```bash
./gradlew bootRun
```

If you get a JDK error, install Java 21+.

## 7. Run with Docker

### Build
```bash
docker build -t java-template-app .
```

### Run
```bash
docker run -d -p 8081:8081 --name java-template java-template-app
```

View logs:
```bash
docker logs -f java-template
```

Access Swagger UI at `http://localhost:8081/swagger-ui.html`.

Stop container:
```bash
docker stop java-template
```

## 8. Database (optional)
If using Postgres:
```bash
docker run --name my-postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=usersdb -p 5433:5432 -d postgres:15
```
Then run the app with:
```bash
-e DBHOST=host.docker.internal -e DBUSER=postgres -e DBPASS=postgres -e DBNAME=usersdb -e DBPORT=5433
```

## 9. Common issues
- **Port in use:** change host port with `-p 9090:8081`
- **JDK mismatch:** install Java 21+
- **Swagger not showing:** try `/swagger-ui.html` or `/swagger-ui/index.html`

## 10. Summary
- Clone → Build → Run → Swagger works.
- Gradle compiles, Spring Boot serves, Docker containers it.
- Don’t modify Dockerfile unless necessary.
