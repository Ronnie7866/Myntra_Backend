package com.backend.ecommerce.implementation;

import com.backend.ecommerce.dto.OrderProductRequest;
import com.backend.ecommerce.dto.OrderRequest;
import com.backend.ecommerce.entity.*;
import com.backend.ecommerce.enums.OrderStatus;
import com.backend.ecommerce.enums.TransactionStatus;
import com.backend.ecommerce.enums.TransactionType;
import com.backend.ecommerce.repository.*;
import com.backend.ecommerce.service.OrderService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class OrderServiceImplementation implements OrderService {

    private final OrderRepository orderRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderProductsRepository orderProductsRepository;
    private final CartRepository cartRepository;


    @Override
    public void createOrder(OrderRequest orderRequest) {
        // Retrieve user
        User user = userRepository.findById(orderRequest.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        // Create Order entity
        Order order = new Order();
        order.setUser(user);
        order.setOrderStatus(OrderStatus.PENDING);

        // Save order to get its ID
        order = orderRepository.save(order);

        // Create OrderProducts entities and establish mappings
        for (OrderProductRequest productRequest : orderRequest.getOrderProducts()) {
            Product product = productRepository.findById(productRequest.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

            // Create OrderProduct entity
            OrderProducts orderProducts = new OrderProducts();
            orderProducts.setOrder(order);
            orderProducts.setProduct(product);
            orderProducts.setQuantity(productRequest.getQuantity());

            // Save OrderProducts entity
            orderProductsRepository.save(orderProducts);

            // Add OrderProducts entity to order's list of order Products
            order.getOrderProducts().add(orderProducts);
        }

        // update the order
        orderRepository.save(order);
    }


    @Override
    public Order getOrder(Integer id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order convertCartToOrder(Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        User user = cart.getUser();

        Order order = new Order();
        order.setUser(user);

        List<OrderProducts> orderProductsList = cart.getCartProducts().stream().map(cartProduct -> {
            OrderProducts orderProduct = new OrderProducts();
            orderProduct.setOrder(order);
            orderProduct.setProduct(cartProduct.getProduct());
            orderProduct.setQuantity(cartProduct.getQuantity());
            return orderProduct;
        }).collect(Collectors.toList());

        order.setOrderProducts(orderProductsList);

        return orderRepository.save(order);
    }
}
