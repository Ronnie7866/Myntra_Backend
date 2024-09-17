package com.backend.ecommerce.enums;

public enum TransactionType {

    IMPS,
    UPI,
    COD,
    DEBIT_CARD,
    CREDIT_CARD, BANK;


    @Override
    public String toString() {
        return switch (this) {
            case COD -> "COD";
            case IMPS -> "IMPS";
            case UPI -> "UPI";
            case DEBIT_CARD -> "DEBIT_CARD";
            case CREDIT_CARD -> "CREDIT_CARD";
            default -> throw new IllegalArgumentException();
        };
    }
}
