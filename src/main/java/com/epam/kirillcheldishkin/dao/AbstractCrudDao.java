package com.epam.kirillcheldishkin.dao;

import com.epam.kirillcheldishkin.dao.exception.*;
import com.epam.kirillcheldishkin.dao.request.CrudRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractCrudDao<T, ID extends Number> extends AbstractGenericDao implements CrudDao<T, ID>, CrudRequest<T, ID> {
    private static final Logger LOGGER = LogManager.getLogger(AbstractCrudDao.class);

    @Override
    @AutoConnection
    public List<T> findAll() throws DaoException {
        try(PreparedStatement statement = this.connection.prepareStatement(getFindAllRequest())) {
            try(ResultSet result = statement.executeQuery()) {
                return convertResultSetToEntityList(result);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed while tried to find all entities");
            throw new DaoException("Failed while found all entities.");
        }
    }

    @Override
    @AutoConnection
    public T findById(ID id) throws DaoException, NoSuchOrderStateException, NoSuchImageTypeException, NoSuchProposalStateException, DiscountNotFoundException {
        try(PreparedStatement statement = this.connection.prepareStatement(getFindByIdRequest())) {
            statement.setLong(1, id.intValue());
            try(ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return initializeEntity(result);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Failed while tried to find entity with id --> " + id);
            throw new DaoException("Failed while found entity.");
        }
    }

    @Override
    @AutoConnection
    public T save(T t) throws DaoException {
        try(PreparedStatement statement = this.connection.prepareStatement(getSaveRequest())) {
            prepareStatementForInsert(statement, t);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Failed while tried to save --> " + t);
            throw new DaoException("Failed while saved entity.");
        }
        return t;
    }

    @Override
    @AutoConnection
    public T update(T t) throws DaoException {
        try(PreparedStatement statement = this.connection.prepareStatement(getUpdateRequest())) {
            prepareStatementForUpdate(statement, t);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Failed while tried to update --> " + t);
            throw new DaoException("Failed while updated entity.");
        }
        return t;
    }

    @Override
    @AutoConnection
    public void deleteById(ID id) throws DaoException {
        try(PreparedStatement statement = connection.prepareStatement(getDeleteByIdRequest())) {
            prepareStatementForDelete(statement, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Failed while tried to delete entity with id --> " + id);
            throw new DaoException("Failed while deleted entity.");
        }
    }
}
