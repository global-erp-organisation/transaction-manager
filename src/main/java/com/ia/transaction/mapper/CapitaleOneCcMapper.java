package com.ia.transaction.mapper;

import com.ia.transaction.model.CapitalOneCCTransaction;
import com.ia.transaction.parser.Parser;
import com.ia.transaction.repository.CategoryRepository;
import com.ia.transaction.repository.TransactionRepository;
import com.ia.transaction.view.Transaction;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Capitale One implementation of TransactionMapper
 */
@Component
public class CapitaleOneCcMapper  extends  TransactionMapper<File, CapitalOneCCTransaction>{


    public CapitaleOneCcMapper(CategoryRepository categoryRepository, TransactionRepository transactionRepository, Parser<File, List<CapitalOneCCTransaction>> parser) {
        super(categoryRepository, transactionRepository, parser);
    }

    @Override
    public Optional<Transaction> map(CapitalOneCCTransaction raw) {
        return map(raw, CapitalOneCCTransaction::getCategory, Transaction::map);
    }
}
