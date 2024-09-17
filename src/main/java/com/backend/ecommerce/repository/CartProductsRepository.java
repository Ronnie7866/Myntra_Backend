package com.backend.ecommerce.repository;

import com.backend.ecommerce.entity.CartProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CartProductsRepository extends JpaRepository<CartProducts, Long> {

    Optional<CartProducts> findByCartIdAndProductId(@Param("cart") Long cartId, @Param("product") Long productId);

    List<CartProducts> findAllByCartId(Long cartId);

    @Transactional
    void deleteAllByCartId(Long cart_id);
}
