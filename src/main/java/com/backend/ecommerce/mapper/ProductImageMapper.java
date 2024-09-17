package com.backend.ecommerce.mapper;

import com.backend.ecommerce.entity.ProductImageTable;
import com.backend.ecommerce.records.ProductImageDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductImageMapper implements EntityDTOMapper<ProductImageTable, ProductImageDTO> {
    @Override
    public ProductImageDTO apply(ProductImageTable entity) {
        return new ProductImageDTO(entity.getId(), entity.getProduct(), entity.getImagePath());
    }

    @Override
    public ProductImageTable reverse(ProductImageDTO dto) {
        ProductImageTable entity = new ProductImageTable();
        entity.setId(dto.id());
        entity.setProduct(dto.product());
        entity.setImagePath(dto.imagePath());
        return entity;
    }
}
