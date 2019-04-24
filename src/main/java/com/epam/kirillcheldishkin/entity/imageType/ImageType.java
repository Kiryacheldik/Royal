package com.epam.kirillcheldishkin.entity.imageType;

public enum  ImageType {
   TITLE(1, "title"), ORDINARY(2, "ordinary"), SKETCH(3, "sketch");
   private int id;
   private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    ImageType(int id, String type) {
        this.id = id;
        this.type = type;
    }
}
