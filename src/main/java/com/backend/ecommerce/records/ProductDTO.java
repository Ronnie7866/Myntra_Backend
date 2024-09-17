package com.backend.ecommerce.records;

import com.backend.ecommerce.entity.ProductImageTable;
import com.backend.ecommerce.entity.Rating;
import com.backend.ecommerce.enums.AvailabilityStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductDTO(
        Long id,
        String name,
        String imageURL,
        BigDecimal price,
        String description,
        String brand,
        String returnPeriod,
        Integer stockQuantity,
        BigDecimal averageRating,
        LocalDateTime dateAdded,
        LocalDateTime dateUpdated,
        List<Rating> rating,
        AvailabilityStatus availability,
        List<Long> categoryIds,
        List<ProductImageTable> productImages
) {}