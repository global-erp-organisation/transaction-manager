package com.ia.transaction.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class DesjardinsCCTransaction {
    private String transactionNumber;
    private String transactionDate;
    private String registerDate;
    private String description;
    private String transactionAmount;
    private String debit;
    private String credit;
    @Builder.Default
    private String category = "NA";
}
