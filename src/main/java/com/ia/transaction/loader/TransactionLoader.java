package com.ia.transaction.loader;

import com.ia.transaction.view.Transaction;

/**
 * Transaction loading specification
 * @param <S>  data source type
 * @param <O>  data output type
 */
@FunctionalInterface
public interface TransactionLoader<S, O>  extends  Loader<S, O, Transaction> {
}
