package com.epam.kirillcheldishkin.service;

import com.epam.kirillcheldishkin.dao.ImageCrudDao;
import com.epam.kirillcheldishkin.dao.exception.DaoException;
import com.epam.kirillcheldishkin.dao.exception.DaoNotFoundException;
import com.epam.kirillcheldishkin.dao.factory.GenericDaoFactory;
import com.epam.kirillcheldishkin.entity.Image;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ImageService {
    private GenericDaoFactory factory = GenericDaoFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(ImageService.class);

    public List<Image> findAll() throws ServiceException{
        try {
            ImageCrudDao imageDao = (ImageCrudDao) factory.createDao(Image.class);
            return imageDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Failed while tried to find all images");
            throw new ServiceException("Failed while tried to find all images");
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found.");
            throw new ServiceException("Dao not found");
        }
    }

    public void renameImage(Image image) throws ServiceException{
        try {
            ImageCrudDao imageDao = (ImageCrudDao) factory.createDao(Image.class);
            imageDao.update(image);
        } catch (DaoException e) {
            LOGGER.error("Failed while tried to rename image with id --> " + image.getId() + " by new name --> " + image.getImage());
            throw new ServiceException("Failed while tried to rename image with id --> " + image.getId() + " by new name --> " + image.getImage());
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found.");
            throw new ServiceException("Dao not found");
        }
    }
}
