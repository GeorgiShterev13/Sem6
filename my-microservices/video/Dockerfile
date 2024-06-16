# Use the official OpenJDK 17 image as a parent image
FROM openjdk:17-jdk-slim as builder
LABEL authors="georgishterev"

# Set the working directory
WORKDIR /app

# Copy the shared Gradle wrapper and settings
COPY gradlew .
COPY gradlew.bat .
COPY gradle gradle
COPY settings.gradle .

# Copy the project-specific build script and source code
COPY video/build.gradle .
COPY video/src src

# Make the Gradle wrapper executable
RUN chmod +x gradlew

# Build the application
RUN ./gradlew build

# Use the official OpenJDK 17 runtime image as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /app/build/libs/*.jar /app/app.jar

# Expose port 8080
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]
