package com.backend.ecommerce.service;

import com.backend.ecommerce.entity.Category;
import com.backend.ecommerce.records.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> findAll();

    CategoryDTO findById(Long id);

    List<CategoryDTO> getCategoryByProductId(Long productId);

    CategoryDTO create(Category category);

    String delete(Long id);

    CategoryDTO update(Category category, Long id);

    List<CategoryDTO> createListOfCategory(List<Category> categoryList);
}
