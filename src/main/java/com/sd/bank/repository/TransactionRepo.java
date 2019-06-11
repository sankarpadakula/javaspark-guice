package com.sd.bank.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import com.sd.bank.model.Transaction;

@Singleton
public class TransactionRepo {
  
  private final List<Transaction> allTransactions;
  private static long transSequence = 0;

  public TransactionRepo() {
    allTransactions = new ArrayList<>();
  }

  public Transaction save(Transaction transaction) {
    transaction.setId(transSequence++);
    allTransactions.add(transaction);
    return transaction;
  }

  public List<Transaction> find(long id) {
    return allTransactions.stream().filter(e -> e.getAccount() == id).collect(Collectors.toList());
  }

}
