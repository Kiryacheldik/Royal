package com.epam.kirillcheldishkin.dao.implementation;

import com.epam.kirillcheldishkin.dao.AbstractCrudDao;
import com.epam.kirillcheldishkin.dao.AutoConnection;
import com.epam.kirillcheldishkin.dao.DiscountCardCrudDao;
import com.epam.kirillcheldishkin.dao.exception.DaoException;
import com.epam.kirillcheldishkin.dao.exception.DiscountNotFoundException;
import com.epam.kirillcheldishkin.dao.request.DiscountCardCrudRequest;
import com.epam.kirillcheldishkin.entity.DiscountCard;
import com.epam.kirillcheldishkin.entity.Discount;
import com.epam.kirillcheldishkin.entity.discount.DiscountType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DiscountCardDao extends AbstractCrudDao<DiscountCard, Integer> implements DiscountCardCrudRequest, DiscountCardCrudDao {
    private static final Logger LOGGER = LogManager.getLogger(DiscountCardDao.class);

    @Override
    @AutoConnection
    public int findDiscountById(Integer id) throws DaoException{
        try(PreparedStatement statement = this.connection.prepareStatement(getDiscountByIdRequest())) {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("discount");
                }
            }
        } catch (SQLException e) {
            LOGGER.error("Failed while tried to find discount with id --> " + id);
            throw new DaoException("Failed while tried to find discount with id --> " + id);
        }
        return -1;
    }

    @Override
    @AutoConnection
    public List<Integer> findCardNumbers() throws DaoException {
        try(PreparedStatement statement = this.connection.prepareStatement(getCardNumbersRequest())) {
            try(ResultSet resultSet = statement.executeQuery()) {
                List<Integer> numbers = new ArrayList<>();
                while (resultSet.next()) {
                    numbers.add(resultSet.getInt("card_number"));
                }
                return numbers;
            }
        } catch (SQLException e) {
            LOGGER.error("Failed while tried to find all card numbers");
            throw new DaoException("Failed while tried to find all card numbers");
        }
    }

    @Override
    @AutoConnection
    public DiscountCard findByUserId(Integer id) throws DaoException {
        try(PreparedStatement statement = this.connection.prepareStatement(getFindByUserIdRequest())) {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    DiscountCard card = initializeEntity(resultSet);
                    if (card.isActiveStatus()) {
                        return card;
                    }
                }
                return null;
            }
        } catch (SQLException | DiscountNotFoundException e) {
            LOGGER.error("Failed while tried to find valid card for user with id --> " + id);
            throw new DaoException("Failed while tried to find valid card for user with id --> " + id);
        }
    }

    @Override
    @AutoConnection
    public DiscountCard save(DiscountCard card, int discount) throws DaoException {
        try(PreparedStatement statement = this.connection.prepareStatement(getSaveRequest(), Statement.RETURN_GENERATED_KEYS)) {
            card.setDiscount(selectDiscount(discount));
            prepareStatementForInsert(statement, card);
            statement.executeUpdate();
            try(ResultSet result = statement.getGeneratedKeys()) {
                if (result.next()) {
                    card.setId(result.getInt(1));
                } else {
                    LOGGER.error("Saving " + card + " was incorrect");
                    throw new DaoException("Failed while getting card id");
                }
            }
        } catch (SQLException | DiscountNotFoundException e) {
            LOGGER.error("Failed while tried to save card --> " + card);
            throw new DaoException("Failed while saving entity.");
        }
        return card;
    }

    @Override
    public String getCardNumbersRequest() {
        return "SELECT card_number FROM discount_card";
    }

    @Override
    public String getFindByUserIdRequest() {
        return "SELECT * FROM discount_card INNER JOIN user_discount USING(discount_id) WHERE user_id = ?";
    }

    @Override
    public String getDiscountByIdRequest() {
        return "SELECT * FROM discount_card INNER JOIN user_discount USING(discount_id) WHERE card_id = ?";
    }//accepted

    @Override
    public String getFindAllRequest() {
        return "SELECT * FROM discount_card INNER JOIN user_discount USING(discount_id)";
    }

    @Override
    public String getSaveRequest() {
        return "INSERT INTO discount_card(user_id, card_number, active_status, discount_id) VALUES (?, ?, 0, ?)";
    }//accepted

    @Override
    public String getUpdateRequest() {
        return "UPDATE discount_card SET active_status = ?, discount_id = ? WHERE card_id = ?";
    }//accepted

    @Override
    public String getDeleteByIdRequest() {
        return "DELETE FROM discount_card WHERE card_id = ?";
    }//accepted

    @Override
    public String getFindByIdRequest() {
        return "SELECT * FROM discount_card INNER JOIN user_discount USING(discount_id) WHERE card_id = ?";
    }//accepted

    @Override
    @AutoConnection
    public DiscountCard update(DiscountCard card, int discount) throws DaoException {
        try(PreparedStatement statement = this.connection.prepareStatement(getUpdateRequest())) {
            card.setDiscount(selectDiscount(discount));
            prepareStatementForUpdate(statement, card);
            statement.executeUpdate();
        } catch (SQLException | DiscountNotFoundException e) {
            LOGGER.error("Failed while tried to update discount(" + discount +") in card --> " + card);
            throw new DaoException("Failed while tried to update discount(" + discount +") in card --> " + card);
        }
        return card;
    }

    @Override
    public List<DiscountCard> convertResultSetToEntityList(ResultSet resultSet) throws SQLException, DaoException {
        List<DiscountCard> cardList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                cardList.add(initializeEntity(resultSet));
            }
        } catch (DiscountNotFoundException e) {
            LOGGER.error("Failed while tried to create list of cards");
            throw new DaoException("Failed while tried to create list of cards");
        }
        return cardList;
    }

    @Override
    public void prepareStatementForInsert(PreparedStatement statement, DiscountCard card) throws SQLException {
        int i = 1;
        statement.setInt(i++, card.getUserId());
        statement.setInt(i++, card.getCardNumber());
        statement.setInt(i, card.getDiscount().getDiscount().getId());
    }

    @Override
    public void prepareStatementForUpdate(PreparedStatement statement, DiscountCard card) throws SQLException {
        int i = 1;
        if (card.isActiveStatus()) {
            statement.setInt(i++, 0);
        } else {
            statement.setInt(i++, 1);
        }
        statement.setInt(i++, card.getDiscount().getDiscount().getId());
        statement.setInt(i, card.getId());
    }

    @Override
    public void prepareStatementForDelete(PreparedStatement statement, Integer id) throws SQLException {
        statement.setInt(1, id);
    }

    @Override
    public DiscountCard initializeEntity(ResultSet r) throws SQLException, DiscountNotFoundException {
        DiscountCard card;
        if (r.getInt("active_status") == 0) {
            card = new DiscountCard(r.getInt("card_number"), true);
        } else {
            card = new DiscountCard(r.getInt("card_number"), false);
        }
        card.setDiscount(selectDiscount(r.getInt("discount")));
        card.setId(r.getInt("card_id"));
        card.setUserId(r.getInt("user_id"));
        return card;
    }

    private Discount selectDiscount(int discount) throws DiscountNotFoundException{
        switch (discount) {
            case 15:
                return new Discount(DiscountType.FIFTEEN);
            case 20:
                return new Discount(DiscountType.TWENTY);
            case 50:
                return new Discount(DiscountType.FIFTY);
            case 60:
                return new Discount(DiscountType.SIXTY);
            case 75:
                return new Discount(DiscountType.SEVENTY_FIVE);
            case 80:
                return new Discount(DiscountType.EIGHTY);
            default:
                LOGGER.error("This discount(" + discount + ") isn't registered");
                throw new DiscountNotFoundException("This discount(" + discount + ") isn't registered");
        }
    }
}
