package com.backend.ecommerce.implementation;

import com.backend.ecommerce.entity.Transaction;
import com.backend.ecommerce.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionServiceImplementation implements com.backend.ecommerce.service.TransactionService {

    TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public Transaction updateTransaction(Transaction transaction, Long id) {
        Optional<Transaction> byId = transactionRepository.findById(id);
        if (byId.isPresent()) {
            Transaction updatedTransaction = transactionRepository.save(transaction);
            updatedTransaction.setTransactionAmount(transaction.getTransactionAmount());
            updatedTransaction.setTransactionType(transaction.getTransactionType());
            updatedTransaction.setTransactionStatus(transaction.getTransactionStatus());
            return transactionRepository.save(updatedTransaction);
        } else {
            throw new RuntimeException("Transaction not found");
        }
    }



}
