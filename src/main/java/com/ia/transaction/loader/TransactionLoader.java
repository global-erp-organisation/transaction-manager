package com.ia.transaction.loader;

import com.ia.transaction.mapper.TransactionMapper;
import com.ia.transaction.repository.TransactionRepository;

@FunctionalInterface
public interface TransactionLoader<S, O> {

    int load(S source);

    default int load(S source, TransactionRepository repository, TransactionMapper<S, O> mapper) {
        return repository.saveAll(mapper.mapFrom(source)).size();
    }
}
