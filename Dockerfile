# Stage 1: Build with Maven and JDK 21
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy Maven wrapper files and build metadata first for caching
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Make Maven wrapper executable and download dependencies
RUN chmod +x ./mvnw && ./mvnw dependency:go-offline

# Copy source code and build
COPY src src
RUN ./mvnw clean package -DskipTests

# Stage 2: Runtime with JRE 21
FROM eclipse-temurin:21-jre
WORKDIR /

# Optional: expose default Spring Boot port
EXPOSE 8080
VOLUME /tmp

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app.jar"]
