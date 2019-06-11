package com.sd.bank.service;

import java.util.List;

import com.sd.bank.dto.TransferRequest;
import com.sd.bank.dto.TransferResult;
import com.sd.bank.model.Account;
import com.sd.bank.model.Transaction;

public interface AccountService {

  Account createAccount(Account account);

  Account update(Account account);
  
  Account getAccount(String sortCode, String accountNumber);
  
  List<Account> getAccounts();
  
  TransferResult transfer(TransferRequest request);
  
  List<Transaction> getTransactions(String sortCode, String accountNumber);
}
