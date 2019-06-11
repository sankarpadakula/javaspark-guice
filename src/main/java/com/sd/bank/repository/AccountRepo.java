package com.sd.bank.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Singleton;

import com.sd.bank.dto.SortCodeAccountNumber;
import com.sd.bank.model.Account;

@Singleton
public class AccountRepo {

  final List<Account> allAccounts;
  static long accSequence = 0;

  public AccountRepo() {
    allAccounts = new ArrayList<>();
    createAccount(1, "john", "SORT123", "123456", 100);
    createAccount(2, "max", "SORT124", "123457", 100);
    createAccount(3, "cla", "SORT125", "123458", 100);
  }

  public void createAccount(final long id, final String name, final String sortCode, final String accountNumber,
      final double balance) {
    final Account account = new Account();
    account.setId(id);
    account.setName(name);
    account.setSortCode(sortCode);
    account.setAccountNumber(accountNumber);
    account.setBalance(balance);
    account.setOpenDate(new Date());
    save(account);
  }

  public Account save(Account account) {
    if (account.getId() != null)
      allAccounts.remove(account);
    else {
      account.setId(accSequence++);
    }
    allAccounts.add(account);
    return account;
  }

  public List<Account> findAll() {
    return allAccounts;
  }

  public Account find(SortCodeAccountNumber account) {
    return find(account.getSortCode(), account.getAccountNumber());
  }

  public Account find(String sortCode, String accountNumber) {
    if (sortCode == null || sortCode.isEmpty() || accountNumber == null || accountNumber.isEmpty()) {
      return null;
    }
    return allAccounts.stream()
        .filter(e -> e.getSortCode().equals(sortCode) && e.getAccountNumber().equals(accountNumber)).findFirst()
        .orElse(null);
  }

}
