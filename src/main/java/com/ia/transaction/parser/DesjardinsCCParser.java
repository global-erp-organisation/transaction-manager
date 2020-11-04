package com.ia.transaction.parser;

import com.ia.transaction.model.DesjardinsCCTransaction;
import com.ia.transaction.model.ReportProperties;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Desjardins credit card implementation of the PdfParser interface
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class DesjardinsCCParser implements PdfParser<List<DesjardinsCCTransaction>> {

    private final ReportProperties properties;

    @Override
    public List<DesjardinsCCTransaction> parse(File input) {
        return proceed(input);
    }

    private List<DesjardinsCCTransaction> proceed(File file) {
        final Function<String, String> extractMonth = line -> line.substring(properties.getMonthBeginingIndex(), properties.getMonthEndingIndex());
        final int month = getDateFromFile(file.getName()).getMonthValue();
        final Predicate<String> trFilter = line -> Integer.parseInt(extractMonth.apply(line)) == month;
        final Stream<String> lines = extractContent(file, properties::getLineDelimitationPattern, () -> log).filter(l -> l.length() > properties.getMinimunLineLen()).filter(l -> StringUtils.isNumeric(extractMonth.apply(l))).filter(trFilter);
        return lines.map(l -> parseToRawTransaction(l, file)).collect(Collectors.toList());
    }

    private DesjardinsCCTransaction parseToRawTransaction(String line, File file) {
        final int year = getDateFromFile(file.getName()).getYear();
        final Predicate<String> toKeep = l -> !l.isEmpty();
        final String[] tr = Stream.of(line.split(StringUtils.SPACE)).map(String::trim).filter(toKeep).collect(Collectors.joining(StringUtils.SPACE)).split(StringUtils.SPACE);
        final String desc = String.join(StringUtils.SPACE, Arrays.copyOfRange(tr, properties.getDescriptionBeginingIndex(), tr.length - 1));
        final String amount = tr[tr.length - 1].replace(",", ".");
        final String trDate = String.join(StringUtils.EMPTY, Arrays.copyOfRange(tr, properties.getDateBeginingIndex(), properties.getDateEndingIndex())) + year;
        final String regDate = String.join(StringUtils.EMPTY, Arrays.copyOfRange(tr, 2, 4)) + year;
        final String trNumber = tr[properties.getTransactionNumberIndex()];
        final String crIndicator = properties.getCreditAmountIndicator();
        final String debit = amount.endsWith(crIndicator) ? StringUtils.EMPTY : amount;
        final String credit = amount.endsWith(crIndicator) ? amount.replace(crIndicator, StringUtils.EMPTY) : StringUtils.EMPTY;
        return DesjardinsCCTransaction.builder().registerDate(regDate).transactionAmount(amount)
                .debit(debit)
                .credit(credit)
                .category(properties.getDefaultCategoryDesc())
                .transactionDate(trDate).description(desc).transactionNumber(trNumber).build();
    }

    private LocalDate getDateFromFile(String fileName) {
        final String stringDate = fileName.substring(fileName.indexOf("_") + 1, fileName.length() - properties.getExtensionLenIndex());
        final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        final LocalDate date = LocalDate.parse(stringDate, df).minusMonths(1);
        final YearMonth ym = YearMonth.of(date.getYear(), date.getMonthValue());
        return LocalDate.of(date.getYear(), date.getMonthValue(), ym.lengthOfMonth());
    }
}
