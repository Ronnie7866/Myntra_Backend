package com.backend.ecommerce.enums;

public enum OrderStatus {
    PENDING,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELED,
    RETURNED,
    CREATED;

    @Override
    public String toString() {
        return switch (this) {
            case PENDING -> "Pending";
            case PROCESSING -> "Processing";
            case SHIPPED -> "Shipped";
            case DELIVERED -> "Delivered";
            case CANCELED -> "Canceled";
            case RETURNED -> "Returned";
            case CREATED -> "Created";
            default -> throw new IllegalArgumentException();
        };
    }
}
