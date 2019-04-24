package com.epam.kirillcheldishkin.entity.state.status;

public enum Status {
    SUBMITTED(1, "submitted"), ACCEPTED(2, "accepted"), CANCELLED(3, "cancelled"), AWAITING_FEED_BACK(4, "awaitingFeedBack"), CLOSED(5, "closed");

    private int id;
    private String statusName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    Status(int id, String status) {
        this.id = id;
        this.statusName = status;
    }
}
