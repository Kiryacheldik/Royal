package com.epam.kirillcheldishkin.dao.implementation;

import com.epam.kirillcheldishkin.dao.AbstractCrudDao;
import com.epam.kirillcheldishkin.dao.AutoConnection;
import com.epam.kirillcheldishkin.dao.TattooImageCrudDao;
import com.epam.kirillcheldishkin.dao.exception.DaoException;
import com.epam.kirillcheldishkin.dao.exception.NoSuchImageTypeException;
import com.epam.kirillcheldishkin.dao.request.TattooImageRequest;
import com.epam.kirillcheldishkin.entity.TattooImage;
import com.epam.kirillcheldishkin.entity.imageType.ImageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TattooImageDao extends AbstractCrudDao<TattooImage, Integer> implements TattooImageCrudDao, TattooImageRequest {
    private static final Logger LOGGER = LogManager.getLogger(TattooImageDao.class);

    @Override
    @AutoConnection
    public List<TattooImage> findImagesByTattooId(Integer id) throws DaoException {
        List<TattooImage> imageIdList = new ArrayList<>();
        try(PreparedStatement statement = this.connection.prepareStatement(getFindImagesForCertainTattooRequest())) {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    imageIdList.add(initializeEntity(resultSet));
                }
            }
        } catch (SQLException | NoSuchImageTypeException e) {
            LOGGER.error("Failed while tried to find images for tattoo with id --> " + id);
            throw new DaoException("Failed while tried to find images for tattoo with id --> " + id);
        }
        return imageIdList;
    }

    @Override
    public String getFindImagesForCertainTattooRequest() {
        return "SELECT * FROM tattoo_image INNER JOIN image USING(image_id) INNER JOIN image_type USING(type_id) WHERE tattoo_id = ?";
    }//accepted

    @Override
    public String getFindAllRequest() {
        return "SELECT * FROM tattoo_image INNER JOIN image_type USING(type_id) INNER JOIN image USING(image_id)";
    }//accepted

    @Override
    public String getSaveRequest() {
        return "INSERT INTO tattoo_image(image_id, tattoo_id, type_id) VALUES(?, ?, ?)";
    }//accepted

    @Override
    public String getUpdateRequest() {
        return "UPDATE tattoo_image SET type_id = ?";
    }//accepted

    @Override
    public String getDeleteByIdRequest() {
        return "DELETE FROM tattoo_image WHERE tattoo_image_id = ?";
    }//accepted

    @Override
    public String getFindByIdRequest() {
        return "SELECT * FROM tattoo_image INNER JOIN image_type USING(type_id) INNER JOIN image USING(image_id) WHERE tattoo_image_id = ?";
    }

    @Override
    public List<TattooImage> convertResultSetToEntityList(ResultSet resultSet) throws SQLException, DaoException {
        List<TattooImage> images = new ArrayList<>();
        try {
            while (resultSet.next()) {
                images.add(initializeEntity(resultSet));
            }
            return images;
        } catch (NoSuchImageTypeException e) {
            LOGGER.error("Failed while tried to create list of tattoo images");
            throw new DaoException("Failed while tried to create list of tattoo images");
        }
    }

    @Override
    public void prepareStatementForInsert(PreparedStatement statement, TattooImage image) throws SQLException {
        int i = 1;
        statement.setLong(i++, image.getId());
        statement.setLong(i++, image.getTattooId());
        statement.setLong(i, image.getType().getId());
    }

    @Override
    public void prepareStatementForUpdate(PreparedStatement statement, TattooImage image) throws SQLException {
        statement.setLong(1, image.getType().getId());
    }

    @Override
    public void prepareStatementForDelete(PreparedStatement statement, Integer id) throws SQLException {
        statement.setLong(1, id);
    }

    @Override
    public TattooImage initializeEntity(ResultSet resultSet) throws SQLException, NoSuchImageTypeException {
        TattooImage image = new TattooImage(resultSet.getString("image"));
        image.setTattooImageId(resultSet.getInt("tattoo_image_id"));
        image.setId(resultSet.getInt("image_id"));
        image.setTattooId(resultSet.getInt("tattoo_id"));
        image.setType(selectType(resultSet.getString("type")));
        return image;
    }

    private ImageType selectType(String type) throws NoSuchImageTypeException{
        switch (type) {
            case "title":
                return ImageType.TITLE;
            case "ordinary":
                return ImageType.ORDINARY;
            case "sketch":
                return ImageType.SKETCH;
            default:
                LOGGER.error("This type (" + type + ") isn't registered");
                throw new NoSuchImageTypeException("This type (" + type + ") isn't registered");
        }
    }
}
