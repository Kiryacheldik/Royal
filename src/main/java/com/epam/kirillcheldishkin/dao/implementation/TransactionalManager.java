package com.epam.kirillcheldishkin.dao.implementation;

import com.epam.kirillcheldishkin.connectionPool.ConnectionPool;
import com.epam.kirillcheldishkin.connectionPool.JdbcConnectionPool;
import com.epam.kirillcheldishkin.connectionPool.exception.ConnectionNotCreatedException;
import com.epam.kirillcheldishkin.dao.AbstractCrudDao;
import com.epam.kirillcheldishkin.dao.GenericDao;
import com.epam.kirillcheldishkin.dao.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionalManager {
    private static final Logger LOGGER = LogManager.getLogger(TransactionalManager.class);
    private Connection proxyConnection;

    public void begin(GenericDao dao, GenericDao... daos) throws DaoException {
        try {
            ConnectionPool connectionPool = JdbcConnectionPool.getInstance();
            this.proxyConnection = connectionPool.getConnection();
            proxyConnection.setAutoCommit(false);
            ((AbstractCrudDao)dao).setConnection(proxyConnection);
            for (GenericDao d : daos) {
                ((AbstractCrudDao)d).setConnection(proxyConnection);
            }
        } catch (ConnectionNotCreatedException e) {
            LOGGER.error("Failed while tried to create connection");
            throw new DaoException("Failed to get a connection from CP.");
        } catch (SQLException e) {
            LOGGER.error("Failed while tried to make transaction");
            throw new DaoException("Failed to make transaction.");
        }
    }

    public void end() throws SQLException{
        proxyConnection.setAutoCommit(true);
        proxyConnection.close();
        proxyConnection = null;
    }

    public void commit() throws SQLException{
        proxyConnection.commit();
    }

    public void rollback() throws SQLException {
        proxyConnection.rollback();
    }
}
