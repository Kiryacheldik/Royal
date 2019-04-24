package com.epam.kirillcheldishkin.util;

import com.epam.kirillcheldishkin.service.DiscountCardService;
import com.epam.kirillcheldishkin.service.exception.ServiceException;
import com.epam.kirillcheldishkin.service.factory.ServiceFactory;

public class DiscountCardNumber {
    private static final DiscountCardNumber DISCOUNT_CARD_NUMBER = new DiscountCardNumber();
    private ServiceFactory factory = ServiceFactory.getInstance();
    private static int number;

    public static DiscountCardNumber getInstance() {
        return DISCOUNT_CARD_NUMBER;
    }

    private DiscountCardNumber() {}

    public void initialize() throws ServiceException {
        DiscountCardService cardService = factory.getDiscountCardService();
        number = cardService.findLastCardNumber();
    }

    public int getNumber() {
        return ++number;
    }
}
