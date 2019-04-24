package com.epam.kirillcheldishkin.entity;

import com.epam.kirillcheldishkin.entity.imageType.ImageType;

public class TattooImage extends Image {
    private Integer tattooImageId;
    private int tattooId;
    private ImageType type;

    public Integer getTattooImageId() {
        return tattooImageId;
    }

    public void setTattooImageId(Integer tattooImageId) {
        this.tattooImageId = tattooImageId;
    }

    public int getTattooId() {
        return tattooId;
    }

    public void setTattooId(int tattooId) {
        this.tattooId = tattooId;
    }

    public ImageType getType() {
        return type;
    }

    public void setType(ImageType type) {
        this.type = type;
    }

    public TattooImage(String image) {
        super(image);
    }

    public TattooImage() {
    }
}
