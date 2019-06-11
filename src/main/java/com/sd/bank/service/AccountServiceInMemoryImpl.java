package com.sd.bank.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sd.bank.dto.TransferRequest;
import com.sd.bank.dto.TransferResult;
import com.sd.bank.exceptions.TransferFailureException;
import com.sd.bank.model.Account;
import com.sd.bank.model.Transaction;
import com.sd.bank.model.Transaction.TransactionType;
import com.sd.bank.repository.AccountRepo;
import com.sd.bank.repository.TransactionRepo;

@Singleton
public class AccountServiceInMemoryImpl implements AccountService {

  private static final Logger LOG = LoggerFactory.getLogger(AccountServiceInMemoryImpl.class);
  public static final String FROM_NOT_FOUND = "from.not-found";
  public static final String FROM_INSUFFICIENT_FUNDS = "from.insufficient-funds";
  public static final String TO_NOT_FOUND = "to.not-found";
  public static final String OPTIMISTIC_LOCKING = "optimistic-locking";

  @Inject
  private AccountRepo accountRepo;

  @Inject
  private TransactionRepo transactionRepo;

  @Override
  public Account createAccount(Account account) {
    if (account == null) {
      throw new IllegalArgumentException("Cannot create empty user.");
    }
    if (account.getSortCode() == null || account.getSortCode().isEmpty() || account.getAccountNumber() == null
        || account.getAccountNumber().isEmpty()) {
      throw new IllegalArgumentException("Invalid account details");
    }
    LOG.info("Account Creating for user " + account.getName());
    account.setOpenDate(new Date());
    return accountRepo.save(account);
  }

  public Transaction createTransaction(Transaction transaction) {
    if (transaction == null) {
      throw new IllegalArgumentException("Cannot create empty transaction.");
    }
    if (transaction.getAmount() < 1) {
      throw new IllegalArgumentException("transaction must be positive.");
    }
    if (transaction.getAccount() == 0) {
      throw new IllegalArgumentException("Invalid account details");
    }
    LOG.info("Transaction Creating for account " + transaction.getAccount());
    transaction.setIssuedAt(new Date());
    return transactionRepo.save(transaction);
  }

  @Override
  public Account getAccount(String sortCode, String accountNumber) {
    LOG.info("Searching account of " + accountNumber + "[" + sortCode + "]");
    return accountRepo.find(sortCode, accountNumber);
  }

  @Override
  public Account update(Account account) {
    return accountRepo.save(account);
  }

  @Override
  public List<Account> getAccounts() {
    return accountRepo.findAll();
  }

  @Override
  public TransferResult transfer(TransferRequest request) {
    try {
      Account from = getAccount(request.getFrom().getSortCode(), request.getFrom().getAccountNumber());
      if (from == null) {
        throw new TransferFailureException(FROM_NOT_FOUND);
      }
      if (!from.hasAtLeast(request.getAmount())) {
        throw new TransferFailureException(FROM_INSUFFICIENT_FUNDS);
      }

      Account to = getAccount(request.getTo().getSortCode(), request.getTo().getAccountNumber());
      if (to == null) {
        throw new TransferFailureException(TO_NOT_FOUND);
      }
      LOG.info("Amount Trasferring from %s to %s ", from.getName(), to.getName());
      from.debit(request.getAmount());
      to.credit(request.getAmount());

      accountRepo.save(from);
      accountRepo.save(to);

      createTransaction(from.getId(), request.getMessage(), request.getAmount(), Transaction.TransactionType.OUT);
      createTransaction(to.getId(), request.getMessage(), request.getAmount(), Transaction.TransactionType.IN);
      return TransferResult.success();
    } catch (Exception e) {
      if (e instanceof TransferFailureException) {
        TransferFailureException fe = (TransferFailureException) e;
        return TransferResult.fail(fe.getErrorCode());
      }
      throw e;
    }
  }

  private Transaction createTransaction(long accountId, String message, double amount, TransactionType type) {
    Transaction transaction = new Transaction();
    transaction.setAccount(accountId);
    transaction.setAmount(amount);
    transaction.setType(type);
    transaction.setMessage(message);
    return createTransaction(transaction);
  }

  @Override
  public List<Transaction> getTransactions(String sortCode, String accountNumber) {
    Account account = accountRepo.find(sortCode, accountNumber);
    if (account == null)
      throw new TransferFailureException(FROM_NOT_FOUND);
    return transactionRepo.find(account.getId());
  }

  public void setAccountRepo(AccountRepo accountRepo) {
    this.accountRepo = accountRepo;
  }

  public void setTransactionRepo(TransactionRepo transactionRepo) {
    this.transactionRepo = transactionRepo;
  }
  
}
