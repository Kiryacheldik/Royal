package com.epam.kirillcheldishkin.dao;

import com.epam.kirillcheldishkin.dao.exception.DaoException;
import com.epam.kirillcheldishkin.entity.TattooRating;

import java.util.List;

public interface TattooRatingCrudDao extends CrudDao<TattooRating, Integer> {
    List<Integer> findRatingsByTattooId(Integer id) throws DaoException;
    boolean findRatingByUserAndTattooId(Integer userId, Integer tattooId) throws DaoException;
}
