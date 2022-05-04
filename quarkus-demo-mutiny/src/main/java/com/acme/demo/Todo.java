package com.acme.demo;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Cacheable
public class Todo extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
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

