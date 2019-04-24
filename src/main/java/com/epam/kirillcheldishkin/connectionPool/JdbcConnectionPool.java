package com.epam.kirillcheldishkin.connectionPool;

import com.epam.kirillcheldishkin.connectionPool.exception.ConnectionNotClosedException;
import com.epam.kirillcheldishkin.connectionPool.exception.ConnectionNotCreatedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JdbcConnectionPool implements ConnectionPool {
    private static final Logger LOGGER = LogManager.getLogger(ConnectionPool.class);
    private static ConnectionPool connectionPool;
    private static final Lock LOCK = new ReentrantLock();
    private int POOL_CAPACITY = 20;
    private final Semaphore SEMAPHORE;
    private final Queue<Connection> pool;
    private final static ConnectionProperties CONNECTION_PROPERTIES = new ConnectionProperties();
    private Properties properties;
    private final static String BD_CONFIG_FILE_NAME = "/Users/kirillceldyskin/Downloads/FinalTask/src/main/resources/db.properties";

    private JdbcConnectionPool(){
        pool = new ArrayDeque<>(POOL_CAPACITY);
        SEMAPHORE = new Semaphore(POOL_CAPACITY);
        properties = CONNECTION_PROPERTIES.getConnectionProperties(BD_CONFIG_FILE_NAME);
        initDriver();
    }

    public static ConnectionPool getInstance() {
        if (connectionPool == null) {
            LOCK.lock();
            try {
                connectionPool = new JdbcConnectionPool();
            } finally {
                LOCK.unlock();
            }
        }
        return connectionPool;
    }

    @Override
    public void initialize() throws SQLException{
        for (int i = 0; i < POOL_CAPACITY; i++) {
            Connection connection = DriverManager.getConnection(properties.getProperty("url"), properties);
            pool.add(connection);
        }
    }

    @Override
    public Connection getConnection() throws ConnectionNotCreatedException {
        try {
            SEMAPHORE.acquire();
            LOCK.lock();
            Connection connection = pool.poll();
            if (!connection.isValid(3)) {
                connection = DriverManager.getConnection(properties.getProperty("url"), properties);
            }
            InvocationHandler handler = new  Handler(connection);
            Class[] classes = {Connection.class};
            return (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(), classes, handler);
        } catch (InterruptedException | SQLException e) {
            LOGGER.error("Failed while tried to get connection. Exception --> " + e);
            throw new ConnectionNotCreatedException("Failed while tried to get connection to data base");
        } finally {
            LOCK.unlock();
        }
    }

    @Override
    public void returnConnectionToPool(Connection connection) throws SQLException{
        try {
            LOGGER.info("releaseConnection");
            LOCK.lock();
            if (Proxy.getInvocationHandler(connection).getClass().equals(Handler.class)) {
                Connection returnConnection = ((Handler)Proxy.getInvocationHandler(connection)).getConnection();
                pool.add(returnConnection);
            } else {
                connection.close();
            }
        } finally {
            SEMAPHORE.release();
            LOCK.unlock();
        }
    }

    @Override
    public void destroy() throws ConnectionNotClosedException {
        for (Connection connection : pool) {
            try {
                LOGGER.info("Connection is closing --> " + connection);
                connection.close();
            } catch (SQLException e) {
                LOGGER.error("Failed while tried to close connection --> " + connection);
                throw new ConnectionNotClosedException("Failed while tried to close connection --> " + connection);
            }
        }
    }

    @Override
    public void initDriver() {
        try {
            Class.forName(properties.getProperty("driver"));
        } catch (ClassNotFoundException e) {
            LOGGER.error("Driver not found " + e);
            throw new IllegalStateException("Driver cannot be found", e);
        }
    }

    private class Handler implements InvocationHandler {
        private Connection connection;

        public Connection getConnection() {
            return connection;
        }

        Handler(Connection connection){
            this.connection=connection;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String methodName = method.getName();
            if (methodName.equals("close")) {
                returnConnectionToPool((Connection) proxy);
                return null;
            } else {
                return method.invoke(connection, args);
            }
        }
    }
}
