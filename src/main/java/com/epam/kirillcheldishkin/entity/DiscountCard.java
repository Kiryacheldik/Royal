package com.epam.kirillcheldishkin.entity;

import java.util.Objects;

public class DiscountCard {
    private Integer id;
    private int userId;
    private Discount discount;
    private int cardNumber;
    private boolean activeStatus;

    public DiscountCard(int cardNumber, boolean activeStatus) {
        this.cardNumber = cardNumber;
        this.activeStatus = activeStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        DiscountCard that = (DiscountCard) obj;
        return cardNumber == that.cardNumber &&
                activeStatus == that.activeStatus &&
                discount.equals(that.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, activeStatus, discount);
    }
}
