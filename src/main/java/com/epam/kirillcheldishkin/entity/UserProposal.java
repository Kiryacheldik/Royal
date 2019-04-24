package com.epam.kirillcheldishkin.entity;

import com.epam.kirillcheldishkin.entity.state.State;

public class UserProposal {
    private Integer id;
    private User user;
    private State state;
    private Image image;
    private int rating;
    private String date;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public UserProposal(State state) {
        this.state = state;
        this.user = new User();
        this.image = new Image();
    }
}
