#!/bin/bash

echo "Setting up Jenkins with Docker support..."

# Start Jenkins and Docker-in-Docker
docker-compose up -d

echo "Waiting for Jenkins to start..."
sleep 30

# Get initial admin password
echo "Getting Jenkins initial admin password..."
docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword

echo ""
echo "Jenkins Setup Instructions:"
echo "1. Open http://localhost:8080 in your browser"
echo "2. Use the password above to unlock Jenkins"
echo "3. Install suggested plugins"
echo "4. Install additional plugins: Docker Pipeline, Docker, Docker Commons"
echo "5. Create admin user"
echo "6. Create a new pipeline job using the Jenkinsfile"
echo ""
echo "Your application will be deployed to http://localhost:8081"
