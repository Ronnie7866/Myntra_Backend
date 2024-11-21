package com.backend.ecommerce.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service
@Slf4j
public class StripeService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    public PaymentIntent createPaymentIntent(BigDecimal amount, String currency) throws StripeException {
        try {
            Stripe.apiKey = stripeSecretKey;

            Map<String, Object> params = new HashMap<>();
            params.put("amount", amount.multiply(new BigDecimal(100)).intValue()); // Stripe expects amount in cents
            params.put("currency", currency);
            params.put("payment_method_types", List.of("card"));

            return PaymentIntent.create(params);
        } catch (StripeException e) {
            log.error("Error creating PaymentIntent: {}", e.getMessage());
            throw  e;
        }
    }

    public PaymentIntent confirmPaymentIntent(String paymentIntentId) throws StripeException {
        Stripe.apiKey = stripeSecretKey;
        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
        return paymentIntent.confirm();
    }

    public PaymentIntent retrievePaymentIntent(String paymentIntentId) throws StripeException {
        return PaymentIntent.retrieve(paymentIntentId);
    }

    public Refund createRefund(String paymentIntentid) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("payment_intent", paymentIntentid);
        return Refund.create(params);
    }
}