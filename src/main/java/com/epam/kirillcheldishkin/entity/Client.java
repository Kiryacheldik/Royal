package com.epam.kirillcheldishkin.entity;

import com.epam.kirillcheldishkin.entity.user.UserStatus;
import com.epam.kirillcheldishkin.entity.user.UserRole;

public class Client extends User {
    private int rating;
    private DiscountCard card;
    private UserStatus activeStatus;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public DiscountCard getCard() {
        return card;
    }

    public void setCard(DiscountCard card) {
        this.card = card;
    }

    public UserStatus getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(UserStatus activeStatus) {
        this.activeStatus = activeStatus;
    }

    public Client(String email, String login, String password, String username, int rating) {
        super(email, login, password, username, UserRole.CLIENT);
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Client{" +
                super.toString() +
                ", rating=" + rating +
                ", card=" + card +
                '}';
    }
}
