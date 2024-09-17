package com.backend.ecommerce.mapper;

import com.backend.ecommerce.entity.Inventory;
import com.backend.ecommerce.records.InventoryDTO;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper implements EntityDTOMapper<Inventory, InventoryDTO> {
    @Override
    public InventoryDTO apply(Inventory entity) {
        return new InventoryDTO(entity.getId(), entity.getProduct(), entity.getQuantity());
    }

    @Override
    public Inventory reverse(InventoryDTO dto) {
        Inventory entity = new Inventory();
        entity.setId(dto.id());
        entity.setProduct(dto.product());
        entity.setQuantity(dto.quantity());
        return entity;
    }
}
