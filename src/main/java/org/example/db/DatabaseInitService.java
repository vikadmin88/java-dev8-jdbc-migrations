package org.example.db;

import org.example.utils.AppPropertyReader;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseInitService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseInitService.class);

    public static void main(String[] args) {
        initDatabase();
    }

    public static void initDatabase() {
        LOGGER.info("H2 migrations...");
        String h2connUrl = AppPropertyReader.getConnectionUrlForH2();
        Flyway flyway = Flyway.configure().dataSource(h2connUrl, null, null).load();
        flyway.migrate();
    }
}
