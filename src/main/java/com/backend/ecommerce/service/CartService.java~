package com.backend.ecommerce.service;

import com.backend.ecommerce.records.CartDTO;
import com.backend.ecommerce.dto.CartItemsDTO;
import com.backend.ecommerce.records.CartProductDTO;
import com.backend.ecommerce.entity.Cart;
import com.backend.ecommerce.entity.CartProducts;

import java.util.List;
import java.util.stream.Collectors;

public interface CartService {
    CartProducts addProductToCart(Long userId, Long productId, Integer quantity);

    void removeProductFromCart(Long userId, Long productId, Integer quantity);

    List<Cart> getAllCarts();

    Cart getCart(Long cartId);

    List<CartItemsDTO> getCartItemsByUserId(Long userId);

    CartDTO getCartByUserId(Long userId);

    default CartDTO convertToDTO(Cart cart) {
        return new CartDTO(
                cart.getId(),
                cart.getUser().getId(),
                cart.getCartProducts().stream()
                        .map(this::convertCartProductToDTO)
                        .collect(Collectors.toList())
        );
    }

    default CartProductDTO convertCartProductToDTO(CartProducts cartProduct) {
        String availabilityStatus = (cartProduct.getAvailabilityStatus() != null) ?
                cartProduct.getAvailabilityStatus().name() :
                "UNAVAILABLE"; // Default to UNAVAILABLE if null
        return new CartProductDTO(
                cartProduct.getId(),
                cartProduct.getProduct(),
                cartProduct.getQuantity(),
                availabilityStatus
        );
    }
}
