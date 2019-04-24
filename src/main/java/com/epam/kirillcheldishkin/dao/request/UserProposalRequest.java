package com.epam.kirillcheldishkin.dao.request;

import com.epam.kirillcheldishkin.entity.UserProposal;

public interface UserProposalRequest extends CrudRequest<UserProposal, Integer>{
    String getFindProposalsByUserIdRequest();
}
