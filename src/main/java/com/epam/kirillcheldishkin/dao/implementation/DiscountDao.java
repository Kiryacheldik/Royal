package com.epam.kirillcheldishkin.dao.implementation;

import com.epam.kirillcheldishkin.dao.AbstractGenericDao;
import com.epam.kirillcheldishkin.dao.AutoConnection;
import com.epam.kirillcheldishkin.dao.DiscountGenericDao;
import com.epam.kirillcheldishkin.dao.exception.DaoException;
import com.epam.kirillcheldishkin.dao.request.DiscountGenericRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DiscountDao extends AbstractGenericDao implements DiscountGenericDao, DiscountGenericRequest {
    private static final Logger LOGGER = LogManager.getLogger(DiscountDao.class);

    @Override
    @AutoConnection
    public List<Integer> findAll() throws DaoException {
        try(PreparedStatement statement = this.connection.prepareStatement(getFindAllRequest())) {
            try(ResultSet resultSet = statement.executeQuery()) {
                List<Integer> discounts = new ArrayList<>();
                while (resultSet.next()) {
                    discounts.add(resultSet.getInt("discount"));
                }
                return discounts;
            }
        } catch (SQLException e) {
            LOGGER.error("Failed while tried to find all discounts from data base");
            throw new DaoException("Failed while tried to find all discounts");
        }
    }

        @Override
    public String getFindAllRequest() {
        return "SELECT discount FROM user_discount";
    }
}
