package com.authMS.Auth.microsservice.infrastructure.exceptionsHandler;

import com.authMS.Auth.microsservice.infrastructure.exceptions.JwtException;
import com.authMS.Auth.microsservice.infrastructure.exceptions.UserAlreadyRegistered;
import com.authMS.Auth.microsservice.infrastructure.exceptions.UserNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionsHandler {

    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<String> handlerUserNotFound(UserNotFound e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UserAlreadyRegistered.class)
    public ResponseEntity<String> handlerUserAlreadyRegistered(UserAlreadyRegistered e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handlerJwtException(JwtException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
