package com.ia.transaction.parser;


@FunctionalInterface
public interface TransactionParser<T,R> {
    R parse(T input);
}
