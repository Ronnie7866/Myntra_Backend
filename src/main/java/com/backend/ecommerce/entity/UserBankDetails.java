//package com.backend.ecommerce.entity;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.hibernate.annotations.CreationTimestamp;
//
//import java.time.LocalDateTime;
//
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//public class UserBankDetails {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToOne
//    @JoinColumn(name = "user_id")
//    @JsonBackReference
//    private User user;
//
//    private String accountHolderName;
//    private String accountNumber;
//    private String bankName;
//    private String branchName;
//    private String ifscCode;
//    private String accountType;
//    private Boolean isPrimary;
//    private String status;
//
//    @CreationTimestamp
//    private LocalDateTime dateAdded;
//}
