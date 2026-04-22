package com.example.jewellery.controller;

import com.example.jewellery.service.AwsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/aws")
@RequiredArgsConstructor
@Slf4j
public class AwsController {

    private final AwsService awsService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "keyName", required = false) String keyName) {
        
        try {
            if (keyName == null || keyName.isEmpty()) {
                keyName = "uploads/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            }

            String fileUrl = awsService.uploadFileToS3(file, keyName);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "File uploaded successfully");
            response.put("fileUrl", fileUrl);
            response.put("keyName", keyName);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error uploading file: {}", e.getMessage());
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Failed to upload file: " + e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PostMapping("/message")
    public ResponseEntity<Map<String, String>> sendMessage(@RequestBody Map<String, String> request) {
        try {
            String message = request.get("message");
            if (message == null || message.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Message cannot be empty"));
            }

            awsService.sendMessageToQueue(message);
            
            return ResponseEntity.ok(Map.of(
                "message", "Message sent to queue successfully",
                "queueMessage", message
            ));
            
        } catch (Exception e) {
            log.error("Error sending message: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to send message: " + e.getMessage()));
        }
    }

    @PostMapping("/invoke")
    public ResponseEntity<Map<String, String>> invokeLambda(@RequestBody Map<String, String> request) {
        try {
            String payload = request.get("payload");
            if (payload == null || payload.trim().isEmpty()) {
                payload = "{}"; // Default empty JSON
            }

            String response = awsService.invokeLambda(payload);
            
            return ResponseEntity.ok(Map.of(
                "message", "Lambda function invoked successfully",
                "response", response
            ));
            
        } catch (Exception e) {
            log.error("Error invoking Lambda: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error", "Failed to invoke Lambda: " + e.getMessage()));
        }
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> getAwsStatus() {
        return ResponseEntity.ok(Map.of(
            "status", "AWS services are configured",
            "services", "S3, SQS, Lambda are ready"
        ));
    }
}
