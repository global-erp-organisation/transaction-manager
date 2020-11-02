package com.ia.transaction.view;

import com.ia.transaction.model.CapitalOneCCTransaction;
import com.ia.transaction.model.DesjardinsCCTransaction;
import com.ia.transaction.model.DesjardinsEOPTransaction;
import jdk.nashorn.internal.runtime.options.Option;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transaction {
    @Id
    private String transactionId;
    private String transactionDate;
    private String transactionDescription;
    private BigDecimal transactionAmount;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    @JoinColumn
    @ManyToOne
    private TransactionCategory category;
    @Enumerated(EnumType.STRING)
    private TransactionScope scope;
    @Enumerated(EnumType.STRING)
    private Account account;

    public static TransactionBuilder map(CapitalOneCCTransaction from) {
        final BigDecimal amount = getAmount(from.getDebit(), from.getCredit());
        final TransactionType type = getTransactionType(from.getDebit(),from.getCredit());
        return Transaction.builder()
                .transactionAmount(amount)
                .transactionDescription(from.getDescription())
                .transactionDate(from.getTransactionDate())
                .scope(getScope(from.getCategory()))
                .account(Account.CAPITALE_ONE_CC)
                .transactionType(type);
    }

    public static TransactionBuilder map(DesjardinsCCTransaction from) {
        final BigDecimal amount = getAmount(from.getDebit(), from.getCredit());
        final TransactionType type = getTransactionType(from.getDebit(),from.getCredit());
        return Transaction.builder()
                .transactionAmount(amount)
                .transactionDescription(from.getDescription())
                .transactionDate(from.getTransactionDate())
                .scope(getScope(from.getCategory()))
                .account(Account.DESJARDINS_CC)
                .transactionType(type);
    }

    public static TransactionBuilder map(DesjardinsEOPTransaction from) {
        final BigDecimal amount = getAmount(from.getDebit().replace(",","."), from.getCredit().replace(",","."));
        final TransactionType type = getTransactionType(from.getDebit(),from.getCredit());
        return Transaction.builder()
                .transactionAmount(amount)
                .transactionDescription(from.getDescription())
                .transactionDate(from.getTransactionDate())
                .scope(getScope(from.getCategory()))
                .account(Account.DESJARDINS_EOP)
                .transactionType(type);
    }

    private static  BigDecimal getAmount(String debit, String credit) {
        final String amount = StringUtils.isEmpty(debit)? credit: debit;
       return StringUtils.isNotEmpty(amount) ? new BigDecimal(amount) : BigDecimal.ZERO;
    }

    private static  TransactionType getTransactionType(String debit, String credit) {
        return StringUtils.isEmpty(debit)? TransactionType.CREDIT: TransactionType.DEBIT;
    }

    private static TransactionScope getScope(String category) {
        final List<String> business = Arrays.asList("Gas/Automotive","Dining");
        return business.contains(category) ? TransactionScope.BUSINESS : TransactionScope.PERSONAL;
    }
}

