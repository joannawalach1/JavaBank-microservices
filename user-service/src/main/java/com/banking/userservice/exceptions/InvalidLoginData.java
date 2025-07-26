package com.banking.userservice.exceptions;

public class InvalidLoginData extends Exception {
    public InvalidLoginData(String message) {
        super(message);
    }
}
