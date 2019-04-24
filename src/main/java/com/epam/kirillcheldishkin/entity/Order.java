package com.epam.kirillcheldishkin.entity;

import com.epam.kirillcheldishkin.entity.state.State;

public class Order {
    private Integer id;
    private User user;
    private Tattoo tattoo;
    private int rating;
    private String date;
    private State state;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tattoo getTattoo() {
        return tattoo;
    }

    public void setTattoo(Tattoo tattoo) {
        this.tattoo = tattoo;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Order(int user_id, int tattoo_id) {
        this.user = new User();
        this.user.setId(user_id);
        this.tattoo = new Tattoo();
        this.tattoo.setId(tattoo_id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "user=" + user +
                ", tattoo=" + tattoo +
                ", rating=" + rating +
                ", date='" + date + '\'' +
                '}';
    }
}
