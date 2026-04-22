package com.example.jewellery.service.auth;

import com.example.jewellery.entity.user.Role;
import com.example.jewellery.entity.user.User;
import com.example.jewellery.repository.user.UserRepository;
import com.example.jewellery.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public String login(String username, String password) {
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(username, password)
		);
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		return jwtUtil.generateToken(userDetails);
	}

	public User register(String username, String email, String password) {
		if (userRepository.existsByUsername(username)) {
			throw new RuntimeException("Username already exists");
		}
		
		if (userRepository.existsByEmail(email)) {
			throw new RuntimeException("Email already exists");
		}

		User user = new User(username, email, passwordEncoder.encode(password), Set.of(Role.ROLE_CUSTOMER));
		
		return userRepository.save(user);
	}

	public String generateTokenForUser(User user) {
		String role = user.getRoles().iterator().next().name();
		return jwtUtil.generateToken(user.getUsername(), role);
	}
}
