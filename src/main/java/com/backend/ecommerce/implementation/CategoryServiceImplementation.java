package com.backend.ecommerce.implementation;


import com.backend.ecommerce.entity.Category;
import com.backend.ecommerce.entity.Product;
import com.backend.ecommerce.mapper.CategoryMapper;
import com.backend.ecommerce.records.CategoryDTO;
import com.backend.ecommerce.repository.CategoryRepository;
import com.backend.ecommerce.repository.ProductRepository;
import com.backend.ecommerce.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImplementation implements CategoryService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryServiceImplementation(ProductRepository productRepository, CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }


    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream().map((categoryMapper::apply)).toList();
    }

    @Override
    public CategoryDTO findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found with this id"));
        return categoryMapper.apply(category);
    }

    @Override
    public List<CategoryDTO> getCategoryByProductId(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found with this id"));
//        return new ArrayList<>(product.getCategory());
        return null;
    }

    @Override
    public CategoryDTO create(Category category) {
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.apply(savedCategory);
    }

    @Override
    public String delete(Long id) {
        categoryRepository.deleteById(id);
        return "Category deleted successfully";
    }

    @Override
    public CategoryDTO update(Category category, Long id) {
        Category existingCategory = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with this id"));
        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());
        existingCategory.setProducts(category.getProducts());
        Category savedCategory = categoryRepository.save(existingCategory);
        return categoryMapper.apply(savedCategory);
    }

    @Override
    public List<CategoryDTO> createListOfCategory(List<Category> categoryList) {
        List<Category> categories = categoryRepository.saveAll(categoryList);
        return categories.stream().map((categoryMapper::apply)).toList();
    }
}
