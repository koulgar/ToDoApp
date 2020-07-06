package com.koulgar.Exception;

public class UserDoesNotHavePermission extends RuntimeException {

    public UserDoesNotHavePermission(String message) {
        super(message);
    }

}