package com.acme.demo;

import io.micronaut.context.annotation.Executable;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {
    @Executable
    Todo findByDescription(String description);
}

