package com.epam.kirillcheldishkin.dao.implementation;

import com.epam.kirillcheldishkin.dao.AbstractCrudDao;
import com.epam.kirillcheldishkin.dao.AutoConnection;
import com.epam.kirillcheldishkin.dao.UserProposalCrudDao;
import com.epam.kirillcheldishkin.dao.exception.*;
import com.epam.kirillcheldishkin.dao.request.UserProposalRequest;
import com.epam.kirillcheldishkin.entity.UserProposal;
import com.epam.kirillcheldishkin.entity.state.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserProposalDao extends AbstractCrudDao<UserProposal, Integer> implements UserProposalCrudDao, UserProposalRequest {
    private static final Logger LOGGER = LogManager.getLogger(UserProposalDao.class);

    @Override
    public String getFindAllRequest() {
        return "SELECT * FROM user_proposal INNER JOIN status USING(status_id) INNER JOIN image USING(image_id)";
    }//accepted

    @Override
    public String getSaveRequest() {
        return "INSERT INTO user_proposal(image_id, user_id, date, status_id, rating) VALUES(?, ?, now(), 1, 0)";
    }//accepted

    @Override
    public String getUpdateRequest() {
        return "UPDATE user_proposal SET status_id = ?, rating = ? where proposal_id = ?";
    }//accepted

    @Override
    public String getDeleteByIdRequest() {
        return "DELETE FROM user_proposal WHERE proposal_id = ?";
    }//accepted

    @Override
    public String getFindByIdRequest() {
        return "SELECT * FROM user_proposal INNER JOIN status USING(status_id) INNER JOIN image USING(image_id) WHERE proposal_id = ?";
    }//accepted

    @Override
    public String getFindProposalsByUserIdRequest() {
        return "SELECT * FROM user_proposal INNER JOIN status USING(status_id) INNER JOIN image USING(image_id) WHERE user_id = ?";
    }

    @Override
    @AutoConnection
    public List<UserProposal> findProposalsByUserId(Integer userId) throws DaoException{
        try(PreparedStatement statement = this.connection.prepareStatement(getFindProposalsByUserIdRequest())) {
            statement.setInt(1, userId);
            try(ResultSet result = statement.executeQuery()) {
                return convertResultSetToEntityList(result);
            }
        } catch (SQLException e) {
            LOGGER.error("Failed while tried to find proposals for user with id -->" + userId);
            throw new DaoException("Failed while found all proposals.");
        }
    }

    @Override
    public List<UserProposal> convertResultSetToEntityList(ResultSet resultSet) throws SQLException, DaoException {
        List<UserProposal> proposals = new ArrayList<>();
        try {
            while (resultSet.next()) {
                proposals.add(initializeEntity(resultSet));
            }
            return proposals;
        } catch (NoSuchProposalStateException e) {
            LOGGER.error("Failed while tried to get list of user proposals");
            throw new DaoException("Failed while found proposals");
        }
    }

    @Override
    public void prepareStatementForInsert(PreparedStatement statement, UserProposal proposal) throws SQLException {
        int i = 1;
        statement.setInt(i++, proposal.getImage().getId());
        statement.setInt(i, proposal.getUser().getId());
    }

    @Override
    public void prepareStatementForUpdate(PreparedStatement statement, UserProposal proposal) throws SQLException {
        int i = 1;
        statement.setInt(i++, proposal.getState().getStatus().getId());
        statement.setInt(i++, proposal.getRating());
        statement.setInt(i, proposal.getId());
    }

    @Override
    public void prepareStatementForDelete(PreparedStatement statement, Integer id) throws SQLException {
        statement.setInt(1, id);
    }

    @Override
    public UserProposal initializeEntity(ResultSet resultSet) throws SQLException, NoSuchProposalStateException {
        UserProposal proposal = new UserProposal(selectState(resultSet.getString("status_name")));
        proposal.setId(resultSet.getInt("proposal_id"));
        proposal.setDate(resultSet.getDate("date").toString());
        proposal.getImage().setImage(resultSet.getString("image"));
        proposal.getImage().setId(resultSet.getInt("image_id"));
        proposal.getUser().setId(resultSet.getInt("user_id"));
        proposal.setRating(resultSet.getInt("rating"));
        return proposal;
    }

    private State selectState(String status) throws NoSuchProposalStateException {
        switch (status) {
            case "submitted":
                return new SubmittedState();
            case "accepted":
                return new AcceptedState();
            case "cancelled":
                return new CancelledState();
            case "closed":
                return new ClosedState();
            default:
                LOGGER.error("There is no such state status (" + status +  ") for proposal");
                throw new NoSuchProposalStateException("There is no such state");
        }
    }
}
