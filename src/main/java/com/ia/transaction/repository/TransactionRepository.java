package com.ia.transaction.repository;

import com.ia.transaction.view.Transaction;
import com.ia.transaction.view.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
    Optional<Transaction> findByTransactionAmountAndTransactionDateAndTransactionDescriptionAndTransactionType(BigDecimal amount,
                                                                                                               String date, String description, TransactionType type);
}
