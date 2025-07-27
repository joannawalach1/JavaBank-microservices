package com.banking.userservice.exceptions;

public class UserNotFound extends Exception {
    public UserNotFound(String message) {
        super(message);
    }
}
