package com.backend.ecommerce.service.payment;

import com.backend.ecommerce.entity.User;

import java.math.BigDecimal;

public interface PaymentStrategy {
    boolean processPayment(User user, BigDecimal amount);
}
