package com.epam.kirillcheldishkin.entity;

import java.util.ArrayList;
import java.util.List;

public class Tattoo {
    private Integer id;
    private String name;
    private double rating;
    private List<TattooImage> images = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public List<TattooImage> getImages() {
        return images;
    }

    public void setImages(List<TattooImage> images) {
        this.images = images;
    }

    public Tattoo(String name) {
        this.name = name;
    }

    public Tattoo() {
    }

    @Override
    public String toString() {
        return "Tattoo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rating=" + rating +
                ", images=" + images +
                '}';
    }
}
