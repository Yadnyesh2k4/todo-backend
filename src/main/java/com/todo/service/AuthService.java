package com.todo.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    
    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
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
        logger.debug("Attempting to register user with email: {}", request.getEmail());
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            logger.warn("Registration failed: Email {} already exists", request.getEmail());
            throw new RuntimeException("Email already registered");
        }
        
        try {
            // Create new user
            User user = new User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            
            User savedUser = userRepository.save(user);
            logger.info("User registered successfully: {}", savedUser.getEmail());
            
            // Generate token for automatic login after registration
            String token = jwtService.generateToken(savedUser.getId(), savedUser.getEmail());
            
            return new AuthResponse(token, "Registration successful", new UserDto(savedUser));
        } catch (Exception e) {
            logger.error("Error during registration for email {}: {}", request.getEmail(), e.getMessage());
            throw new RuntimeException("Registration failed due to server error: " + e.getMessage());
        }
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
