package com.ia.transaction.parser;

import com.ia.transaction.model.DesjardinsEOPTransaction;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DesjardinsEopParser implements CsvParser<DesjardinsEOPTransaction> {
    @Override
    public List<DesjardinsEOPTransaction> parse(File input) {
        return parse(input, () -> DesjardinsEOPTransaction.class)
                .stream().filter(t -> StringUtils.isNotEmpty(t.getAccountType())).collect(Collectors.toList());
    }
}