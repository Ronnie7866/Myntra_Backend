package com.backend.ecommerce.repository;

import com.backend.ecommerce.entity.OrderProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductsRepository extends JpaRepository<OrderProducts, Long> {
}
