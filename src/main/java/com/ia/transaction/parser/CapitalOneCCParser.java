package com.ia.transaction.parser;

import com.ia.transaction.model.CapitalOneCCTransaction;
import com.ia.transaction.model.DesjardinsEOPTransaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CapitalOneCCParser implements CsvParser<CapitalOneCCTransaction> {

    @Override
    public List<CapitalOneCCTransaction> parse(File input) {
        return parse(input, () -> CapitalOneCCTransaction.class, () -> 1, () -> log)
                .stream().filter(t -> StringUtils.isNotEmpty(t.getAccountNumber())).collect(Collectors.toList());
    }
}
