package com.backend.ecommerce.implementation;

import com.backend.ecommerce.mapper.ProductMapper;
import com.backend.ecommerce.records.ProductDTO;
import com.backend.ecommerce.records.ProductResponse;
import com.backend.ecommerce.entity.Category;
import com.backend.ecommerce.entity.Product;
import com.backend.ecommerce.exception.CategoryNotFoundException;
import com.backend.ecommerce.exception.ProductNotFoundException;
import com.backend.ecommerce.repository.CategoryRepository;
import com.backend.ecommerce.repository.ProductRepository;
import com.backend.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImplementation implements ProductService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImplementation(CategoryRepository categoryRepository, ProductRepository productRepository, ProductMapper productMapper) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        return productMapper.apply(savedProduct);
    }

    @Override
    public List<ProductDTO> addAll(List<Product> products) {         // Save all products at once
            List<Product> savedProducts = productRepository.saveAll(products);
            // Map the saved products to DTOs
        Collectors Collectors;
        return savedProducts.stream()
                .map(productMapper::apply)
                .toList();
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        return productList.stream().map(productMapper::apply).toList();
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with the id: " + id));
        return productMapper.apply(product);
    }

    @Override
    public ProductDTO updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found " + id));
        existingProduct.setId(product.getId());
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        Product savedProduct = productRepository.save(existingProduct);
        return productMapper.apply(savedProduct);
    }

    @Override
    public List<ProductDTO> getProductByCategory(Long catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(() -> new CategoryNotFoundException("Category not found with this Id : " + catId));
        List<Product> categoryProducts = category.getProducts();

//        using loops

//        List<ProductDTO> categoryProductsDTO = new ArrayList<>();
//        for (int i = 0; i < categoryProducts.size(); i++) {
//            ProductDTO productDTO = productMapper.apply(categoryProducts.get(i));
//            categoryProductsDTO.add(productDTO);
//        }
//        return categoryProductsDTO;


//         Using lambda

//        categoryProducts.stream().map((categoryProduct) -> productMapper.apply(categoryProduct)).collect(Collectors.toList());


        // using method referencing

        return categoryProducts.stream().map(productMapper::apply).toList();
    }

    @Override
    public ProductDTO assignCategoryToProduct(Long productId, Long categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));


        Long id = category.getId();
//        product.getCategoryIds().add(id);// TODO fix this function
        Product savedProduct = productRepository.save(product);
        return productMapper.apply(savedProduct);
    }

    @Override
    public ProductDTO deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
        productRepository.deleteById(id);
        if (product.isPresent()) {
            return productMapper.apply(product.get());
        }
        throw new ProductNotFoundException("Product not found");
    }

    @Override
    public ProductResponse getAllProductWithPagination(Integer pageNo, Integer pageSize) {
        Pageable pageable = (Pageable) PageRequest.of(pageNo, pageSize);
        Page<Product> productPage = productRepository.findAll((PageRequest) pageable);
        List<Product> productList = productPage.getContent();
        return new ProductResponse(productList, pageNo, pageSize, productPage.getTotalElements(), productPage.getTotalPages(), productPage.isLast());
    }

    @Override
    public ProductResponse getProductWithPaginationAndSorting(Integer pageNo, Integer pageSize, String sortBy, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = (Pageable) PageRequest.of(pageNo, pageSize, sort);
        Page<Product> productPage = productRepository.findAll((PageRequest) pageable);
        List<Product> productList = productPage.getContent();
        return new ProductResponse(productList, pageNo, pageSize, productPage.getTotalElements(), productPage.getTotalPages(), productPage.isLast());
    }

    @Override
    public ProductResponse searchProducts(String query, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        // Create pageable object with sorting
        Sort sort = Sort.by(Sort.Order.by(sortBy));
        if (sortOrder.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        }
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        // Perform search with pagination and sorting
        Page<Product> products = productRepository.search(query, pageRequest);
        return new ProductResponse(
                products.getContent(), // List<Product>
                products.getNumber(),  // Page number
                products.getSize(),    // Page size
                products.getTotalElements(), // Total number of products
                products.getTotalPages(), // Total number of pages
                products.isLast() // Is last page
        );
    }

    @Override
    public List<Product> searchProducts(String query) {
        return productRepository.findByNameContainingIgnoreCase(query);
    }
}
