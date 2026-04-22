# Jenkins Setup Script for Windows
Write-Host "Setting up Jenkins with Docker support..." -ForegroundColor Green

# Start Jenkins and Docker-in-Docker
Write-Host "Starting Jenkins containers..." -ForegroundColor Yellow
docker-compose up -d

Write-Host "Waiting for Jenkins to start..." -ForegroundColor Yellow
Start-Sleep -Seconds 30

# Get initial admin password
Write-Host "Getting Jenkins initial admin password..." -ForegroundColor Yellow
$containerName = "jenkins"
$password = docker exec $containerName cat /var/jenkins_home/secrets/initialAdminPassword

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "JENKINS SETUP INSTRUCTIONS" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "1. Open http://localhost:8080 in your browser" -ForegroundColor White
Write-Host "2. Use this password to unlock Jenkins:" -ForegroundColor White
Write-Host "$password" -ForegroundColor Yellow
Write-Host "3. Install suggested plugins" -ForegroundColor White
Write-Host "4. Install additional plugins: Docker Pipeline, Docker, Docker Commons" -ForegroundColor White
Write-Host "5. Create admin user" -ForegroundColor White
Write-Host "6. Create a new pipeline job using the Jenkinsfile" -ForegroundColor White
Write-Host ""
Write-Host "Your application will be deployed to http://localhost:8081" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
