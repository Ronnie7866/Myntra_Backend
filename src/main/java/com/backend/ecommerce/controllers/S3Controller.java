package com.backend.ecommerce.controllers;

import com.backend.ecommerce.service.ImageUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/s3")
public class S3Controller {

    private final ImageUploader uploader;

    @Autowired
    public S3Controller(ImageUploader uploader) {
        this.uploader = uploader;
    }

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file,
                                         @RequestParam("productId") Long productId) {
        return ResponseEntity.ok(uploader.uploadImage(file, productId));
    }

    @GetMapping
    public List<String> getAllImages() {
        return uploader.allImages();
    }

    @GetMapping("/{filename}")
    public String urlByName(@PathVariable String filename) {
        return uploader.getImageUrlByName(filename);
    }
}