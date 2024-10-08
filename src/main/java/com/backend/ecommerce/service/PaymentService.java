package com.backend.ecommerce.service;

import com.backend.ecommerce.entity.Order;
import com.backend.ecommerce.payload.PaymentResponse;

public interface PaymentService {

    PaymentResponse createPaymentLink(Order order);
}
