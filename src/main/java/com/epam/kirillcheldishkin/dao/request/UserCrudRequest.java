package com.epam.kirillcheldishkin.dao.request;

import com.epam.kirillcheldishkin.entity.User;

public interface UserCrudRequest extends CrudRequest<User, Integer> {
    String getIdentifyItemRequest();
    String getFindByLoginRequest();
    String getUpdateRatingRequest();
    String getUpdateActiveStatusRequest();
}
