package com.sd.bank.test;

import java.util.List;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.sd.bank.dto.SortCodeAccountNumber;
import com.sd.bank.dto.TransferRequest;
import com.sd.bank.dto.TransferResult;
import com.sd.bank.model.Account;
import com.sd.bank.model.Transaction;
import com.sd.bank.repository.AccountRepo;
import com.sd.bank.repository.TransactionRepo;
import com.sd.bank.service.AccountServiceInMemoryImpl;

@PrepareForTest({ AccountServiceInMemoryImpl.class, AccountRepo.class, TransactionRepo.class })
public class TransferTest {

  AccountServiceInMemoryImpl service;

  @Mock
  AccountRepo accountRepo;

  @Mock
  TransactionRepo transactionRepo;

  @Before
  public void setUp() throws Exception {
    service = new AccountServiceInMemoryImpl();
    service.setAccountRepo(new AccountRepo());
    service.setTransactionRepo(new TransactionRepo());
  }

  @Test
  public void shouldTransferWhenHasBalance() {
    SortCodeAccountNumber from = buildAccount("SORT123", "123456");
    SortCodeAccountNumber to = buildAccount("SORT124", "123457");
    TransferRequest request = buildTransferReq(from, to, 50);
    TransferResult result = service.transfer(request);

    Assert.assertTrue(result.isTransferred());
    Account fromAcc = service.getAccount(from.getSortCode(), from.getAccountNumber());
    Account toAcc = service.getAccount(to.getSortCode(), to.getAccountNumber());
    Assert.assertEquals(fromAcc.getBalance(), 50.0, 50);
    Assert.assertEquals(toAcc.getBalance(), 150.0, 150);
  }

  private TransferRequest buildTransferReq(SortCodeAccountNumber from, SortCodeAccountNumber to, double balance) {
    TransferRequest request = new TransferRequest();
    request.setFrom(from);
    request.setTo(to);
    request.setAmount(balance);
    request.setMessage("Avail balance");
    return request;
  }

  private SortCodeAccountNumber buildAccount(String sortCode, String accountNum) {
    SortCodeAccountNumber account = new SortCodeAccountNumber();
    account.setAccountNumber(accountNum);
    account.setSortCode(sortCode);
    return account;
  }

  @Test
  public void shouldRegisterTransactionsWhenTransferred() {
    SortCodeAccountNumber from = buildAccount("SORT123", "123456");
    SortCodeAccountNumber to = buildAccount("SORT124", "123457");
    TransferRequest request = buildTransferReq(from, to, 50);
    TransferResult result = service.transfer(request);

    Assert.assertTrue(result.isTransferred());
    Account fromAcc = service.getAccount(from.getSortCode(), from.getAccountNumber());
    Account toAcc = service.getAccount(to.getSortCode(), to.getAccountNumber());
    Assert.assertEquals(fromAcc.getBalance(), 50, 50);
    Assert.assertEquals(toAcc.getBalance(), 150, 150);
    List<Transaction> fromTransactions = service.getTransactions(from.getSortCode(), from.getAccountNumber());
    List<Transaction> toTransactions = service.getTransactions(to.getSortCode(), to.getAccountNumber());
    Assert.assertEquals(fromTransactions.stream().findFirst().get().getAccount(), 50.0, 50);
    Assert.assertSame(fromTransactions.stream().findFirst().get().getType(), Transaction.TransactionType.OUT);
    Assert.assertEquals(toTransactions.stream().findFirst().get().getAccount(), 50.0, 50);
    Assert.assertSame(toTransactions.stream().findFirst().get().getType(), Transaction.TransactionType.IN);
  }

  /*
   * Not using error code constants that are defined in production code. This is because I want this test to fail in
   * case the error codes change. Error codes are a part of our API contract and consumers have to adapt. Until they do
   * we need to ensure backwards compatibility so they need to remain the same. So these tests also act like consumer
   * contract tests
   */

  @Test
  public void shouldNotTransferIfInsufficientBalance() {
    SortCodeAccountNumber from = buildAccount("SORT123", "123456");
    SortCodeAccountNumber to = buildAccount("SORT124", "123457");
    TransferRequest request = buildTransferReq(from, to, 150);
    TransferResult result = service.transfer(request);
    Assert.assertFalse(result.isTransferred());
    Assert.assertSame("from.insufficient-funds", result.getErrorCode());
    Account fromAcc = service.getAccount(from.getSortCode(), from.getAccountNumber());
    Account toAcc = service.getAccount(to.getSortCode(), to.getAccountNumber());
    Assert.assertEquals(fromAcc.getBalance(), 100.0, 100);
    Assert.assertEquals(toAcc.getBalance(), 100.0, 100);
  }

  @Test
  public void shouldFailWhenFromDoesNotExist() {
    SortCodeAccountNumber from = buildAccount("SORT129", "123456");
    SortCodeAccountNumber to = buildAccount("SORT124", "123457");
    TransferRequest request = buildTransferReq(from, to, 50);
    TransferResult result = service.transfer(request);
    Assert.assertFalse(result.isTransferred());
    Assert.assertSame(result.getErrorCode(), "from.not-found");
    Account fromAcc = service.getAccount(from.getSortCode(), from.getAccountNumber());
    Account toAcc = service.getAccount(to.getSortCode(), to.getAccountNumber());
    Assert.assertNull(fromAcc);
    Assert.assertEquals(toAcc.getBalance(), 100, 100);
  }

  @Test
  public void shouldFailWhenToDoesNotExist() {
    SortCodeAccountNumber from = buildAccount("SORT123", "123456");
    SortCodeAccountNumber to = buildAccount("SORT128", "123457");
    TransferRequest request = buildTransferReq(from, to, 50);
    TransferResult result = service.transfer(request);
    Assert.assertFalse(result.isTransferred());
    Assert.assertSame(result.getErrorCode(), "to.not-found");
    Account fromAcc = service.getAccount(from.getSortCode(), from.getAccountNumber());
    Account toAcc = service.getAccount(to.getSortCode(), to.getAccountNumber());
    Assert.assertNull(toAcc);
    Assert.assertEquals(fromAcc.getBalance(), 100, 100);
  }

}