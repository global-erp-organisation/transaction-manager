package com.ia.transaction.loader;

import com.ia.transaction.mapper.TransactionMapper;
import com.ia.transaction.repository.TransactionRepository;

/**
 * Transaction loading specification
 * @param <S>  Source type
 * @param <O>  Ouput type
 */
@FunctionalInterface
public interface TransactionLoader<S, O> {

    /**
     * Load transaction from the specify source.
     * @param source Source where  transactions are loading from
     * @return The number of new transactions that have been loaded.
     */
    int load(S source);

    /**
     * Default loading operation
     * @param source Source where transactions are loading from
     * @param repository Repository that help to fetch or store transaction from or in the data storage.
     * @param mapper Mapper that help to map the extracted raw item to the transaction object.
     * @return The number of new transactions that have been loaded.
     */
    default int load(S source, TransactionRepository repository, TransactionMapper<S, O> mapper) {
        return repository.saveAll(mapper.mapFrom(source)).size();
    }
}
