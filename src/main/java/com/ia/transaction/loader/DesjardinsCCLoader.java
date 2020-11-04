package com.ia.transaction.loader;

import com.ia.transaction.mapper.TransactionMapper;
import com.ia.transaction.model.DesjardinsCCTransaction;
import com.ia.transaction.parser.TransactionParser;
import com.ia.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

/**
 * Desjardins credit card implementation of the TransactionLoader interface
 */
@Component
@RequiredArgsConstructor
public class DesjardinsCCLoader implements  TransactionLoader<File, DesjardinsCCTransaction> {

    private final TransactionRepository repository;
    private final TransactionMapper<File, DesjardinsCCTransaction> mapper;

    @Override
    public int load(File source) {
        return load(source, repository, mapper);
    }
}
