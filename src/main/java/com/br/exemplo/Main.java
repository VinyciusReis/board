package com.br.exemplo;

import com.br.exemplo.flyway.FlywayConfig;

public class Main {
    public static void main(String[] args) {
        FlywayConfig.migrate();
    }
}