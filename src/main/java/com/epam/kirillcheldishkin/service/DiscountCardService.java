package com.epam.kirillcheldishkin.service;

import com.epam.kirillcheldishkin.dao.DiscountCardCrudDao;
import com.epam.kirillcheldishkin.dao.DiscountGenericDao;
import com.epam.kirillcheldishkin.dao.exception.*;
import com.epam.kirillcheldishkin.dao.factory.GenericDaoFactory;
import com.epam.kirillcheldishkin.entity.Discount;
import com.epam.kirillcheldishkin.entity.DiscountCard;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class DiscountCardService {
    private GenericDaoFactory factory = GenericDaoFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(DiscountCardService.class);

    public List<Integer> findAllDiscounts() throws ServiceException{
        try {
            DiscountGenericDao discountDao = (DiscountGenericDao) factory.createDao(Discount.class);
            return discountDao.findAll();
        } catch (DaoException e) {
            LOGGER.error("Failed while tried to find all discounts");
            throw new ServiceException("Failed while tried to find all discounts");
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found.");
            throw new ServiceException("Dao not found");
        }
    }

    public DiscountCard findById(Integer id) throws ServiceException{
        try {
            DiscountCardCrudDao discountCardDao = (DiscountCardCrudDao) factory.createDao(DiscountCard.class);
            return discountCardDao.findById(id);
        } catch (DaoException | SQLException | NoSuchImageTypeException | NoSuchOrderStateException | NoSuchProposalStateException | DiscountNotFoundException e) {
            LOGGER.error("Failed while tried to find card with id --> " + id);
            throw new ServiceException("Failed while tried to find card with id --> " + id);
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found.");
            throw new ServiceException("Dao not found");
        }
    }

    public DiscountCard update(DiscountCard card, int discount) throws ServiceException{
        try {
            DiscountCardCrudDao discountCardDao = (DiscountCardCrudDao) factory.createDao(DiscountCard.class);
            return discountCardDao.update(card, discount);
        } catch (DaoException e) {
            LOGGER.error("Failed while tried to update discount in card with id --> " + card.getId() + " by " + discount);
            throw new ServiceException("Failed while tried to update discount in card with id --> " + card.getId() + " by " + discount);
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found.");
            throw new ServiceException("Dao not found");
        }
    }

    public DiscountCard saveCard(Integer userId, int cardNumber, int discount) throws ServiceException {
        DiscountCard card = new DiscountCard(cardNumber, true);
        card.setUserId(userId);
        try {
            DiscountCardCrudDao discountCardDao = (DiscountCardCrudDao) factory.createDao(DiscountCard.class);
            card = discountCardDao.save(card, discount);
            return card;
        } catch (DaoException e) {
            LOGGER.error("Failed while tried to save card --> " + card);
            throw new ServiceException("Failed while tried to save card --> " + card);
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found.");
            throw new ServiceException("Dao not found");
        }
    }

    public int findLastCardNumber() throws ServiceException{
        try {
            DiscountCardCrudDao cardDao = (DiscountCardCrudDao) factory.createDao(DiscountCard.class);
            List<Integer> cardNumbers = cardDao.findCardNumbers();
            return findLast(cardNumbers);
        } catch (DaoException e) {
            LOGGER.error("Failed while tried to find last card number");
            throw new ServiceException("Failed while tried to find last card number");
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found.");
            throw new ServiceException("Dao not found");
        }
    }

    private int findLast(List<Integer> cardNumbers) {
        if (cardNumbers.isEmpty()) {
            return 1_000_000;
        }
        int number = cardNumbers.get(0);
        for (int cardNumber : cardNumbers) {
            if (cardNumber > number) {
                number = cardNumber;
            }
        }
        return number;
    }
}
