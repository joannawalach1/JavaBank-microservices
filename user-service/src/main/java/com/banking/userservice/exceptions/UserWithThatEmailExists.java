package com.banking.userservice.exceptions;

public class UserWithThatEmailExists extends RuntimeException {
    public UserWithThatEmailExists(String message) {
        super(message);
    }
}
