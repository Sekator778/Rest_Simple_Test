package com.apress.reactor.example.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ToDo {

    private String id;
    private String description;
    private LocalDateTime created;
    private LocalDateTime modified;
    private boolean completed;

    public ToDo(String description, boolean completed) {
        this.description = description;
        this.completed = completed;
    }

    public ToDo(String s) {
        this.description = s;
    }
}