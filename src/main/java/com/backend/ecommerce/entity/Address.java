package com.backend.ecommerce.entity;

import com.backend.ecommerce.enums.PhoneType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String addressType;
    private String streetAddress;
    private String pinCode;

    @Enumerated(EnumType.STRING)
    private PhoneType phoneType;

    @ManyToOne(cascade = CascadeType.ALL, fetch =  FetchType.EAGER)
    @JsonBackReference
    private User user;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JsonIgnore
//    @JoinColumn(name = "buyer_id")
//    private Buyer buyer;
}
