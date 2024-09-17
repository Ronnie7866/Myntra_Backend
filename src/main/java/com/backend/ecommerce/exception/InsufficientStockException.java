package com.backend.ecommerce.exception;

public class InsufficientStockException extends Throwable {
    public InsufficientStockException(String message) {
        super(message);
    }
}
