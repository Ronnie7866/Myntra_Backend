package com.backend.ecommerce.service;

import com.backend.ecommerce.entity.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getAllTransactions();

    Transaction getTransactionById(Long id);

    Transaction saveTransaction(Transaction transaction);

    void deleteTransaction(Long id);

    Transaction updateTransaction(Transaction transaction, Long id);
}
