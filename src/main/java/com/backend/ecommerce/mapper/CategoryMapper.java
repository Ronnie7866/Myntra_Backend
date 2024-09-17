package com.backend.ecommerce.mapper;

import com.backend.ecommerce.entity.Category;
import com.backend.ecommerce.entity.Product;
import com.backend.ecommerce.exception.ProductNotFoundException;
import com.backend.ecommerce.records.CategoryDTO;
import com.backend.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper implements EntityDTOMapper<Category, CategoryDTO> {

    private final ProductRepository productRepository;

    @Autowired
    public CategoryMapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public CategoryDTO apply(Category category) {

        List<Long> productIds = category.getProducts().stream().map(Product::getId).toList();

        return new CategoryDTO(category.getId(), category.getName(), category.getDescription(), productIds);
    }

    @Override
    public Category reverse(CategoryDTO dto) {

        List<Product> productList = dto.productIds().stream().map(productId -> productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found with this id:  " + productId))).toList();

        Category category = new Category();
        category.setId(dto.id());
        category.setName(dto.name());
        category.setDescription(dto.description());
        category.setProducts(productList);
        return category;
    }
}
