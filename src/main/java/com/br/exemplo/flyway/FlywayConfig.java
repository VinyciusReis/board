package com.br.exemplo.flyway;

import static java.lang.System.getenv;

import org.flywaydb.core.Flyway;

public class FlywayConfig {

    public static void migrate() {
        Flyway.configure().
        locations("classpath:com/br/exemplo/flyway/migrations").
        dataSource(getenv("DB_URL"), getenv("DB_USER"), getenv("DB_PASSWORD")).
        load().migrate();
    }
}
