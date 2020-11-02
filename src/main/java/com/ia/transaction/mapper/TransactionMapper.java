package com.ia.transaction.mapper;

import com.ia.transaction.view.Transaction.TransactionBuilder;
import com.ia.transaction.parser.TransactionParser;
import com.ia.transaction.repository.CategoryRepository;
import com.ia.transaction.repository.TransactionRepository;
import com.ia.transaction.view.Transaction;
import com.ia.transaction.view.TransactionCategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public abstract class TransactionMapper<S, O> {
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionParser<S, List<O>> parser;

    public List<Transaction> mapFrom(S source) {
        final List<Transaction> transactions = parser.parse(source).stream().map(this::map).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
        log.info("{} new transaction(s) have been extracted from {}", transactions.size(), source);
        return transactions;
    }

    protected abstract Optional<Transaction> map(O raw);

    protected Optional<Transaction> map(O raw, Function<O, String> catFunction, Converter<O, TransactionBuilder> converter) {
        final TransactionBuilder builder = Objects.requireNonNull(converter.convert(raw)).transactionId(UUID.randomUUID().toString());
        final Transaction sample = builder.build();
        if (transactionRepository.findFirstByTransactionAmountAndTransactionDateAndTransactionDescriptionAndTransactionType(
                sample.getTransactionAmount(),
                sample.getTransactionDate(),
                sample.getTransactionDescription(),
                sample.getTransactionType()).isPresent())
            return Optional.empty();
        final String defaultCategory = catFunction.apply(raw);
        final Optional<TransactionCategory> cat = categoryRepository.findFirstByCategoryDescription(defaultCategory);
        return Optional.of(cat.map(c -> builder.category(c).build()).orElseGet(() -> {
            final TransactionCategory category = categoryRepository.save(TransactionCategory.builder()
                    .categoryId(UUID.randomUUID().toString())
                    .categoryDescription(defaultCategory)
                    .build());
            return builder.category(category).build();
        }));
    }
}