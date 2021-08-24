package com.acme.demo;

import io.agroal.api.AgroalDataSource;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import javax.enterprise.context.control.ActivateRequestContext;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

@QuarkusMain
@Command(name = "demo", mixinStandardHelpOptions = true)
public class DemoCommand implements Runnable, QuarkusApplication {
    private static final Logger LOG = LoggerFactory.getLogger(DemoCommand.class);

    @Inject
    CommandLine.IFactory factory;

    @Inject
    AgroalDataSource dataSource;

    @Override
    @Transactional
    @ActivateRequestContext
    public void run() {
        initSchema();

        new Todo("Javadoc").persist();
        new Todo("Application").persist();

        Todo.findAll().stream()
            .forEach(System.out::println);
    }

    @Override
    public int run(String... args) throws Exception {
        return new CommandLine(this, factory).execute(args);
    }

    private void initSchema() {
        URL url = DemoCommand.class.getClassLoader().getResource("com/acme/demo/schema.ddl");

        try {
            Connection connection = dataSource.getConnection();
            try (Scanner sc = new Scanner(url.openStream()); Statement statement = connection.createStatement()) {
                sc.useDelimiter("#");
                while (sc.hasNext()) {
                    String line = sc.next().trim();
                    statement.execute(line);
                }
            } catch (IOException | SQLException e) {
                LOG.error("An error occurred when reading schema DDL from " + url, e);
            }
            connection.close();
        } catch (SQLException e) {
            LOG.error("An error occurred when reading schema DDL from " + url, e);
        }
    }
}
