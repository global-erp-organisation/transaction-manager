package com.ia.transaction.parser;

import com.ia.transaction.model.DesjardinsCCTransaction;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
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

@Component
public class DesjardinsCCParser implements  TransactionParser<File, List<DesjardinsCCTransaction>> {

    @Override
    public List<DesjardinsCCTransaction> parse(File input) {
        return proceed(input);
    }

    private List<DesjardinsCCTransaction> proceed(File file) {
        try (final PDDocument document = PDDocument.load(file)) {
            final Function<String, String> extractMonth = line -> line.substring(3, 5);
            final int month = getDateFromFile(file.getName()).getMonthValue();
            final Predicate<String> trFilter = line -> Integer.parseInt(extractMonth.apply(line)) == month;
            if (!document.isEncrypted()) {
                final PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                final PDFTextStripper tStripper = new PDFTextStripper();
                final String pdfFileInText = tStripper.getText(document);
                final Stream<String> lines = Stream.of(pdfFileInText.split("\\r?\\n")).filter(l -> l.length() > 6).filter(l -> StringUtils.isNumeric(extractMonth.apply(l))).filter(trFilter);
                return lines.map(l -> parseToRawTransaction(l, file)).collect(Collectors.toList());
            } else {
                throw new IllegalArgumentException("The file " + file.getAbsolutePath() + "is encrypted");
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage(),e);
        }
    }

    private DesjardinsCCTransaction parseToRawTransaction(String line, File file) {
        final int year = getDateFromFile(file.getName()).getYear();
        final Predicate<String> toKeep = l -> !l.isEmpty();
        final String[] tr = Stream.of(line.split(StringUtils.SPACE)).map(String::trim).filter(toKeep).collect(Collectors.joining(StringUtils.SPACE)).split(StringUtils.SPACE);
        final String desc = String.join(StringUtils.SPACE, Arrays.copyOfRange(tr, 5, tr.length - 1));
        final String amount = tr[tr.length - 1];
        final String trDate = String.join(StringUtils.EMPTY, Arrays.copyOfRange(tr, 0, 2)) + year;
        final String regDate = String.join(StringUtils.EMPTY, Arrays.copyOfRange(tr, 2, 4)) + year;
        final String trNumber = tr[4];
        return DesjardinsCCTransaction.builder().registerDate(regDate).transactionAmount(amount).transactionDate(trDate).transactionDescription(desc).transactionNumber(trNumber).build();
    }

    private LocalDate getDateFromFile(String fileName) {
        final String stringDate = fileName.substring(fileName.indexOf("_") + 1, fileName.length() - 4);
        final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
        final LocalDate date = LocalDate.parse(stringDate, df).minusMonths(1);
        final YearMonth ym = YearMonth.of(date.getYear(), date.getMonthValue());
        return LocalDate.of(date.getYear(), date.getMonthValue(), ym.lengthOfMonth());
    }

}
