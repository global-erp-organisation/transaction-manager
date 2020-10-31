package com.ia.transaction.loader;

import com.ia.transaction.mapper.TransactionMapper;
import com.ia.transaction.model.DesjardinsCCTransaction;
import com.ia.transaction.parser.TransactionParser;
import com.ia.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DesjardinsCCLoader implements  TransactionLoader<File, DesjardinsCCTransaction> {

    private final TransactionParser<File, List<DesjardinsCCTransaction>> parser;
    private final TransactionRepository repository;
    private final TransactionMapper<DesjardinsCCTransaction> mapper;

    @Override
    public void load(File source) {
        load(source, parser, repository, mapper);
    }
}
