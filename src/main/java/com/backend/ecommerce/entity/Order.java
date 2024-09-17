package com.backend.ecommerce.entity;


import com.backend.ecommerce.enums.AvailabilityStatus;
import com.backend.ecommerce.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    private LocalDateTime orderDate;
//    private LocalDateTime updatedAt;

//    @Enumerated(EnumType.STRING)
//    private AvailabilityStatus availabilityStatus;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToOne
    @JsonBackReference
    private User user;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Transaction transaction;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @JsonManagedReference
    private List<OrderProducts> orderProducts = new ArrayList<>();

//    @ManyToOne
//    private Buyer buyer;


    @CreationTimestamp
    @Column(name = "createdat", nullable = false)
    private LocalDateTime createdat;

    @UpdateTimestamp
    @Column(name = "modifiedat", nullable = false)
    private LocalDateTime modifiedate;

//    @Transient
//    private Long userId = user.getId();
//
//    @Transient
//    private Long transactionId = transaction.getId();
}
