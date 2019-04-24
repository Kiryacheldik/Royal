package com.epam.kirillcheldishkin.entity.state;

import com.epam.kirillcheldishkin.entity.state.status.Status;

public class SubmittedState extends State {
    private boolean accepted;

    public SubmittedState() {
        super(Status.SUBMITTED);
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public State nextState() {
        if (accepted) {
            return new AcceptedState();
        } else {
            return new CancelledState();
        }
    }
}
