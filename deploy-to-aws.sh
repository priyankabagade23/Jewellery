#!/bin/bash

# AWS Deployment Script for Jewellery Shop Backend

echo "Starting AWS deployment process..."

# Variables
AWS_REGION="us-east-1"
S3_BUCKET="jewellery-shop-bucket"
SQS_QUEUE="jewellery-shop-queue"
LAMBDA_FUNCTION="jewellery-shop-processor"
ECR_REPOSITORY="jewellery-shop-backend"

# Check if AWS CLI is installed
if ! command -v aws &> /dev/null; then
    echo "AWS CLI is not installed. Please install it first."
    exit 1
fi

# Check if AWS credentials are configured
if ! aws sts get-caller-identity &> /dev/null; then
    echo "AWS credentials are not configured. Please run 'aws configure' first."
    exit 1
fi

echo "AWS credentials verified successfully."

# Create S3 Bucket
echo "Creating S3 bucket: $S3_BUCKET"
aws s3 mb s3://$S3_BUCKET --region $AWS_REGION || echo "Bucket may already exist"

# Enable static website hosting
echo "Configuring S3 bucket for static website hosting..."
aws s3 website s3://$S3_BUCKET --index-document index.html --error-document error.html

# Set bucket policy for public read access
echo "Setting bucket policy..."
cat > bucket-policy.json << EOF
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "PublicReadGetObject",
            "Effect": "Allow",
            "Principal": "*",
            "Action": "s3:GetObject",
            "Resource": "arn:aws:s3:::$S3_BUCKET/*"
        }
    ]
}
EOF

aws s3api put-bucket-policy --bucket $S3_BUCKET --policy file://bucket-policy.json

# Create SQS Queue
echo "Creating SQS queue: $SQS_QUEUE"
aws sqs create-queue --queue-name $SQS_QUEUE --region $AWS_REGION || echo "Queue may already exist"

# Get Queue URL
QUEUE_URL=$(aws sqs get-queue-url --queue-name $SQS_QUEUE --region $AWS_REGION --query 'QueueUrl' --output text)
echo "SQS Queue URL: $QUEUE_URL"

# Build the Spring Boot application
echo "Building Spring Boot application..."
cd jewellery-shop-backend
./mvnw clean package -DskipTests

# Create Lambda function deployment package
echo "Creating Lambda deployment package..."
mkdir -p lambda-deployment
cp target/jewellery-shop-backend-0.0.1-SNAPSHOT.jar lambda-deployment/

# Create Lambda handler file
cat > lambda-deployment/lambda-handler.py << EOF
import json
import subprocess
import sys

def lambda_handler(event, context):
    try:
        # Process the event
        print(f"Received event: {json.dumps(event)}")
        
        # Your business logic here
        response = {
            'statusCode': 200,
            'body': json.dumps({
                'message': 'Processing completed successfully',
                'event': event
            })
        }
        
        return response
        
    except Exception as e:
        print(f"Error: {str(e)}")
        return {
            'statusCode': 500,
            'body': json.dumps({
                'error': str(e)
            })
        }
EOF

# Create Lambda ZIP package
cd lambda-deployment
zip -r lambda-function.zip .

# Create Lambda function
echo "Creating Lambda function: $LAMBDA_FUNCTION"
aws lambda create-function \
    --function-name $LAMBDA_FUNCTION \
    --runtime python3.9 \
    --role arn:aws:iam::$(aws sts get-caller-identity --query Account --output text):role/lambda-execution-role \
    --handler lambda-handler.lambda_handler \
    --zip-file fileb://lambda-function.zip \
    --region $AWS_REGION || echo "Lambda function may already exist"

# Update Lambda function code
aws lambda update-function-code \
    --function-name $LAMBDA_FUNCTION \
    --zip-file fileb://lambda-function.zip \
    --region $AWS_REGION

# Create ECR repository for Docker image
echo "Creating ECR repository: $ECR_REPOSITORY"
aws ecr create-repository --repository-name $ECR_REPOSITORY --region $AWS_REGION || echo "Repository may already exist"

# Get ECR login token
echo "Logging into ECR..."
aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $(aws sts get-caller-identity --query Account --output text).dkr.ecr.$AWS_REGION.amazonaws.com

# Build Docker image
echo "Building Docker image..."
ACCOUNT_ID=$(aws sts get-caller-identity --query Account --output text)
ECR_URI="$ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com"
IMAGE_TAG="latest"

docker build -t $ECR_REPOSITORY:$IMAGE_TAG .
docker tag $ECR_REPOSITORY:$IMAGE_TAG $ECR_URI/$ECR_REPOSITORY:$IMAGE_TAG

# Push Docker image to ECR
echo "Pushing Docker image to ECR..."
docker push $ECR_URI/$ECR_REPOSITORY:$IMAGE_TAG

echo "AWS deployment completed successfully!"
echo ""
echo "Deployment Summary:"
echo "S3 Bucket: $S3_BUCKET"
echo "SQS Queue: $SQS_QUEUE"
echo "Lambda Function: $LAMBDA_FUNCTION"
echo "ECR Repository: $ECR_REPOSITORY"
echo ""
echo "Next Steps:"
echo "1. Set up API Gateway to expose your Lambda functions"
echo "2. Configure your Spring Boot application to use AWS services"
echo "3. Set up CI/CD pipeline for automated deployments"
echo "4. Configure monitoring and logging with CloudWatch"

# Cleanup
rm -rf lambda-deployment
rm bucket-policy.json
