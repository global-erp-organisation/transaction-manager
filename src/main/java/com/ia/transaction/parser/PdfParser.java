package com.ia.transaction.parser;

import com.ia.transaction.model.ReportProperties;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.slf4j.Logger;

import java.io.File;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface PdfParser<R> extends TransactionParser<File, R> {

    default Stream<String> extractContent(File source, Supplier<ReportProperties> prop, Supplier<Logger> log) {
        try (final PDDocument document = PDDocument.load(source)) {
            if (!document.isEncrypted()) {
                final PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                final PDFTextStripper tStripper = new PDFTextStripper();
                final String pdfFileInText = tStripper.getText(document);
                return Stream.of(pdfFileInText.split(prop.get().getLineDelimitationPattern())).filter(l -> l.length() > prop.get().getMinimunLineLen());
            } else {
                log.get().error("[{}] is actually encrypted and we were unable to extract the content.", source.getAbsolutePath());
                throw new IllegalArgumentException("The file " + source.getAbsolutePath() + "is encrypted");
            }
        } catch (Exception e) {
            log.get().error("Error occured during the file processing. message=[{}]", e.getLocalizedMessage(), e);
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }
}
