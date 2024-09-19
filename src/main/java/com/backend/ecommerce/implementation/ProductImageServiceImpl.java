//package com.backend.ecommerce.implementation;
//
//import com.backend.ecommerce.entity.Product;
//import com.backend.ecommerce.entity.ProductImageTable;
//import com.backend.ecommerce.mapper.ProductImageMapper;
//import com.backend.ecommerce.records.ProductImageDTO;
//import com.backend.ecommerce.repository.ProductImageRepository;
//import com.backend.ecommerce.repository.ProductRepository;
//import com.backend.ecommerce.service.FileService;
//import com.backend.ecommerce.service.ProductImageService;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StreamUtils;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;
//
//@Service
//public class ProductImageServiceImpl implements ProductImageService {
//
//    private final ProductImageRepository productImageRepository;
//    private final ProductRepository productRepository;
//    private final FileServiceImplementation fileService;
//    private final String uploadDir = "C:/Users/mohda/IdeaProjects/Myntra_Frontend/public/images/";
//    private final ProductImageMapper productImageMapper;
//
//    @Autowired
//    public ProductImageServiceImpl(ProductImageRepository productImageRepository, ProductRepository productRepository, FileServiceImplementation fileService, ProductImageMapper productImageMapper) {
//        this.productImageRepository = productImageRepository;
//        this.productRepository = productRepository;
//        this.fileService = fileService;
//        this.productImageMapper = productImageMapper;
//    }
//
//    @Override
//    public ProductImageDTO saveProductImage(MultipartFile file, Long productId) throws IOException {
//        String fileName = fileService.uploadFile(uploadDir, file);
//
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new RuntimeException("Product not found "));
//
//        ProductImageTable productImage = new ProductImageTable();
//        productImage.setProduct(product);
//        productImage.setImagePath(uploadDir + fileName);
//
//        product.setImageURL(fileName);
//        productRepository.save(product);
//
//        ProductImageTable save = productImageRepository.save(productImage);
//        return productImageMapper.apply(save);
//    }
//}
