package com.epam.kirillcheldishkin.dao;

import java.sql.Connection;

public abstract class AbstractGenericDao {
    protected Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
