package com.acme.demo;

import io.micronaut.configuration.picocli.PicocliRunner;
import jakarta.inject.Inject;
import picocli.CommandLine.Command;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;

@Command(name = "demo", description = "demo",
    mixinStandardHelpOptions = true)
public class Application implements Runnable {
    @Inject
    TodoRepository repository;

    public void run() {
        Flux.from(repository.saveAll(Arrays.asList(new Todo("Javadoc"),
            new Todo("Application"))))
            .blockLast(Duration.ofSeconds(10));

        Flux.from(repository.findAll())
            .doOnError(Throwable::printStackTrace)
            .doOnNext(System.out::println)
            .blockLast(Duration.ofSeconds(10));
    }

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(Application.class, args);
    }
}