package com.ia.transaction;

import com.ia.transaction.loader.TransactionLoader;
import com.ia.transaction.model.CapitalOneCCTransaction;
import com.ia.transaction.model.DesjardinsCCTransaction;
import com.ia.transaction.model.DesjardinsEOPTransaction;
import com.ia.transaction.model.ReportProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
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

        final Map<Path, Integer> result = new HashMap<>();
        //capitale one credit card transactions
        final Path coFolder = loader.getResource(reportProperties.getLocations().getCapitaleOne()).getFile().toPath();
        //Desjardins checking account transactions.
        final Path eopFolder = loader.getResource(reportProperties.getLocations().getDesjardinsEop()).getFile().toPath();
        //Desjardins credit card transactions
        final Path dccfolder = loader.getResource(reportProperties.getLocations().getDesjardinsCc()).getFile().toPath();
        result.put(coFolder, Files.list(coFolder).parallel().mapToInt(p -> coLoader.load(p.toFile())).sum());
        result.put(eopFolder, Files.list(eopFolder).parallel().mapToInt(p -> desEOPLoader.load(p.toFile())).sum());
        result.put(dccfolder, Files.list(dccfolder).parallel().mapToInt(p -> desjCCLoader.load(p.toFile())).sum());
        result.forEach((k, v) -> log.info("{} transaction(s) have been loaded from {}", v, k));
    }
}
