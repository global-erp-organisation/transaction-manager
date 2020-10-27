package com.ia.transaction.parser;


import java.io.FileNotFoundException;

@FunctionalInterface
public interface TransactionParser<T,R> {
    R parse(T input);
}
