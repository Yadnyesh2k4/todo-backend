package com.todo.dto;

/**
 * DTO for authentication response
 */
public class AuthResponse {
    
    private String token;
    private String message;
    private UserDto user;
    
    public AuthResponse() {}
    
    public AuthResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }
    
    public AuthResponse(String token, String message, UserDto user) {
        this.token = token;
        this.message = message;
        this.user = user;
    }
    
    // Getters and Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public UserDto getUser() {
        return user;
    }
    
    public void setUser(UserDto user) {
        this.user = user;
    }
}
