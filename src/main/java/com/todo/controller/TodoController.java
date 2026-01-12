package com.todo.controller;

import com.todo.dto.TodoRequest;
import com.todo.model.Todo;
import com.todo.model.User;
import com.todo.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * REST Controller for todo endpoints
 */
@RestController
@RequestMapping("/api/todos")
public class TodoController {
    
    private final TodoService todoService;
    
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }
    
    /**
     * Get all todos for the authenticated user
     * GET /api/todos
     */
    @GetMapping
    public ResponseEntity<?> getAllTodos(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Unauthorized"));
        }
        
        List<Todo> todos = todoService.getTodosByUserId(user.getId());
        return ResponseEntity.ok(todos);
    }
    
    /**
     * Get a single todo by ID
     * GET /api/todos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable String id,
                                         @AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Unauthorized"));
        }
        
        try {
            Todo todo = todoService.getTodoById(id);
            
            // Verify ownership
            if (!todo.getUserId().equals(user.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Map.of("message", "Access denied"));
            }
            
            return ResponseEntity.ok(todo);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Create a new todo
     * POST /api/todos
     */
    @PostMapping
    public ResponseEntity<?> createTodo(@Valid @RequestBody TodoRequest request,
                                        @AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Unauthorized"));
        }
        
        Todo todo = todoService.createTodo(request, user.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(todo);
    }
    
    /**
     * Update a todo
     * PUT /api/todos/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTodo(@PathVariable String id,
                                        @RequestBody TodoRequest request,
                                        @AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Unauthorized"));
        }
        
        try {
            Todo todo = todoService.updateTodo(id, request, user.getId());
            return ResponseEntity.ok(todo);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }
    
    /**
     * Delete a todo
     * DELETE /api/todos/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable String id,
                                        @AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Unauthorized"));
        }
        
        try {
            todoService.deleteTodo(id, user.getId());
            return ResponseEntity.ok(Map.of("message", "Todo deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
