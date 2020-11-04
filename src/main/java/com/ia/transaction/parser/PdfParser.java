package com.ia.transaction.parser;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.slf4j.Logger;

import java.io.File;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Portal document file parsing specification.
 * @param <O> output type
 */
public interface PdfParser<O> extends TransactionParser<File, O> {
    /**
     * Default parsing operation that extract the content of pdf file into a stream of lines.
     * @param source file that need to be parsed.
     * @param delimit file line delimitation supplier function.
     * @param log logger supplier
     * @return Stream of string that encapsulate all the line extracted from the file.
     */
    default Stream<String> extractContent(File source, Supplier<String> delimit, Supplier<Logger> log) {
        try (final PDDocument document = PDDocument.load(source)) {
            if (!document.isEncrypted()) {
                final PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                final PDFTextStripper tStripper = new PDFTextStripper();
                final String pdfFileInText = tStripper.getText(document);
                return Stream.of(pdfFileInText.split(delimit.get()));
            } else {
                log.get().error("[{}] is actually encrypted and we were unable to extract the content.", source.getAbsolutePath());
                throw new IllegalArgumentException("The file " + source.getAbsolutePath() + "is encrypted");
            }
        } catch (Exception e) {
            log.get().error("Error occurred during the file processing. message=[{}]", e.getLocalizedMessage(), e);
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }
}
