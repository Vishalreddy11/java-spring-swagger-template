# ---- Stage 1: Build the app ----
FROM gradle:8.5-jdk21 AS builder
WORKDIR /app

# Copy project files
COPY build.gradle settings.gradle ./
COPY src ./src

# Build the application (creates jar inside /app/build/libs)
RUN gradle clean bootJar --no-daemon

# ---- Stage 2: Run the app ----
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy the built jar from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose application port
EXPOSE 8081

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
