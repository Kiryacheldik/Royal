package com.epam.kirillcheldishkin.dao.implementation;

import com.epam.kirillcheldishkin.dao.AbstractCrudDao;
import com.epam.kirillcheldishkin.dao.AutoConnection;
import com.epam.kirillcheldishkin.dao.TattooCrudDao;
import com.epam.kirillcheldishkin.dao.exception.DaoException;
import com.epam.kirillcheldishkin.dao.request.TattooCrudRequest;
import com.epam.kirillcheldishkin.entity.Tattoo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TattooDao extends AbstractCrudDao<Tattoo, Integer> implements TattooCrudRequest, TattooCrudDao {
    private static final Logger LOGGER = LogManager.getLogger(TattooDao.class);

    @Override
    @AutoConnection
    public List<Tattoo> findByNameLike(String name) throws DaoException{
        List<Tattoo> tattooList;
        try(PreparedStatement statement = this.connection.prepareStatement(getFindByNameLikeRequest())) {
            statement.setString(1, "%" + name + "%");
            try (ResultSet r = statement.executeQuery()) {
                tattooList = convertResultSetToEntityList(r);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed while tried to find tattoos which have '" + name + "' in their name");
            throw new DaoException("Failed while tried to find tattoos which have '" + name + "' in their name");
        }
        return tattooList;
    }

    @Override
    @AutoConnection
    public Tattoo save(Tattoo tattoo) throws DaoException {
        try(PreparedStatement statement = this.connection.prepareStatement(getSaveRequest(), Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForInsert(statement, tattoo);
            statement.executeUpdate();
            try(ResultSet result = statement.getGeneratedKeys()) {
                if (result.next()) {
                    tattoo.setId(result.getInt(1));
                } else {
                    LOGGER.error("Saving " + tattoo + " was incorrect");
                    throw new DaoException("Failed while getting user id");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Failed while tried to save tattoo --> " + tattoo);
            throw new DaoException("Failed while saving entity.");
        }
        return tattoo;
    }

    @Override
    public String getFindByNameLikeRequest() {
        return "SELECT tattoo_id, name FROM tattoo where name like ?";
    }//accepted

    @Override
    public String getFindByIdRequest() {
        return "SELECT tattoo_id, name FROM tattoo WHERE tattoo_id = ?";
    }//accepted

    @Override
    public String getFindAllRequest() {
        return "SELECT tattoo_id, name FROM tattoo";
    }//accepted

    @Override
    public String getSaveRequest() {
        return "INSERT INTO tattoo(name) VALUES (?)";
    }//accepted

    @Override
    public String getUpdateRequest() {
        return "UPDATE tattoo SET name = ? WHERE tattoo_id = ?";
    }//accepted

    @Override
    public String getDeleteByIdRequest() {
        return "DELETE FROM tattoo WHERE tattoo_id = ?";
    }//accepted


    @Override
    public List<Tattoo> convertResultSetToEntityList(ResultSet resultSet) throws SQLException {
        List<Tattoo> tattooList = new ArrayList<>();
        while (resultSet.next()) {
            tattooList.add(initializeEntity(resultSet));
        }
        return tattooList;
    }

    @Override
    public void prepareStatementForInsert(PreparedStatement statement, Tattoo tattoo) throws SQLException {
        statement.setString(1, tattoo.getName());
    }

    @Override
    public void prepareStatementForUpdate(PreparedStatement statement, Tattoo tattoo) throws SQLException {
        statement.setString(1, tattoo.getName());
        statement.setInt(2, tattoo.getId());
    }

    @Override
    public void prepareStatementForDelete(PreparedStatement statement, Integer id) throws SQLException {
        statement.setInt(1, id);
    }

    @Override
    public Tattoo initializeEntity(ResultSet resultSet) throws SQLException {
        Tattoo tattoo = new Tattoo(resultSet.getString("name"));
        tattoo.setId(resultSet.getInt("tattoo_id"));
        return tattoo;
    }
}
