package com.authMS.Auth.microsservice.infrastructure.exceptions;

public class JwtException extends RuntimeException{
    public JwtException(String message){
        super(message);
    }
}
