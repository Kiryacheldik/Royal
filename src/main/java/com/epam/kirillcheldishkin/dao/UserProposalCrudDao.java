package com.epam.kirillcheldishkin.dao;

import com.epam.kirillcheldishkin.dao.exception.DaoException;
import com.epam.kirillcheldishkin.entity.UserProposal;

import java.util.List;


public interface UserProposalCrudDao extends CrudDao<UserProposal, Integer> {
    List<UserProposal> findProposalsByUserId(Integer userId) throws DaoException;
}
