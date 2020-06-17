package com.koulgar.Exception;

public class PasswordDoesNotMatchException extends RuntimeException {

    public PasswordDoesNotMatchException(String message) {
        super(message);
    }
}
