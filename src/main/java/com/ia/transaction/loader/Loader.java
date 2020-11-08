package com.ia.transaction.loader;

import com.ia.transaction.mapper.Mapper;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Loader specification interface
 *
 * @param <S> data source type.
 * @param <O> raw data output type
 * @param <T> target data type
 */
public interface Loader<S, O, T> {
    /**
     * Load transaction from the specify source.
     *
     * @param source Source where  data are loading from
     * @return The number of new records that have been loaded.
     */
    long load(S source);

    /**
     * Default loading operation
     *
     * @param source     Source where records are loading from
     * @param repository Repository that help to fetch or store transaction from or in the data storage.
     * @param mapper     Mapper that help to map the extracted raw item to the target type object.
     * @return The number of new records that have been loaded.
     */
    default long load(S source, CrudRepository<T, String> repository, Mapper<S, O, T> mapper) {
        final Iterable<T> loaded = repository.saveAll(mapper.mapFrom(source));
        return StreamSupport.stream(loaded.spliterator(), false).count();
    }
}
