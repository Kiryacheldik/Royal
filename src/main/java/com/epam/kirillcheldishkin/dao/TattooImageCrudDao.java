package com.epam.kirillcheldishkin.dao;

import com.epam.kirillcheldishkin.dao.exception.DaoException;
import com.epam.kirillcheldishkin.entity.TattooImage;

import java.util.List;

public interface TattooImageCrudDao extends CrudDao<TattooImage, Integer> {
    List<TattooImage> findImagesByTattooId(Integer id) throws DaoException;
}
