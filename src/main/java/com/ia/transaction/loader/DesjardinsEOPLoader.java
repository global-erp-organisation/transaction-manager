package com.ia.transaction.loader;

import com.ia.transaction.mapper.TransactionMapper;
import com.ia.transaction.model.DesjardinsEOPTransaction;
import com.ia.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Desjardins checking account implementation of the TransactionLoader interface
 */
@Component
@RequiredArgsConstructor
public class DesjardinsEOPLoader implements TransactionLoader<File, DesjardinsEOPTransaction> {

    private final TransactionRepository repository;
    private final TransactionMapper<File, DesjardinsEOPTransaction> mapper;

    @Override
    public int load(File source) {
        return load(source, repository, mapper);
    }
}
