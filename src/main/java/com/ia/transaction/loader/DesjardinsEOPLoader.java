package com.ia.transaction.loader;

import com.ia.transaction.mapper.TransactionMapper;
import com.ia.transaction.model.DesjardinsEOPTransaction;
import com.ia.transaction.parser.TransactionParser;
import com.ia.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DesjardinsEOPLoader implements TransactionLoader<File, DesjardinsEOPTransaction> {

    private final TransactionParser<File, List<DesjardinsEOPTransaction>> parser;
    private final TransactionRepository repository;
    private final TransactionMapper<DesjardinsEOPTransaction> mapper;

    @Override
    public void load(File source) {
        load(source, parser, repository, mapper);
    }
}
