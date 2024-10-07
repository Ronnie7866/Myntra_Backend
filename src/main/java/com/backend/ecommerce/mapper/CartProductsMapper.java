package com.backend.ecommerce.mapper;

import com.backend.ecommerce.entity.CartProducts;
import com.backend.ecommerce.records.CartProductDTO;
import org.springframework.stereotype.Service;

@Service
public class CartProductsMapper implements EntityDTOMapper<CartProducts, CartProductDTO> {
    @Override
    public CartProductDTO apply(CartProducts cartProducts) {
        return new CartProductDTO(
                cartProducts.getId(),
                cartProducts.getProduct(),
                cartProducts.getQuantity(),
                cartProducts.getAvailabilityStatus().toString()
        );
    }

    @Override
    public CartProducts reverse(CartProductDTO dto) {
        CartProducts cartProducts = new CartProducts();
        cartProducts.setProduct(dto.product());
        cartProducts.setQuantity(dto.quantity());
        cartProducts.setId(dto.id());
        return cartProducts;
    }
}
