package com.backend.ecommerce.controllers;

import com.backend.ecommerce.records.CartDTO;
import com.backend.ecommerce.entity.Cart;
import com.backend.ecommerce.entity.CartProducts;
import com.backend.ecommerce.dto.CartItemsDTO;
import com.backend.ecommerce.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public List<Cart> getAllCarts() {
        return cartService.getAllCarts();
    }

    @GetMapping("/user/{userId}")
    public List<CartItemsDTO> getCartItemsByUserId(@PathVariable Long userId) {
        return cartService.getCartItemsByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<CartProducts> addProductToCart(@RequestParam Long userId, @RequestParam Long productId, @RequestParam Integer quantity){ // TODO change userID to RequestParam
        CartProducts cartProducts = cartService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.ok(cartProducts);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long cartId){
        Cart cart = cartService.getCart(cartId);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/getCartByUserId/{userId}")
    public ResponseEntity<CartDTO> getCartByUserId(@PathVariable Long userId) {
        CartDTO cartByUserId = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cartByUserId);
    }
}
