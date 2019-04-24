package com.epam.kirillcheldishkin.dao.implementation;

import com.epam.kirillcheldishkin.dao.AbstractCrudDao;
import com.epam.kirillcheldishkin.dao.AutoConnection;
import com.epam.kirillcheldishkin.dao.UserCrudDao;
import com.epam.kirillcheldishkin.dao.exception.DaoException;
import com.epam.kirillcheldishkin.dao.request.UserCrudRequest;
import com.epam.kirillcheldishkin.entity.Admin;
import com.epam.kirillcheldishkin.entity.Client;
import com.epam.kirillcheldishkin.entity.User;
import com.epam.kirillcheldishkin.entity.user.UserStatus;
import com.epam.kirillcheldishkin.entity.user.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.util.logging.resources.logging;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends AbstractCrudDao<User, Integer> implements UserCrudRequest, UserCrudDao {
    private static final Logger LOGGER = LogManager.getLogger(UserProposalDao.class);

    @Override
    @AutoConnection
    public User identifyUser(String login, String password) throws DaoException {
        try(PreparedStatement statement = this.connection.prepareStatement(getIdentifyItemRequest())) {
            statement.setString(1, login);
            statement.setString(2, password);
            try(ResultSet r = statement.executeQuery()) {
                if (r.next()) {
                    return initializeEntity(r);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Failed while tried to find user with login --> " + login + " and encrypted password --> " + password);
            throw new DaoException("Failed while identified user.");
        }
    }

    @Override
    @AutoConnection
    public User findByLogin(String login) throws DaoException {
        try(PreparedStatement statement = this.connection.prepareStatement(getFindByLoginRequest())) {
            statement.setString(1, login);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return initializeEntity(resultSet);
                }
                else {
                    return null;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Failed while tried to find user with login --> " + login);
            throw new DaoException("Failed while tried to find user with login - " + login);
        }
    }

    @Override
    @AutoConnection
    public Client updateRating(Client client) throws DaoException {
        try(PreparedStatement statement = this.connection.prepareStatement(getUpdateRatingRequest())) {
            statement.setInt(1, client.getRating());
            statement.setInt(2, client.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Failed while tried to update rating for client with id --> " + client.getId());
            throw new DaoException("Failed while tried to update user.");
        }
        return client;
    }

    @Override
    @AutoConnection
    public User save(User user) throws DaoException {
        try(PreparedStatement statement = this.connection.prepareStatement(getSaveRequest(), Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForInsert(statement, user);
            statement.executeUpdate();
            try(ResultSet result = statement.getGeneratedKeys()) {
                if (result.next()) {
                    user.setId(result.getInt(1));
                } else {
                    LOGGER.error("Saving " + user + " was incorrect");
                    throw new DaoException("Failed while getting user id");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Failed while tried to save user --> " + user);
            throw new DaoException("Failed while tried to save user.");
        }
        return user;
    }

    @Override
    @AutoConnection
    public Client updateActiveStatus(Client client) throws DaoException {
        try(PreparedStatement statement = this.connection.prepareStatement(getUpdateActiveStatusRequest())) {
            statement.setInt(1, client.getActiveStatus().getId());
            statement.setInt(2, client.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Failed while tried to update activeStatus for client with id --> " + client.getId());
            throw new DaoException("Failed while tried to update client.");
        }
        return client;
    }

    @Override
    public String getUpdateActiveStatusRequest() {
        return "UPDATE user SET user_status = ? WHERE user_id = ?";
    }

    @Override
    public String getFindByLoginRequest() {
        return "SELECT * FROM user INNER JOIN user_role USING(role_id) WHERE login = ?";
    }//accepted

    @Override
    public String getFindByIdRequest() {
        return "SELECT * FROM user INNER JOIN user_role USING(role_id) WHERE user_id = ?";
    }//accepted

    @Override
    public String getIdentifyItemRequest() {
        return "SELECT * FROM user INNER JOIN user_role USING(role_id) WHERE login = ? AND password = ?";
    }//accepted

    @Override
    public String getFindAllRequest() {
        return "SELECT * FROM user INNER JOIN user_role USING(role_id)";
    }//accepted

    @Override
    public String getSaveRequest() {
        return "INSERT INTO user (email, login, password, username, user_status, rating, role_id) VALUES (?, ?, ?, ?, 0, 0, 2)";
    }//accepted

    @Override
    public String getUpdateRequest() {
        return "UPDATE user SET email = ?, login = ?, password = ?, username = ? WHERE user_id = ?";
    }//accepted

    @Override
    public String getUpdateRatingRequest() {
        return "UPDATE user SET rating = ? WHERE user_id = ?";
    }//accepted

    @Override
    public String getDeleteByIdRequest() {
        return "DELETE FROM user WHERE user_id = ?";
    }//accepted

    @Override
    public List<User> convertResultSetToEntityList(ResultSet resultSet) throws SQLException, DaoException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(initializeEntity(resultSet));
        }
        return users;
    }

    @Override
    public void prepareStatementForInsert(PreparedStatement statement, User user) throws SQLException {
        int i = 1;
        statement.setString(i++, user.getEmail());
        statement.setString(i++, user.getLogin());
        statement.setString(i++, user.getPassword());
        statement.setString(i, user.getUsername());
    }

    @Override
    public void prepareStatementForUpdate(PreparedStatement statement, User user) throws SQLException {
        int i = 1;
        statement.setString(i++, user.getEmail());
        statement.setString(i++, user.getLogin());
        statement.setString(i++, user.getPassword());
        statement.setString(i++, user.getUsername());
        statement.setInt(i, user.getId());
    }

    @Override
    public void prepareStatementForDelete(PreparedStatement statement, Integer id) throws SQLException {
        statement.setInt(1, id);
    }

    @Override
    public User initializeEntity(ResultSet r) throws SQLException, DaoException {
        switch (r.getString("role")) {
            case "client":
                Client client = new Client(r.getString("email"), r.getString("login"), r.getString("password"), r.getString("username"), r.getInt("rating"));
                client.setId(r.getInt("user_id"));
                if (r.getInt("user_status") == 0) {
                    client.setActiveStatus(UserStatus.ACTIVE);
                } else client.setActiveStatus(UserStatus.BLOCKED);
                return client;
            case "admin":
                Admin admin = new Admin(r.getString("email"), r.getString("login"), r.getString("password"), r.getString("username"));
                admin.setId(r.getInt("user_id"));
                return admin;
            default:
                //logger = message("this role isn't registered." + r.getString("role"))
                LOGGER.error("This role(" + r.getString("role") + ") isn't registered.");
                throw new DaoException("This role(" + r.getString("role") + ") isn't registered.");
        }
    }

}
