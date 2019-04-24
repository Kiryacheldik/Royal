package com.epam.kirillcheldishkin.dao;

import com.epam.kirillcheldishkin.dao.exception.DaoException;
import com.epam.kirillcheldishkin.entity.DiscountCard;

import java.util.List;

public interface DiscountCardCrudDao extends CrudDao<DiscountCard, Integer> {
    int findDiscountById(Integer id) throws DaoException;
    List<Integer> findCardNumbers() throws DaoException;
    DiscountCard findByUserId(Integer id) throws DaoException;
    DiscountCard save(DiscountCard card, int discount) throws DaoException;
    DiscountCard update(DiscountCard card, int discount) throws DaoException;
}
