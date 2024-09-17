//package com.backend.ecommerce.implementation;
//
//import com.backend.ecommerce.entity.ProductReview;
//import com.backend.ecommerce.exception.ProductReviewNotFoundException;
//import com.backend.ecommerce.repository.ProductReviewRepository;
//import com.backend.ecommerce.service.ProductReviewService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Transactional
//@Service
//public class ProductReviewServiceImplementation implements ProductReviewService {
//
//    private final ProductReviewRepository productReviewRepository;
//
//    @Autowired
//    public ProductReviewServiceImplementation(ProductReviewRepository productReviewRepository) {
//        this.productReviewRepository = productReviewRepository;
//    }
//
//    @Override
//    public ProductReview create(ProductReview productReview) {
//        return productReviewRepository.save(productReview);
//    }
//
//    @Override
//    public ProductReview update(int productReviewId, ProductReview productReview) {
//        ProductReview existingProductReview = productReviewRepository.findById(productReviewId)
//                .orElseThrow(() -> new ProductReviewNotFoundException("Product review not found with this id: " + productReviewId));
//        existingProductReview.setComment(productReview.getComment());
//        existingProductReview.setRating(productReview.getRating());
//        return productReviewRepository.save(existingProductReview);
//    }
//
//    @Override
//    public void delete(int productReviewId) {
//        productReviewRepository.deleteById(productReviewId);
//    }
//
//    @Override
//    public List<ProductReview> findByProductId(Long productId) {
//        return productReviewRepository.findByProductId(productId);
//    }
//
//    @Override
//    public List<ProductReview> findByUserId(Long userId) {
//        return productReviewRepository.findByUserId(userId);
//    }
//}
