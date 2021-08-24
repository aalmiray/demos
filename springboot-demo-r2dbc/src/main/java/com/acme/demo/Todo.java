package com.acme.demo;

import org.springframework.data.annotation.Id;

public class Todo {
    @Id
    private Long id;
    private String description;
    private boolean done;

    public Todo() {
    }

    public Todo(String description) {
        this.description = description;
    }

    public Todo(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format(
            "Todo[id=%d, description='%s', done='%s']",
            id, description, done);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}