package com.epam.kirillcheldishkin.service;

import com.epam.kirillcheldishkin.dao.UserCrudDao;
import com.epam.kirillcheldishkin.dao.UserProposalCrudDao;
import com.epam.kirillcheldishkin.dao.exception.*;
import com.epam.kirillcheldishkin.dao.factory.GenericDaoFactory;
import com.epam.kirillcheldishkin.dao.implementation.TransactionalManager;
import com.epam.kirillcheldishkin.entity.User;
import com.epam.kirillcheldishkin.entity.UserProposal;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class UserProposalService {
    private GenericDaoFactory factory = GenericDaoFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(UserProposalService.class);

    public UserProposal findById(Integer id) throws ServiceException{
        try {
            UserProposalCrudDao proposalDao = (UserProposalCrudDao) factory.createDao(UserProposal.class);
            return proposalDao.findById(id);
        } catch (DaoException | SQLException | NoSuchImageTypeException | NoSuchOrderStateException | NoSuchProposalStateException | DiscountNotFoundException e) {
            LOGGER.error("Failed while tried to find proposal with id --> " + id);
            throw new ServiceException("Failed while tried to find proposal with id --> " + id);
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found.");
            throw new ServiceException("Dao not found");
        }
    }

    public List<UserProposal> findAllProposals() throws ServiceException, SQLException{
        TransactionalManager manager = new TransactionalManager();
        List<UserProposal> proposals;
        try {
            UserProposalCrudDao proposalDao = (UserProposalCrudDao) factory.createTransactionalDao(UserProposal.class);
            UserCrudDao userDao = (UserCrudDao) factory.createTransactionalDao(User.class);
            manager.begin(proposalDao, userDao);
            proposals = proposalDao.findAll();
            for (UserProposal proposal : proposals) {
                String userName = userDao.findById(proposal.getUser().getId()).getUsername();
                proposal.getUser().setUsername(userName);
            }
            manager.commit();
        } catch (DaoException | NoSuchImageTypeException | NoSuchOrderStateException | NoSuchProposalStateException | DiscountNotFoundException e) {
            manager.rollback();
            LOGGER.error("Failed while tried to find all proposals");
            throw new ServiceException("Failed while tried to find all proposals");
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found.");
            throw new ServiceException("Dao not found");
        } finally {
            manager.end();
        }
        return proposals;
    }
}
