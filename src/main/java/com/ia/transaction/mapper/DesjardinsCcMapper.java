package com.ia.transaction.mapper;

import com.ia.transaction.model.DesjardinsCCTransaction;
import com.ia.transaction.parser.TransactionParser;
import com.ia.transaction.repository.CategoryRepository;
import com.ia.transaction.repository.TransactionRepository;
import com.ia.transaction.view.Transaction;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Desjardins credit card implementation of TransactionMapper
 */
@Component
public class DesjardinsCcMapper extends TransactionMapper<File, DesjardinsCCTransaction> {

    public DesjardinsCcMapper(CategoryRepository categoryRepository, TransactionRepository transactionRepository, TransactionParser<File, List<DesjardinsCCTransaction>> parser) {
        super(categoryRepository, transactionRepository, parser);
    }

    @Override
    protected Optional<Transaction> map(DesjardinsCCTransaction raw) {
        return map(raw, DesjardinsCCTransaction::getCategory, Transaction::map);
    }
}
