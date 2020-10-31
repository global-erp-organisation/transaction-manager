package com.ia.transaction.mapper;

import com.ia.transaction.model.CapitalOneCCTransaction;
import com.ia.transaction.model.DesjardinsCCTransaction;
import com.ia.transaction.repository.CategoryRepository;
import com.ia.transaction.repository.TransactionRepository;
import com.ia.transaction.view.Transaction;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DesjardinsCcMapper extends TransactionMapper<DesjardinsCCTransaction> {
    public DesjardinsCcMapper(CategoryRepository categoryRepository, TransactionRepository transactionRepository) {
        super(categoryRepository, transactionRepository);
    }

    @Override
    public Optional<Transaction> map(DesjardinsCCTransaction raw) {
        return map(raw, DesjardinsCCTransaction::getCategory, Transaction::map);
    }
}
