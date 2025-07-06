package com.authMS.Auth.microsservice.infrastructure.exceptions;

public class UserNotFound extends RuntimeException {
    public UserNotFound(String message) {
        super(message);
    }
}
