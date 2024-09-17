package com.backend.ecommerce.records;

import com.backend.ecommerce.entity.Product;

public record InventoryDTO(Long id, Product product, Integer quantity) {
}
