package com.epam.kirillcheldishkin.entity.state;

import com.epam.kirillcheldishkin.entity.state.status.Status;

public class CancelledState extends State {
    public CancelledState() {
        super(Status.CANCELLED);
    }

    @Override
    public State nextState() {
        return null;
    }
}
