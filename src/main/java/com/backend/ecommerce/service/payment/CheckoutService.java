package com.backend.ecommerce.service.payment;

import com.backend.ecommerce.entity.*;
import com.backend.ecommerce.enums.OrderStatus;
import com.backend.ecommerce.enums.TransactionStatus;
import com.backend.ecommerce.enums.TransactionType;
import com.backend.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckoutService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;
    private final CartProductsRepository cartProductsRepository;

    public CheckoutService(UserRepository userRepository, CartRepository cartRepository, OrderRepository orderRepository, TransactionRepository transactionRepository, CartProductsRepository cartProductsRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.transactionRepository = transactionRepository;
        this.cartProductsRepository = cartProductsRepository;
    }
//    private final PaymentContext paymentContext;



    @Transactional
    public Order checkout(Long userId, TransactionType transactionType, BigDecimal transactionAmount) {
        System.out.println("Fetching user with ID: " + userId);
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User Not Found"));
        System.out.println("User found: " + user);

        System.out.println("Fetching cart with ID: " + user.getCart().getId());
        Cart cart = cartRepository.findById(user.getCart().getId()).orElseThrow(() -> new RuntimeException("Cart Not Found"));
        System.out.println("Cart found: " + cart);

        List<CartProducts> cartProducts = cart.getCartProducts();
        if (cartProducts.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        System.out.println("Cart products found: " + cartProducts);

        List<OrderProducts> orderProducts = cartProducts.stream().map(cartProduct -> {
            OrderProducts orderProduct = new OrderProducts();
            orderProduct.setProduct(cartProduct.getProduct());
            orderProduct.setQuantity(cartProduct.getQuantity());
            return orderProduct;
        }).collect(Collectors.toList());

        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderProducts(orderProducts);

        Transaction transaction = new Transaction();
        transaction.setTransactionType(transactionType);
        transaction.setTransactionAmount(transactionAmount);
        transaction.setUser(user);
        transaction.setOrder(order);

//        PaymentStrategy paymentStrategy = paymentContext.getPaymentStrategy(transactionType);
//        boolean isPaymentSuccessful = paymentStrategy.processPayment(user, transactionAmount);
//        if (!isPaymentSuccessful) {
//            throw new RuntimeException("Payment Failed");
//        }

        transaction.setTransactionStatus(TransactionStatus.COMPLETED);

        orderRepository.save(order);
        System.out.println("Order saved: " + order);

        transactionRepository.save(transaction);
        System.out.println("Transaction saved: " + transaction);

        order.setTransaction(transaction);
        orderRepository.save(order);
        System.out.println("Order updated with transaction: " + order);

        for (OrderProducts orderProduct : orderProducts) {
            orderProduct.setOrder(order);
        }

        cartProductsRepository.deleteAll(cartProducts);
        cartRepository.deleteByUserId(userId);
        System.out.println("Cart deleted");

        return order;
    }
}
