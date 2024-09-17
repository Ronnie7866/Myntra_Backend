package com.backend.ecommerce.dto;


import com.backend.ecommerce.entity.OrderProducts;
import com.backend.ecommerce.entity.Transaction;
import com.backend.ecommerce.entity.User;
import com.backend.ecommerce.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CheckoutRequest {

    private Long userId;
    private List<Long> orderProductsId;
    private TransactionType transactionType;
    private BigDecimal transactionAmount;
}
