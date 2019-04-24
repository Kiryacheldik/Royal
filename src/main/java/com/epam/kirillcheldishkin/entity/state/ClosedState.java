package com.epam.kirillcheldishkin.entity.state;

import com.epam.kirillcheldishkin.entity.state.status.Status;

public class ClosedState extends State {

    public ClosedState() {
        super(Status.CLOSED);
    }

    @Override
    public State nextState() {
        return null;
    }
}
