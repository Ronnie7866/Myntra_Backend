package com.backend.ecommerce.implementation;

import com.backend.ecommerce.entity.Product;
import com.backend.ecommerce.exception.ImageUploadException;
import com.backend.ecommerce.mapper.ProductMapper;
import com.backend.ecommerce.records.ProductDTO;
import com.backend.ecommerce.service.ImageUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class S3ImageUploader implements ImageUploader {

    private final S3Client s3Client;
    private final String bucketName;
    private final ProductServiceImplementation productService;
    private final ProductMapper productMapper;
    private final S3Presigner s3Presigner;

    @Autowired
    public S3ImageUploader(S3Client s3Client,
                           @Value("${app.s3.bucket}") String bucketName,
                           ProductServiceImplementation productService,
                           ProductMapper productMapper,
                           S3Presigner s3Presigner) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.productService = productService;
        this.productMapper = productMapper;
        this.s3Presigner = s3Presigner;
    }

    @Override
    public String uploadImage(MultipartFile image, Long productId) {
        Product product;

        String actualFilename = image.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + actualFilename.substring(actualFilename.lastIndexOf("."));

        try {
            product = productMapper.reverse(productService.getProductById(productId));
            s3Client.putObject(PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .build(),
                    RequestBody.fromInputStream(image.getInputStream(), image.getSize()));
            product.setImageURL(fileName);
            productService.createProduct(product);
            return fileName;
        } catch (IOException e) {
            throw new ImageUploadException("Error while uploading image " + e.getMessage());
        }
    }

    @Override
    public List<String> allImages() {
        ListObjectsV2Request listRequest = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response listResponse = s3Client.listObjectsV2(listRequest);

        return listResponse.contents().stream()
                .map(S3Object::key)
                .map(this::preSignedUrl)
                .collect(Collectors.toList());
    }

    @Override
    public String preSignedUrl(String fileName) {
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofHours(1))
                .getObjectRequest(GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .build())
                .build();

        PresignedGetObjectRequest presignedRequest = s3Presigner.presignGetObject(presignRequest);
        return presignedRequest.url().toString();
    }

    @Override
    public String getImageUrlByName(String fileName) {
        return preSignedUrl(fileName);
    }
}