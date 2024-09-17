package com.backend.ecommerce.service;

import com.backend.ecommerce.dto.ProductDTO;
import com.backend.ecommerce.entity.Product;
import com.backend.ecommerce.records.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(Product product);

    List<ProductDTO> addAll(List<Product> productList);

    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(Long id);

    ProductDTO updateProduct(Long id, Product product);

    List<ProductDTO> getProductByCategory(Long catId);

    ProductDTO assignCategoryToProduct(Long productId, Long categoryId);

    ProductDTO deleteProduct(Long id);

    ProductResponse getAllProductWithPagination(Integer pageNo, Integer pageSize);

    ProductResponse getProductWithPaginationAndSorting(Integer pageNo, Integer pageSize, String sortBy, String sortOrder);
}