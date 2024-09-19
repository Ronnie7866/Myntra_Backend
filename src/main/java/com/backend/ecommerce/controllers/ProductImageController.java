//package com.backend.ecommerce.controllers;
//
//
//import com.backend.ecommerce.entity.ProductImageTable;
//import com.backend.ecommerce.implementation.FileServiceImplementation;
//import com.backend.ecommerce.implementation.ProductImageServiceImpl;
//import com.backend.ecommerce.implementation.ProductServiceImplementation;
//import com.backend.ecommerce.mapper.ProductImageMapper;
//import com.backend.ecommerce.records.ProductImageDTO;
//import com.backend.ecommerce.service.ProductImageService;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.StreamUtils;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/images")
//@AllArgsConstructor
//public class ProductImageController {
//
//
//    @Autowired
//    private final FileServiceImplementation fileService;
//    @Autowired
//    private final  ProductImageServiceImpl productImageService;
//
//
//    @PostMapping("/upload")
//    public ResponseEntity<ProductImageDTO> uploadProductImage(@RequestParam("file") MultipartFile file, @RequestParam("productId") Long productId) throws IOException {
//        ProductImageDTO productImage = productImageService.saveProductImage(file, productId);
//        return ResponseEntity.ok(productImage);
//    }
//
//    @GetMapping("/{imageName}")
//    public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {
//        // Fetch the image as an InputStream from the fileService
//        InputStream resourceFile = fileService.getResourceFile("uploads", imageName);
//
//        // Set the content type based on the file extension
//        String contentType = getContentType(imageName);
//        response.setContentType(contentType);
//
//        // Copy the file data to the response output stream
//        StreamUtils.copy(resourceFile, response.getOutputStream());
//
//        // Ensure to flush the response output stream
//        response.flushBuffer();
//    }
//
//    private String getContentType(String fileName) {
//        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
//            return MediaType.IMAGE_JPEG_VALUE;
//        } else if (fileName.endsWith(".png")) {
//            return MediaType.IMAGE_PNG_VALUE;
//        } else if (fileName.endsWith(".gif")) {
//            return MediaType.IMAGE_GIF_VALUE;
//        } else {
//            return MediaType.APPLICATION_OCTET_STREAM_VALUE;
//        }
//    }
//}
