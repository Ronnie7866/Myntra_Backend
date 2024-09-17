package com.backend.ecommerce.exception;

public class UserHasNoRoleException extends Throwable {
    public UserHasNoRoleException(String message) {
        super(message);
    }
}
