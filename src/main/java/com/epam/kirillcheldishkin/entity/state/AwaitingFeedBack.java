package com.epam.kirillcheldishkin.entity.state;

import com.epam.kirillcheldishkin.entity.state.status.Status;

public class AwaitingFeedBack extends State {

    public AwaitingFeedBack() {
        super(Status.AWAITING_FEED_BACK);
    }

    @Override
    public State nextState() {
        return new ClosedState();
    }
}
