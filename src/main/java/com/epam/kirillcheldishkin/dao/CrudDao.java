package com.epam.kirillcheldishkin.dao;

import com.epam.kirillcheldishkin.dao.exception.*;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface CrudDao<T, ID extends Serializable> extends GenericDao<T, ID> {
    List<T> findAll() throws DaoException;
    T findById(ID id) throws SQLException, DaoException, NoSuchOrderStateException, NoSuchImageTypeException, NoSuchProposalStateException, DiscountNotFoundException;
    T save(T entity) throws DaoException;
    T update(T t) throws DaoException;
    void deleteById(ID id) throws DaoException;
    T initializeEntity(ResultSet resultSet) throws SQLException, DaoException, NoSuchOrderStateException, NoSuchImageTypeException, NoSuchProposalStateException, DiscountNotFoundException;
}
