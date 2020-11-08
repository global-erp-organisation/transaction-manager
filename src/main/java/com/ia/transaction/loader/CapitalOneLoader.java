package com.ia.transaction.loader;

import com.ia.transaction.mapper.TransactionMapper;
import com.ia.transaction.model.CapitalOneCCTransaction;
import com.ia.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Capital One credit card implementation of the TransactionLoader interface
 */
@Component
@RequiredArgsConstructor
public class CapitalOneLoader implements TransactionLoader<File, CapitalOneCCTransaction> {

    private final TransactionRepository repository;
    private final TransactionMapper<File, CapitalOneCCTransaction> mapper;

    @Override
    public long load(File source) {
        return load(source, repository, mapper);
    }
}
