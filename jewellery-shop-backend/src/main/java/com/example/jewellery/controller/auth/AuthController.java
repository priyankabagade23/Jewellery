package com.example.jewellery.controller.auth;

import com.example.jewellery.dto.auth.LoginRequest;
import com.example.jewellery.dto.auth.RegisterRequest;
import com.example.jewellery.entity.user.User;
import com.example.jewellery.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		try {
			String token = authService.login(request.getUsername(), request.getPassword());
			return ResponseEntity.ok(Map.of(
				"token", token,
				"type", "Bearer",
				"message", "Login successful"
			));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of(
				"message", "Invalid credentials",
				"error", e.getMessage()
			));
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
		try {
			User user = authService.register(
				request.getUsername(),
				request.getEmail(),
				request.getPassword()
			);
			
			String token = authService.generateTokenForUser(user);
			
			return ResponseEntity.ok(Map.of(
				"token", token,
				"type", "Bearer",
				"userId", user.getId(),
				"username", user.getUsername(),
				"email", user.getEmail(),
				"message", "Registration successful"
			));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(Map.of(
				"message", "Registration failed",
				"error", e.getMessage()
			));
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout() {
		return ResponseEntity.ok(Map.of("message", "Logout successful"));
	}
}
