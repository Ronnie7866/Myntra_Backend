package com.backend.ecommerce.records;

import java.util.List;

public record CategoryDTO(
        Long id,
        String name,
        String description,
        List<Long> productIds
) {}