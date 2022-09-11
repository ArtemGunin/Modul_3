package com.nix11;

import com.nix11.command.ConsoleMenu;
import com.nix11.config.FlywayConfig;
import com.nix11.config.HibernateFactoryUtils;
import com.nix11.config.JDBCConfig;
import lombok.SneakyThrows;
import org.flywaydb.core.Flyway;

import java.sql.SQLException;

public class Main {

    @SneakyThrows
    public static void main(String[] args) {

        Class.forName("org.postgresql.Driver");

        Flyway flyway = FlywayConfig.getFlywayConfig();

        flyway.clean();

        try {
            JDBCConfig.getConnection().createStatement().execute("create SCHEMA IF NOT EXISTS public");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        HibernateFactoryUtils.getSessionFactory();

        flyway.migrate();

        ConsoleMenu.menu();
    }

}
