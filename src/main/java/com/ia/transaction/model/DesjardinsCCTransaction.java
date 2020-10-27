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
    private String transactionDescription;
    private String transactionAmount;
}
