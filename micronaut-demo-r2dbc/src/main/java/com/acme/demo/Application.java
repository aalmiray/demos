package com.acme.demo;

import io.micronaut.configuration.picocli.PicocliRunner;
import io.reactivex.Single;
import jakarta.inject.Inject;
import org.flywaydb.core.Flyway;
import picocli.CommandLine.Command;
import reactor.core.publisher.Flux;

@Command(name = "demo", description = "demo",
    mixinStandardHelpOptions = true)
public class Application implements Runnable {
    @Inject
    TodoRepository repository;

    public void run() {
        // force migration
        Flyway.configure()
            .baselineOnMigrate(true)
            .dataSource("jdbc:oracle:thin:@localhost:1521/XEPDB1", "System", "Admin01").load()
            .migrate();

        Single.fromPublisher(repository.save(new Todo("Javadoc"))).subscribe();
        Single.fromPublisher(repository.save(new Todo("Application"))).subscribe();

        Flux.from(repository.findAll())
            .doOnError(Throwable::printStackTrace)
            .subscribe(System.out::println);
    }

    public static void main(String[] args) throws Exception {
        PicocliRunner.run(Application.class, args);
    }
}