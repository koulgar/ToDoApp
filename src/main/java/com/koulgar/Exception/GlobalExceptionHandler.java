package com.koulgar.Exception;

import com.koulgar.Model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(UserNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                "UserNotFoundException",
                LocalDateTime.now(),
                Collections.singletonList(exception.getMessage())
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PasswordDoesNotMatchException.class)
    public ResponseEntity<ErrorResponse> handle(PasswordDoesNotMatchException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                "PasswordDoesNotMatchException",
                LocalDateTime.now(),
                Collections.singletonList(exception.getMessage())
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
