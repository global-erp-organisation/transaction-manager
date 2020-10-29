package com.ia.transaction.loader;

import com.ia.transaction.model.DesjardinsCCTransaction;
import com.ia.transaction.model.DesjardinsEOPTransaction;
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
public class DesjardinsEOPLoader implements TransactionLoader<File, DesjardinsEOPTransaction> {

    private final TransactionParser<File, List<DesjardinsEOPTransaction>> parser;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public void load(File source) {
        parser.parse(source).stream().map(this::build)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(transactionRepository::saveAndFlush);
    }

    private Optional<Transaction> build(DesjardinsEOPTransaction tr) {
        return  build(tr, transactionRepository,categoryRepository, tr.getDescription(), tr.getCategory(), Transaction::map);
    }
}
