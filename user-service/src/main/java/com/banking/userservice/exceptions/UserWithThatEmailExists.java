package com.banking.userservice.exceptions;

public class UserWithThatEmailExists extends Exception {
    public UserWithThatEmailExists(String message) {
        super(message);
    }
}
