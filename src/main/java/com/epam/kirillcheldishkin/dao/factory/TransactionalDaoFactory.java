package com.epam.kirillcheldishkin.dao.factory;

import com.epam.kirillcheldishkin.dao.GenericDao;
import com.epam.kirillcheldishkin.dao.exception.DaoNotFoundException;

import java.io.Serializable;

public interface TransactionalDaoFactory {
    <T, ID extends Serializable> GenericDao<T, ID> createTransactionalDao(Class<T> cls) throws DaoNotFoundException;
}
