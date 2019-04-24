package com.epam.kirillcheldishkin.entity.discount;

public enum DiscountType {
    FIFTEEN(1, 15), TWENTY(2, 20), FIFTY(3, 50), SIXTY(4, 60), SEVENTY_FIVE(5, 75), EIGHTY(6, 80);

    private int id;
    private int discount;

    public int getId() {
        return id;
    }

    public int getDiscount() {
        return discount;
    }

    DiscountType(int id, int discount) {
        this.id = id;
        this.discount = discount;
    }
}
