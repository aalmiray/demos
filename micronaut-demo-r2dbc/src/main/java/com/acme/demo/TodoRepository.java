package com.acme.demo;

import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.r2dbc.annotation.R2dbcRepository;
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository;

@R2dbcRepository(dialect = Dialect.ORACLE)
public interface TodoRepository extends ReactiveStreamsCrudRepository<Todo, Long> {

}