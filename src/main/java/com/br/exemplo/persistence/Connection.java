package com.br.exemplo.persistence;

import static java.lang.System.getenv;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connection {

    public static java.sql.Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getenv("DB_URL"), getenv("DB_USER"), getenv("DB_PASSWORD"));
    }
}
