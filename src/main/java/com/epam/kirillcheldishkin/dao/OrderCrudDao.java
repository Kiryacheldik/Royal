package com.epam.kirillcheldishkin.dao;

import com.epam.kirillcheldishkin.dao.exception.DaoException;
import com.epam.kirillcheldishkin.entity.Order;

import java.io.Serializable;
import java.util.List;

public interface OrderCrudDao extends CrudDao<Order, Integer> {
    List<Order> findOrderListByUserId(Integer id) throws DaoException;
}
