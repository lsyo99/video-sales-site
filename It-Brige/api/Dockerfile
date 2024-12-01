# Step 1: Base image
FROM openjdk:21-jdk-slim

# Step 2: Set working directory
WORKDIR /app

# Step 3: Copy the built JAR file

COPY api/build/libs/api-1.0-SNAPSHOT.jar app.jar
# Step 4: Expose port
EXPOSE 8080

# Step 5: Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
