package com.ia.transaction.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/**
 * csv file parsing specification
 * @param <O> Output type
 */
public interface CsvParser<O> extends TransactionParser<File, List<O>> {

    /**
     * Default parsing operation for csv files.
     * @param input file that need to be parsed.
     * @param beanType ouput object type.
     * @param lineToSkip number of lines to skip on top of the file.
     * @param loggerSupplier logger supplier.
     * @return Collection of parsed items.
     */
    default List<O> parse(File input, Supplier<Class<O>> beanType, IntSupplier lineToSkip, Supplier<Logger> loggerSupplier) {
        try (Reader reader = new FileReader(input.getAbsolutePath())) {
            return new CsvToBeanBuilder<O>(reader)
                    .withSkipLines(lineToSkip.getAsInt())
                    .withType(beanType.get())
                    .build().parse();
        } catch (Exception e) {
            loggerSupplier.get().error("Error occurred during the csv parsing. message =[{}]",e.getLocalizedMessage(), e);
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }
}
