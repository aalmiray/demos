package com.acme.demo;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Todo extends PanacheEntity {
    public String description;
    public boolean done;

    public Todo(String description) {
        this(description, false);
    }

    public Todo(String description, boolean done) {
        this.description = description;
        this.done = done;
    }

    public Todo() {
    }

    @Override
    public String toString() {
        return String.format(
            "Todo[id=%d, description='%s', done='%s']",
            id, description, done);
    }
}

