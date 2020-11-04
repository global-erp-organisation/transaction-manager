package com.ia.transaction.mapper;

import com.ia.transaction.model.DesjardinsEOPTransaction;
import com.ia.transaction.parser.TransactionParser;
import com.ia.transaction.repository.CategoryRepository;
import com.ia.transaction.repository.TransactionRepository;
import com.ia.transaction.view.Transaction;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Desjardins checking account implementation of TransactionMapper
 */
@Component
public class DesjardinsEopMapper extends TransactionMapper<File, DesjardinsEOPTransaction> {


    public DesjardinsEopMapper(CategoryRepository categoryRepository, TransactionRepository transactionRepository, TransactionParser<File, List<DesjardinsEOPTransaction>> parser) {
        super(categoryRepository, transactionRepository, parser);
    }

    @Override
    protected Optional<Transaction> map(DesjardinsEOPTransaction raw) {
        return map(raw, DesjardinsEOPTransaction::getCategory, Transaction::map);
    }
}
