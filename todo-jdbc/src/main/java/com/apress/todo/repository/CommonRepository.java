package com.apress.todo.repository;

import com.apress.todo.client.domain.ToDo;

import java.util.Collection;

public interface CommonRepository<T> {
    ToDo save(ToDo domain);

    Iterable<ToDo> save(Collection<ToDo> domains);

    void delete(ToDo domain);

    ToDo findById(String id);

    Iterable<ToDo> findAll();
}
