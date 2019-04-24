package com.epam.kirillcheldishkin.service;

import com.epam.kirillcheldishkin.comparator.TattooImageComparator;
import com.epam.kirillcheldishkin.dao.ImageCrudDao;
import com.epam.kirillcheldishkin.dao.TattooCrudDao;
import com.epam.kirillcheldishkin.dao.TattooImageCrudDao;
import com.epam.kirillcheldishkin.dao.TattooRatingCrudDao;
import com.epam.kirillcheldishkin.dao.exception.*;
import com.epam.kirillcheldishkin.dao.factory.GenericDaoFactory;
import com.epam.kirillcheldishkin.dao.implementation.*;
import com.epam.kirillcheldishkin.entity.Image;
import com.epam.kirillcheldishkin.entity.Tattoo;
import com.epam.kirillcheldishkin.entity.TattooImage;
import com.epam.kirillcheldishkin.entity.TattooRating;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class TattooService {
    private GenericDaoFactory factory = GenericDaoFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(TattooService.class);

    public List<Tattoo> findAll() throws ServiceException{
        try {
            TattooCrudDao tattooDao = (TattooCrudDao) factory.createDao(Tattoo.class);
            TattooImageCrudDao imageDao = (TattooImageCrudDao) factory.createDao(TattooImage.class);
            TattooRatingCrudDao ratingDao = (TattooRatingCrudDao) factory.createDao(TattooRating.class);
            List<Tattoo> tattoos = tattooDao.findAll();
            for (Tattoo tattoo : tattoos) {
                tattoo.setImages(imageDao.findImagesByTattooId(tattoo.getId()));
                evaluateRating(tattoo, ratingDao);
            }
            return tattoos;
        } catch (DaoException e) {
            LOGGER.error("Failed while tried to find all tattoos");
            throw new ServiceException("Failed while tried to find all tattoos");
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found.");
            throw new ServiceException("Dao not found");
        }
    }

    public Tattoo saveTattoo(Tattoo tattoo) throws DaoNotFoundException, ServiceException, SQLException {
        TransactionalManager manager = new TransactionalManager();
        TattooCrudDao tattooDao = (TattooCrudDao) factory.createTransactionalDao(Tattoo.class);
        TattooImageCrudDao tattooImageDao = (TattooImageCrudDao) factory.createTransactionalDao(TattooImage.class);
        ImageCrudDao imageDao = (ImageCrudDao) factory.createTransactionalDao(Image.class);
        try {
            manager.begin(tattooDao, tattooImageDao, imageDao);
            tattooDao.save(tattoo);
            for (TattooImage image : tattoo.getImages()) {
                imageDao.save(image);
                image.setTattooId(tattoo.getId());
                tattooImageDao.save(image);
            }
            tattoo.getImages().sort(new TattooImageComparator());
            manager.commit();
            return tattoo;
        } catch (DaoException e) {
            manager.rollback();
            LOGGER.error("Failed while tried to save tattoo --> " + tattoo);
            throw new ServiceException("Failed while tried to save tattoo --> " + tattoo);
        } finally {
            manager.end();
        }
    }

    public Tattoo update(Tattoo tattoo) throws ServiceException{
        try {
            TattooCrudDao tattooDao = (TattooCrudDao) factory.createDao(Tattoo.class);
            return tattooDao.update(tattoo);
        } catch (DaoException | DaoNotFoundException e) {
            LOGGER.error("Failed while tried to update tattoo --> " + tattoo);
            throw new ServiceException("Failed while tried to update tattoo --> " + tattoo);
        }
    }

    public List<Tattoo> findByNameLike(String name) throws ServiceException{
        try {
            TattooCrudDao tattooDao = (TattooCrudDao) factory.createDao(Tattoo.class);
            TattooImageCrudDao imageDao = (TattooImageCrudDao) factory.createDao(TattooImage.class);
            TattooRatingCrudDao ratingDao = (TattooRatingCrudDao) factory.createDao(TattooRating.class);
            List<Tattoo> tattoos =  tattooDao.findByNameLike(name);
            for (Tattoo tattoo : tattoos) {
                tattoo.setImages(imageDao.findImagesByTattooId(tattoo.getId()));
                evaluateRating(tattoo, ratingDao);
            }
            return tattoos;
        } catch (DaoException e) {
            LOGGER.error("Failed while tried to find tattoos which have " + name + " in name");
            throw new ServiceException("Failed while tried to find tattoos which have " + name + " in name");
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found.");
            throw new ServiceException("Dao not found");
        }
    }

    public Tattoo findById(Integer id) throws ServiceException {
        try {
            TattooCrudDao tattooDao = (TattooCrudDao) factory.createTransactionalDao(Tattoo.class);
            return tattooDao.findById(id);
        } catch (DaoException | SQLException | NoSuchImageTypeException | NoSuchOrderStateException | NoSuchProposalStateException | DiscountNotFoundException e) {
            LOGGER.error("Failed while tried to find tattoo with id --> " + id);
            throw new ServiceException("Failed while tried to find tattoo with id --> " + id);
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found.");
            throw new ServiceException("Dao not found");
        }
    }

    public List<TattooImage> getImagesById(Integer id) throws ServiceException, DaoNotFoundException {
        TattooImageDao tattooImageDao = (TattooImageDao) factory.createDao(TattooImageDao.class);
        try {
            return tattooImageDao.findImagesByTattooId(id);
        } catch (DaoException e) {
            LOGGER.error("Failed while tried to get images for tattoo with id --> " + id);
            throw new ServiceException("Failed while tried to get images for tattoo with id --> " + id);
        }
    }

    private void evaluateRating(Tattoo tattoo, TattooRatingCrudDao ratingDao) throws DaoException{
        List<Integer> ratingList = ratingDao.findRatingsByTattooId(tattoo.getId());
        int sum = 0;
        if (ratingList.size() <= 2) {
            for (int rating : ratingList) {
                sum += rating;
            }
            tattoo.setRating((double) sum / ratingList.size());
        } else {
            double median = 0;
            int count = 0;
            double delta;
            for (int rating : ratingList) {
                sum += rating;
                count++;
                delta = (double) sum / count / count;
                if (rating < median) {
                    median -= delta;
                } else {
                    median += delta;
                }
            }
            tattoo.setRating(median);
        }
    }
}
