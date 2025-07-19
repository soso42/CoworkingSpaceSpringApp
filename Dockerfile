# Use a lightweight Java runtime image
FROM amazoncorretto:21.0.7-alpine3.21

# Set the working directory
WORKDIR /app

# Copy your JAR file into the image
COPY target/CoworkingSpaceSpringApp-1.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
