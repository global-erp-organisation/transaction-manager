package com.ia.transaction;

import com.ia.transaction.loader.TransactionLoader;
import com.ia.transaction.model.CapitalOneCCTransaction;
import com.ia.transaction.model.DesjardinsCCTransaction;
import com.ia.transaction.model.DesjardinsEOPTransaction;
import com.ia.transaction.parser.TransactionParser;
import com.ia.transaction.repository.CategoryRepository;
import com.ia.transaction.repository.TransactionRepository;
import com.ia.transaction.view.Transaction;
import com.ia.transaction.view.TransactionCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@SpringBootApplication
@RequiredArgsConstructor
public class TransactionApplication implements CommandLineRunner {

    private final TransactionLoader<File, CapitalOneCCTransaction> coLoader;
    private final TransactionLoader<File, DesjardinsCCTransaction> desjCCLoader;
    private final TransactionLoader<File, DesjardinsEOPTransaction> desEOPLoader;

    public static void main(String[] args) {
        SpringApplication.run(TransactionApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        //Load capitale one credit card transactions
        final Path coFolder = Paths.get("/Users/mbsigne/Downloads/releves/co/2020-10-26_transaction_download.csv");
        coLoader.load(coFolder.toFile());
        //Load Desjardins checking account transactions.
        final Path folder = Paths.get("/Users/mbsigne/Downloads/releves/eop/releve.csv");
        desEOPLoader.load(folder.toFile());
        //Load Desjardins credit card transactions
        final Path dccfolder = Paths.get("/Users/mbsigne/Documents/projects/pocs/transaction/src/main/resources/data/releves/dcc");
        Files.newDirectoryStream(dccfolder).forEach(p -> desjCCLoader.load(p.toFile()));
    }
}
