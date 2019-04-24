package com.epam.kirillcheldishkin.dao.implementation;

import com.epam.kirillcheldishkin.dao.AbstractCrudDao;
import com.epam.kirillcheldishkin.dao.AutoConnection;
import com.epam.kirillcheldishkin.dao.TattooRatingCrudDao;
import com.epam.kirillcheldishkin.dao.exception.DaoException;
import com.epam.kirillcheldishkin.dao.request.TattooRatingCrudRequest;
import com.epam.kirillcheldishkin.entity.TattooRating;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TattooRatingDao extends AbstractCrudDao<TattooRating, Integer> implements TattooRatingCrudRequest, TattooRatingCrudDao {
    private static final Logger LOGGER = LogManager.getLogger(TattooRatingDao.class);

    @Override
    @AutoConnection
    public List<Integer> findRatingsByTattooId(Integer id) throws DaoException{
        List<Integer> ratings = new ArrayList<>();
        try (PreparedStatement statement = this.connection.prepareStatement(getFindRatingsForCertainTattooRequest())) {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ratings.add(resultSet.getInt("rating"));
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Failed while tried to get list of ratings for tattoo with id --> " + id);
            throw new DaoException("Failed while found ratings");
        }
        return ratings;
    }

    @Override
    @AutoConnection
    public boolean findRatingByUserAndTattooId(Integer userId, Integer tattooId) throws DaoException {
        try(PreparedStatement statement = this.connection.prepareStatement(getFindRatingByUserAndTattooIdRequest())) {
            statement.setInt(1, userId);
            statement.setInt(2, tattooId);
            try(ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        } catch (SQLException e) {
            LOGGER.error("Failed while tried to find rating for tattoo with id --> " + tattooId + " from user with id --> " + userId);
            throw new DaoException("Failed while found rating");
        }
    }

    @Override
    public String getFindRatingByUserAndTattooIdRequest() {
        return "SELECT rating FROM tattoo_rating WHERE user_id = ? AND tattoo_id = ?";
    }

    @Override
    public String getFindRatingsForCertainTattooRequest() {
        return "SELECT rating FROM tattoo_rating WHERE tattoo_id = ?";
    }

    @Override
    public String getFindAllRequest() {
        return "SELECT rating_id, user_id, tattoo_id, rating FROM tattoo_rating";
    }//accepted

    @Override
    public String getSaveRequest() {
        return "INSERT INTO tattoo_rating(user_id, tattoo_id, rating) values (?, ?, ?)";
    }//accepted

    @Override
    public String getUpdateRequest() {
        return "UPDATE tattoo_rating SET rating = ? WHERE user_id = ? AND tattoo_id = ?";
    }//accepted

    @Override
    public String getDeleteByIdRequest() {
        return "DELETE FROM tattoo_rating WHERE rating_id = ?";
    }//accepted

    @Override
    public String getFindByIdRequest() {
        return "SELECT rating_id, user_id, tattoo_id, rating FROM tattoo_rating WHERE rating_id = ?";
    }//accepted

    @Override
    public List<TattooRating> convertResultSetToEntityList(ResultSet resultSet) throws SQLException, DaoException {
        List<TattooRating> ratings = new ArrayList<>();
        while (resultSet.next()) {
            ratings.add(initializeEntity(resultSet));
        }
        return ratings;
    }

    @Override
    public void prepareStatementForInsert(PreparedStatement statement, TattooRating rating) throws SQLException {
        int i = 1;
        statement.setInt(i++, rating.getUserId());
        statement.setInt(i++, rating.getTattooId());
        statement.setInt(i, rating.getRating());
    }

    @Override
    public void prepareStatementForUpdate(PreparedStatement statement, TattooRating rating) throws SQLException {
        int i = 1;
        statement.setInt(i++, rating.getRating());
        statement.setInt(i++, rating. getUserId());
        statement.setInt(i, rating.getTattooId());
    }

    @Override
    public void prepareStatementForDelete(PreparedStatement statement, Integer id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    public TattooRating initializeEntity(ResultSet resultSet) throws SQLException {
        TattooRating rating = new TattooRating(resultSet.getInt("rating"));
        rating.setId(resultSet.getInt("rating_id"));
        rating.setUserId(resultSet.getInt("user_id"));
        rating.setTattooId(resultSet.getInt("tattoo_id"));
        return rating;
    }
}
