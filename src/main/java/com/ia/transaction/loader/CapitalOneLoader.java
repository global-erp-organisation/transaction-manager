package com.ia.transaction.loader;

import com.ia.transaction.model.CapitalOneCCTransaction;
import com.ia.transaction.parser.TransactionParser;
import com.ia.transaction.repository.CategoryRepository;
import com.ia.transaction.repository.TransactionRepository;
import com.ia.transaction.view.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CapitalOneLoader implements TransactionLoader<File, CapitalOneCCTransaction> {

    private final TransactionParser<File, List<CapitalOneCCTransaction>> parser;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public void load(File source) {
        load(source, parser, transactionRepository, categoryRepository, CapitalOneCCTransaction::getCategory, Transaction::map);
    }
}
