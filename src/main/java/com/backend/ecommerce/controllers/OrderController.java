package com.backend.ecommerce.controllers;

import com.backend.ecommerce.dto.OrderRequest;
import com.backend.ecommerce.entity.Order;
import com.backend.ecommerce.enums.TransactionType;
import com.backend.ecommerce.implementation.OrderServiceImplementation;
import com.backend.ecommerce.service.OrderService;
import com.backend.ecommerce.service.payment.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.backend.ecommerce.enums.PaymentStatus;

import com.backend.ecommerce.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final CheckoutService checkoutService;
    private final OrderServiceImplementation orderService;
    private final StripeService stripeService;

    @PostMapping("/create-payment-intent")
    public ResponseEntity<Map<String, String>> createPaymentIntent(@RequestParam Long userId,
                                                                   @RequestParam BigDecimal amount) {
        try {
            PaymentIntent paymentIntent = stripeService.createPaymentIntent(amount, "usd");
            Map<String, String> response = new HashMap<>();
            response.put("clientSecret", paymentIntent.getClientSecret());
            return ResponseEntity.ok(response);
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/confirm-payment")
public ResponseEntity<Order> confirmPaymentAndCreateOrder(@RequestParam Long userId,
                                                          @RequestParam String paymentIntentId,
                                                          @RequestParam BigDecimal amount) {
    System.out.println("Entering confirmPaymentAndCreateOrder method");
    System.out.println("UserId: " + userId + ", PaymentIntentId: " + paymentIntentId + ", Amount: " + amount);
    try {
        System.out.println("Retrieving payment intent");
        PaymentIntent paymentIntent = stripeService.retrievePaymentIntent(paymentIntentId);
        System.out.println("Payment intent status: " + paymentIntent.getStatus());
        
        if ("succeeded".equals(paymentIntent.getStatus())) {
            System.out.println("Payment already succeeded, creating order");
            Order order = checkoutService.checkout(userId, TransactionType.STRIPE, amount);
            order.setStripePaymentIntentId(paymentIntent.getId());
            order.setPaymentStatus(PaymentStatus.SUCCESS);
            Order updatedOrder = orderService.updateOrder(order);
            System.out.println("Order created and updated successfully: " + updatedOrder.getId());
            return ResponseEntity.ok(updatedOrder);
        } else if ("requires_confirmation".equals(paymentIntent.getStatus())) {
            System.out.println("Attempting to confirm payment intent");
            PaymentIntent confirmedPaymentIntent = stripeService.confirmPaymentIntent(paymentIntentId);
            System.out.println("Payment intent status after confirmation: " + confirmedPaymentIntent.getStatus());
            if ("succeeded".equals(confirmedPaymentIntent.getStatus())) {
                System.out.println("Payment succeeded, creating order");
                Order order = checkoutService.checkout(userId, TransactionType.STRIPE, amount);
                order.setStripePaymentIntentId(confirmedPaymentIntent.getId());
                order.setPaymentStatus(PaymentStatus.SUCCESS);
                Order updatedOrder = orderService.updateOrder(order);
                System.out.println("Order created and updated successfully: " + updatedOrder.getId());
                return ResponseEntity.ok(updatedOrder);
            } else {
                System.out.println("Payment did not succeed. Status: " + confirmedPaymentIntent.getStatus());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
        } else {
            System.out.println("Payment intent is in an unexpected state: " + paymentIntent.getStatus());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    } catch (StripeException e) {
        System.out.println("StripeException occurred: " + e.getMessage());
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    } finally {
        System.out.println("Exiting confirmPaymentAndCreateOrder method");
    }
}

    @PostMapping("checkout")
    public Order checkout(@RequestParam Long userId,
                          @RequestParam TransactionType transactionType,
                          @RequestParam BigDecimal transactionAmount) {
        return checkoutService.checkout(userId, transactionType, transactionAmount);
    }


    @PostMapping()
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest orderRequest) {
        orderService.createOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable long id) {
        Order order = orderService.getOrder((int) id);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> allOrders = orderService.getAllOrders();
        return ResponseEntity.ok(allOrders);
    }

    @PostMapping("/convert/{cartId}")
    public Order convertCartToOrder(@PathVariable Long cartId) {
        return orderService.convertCartToOrder(cartId);
    }
}

