package com.ia.transaction.parser;

/**
 * Transaction parsing specification
 * @param <S> Source type
 * @param <O> Output type
 */
@FunctionalInterface
public interface TransactionParser<S,O> {
    /**
     * Parse the input source to output objects
     * @param input Source to parse
     * @return parsed result.
     */
    O parse(S input);
}
