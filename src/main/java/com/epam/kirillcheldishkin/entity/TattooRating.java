package com.epam.kirillcheldishkin.entity;

public class TattooRating {
    private Integer id;
    private int userId;
    private int tattooId;
    private int rating;

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

    public int getTattooId() {
        return tattooId;
    }

    public void setTattooId(int tattooId) {
        this.tattooId = tattooId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public TattooRating(int rating) {
        this.rating = rating;
    }
}
