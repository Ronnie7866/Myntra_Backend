package com.backend.ecommerce.records;

import com.backend.ecommerce.entity.ProductImageTable;
import com.backend.ecommerce.entity.Rating;
import com.backend.ecommerce.enums.AvailabilityStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        BigDecimal discountedPercentage,
        BigDecimal currentPrice,
        LocalDate deliveryDate,
        LocalDateTime dateAdded,
        LocalDateTime dateUpdated,
        List<Rating> rating,
        AvailabilityStatus availability,
        List<String> sizes,
        List<String> colors,
        List<Long> categoryIds,
        List<ProductImageTable> productImages
) {}