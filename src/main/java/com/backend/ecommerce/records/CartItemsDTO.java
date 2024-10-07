package com.backend.ecommerce.records;

import com.backend.ecommerce.entity.CartProducts;
import com.backend.ecommerce.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CartItemsDTO(
        Long cartItemId,
        Product product,
        String productName,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal totalPrice,
        String imageUri,
        Boolean availability,
        LocalDateTime addedOn,
        String productApiEndpoint
) {
    public CartItemsDTO(CartProducts ci) {
        this(
            ci.getId(),
            ci.getProduct(),
            ci.getProduct().getName(),
            ci.getQuantity(),
            ci.getProduct().getPrice(),
            ci.getProduct().getPrice().multiply(BigDecimal.valueOf(ci.getQuantity())),
            null, // imageUri
            null, // availability
            null, // addedOn
            null  // productApiEndpoint
        );
    }
}
