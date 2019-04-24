package com.epam.kirillcheldishkin.dao;

import com.epam.kirillcheldishkin.dao.exception.DaoException;
import com.epam.kirillcheldishkin.entity.Tattoo;

import java.util.List;

public interface TattooCrudDao extends CrudDao<Tattoo, Integer> {
    List<Tattoo> findByNameLike(String name) throws DaoException;
}
