package com.backend.ecommerce.entity;

import com.backend.ecommerce.enums.TransactionStatus;
import com.backend.ecommerce.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private TransactionStatus transactionStatus;

    private BigDecimal transactionAmount;

//    @ElementCollection
//    private List<String> productIds = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "createdat", nullable = false)
    private LocalDateTime createdat;

    @UpdateTimestamp
    @Column(name = "modifiedat", nullable = false)
    private LocalDateTime modifiedat;

    @ManyToOne
    @JsonBackReference
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private Order order;
}
