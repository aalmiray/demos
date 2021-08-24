package com.acme.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Todo {
    @Id
    @GeneratedValue
    private Long id;
    private String description;
    private boolean done;

    public Todo(String description) {
        this(description, false);
    }

    public Todo(String description, boolean done) {
        this.description = description;
        this.done = done;
    }

    public Todo() {
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

    @Override
    public String toString() {
        return String.format(
            "Todo[id=%d, description='%s', done='%s']",
            id, description, done);
    }
}

