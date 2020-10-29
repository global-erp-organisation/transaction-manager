package com.ia.transaction.loader;

import com.ia.transaction.model.CapitalOneCCTransaction;
import com.ia.transaction.parser.TransactionParser;
import com.ia.transaction.repository.CategoryRepository;
import com.ia.transaction.repository.TransactionRepository;
import com.ia.transaction.view.Transaction;
import com.ia.transaction.view.TransactionCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CapitalOneLoader implements  TransactionLoader<File, CapitalOneCCTransaction>{

    private final TransactionParser<File, List<CapitalOneCCTransaction>> parser;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public void load(File source) {
        parser.parse(source).stream().map(this::build)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(transactionRepository::saveAndFlush);
    }

    private Optional<Transaction> build(CapitalOneCCTransaction tr) {
        return  build(tr, transactionRepository,categoryRepository, tr.getDescription(), tr.getCategory(), Transaction::map);
    }
}
