package com.ia.transaction.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@NoArgsConstructor
@Data
@AllArgsConstructor
public class TransactionCategory {
    @Id
    private String categoryId;
    private String categoryDescription;
}
