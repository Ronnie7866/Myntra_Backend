package com.backend.ecommerce.records;

import com.backend.ecommerce.entity.Product;

public record ProductImageDTO(Long id, Product product, String imagePath) {
}
