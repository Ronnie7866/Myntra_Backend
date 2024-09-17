package com.backend.ecommerce.exception;

public class FIleExistsException extends RuntimeException {

    public FIleExistsException(String message) {
        super(message);
    }
}
