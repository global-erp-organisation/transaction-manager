package com.ia.transaction.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DesjardinsEOPTransaction {
    @CsvBindByPosition(position = 0)
    private String accountLocation;
    @CsvBindByPosition(position = 1)
    private String institutionFolio;
    @CsvBindByPosition(position = 2)
    private String accountType;
    @CsvBindByPosition(position = 3)
    private String transactionDate;
    @CsvBindByPosition(position = 4)
    private String transactionNumber;
    @CsvBindByPosition(position = 5)
    private String description;
    @CsvBindByPosition(position = 7)
    private String debit;
    @CsvBindByPosition(position = 8)
    private String credit;
    @CsvBindByPosition(position = 13)
    private String balance;
    @Builder.Default
    private String category = "NA";
}
