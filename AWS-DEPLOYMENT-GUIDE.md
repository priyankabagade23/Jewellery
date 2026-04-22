# AWS Deployment Guide for Jewellery Shop Backend

## Prerequisites

1. **AWS Account**: Create an account at https://aws.amazon.com/
2. **AWS CLI**: Install and configure AWS CLI
3. **Docker**: Install Docker for containerization
4. **Java 17**: Ensure Java 17 is installed
5. **Maven**: Ensure Maven is installed

## Step 1: AWS Account Setup

### Create AWS Account
1. Go to https://aws.amazon.com/
2. Click "Create an AWS Account"
3. Fill in your email and password
4. Choose account type (Personal/Business)
5. Enter contact information
6. Add payment method (required for verification)
7. Verify your identity (phone/SMS)
8. Select support plan (Basic is free)

### Configure AWS CLI
```bash
# Install AWS CLI (Windows)
# Download from: https://aws.amazon.com/cli/

# Configure AWS credentials
aws configure
```

You'll need:
- Access Key ID
- Secret Access Key
- Default region: `us-east-1`
- Default output format: `json`

## Step 2: Create IAM User and Permissions

### Create IAM User
1. Go to AWS Console → IAM → Users
2. Click "Create user"
3. Enter username: `jewellery-shop-admin`
4. Select "Access key - Programmatic access"
5. Attach policies:
   - `AmazonS3FullAccess`
   - `AmazonSQSFullAccess`
   - `AWSLambda_FullAccess`
   - `AmazonEC2ContainerRegistryFullAccess`
   - `IAMFullAccess` (for setup)

### Save Credentials
Save the Access Key ID and Secret Access Key securely.

## Step 3: Deploy Infrastructure

### Option 1: Automated Deployment
```bash
# Make the deployment script executable (Linux/Mac)
chmod +x deploy-to-aws.sh

# Run the deployment script
./deploy-to-aws.sh
```

### Option 2: Manual Setup

#### Create S3 Bucket
```bash
aws s3 mb s3://jewellery-shop-bucket --region us-east-1
aws s3 website s3://jewellery-shop-bucket --index-document index.html
```

#### Create SQS Queue
```bash
aws sqs create-queue --queue-name jewellery-shop-queue --region us-east-1
```

#### Create Lambda Function
```bash
# Create Lambda execution role
aws iam create-role --role-name lambda-execution-role --assume-role-policy-document file://trust-policy.json

# Attach Lambda basic execution policy
aws iam attach-role-policy --role-name lambda-execution-role --policy-arn arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole
```

#### Create ECR Repository
```bash
aws ecr create-repository --repository-name jewellery-shop-backend --region us-east-1
```

## Step 4: Configure Application

### Set Environment Variables
```bash
export AWS_ACCESS_KEY_ID=your_access_key
export AWS_SECRET_ACCESS_KEY=your_secret_key
export DB_USERNAME=your_db_username
export DB_PASSWORD=your_db_password
```

### Update Application Properties
Edit `src/main/resources/application-aws.properties` with your specific values.

## Step 5: Build and Deploy

### Build Application
```bash
cd jewellery-shop-backend
./mvnw clean package -DskipTests
```

### Build Docker Image
```bash
# Login to ECR
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin $(aws sts get-caller-identity --query Account --output text).dkr.ecr.us-east-1.amazonaws.com

# Build and tag image
docker build -t jewellery-shop-backend:latest .
docker tag jewellery-shop-backend:latest $(aws sts get-caller-identity --query Account --output text).dkr.ecr.us-east-1.amazonaws.com/jewellery-shop-backend:latest

# Push to ECR
docker push $(aws sts get-caller-identity --query Account --output text).dkr.ecr.us-east-1.amazonaws.com/jewellery-shop-backend:latest
```

## Step 6: Test AWS Integration

### Test S3 Upload
```bash
curl -X POST http://localhost:8080/api/aws/upload \
  -F "file=@test-image.jpg" \
  -F "keyName=products/test-image.jpg"
```

### Test SQS Message
```bash
curl -X POST http://localhost:8080/api/aws/message \
  -H "Content-Type: application/json" \
  -d '{"message": "Test message from jewellery shop"}'
```

### Test Lambda Invocation
```bash
curl -X POST http://localhost:8080/api/aws/invoke \
  -H "Content-Type: application/json" \
  -d '{"payload": "{\"action\": \"process_order\", \"orderId\": \"12345\"}"}'
```

## Step 7: Production Deployment

### Using ECS (Elastic Container Service)
1. Create ECS Cluster
2. Define Task Definition
3. Create Service
4. Configure Load Balancer

### Using EC2 Instance
1. Launch EC2 instance
2. Install Docker
3. Pull image from ECR
4. Run container

## Cost Optimization

### Free Tier Benefits (12 months)
- **S3**: 5GB storage, 20,000 requests/month
- **Lambda**: 1 million requests/month, 400,000 GB-seconds/month
- **SQS**: 1 million messages/month
- **EC2**: 750 hours/month (t2.micro)

### Cost Saving Tips
1. Use S3 Standard for frequently accessed data
2. Use S3 Infrequent Access for backups
3. Enable Lambda reserved concurrency
4. Monitor CloudWatch metrics
5. Set up billing alerts

## Monitoring and Logging

### CloudWatch
```bash
# View Lambda logs
aws logs tail /aws/lambda/jewellery-shop-processor --follow

# View CloudWatch metrics
aws cloudwatch get-metric-statistics \
  --namespace AWS/SQS \
  --metric-name NumberOfMessagesReceived \
  --dimensions Name=QueueName,Value=jewellery-shop-queue \
  --start-time 2024-01-01T00:00:00Z \
  --end-time 2024-01-02T00:00:00Z \
  --period 3600 \
  --statistics Sum
```

## Security Best Practices

1. **IAM Roles**: Use least privilege principle
2. **VPC**: Deploy resources in private subnets
3. **Encryption**: Enable encryption at rest and in transit
4. **Security Groups**: Restrict access to necessary ports only
5. **Monitoring**: Enable CloudTrail for audit logging

## Troubleshooting

### Common Issues
1. **Access Denied**: Check IAM permissions
2. **Connection Timeout**: Verify security group settings
3. **Lambda Timeout**: Increase function timeout
4. **S3 CORS**: Configure bucket CORS policy

### Debug Commands
```bash
# Check AWS CLI configuration
aws configure list

# Test S3 access
aws s3 ls

# Test SQS access
aws sqs list-queues

# Test Lambda access
aws lambda list-functions
```

## Next Steps

1. **API Gateway**: Set up API Gateway for HTTP endpoints
2. **CI/CD Pipeline**: Configure GitHub Actions or AWS CodePipeline
3. **Database**: Set up RDS for production database
4. **CDN**: Configure CloudFront for static assets
5. **Backup**: Implement automated backup strategy

## Support

- AWS Documentation: https://docs.aws.amazon.com/
- AWS Support Center: https://console.aws.amazon.com/support/
- Community Forums: https://forums.aws.amazon.com/
