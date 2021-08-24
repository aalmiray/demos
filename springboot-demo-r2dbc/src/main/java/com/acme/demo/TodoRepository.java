package com.acme.demo;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface TodoRepository extends ReactiveCrudRepository<Todo, Long> {
    @Query("SELECT * FROM todo WHERE description = :description")
    Flux<Todo> findByDescription(String description);
}