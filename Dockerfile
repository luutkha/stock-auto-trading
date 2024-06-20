# First stage: Build the WAR file
FROM maven:3.8.4-openjdk-17-slim AS builder

# Set the working directory
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Package the application to create the WAR file
RUN mvn clean install -DskipTests

# Second stage: Set up Tomcat and deploy the WAR file
FROM tomcat:11.0.0-jdk21-openjdk

# Copy the WAR file from the builder stage to the Tomcat webapps directory
COPY --from=builder /app/target/stock-trading.war /usr/local/tomcat/webapps/

# Expose port 8080
EXPOSE 8080

# Define entrypoint
ENTRYPOINT ["catalina.sh", "run"]
