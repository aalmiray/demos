package com.acme.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Application implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        initSchema();

        List<Object[]> descriptions = Arrays.asList(
            new Object[]{"Javadoc"},
            new Object[]{"Application"}
        );

        jdbcTemplate.batchUpdate("INSERT INTO todos(description, done) VALUES (?, 0)", descriptions);

        jdbcTemplate.query("SELECT * FROM todos", (RowMapper<Object>) (rs, i) -> new Todo(rs.getLong("id"), rs.getString("description")))
            .forEach(System.out::println);
    }

    private void initSchema() {
        URL url = Application.class.getClassLoader().getResource("com/acme/demo/schema.ddl");

        try (Scanner sc = new Scanner(url.openStream())) {
            sc.useDelimiter("#");
            while (sc.hasNext()) {
                String line = sc.next().trim();
                jdbcTemplate.execute(line);
            }
        } catch (IOException e) {
            LOG.error("An error occurred when reading schema DDL from " + url, e);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}