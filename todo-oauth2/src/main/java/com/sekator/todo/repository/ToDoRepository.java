package com.sekator.todo.repository;

import com.sekator.todo.domain.ToDo;
import org.springframework.data.repository.CrudRepository;

/**
 *
 */

public interface ToDoRepository extends CrudRepository<ToDo, String> {
}
