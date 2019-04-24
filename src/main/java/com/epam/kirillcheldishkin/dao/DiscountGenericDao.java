package com.epam.kirillcheldishkin.dao;

import com.epam.kirillcheldishkin.dao.exception.DaoException;
import com.epam.kirillcheldishkin.entity.Discount;

import java.io.Serializable;
import java.util.List;

public interface DiscountGenericDao extends GenericDao<Discount, Integer>{
    List<Integer> findAll() throws DaoException;
}
