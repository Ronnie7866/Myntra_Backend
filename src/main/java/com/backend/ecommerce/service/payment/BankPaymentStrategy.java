//package com.backend.ecommerce.service.payment;
//
//import com.backend.ecommerce.entity.User;
//import com.backend.ecommerce.entity.UserBankDetails;
//import com.backend.ecommerce.repository.UserBankDetailsRepository;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//
//@Service
//public class BankPaymentStrategy implements PaymentStrategy {
//
//    private final UserBankDetailsRepository userBankDetailsRepository;
//
//    public BankPaymentStrategy(UserBankDetailsRepository userBankDetailsRepository) {
//        this.userBankDetailsRepository = userBankDetailsRepository;
//    }
//
//    @Override
//    public boolean processPayment(User user, BigDecimal amount) {
//        UserBankDetails primaryBankDetails = userBankDetailsRepository.findByUserIdAndIsPrimaryTrue(user.getId())
//                .orElseThrow(() -> new RuntimeException("Primary Bank Details Not Found"));
//        // Simulate bank transaction processing
//        System.out.println("Processing bank transaction for account: " + primaryBankDetails.getAccountNumber() + " Amount: " + amount);
//        return true;  // Assume success for simulation
//    }
//}
