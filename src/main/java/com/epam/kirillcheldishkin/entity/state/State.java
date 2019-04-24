package com.epam.kirillcheldishkin.entity.state;

import com.epam.kirillcheldishkin.entity.state.status.Status;

public abstract class State {
    private Status status;

    public State(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public abstract State nextState();
}
