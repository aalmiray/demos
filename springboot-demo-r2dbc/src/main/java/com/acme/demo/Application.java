package com.acme.demo;

import io.r2dbc.spi.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

import java.time.Duration;
import java.util.Arrays;

@SpringBootApplication
public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    @Bean
    public CommandLineRunner demo(TodoRepository repository) {
        return (args) -> {
            repository.saveAll(Arrays.asList(new Todo("Javadoc"),
                new Todo("Application")))
                .blockLast(Duration.ofSeconds(10));

            LOG.info("Display all todos:");
            LOG.info("-------------------------------");
            repository.findAll().doOnNext(todo -> {
                LOG.info(todo.toString());
            }).blockLast(Duration.ofSeconds(10));
            LOG.info("");

            repository.findById(1L).doOnNext(todo -> {
                LOG.info("Todo found with findById(1L):");
                LOG.info("--------------------------------");
                LOG.info(todo.toString());
                LOG.info("");
            }).block(Duration.ofSeconds(10));

            LOG.info("Todo found with findByDescription('Application'):");
            LOG.info("--------------------------------------------");
            repository.findByDescription("Application").doOnNext(todo -> {
                LOG.info(todo.toString());
            }).blockLast(Duration.ofSeconds(10));

            LOG.info("");
        };
    }

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(new ClassPathResource("oracle.sql"));
        databasePopulator.setSeparator("#");
        initializer.setDatabasePopulator(databasePopulator);
        return initializer;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}