package com.backend.ecommerce.mapper;

public interface EntityDTOMapper<E, D> {
    D apply(E entity);
    E reverse(D dto);
}