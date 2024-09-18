package com.backend.ecommerce.implementation;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.backend.ecommerce.entity.Product;
import com.backend.ecommerce.exception.ImageUploadException;
import com.backend.ecommerce.mapper.ProductMapper;
import com.backend.ecommerce.records.ProductDTO;
import com.backend.ecommerce.service.ImageUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class S3ImageUploader implements ImageUploader {

    @Autowired
    private AmazonS3 client;

    @Autowired
    private ProductServiceImplementation productService;

    @Autowired
    private ProductMapper productMapper;

    @Value("${app.s3.bucket}")
    private String bucketName;

    @Override
    public String uploadImage(MultipartFile image, Long productId) {

        Product product;

        String actualFilename = image.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + actualFilename.substring(actualFilename.lastIndexOf("."));

        // create metadata
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(image.getSize());


        try {
            product = productMapper.reverse(productService.getProductById(productId));
            PutObjectResult putObjectResult = client.putObject(new PutObjectRequest(bucketName, fileName, image.getInputStream(), metadata));
            product.setImageURL(fileName);
            productService.createProduct(product);
            return fileName;
        } catch (IOException e) {
            throw new ImageUploadException("Error while uploading image " + e.getMessage());
        }
    }

    @Override
    public List<String> allImages() {

        ListObjectsV2Request listObjectRequest = new ListObjectsV2Request().withBucketName(bucketName);

        ListObjectsV2Result listObjectsV2Result =
        client.listObjectsV2(listObjectRequest);

        List<S3ObjectSummary> objectSummaries = listObjectsV2Result.getObjectSummaries();

        return objectSummaries.stream().map(item -> this.preSignedUrl(item.getKey())).toList();
    }

    @Override
    public String preSignedUrl(String fileName) {

        Date expirationDate = new Date();
        long time = expirationDate.getTime();
        int hour = 2;
        time = time + hour * 60 * 60 * 1000;

        expirationDate.setTime(time);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expirationDate);
        URL url = client.generatePresignedUrl(generatePresignedUrlRequest);

        return url.toString();
    }

    @Override
    public String getImageUrlByName(String fileName) {
        S3Object object = client.getObject(bucketName, fileName);
        String key = object.getKey();
        return preSignedUrl(key);
    }


}
