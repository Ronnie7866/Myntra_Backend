package com.backend.ecommerce.mapper;

import com.backend.ecommerce.entity.Rating;
import com.backend.ecommerce.records.RatingDTO;

public class RatingMapper implements EntityDTOMapper<Rating, RatingDTO> {
    @Override
    public RatingDTO apply(Rating rating) {



        return new RatingDTO(rating.getId(), rating.getStars(), rating.getCount(), rating.getComment(), rating.getProduct(), rating.getUser());
    }

    @Override
    public Rating reverse(RatingDTO dto) {
        Rating rating = new Rating();
        rating.setId(dto.id());
        rating.setUser(dto.user());
        rating.setProduct(dto.product());
        rating.setCount(dto.count());
        rating.setStars(dto.start());
        rating.setComment(dto.comment());
        return rating;
    }
}
