package com.backend.ecommerce.mapper;

import com.backend.ecommerce.entity.Cart;
import com.backend.ecommerce.entity.CartProducts;
import com.backend.ecommerce.records.CartDTO;
import com.backend.ecommerce.records.CartProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartMapper implements EntityDTOMapper<Cart, CartDTO> {
    private final CartProductsMapper cartProductsMapper;
    @Autowired
    public CartMapper(CartProductsMapper cartProductsMapper) {
        this.cartProductsMapper = cartProductsMapper;
    }

    @Override
    public CartDTO apply(Cart cart) {
        List<CartProductDTO> cartProductDTOS = cart.getCartProducts().stream().map((cartProductsMapper::apply)).toList();
        return new CartDTO(
                cart.getId(),
                cart.getUser().getId(),
                cartProductDTOS
        );
    }

    @Override
    public Cart reverse(CartDTO dto) {
        return null;
    }
}
