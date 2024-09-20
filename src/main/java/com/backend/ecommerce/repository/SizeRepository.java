package com.backend.ecommerce.repository;

import com.backend.ecommerce.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface SizeRepository extends JpaRepository<Size, Long> {
    Optional<Size> findByName(String sizeName);
}