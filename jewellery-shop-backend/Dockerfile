# Use official OpenJDK runtime as a parent image
FROM eclipse-temurin:21-jdk-jammy

# Install git
RUN apt-get update && apt-get install -y git && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Clone repository from GitHub
RUN git clone https://github.com/priyankabagade23/Jewellery.git /tmp/jewellery
RUN cp -r /tmp/jewellery/jewellery-shop-backend/* /app/
RUN rm -rf /tmp/jewellery

# Download dependencies
RUN ./mvnw dependency:go-offline -B

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose port
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/jewellery-shop-backend-0.0.1-SNAPSHOT.jar"]
