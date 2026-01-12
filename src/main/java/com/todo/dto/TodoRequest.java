package com.todo.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for todo create/update request
 */
public class TodoRequest {
    
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    private Boolean isCompleted;
    
    // Getters and Setters
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Boolean getIsCompleted() {
        return isCompleted;
    }
    
    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
