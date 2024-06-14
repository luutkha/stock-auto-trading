FROM ubuntu:latest
LABEL authors="lkha"

ENTRYPOINT ["top", "-b"]

# Use an official Tomcat runtime as a parent image
FROM tomcat:11.0.0-jdk21-openjdk

# Copy the WAR file to the webapps directory in Tomcat
COPY target/stock-trading.war /usr/local/tomcat/webapps/

# Expose port 8080
EXPOSE 8080
