package com.ia.transaction;

import com.ia.transaction.loader.TransactionLoader;
import com.ia.transaction.model.CapitalOneCCTransaction;
import com.ia.transaction.model.DesjardinsCCTransaction;
import com.ia.transaction.model.DesjardinsEOPTransaction;
import com.ia.transaction.model.ReportProperties;
import com.ia.transaction.parser.TransactionParser;
import com.ia.transaction.repository.CategoryRepository;
import com.ia.transaction.repository.TransactionRepository;
import com.ia.transaction.view.Transaction;
import com.ia.transaction.view.TransactionCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;

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
    private final ReportProperties reportProperties;
    private final ResourceLoader loader;

    public static void main(String[] args) {
        SpringApplication.run(TransactionApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        //Load capitale one credit card transactions
        final Path coFolder = loader.getResource(reportProperties.getLocations().getCapitaleOne()).getFile().toPath();
        Files.newDirectoryStream(coFolder).forEach(p -> coLoader.load(p.toFile()));
        //Load Desjardins checking account transactions.
        final Path eopFolder = loader.getResource(reportProperties.getLocations().getDesjardinsEop()).getFile().toPath();
        Files.newDirectoryStream(eopFolder).forEach(p -> desEOPLoader.load(p.toFile()));
        //Load Desjardins credit card transactions
        final Path dccfolder = loader.getResource(reportProperties.getLocations().getDesjardinsCc()).getFile().toPath();
        Files.newDirectoryStream(dccfolder).forEach(p -> desjCCLoader.load(p.toFile()));
    }
}
