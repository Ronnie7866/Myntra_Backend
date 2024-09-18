package com.backend.ecommerce.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageUploader {
    String uploadImage(MultipartFile image, Long productId);

    List<String> allImages();

    String preSignedUrl(String fileName);

    String getImageUrlByName(String fileName);
}
