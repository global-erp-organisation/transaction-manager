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
        final ReportProperties.Locations locations = reportProperties.getLocations();
        final Map<Path, Integer> result = new HashMap<>();
        //capitale one credit card path
        final Path coFolder = getPath(locations.getCapitaleOne());
        //Desjardins checking account path.
        final Path eopFolder = getPath(locations.getDesjardinsEop());
        //Desjardins credit card path
        final Path dccfolder = getPath(locations.getDesjardinsCc());
        result.put(coFolder, Files.list(coFolder).parallel().mapToInt(p -> coLoader.load(p.toFile())).sum());
        result.put(eopFolder, Files.list(eopFolder).parallel().mapToInt(p -> desEOPLoader.load(p.toFile())).sum());
        result.put(dccfolder, Files.list(dccfolder).parallel().mapToInt(p -> desjCCLoader.load(p.toFile())).sum());
        result.forEach((k, v) -> log.info("{} transaction(s) have been loaded from {}", v, k));
    }

    private Path getPath(String uri) throws Exception {
        return loader.getResource(uri).getFile().toPath();
    }
}
