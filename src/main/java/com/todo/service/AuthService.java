package com.todo.service;

import com.todo.dto.LoginRequest;
import com.todo.dto.RegisterRequest;
import com.todo.dto.AuthResponse;
import com.todo.dto.UserDto;
import com.todo.model.User;
import com.todo.repository.UserRepository;
import com.todo.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service for user authentication operations
 */
@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    
    public AuthService(UserRepository userRepository, 
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    
    /**
     * Register a new user
     */
    public AuthResponse register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        
        // Create new user
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        
        User savedUser = userRepository.save(user);
        
        return new AuthResponse(null, "Registration successful", new UserDto(savedUser));
    }
    
    /**
     * Login user and return JWT token
     */
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        
        String token = jwtService.generateToken(user.getId(), user.getEmail());
        
        return new AuthResponse(token, "Login successful", new UserDto(user));
    }
    
    /**
     * Get user by ID
     */
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDto(user);
    }
}
