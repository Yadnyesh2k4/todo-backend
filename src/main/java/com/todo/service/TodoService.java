package com.todo.service;

import com.todo.dto.TodoRequest;
import com.todo.model.Todo;
import com.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Service for todo operations
 */
@Service
public class TodoService {
    
    private final TodoRepository todoRepository;
    
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }
    
    /**
     * Get all todos for a user
     */
    public List<Todo> getTodosByUserId(String userId) {
        return todoRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
    
    /**
     * Get todo by ID
     */
    public Todo getTodoById(String id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
    }
    
    /**
     * Create a new todo
     */
    public Todo createTodo(TodoRequest request, String userId) {
        Todo todo = new Todo();
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription() != null ? request.getDescription() : "");
        todo.setUserId(userId);
        todo.setCompleted(false);
        
        return todoRepository.save(todo);
    }
    
    /**
     * Update an existing todo
     */
    public Todo updateTodo(String id, TodoRequest request, String userId) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
        
        // Verify ownership
        if (!todo.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        
        if (request.getTitle() != null) {
            todo.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            todo.setDescription(request.getDescription());
        }
        if (request.getIsCompleted() != null) {
            todo.setCompleted(request.getIsCompleted());
        }
        
        return todoRepository.save(todo);
    }
    
    /**
     * Delete a todo
     */
    public void deleteTodo(String id, String userId) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));
        
        // Verify ownership
        if (!todo.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }
        
        todoRepository.delete(todo);
    }
}
