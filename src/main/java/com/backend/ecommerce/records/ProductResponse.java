package com.backend.ecommerce.records;

import com.backend.ecommerce.entity.Product;

import java.util.List;

public record ProductResponse (List<Product> products,
                               Integer pageNumber,
                               Integer pageSize,
                               long totalElements,
                               int totalPages,
                               boolean isLast) {
}
