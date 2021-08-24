package com.acme.demo;

import io.micronaut.configuration.picocli.PicocliRunner;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;

@Command(name = "demo", description = "demo",
    mixinStandardHelpOptions = true)
public class Application implements Runnable {
    @Inject
    TodoRepository repository;

    public void run() {
        repository.save(new Todo("Javadoc"));
        repository.save(new Todo("Application"));

        repository.findAll().forEach(System.out::println);
    }

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(Application.class, args);
    }
}