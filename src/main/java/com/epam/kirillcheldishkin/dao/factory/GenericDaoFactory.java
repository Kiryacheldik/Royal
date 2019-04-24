package com.epam.kirillcheldishkin.dao.factory;

import com.epam.kirillcheldishkin.connectionPool.ConnectionPool;
import com.epam.kirillcheldishkin.connectionPool.JdbcConnectionPool;
import com.epam.kirillcheldishkin.dao.AbstractCrudDao;
import com.epam.kirillcheldishkin.dao.AbstractGenericDao;
import com.epam.kirillcheldishkin.dao.AutoConnection;
import com.epam.kirillcheldishkin.dao.GenericDao;
import com.epam.kirillcheldishkin.dao.exception.DaoNotFoundException;
import com.epam.kirillcheldishkin.dao.implementation.*;
import com.epam.kirillcheldishkin.entity.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GenericDaoFactory implements DaoFactory, TransactionalDaoFactory {
    private static GenericDaoFactory factory;
    private static  final Lock LOCK = new ReentrantLock();
    private Map<Class, GenericDao> daoMap;

    private class DaoInvocationHandler implements InvocationHandler {
        private final GenericDao dao;

        DaoInvocationHandler(GenericDao dao) {
            this.dao = dao;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object result;

            if (Arrays.stream(dao.getClass().getMethods())
                    .filter(m -> m.isAnnotationPresent(AutoConnection.class))
                    .map(Method::getName)
                    .anyMatch(m -> m.equals(method.getName()))) {

                ConnectionPool connectionPool = JdbcConnectionPool.getInstance();
                Connection connection = connectionPool.getConnection();

                ((AbstractGenericDao)dao).setConnection(connection);

                try {
                    result = method.invoke(dao, args);
                } finally {
                    connectionPool.returnConnectionToPool(connection);
                    ((AbstractGenericDao)dao).setConnection(null);
                }
            } else {
                result = method.invoke(dao, args);
            }
            return result;
        }

    }

    private Map<Class, GenericDao> initialize() {
        daoMap = new HashMap<>();
        daoMap.put(DiscountCard.class, new DiscountCardDao());
        daoMap.put(Image.class, new ImageDao());
        daoMap.put(Order.class, new OrderDao());
        daoMap.put(Tattoo.class, new TattooDao());
        daoMap.put(TattooImage.class, new TattooImageDao());
        daoMap.put(TattooRating.class, new TattooRatingDao());
        daoMap.put(User.class, new UserDao());
        daoMap.put(UserProposal.class, new UserProposalDao());
        daoMap.put(Discount.class, new DiscountDao());
        return daoMap;
    }

    private GenericDaoFactory() {
        daoMap = initialize();
    }

    public static GenericDaoFactory getInstance() {
        LOCK.lock();
        try {
            if (factory == null) {
                factory = new GenericDaoFactory();
            }

        } finally {
            LOCK.unlock();
        }

        return factory;
    }

    @Override
    public GenericDao createDao(Class cls) throws DaoNotFoundException{
        GenericDao dao = daoMap.get(cls);
        if (dao == null) {
            throw new DaoNotFoundException("Sorry, this dao isn't found.");
        }
        return (GenericDao) Proxy.newProxyInstance(
                dao.getClass().getClassLoader(),
                dao.getClass().getInterfaces(),
                new DaoInvocationHandler(dao));
    }

    @Override
    public GenericDao createTransactionalDao(Class cls) throws DaoNotFoundException {
        GenericDao dao = daoMap.get(cls);
        if (dao == null) {
            throw new DaoNotFoundException("Entity Class cannot be found");
        }
        return dao;
    }
}
