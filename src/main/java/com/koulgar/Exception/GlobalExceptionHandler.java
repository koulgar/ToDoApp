package com.koulgar.Exception;

import com.koulgar.Model.ErrorResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;
    private static final Locale TR = new Locale("tr");

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exception) {
        List<String> errorMessages = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::getMessage)
                .collect(Collectors.toList());

        ErrorResponse errorResponse = new ErrorResponse(
                "MethodArgumentNotValidException",
                LocalDateTime.now(),
                errorMessages
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private String getMessage(FieldError error) {
        return getMessage(error.getDefaultMessage(), error.getArguments());
    }

    private String getMessage(String messageKey, Object... args) {
        return messageSource.getMessage(messageKey, args, messageKey, TR);
    }

}
