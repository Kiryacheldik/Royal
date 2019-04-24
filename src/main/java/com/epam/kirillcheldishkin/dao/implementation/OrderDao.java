package com.epam.kirillcheldishkin.dao.implementation;

import com.epam.kirillcheldishkin.dao.AbstractCrudDao;
import com.epam.kirillcheldishkin.dao.AutoConnection;
import com.epam.kirillcheldishkin.dao.OrderCrudDao;
import com.epam.kirillcheldishkin.dao.exception.DaoException;
import com.epam.kirillcheldishkin.dao.exception.NoSuchOrderStateException;
import com.epam.kirillcheldishkin.dao.request.OrderCrudRequest;
import com.epam.kirillcheldishkin.entity.Order;
import com.epam.kirillcheldishkin.entity.state.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderDao extends AbstractCrudDao<Order, Integer> implements OrderCrudRequest, OrderCrudDao {
    private static final Logger LOGGER = LogManager.getLogger(OrderDao.class);

    @Override
    @AutoConnection
    public Order save(Order order) throws DaoException {
        try(PreparedStatement statement = this.connection.prepareStatement(getSaveRequest(), Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForInsert(statement, order);
            statement.executeUpdate();
            try(ResultSet result = statement.getGeneratedKeys()) {
                if (result.next()) {
                    order.setId(result.getInt(1));
                } else {
                    LOGGER.error("Saving " + order + " was incorrect");
                    throw new DaoException("Failed while getting user id");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Failed while tried to save order --> " + order);
            throw new DaoException("Failed while saving entity.");
        }
        return order;
    }

    @Override
    @AutoConnection
    public List<Order> findOrderListByUserId(Integer id) throws DaoException {
        try(PreparedStatement statement = this.connection.prepareStatement(getFindOrdersByUserIdRequest())) {
            statement.setInt(1, id);
            try(ResultSet result = statement.executeQuery()) {
                return convertResultSetToEntityList(result);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed while tried to find orders for user with id --> " + id);
            throw new DaoException("Failed while tried to find orders for user with id --> " + id);
        }
    }

    @Override
    public String getFindAllRequest() {
            return "SELECT * FROM user_order INNER JOIN status USING(status_id)";
    }//accepted

    @Override
    public String getSaveRequest() {
        return "INSERT INTO user_order(user_id, tattoo_id, rating, date, status_id) values (?, ?, 0, now(), 1)";//accepted
    }

    @Override
    public String getUpdateRequest() {
        return "UPDATE user_order SET rating = ?, status_id = ? WHERE order_id = ?";
    }//accepted

    @Override
    public String getDeleteByIdRequest() {
        return "DELETE FROM user_order WHERE order_id = ?";
    }//accepted

    @Override
    public String getFindByIdRequest() {
        return "SELECT * FROM user_order INNER JOIN status USING(status_id) WHERE order_id = ?";
    }

    @Override
    public String getFindOrdersByUserIdRequest() {
        return "SELECT * FROM user_order INNER JOIN status USING(status_id) WHERE user_id = ?";
    }

    @Override
    public List<Order> convertResultSetToEntityList(ResultSet resultSet) throws SQLException , DaoException{
        List<Order> orderList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                orderList.add(initializeEntity(resultSet));
            }
        } catch (NoSuchOrderStateException e) {
            LOGGER.error("Failed while tried to create list of orders");
            throw new DaoException("Failed while tried to create list of orders");
        }
        return orderList;
    }

    @Override
    public void prepareStatementForInsert(PreparedStatement statement, Order order) throws SQLException {
        int i = 1;
        statement.setLong(i++, order.getUser().getId());
        statement.setLong(i, order.getTattoo().getId());
    }

    @Override
    public void prepareStatementForUpdate(PreparedStatement statement, Order order) throws SQLException {
        int i = 1;
        statement.setInt(i++, order.getRating());
        statement.setLong(i++, order.getState().getStatus().getId());
        statement.setLong(i, order.getId());
    }

    @Override
    public void prepareStatementForDelete(PreparedStatement statement, Integer id) throws SQLException {
        int i = 1;
        statement.setLong(i, id);
    }

    @Override
    public Order initializeEntity(ResultSet r) throws SQLException, NoSuchOrderStateException {
        Order order = new Order(r.getInt("user_id"), r.getInt("tattoo_id"));
        order.setId(r.getInt("order_id"));
        order.setRating(r.getInt("rating"));
        order.setDate(r.getString("date"));
        order.setState(selectState(r.getString("status_name")));
        return order;
    }

    private State selectState(String status) throws NoSuchOrderStateException {
        switch (status) {
            case "submitted":
                return new SubmittedState();
            case "accepted":
                return new AcceptedState();
            case "cancelled":
                return new CancelledState();
            case "awaitingFeedBack":
                return new AwaitingFeedBack();
            case "closed":
                return new ClosedState();
            default:
                LOGGER.error("This status(" + status + ") isn't registered");
                throw new NoSuchOrderStateException("There is no such state");
        }
    }
}
