package com.example.jewellery.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;
import software.amazon.awssdk.core.SdkBytes;

import java.io.IOException;
import java.nio.ByteBuffer;

@Service
@RequiredArgsConstructor
@Slf4j
public class AwsService {

    private final S3Client s3Client;
    private final SqsClient sqsClient;
    private final LambdaClient lambdaClient;

    @Value("${aws.s3.bucket.name}")
    private String s3BucketName;

    @Value("${aws.sqs.queue.name}")
    private String sqsQueueName;

    @Value("${aws.lambda.function.name}")
    private String lambdaFunctionName;

    // S3 Operations
    public String uploadFileToS3(MultipartFile file, String keyName) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(s3BucketName)
                    .key(keyName)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            
            String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", 
                    s3BucketName, "us-east-1", keyName);
            
            log.info("File uploaded successfully to S3: {}", fileUrl);
            return fileUrl;
            
        } catch (S3Exception | IOException e) {
            log.error("Error uploading file to S3: {}", e.getMessage());
            throw new RuntimeException("Failed to upload file to S3", e);
        }
    }

    // SQS Operations
    public void sendMessageToQueue(String messageBody) {
        try {
            String queueUrl = sqsClient.getQueueUrl(request -> 
                request.queueName(sqsQueueName)).queueUrl();

            SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(messageBody)
                    .delaySeconds(5)
                    .build();

            sqsClient.sendMessage(sendMsgRequest);
            log.info("Message sent to SQS queue successfully");
            
        } catch (Exception e) {
            log.error("Error sending message to SQS: {}", e.getMessage());
            throw new RuntimeException("Failed to send message to SQS", e);
        }
    }

    // Lambda Operations
    public String invokeLambda(String payload) {
        try {
            InvokeRequest invokeRequest = InvokeRequest.builder()
                    .functionName(lambdaFunctionName)
                    .payload(SdkBytes.fromUtf8String(payload))
                    .build();

            InvokeResponse invokeResponse = lambdaClient.invoke(invokeRequest);
            
            String response = invokeResponse.payload().asUtf8String();
            log.info("Lambda function invoked successfully");
            return response;
            
        } catch (Exception e) {
            log.error("Error invoking Lambda function: {}", e.getMessage());
            throw new RuntimeException("Failed to invoke Lambda function", e);
        }
    }
}
