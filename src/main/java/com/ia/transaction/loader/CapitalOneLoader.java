package com.ia.transaction.loader;

import com.ia.transaction.mapper.TransactionMapper;
import com.ia.transaction.model.CapitalOneCCTransaction;
import com.ia.transaction.parser.TransactionParser;
import com.ia.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CapitalOneLoader implements TransactionLoader<File, CapitalOneCCTransaction> {

    private final TransactionParser<File, List<CapitalOneCCTransaction>> parser;
    private final TransactionRepository repository;
    private final TransactionMapper<CapitalOneCCTransaction> mapper;

    @Override
    public void load(File source) {
        load(source, parser, repository, mapper);
    }
}
