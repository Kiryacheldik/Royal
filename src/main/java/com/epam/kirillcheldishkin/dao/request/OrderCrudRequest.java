package com.epam.kirillcheldishkin.dao.request;

import com.epam.kirillcheldishkin.entity.Order;

public interface OrderCrudRequest extends CrudRequest<Order, Integer> {
    String getFindOrdersByUserIdRequest();
}
