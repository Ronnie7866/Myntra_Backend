package com.backend.ecommerce.exception;

public class InventoryNotFoundException extends Throwable {
    public InventoryNotFoundException(String s) {
        super(s);
    }
}
