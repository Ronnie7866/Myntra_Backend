package com.backend.ecommerce.implementation;

import com.backend.ecommerce.dto.CartItemsDTO;
import com.backend.ecommerce.entity.Cart;
import com.backend.ecommerce.entity.CartProducts;
import com.backend.ecommerce.entity.Product;
import com.backend.ecommerce.entity.User;
import com.backend.ecommerce.exception.ResourceNotFoundException;
import com.backend.ecommerce.records.CartDTO;
import com.backend.ecommerce.repository.CartProductsRepository;
import com.backend.ecommerce.repository.CartRepository;
import com.backend.ecommerce.repository.ProductRepository;
import com.backend.ecommerce.repository.UserRepository;
import com.backend.ecommerce.service.CartService;
import jakarta.transaction.Transactional;
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

    /**
     * The following code either creates a new cart with new cartItem
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
    public CartProducts addProductToCart(Long userId, Long productId, Integer newQuantity) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart); // Save the new cart to the database
            user.setCart(cart); // Update the user's cart reference
            userRepository.save(user); // Save the updated user
        }

        Optional<CartProducts> existingCartItem = cartProductsRepository.findByCartIdAndProductId(cart.getId(), productId);

        CartProducts cartProducts;
        if (existingCartItem.isPresent()) {
            cartProducts = existingCartItem.get();
            // Set the quantity to the new value, not add to it
            cartProducts.setQuantity(newQuantity);
        } else {
            cartProducts = new CartProducts();
            cartProducts.setCart(cart);
            cartProducts.setProduct(product);
            cartProducts.setQuantity(newQuantity);
        }

        return cartProductsRepository.save(cartProducts);
    }

    @Transactional
    public void removeProductFromCart(Long userId) {
            Cart cart = cartRepository.findByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Cart not found for user with id: " + userId));
            cart.getCartProducts().clear();
            cartRepository.save(cart);
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
        if (cartOpt.isEmpty()) {
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
        if (cartOpt.isEmpty()) {
            throw new RuntimeException("Not Present");
        } else {
            return convertToDTO(cartOpt.get());
        }
    }
}
