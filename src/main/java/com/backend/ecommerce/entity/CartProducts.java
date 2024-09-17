package com.backend.ecommerce.entity;

import com.backend.ecommerce.enums.AvailabilityStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private Cart cart;

    @OneToOne(cascade = CascadeType.ALL)
    private Product product;

    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private AvailabilityStatus availabilityStatus;

    @Override
    public String toString() {
        return "CartProducts{" +
                "id=" + id +
                ", cart=" + "Cart Value Omitted" +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
