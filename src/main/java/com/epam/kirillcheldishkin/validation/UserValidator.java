package com.epam.kirillcheldishkin.validation;

import com.epam.kirillcheldishkin.dao.UserCrudDao;
import com.epam.kirillcheldishkin.dao.exception.*;
import com.epam.kirillcheldishkin.entity.User;
import com.epam.kirillcheldishkin.util.PasswordEncryptor;
import com.epam.kirillcheldishkin.validation.exception.InvalidUserPasswordException;
import com.epam.kirillcheldishkin.validation.exception.LoginIsAlreadyUsedException;
import com.epam.kirillcheldishkin.validation.exception.ValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class UserValidator {
    private static final Logger LOGGER = LogManager.getLogger(UserValidator.class);
    private static final String LOGIN_PATTERN="^[\\w]+$";
    private static final String PASSWORD_PATTERN="^[\\w]+$";
    private static final String EMAIL_PATTERN="^[\\w\\.-]+@[_a-zA-Z]+\\.[a-z]{2,3}$";

    public UserValidator(){}

    public static boolean validate(User user){
        String login=user.getLogin();
        String email=user.getEmail();
        String password=user.getPassword();

        //if cases needed to be changed to the messages for user
        if(login.length()<1||!login.matches(LOGIN_PATTERN)){
            return false;
        }
        if(password.length()<4||!password.matches(PASSWORD_PATTERN)){
            return false;
        }
        if(email.length()<5||!email.matches(EMAIL_PATTERN)){
            return false;
        }
        return true;
    }

    public static boolean isCorrectPassword(User user, String password, UserCrudDao userDao) throws ValidationException, InvalidUserPasswordException {
        try {
            if (!userDao.findById(user.getId()).getPassword().equals(PasswordEncryptor.encrypt(password))) {
                LOGGER.error("Password --> " + password + " is incorrect for user --> " + user);
                throw new InvalidUserPasswordException("Password is incorrect");
            }
        } catch (DaoException | SQLException | NoSuchImageTypeException | NoSuchOrderStateException | NoSuchProposalStateException | DiscountNotFoundException e) {
            LOGGER.error("Failed while tried to get connection");
            throw new ValidationException("Failed while tried to get connection");
        }
        return true;
    }

    public static boolean isReservedLogin(String login, UserCrudDao userDao) throws ValidationException, LoginIsAlreadyUsedException {
        try {
            if (userDao.findByLogin(login) != null) {
                LOGGER.error("Login --> " + login + " is already used");
                throw new LoginIsAlreadyUsedException("Login is already in use");
            }
        } catch (DaoException e) {
            LOGGER.error("Failed while tried to get connection");
            throw new ValidationException("Failed while tried to get connection");
        }
        return true;
    }
}
