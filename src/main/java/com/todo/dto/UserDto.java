package com.todo.dto;

import com.todo.model.User;

/**
 * DTO for user response (excludes password)
 */
public class UserDto {
    
    private String id;
    private String name;
    private String email;
    private String profilePic;
    private String createdAt;
    
    public UserDto() {}
    
    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.profilePic = user.getProfilePic();
        this.createdAt = user.getCreatedAt() != null ? user.getCreatedAt().toString() : null;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getProfilePic() {
        return profilePic;
    }
    
    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
    
    public String getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
