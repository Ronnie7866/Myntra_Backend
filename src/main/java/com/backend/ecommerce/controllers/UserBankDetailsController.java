//package com.backend.ecommerce.controllers;
//
//import com.backend.ecommerce.entity.UserBankDetails;
//import com.backend.ecommerce.service.UserBankDetailsService;
//import lombok.AllArgsConstructor;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/addBankDetails")
//@AllArgsConstructor
//public class UserBankDetailsController {
//
//    private final UserBankDetailsService userBankDetailsService;
//
//    @GetMapping
//    public List<UserBankDetails> getAllBankDetails() {
//        return userBankDetailsService.getAllBankDetails();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<UserBankDetails> getBankDetailsById(@PathVariable Long id) {
//        Optional<UserBankDetails> bankDetails = userBankDetailsService.getBankDetailsById(id);
//        return bankDetails.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/user/{userId}")
//    public List<UserBankDetails> getBankDetailsByUserId(@PathVariable Long userId) {
//        return userBankDetailsService.getBankDetailsByUserId(userId);
//    }
//
//    @GetMapping("/primary/{isPrimary}")
//    public List<UserBankDetails> getPrimaryBankDetails(@PathVariable Boolean isPrimary) {
//        return userBankDetailsService.getPrimaryBankDetails(isPrimary);
//    }
//
//    @GetMapping("/status/{status}")
//    public List<UserBankDetails> getBankDetailsByStatus(@PathVariable String status) {
//        return userBankDetailsService.getBankDetailsByStatus(status);
//    }
//
//    @PostMapping("/{id}")
//    public UserBankDetails saveBankDetails(@PathVariable Long id, @RequestBody UserBankDetails bankDetails) {
//        return userBankDetailsService.saveBankDetails(id, bankDetails);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteBankDetails(@PathVariable Long id) {
//        userBankDetailsService.deleteBankDetails(id);
//        return ResponseEntity.noContent().build();
//    }
//}
