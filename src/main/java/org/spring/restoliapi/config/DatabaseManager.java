package org.spring.restoliapi.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseManager {

    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    public DatabaseManager() {
        Dotenv dotenv = Dotenv.load();
        this.dbUrl = dotenv.get("DB_URL");
        this.dbUser = dotenv.get("DB_USERNAME");
        this.dbPassword = dotenv.get("DB_PASSWORD");
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }
}
