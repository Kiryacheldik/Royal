package com.epam.kirillcheldishkin.entity.state;

import com.epam.kirillcheldishkin.entity.state.status.Status;

public class AcceptedState extends State {
    private boolean accepted;

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public AcceptedState() {
        super(Status.ACCEPTED);
    }

    @Override
    public State nextState() {
        if (accepted) {
            return new AwaitingFeedBack();
        } else {
            return new ClosedState();
        }
    }
}
