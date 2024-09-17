package com.backend.ecommerce.controllers;

import com.backend.ecommerce.entity.Inventory;
import com.backend.ecommerce.exception.InsufficientStockException;
import com.backend.ecommerce.exception.InventoryNotFoundException;
import com.backend.ecommerce.implementation.InventoryServiceImplementation;
import com.backend.ecommerce.records.InventoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryServiceImplementation inventoryServiceImplementation;

    @Autowired
    public InventoryController(InventoryServiceImplementation inventoryServiceImplementation) {
        this.inventoryServiceImplementation = inventoryServiceImplementation;
    }
    @PostMapping("/{productId}")
    public ResponseEntity<?> addInventory(@PathVariable Long productId, @RequestParam int stock, @RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token invalid");
        }
        InventoryDTO inventory = inventoryServiceImplementation.addInventory(productId, stock);
        return new ResponseEntity<>(inventory, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<InventoryDTO> updateStock(@PathVariable Long productId, @RequestParam int stock) {
        InventoryDTO inventory = inventoryServiceImplementation.updateStock(productId, stock);
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryDTO> getInventoryByProduct(@PathVariable Long productId) {
        InventoryDTO inventory = null;
        try {
            inventory = inventoryServiceImplementation.getInventoryByProduct(productId);
        } catch (InventoryNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<InventoryDTO>> getAllInventories() {
        List<InventoryDTO> inventories = inventoryServiceImplementation.getAllInventory();
        return new ResponseEntity<>(inventories, HttpStatus.OK);
    }

    @PostMapping("/decrease/{productId}")
    public ResponseEntity<InventoryDTO> decreaseStock(@PathVariable Long productId, @RequestParam int quantity) {
        InventoryDTO inventory = null;
        try {
            inventory = inventoryServiceImplementation.decreaseStock(productId, quantity);
        } catch (InsufficientStockException | InventoryNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @PostMapping("/increase/{productId}")
    public ResponseEntity<InventoryDTO> increaseStock(@PathVariable Long productId, @RequestParam int quantity) {
        InventoryDTO inventory = null;
        try {
            inventory = inventoryServiceImplementation.increaseStock(productId, quantity);
        } catch (InventoryNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(inventory, HttpStatus.OK);
    }

    @GetMapping("/check/{productId}")
    public ResponseEntity<Boolean> isStockAvailable(@PathVariable Long productId, @RequestParam int quantity) {
        boolean isAvailable = false;
        try {
            isAvailable = inventoryServiceImplementation.isStockAvailable(productId, quantity);
        } catch (InventoryNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(isAvailable, HttpStatus.OK);
    }

    @PostMapping("/bulk-update")
    public ResponseEntity<Void> bulkUpdateStock(@RequestBody Map<Long, Integer> stockUpdates) {
        try {
            inventoryServiceImplementation.bulkUpdateStock(stockUpdates);
        } catch (InventoryNotFoundException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
