package com.ia.transaction.loader;

import com.ia.transaction.model.DesjardinsCCTransaction;
import com.ia.transaction.repository.CategoryRepository;
import com.ia.transaction.repository.TransactionRepository;
import com.ia.transaction.view.Transaction;
import com.ia.transaction.view.TransactionCategory;
import org.springframework.core.convert.converter.Converter;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;

@FunctionalInterface
public interface TransactionLoader<S, O> {

    void load(S source);

    default Optional<Transaction> build(O raw, TransactionRepository transactionRepository, CategoryRepository repository, String description, String defaultcategory, Converter<O, Transaction.TransactionBuilder> converter) {
        final Transaction.TransactionBuilder builder = Objects.requireNonNull(converter.convert(raw)).transactionId(UUID.randomUUID().toString());
        final Transaction sample = builder.build();
        if (transactionRepository.findByTransactionAmountAndTransactionDateAndTransactionDescriptionAndTransactionType(
                sample.getTransactionAmount(),
                sample.getTransactionDate(),
                sample.getTransactionDescription(),
                sample.getTransactionType()).isPresent())
            return Optional.empty();

        final Optional<TransactionCategory> cat = repository.findByCategoryDescription(defaultcategory);
        return Optional.of(cat.map(c -> builder.category(c).build()).orElseGet(() -> {
            final TransactionCategory category = repository.save(TransactionCategory.builder().categoryId(UUID.randomUUID().toString()).categoryDescription(defaultcategory).build());
            return builder.category(category).build();
        }));
    }
}
