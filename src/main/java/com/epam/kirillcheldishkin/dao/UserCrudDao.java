package com.epam.kirillcheldishkin.dao;

import com.epam.kirillcheldishkin.dao.exception.DaoException;
import com.epam.kirillcheldishkin.entity.Client;
import com.epam.kirillcheldishkin.entity.User;

public interface UserCrudDao extends CrudDao<User, Integer> {
    User identifyUser(String login, String password) throws DaoException;
    User findByLogin(String login) throws DaoException;
    Client updateRating(Client client) throws DaoException;
    Client updateActiveStatus(Client client) throws DaoException;
}
