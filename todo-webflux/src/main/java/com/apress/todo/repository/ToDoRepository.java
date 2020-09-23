package com.apress.todo.repository;

import com.apress.todo.domain.ToDo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

public class ToDoRepository {

    private final Flux<ToDo> toDoFlux = Flux.fromIterable(Arrays.asList(
            new ToDo("Learn java"),
            new ToDo("Workout in spring ....", true),
            new ToDo("Make todo app"),
            new ToDo("Clean code read", true)));


    public Mono<ToDo> findById(String id){
        return Mono.from(toDoFlux.filter( todo -> todo.getId().equals(id)));
    }

    public Flux<ToDo> findAll(){
        return toDoFlux;
    }
}
