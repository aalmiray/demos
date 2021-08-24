package com.acme.demo;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Cacheable
public class Todo extends PanacheEntity {
    @Column(length = 40, unique = true)
    public String description;
    @Column
    public boolean done;

    @Override
    public String toString() {
        return String.format(
            "Todo[id=%d, description='%s', done='%s']",
            id, description, done);
    }
}

