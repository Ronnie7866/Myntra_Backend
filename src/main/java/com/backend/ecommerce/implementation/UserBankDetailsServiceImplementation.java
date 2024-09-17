//package com.backend.ecommerce.implementation;
//
//import com.backend.ecommerce.entity.User;
//import com.backend.ecommerce.entity.UserBankDetails;
//import com.backend.ecommerce.exception.ResourceNotFoundException;
//import com.backend.ecommerce.repository.UserBankDetailsRepository;
//import com.backend.ecommerce.repository.UserRepository;
//import com.backend.ecommerce.service.UserBankDetailsService;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@AllArgsConstructor
//public class UserBankDetailsServiceImplementation implements UserBankDetailsService {
//
//    private final UserBankDetailsRepository repository;
//    private final UserRepository userRepository;
//
//    public List<UserBankDetails> getAllBankDetails() {
//        return repository.findAll();
//    }
//
//    public Optional<UserBankDetails> getBankDetailsById(Long id) {
//        return repository.findById(id);
//    }
//
//    public List<UserBankDetails> getBankDetailsByUserId(Long userId) {
//        return repository.findByUserId(userId);
//    }
//
//    public List<UserBankDetails> getPrimaryBankDetails(Boolean isPrimary) {
//        return repository.findByIsPrimary(isPrimary);
//    }
//
//    public List<UserBankDetails> getBankDetailsByStatus(String status) {
//        return repository.findByStatus(status);
//    }
//
//    public UserBankDetails saveBankDetails(Long userId, UserBankDetails bankDetails) {
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with this id: " + userId));
//        user.setUserBankDetails(bankDetails);
//        bankDetails.setUser(user);
//        return repository.save(bankDetails);
//    }
//
//    public void deleteBankDetails(Long id) {
//        repository.deleteById(id);
//    }
//}
