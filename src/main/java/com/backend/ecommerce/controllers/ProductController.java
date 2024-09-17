package com.backend.ecommerce.controllers;


import com.backend.ecommerce.implementation.ProductServiceImplementation;
import com.backend.ecommerce.mapper.ProductMapper;
import com.backend.ecommerce.records.ProductDTO;
import com.backend.ecommerce.records.ProductResponse;
import com.backend.ecommerce.entity.Product;
import com.backend.ecommerce.service.CategoryService;
import com.backend.ecommerce.utility.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin("*")

public class ProductController {

    private final ProductServiceImplementation productService;
    private final CategoryService categoryService;
    private final ProductMapper productMapper;

    @Autowired
    public ProductController(ProductServiceImplementation productService, CategoryService categoryService, ProductMapper productMapper) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product, @RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authorization token invalid");
        }

        String jwtToken = token.substring(7);

        ProductDTO savedProducts = productService.createProduct(product);
        return new ResponseEntity<>(savedProducts, HttpStatus.CREATED);
    }

    @PostMapping("/add-all")
    public ResponseEntity<List<ProductDTO>> createListProduct(@RequestBody List<Product> productList) {
        List<ProductDTO> products = productService.addAll(productList);
        return new ResponseEntity<>(products, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO productById = productService.getProductById(id);
        return ResponseEntity.ok(productById);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody Product product, @PathVariable Long id) {
        ProductDTO updatedProduct = productService.updateProduct(id, product);
        return ResponseEntity.ok(updatedProduct);
    }

    @PostMapping("/mapCategory")
    public ResponseEntity<ProductDTO> assignProductWithCategory(@RequestParam Long productId, @RequestParam Long categoryId) {
        ProductDTO product = productService.assignCategoryToProduct(productId, categoryId);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/getProductByCategory/{id}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long id) {
        List<ProductDTO> productByCategory = productService.getProductByCategory(id);
        return ResponseEntity.ok(productByCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long id) {
        ProductDTO product = productService.deleteProduct(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/allProductPage")
    public ResponseEntity<ProductResponse> getAllProductPage(@RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                                   @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize) {
        return ResponseEntity.ok(productService.getAllProductWithPagination(pageNumber, pageSize));
    }

    @GetMapping("/allProductPageWithPagination")
    public ResponseEntity<ProductResponse> getAllProductPage(@RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                             @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
                                                             @RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                             @RequestParam(defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder) {
        return ResponseEntity.ok(productService.getProductWithPaginationAndSorting(pageNumber, pageSize, sortBy, sortOrder));
    }
}
