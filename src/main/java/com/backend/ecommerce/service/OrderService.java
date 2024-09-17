package com.backend.ecommerce.service;

import com.backend.ecommerce.dto.OrderRequest;
import com.backend.ecommerce.entity.Order;
import com.backend.ecommerce.enums.TransactionType;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
    void createOrder(OrderRequest orderRequest);

    Order getOrder(Integer id);

    List<Order> getAllOrders();

    Order convertCartToOrder(Long cartId);

}
