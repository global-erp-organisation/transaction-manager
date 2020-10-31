package com.ia.transaction.mapper;

import com.ia.transaction.repository.CategoryRepository;
import com.ia.transaction.repository.TransactionRepository;
import com.ia.transaction.view.Transaction;
import com.ia.transaction.view.TransactionCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@RequiredArgsConstructor
@Component
public class TransactionMapper<O> {

    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    public Optional<Transaction> map(O raw, Function<O, String> catFunction, Converter<O, Transaction.TransactionBuilder> converter) {
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
            final Optional<TransactionCategory> cat = categoryRepository.findByCategoryDescription(defaultCategory);
            return Optional.of(cat.map(c -> builder.category(c).build()).orElseGet(() -> {
                final TransactionCategory category = categoryRepository.save(TransactionCategory.builder()
                        .categoryId(UUID.randomUUID().toString())
                        .categoryDescription(defaultCategory)
                        .build());
                return builder.category(category).build();
            }));
        };
        return transactionMapper.apply(raw);
    }
}
