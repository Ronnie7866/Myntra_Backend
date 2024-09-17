package com.backend.ecommerce.repository;

import com.backend.ecommerce.entity.ProductImageTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImageRepository extends JpaRepository<ProductImageTable, Long> {
}
