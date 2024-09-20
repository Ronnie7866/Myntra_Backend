package com.backend.ecommerce.repository;

import com.backend.ecommerce.entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<Color, Long> {
    Optional<Color> findByName(String colorName);
}