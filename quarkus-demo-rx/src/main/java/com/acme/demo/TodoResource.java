package com.acme.demo;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import org.jboss.resteasy.reactive.RestPath;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.CREATED;

@Path("/todos")
@ApplicationScoped
public class TodoResource {
    @GET
    public Uni<List<Todo>> get() {
        return Todo.listAll();
    }

    @GET
    @Path("{id}")
    public Uni<Todo> getSingle(@RestPath Long id) {
        return Todo.findById(id);
    }

    @POST
    public Uni<Response> create(Todo todo) {
        if (todo == null || todo.id != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }

        return Panache.withTransaction(todo::persist)
            .replaceWith(Response.ok(todo).status(CREATED)::build);
    }
}