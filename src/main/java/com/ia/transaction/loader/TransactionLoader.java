package com.ia.transaction.loader;

import com.ia.transaction.mapper.TransactionMapper;
import com.ia.transaction.parser.TransactionParser;
import com.ia.transaction.repository.TransactionRepository;
import com.ia.transaction.view.Transaction;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@FunctionalInterface
public interface TransactionLoader<S, O> {

    void load(S source);

    default void load(S source, TransactionParser<S, List<O>> parser, TransactionRepository repository,
                      Function<O, String> catFunction, TransactionMapper<O> mapper, Converter<O, Transaction.TransactionBuilder> converter) {
        parser.parse(source).stream()
                .map(o -> mapper.map(o, catFunction, converter))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(repository::saveAndFlush);
    }
}
