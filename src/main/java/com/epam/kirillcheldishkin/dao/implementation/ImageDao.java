package com.epam.kirillcheldishkin.dao.implementation;

import com.epam.kirillcheldishkin.dao.AbstractCrudDao;
import com.epam.kirillcheldishkin.dao.AutoConnection;
import com.epam.kirillcheldishkin.dao.ImageCrudDao;
import com.epam.kirillcheldishkin.dao.exception.DaoException;
import com.epam.kirillcheldishkin.dao.request.ImageCrudRequest;
import com.epam.kirillcheldishkin.entity.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImageDao extends AbstractCrudDao<Image, Integer> implements ImageCrudRequest, ImageCrudDao {
    private static final Logger LOGGER = LogManager.getLogger(ImageDao.class);

    @Override
    @AutoConnection
    public Image save(Image image) throws DaoException {
        try(PreparedStatement statement = this.connection.prepareStatement(getSaveRequest(), Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForInsert(statement, image);
            statement.executeUpdate();
            try(ResultSet result = statement.getGeneratedKeys()) {
                if (result.next()) {
                    image.setId(result.getInt(1));
                } else {
                    LOGGER.error("Saving " + image + " was incorrect");
                    throw new DaoException("Failed while tried to save " + image);
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Failed while tried to save image --> " + image);
            throw new DaoException("Failed while saving entity.");
        }
        return image;
    }

    @Override
    public String getFindAllRequest() {
        return "SELECT image_id, image FROM image";
    }//accepted

    @Override
    public String getSaveRequest() {
        return "INSERT INTO image(image) VALUES(?)";
    }//accepted

    @Override
    public String getUpdateRequest() {
        return "UPDATE image SET image = ? WHERE image_id = ?";
    }//accepted

    @Override
    public String getDeleteByIdRequest() {
        return "DELETE FROM image WHERE image_id = ?";
    }//accepted

    @Override
    public String getFindByIdRequest() {
        return "SELECT image_id, image FROM image WHERE image_id = ?";
    }//accepted

    @Override
    public List<Image> convertResultSetToEntityList(ResultSet resultSet) throws SQLException, DaoException {
        List<Image> images = new ArrayList<>();
        while (resultSet.next()) {
            images.add(initializeEntity(resultSet));
        }
        return images;
    }

    @Override
    public void prepareStatementForInsert(PreparedStatement statement, Image image) throws SQLException {
        statement.setString(1, image.getImage());
    }

    @Override
    public void prepareStatementForUpdate(PreparedStatement statement, Image image) throws SQLException {
        statement.setString(1, image.getImage());
        statement.setLong(2, image.getId());
    }

    @Override
    public void prepareStatementForDelete(PreparedStatement statement, Integer id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    public Image initializeEntity(ResultSet resultSet) throws SQLException {
        Image image = new Image(resultSet.getString("image"));
        image.setId(resultSet.getInt("image_id"));
        return image;
    }
}
