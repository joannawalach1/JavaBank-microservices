package com.banking.userservice.exceptions;

public class InvalidLoginData extends Throwable {
    public InvalidLoginData(String message) {
        super(message);
    }
}
