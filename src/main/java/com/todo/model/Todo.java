package com.todo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

/**
 * Todo entity for MongoDB
 */
@Document(collection = "todos")
public class Todo {
    
    @Id
    private String id;
    
    private String title;
    
    private String description;
    
    private boolean isCompleted;
    
    private String userId;
    
    private LocalDateTime createdAt;
    
    // Default constructor
    public Todo() {
        this.isCompleted = false;
        this.createdAt = LocalDateTime.now();
    }
    
    // Constructor with fields
    public Todo(String title, String description, String userId) {
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.isCompleted = false;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
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
    
    public boolean isCompleted() {
        return isCompleted;
    }
    
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
