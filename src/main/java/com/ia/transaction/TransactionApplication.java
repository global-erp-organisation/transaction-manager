package com.ia.transaction;

import com.ia.transaction.model.CapitalOneCCTransaction;
import com.ia.transaction.model.DesjardinsCCTransaction;
import com.ia.transaction.model.DesjardinsEOPTransaction;
import com.ia.transaction.parser.TransactionParser;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class TransactionApplication implements CommandLineRunner {

    private final TransactionParser<File, List<DesjardinsCCTransaction>> pdfParser;
    private final TransactionParser<File, List<DesjardinsEOPTransaction>> eopParser;
    private final TransactionParser<File, List<CapitalOneCCTransaction>> coParser;

    public static void main(String[] args) {
        SpringApplication.run(TransactionApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        final Path folder = Paths.get("/Users/mbsigne/Downloads/releves/co/2020-10-26_transaction_download.csv");
        System.out.println(Files.exists(folder));
        //Files.newDirectoryStream(folder).forEach(p -> pdfParser.parse(p.toFile()).forEach(System.out::println));
        coParser.parse(folder.toFile()).forEach(System.out::println);
    }
}
