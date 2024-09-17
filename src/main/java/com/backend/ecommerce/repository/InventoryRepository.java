package com.backend.ecommerce.repository;

import com.backend.ecommerce.entity.Inventory;
import com.backend.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProduct(Product product);
}
