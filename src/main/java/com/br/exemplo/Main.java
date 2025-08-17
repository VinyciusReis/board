package com.br.exemplo;

import java.sql.SQLException;

import com.br.exemplo.flyway.FlywayConfig;
import com.br.exemplo.ui.MainMenu;

public class Main {
    public static void main(String[] args) {
        FlywayConfig.migrate();
        try {
            new MainMenu().execute();
        }catch(SQLException e) {
            e.printStackTrace();
        }
    }
}