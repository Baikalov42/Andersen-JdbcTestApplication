package com.andersenlab.jdbctest.connection;

import java.sql.Connection;

public interface DBConnection {
    Connection getConnection();
}
