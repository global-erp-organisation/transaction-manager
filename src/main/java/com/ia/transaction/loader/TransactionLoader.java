package com.ia.transaction.loader;

import com.ia.transaction.model.CapitalOneCCTransaction;
import com.ia.transaction.model.DesjardinsCCTransaction;
import com.ia.transaction.parser.TransactionParser;
import com.ia.transaction.repository.CategoryRepository;
import com.ia.transaction.repository.TransactionRepository;
import com.ia.transaction.view.Transaction;
import com.ia.transaction.view.TransactionCategory;
import org.springframework.core.convert.converter.Converter;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
public interface TransactionLoader<S, O> {

    void load(S source);

    default void load(S source, TransactionParser<S, List<O>> parser, TransactionRepository transactionRepository, CategoryRepository repository, Function<O, String> catFunction, Converter<O, Transaction.TransactionBuilder> converter) {
        final Function<O, Optional<Transaction>> transactionMapper = tr -> {
            final Transaction.TransactionBuilder builder = Objects.requireNonNull(converter.convert(tr)).transactionId(UUID.randomUUID().toString());
            final Transaction sample = builder.build();
            if (transactionRepository.findByTransactionAmountAndTransactionDateAndTransactionDescriptionAndTransactionType(
                    sample.getTransactionAmount(),
                    sample.getTransactionDate(),
                    sample.getTransactionDescription(),
                    sample.getTransactionType()).isPresent())
                return Optional.empty();
            final String defaultCategory = catFunction.apply(tr);
            final Optional<TransactionCategory> cat = repository.findByCategoryDescription(defaultCategory);
            return Optional.of(cat.map(c -> builder.category(c).build()).orElseGet(() -> {
                final TransactionCategory category = repository.save(TransactionCategory.builder()
                        .categoryId(UUID.randomUUID().toString())
                        .categoryDescription(defaultCategory)
                        .build());
                return builder.category(category).build();
            }));
        };
        parser.parse(source).stream()
                .map(transactionMapper)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(transactionRepository::saveAndFlush);
    }
}
