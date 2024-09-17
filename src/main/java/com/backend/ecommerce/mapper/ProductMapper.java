package com.backend.ecommerce.mapper;

import com.backend.ecommerce.dto.ProductDTO;
import com.backend.ecommerce.entity.Product;
import com.backend.ecommerce.entity.Category;
import com.backend.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper implements EntityDTOMapper<Product, ProductDTO> {

    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductDTO apply(Product product) {
        List<Long> categoryIds = product.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toList());

        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getImageURL(),
                product.getPrice(),
                product.getDescription(),
                product.getBrand(),
                product.getReturnPeriod(),
                product.getStockQuantity(),
                product.getAverageRating(),
                product.getDateAdded(),
                product.getDateUpdated(),
                product.getRating(),
                product.getAvailability(),
                categoryIds,
                product.getProductImages()
        );
    }

    @Override
    public Product reverse(ProductDTO dto) {
        // Fetch the category entities based on the categoryIds in the DTO
        List<Category> categories = dto.categoryIds().stream()
                .map(categoryId -> categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId)))
                .collect(Collectors.toList());

        // Create a new Product entity and set the fields from the DTO
        Product product = new Product();
        product.setId(dto.id());
        product.setName(dto.name());
        product.setImageURL(dto.imageURL());
        product.setPrice(dto.price());
        product.setDescription(dto.description());
        product.setBrand(dto.brand());
        product.setReturnPeriod(dto.returnPeriod());
        product.setStockQuantity(dto.stockQuantity());
        product.setAverageRating(dto.averageRating());
        product.setDateAdded(dto.dateAdded());
        product.setDateUpdated(dto.dateUpdated());
        product.setRating(dto.rating());
        product.setAvailability(dto.availability());
        product.setCategories(categories); // Set the fetched categories
        product.setProductImages(dto.productImages());

        return product;
    }
}
