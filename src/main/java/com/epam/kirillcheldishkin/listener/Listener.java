package com.epam.kirillcheldishkin.listener;

import com.epam.kirillcheldishkin.connectionPool.ConnectionPool;
import com.epam.kirillcheldishkin.connectionPool.JdbcConnectionPool;
import com.epam.kirillcheldishkin.connectionPool.exception.ConnectionNotClosedException;
import com.epam.kirillcheldishkin.dao.implementation.DiscountCardDao;
import com.epam.kirillcheldishkin.service.TattooService;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import com.epam.kirillcheldishkin.util.DiscountCardNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

public class Listener implements ServletContextListener {
    private static final Logger LOGGER = LogManager.getLogger(Listener.class);

    @Override
    public void contextInitialized(ServletContextEvent event) {
        ConnectionPool connectionPool = JdbcConnectionPool.getInstance();
        try {
            connectionPool.initialize();
            DiscountCardNumber.getInstance().initialize();
        } catch (SQLException | ServiceException e) {
           LOGGER.error("Failed while tried to initialize listener");
        }
    }
    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("destroyed");
        ConnectionPool connectionPool = JdbcConnectionPool.getInstance();
        try {
            connectionPool.destroy();
        } catch (ConnectionNotClosedException e) {
            LOGGER.error("Failed while tried to destroy connection pool");
        }
    }
}
