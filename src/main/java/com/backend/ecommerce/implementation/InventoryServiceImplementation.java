package com.backend.ecommerce.implementation;

import com.backend.ecommerce.entity.Inventory;
import com.backend.ecommerce.entity.Product;
import com.backend.ecommerce.exception.InsufficientStockException;
import com.backend.ecommerce.exception.InventoryNotFoundException;
import com.backend.ecommerce.exception.ProductNotFoundException;
import com.backend.ecommerce.exception.ResourceNotFoundException;
import com.backend.ecommerce.mapper.InventoryMapper;
import com.backend.ecommerce.records.InventoryDTO;
import com.backend.ecommerce.repository.InventoryRepository;
import com.backend.ecommerce.repository.ProductRepository;
import com.backend.ecommerce.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class InventoryServiceImplementation implements InventoryService {

    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Autowired
    public InventoryServiceImplementation(ProductRepository productRepository, InventoryRepository inventoryRepository, InventoryMapper inventoryMapper) {
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.inventoryMapper = inventoryMapper;
    }


    @Override
    public InventoryDTO addInventory(Long productId, Integer stock) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with this id:" + productId));
        Inventory inventory = new Inventory();
        inventory.setProduct(product);
        inventory.setQuantity(stock);
        Inventory savedInventory = inventoryRepository.save(inventory);
        return inventoryMapper.apply(savedInventory);
    }

    @Override
    public InventoryDTO updateStock(Long productId, Integer stock) {
        Inventory inventory = inventoryRepository.findByProduct(productRepository.findById(productId)
                        .orElseThrow(() -> new ResourceNotFoundException("Product not found")))
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found"));
        inventory.setQuantity(stock);
        Inventory save = inventoryRepository.save(inventory);
        return inventoryMapper.apply(save);
    }

    @Override
    public InventoryDTO getInventoryByProduct(Long productId) throws InventoryNotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found with this id: " + productId));
        Optional<Inventory> byProduct = inventoryRepository.findByProduct(product);
        if (byProduct.isEmpty()) {
            throw new InventoryNotFoundException("Inventory not found with this product");
        }
        Inventory inventory = byProduct.get();
        return inventoryMapper.apply(inventory);
    }

    @Override
    public List<InventoryDTO> getAllInventory() {
        List<Inventory> inventoryList = inventoryRepository.findAll();
        return inventoryList.stream().map(inventoryMapper::apply).toList();
    }

    @Override
    public InventoryDTO increaseStock(Long productId, Integer quantity) throws InventoryNotFoundException {
        Inventory inventory = inventoryRepository.findByProduct(productRepository.findById(productId)
                        .orElseThrow(() -> new ProductNotFoundException("Product not found with this id: " + productId)))
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found"));

        inventory.setQuantity(inventory.getQuantity() + quantity);
        Inventory save = inventoryRepository.save(inventory);
        return inventoryMapper.apply(save);
    }

    @Override
    public InventoryDTO decreaseStock(Long productId, Integer quantity) throws InsufficientStockException, InventoryNotFoundException {
        Inventory inventory = inventoryRepository.findByProduct(productRepository.findById(productId)
                        .orElseThrow(() -> new ProductNotFoundException("Product not found with this id: " + productId)))
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found"));

        if (inventory.getQuantity() < quantity) {
            throw new InsufficientStockException("Not enough stock available");
        }
        inventory.setQuantity(inventory.getQuantity() - quantity);
        Inventory save = inventoryRepository.save(inventory);
        return inventoryMapper.apply(save);
    }

    @Override
    public Boolean isStockAvailable(Long productId, Integer quantity) throws InventoryNotFoundException {
        Inventory inventory = inventoryRepository.findByProduct(productRepository.findById(productId)
                        .orElseThrow(() -> new ProductNotFoundException("Product not found with this id: " + productId)))
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found"));

        return inventory.getQuantity() >= quantity;
    }

    @Override
    public void bulkUpdateStock(Map<Long, Integer> stockUpdates) throws InventoryNotFoundException {
        for (Map.Entry<Long, Integer> entry : stockUpdates.entrySet()) {
            Long productId = entry.getKey();
            Integer stock = entry.getValue();

            Inventory inventory = inventoryRepository.findByProduct(productRepository.findById(productId)
                            .orElseThrow(() -> new ProductNotFoundException("Product not found with this id: " + productId)))
                    .orElseThrow(() -> new InventoryNotFoundException("Inventory not found"));

            inventory.setQuantity(stock);
            inventoryRepository.save(inventory);
        }
    }
}
