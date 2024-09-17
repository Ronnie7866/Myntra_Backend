package com.backend.ecommerce.service;

import com.backend.ecommerce.entity.ProductImageTable;
import com.backend.ecommerce.records.ProductImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface ProductImageService {
    ProductImageDTO saveProductImage(MultipartFile file, Long productId) throws IOException;
}
