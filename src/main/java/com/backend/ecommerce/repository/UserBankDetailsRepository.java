//package com.backend.ecommerce.repository;
//
//import com.backend.ecommerce.entity.User;
//import com.backend.ecommerce.entity.UserBankDetails;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface UserBankDetailsRepository extends JpaRepository<UserBankDetails, Long> {
//    List<UserBankDetails> findByUserId(Long userId);
//    List<UserBankDetails> findByIsPrimary(boolean isPrimary);
//    List<UserBankDetails> findByStatus(String status);
//    Optional<UserBankDetails> findByUserIdAndIsPrimaryTrue(Long userId);
//}
