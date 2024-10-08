package com.backend.ecommerce.implementation;

import com.backend.ecommerce.entity.Order;
import com.backend.ecommerce.payload.PaymentResponse;
import com.backend.ecommerce.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;

public class PaymentServiceImplementation implements PaymentService {

    @Value("${stripe.publishable.key}")
    private String stripePublishableKey;
    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Override
    public PaymentResponse createPaymentLink(Order order) {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment/success" + order.getId())
                .setCancelUrl("http://localhost:3000/payment")
    }
}
