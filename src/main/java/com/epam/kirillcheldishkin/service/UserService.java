package com.epam.kirillcheldishkin.service;

import com.epam.kirillcheldishkin.dao.*;
import com.epam.kirillcheldishkin.dao.exception.*;
import com.epam.kirillcheldishkin.dao.factory.GenericDaoFactory;
import com.epam.kirillcheldishkin.dao.implementation.*;
import com.epam.kirillcheldishkin.entity.*;
import com.epam.kirillcheldishkin.entity.state.SubmittedState;
import com.epam.kirillcheldishkin.entity.user.UserRole;
import com.epam.kirillcheldishkin.listener.Listener;
import com.epam.kirillcheldishkin.service.exception.UserRegisterException;
import com.epam.kirillcheldishkin.util.PasswordEncryptor;
import com.epam.kirillcheldishkin.validation.UserValidator;
import com.epam.kirillcheldishkin.validation.exception.InvalidUserPasswordException;
import com.epam.kirillcheldishkin.validation.exception.LoginIsAlreadyUsedException;
import com.epam.kirillcheldishkin.validation.exception.ValidationException;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private GenericDaoFactory factory = GenericDaoFactory.getInstance();
    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    /*private UserDao userDao = factory.createDao(UserDao.class);
    private OrderDao orderDao = factory.createDao(OrderDao.class);
    private TattooDao tattooDao = factory.createDao(TattooDao.class);
    private TattooRatingDao ratingDao = factory.createDao(TattooRatingDao.class);
    private ImageDao imageDao = factory.createDao(ImageDao.class);
    private UserProposalDao proposalDao = factory.createDao(UserProposal.class);*/ // Think about this approach

    public List<User> findAll() throws ServiceException {
        TransactionalManager manager = new TransactionalManager();
        List<User> userList;
        try {
            UserCrudDao userDao = (UserCrudDao) factory.createTransactionalDao(User.class);
            DiscountCardCrudDao cardDao = (DiscountCardCrudDao) factory.createTransactionalDao(DiscountCard.class);
            manager.begin(userDao, cardDao);
            userList = userDao.findAll();
            for (User user : userList) {
                if (user.getRole().equals(UserRole.CLIENT)) {
                    ((Client)user).setCard(cardDao.findByUserId(user.getId()));
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Failed while tried to find all clients");
            throw new ServiceException("Failed while tried to find all clients");
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found.");
            throw new ServiceException("Dao not found");
        }
        return userList;
    }

    public User update(User user) throws ServiceException{
        try {
            UserCrudDao userDao = (UserCrudDao) factory.createDao(User.class);
            return userDao.update(user);
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found.");
            throw new ServiceException("Dao not found");
        } catch (DaoException e) {
            LOGGER.error("Failed while tried to update user --> " + user);
            throw new ServiceException("Failed while tried to update user --> " + user);
        }
    }

    public User updateClientStatus(Client client) throws ServiceException{
        try {
            UserCrudDao userDao = (UserCrudDao) factory.createDao(User.class);
            return userDao.updateActiveStatus(client);
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found.");
            throw new ServiceException("Dao not found");
        } catch (DaoException e) {
            LOGGER.error("Failed while tried to update user --> " + client);
            throw new ServiceException("Failed while tried to update user --> " + client);
        }
    }

    public User findById(Integer id) throws ServiceException{
        try {
            UserCrudDao userDao = (UserCrudDao) factory.createDao(User.class);
            DiscountCardCrudDao cardDao = (DiscountCardCrudDao) factory.createDao(DiscountCard.class);
            User user = userDao.findById(id);
            if (user != null) {
                return initializeCard(user, cardDao);
            } else {
                return null;
            }
        } catch (DaoException | SQLException | NoSuchImageTypeException | NoSuchOrderStateException | NoSuchProposalStateException | DiscountNotFoundException e) {
            LOGGER.error("Failed while tried to find user with id --> " + id);
            throw new ServiceException("Failed while tried to find user with id --> " + id);
        }
        catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found");
            throw new ServiceException("Dao not found");
        }
    }

    public void deleteById(Integer id) throws ServiceException{
        try {
            UserCrudDao userDao = (UserCrudDao) factory.createDao(User.class);
            userDao.deleteById(id);
        } catch (DaoException e) {
            LOGGER.error("Failed while tried to delete user with id --> " + id);
            throw new ServiceException("Failed while tried to delete user with id --> " + id);
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found");
            throw new ServiceException("Dao not found");
        }
    }

    public void addRatingToTattoo(Integer userId, Integer tattooId, int rating) throws ServiceException{
        TattooRating tattooRating = new TattooRating(rating);
        tattooRating.setUserId(userId);
        tattooRating.setTattooId(tattooId);
        try {
            TattooRatingCrudDao ratingDao = (TattooRatingCrudDao) factory.createDao(TattooRating.class);
            if (ratingDao.findRatingByUserAndTattooId(userId, tattooId)) {
                ratingDao.update(tattooRating);
            } else {
                ratingDao.save(tattooRating);
            }
        } catch (DaoException e) {
            LOGGER.error("Failed while tried to add rating to tattoo with id --> " + tattooId + " from user with id --> " + userId);
            throw new ServiceException("Failed while tried to add rating to tattoo with id --> " + tattooId + " from user with id --> " + userId);
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found");
            throw new ServiceException("Dao not found");
        }
    }

    public void signIn(User user) throws ServiceException, UserRegisterException, LoginIsAlreadyUsedException, SQLException {
        TransactionalManager manager = new TransactionalManager();
        try {
            UserCrudDao userDao = (UserCrudDao) factory.createTransactionalDao(User.class);
            manager.begin(userDao);
            if (UserValidator.validate(user) && UserValidator.isReservedLogin(user.getLogin(), userDao)) {
                String password = PasswordEncryptor.encrypt(user.getPassword() + user.getLogin());
                user.setPassword(password);
                userDao.save(user);
            } else {
                LOGGER.error("Invalid user information --> " + user);
                throw new UserRegisterException("Invalid user information --> " + user);
            }
            manager.commit();
        } catch (DaoException | ValidationException e) {
            manager.rollback();
            LOGGER.error("Failed while tried to save user");
            throw new ServiceException("Failed while tried to save user");
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found");
            throw new ServiceException("Dao not found");
        } finally {
            manager.end();
        }
    }

    public User logIn(String login, String password) throws ServiceException {
        try {
            UserCrudDao userDao = (UserCrudDao) factory.createDao(User.class);
            DiscountCardCrudDao cardDao = (DiscountCardCrudDao) factory.createDao(DiscountCard.class);
            String encryptedPassword = PasswordEncryptor.encrypt(password + login);
            User user = userDao.identifyUser(login, encryptedPassword);
            if (user != null) {
                return initializeCard(user, cardDao);
            } else {
                return null;
            }
        } catch (DaoException e) {
            LOGGER.error("Failed while tried to log in user with login --> " + login + " and password --> " + password);
            throw new ServiceException("Failed while tried to log in user with login --> " + login + " and password --> " + password);
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found");
            throw new ServiceException("Dao not found");
        }
    }

    public UserProposal proposeImage(Image image, Integer userId) throws ServiceException, SQLException{
        TransactionalManager manager = new TransactionalManager();
        try {
            if (image == null) {
                LOGGER.error("Invalid data. Image is null");
                throw new ServiceException("Invalid data. Image is null");
            }
            ImageCrudDao imageDao = (ImageCrudDao) factory.createTransactionalDao(Image.class);
            UserProposalCrudDao proposalDao = (UserProposalCrudDao) factory.createTransactionalDao(UserProposal.class);
            manager.begin(imageDao, proposalDao);
            imageDao.save(image);
            UserProposal proposal = new UserProposal(new SubmittedState());
            proposal.getUser().setId(userId);
            proposal.setImage(image);
            proposalDao.save(proposal);
            manager.commit();
            return proposal;
        } catch (DaoException e) {
            manager.rollback();
            LOGGER.error("Failed while tried to save image --> " + image);
            throw new ServiceException("Failed while tried to save image --> " + image);
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found");
            throw new ServiceException("Dao not found");
        }
        finally {
            manager.end();
        }
    }

    public List<UserProposal> findProposalsByUserId(Integer userId) throws ServiceException{
        List<UserProposal> proposals;
        try {
            UserProposalCrudDao proposalDao = (UserProposalCrudDao) factory.createDao(UserProposal.class);
            proposals = proposalDao.findProposalsByUserId(userId);
        } catch (DaoException e) {
            LOGGER.error("Failed while tried to find user with id --> " + userId);
            throw new ServiceException("Failed while tried to find user with id --> " + userId);
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found");
            throw new ServiceException("Dao not found");
        }
        return proposals;
    }

    public UserProposal cancelProposal(UserProposal proposal) throws ServiceException{
        try {
            UserProposalCrudDao proposalDao = (UserProposalCrudDao) factory.createDao(UserProposal.class);
            proposalDao.update(proposal);
            return proposal;
        } catch (DaoException e) {
            LOGGER.error("Failed while tried to update proposal with id " + proposal.getId() + " to state --> " + proposal.getState());
            throw new ServiceException("Failed while tried to update proposal with id " + proposal.getId() + " to state --> " + proposal.getState());
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found");
            throw new ServiceException("Dao not found");
        }
    }

    public UserProposal acceptProposal(UserProposal proposal, int rating) throws ServiceException, SQLException{
        TransactionalManager manager = new TransactionalManager();
        try {
            UserProposalCrudDao proposalDao = (UserProposalCrudDao) factory.createTransactionalDao(UserProposal.class);
            UserCrudDao userDao = (UserCrudDao) factory.createTransactionalDao(User.class);
            manager.begin(proposalDao, userDao);
            proposalDao.update(proposal);
            User user = userDao.findById(proposal.getUser().getId());
            ((Client)user).setRating(((Client) user).getRating() + rating);
            userDao.updateRating((Client) user);
            manager.commit();
            return proposal;
        } catch (DaoException | SQLException | NoSuchImageTypeException | NoSuchOrderStateException | NoSuchProposalStateException | DiscountNotFoundException e) {
            manager.rollback();
            LOGGER.error("Failed while tried to update state --> " + proposal.getState() + " in proposal with id --> " + proposal.getId());
            throw new ServiceException("Failed while tried to update state --> " + proposal.getState() + " in proposal with id --> " + proposal.getId());
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found");
            throw new ServiceException("Dao not found");
        } finally {
            manager.end();
        }
    }

    public void addRatingToWorkQuality(Integer userId, Integer tattooId, int rating) throws ServiceException{
        Order order = new Order(userId, tattooId);
        try {
            OrderCrudDao orderDao = (OrderCrudDao) factory.createDao(Order.class);
            order.setRating(rating);
            orderDao.update(order);
        } catch ( DaoException e) {
            LOGGER.error("Failed while tried to add rating to order --> " + order + " from user with id --> " + userId);
            throw new ServiceException("Failed while tried to add rating to order --> " + order + " from user with id --> " + userId);
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found");
            throw new ServiceException("Dao not found");
        }
    }

    public void makeOrder(Integer userId, Integer tattooId) throws ServiceException{
        try {
            OrderCrudDao orderDao = (OrderCrudDao) factory.createDao(Order.class);
            orderDao.save(new Order(userId, tattooId));
        } catch ( DaoException e) {
            LOGGER.error("Failed while tried to make order from user with id --> " + userId + " on tattoo with id --> " + tattooId);
            throw new ServiceException("Failed while tried to make order from user with id --> " + userId + " on tattoo with id --> " + tattooId);
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found");
            throw new ServiceException("Dao not found");
        }
    }

    public void changePassword(User user, String oldPassword, String newPassword) throws ServiceException, InvalidUserPasswordException {
        try {
            UserCrudDao userDao = (UserCrudDao) factory.createDao(User.class);
            if (UserValidator.isCorrectPassword(user, oldPassword + user.getLogin(), userDao)) {
                user.setPassword(PasswordEncryptor.encrypt(newPassword + user.getLogin()));
                userDao.update(user);
            }
        } catch (DaoNotFoundException e) {
            LOGGER.error("Dao not found");
            throw new ServiceException("Dao not found");
        } catch (DaoException | ValidationException e) {
            LOGGER.error("Failed while tried to change password for user --> " + user);
            throw new ServiceException("Failed while tried to change password for user --> " + user);
        }
    }

    private User initializeCard(User user, DiscountCardCrudDao cardDao) throws DaoException{
        if (user.getRole().equals(UserRole.CLIENT)) {
            ((Client)user).setCard(cardDao.findByUserId(user.getId()));
        }
        return user;
    }
}
