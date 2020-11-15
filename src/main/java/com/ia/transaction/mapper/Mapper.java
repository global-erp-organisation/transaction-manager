package com.ia.transaction.mapper;

import com.ia.transaction.parser.Parser;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Mapper specification interface
 *
 * @param <S> data source type.
 * @param <O> raw data output type
 * @param <T> target data type
 */
public interface Mapper<S, O, T> {
    /**
     * Map the incoming raw item to a target object
     *
     * @param raw Raw item that need to be converted.
     * @return The mapped target object.
     */
    Optional<T> map(O raw);

    /**
     * Map the source content into a collection of target data type.
     *
     * @param source data source.
     * @return Collection on target data type.
     */
    List<T> mapFrom(S source);

    /**
     * Map the source content into a collection of target data type.
     *
     * @param source data source.
     * @param parserSupplier parser supplier
     * @return Collection on target data type.
     */
    default List<T> mapFrom(S source, Supplier<Parser<S, List<O>>> parserSupplier) {
        return parserSupplier.get().parse(source).stream().map(this::map).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
    }
}
