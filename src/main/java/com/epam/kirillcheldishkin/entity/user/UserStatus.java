package com.epam.kirillcheldishkin.entity.user;

public enum UserStatus {
    ACTIVE(0, true), BLOCKED(1, false);

    private int id;
    private boolean status;

    public int getId() {
        return id;
    }

    public boolean isStatus() {
        return status;
    }

    UserStatus(int id, boolean status) {
        this.id = id;
        this.status = status;
    }
}
