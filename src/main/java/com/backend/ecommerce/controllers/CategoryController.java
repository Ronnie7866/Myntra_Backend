package com.backend.ecommerce.controllers;


import com.backend.ecommerce.entity.Category;
import com.backend.ecommerce.records.CategoryDTO;
import com.backend.ecommerce.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;


    @PostMapping
    public ResponseEntity<CategoryDTO> addCategory(@RequestBody Category category) {
        CategoryDTO category1 = categoryService.create(category);
        return new ResponseEntity<>(category1, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> allCategories = categoryService.findAll();
        return ResponseEntity.ok(allCategories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO byId = categoryService.findById(id);
        if (byId != null) {
            return ResponseEntity.ok(byId);
        } else {
            throw new RuntimeException("Category not found");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestBody Category category, @PathVariable Long id) {
        CategoryDTO updatedCategory = categoryService.update(category, id);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        String delete = categoryService.delete(id);
        return ResponseEntity.ok("Category deleted successfully");
    }

    @PostMapping("/batch")
    public ResponseEntity<List<CategoryDTO>> createCategory(@RequestBody List<Category> category) {
        List<CategoryDTO> listOfCategory = categoryService.createListOfCategory(category);
        return ResponseEntity.ok(listOfCategory);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> findListOfCategoryByProductId(@RequestParam Long productId) {
        List<CategoryDTO> categoryByProductId = categoryService.getCategoryByProductId(productId);
        return ResponseEntity.ok(categoryByProductId);
    }
}
