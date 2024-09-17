package com.backend.ecommerce.repository;

import com.backend.ecommerce.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
