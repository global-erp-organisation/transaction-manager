package com.ia.transaction.mapper;

import com.ia.transaction.model.DesjardinsEOPTransaction;
import com.ia.transaction.repository.CategoryRepository;
import com.ia.transaction.repository.TransactionRepository;
import com.ia.transaction.view.Transaction;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DesjardinsEopMapper extends TransactionMapper<DesjardinsEOPTransaction> {
    public DesjardinsEopMapper(CategoryRepository categoryRepository, TransactionRepository transactionRepository) {
        super(categoryRepository, transactionRepository);
    }

    @Override
    public Optional<Transaction> map(DesjardinsEOPTransaction raw) {
        return map(raw, DesjardinsEOPTransaction::getCategory, Transaction::map);
    }
}
