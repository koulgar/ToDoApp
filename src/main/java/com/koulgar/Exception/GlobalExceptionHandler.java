package com.koulgar.Exception;

import com.koulgar.Model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(com.couchbase.client.core.error.UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(com.couchbase.client.core.error.UserNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                "UserNotFoundException",
                System.currentTimeMillis(),
                Collections.singletonList(exception.getMessage())
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordDoesNotMatchException.class)
    public ResponseEntity<ErrorResponse> handle(PasswordDoesNotMatchException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                "PasswordDoesNotMatchException",
                System.currentTimeMillis(),
                Collections.singletonList(exception.getMessage())
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
