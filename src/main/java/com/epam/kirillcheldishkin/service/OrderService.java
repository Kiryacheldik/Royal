package com.epam.kirillcheldishkin.service;

import com.epam.kirillcheldishkin.dao.OrderCrudDao;
import com.epam.kirillcheldishkin.dao.TattooCrudDao;
import com.epam.kirillcheldishkin.dao.UserCrudDao;
import com.epam.kirillcheldishkin.dao.exception.*;
import com.epam.kirillcheldishkin.dao.factory.GenericDaoFactory;
import com.epam.kirillcheldishkin.dao.implementation.TransactionalManager;
import com.epam.kirillcheldishkin.entity.Order;
import com.epam.kirillcheldishkin.entity.Tattoo;
import com.epam.kirillcheldishkin.entity.User;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private GenericDaoFactory factory = GenericDaoFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(OrderService.class);

    public List<Order> findAll() throws ServiceException, SQLException {
        TransactionalManager manager = new TransactionalManager();
        List<Order> orderList;
        try {
            OrderCrudDao orderDao = (OrderCrudDao) factory.createTransactionalDao( Order.class);
            UserCrudDao userDao = (UserCrudDao) factory.createTransactionalDao(User.class);
            TattooCrudDao tattooDao = (TattooCrudDao) factory.createTransactionalDao(Tattoo.class);
            manager.begin(orderDao, userDao, tattooDao);
            orderList = orderDao.findAll();
            findUserLoginAndTattooName(orderList, userDao, tattooDao);
            manager.commit();
        } catch (DaoException | NoSuchImageTypeException | NoSuchOrderStateException | NoSuchProposalStateException | DiscountNotFoundException e) {
            manager.rollback();
            LOGGER.error("Failed while tried to find all orders");
            throw new ServiceException("Failed while tried to find all orders");
        } catch (DaoNotFoundException e) {
            manager.rollback();
            LOGGER.error("Dao not found.");
            throw new ServiceException("Dao not found");
        } finally {
            manager.end();
        }
        return orderList;
    }

    public List<Order> findOrdersByUserId(Integer id) throws ServiceException, SQLException{
        TransactionalManager manager = new TransactionalManager();
        List<Order> orderList;
        try {
            OrderCrudDao orderDao = (OrderCrudDao) factory.createTransactionalDao(Order.class);
            UserCrudDao userDao = (UserCrudDao) factory.createTransactionalDao(User.class);
            TattooCrudDao tattooDao = (TattooCrudDao) factory.createTransactionalDao(Tattoo.class);
            manager.begin(orderDao, userDao, tattooDao);
            orderList = orderDao.findOrderListByUserId(id);
            findUserLoginAndTattooName(orderList, userDao, tattooDao);
            manager.commit();
        } catch (DaoException | NoSuchImageTypeException | NoSuchOrderStateException | NoSuchProposalStateException | DiscountNotFoundException e) {
            manager.rollback();
            LOGGER.error("Failed while tried to find orders for user with id --> " + id);
            throw new ServiceException("Failed while tried to find all orders");
        } catch (DaoNotFoundException e) {
            manager.rollback();
            LOGGER.error("Dao not found.");
            throw new ServiceException("Dao not found");
        } finally {
            manager.end();
        }
        return orderList;
    }

    public Order makeNextStepOrderState(Order order) throws ServiceException, SQLException{
        try {
            OrderCrudDao orderDao = (OrderCrudDao) factory.createDao(Order.class);
            return orderDao.update(order);
        } catch (DaoException e) {
            LOGGER.error("Failed while tried to make next state in order with id --> " + order.getId());
            throw new ServiceException("Failed while tried to make next state in order with id --> " + order.getId());
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found.");
            throw new ServiceException("Dao not found");
        }
    }

    public Order findById(Integer id) throws ServiceException{
        try {
            OrderCrudDao orderDao = (OrderCrudDao) factory.createDao(Order.class);
            return orderDao.findById(id);
        } catch (DaoException | SQLException | NoSuchImageTypeException | NoSuchOrderStateException | NoSuchProposalStateException | DiscountNotFoundException e) {
            LOGGER.error("Failed while tried to find order with id --> " + id);
            throw new ServiceException("Failed while tried to find order with id --> " + id);
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found.");
            throw new ServiceException("Dao not found");
        }
    }

    public Order save(Order order) throws ServiceException, SQLException{
        TransactionalManager manager = new TransactionalManager();
        try {
            OrderCrudDao dao = (OrderCrudDao) factory.createTransactionalDao(Order.class);
            manager.begin(dao);
            dao.save(order);
            manager.commit();
            return order;
        } catch (DaoException e) {
            manager.rollback();
            LOGGER.error("Failed while tried to save order --> " + order);
            throw new ServiceException("Failed while tried to save order --> " + order);
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found.");
            throw new ServiceException("Dao not found");
        } finally {
            manager.end();
        }
    }

    public Order update(Order order) throws ServiceException{
        try {
            OrderCrudDao orderDao = (OrderCrudDao) factory.createDao(Order.class);
            return orderDao.update(order);
        } catch (DaoException e) {
            LOGGER.error("Failed while tried to update order with id --> " + order.getId());
            throw new ServiceException("Failed while tried to update order with id --> " + order.getId());
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found.");
            throw new ServiceException("Dao not found");
        }
    }

    private void findUserLoginAndTattooName(List<Order> orderList, UserCrudDao userDao, TattooCrudDao tattooDao) throws SQLException, DaoException, NoSuchOrderStateException, NoSuchImageTypeException, NoSuchProposalStateException, DiscountNotFoundException {
        for (Order order : orderList) {
            User user = userDao.findById(order.getUser().getId());
            Tattoo tattoo = tattooDao.findById(order.getTattoo().getId());
            order.getUser().setLogin(user.getLogin());
            order.getTattoo().setName(tattoo.getName());
        }
    }
}
