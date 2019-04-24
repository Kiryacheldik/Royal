package com.epam.kirillcheldishkin.connectionPool;

import com.epam.kirillcheldishkin.connectionPool.exception.ConnectionNotClosedException;
import com.epam.kirillcheldishkin.connectionPool.exception.ConnectionNotCreatedException;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {
    void initialize() throws SQLException;
    Connection getConnection() throws ConnectionNotCreatedException;
    void returnConnectionToPool(Connection connection) throws SQLException;
    void destroy() throws ConnectionNotClosedException;
    void initDriver();
}
