package com.ia.transaction.parser;

import com.ia.transaction.model.DesjardinsEOPTransaction;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public interface CsvParser<R> extends TransactionParser<File, List<R>> {

    default List<R> parse(File input, Supplier<Class<R>> beanType, IntSupplier lineToSkip) {
        try (Reader reader = new FileReader(input.getAbsolutePath())) {
            return new CsvToBeanBuilder<R>(reader)
                    .withSkipLines(lineToSkip.getAsInt())
                    .withType(beanType.get())
                    .build().parse();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
