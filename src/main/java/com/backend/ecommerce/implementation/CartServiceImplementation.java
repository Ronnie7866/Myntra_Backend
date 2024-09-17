package com.backend.ecommerce.implementation;

import com.backend.ecommerce.exception.InsufficientStockException;
import com.backend.ecommerce.exception.InventoryNotFoundException;
import com.backend.ecommerce.records.CartDTO;
import com.backend.ecommerce.entity.*;
import com.backend.ecommerce.repository.CartProductsRepository;
import com.backend.ecommerce.repository.CartRepository;
import com.backend.ecommerce.repository.ProductRepository;
import com.backend.ecommerce.repository.UserRepository;
import com.backend.ecommerce.dto.CartItemsDTO;
import com.backend.ecommerce.service.CartService;
import com.backend.ecommerce.service.InventoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class CartServiceImplementation implements CartService {

    private UserRepository userRepository;
    private CartRepository cartRepository;
    private ProductRepository productRepository;
    private CartProductsRepository cartProductsRepository;
    private final InventoryService inventoryService;

    /** The following code either creates a new cart with new cartItem
     * or creates a new cartItem in the cart, if product was not already in the cart
     * or updates the quantity of the product if it was already present in the cart
     **/

//    @Override
//    public CartProducts addProductToCart(Long userId, Long productId, Integer quantity) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
//        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
//
//
//        Cart cart = user.getCart();
//
//        CartProducts cartProducts;
//
//        //FLOWCHART
//        //                                                       /---- Exists ---> Update Its
//        //                /--> Exists --------> product exists? {                   Quantity --->\
//        //               /                                       \                                \
//        // cart exists? {                                   Doesn't exist                          X ->Save Cart
//        //               \                                         \                              /
//        //                \--> Doesn't exist -> ----------------------> Create New item  ------->/
//        //                                                                & add to cart
//
//        Optional<CartProducts> existingCartItemOpt = Optional.empty();
//
//        //if Cart is present then try finding if the product being added is already in the cart or not
//        if(!Objects.isNull(cart)) {
//            existingCartItemOpt = cartProductsRepository.findByCartIdAndProductId(cart.getId(), productId);
//        }
//        //if cart is not present then create new cart
//        else{
//            cart = new Cart();
//            cart.setUser(user);
//        }
//
//        //if product is present then change its quantity to new one
//        if (existingCartItemOpt.isPresent()) {
//            cartProducts = existingCartItemOpt.get();
//            cartProducts.setQuantity(quantity);
//        }
//        //if product is not present, or it's a new cart then create new item
//        else {
//            cartProducts = new CartProducts();
//            cartProducts.setCart(cart);
//            cartProducts.setProduct(product);
//            cartProducts.setQuantity(quantity);
//        }
//
//        return cartProductsRepository.save(cartProducts);
//    }

    @Override
    public CartProducts addProductToCart(Long userId, Long productId, Integer quantity) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = user.getCart();
        CartProducts cartProducts;

        Optional<CartProducts> existingCartItemOpt = Optional.empty();

        // Check if the cart exists
        if (cart != null) {
            existingCartItemOpt = cartProductsRepository.findByCartIdAndProductId(cart.getId(), productId);
        } else {
            // Create a new cart if it doesn't exist
            cart = new Cart();
            cart.setUser(user);
        }

        // If the product already exists in the cart, update the quantity
        if (existingCartItemOpt.isPresent()) {
            cartProducts = existingCartItemOpt.get();
            int newQuantity = cartProducts.getQuantity() + quantity;
            cartProducts.setQuantity(newQuantity);
        } else {
            // If the product is not in the cart, create a new cart item
            cartProducts = new CartProducts();
            cartProducts.setCart(cart);
            cartProducts.setProduct(product);
            cartProducts.setQuantity(quantity);
        }

        // Decrease stock
        try {
            inventoryService.decreaseStock(productId, quantity);
        } catch (InsufficientStockException | InventoryNotFoundException e) {
            throw new RuntimeException(e);
        }

        return cartProductsRepository.save(cartProducts);
    }

    @Override // TODO create controller for this
    public void removeProductFromCart(Long userId, Long productId, Integer quantity) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = user.getCart();

        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        CartProducts cartProducts = cartProductsRepository.findByCartIdAndProductId(cart.getId(), productId)
                .orElseThrow(() -> new RuntimeException("Product not found in cart"));

        int newQuantity = cartProducts.getQuantity() - quantity;

        if (newQuantity <= 0) {
            cartProductsRepository.delete(cartProducts);
        } else {
            cartProducts.setQuantity(newQuantity);
            cartProductsRepository.save(cartProducts);
        }

        // Increase stock
        try {
            inventoryService.increaseStock(productId, quantity);
        } catch (InventoryNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    @Override
    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId).get();
    }

    @Override
    public List<CartItemsDTO> getCartItemsByUserId(Long userId) {
        Optional<Cart> cartOpt = cartRepository.findByUserId(userId);
        if(cartOpt.isEmpty()) {
            throw new RuntimeException("Not Present");
        }
        Long cartId = cartOpt.get().getId();
        List<CartProducts> cartProductsList = cartProductsRepository.findAllByCartId(cartId);
        return cartProductsList.stream()
                .map(CartItemsDTO::new).toList();
    }

    @Override
    public CartDTO getCartByUserId(Long userId) { // TODO when creating new user the new user gets the old cart fix this
        Optional<Cart> cartOpt = cartRepository.findByUserId(userId);
            if(cartOpt.isEmpty()) {
                throw new RuntimeException("Not Present");
            } else {
                return convertToDTO(cartOpt.get());
            }
        }

}
