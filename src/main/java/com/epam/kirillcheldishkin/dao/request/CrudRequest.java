package com.epam.kirillcheldishkin.dao.request;

import com.epam.kirillcheldishkin.dao.exception.DaoException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * getFindAllRequest() - returns sql request for finding all <T> entities.
 * getSaveRequest() - returns sql request for saving <T> entity.
 * getUpdateRequest() - returns sql request for updating <T> entity.
 * convertResultSetToEntityList() - Creates List and adds information from ResultSet.
 * prepareStatementForInsert() - adds information in PreparedStatement.
 * prepareStatementForUpdate() - adds information in PreparedStatement.
 * @param <T>
 */
public interface CrudRequest<T, ID> {
    String getFindAllRequest();
    String getSaveRequest();
    String getUpdateRequest();
    String getDeleteByIdRequest();
    String getFindByIdRequest();
    List<T> convertResultSetToEntityList(ResultSet resultSet) throws SQLException, DaoException;
    void prepareStatementForInsert(PreparedStatement statement, T t) throws SQLException;
    void prepareStatementForUpdate(PreparedStatement statement, T t) throws SQLException;
    void prepareStatementForDelete(PreparedStatement statement, ID id) throws SQLException;
}
