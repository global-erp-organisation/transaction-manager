package com.ia.transaction.mapper;

import com.ia.transaction.view.Transaction;

import java.util.List;
import java.util.Optional;

/**
 * Mapper specification interface
 * @param <S> data source type.
 * @param <O> raw data output type
 * @param <T> target data type
 */
public interface Mapper<S, O, T> {
    /**
     * Map the incoming raw item to a transaction object
     * @param raw Raw item that need to be converted.
     * @return The mapped transaction object.
     */
     Optional<T> map(O raw);

    /**
     * Map the source into a collection of target data type.
     * @param source data source.
     * @return Collection on target data type.
     */
     List<T> mapFrom(S source);
}
