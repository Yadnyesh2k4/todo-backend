package com.todo.repository;

import com.todo.model.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository for Todo entity
 */
@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {
    
    List<Todo> findByUserIdOrderByCreatedAtDesc(String userId);
    
    List<Todo> findByUserIdAndIsCompletedOrderByCreatedAtDesc(String userId, boolean isCompleted);
}
