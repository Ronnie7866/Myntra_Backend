//package com.backend.ecommerce.controllers;
//
//
//import com.backend.ecommerce.entity.ProductReview;
//import com.backend.ecommerce.implementation.ProductReviewServiceImplementation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/product/review")
//public class ProductReviewController {
//
//    private final ProductReviewServiceImplementation productReviewServiceImplementation;
//
//    @Autowired
//    public ProductReviewController(ProductReviewServiceImplementation productReviewServiceImplementation) {
//        this.productReviewServiceImplementation = productReviewServiceImplementation;
//    }
//
//    @PostMapping
//    public ResponseEntity<ProductReview> addProductReview(@RequestBody ProductReview productReview) {
//        ProductReview createdProductReview = productReviewServiceImplementation.create(productReview);
//        return new ResponseEntity<>(createdProductReview, HttpStatus.CREATED);
//    }
//}
