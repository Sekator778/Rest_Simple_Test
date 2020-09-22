package com.apress.todo.repository;

import com.apress.todo.client.domain.ToDo;
import org.springframework.data.repository.CrudRepository;

/**
 *
 */

public interface ToDoRepository extends CrudRepository<ToDo, String> {
}
