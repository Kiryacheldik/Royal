package com.epam.kirillcheldishkin.dao.request;

import com.epam.kirillcheldishkin.entity.DiscountCard;

public interface DiscountCardCrudRequest extends CrudRequest<DiscountCard, Integer> {
    String getDiscountByIdRequest();
    String getFindByUserIdRequest();
    String getCardNumbersRequest();
}
