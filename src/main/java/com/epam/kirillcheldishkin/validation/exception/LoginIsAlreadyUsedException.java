package com.epam.kirillcheldishkin.validation.exception;

public class LoginIsAlreadyUsedException extends Exception {
    public LoginIsAlreadyUsedException(String message) {
        super(message);
    }
}
