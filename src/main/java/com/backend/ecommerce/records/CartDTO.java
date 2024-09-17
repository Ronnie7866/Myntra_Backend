package com.backend.ecommerce.records;

import java.util.List;


public record CartDTO (Long id,
        Long userId,
        List<CartProductDTO> cartProducts) {
}
