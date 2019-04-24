package com.epam.kirillcheldishkin.connectionPool.exception;

public class ConnectionNotClosedException extends Exception {
    public ConnectionNotClosedException(String message) {
        super(message);
    }
}
