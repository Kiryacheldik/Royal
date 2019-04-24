package com.epam.kirillcheldishkin.entity;

import com.epam.kirillcheldishkin.entity.discount.DiscountType;

public class Discount {
    private DiscountType discount;

    public DiscountType getDiscount() {
        return discount;
    }

    public void setDiscount(DiscountType discount) {
        this.discount = discount;
    }

    public Discount(DiscountType discount) {
        this.discount = discount;
    }
}
