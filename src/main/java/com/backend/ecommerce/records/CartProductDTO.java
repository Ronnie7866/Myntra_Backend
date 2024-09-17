package com.backend.ecommerce.records;

import com.backend.ecommerce.entity.Product;


public record CartProductDTO ( Long id,
         Product product,
         Integer quantity,
         String availabilityStatus) {
}