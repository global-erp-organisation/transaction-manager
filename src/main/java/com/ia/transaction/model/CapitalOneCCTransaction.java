package com.ia.transaction.model;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CapitalOneCCTransaction {
    @CsvBindByPosition(position = 0)
    private String transactionDate;
    @CsvBindByPosition(position = 1)
    private String postedDate;
    @CsvBindByPosition(position = 2)
    private String accountNumber;
    @CsvBindByPosition(position = 3)
    private String description;
    @CsvBindByPosition(position = 4)
    private String category;
    @CsvBindByPosition(position = 5)
    private String debit;
    @CsvBindByPosition(position = 6)
    private String credit;
}
