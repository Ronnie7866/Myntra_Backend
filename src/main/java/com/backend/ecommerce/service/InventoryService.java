package com.backend.ecommerce.service;

import com.backend.ecommerce.entity.Inventory;
import com.backend.ecommerce.entity.Product;
import com.backend.ecommerce.exception.InsufficientStockException;
import com.backend.ecommerce.exception.InventoryNotFoundException;
import com.backend.ecommerce.records.InventoryDTO;

import java.util.List;
import java.util.Map;

public interface InventoryService {

    InventoryDTO addInventory(Long productId, Integer stock);
    InventoryDTO updateStock(Long productId, Integer stock);
    InventoryDTO getInventoryByProduct(Long productId) throws InventoryNotFoundException;
    List<InventoryDTO> getAllInventory();
    InventoryDTO increaseStock(Long productId, Integer quantity) throws InventoryNotFoundException;
    InventoryDTO decreaseStock(Long productId, Integer quantity) throws InsufficientStockException, InventoryNotFoundException;
    Boolean isStockAvailable(Long productId, Integer quantity) throws InventoryNotFoundException;
    void bulkUpdateStock(Map<Long, Integer> stockUpdates) throws InventoryNotFoundException;
}
