package com.ia.transaction.parser;

import com.ia.transaction.model.DesjardinsEOPTransaction;
import com.ia.transaction.model.ReportProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Desjardins checking account implementation of the CsvParser interface
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DesjardinsEopParser implements CsvParser<DesjardinsEOPTransaction> {
    private final ReportProperties properties;
    @Override
    public List<DesjardinsEOPTransaction> parse(File input) {
        return parse(input, () -> DesjardinsEOPTransaction.class, () -> 0, () -> log)
                .stream().filter(t -> StringUtils.isNotEmpty(t.getAccountType()))
                .peek(t->t.setCategory(properties.getDefaultCategoryDesc()))
                .collect(Collectors.toList());
    }
}