package com.andersenlab.jdbctest.connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class PostgresConnection implements DBConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/users_roles";
    private static final String USER = "Baikalov_V_A";
    private static final String PASSWORD = "232748";
    private static final String DRIVER = "org.postgresql.Driver";

    @Override
    public Connection getConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PASSWORD);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("driver not found", e);
        }
    }
}
