package com.backend.ecommerce.records;

import com.backend.ecommerce.entity.Product;
import com.backend.ecommerce.entity.User;

public record RatingDTO(int id, float start, float count, String comment, Product product, User user) {
}
